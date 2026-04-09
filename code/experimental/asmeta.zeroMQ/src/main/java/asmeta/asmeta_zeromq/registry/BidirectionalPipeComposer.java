package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.zeromq.ZContext;

public class BidirectionalPipeComposer extends SimulationLauncher {
	
	public BidirectionalPipeComposer(String configPath) {
		super(configPath);
	}
	
	@Override
	
	protected List<ZeroMQWA> setupComposition(Properties systemConfig) {
		//read the compositionalFormula from "setup.setup_comp" in zmq_config_Pipe.properties
		String compositionalFormula = systemConfig.getProperty("setup.setup_comp");    
		//parsing removing "()" and \s, split the formula from "|" , we obtain an ordered array ["M1","M2"]
		String[] orderedModels = compositionalFormula.replaceAll("[()<>\\s]", "").split("\\|");
       
	
        List<ZeroMQWA> pipelineModels = new ArrayList<>();
        for (String modelName : orderedModels) {
            System.out.println("Init modello in BiPipe Composition Half Duplex: " + modelName);
            Properties modelSpecificConfig = extractSection(systemConfig, modelName);
       

            ZeroMQWA asmModel = new ZeroMQWA(modelSpecificConfig, modelName);
            pipelineModels.add(asmModel);
        }

        return pipelineModels;
        
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
	            for (ZeroMQWA model : modelsToRun) {
	                boolean success = false;
	                while (!success) {
	                    Thread.sleep(100);
	                    success = model.runStep();
	                }
	            }
	        }
	    }
	}
	

	public static void main(String[] args) {
        try {

          //String configPath = "configs/producerconsumer/zmq_config_Pipe.properties";
        	//String configPath = "configs/incDecMulti/zmq_config_IncDecBiPipe.properties";
        	String configPath = "digitalTwinExample/zmq_config_DigitalTwinBidirectionalPipe.properties";
        	BidirectionalPipeComposer  launcher = new BidirectionalPipeComposer (configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}