package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PipeComposer extends SimulationLauncher {
	
	public PipeComposer(String configPath) {
		super(configPath);
	}
	
	@Override
	
	protected List<ZeroMQWA> setupComposition(Properties systemConfig) {
		//read the compositionalFormula from "setup.setup_comp" in zmq_config_Pipe.properties
		String compositionalFormula = systemConfig.getProperty("setup.setup_comp");    
		//parsing removing "()" and \s, split the formula from "|" , we obtain an ordered array ["M1","M2"]
        String[] orderedModels = compositionalFormula.replaceAll("[()\\s]", "").split("\\|");
        String host = systemConfig.getProperty("common.HOST", "127.0.0.1");
	
        List<ZeroMQWA> pipelineModels = new ArrayList<>();
        for (String modelName : orderedModels) {
            System.out.println("Inizializzazione modello in Pipe Composition: " + modelName + "...");
            Properties modelSpecificConfig = extractSection(systemConfig, modelName);
            modelSpecificConfig.setProperty("RUNTIME_MODEL_PATH", systemConfig.getProperty("setup." + modelName));
            modelSpecificConfig.setProperty("ASM_ENVIRONMENT_ADDRESS", "tcp://" + host + ":5563");

            ZeroMQWA asmModel = new ZeroMQWA(modelSpecificConfig, modelName);
            pipelineModels.add(asmModel);
        }

        return pipelineModels;
        
	}
	
	public static void main(String[] args) {
        try {

          //String configPath = "configs/producerconsumer/zmq_config_Pipe.properties";
        	String configPath = "digitalTwinExample/zmq_config_DigitalTwinPIPE.properties";
            PipeComposer launcher = new PipeComposer(configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}