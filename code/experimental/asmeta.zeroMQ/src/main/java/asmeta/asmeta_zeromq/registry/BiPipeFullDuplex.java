package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.common.environment;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BiPipeFullDuplex extends SimulationLauncher {
	
	public BiPipeFullDuplex(String configPath) {
		super(configPath);
	}
	
	@Override
	
	protected List<ZeroMQWA> setupComposition(Properties systemConfig) {
		//read the compositionalFormula from "setup.setup_comp" in zmq_config_Pipe.properties
		String compositionalFormula = systemConfig.getProperty("setup.setup_comp");    
		//parsing removing "()" and \s, split the formula from "|" , we obtain an ordered array ["M1","M2"]
		String[] orderedModels = compositionalFormula.replaceAll("[()\\s]", "").split("<\\|\\|>");
       
	
        List<ZeroMQWA> fullDuplexModels  = new ArrayList<>();
        for (String modelName : orderedModels) {
            System.out.println("Init modello in BiPipe Composition FULL Duplex: " + modelName);
            Properties modelSpecificConfig = extractSection(systemConfig, modelName);
       
            ZeroMQWA asmModel = new ZeroMQWA(modelSpecificConfig, modelName);
            fullDuplexModels .add(asmModel);
        }
        return fullDuplexModels ;
        
	}
	
	 private String toConnectAddress(String bindAddress) {
	        return bindAddress.replace("tcp://*:", "tcp://127.0.0.1:");
	    }
	
	
	 @Override
	    public void run() throws Exception {
	        Properties systemConfig = loadUnifiedConfig(configPath);

	        String formula = systemConfig.getProperty("setup.setup_comp");
	        String[] modelNames = formula.replaceAll("[()\\s]", "").split("<\\|\\|>");

	        // Collect PUB addresses of all models
	        List<String> modelPubAddresses = new ArrayList<>();
	        for (String modelName : modelNames) {
	            String bindAddr = systemConfig.getProperty(modelName + ".ZMQ_PUB_SOCKET");
	            if (bindAddr == null)
	                throw new RuntimeException("Missing " + modelName + ".ZMQ_PUB_SOCKET in config");
	            modelPubAddresses.add(toConnectAddress(bindAddr));
	        }

	        // Barrier signals shared with environment
	        Semaphore stepBarrier = new Semaphore(0);
	        AtomicBoolean retrySignal = new AtomicBoolean(false);
	        
	        // Spawn one JVM process per model
	        String classpath = ManagementFactory.getRuntimeMXBean().getClassPath();
	        List<Process> processes = new ArrayList<>();
	        for (String modelName : modelNames) {
	            ProcessBuilder pb = new ProcessBuilder(
	                "java", "-cp", classpath,
	                "asmeta.asmeta_zeromq.registry.ModelProcess",
	                configPath, modelName, "true"
	            );
	            pb.inheritIO();
	            processes.add(pb.start());
	        }

	        Thread.sleep(2000);
	        startEnvironment();

	        // Barrier loop
	        Gson gson = new Gson();
	        Type mapType = new TypeToken<Map<String, String>>() {}.getType();

	        try (ZContext context = new ZContext()) {
	            List<ZMQ.Socket> modelSubs = new ArrayList<>();
	            for (String addr : modelPubAddresses) {
	                ZMQ.Socket sub = context.createSocket(SocketType.SUB);
	                sub.connect(addr);
	                sub.subscribe("".getBytes(ZMQ.CHARSET));
	                modelSubs.add(sub);
	                System.out.println("[BiPipeFullDuplex] Subscribed to model output at " + addr);
	            }

	            while (processes.stream().anyMatch(Process::isAlive)) {
	                boolean stepUnsafe = false;

	                // Attendi un output da ciascun modello — entrambi girano in parallelo
	                for (int i = 0; i < modelSubs.size(); i++) {
	                    String message = modelSubs.get(i).recvStr();
	                    try {
	                        Map<String, String> output = gson.fromJson(message, mapType);
	                        
	                     // <--- AGGIUNGI QUESTO IF: Se è un re-seed, ignoralo e aspetta il vero output
	                        if (output != null && "true".equals(output.get("is_reseed"))) {
	                            i--; // Decrementa l'indice per continuare ad aspettare il messaggio reale per questo modello
	                            continue; 
	                        }
	                        
	                        if (output != null && "UNSAFE".equals(output.get("asm_status"))) {
	                            stepUnsafe = true;
	                            System.out.println("[BiPipeFullDuplex] Model " 
	                                + modelNames[i] + " returned UNSAFE.");
	                        }
	                    } catch (Exception e) {
	                        System.err.println("[BiPipeFullDuplex] Failed to parse output from " 
	                            + modelNames[i]);
	                    }
	                }

	                if (stepUnsafe) {
	                    System.out.println("[BiPipeFullDuplex] UNSAFE step — signaling rollback.");
	                    retrySignal.set(true);
	                } else {
	                    System.out.println("[BiPipeFullDuplex] All models SAFE — advancing.");
	                }

	                // Rilascia barrier in ogni caso
	                stepBarrier.release();
	            }
	        }

	        for (Process p : processes) p.waitFor();
	    }
	    	
	public static void main(String[] args) {
        try {
        	String configPath = "configs/incDecMulti/zmq_config_IncDecBiPipeFullDuplex.properties";
        	BiPipeFullDuplex  launcher = new BiPipeFullDuplex (configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}