package asmeta.asmeta_zeromq;

//import java.io.IOException;
//import java.io.InputStream;
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

 //Section prefix in the unified file (e.g., "producer", "consumer")
    private String sectionPrefix;
    
    private final String CONFIG_FILE_PATH = "in-memory-config";
    private String RUNTIME_MODEL_PATH;
    private String ZMQ_PUB_SOCKET;
    private String ZMQ_SUB_CONNECT_ADDRESSES;
    private String ASM_ENVIRONMENT_ADDRESS;
    private List<String> ASM_ENVIRONMENT_FUNCTIONS;
    private List<String> CONSOLE_INPUT_FUNCTIONS;
    private String HOST;                 // e.g. 127.0.0.1 (from common.HOST)

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
 
    public ZeroMQWA(Properties config, String sectionPrefix) {
        this.requiredMonitored = new HashSet<>();
        this.gson = new Gson();
        this.currentMonitoredValues = new HashMap<>();
        this.mapStringStringType = new TypeToken<Map<String, String>>() {}.getType();
        this.properties = config;
        this.sectionPrefix = sectionPrefix;   // <--- SET the prefix here
        logger.info("zeroMQW initialized from in-memory Properties for section: {}", sectionPrefix);

        try {
            // Lettura diretta dalle properties 
            this.RUNTIME_MODEL_PATH = config.getProperty("RUNTIME_MODEL_PATH");
            this.ZMQ_PUB_SOCKET = config.getProperty("ZMQ_PUB_SOCKET");
            this.ZMQ_SUB_CONNECT_ADDRESSES = config.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
            
            // Pulizia stringa indirizzi
            if (this.ZMQ_SUB_CONNECT_ADDRESSES == null || "null".equalsIgnoreCase(this.ZMQ_SUB_CONNECT_ADDRESSES.trim())) {
                this.ZMQ_SUB_CONNECT_ADDRESSES = "";
            }
            
            this.ASM_ENVIRONMENT_ADDRESS = config.getProperty("ASM_ENVIRONMENT_ADDRESS");
            
            String envFuncs = config.getProperty("ASM_ENVIRONMENT_FUNCTIONS", "");
            this.ASM_ENVIRONMENT_FUNCTIONS = envFuncs.isEmpty() ? new ArrayList<>() : Arrays.asList(envFuncs.split(","));
            
            this.HOST = config.getProperty("HOST", "127.0.0.1");
          
         // Console Inputs
            if (config.getProperty("CONSOLE_INPUT_FUNCTIONS") != null) {
                this.CONSOLE_INPUT_FUNCTIONS = Arrays.asList(config.getProperty("CONSOLE_INPUT_FUNCTIONS", "").split(","));
            } else {
                this.CONSOLE_INPUT_FUNCTIONS = null;
            }

            // Initialize ASM
            this.asmId = this.initializeAsm(this.RUNTIME_MODEL_PATH);

            logger.info("zeroMQW instance (in-memory config) initialized successfully for section '{}'", this.sectionPrefix);

        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR initializing zeroMQW from in-memory config for '{}': {}", this.sectionPrefix, e.getMessage(), e);
            throw new RuntimeException(e); 
        }
    }
 
    /////////////////////////////////////////////////////////////////////////////////////////
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
   
    /////////////////////////////////////////////////////////////////////////////////////////

    
 //  metodo initializeZmqSockets
    private void initializeZmqSockets(ZContext context) {
        logger.info("Initializing ZeroMQ sockets...");
        subscribers.clear();

        String pubBindAddress = this.properties.getProperty("ZMQ_PUB_SOCKET");
        
        if (pubBindAddress != null && !pubBindAddress.isEmpty()) {
            publisher = context.createSocket(SocketType.PUB);
            publisher.bind(pubBindAddress);
            logger.info("PUB Socket bound to Address: {}", pubBindAddress);
        } else {
            logger.error("ZMQ_PUB_SOCKET not defined for this model!");
        }

        String subConnectString = this.properties.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
  
        if (!subConnectString.trim().isEmpty()) {
            String[] subAddresses = subConnectString.split(",");
            logger.info("Attempting to create and connect {} SUB socket(s)...", subAddresses.length);

            for (String addr : subAddresses) {
                String trimmedAddr = addr.trim();
                if (!trimmedAddr.isEmpty()) {
                    try {
                        ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                        sub.connect(trimmedAddr);
                        sub.subscribe("".getBytes(ZMQ.CHARSET)); // Sottoscrivi a tutto
                        subscribers.add(sub);
                        logger.info("SUB socket connected and subscribed to address {}", trimmedAddr);
                    } catch (Exception e) {
                        logger.error("Failed to connect SUB socket to address '{}': {}", trimmedAddr, e.getMessage());
                    }
                }
            }
        } else {
            logger.info("No ZMQ_SUB_CONNECT_ADDRESSES defined. This model is not subscribing to other models.");
        }

        //  CONFIGURAZIONE ENVIRONMENT (Ricezione Input) 
        if (this.ASM_ENVIRONMENT_ADDRESS != null && !this.ASM_ENVIRONMENT_ADDRESS.trim().isEmpty()) {
            String envAddr = this.ASM_ENVIRONMENT_ADDRESS.trim(); 
            try {
                ZMQ.Socket environmentSocket = context.createSocket(SocketType.SUB);
                environmentSocket.connect(envAddr);
                
                // Subscribe solo ai topic specifici definiti nella lista ASM_ENVIRONMENT_FUNCTIONS
                if (this.ASM_ENVIRONMENT_FUNCTIONS != null) {
                    for (String topic : this.ASM_ENVIRONMENT_FUNCTIONS) {
                        if (topic != null && !topic.trim().isEmpty()) {
                            environmentSocket.subscribe(topic.trim().getBytes(ZMQ.CHARSET));
                        }
                    }
                }
                
                subscribers.add(environmentSocket);
                logger.info("Environment socket connected to address {}", envAddr);
            } catch (Exception e) {
                logger.error("Failed to connect to Environment at '{}': {}", envAddr, e.getMessage());
            }
        }
        
        logger.info("ZeroMQ Socket initialization completed with {} SUB connections.", subscribers.size());
    }
 
    private void handleSubscriptionMessages() {
        boolean messageReceived = false;
        for (int i = 0; i < subscribers.size(); i++) {
            ZMQ.Socket sub = subscribers.get(i);
            String message;
            while ((message = sub.recvStr(ZMQ.DONTWAIT)) != null) {
            	logger.debug("SUB raw frame: '{}'", message);

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
                        logger.info("Monitored updated: {}", currentMonitoredValues);

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
        response.putAll(output.getOutvalues());
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
/////////////////////////////////////////////////////////////////////////////////////////////////////
    public void run() {
        if (this.asmId <= 0) { // ASM ID should be positive
            logger.fatal("ASM ID was not initialized correctly. Aborting run loop.");
            return;
        }

        try {
            logger.info("Starting zeroMQW run loop for config {}...", this.CONFIG_FILE_PATH);
            try (ZContext context = new ZContext()) {
                initializeZmqSockets(context);
                logger.info("Entering main loop for {}...", this.CONFIG_FILE_PATH);

                initializeStartingValues();
                if (this.CONSOLE_INPUT_FUNCTIONS != null) {
                    logger.info("Starting values initialized: {}", currentMonitoredValues);
                } else {
                    logger.info("No starting values provided.");
                }

                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(1000);

                    handleSubscriptionMessages();
                    
                    if (!hasAllRequiredInputs()) {
                        logMissingInputsIfAny();
                        Thread.sleep(500);
                        continue;  
                    }
                    
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

    private boolean hasAllRequiredInputs() {
        if (requiredMonitored == null || requiredMonitored.isEmpty()) return true;
        for (String k : requiredMonitored) {
            if (!currentMonitoredValues.containsKey(k) || currentMonitoredValues.get(k) == null) return false;
        }
        return true;
    }

    private void logMissingInputsIfAny() {
        List<String> missing = new ArrayList<>();
        for (String k : requiredMonitored) {
            if (!currentMonitoredValues.containsKey(k) || currentMonitoredValues.get(k) == null) missing.add(k);
        }
        if (!missing.isEmpty()) logger.warn("Waiting for required inputs: {}", missing);
    }
 
}