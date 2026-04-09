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
    
    