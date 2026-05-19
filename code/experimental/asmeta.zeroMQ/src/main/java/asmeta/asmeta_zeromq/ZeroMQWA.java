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
import java.util.Map.Entry;
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
    private Map<String, String> INITIAL_OUTPUTS; 
    private List<String> CONSOLE_INPUT_FUNCTIONS;
    private String HOST;              

    
    // Core components
    private SimulationContainer  sim;
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

    private boolean lastStepWasSafe = true;
    //choreographed and orchestrated
    private String executionMode; 
    private String myPubTopic;    
    private List<String> mySubTopics;
    private Map<String, Object> previousOutvalues = new HashMap<>();
    
    
    
 
    public ZeroMQWA(Properties config, String sectionPrefix) {
    	
    	this.requiredMonitored = new HashSet<>();
        this.gson = new Gson();
        this.currentMonitoredValues = new HashMap<>();
        this.mapStringStringType = new TypeToken<Map<String, String>>() {}.getType();
        this.properties = config;
        this.sectionPrefix = sectionPrefix;
        this.isForkJoinMode = Boolean.parseBoolean(config.getProperty("FORKJOIN_MODE", "false"));

        try {        	
        	// Reading basic parameters
            this.RUNTIME_MODEL_PATH = config.getProperty("RUNTIME_MODEL_PATH");
            this.ZMQ_PUB_SOCKET = config.getProperty("ZMQ_PUB_SOCKET");
            this.ASM_ENVIRONMENT_ADDRESS = config.getProperty("ASM_ENVIRONMENT_ADDRESS");
            this.HOST = config.getProperty("HOST", "127.0.0.1");
            
            
         // Initial outputs per bootstrap publish (CHOREOGRAPHED)
            String initialOutputsStr = config.getProperty("INITIAL_OUTPUTS", "");
            this.INITIAL_OUTPUTS = new HashMap<>();
            if (!initialOutputsStr.isEmpty()) {
                for (String pair : initialOutputsStr.split(",")) {
                    String[] kv = pair.trim().split("=");
                    if (kv.length == 2) {
                        this.INITIAL_OUTPUTS.put(kv[0].trim(), kv[1].trim());
                    }
                }
            }
            
            
            
           

            String subAddresses = config.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
            this.ZMQ_SUB_CONNECT_ADDRESSES = "null".equalsIgnoreCase(subAddresses.trim()) ? "" : subAddresses.trim();
            
            // environment functions
            String envFuncs = config.getProperty("ASM_ENVIRONMENT_FUNCTIONS", "");
            this.ASM_ENVIRONMENT_FUNCTIONS = envFuncs.isEmpty() ? new ArrayList<>() : Arrays.asList(envFuncs.split(","));
            // Console Inputs
            String consoleInputs = config.getProperty("CONSOLE_INPUT_FUNCTIONS");
            this.CONSOLE_INPUT_FUNCTIONS = (consoleInputs == null || consoleInputs.trim().isEmpty()) 
                                           ? null 
                                           : Arrays.asList(consoleInputs.split(","));  
            
            this.executionMode = config.getProperty("execution_mode", "LEGACY"); 
            
            if ("CHOREOGRAPHED".equals(this.executionMode)) {
                this.myPubTopic = config.getProperty("calculated_pub_topic");
                String subs = config.getProperty("calculated_sub_topics");
                this.mySubTopics = (subs != null && !subs.isEmpty()) 
                                   ? Arrays.asList(subs.split(",")) 
                                   : new ArrayList<>();
            }
        	       	        	       	
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

    
 //initializeZmqSockets
    private void initializeZmqSockets(ZContext context) {
 //       logger.info("Initializing ZeroMQ sockets...");
        subscribers.clear();

        // PUB socket configuration
        if (this.ZMQ_PUB_SOCKET != null && !this.ZMQ_PUB_SOCKET.isEmpty()) {
            publisher = context.createSocket(SocketType.PUB);
            publisher.bind(this.ZMQ_PUB_SOCKET);
            //logger.info("PUB Socket bound to Address: {}", this.ZMQ_PUB_SOCKET);
        } else {
            //logger.error("ZMQ_PUB_SOCKET not defined for this model!");
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
                //logger.info("[{}] ForkJoin mode ON. Connected to Orchestrator on tcp://127.0.0.1:5556", sectionPrefix);
            } catch (Exception e) {
                logger.error("Error connecting to Orchestrator: {}", e.getMessage());
            }

        } else {
        
        // SUB socket configuration
        if (this.ZMQ_SUB_CONNECT_ADDRESSES != null && !this.ZMQ_SUB_CONNECT_ADDRESSES.isEmpty()) {
            String[] subAddresses = this.ZMQ_SUB_CONNECT_ADDRESSES.split(",");
           // logger.info("Attempting to create and connect {} SUB socket(s)...", subAddresses.length);

            for (String addr : subAddresses) {
                String trimmedAddr = addr.trim();
                if (!trimmedAddr.isEmpty()) {
                    try {
                        ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                        sub.connect(trimmedAddr);
                        sub.subscribe("".getBytes(ZMQ.CHARSET));
                        
                        if ("CHOREOGRAPHED".equals(this.executionMode) && this.mySubTopics != null && !this.mySubTopics.isEmpty()) {
                            for (String topic : this.mySubTopics) {
                                sub.subscribe(topic.trim().getBytes(ZMQ.CHARSET));
                            }
                            logger.info("SUB socket connected to {} and subscribed to topics: {}", trimmedAddr, this.mySubTopics);
                        } else {
                            // Default legacy behaviour
                            sub.subscribe("".getBytes(ZMQ.CHARSET));
                           // logger.info("SUB socket connected and subscribed to all topics at address {}", trimmedAddr);
                        } 

                        subscribers.add(sub);
                       // logger.info("SUB socket connected and subscribed to address {}", trimmedAddr);
                    } catch (Exception e) {
                        logger.error("Failed to connect SUB socket to address '{}': {}", trimmedAddr, e.getMessage());
                    }
                }
            }
        } else {
            //logger.info("No ZMQ_SUB_CONNECT_ADDRESSES defined. This model is not subscribing to other models.");
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
        if ("CHOREOGRAPHED".equals(this.executionMode)) {
            runAsChoreographed();
        } else if ("ORCHESTRATED".equals(this.executionMode)) {
            runAsOrchestrated();
        } else {
            runAsLegacyOrchestrated();
        }
    }

////////////////////////////////////////////////////////
    public void runAsChoreographed() {
        if (this.asmId <= 0) {
            logger.fatal("ASM ID was not initialized correctly. Aborting run loop.");
            return;
        }

        try (ZContext context = new ZContext()) {
            initializeZmqSockets(context);
            initializeStartingValues();
            
            //pre publishing to avoid deadlock
            publishBootstrap();

           // logger.info("[{}] CHOREOGRAPHED execution has begun.Waiting topics: {}", sectionPrefix, mySubTopics);
            
            while (!Thread.currentThread().isInterrupted()) {
                boolean receivedData = false;

                for (ZMQ.Socket sub : subscribers) {
                    String topic = sub.recvStr(ZMQ.DONTWAIT);
                    if (topic != null) {
                        String payload = sub.recvStr();
                        logger.debug("[{}] Payload ricevuto su [{}]: {}", sectionPrefix, topic, payload);
                        updateMonitoredFromPayload(payload);
                        receivedData = true;
                    }
                }

                if (receivedData && hasAllRequiredInputs()) {
                	logger.debug("[{}] Conditions satisfied. Execute step.", sectionPrefix);
                    
                    Map<String, String> monitoredForStep = new HashMap<>();
                    for (String key : this.requiredMonitored) {
                        monitoredForStep.put(key, currentMonitoredValues.get(key));
                    }

                    java.io.PrintStream originalOut = System.out;
                    System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                        public void write(int b) { /* vuoto */ }
                    }));


                    RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                    System.setOut(originalOut);

                    if (output.getEsit() == Esit.SAFE) {
                        Map<String, String> currentOutvalues = output.getOutvalues();

                        logStateTransition(currentOutvalues);

                        Map<String, Object> response = new HashMap<>(currentOutvalues);
                        response.put("asm_status", "SAFE");
                        String jsonOut = gson.toJson(response);
                        
                        if (publisher != null && myPubTopic != null) {
                            publisher.sendMore(this.myPubTopic);
                            publisher.send(jsonOut);
                            logger.debug("[{}] Step SAFE. Pubblicato su [{}]", sectionPrefix, this.myPubTopic);
                        }

                        previousOutvalues = new HashMap<>(currentOutvalues);
                    }

                    	else {

                        logger.error("[{}] Step UNSAFE.  Local Rollback.", sectionPrefix);
                        handleUnsafeState(monitoredForStep);
                        sim.rollback(this.asmId);
                    }
                    
                   // currentMonitoredValues.clear(); 
                } else if (!receivedData) {
                    Thread.sleep(10); 
                }
            }
        } catch (InterruptedException e) {
            logger.warn("[{}] Ciclo coreografico interrotto.", sectionPrefix);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.fatal("[{}] ERRORE FATALE: {}", sectionPrefix, e.getMessage(), e);
        }
    }

    //Helper for extracting data from JSON 
    private void updateMonitoredFromPayload(String payload) {
        try {
            Map<String, String> receivedData = gson.fromJson(payload, mapStringStringType);
            if (receivedData != null) {
                currentMonitoredValues.putAll(receivedData);
                logger.debug("[{}] monitored attuali: {}", sectionPrefix, currentMonitoredValues);
            } else {
                logger.warn("[{}] DEBUG payload non parsato come JSON: {}", sectionPrefix, payload);
            }
        } catch (Exception e) {
            logger.error("[{}] Failed to parse JSON payload '{}': {}", sectionPrefix, payload, e.getMessage());
        }
    }
    
    /*Bootstrap publish: publishes the initial state (s0) to the OUT topics.
     * Required to break circular deadlocks involving <|> and <||> in choreographed mode.
     * Without this step, mutually dependent models would remain blocked 
     * while waiting for each other’s out functions.
     */
    private void publishBootstrap() {
        try {
            Thread.sleep(1500);
            
            if (INITIAL_OUTPUTS == null || INITIAL_OUTPUTS.isEmpty()) {
                //logger.warn("[{}] Bootstrap saltato: INITIAL_OUTPUTS non definiti nel config", sectionPrefix);
                return;
            }
            
            if (publisher == null || myPubTopic == null) {
               // logger.warn("[{}] Bootstrap saltato: publisher non configurato", sectionPrefix);
                return;
            }
            
            Map<String, Object> bootstrapPayload = new HashMap<>(INITIAL_OUTPUTS);
            bootstrapPayload.put("asm_status", "SAFE");
            
            String jsonOut = gson.toJson(bootstrapPayload);
            publisher.sendMore(this.myPubTopic);
            publisher.send(jsonOut);
           // logger.info("[{}] Bootstrap pubblicato su [{}]: {}", sectionPrefix, myPubTopic, jsonOut);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
           // logger.warn("[{}] Bootstrap interrotto", sectionPrefix);
        } catch (Exception e) {
            logger.error("[{}] Errore durante il bootstrap publish: {}",  sectionPrefix, e.getMessage(), e);
        }
    }
    
    
    private void logStateTransition(Map<String, String> currentOutvalues) {
        List<String> changes = new ArrayList<>();
        
        for (Entry<String, String> entry : currentOutvalues.entrySet()) {
            String key = entry.getKey();
            Object newValue = entry.getValue();
            Object oldValue = previousOutvalues.get(key);
            
            if (oldValue == null) {

                changes.add(key + "=" + newValue);
            } else if (!oldValue.equals(newValue)) {

                changes.add(key + ": " + oldValue + " → " + newValue);
            }

        }
        
        if (!changes.isEmpty()) {
            logger.info("[{}] {}", sectionPrefix, String.join(", ", changes));
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////    
    public void runAsOrchestrated() {
        if (this.asmId <= 0) {
            logger.fatal("ASM ID was not initialized correctly. Aborting.");
            return;
        }

        try (ZContext context = new ZContext()) {
            ZMQ.Socket subSocket = context.createSocket(SocketType.SUB);
            ZMQ.Socket pubSocket = context.createSocket(SocketType.PUB);

            String orchPubAddr = "tcp://127.0.0.1:5550"; 
            String orchSubAddr = "tcp://127.0.0.1:5551"; 

            subSocket.connect(orchPubAddr);
            subSocket.subscribe(("CMD_" + this.sectionPrefix).getBytes(ZMQ.CHARSET));
            subSocket.subscribe("CMD:ROLLBACK_ALL".getBytes(ZMQ.CHARSET));

            pubSocket.connect(orchSubAddr);

            logger.info("[{}] Worker ORCHESTRATO Avviato. Connesso al Master.", this.sectionPrefix);
            initializeStartingValues();

            while (!Thread.currentThread().isInterrupted()) {
                String topic = subSocket.recvStr();
                String payload = subSocket.recvStr();

                // 1. Comando di Rollback Globale
                if ("CMD:ROLLBACK_ALL".equals(topic)) {
                    logger.warn("[{}] Eseguo ROLLBACK su ordine dell'Orchestratore!", this.sectionPrefix);
                    sim.rollback(this.asmId);
                    continue;
                }

                // 2. Comando di Esecuzione Step
                if (("CMD_" + this.sectionPrefix).equals(topic)) {
                    try {
                        // Parsa i dati passati dal Visitor
                        Map<String, Object> command = gson.fromJson(payload, new TypeToken<Map<String, Object>>(){}.getType());
                        Object dataObj = command.get("data");
                        
                        // Estrae i JSON dal payload per caricare i valori monitorati
                        if (dataObj instanceof List) {
                            for(Object item : (List<?>)dataObj) {
                                String jsonStr = String.valueOf(item);
                                if (jsonStr.startsWith("{")) updateMonitoredFromPayload(jsonStr);
                            }
                        }

                        if (hasAllRequiredInputs()) {
                            Map<String, String> monitoredForStep = new HashMap<>();
                            for (String key : this.requiredMonitored) {
                                monitoredForStep.put(key, currentMonitoredValues.get(key));
                            }

                            RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                            Map<String, Object> response = new HashMap<>();
                            if (output.getEsit() == Esit.SAFE) {
                                response.put("asm_status", "SAFE");
                                response.put("out_data", output.getOutvalues());
                                logger.info("[{}] Step COMPLETED (SAFE).", this.sectionPrefix);
                            } else {
                                response.put("asm_status", "UNSAFE");
                                handleUnsafeState(monitoredForStep);
                            }

                            pubSocket.sendMore("STATUS_" + this.sectionPrefix);
                            pubSocket.send(gson.toJson(response));
                            
                            currentMonitoredValues.clear(); 
                        } else {
                            logMissingInputsIfAny();
                            Map<String, Object> response = new HashMap<>();
                            response.put("asm_status", "UNSAFE");
                            pubSocket.sendMore("STATUS_" + this.sectionPrefix);
                            pubSocket.send(gson.toJson(response));
                        }
                    } catch (Exception e) {
                        logger.error("[{}] Errore durante l'esecuzione: {}", this.sectionPrefix, e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.fatal("[{}] ERRORE in runAsOrchestrated: {}", this.sectionPrefix, e.getMessage(), e);
        }
    }
    //////////////////////////////////////////////
    public void runAsLegacyOrchestrated() {
        if (this.asmId <= 0) return;
        try {
            try (ZContext context = new ZContext()) {
                initializeZmqSockets(context);
                initializeStartingValues();

                while (!Thread.currentThread().isInterrupted()) {
                    boolean readyToStep = false;
                    if (this.isForkJoinMode) {
                        String topic = null, payload = null; 
                        for (ZMQ.Socket sub : subscribers) {
                            topic = sub.recvStr(ZMQ.DONTWAIT);
                            if (topic != null) { payload = sub.recvStr(); break; }
                        }
                        if (topic == null || payload == null) {
                            try { Thread.sleep(50); } catch (InterruptedException e) {}
                            continue;
                        }

                        if ("ORCH_CMD".equals(topic)) {
                            if ("CMD:ROLLBACK".equals(payload)) {
                                if (this.lastStepWasSafe) sim.rollback(asmId);
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
                        Thread.sleep(1000);
                        handleSubscriptionMessages();
                        if (hasAllRequiredInputs()) readyToStep = true;
                        else { logMissingInputsIfAny(); Thread.sleep(500); }
                    }

                    if (readyToStep) {
                        Map<String, String> monitoredForStep = new HashMap<>();
                        for (String key : this.requiredMonitored) monitoredForStep.put(key, currentMonitoredValues.get(key));

                        RunOutput output = sim.runStep(this.asmId, monitoredForStep);

                        if (this.isForkJoinMode) {
                            Map<String, Object> response = new HashMap<>(output.getOutvalues());
                            if (output.getEsit() == Esit.SAFE) {
                                this.lastStepWasSafe = true;
                                response.put("asm_status", "SAFE");
                            } else {
                                handleUnsafeState(monitoredForStep);                        
                                this.lastStepWasSafe = false;
                                response.put("asm_status", "UNSAFE");
                            }
                            String jsonResponse = gson.toJson(response);
                            publisher.sendMore("STATUS_UPDATE");
                            publisher.send(jsonResponse);
                            publisher.sendMore(this.sectionPrefix);
                            publisher.send(jsonResponse);
                        } else {
                            if (output.getEsit() == Esit.SAFE) handlePublisherMessages(output);
                            else { handleUnsafeState(monitoredForStep); handlePublisherMessages(output); }
                        }
                        this.currentMonitoredValues.clear();
                    }
                }
            }
        } catch (Exception e) {
            logger.fatal("CRITICAL ERROR in run loop: {}", e.getMessage(), e);
        }
    }
    
    
    /////////////////////////////////////////////
    
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