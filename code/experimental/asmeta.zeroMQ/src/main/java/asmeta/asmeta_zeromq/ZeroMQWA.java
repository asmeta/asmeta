package asmeta.asmeta_zeromq;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.asmeta.simulator.Environment;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ZeroMQWA {

    // Logger
    private static final Logger logger = LogManager.getLogger(ZeroMQWA.class);

    // Instance variables matching configuration keys (UPPER_SNAKE_CASE)
    private final String CONFIG_FILE_PATH;
    private String RUNTIME_MODEL_PATH;
    private String ZMQ_PUB_SOCKET;
    private String ZMQ_SUB_CONNECT_ADDRESSES;
    private String ASM_ENVIRONMENT_ADDRESS;
    private List<String> ASM_ENVIRONMENT_FUNCTIONS;
    private List<String> CONSOLE_INPUT_FUNCTIONS;

    // Core components
    private SimulationContainer sim;
    private ZMQ.Socket publisher;
    private final List<ZMQ.Socket> subscribers = new ArrayList<>();
    private Properties properties;
    private Gson gson;
    private Type mapStringStringType;

    // Other instance variables
    private int asmId;
    private Set<String> requiredMonitored;
    private Map<String, String> currentMonitoredValues;

    public ZeroMQWA(String config_filepath) {
        this.requiredMonitored = new HashSet<>();
        this.gson = new Gson();
        this.currentMonitoredValues = new HashMap<>();
        this.mapStringStringType = new TypeToken<Map<String, String>>() {}.getType();
        this.CONFIG_FILE_PATH = config_filepath;
        logger.info("zeroMQW initialized for config: {}", this.CONFIG_FILE_PATH);

        try {
            // Configuration section
            Properties config = this.loadConfig();
            this.RUNTIME_MODEL_PATH = config.getProperty("RUNTIME_MODEL_PATH");
            this.ZMQ_PUB_SOCKET = config.getProperty("ZMQ_PUB_SOCKET");
            this.ZMQ_SUB_CONNECT_ADDRESSES = config.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
            this.ASM_ENVIRONMENT_ADDRESS = config.getProperty("ASM_ENVIRONMENT_ADDRESS");
            this.ASM_ENVIRONMENT_FUNCTIONS = Arrays.asList(config.getProperty("ASM_ENVIRONMENT_FUNCTIONS", "").split(","));

            if (config.getProperty("CONSOLE_INPUT_FUNCTIONS") != null) {
                this.CONSOLE_INPUT_FUNCTIONS = Arrays.asList(config.getProperty("CONSOLE_INPUT_FUNCTIONS", "").split(","));
            } else {
                this.CONSOLE_INPUT_FUNCTIONS = null;
            }

            // Initialize ASM
            this.asmId = this.initializeAsm(this.RUNTIME_MODEL_PATH);

            logger.info("zeroMQW instance configured successfully for {}", this.CONFIG_FILE_PATH);

        } catch (IOException | NullPointerException e) {
            logger.fatal("CRITICAL ERROR during zeroMQW initialization for {}: {}", this.CONFIG_FILE_PATH, e.getMessage(), e);
        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR during initialization for {}: {}", this.CONFIG_FILE_PATH, e.getMessage(), e);
        }
    }

    private Properties loadConfig() throws IOException, NullPointerException {
        properties = new Properties();
        try (InputStream input = ZeroMQWA.class.getResourceAsStream(this.CONFIG_FILE_PATH)) {
            if (input == null) {
                logger.error("Config file not found at path: {}", this.CONFIG_FILE_PATH);
                throw new IOException("Config file not found, path: " + this.CONFIG_FILE_PATH);
            }
            properties.load(input);
            logger.debug("Properties loaded from file.");

            if (properties.getProperty("RUNTIME_MODEL_PATH") == null || properties.getProperty("ZMQ_PUB_SOCKET") == null) {
                String missing = "";
                if (properties.getProperty("RUNTIME_MODEL_PATH") == null) missing += "RUNTIME_MODEL_PATH ";
                if (properties.getProperty("ZMQ_PUB_SOCKET") == null) missing += "ZMQ_PUB_SOCKET ";
                logger.error("Essential configuration parameters missing: {}", missing.trim());
                throw new NullPointerException("ERROR Essential parameters missing: " + missing.trim());
            }
        } catch (IOException e) {
            logger.error("ERROR loading config file '{}': {}", this.CONFIG_FILE_PATH, e.getMessage(), e);
            throw e;
        } catch (NullPointerException e) {
            logger.error("ERROR checking config properties: {}", e.getMessage(), e);
            throw e;
        }
        logger.info("Configuration Loaded Successfully!");
        logger.info(" * {} = {}", "RUNTIME_MODEL_PATH", properties.getProperty("RUNTIME_MODEL_PATH"));
        logger.info(" * {} = {}", "ZMQ_PUB_SOCKET", properties.getProperty("ZMQ_PUB_SOCKET"));
        logger.info(" * {} = {}", "ZMQ_SUB_CONNECT_ADDRESSES", properties.getProperty("ZMQ_SUB_CONNECT_ADDRESSES"));
        return properties;
    }

    private int initializeAsm(String modelPath) throws Exception {
        logger.info("Initializing ASM simulation container...");
        sim = new SimulationContainer(Environment.TimeMngt.use_java_time);
        sim.init(1);
        logger.debug("Simulation container initialized.");
        logger.info("Starting ASM execution for model: {}", modelPath);
        int currentAsmId = sim.startExecution(modelPath);
        if (currentAsmId < 0) {
            logger.error("Starting ASM model failed: negative id received ({})", currentAsmId);
            throw new Exception("Starting ASM model failed: negative id received ( " + currentAsmId + " )");
        }
        logger.info("Started ASM Model successfully! Model path: {} with ID: {}", modelPath, currentAsmId);
        this.requiredMonitored.addAll(sim.getMonitored(modelPath));
        if (this.requiredMonitored.isEmpty()) {
            logger.warn("No required monitored vars specified in the model '{}'", modelPath);
        } else {
            logger.info("Required monitored vars for model ID {}: {}", currentAsmId, this.requiredMonitored);
        }
        return currentAsmId;
    }

    private void initializeZmqSockets(ZContext context, String pubBindAddress, String subConnectAddressesString) {
        logger.info("Initializing ZeroMQ sockets...");
        publisher = context.createSocket(SocketType.PUB);
        publisher.bind(pubBindAddress);
        logger.info("PUB Socket bound to Address: {}", pubBindAddress);

        String[] subAddresses = subConnectAddressesString.split(",");
        logger.info("Attempting to create and connect {} SUB socket(s)...", subAddresses.length);
        subscribers.clear();
        for (String addr : subAddresses) {
            String trimmedAddr = addr.trim();
            if (!trimmedAddr.isEmpty()) {
                try {
                    ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                    sub.connect(trimmedAddr);
                    sub.subscribe("".getBytes(ZMQ.CHARSET));
                    subscribers.add(sub);
                    logger.info("SUB socket connected and subscribed to address {}", trimmedAddr);
                } catch (Exception e) {
                    logger.error("Failed to connect SUB socket to address '{}': {}", trimmedAddr, e.getMessage());
                }
            }
        }

        // Initialize environment socket
        if (this.ASM_ENVIRONMENT_ADDRESS != null && !this.ASM_ENVIRONMENT_ADDRESS.trim().isEmpty()) {
            ZMQ.Socket environmentSocket = context.createSocket(SocketType.SUB);
            environmentSocket.connect(this.ASM_ENVIRONMENT_ADDRESS);
            // Subscribe only to ASM_ENVIRONMENT_FUNCTIONS
            for (String topic : this.ASM_ENVIRONMENT_FUNCTIONS) {
                if (topic != null && !topic.trim().isEmpty()) {
                    environmentSocket.subscribe(topic.getBytes(ZMQ.CHARSET));
                }
            }
            subscribers.add(environmentSocket);
            logger.info("Environment socket connected to address {}", this.ASM_ENVIRONMENT_ADDRESS);
        }

        logger.info("ZeroMQ Socket initialization completed with {} SUB connections.", subscribers.size());
    }

    private void handleSubscriptionMessages() {
        boolean messageReceived = false;
        for (int i = 0; i < subscribers.size(); i++) {
            ZMQ.Socket sub = subscribers.get(i);
            String message;
            while ((message = sub.recvStr(ZMQ.DONTWAIT)) != null) {
                boolean topicReceived = false;
                for (String topic : this.ASM_ENVIRONMENT_FUNCTIONS) {
                    if (message.equals(topic)) {
                        logger.debug("Received topic identifier: {} on SUB socket #{}", topic, i);
                        topicReceived = true;
                        break;
                    }
                }
                if (topicReceived) {
                    continue; // Skip processing the topic string itself as a JSON message
                }
                messageReceived = true;
                message = message.trim();
                logger.debug("Received message on SUB socket #{}: {}", i, message);
                try {
                    Map<String, String> receivedData = gson.fromJson(message, mapStringStringType);
                    if (receivedData != null) {
                        currentMonitoredValues.putAll(receivedData);
                        logger.trace("Monitored values updated: {}", currentMonitoredValues);
                    } else {
                        logger.warn("Parsed JSON was null from SUB socket #{}, msg='{}'", i, message);
                    }
                } catch (Exception e) {
                    logger.error("Failed to parse incoming JSON on SUB socket #{}: {}", i, e.getMessage());
                }
            }
        }
        if (!messageReceived) {
            logger.trace("No messages received on any SUB socket in this check.");
        }
    }

    private void handlePublisherMessages(RunOutput output) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(output.getOutvaluesZMQ());
        response.put("asm_status", output.getEsit().toString());
        String jsonResponse = gson.toJson(response);
        logger.info("Publishing output: {}", jsonResponse);
        publisher.send(jsonResponse);
    }

    private void handleUnsafeState(Map<String, String> monitoredForStep) {
        logger.error("ASM state is UNSAFE after step with input: {}", monitoredForStep);
    }

    private void initializeStartingValues() {
        if (this.CONSOLE_INPUT_FUNCTIONS == null) {
            return;
        }
        for (int i = 0; i < this.CONSOLE_INPUT_FUNCTIONS.size(); i += 2) {
            String key = this.CONSOLE_INPUT_FUNCTIONS.get(i);
            String value = this.CONSOLE_INPUT_FUNCTIONS.get(i + 1);
            currentMonitoredValues.put(key, value);
            logger.info("Initialized starting value for key: {} with value: {}", key, value);
        }
    }

    public void run() {
        if (this.asmId <= 0) { // ASM ID should be positive
            logger.fatal("ASM ID was not initialized correctly. Aborting run loop.");
            return;
        }

        try {
            logger.info("Starting zeroMQW run loop for config {}...", this.CONFIG_FILE_PATH);
            try (ZContext context = new ZContext()) {
                initializeZmqSockets(context, this.ZMQ_PUB_SOCKET, this.ZMQ_SUB_CONNECT_ADDRESSES);
                logger.info("Entering main loop for {}...", this.CONFIG_FILE_PATH);

                initializeStartingValues();
                if (this.CONSOLE_INPUT_FUNCTIONS != null) {
                    logger.info("Starting values initialized: {}", currentMonitoredValues);
                } else {
                    logger.info("No starting values provided.");
                }

                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(3000);

                    handleSubscriptionMessages();

                    Map<String, String> monitoredForStep = new HashMap<>();
                    for (String key : this.requiredMonitored) {
                        monitoredForStep.put(key, currentMonitoredValues.get(key));
                    }

                    logger.debug("Executing ASM step with monitored input: {}", monitoredForStep);
                    RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                    if (output.getEsit() == Esit.SAFE) {
                        logger.info("ASM step SAFE. Output: {}", output.getOutvalues());
                        handlePublisherMessages(output);
                    } else {
                        handleUnsafeState(monitoredForStep);
                    }
                    logger.debug("Step processing complete.");
                }
            }
        } catch (InterruptedException e) {
            logger.warn("Run loop interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR in run loop: {}", e.getMessage(), e);
        } finally {
            logger.info("zeroMQW run method finished for {}.", this.CONFIG_FILE_PATH);
        }
    }
}