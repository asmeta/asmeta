package asmeta.asmeta_zeromq.registry;
 
import asmeta.asmeta_zeromq.ZeroMQWA;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
public class ForkJoinComposer extends SimulationLauncher {
 
    public ForkJoinComposer(String configPath) {
        super(configPath);
    }

 /*Not used: ForkJoinComposer spawns separate JVM processes via ProcessBuilder in run(). This method exists only to satisfy
   the abstract contract defined in SimulationLauncher.
 */
    @Override
    protected List<ZeroMQWA> setupComposition(Properties systemConfig) {
    	return new ArrayList<>();
    }
    
//Converts a bind address like "tcp://*:5556" to a connectable address "tcp://127.0.0.1:5556" so the composer can subscribe to model outputs.
    private String toConnectAddress(String bindAddress) {
        return bindAddress.replace("tcp://*:", "tcp://127.0.0.1:");
    }

    @Override
    public void run() throws Exception {
    	//load config and read compositional formula
        Properties systemConfig = loadUnifiedConfig(configPath);
        String formula = systemConfig.getProperty("setup.setup_comp");
        String[] modelNames = formula.replaceAll("[()\\s]", "").split("\\|\\|");
 
        // Collect PUB addresses of all models
        List<String> modelPubAddresses = new ArrayList<>();
        for (String modelName : modelNames) {
            String bindAddr = systemConfig.getProperty(modelName + ".ZMQ_PUB_SOCKET");
            if (bindAddr == null)
                throw new RuntimeException("Missing " + modelName + ".ZMQ_PUB_SOCKET in config");
            modelPubAddresses.add(toConnectAddress(bindAddr));
        }

        // Spawn one JVM process per model
        String classpath = ManagementFactory.getRuntimeMXBean().getClassPath();
        List<Process> processes = new ArrayList<>();
        for (String modelName : modelNames) {
            ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", classpath,
                "asmeta.asmeta_zeromq.registry.ModelProcess",
                configPath, modelName
            );
            pb.inheritIO();
            processes.add(pb.start());
        }
 
        Thread.sleep(5000);
        startEnvironment();
       
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();
        
        try (ZContext context = new ZContext()) {
            
        	//environment interface
            String envBindAddr = systemConfig.getProperty("environment.address", "tcp://*:5563");
            String envConnectAddr = toConnectAddress(envBindAddr);
            ZMQ.Socket envSub = context.createSocket(SocketType.SUB);
            envSub.connect(envConnectAddr);            
            String envFunctions = systemConfig.getProperty("environment.env_functions", "");
            if (envFunctions.isEmpty()) {
                envSub.subscribe("".getBytes(ZMQ.CHARSET));
            } else {
                for (String topic : envFunctions.split(",")) {
                    if (!topic.trim().isEmpty()) {
                        envSub.subscribe(topic.trim().getBytes(ZMQ.CHARSET));
                    }
                }
            }
            
            ZMQ.Socket orchPub = context.createSocket(SocketType.PUB);
            orchPub.bind("tcp://*:5556"); 

            List<ZMQ.Socket> modelSubs = new ArrayList<>();
            for (String addr : modelPubAddresses) {
                ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                sub.connect(addr);
                sub.subscribe("STATUS_UPDATE".getBytes(ZMQ.CHARSET));
                modelSubs.add(sub);
            }

            Thread.sleep(2000);
            
            
/*To ensure the formal validity of the first transition, we must ensure that the environment has provided at least the first data
  snapshot before the simulation's logical clock starts
*/            
            envSub.recvStr(); //Reads the Topic and ignores it e.g. "my input"
            String firstPayload = envSub.recvStr(); //Reads JSON payload e.g. "2"
            orchPub.sendMore("ORCH_CMD");
            orchPub.send("DATA:" + firstPayload);

            //sync barrier
            while (processes.stream().anyMatch(Process::isAlive)) {
                boolean stepUnsafe = false;


                for (int i = 0; i < modelSubs.size(); i++) {
                    modelSubs.get(i).recvStr(); 
                    String message = modelSubs.get(i).recvStr(); 
                    
                    try {
                        Map<String, String> output = gson.fromJson(message, mapType);
                        if (output != null && "UNSAFE".equals(output.get("asm_status"))) {
                            stepUnsafe = true;
                        }
                    } catch (Exception e) {}
                }

                String comandoDaInviare;

                if (stepUnsafe) {
                    comandoDaInviare = "CMD:ROLLBACK";
                } else {
                    String freshTopic = null;
                    String freshPayload = null;
                    String topic;
                    
                    //flushing
                    while ((topic = envSub.recvStr(ZMQ.DONTWAIT)) != null) {
                        freshTopic = topic;
                        freshPayload = envSub.recvStr();
                    }
                    
                    if (freshTopic == null) {
                        freshTopic = envSub.recvStr();
                        freshPayload = envSub.recvStr();
                    }
                    comandoDaInviare = "DATA:" + freshPayload;
                }

                orchPub.sendMore("ORCH_CMD"); //broadcast 
                orchPub.send(comandoDaInviare);
            }
        }
     
    }
    
    public static void main(String[] args) {
        try {
            String configPath = "configs/incDecMulti/zmq_config_IncDecForkJoin.properties";
            ForkJoinComposer launcher = new ForkJoinComposer(configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  


/* choreographed version
 package asmeta.asmeta_zeromq.registry;
 
import asmeta.asmeta_zeromq.ZeroMQWA;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.zeromq.ZContext;
 
public class ForkJoinComposer extends SimulationLauncher {
 
    public ForkJoinComposer(String configPath) {
        super(configPath);
    }
     
    @Override
    protected List<ZeroMQWA> setupComposition(Properties systemConfig) {
        // read the compositional formula from "setup.setup_comp" expected format: (M1 || M2)
        String compositionalFormula = systemConfig.getProperty("setup.setup_comp");
        // parse removing "()" and whitespace, split on "||" to get model names
        String[] orderedModels = compositionalFormula.replaceAll("[()\\s]", "").split("\\|\\|");
 
        List<ZeroMQWA> forkJoinModels = new ArrayList<>();
        for (String modelName : orderedModels) {
        	
            System.out.println("Init models in Fork-Join Composition: " + modelName);
            Properties modelSpecificConfig = extractSection(systemConfig, modelName);
            ZeroMQWA asmModel = new ZeroMQWA(modelSpecificConfig, modelName);
            forkJoinModels.add(asmModel);
        }
 
        return forkJoinModels;
    }
    
    @Override
    public void run() throws Exception {
        Properties systemConfig = loadUnifiedConfig(configPath);
        List<ZeroMQWA> modelsToRun = setupComposition(systemConfig);
        List<Thread> parallelStepThreads = new ArrayList<>();
        
        try (ZContext context = new ZContext()) {
            for (ZeroMQWA model : modelsToRun) {
                model.initializeSockets(context);
            }

            Thread.sleep(2000);
            startEnvironment();

            for (ZeroMQWA model : modelsToRun) {
                Thread modelThread = new Thread(() -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            Thread.sleep(100);
                            model.runStep();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                parallelStepThreads.add(modelThread);
                modelThread.start();
            }
            for (Thread modelThread  : parallelStepThreads) {
            	modelThread.join();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String configPath = "configs/incDecMulti/zmq_config_IncDecForkJoin.properties";
            ForkJoinComposer launcher = new ForkJoinComposer(configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  
   */
