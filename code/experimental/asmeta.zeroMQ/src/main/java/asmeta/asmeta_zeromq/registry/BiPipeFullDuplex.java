package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.zeromq.ZContext;

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
	
	 @Override
	    public void run() throws Exception {
	        Properties systemConfig = loadUnifiedConfig(configPath);
	        List<ZeroMQWA> modelsToRun = setupComposition(systemConfig);
	 
	        try (ZContext context = new ZContext()) {	           
	            for (ZeroMQWA model : modelsToRun) {
	                model.initializeSockets(context);
	            }
	 
	            System.out.println(">>> Attendo 2 secondi per permettere registrazione e connessioni...");
	            Thread.sleep(2000);
	            startEnvironment();
	            System.out.println("Simulazione avviata correttamente.");
	 
	            while (!Thread.currentThread().isInterrupted()) {
	            	//internal counter
	            CountDownLatch latch = new CountDownLatch(modelsToRun.size());
	 
	                for (ZeroMQWA model : modelsToRun) {	                   
	                    Thread modelThread = new Thread(() -> {
	                        try {
	                        	boolean success = false;
	                            while (!success) {
	                                Thread.sleep(100);
	                                success = model.runStep();
	                            }
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        } finally {	                        	
	                        	latch.countDown();
	                        }
	                    });	                 
	                    modelThread.start();
	                }
	                latch.await();
	                }
	           
	            }
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