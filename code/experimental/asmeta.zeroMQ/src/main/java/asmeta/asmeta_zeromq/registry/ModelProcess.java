package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ModelProcess {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("[ModelProcess] Usage: ModelProcess <configPath> <modelName> [executionMode] [pubTopic] [subTopics]");
            System.exit(1);
        }

        String configPath = args[0]; // e.g.  "configs/incDecMulti/zmq_config_IncDecForkJoin.properties"
        String modelName  = args[1]; // e.g. "M1"
        
        System.out.println("[ModelProcess] Starting model: " + modelName + " from config: " + configPath);

        try {
            // Load unified config from classpath
            Properties globalConfig = new Properties();
            try (InputStream is = ModelProcess.class.getClassLoader().getResourceAsStream(configPath)) {
                if (is == null) {
                    throw new IOException("Config file not found on classpath: " + configPath);
                }
                globalConfig.load(is);
            }

            Properties modelConfig = SimulationLauncher.extractSection(globalConfig, modelName);  

            if (args.length >= 5) {
                String executionMode = args[2];
                String pubTopic = args[3];
                String subTopics = args[4].equals("NONE") ? "" : args[4];

                modelConfig.setProperty("execution_mode", executionMode);
                modelConfig.setProperty("calculated_pub_topic", pubTopic);
                modelConfig.setProperty("calculated_sub_topics", subTopics);
                
                System.out.println("[ModelProcess] Parametri AST ricevuti -> Mode: " + executionMode + 
                                   ", PUB: " + pubTopic + ", SUB: [" + subTopics + "]");
            }


            // Instantiate and run
            ZeroMQWA model = new ZeroMQWA(modelConfig, modelName);
            model.run();

        } catch (Exception e) {
            System.err.println("[ModelProcess] FATAL error in model " + modelName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}