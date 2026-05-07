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

    private static final Logger logger = LogManager.getLogger(ZeroMQWA.class);

 //Section prefix in the unified file
    private String sectionPrefix;    
    private final String CONFIG_FILE_PATH = "in-memory-config";
    private String RUNTIME_MODEL_PATH;
    private String ZMQ_PUB_SOCKET;
    private String ZMQ_SUB_CONNECT_ADDRESSES;
    private String ASM_ENVIRONMENT_ADDRESS;
    private List<String> ASM_ENVIRONMENT_FUNCTIONS;
    private List<String> CONSOLE_INPUT_FUNCTIONS;
    private String HOST;              

    
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
    private Map<String, Object> lastSafeOutput = new HashMap<>();
    private boolean isForkJoinMode = false;
 // Variabile per evitare il Double Rollback
    private boolean lastStepWasSafe = true;
 
    public ZeroMQWA(Properties config, String sectionPrefix) {
    	
    	this.requiredMonitored = new HashSet<>();
        this.gson = new Gson();
        this.currentMonitoredValues = new HashMap<>();
        this.mapStringStringType = new TypeToken<Map<String, String>>() {}.getType();
        this.properties = config;
        this.sectionPrefix = sectionPrefix;
        this.isForkJoinMode = Boolean.parseBoolean(config.getProperty("FORKJOIN_MODE", "false"));

        try {        	
        	// Lettura parametri base
            this.RUNTIME_MODEL_PATH = config.getProperty("RUNTIME_MODEL_PATH");
            this.ZMQ_PUB_SOCKET = config.getProperty("ZMQ_PUB_SOCKET");
            this.ASM_ENVIRONMENT_ADDRESS = config.getProperty("ASM_ENVIRONMENT_ADDRESS");
            this.HOST = config.getProperty("HOST", "127.0.0.1");
           

            String subAddresses = config.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
            this.ZMQ_SUB_CONNECT_ADDRESSES = "null".equalsIgnoreCase(subAddresses.trim()) ? "" : subAddresses.trim();
            
            // Lista funzioni ambiente
            String envFuncs = config.getProperty("ASM_ENVIRONMENT_FUNCTIONS", "");
            this.ASM_ENVIRONMENT_FUNCTIONS = envFuncs.isEmpty() ? new ArrayList<>() : Arrays.asList(envFuncs.split(","));
            
            // Console Inputs
            String consoleInputs = config.getProperty("CONSOLE_INPUT_FUNCTIONS");
            this.CONSOLE_INPUT_FUNCTIONS = (consoleInputs == null || consoleInputs.trim().isEmpty()) 
                                           ? null 
                                           : Arrays.asList(consoleInputs.split(","));  	
        	       	        	       	
            // Initialize ASM
            this.asmId = this.initializeAsm(this.RUNTIME_MODEL_PATH);

        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR initializing zeroMQW from in-memory config for '{}': {}", this.sectionPrefix, e.getMessage(), e);
            throw new RuntimeException(e); 
        }
    }
  
    /////////////////////////////////////////////////////////////////////////////////////////
    private int initializeAsm(String modelPath) throws Exception {

        sim = new SimulationContainer(Environment.TimeMngt.use_java_time);
        sim.init(1);

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
 //       logger.info("Initializing ZeroMQ sockets...");
        subscribers.clear();

        // PUB socket configuration
        if (this.ZMQ_PUB_SOCKET != null && !this.ZMQ_PUB_SOCKET.isEmpty()) {
            publisher = context.createSocket(SocketType.PUB);
            publisher.bind(this.ZMQ_PUB_SOCKET);
            logger.info("PUB Socket bound to Address: {}", this.ZMQ_PUB_SOCKET);
        } else {
            logger.error("ZMQ_PUB_SOCKET not defined for this model!");
        }
        
     // SUB socket configuration
        if (this.isForkJoinMode) {
            // If we are in ForkJoin, we don't connect to the environment or the other models.
            // We ONLY connect to the Orchestrator to receive commands
            try {
                ZMQ.Socket orchSub = context.createSocket(SocketType.SUB);
                orchSub.connect("tcp://127.0.0.1:5556"); //address of the Orchestrator
                orchSub.subscribe("ORCH_CMD".getBytes(ZMQ.CHARSET)); // We just listen to the commands
                subscribers.add(orchSub);
                logger.info("[{}] ForkJoin mode ON. Connected to Orchestrator on tcp://127.0.0.1:5556", sectionPrefix);
            } catch (Exception e) {
                logger.error("Error connecting to Orchestrator: {}", e.getMessage());
            }

        } else {
        
        // SUB socket configuration
        if (this.ZMQ_SUB_CONNECT_ADDRESSES != null && !this.ZMQ_SUB_CONNECT_ADDRESSES.isEmpty()) {
            String[] subAddresses = this.ZMQ_SUB_CONNECT_ADDRESSES.split(",");
            logger.info("Attempting to create and connect {} SUB socket(s)...", subAddresses.length);

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
        } else {
            logger.info("No ZMQ_SUB_CONNECT_ADDRESSES defined. This model is not subscribing to other models.");
        }

        // environment configuration (input reception)
        if (this.ASM_ENVIRONMENT_ADDRESS != null && !this.ASM_ENVIRONMENT_ADDRESS.isEmpty()) {
            try {
                ZMQ.Socket environmentSocket = context.createSocket(SocketType.SUB);
                environmentSocket.connect(this.ASM_ENVIRONMENT_ADDRESS);
                
                // Subscribe only to specific topics defined in the ASM_ENVIRONMENT_FUNCTIONS list
                if (this.ASM_ENVIRONMENT_FUNCTIONS != null) {
                    for (String topic : this.ASM_ENVIRONMENT_FUNCTIONS) {
                        if (topic != null && !topic.trim().isEmpty()) {
                            environmentSocket.subscribe(topic.trim().getBytes(ZMQ.CHARSET));
                        }
                    }

                }
                subscribers.add(environmentSocket);
                logger.info("Environment socket connected to address {}", this.ASM_ENVIRONMENT_ADDRESS);
            } catch (Exception e) {
                logger.error("Failed to connect to Environment at '{}': {}", this.ASM_ENVIRONMENT_ADDRESS, e.getMessage());
            }
        }
        
        logger.info("ZeroMQ Socket initialization completed with {} SUB connections.", subscribers.size());
    }
    }
 ///////////////////////////////////////////////////////////////////////////////////////////
    private void handleSubscriptionMessages() {
        boolean messageReceived = false;
        for (int i = 0; i < subscribers.size(); i++) {
            ZMQ.Socket sub = subscribers.get(i);
            String message;
            while ((message = sub.recvStr(ZMQ.DONTWAIT)) != null) {

                boolean topicReceived = false;
                for (String topic : this.ASM_ENVIRONMENT_FUNCTIONS) {
                    if (message.equals(topic)) {
                        topicReceived = true;
                        break;
                    }
                }
                if (topicReceived) {
                    continue; // Skip processing the topic string itself as a JSON message
                }
                messageReceived = true;
                message = message.trim();
           //     logger.debug("Received message on SUB socket #{}: {}", i, message);
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
        response.putAll(output.getOutvalues());
        response.put("asm_status", output.getEsit().toString());
        String jsonResponse = gson.toJson(response);
        logger.info("[{}] Output: {}",this.sectionPrefix, jsonResponse);
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
        if (this.asmId <= 0) { // ASM ID should be positive, ASM loaded successfully
            logger.fatal("ASM ID was not initialized correctly. Aborting run loop.");
            return;
        }

        try {
            try (ZContext context = new ZContext()) {
                initializeZmqSockets(context);

                initializeStartingValues();
                if (this.CONSOLE_INPUT_FUNCTIONS != null) {
                    logger.info("Starting values initialized: {}", currentMonitoredValues);
                } else {
                    logger.info("No starting values provided.");
                }

                while (!Thread.currentThread().isInterrupted()) {
                	//Synchronization Gate
                    boolean readyToStep = false;

                    // DATA RECEPTION AND COMMANDS
                    if (this.isForkJoinMode) {
                        String topic = null, payload = null; //topic = "ORCH_CMD" payload ="2,3,4"
                        //polling
                        for (ZMQ.Socket sub : subscribers) {
                            topic = sub.recvStr(ZMQ.DONTWAIT);
                            if (topic != null) { 
                            	payload = sub.recvStr(); 
                            	break; }
                        }
                        //ZMQ.poller?
                        if (topic == null || payload == null) {
                            try { 
                            	Thread.sleep(50); 
                            	} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                            continue;
                        }

                        if ("ORCH_CMD".equals(topic)) {
                            if ("CMD:ROLLBACK".equals(payload)) {
                                logger.warn("[{}] Ricevuto ROLLBACK GLOBALE.", sectionPrefix);
                                if (this.lastStepWasSafe) {
                                    sim.rollback(asmId);
                                    logger.info("[{}] Eseguito rollback. Allineato a S_n-1.", sectionPrefix);
                                } else {
                                    //logger.info("[{}] Ignoro comando: ero già a S_n-1.", sectionPrefix);
                                }
                                Map<String, String> statusMsg = new HashMap<>();
                                statusMsg.put("asm_status", "SAFE"); 
                                publisher.sendMore("STATUS_UPDATE");
                                publisher.send(gson.toJson(statusMsg));
                                continue;
                                
                            } else if (payload.startsWith("DATA:")) {
                                Map<String, String> receivedData = gson.fromJson(payload.substring(5), mapStringStringType);
                                if (receivedData != null) currentMonitoredValues.putAll(receivedData);
                                readyToStep = true;
                            }
                        }
                    } else {
                        // Classical Sequential Logic
                        Thread.sleep(1000);
                        handleSubscriptionMessages();
                        if (hasAllRequiredInputs()) {
                            readyToStep = true;
                        } else {
                            logMissingInputsIfAny();
                            Thread.sleep(500);
                        }
                    }

                    // EXECUTION OF THE ASMETA STEP 
                    if (readyToStep) {
                        Map<String, String> monitoredForStep = new HashMap<>();
                        for (String key : this.requiredMonitored) {
                            monitoredForStep.put(key, currentMonitoredValues.get(key));
                        }

                        logger.debug("Executing ASM step with monitored input: {}", monitoredForStep);
                        RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                        //  RESULT MANAGEMENT AND PUBLICATION
                        if (this.isForkJoinMode) {
                            Map<String, Object> response = new HashMap<>(output.getOutvalues());
                            if (output.getEsit() == Esit.SAFE) {
                                logger.info("[{}] ASM step SAFE. Output: {}", sectionPrefix, output.getOutvalues());
                                this.lastStepWasSafe = true;
                                response.put("asm_status", "SAFE");
                            } else {
                                logger.error("[{}] ASM step UNSAFE!", sectionPrefix);
                                handleUnsafeState(monitoredForStep);                        
                                this.lastStepWasSafe = false;
                                response.put("asm_status", "UNSAFE");
                            }
                            
                            String jsonResponse = gson.toJson(response);
                            publisher.sendMore("STATUS_UPDATE");
                            publisher.send(jsonResponse);
                            publisher.sendMore(this.sectionPrefix);
                            publisher.send(jsonResponse);
                            
                            //sequential run
                        } else {
                            if (output.getEsit() == Esit.SAFE) {
                                logger.info("ASM step SAFE. Output: {}", output.getOutvalues());
                                handlePublisherMessages(output);
                            } else {
                                handleUnsafeState(monitoredForStep);
                                handlePublisherMessages(output);
                            }
                        }


                        this.currentMonitoredValues.clear();
                    }
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
    

    //////////////////////////////////////////////
    
    public void initializeSockets(ZContext context) {
        initializeZmqSockets(context);
        initializeStartingValues();
        if (this.CONSOLE_INPUT_FUNCTIONS != null) {
            logger.info("Starting values initialized: {}", currentMonitoredValues);
        } else {
            logger.info("No starting values provided.");
        }
    }

    public boolean runStep() throws Exception {
    	
    	if (this.isForkJoinMode) {
            logger.error("Il metodo runStep() non supporta la modalità ForkJoin. Usa run() per l'orchestrazione continua.");
            return false; 
        }
    	
        handleSubscriptionMessages();
   
        if (!hasAllRequiredInputs()) {
        	//Waiting for required inputs
            logMissingInputsIfAny();
            //currentMonitoredValues ​​does not yet contain all requiredMonitored values
            return false;
        }
        Map<String, String> monitoredForStep = new HashMap<>();
        for (String key : this.requiredMonitored) {
            monitoredForStep.put(key, currentMonitoredValues.get(key));
        }
        
        RunOutput output;
            output = sim.runStep(this.asmId, monitoredForStep);
                                                
            if (output.getEsit() == Esit.SAFE) {
                logger.info("ASM step SAFE. Output: {}", output.getOutvalues());
                handlePublisherMessages(output);
                this.currentMonitoredValues.clear();
                return true;
            } else {
                handleUnsafeState(monitoredForStep);
                handlePublisherMessages(output);
                this.currentMonitoredValues.clear();
                return false;
            }
    }

    ///////////////////////////////////////////////

                
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