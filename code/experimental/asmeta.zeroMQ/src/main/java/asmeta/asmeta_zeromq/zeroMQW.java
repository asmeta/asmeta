package asmeta.asmeta_zeromq;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class zeroMQW extends Thread {

    private static final Logger logger = LogManager.getLogger(zeroMQW.class);

    private final String CONFIG_FILE_PATH;
    private static final String RUNTIME_MODEL_PATH = "RUNTIME_MODEL_PATH";
    private static final String ZMQ_PUB_SOCKET = "ZMQ_PUB_SOCKET";
    private static final String ZMQ_SUB_CONNECT_ADDRESSES = "ZMQ_SUB_CONNECT_ADDRESSES";

    private SimulationContainer sim;
    private ZMQ.Socket publisher;
    private final List<ZMQ.Socket> subscribers = new ArrayList<>();
    private Properties properties;

    private int asmId;
    private String modelPath;
    private String pubAddress;
    private String subConnectAddresses;

    private Set<String> requiredMonitored;
    private Map<String, String> currentMonitoredValues;
    private Gson gson;
    private Type mapStringStringType;

    public zeroMQW(String config_filepath) {
        this.requiredMonitored = new HashSet<>();
        this.currentMonitoredValues = new HashMap<>();
        this.gson = new Gson();
        this.mapStringStringType = new TypeToken<Map<String, String>>(){}.getType();
        this.CONFIG_FILE_PATH = config_filepath;
        logger.info("zeroMQW initialized for config: {}", config_filepath);

        try {
            // Configuration section moved from main
            Properties config = this.loadConfig();
            this.modelPath = config.getProperty(RUNTIME_MODEL_PATH);
            this.pubAddress = config.getProperty(ZMQ_PUB_SOCKET);
            this.subConnectAddresses = config.getProperty(ZMQ_SUB_CONNECT_ADDRESSES, "");

            // Initialize ASM moved from main
            this.asmId = this.initializeAsm(this.modelPath);

            logger.info("zeroMQW instance configured for {}", config_filepath);

        } catch (IOException | NullPointerException e) {
            logger.fatal("CRITICAL ERROR during zeroMQW initialization for {}: {}", config_filepath, e.getMessage(), e);
        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR during ASM initialization for {}: {}", config_filepath, e.getMessage(), e);
        }
    }

    private Properties loadConfig() throws IOException, NullPointerException {
        properties = new Properties();

        // Load config file try_catch block
        try (InputStream input = zeroMQW.class.getResourceAsStream(CONFIG_FILE_PATH)) {
            if (input == null) {
                logger.error("Config file not found at path: {}", CONFIG_FILE_PATH);
                throw new IOException("Config file not found, path: " + CONFIG_FILE_PATH );
            }
            properties.load(input);
            logger.debug("Properties loaded from file.");

            // Check for essential properties
            if (properties.getProperty(RUNTIME_MODEL_PATH) == null ||
                properties.getProperty(ZMQ_PUB_SOCKET) == null) {

                StringBuilder missing = new StringBuilder();
                if (properties.getProperty(RUNTIME_MODEL_PATH) == null) missing.append(RUNTIME_MODEL_PATH).append(" ");
                if (properties.getProperty(ZMQ_PUB_SOCKET) == null) missing.append(ZMQ_PUB_SOCKET).append(" ");
                logger.error("Essential configuration parameters missing: {}", missing.toString().trim());
                throw new NullPointerException("ERROR Essential parameters missing: " + missing.toString().trim());
            }

        } catch (IOException e) {
            logger.error("ERROR loading config file '{}': {}", CONFIG_FILE_PATH, e.getMessage(), e);
            throw e;
        } catch (NullPointerException e) {
            logger.error("ERROR checking config properties: {}", e.getMessage(), e);
            throw e;
        }

        logger.info("Configuration Loaded Successfully!");
        logger.info(" * {} = {}", RUNTIME_MODEL_PATH, properties.getProperty(RUNTIME_MODEL_PATH));
        logger.info(" * {} = {}", ZMQ_PUB_SOCKET, properties.getProperty(ZMQ_PUB_SOCKET));
        logger.info(" * {} = {}", ZMQ_SUB_CONNECT_ADDRESSES, properties.getProperty(ZMQ_SUB_CONNECT_ADDRESSES));
        return properties;
    }

    private int initializeAsm(String modelPath) throws Exception {
        logger.info("Initializing ASM simulation container...");
        sim = new SimulationContainer();
        sim.init(1);
        logger.debug("Simulation container initialized.");

        logger.info("Starting ASM execution for model: {}", modelPath);
        int asmId = sim.startExecution(modelPath);
        if (asmId < 0) {
            logger.error("Starting ASM model failed: negative id received ({})", asmId);
            throw new Exception("Starting ASM model failed: negative id received (" + asmId + ")");
        }
        logger.info("Started ASM Model successfully! ID: {}", asmId);

        // Load monitored from model directly
        this.requiredMonitored.addAll(sim.getMonitored(modelPath));
        if (this.requiredMonitored.isEmpty()) {
            logger.warn("No required monitored vars specified in the model '{}'", modelPath);
        } else {
            logger.info("Required monitored vars for model ID {}: {}", asmId, this.requiredMonitored);
        }
        return asmId;
    }

    private void initializeZmqSockets(ZContext context, String pubBindAddress, String subConnectAddressesString) {
        logger.info("Initializing ZeroMQ sockets...");

        // Create publisher socket
        publisher = context.createSocket(SocketType.PUB);
        publisher.bind(pubBindAddress);
        logger.info("PUB Socket bound to Address: {}", pubBindAddress);

        // One SUB socket per address, split the addresses string into an array (from the config file)
        String[] subAddresses = subConnectAddressesString.split(",");
        logger.info("Attempting to create {} subscriber connections...", subAddresses.length);
        for (String addr : subAddresses) {
            String trimmed = addr.trim();
            if (trimmed.isEmpty()) continue;
            try {
                ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                sub.connect(trimmed);
                sub.subscribe(ZMQ.SUBSCRIPTION_ALL);
                subscribers.add(sub);
                logger.info("SUB socket connected & subscribed to {}", trimmed);
            } catch (Exception e) {
                logger.error("Failed SUB connection to '{}': {}", trimmed, e.getMessage());
            }
        }
        logger.info("ZeroMQ Socket initialization completed with {} SUB sockets.", subscribers.size());
    }

    // Handle data received -> updates input variables (monitored)
    private void handleSubscriptionMessages() {
        // Scan all SUB sockets
        for (ZMQ.Socket sub : subscribers) {
            String msg = sub.recvStr(ZMQ.DONTWAIT);
            if (msg == null) continue;
            msg = msg.trim();
            int idx = subscribers.indexOf(sub);
            logger.debug("Received on SUB[{}]: {}", idx, msg);
            try {
                Map<String, String> data = gson.fromJson(msg, mapStringStringType);
                if (data != null) {
                    currentMonitoredValues.putAll(data);
                    logger.trace("Monitored now: {}", currentMonitoredValues);
                } else {
                    logger.warn("Received null JSON from SUB[{}]", idx);
                }
            } catch (Exception e) {
                logger.error("JSON parse error on SUB[{}]: {}", idx, e.getMessage());
            }
        }
    }

    // Prepare and publish the output from the ASM step
    private void handlePublisherMessages(RunOutput output) {
        // Build the response with the output values and the ASM status
        Map<String, Object> response = new HashMap<>();
        response.putAll(output.getOutvalues());
        response.put("asm_status", output.getEsit().toString());

        String jsonResponse = gson.toJson(response);
        logger.info("Publishing output: {}", jsonResponse);

        // Send the response to the publisher socket
        publisher.send(jsonResponse);
    }

    // Handle UNSAFE state
    private void handleUnsafeState(Map<String, String> monitoredForStep) {
        logger.error("ASM state is UNSAFE after step with input: {}", monitoredForStep);
    }

    @Override
    public void run() {
        while (this.asmId == 0) {
            logger.info("Waiting for ASM ID to be set...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Thread interrupted while waiting for ASM ID: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        try {
            logger.info("Starting zeroMQW run loop for config {}...", CONFIG_FILE_PATH);
            try (ZContext context = new ZContext()) {
                // Use instance variables for addresses
                initializeZmqSockets(context, this.pubAddress, this.subConnectAddresses);
                logger.info("Entering main loop for {}...", CONFIG_FILE_PATH);

                // Start Loop
                while ( true ) {
                    // Wait for 1.5 seconds before processing the next step
                    Thread.sleep(1500);

                    // 1. Handle listen section
                    handleSubscriptionMessages();

                    logger.info("All required monitored vars received and non-null ({}), proceeding with ASM step.", requiredMonitored);

                    // 2. Prepare input for this step 
                    Map<String, String> monitoredForStep = new HashMap<>();
                    for (String key : requiredMonitored) {
                        monitoredForStep.put(key, currentMonitoredValues.get(key));
                    }
                    logger.debug("Executing ASM step with monitored input: {}", monitoredForStep);

                    // 3. Run a step
                    RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                    // 4. Process result and publish
                    if (output.getEsit() == Esit.SAFE) {
                        logger.info("ASM step SAFE. Output: {}", output.getOutvalues());
                        handlePublisherMessages(output);
                    } else {
                        handleUnsafeState(monitoredForStep);
                    }

                    logger.debug("Step processing complete.");
                }

            }

        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR in run loop: {}", e.getMessage(), e);
        } finally {
            logger.info("zeroMQW run method finished for {}.", CONFIG_FILE_PATH);
        }
    }
}