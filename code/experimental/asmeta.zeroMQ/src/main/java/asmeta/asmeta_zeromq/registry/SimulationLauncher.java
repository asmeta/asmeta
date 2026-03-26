package asmeta.asmeta_zeromq.registry;
import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.common.environment;

import java.io.*;
import java.util.*;

public abstract class SimulationLauncher {
	protected String configPath;
	
	public SimulationLauncher(String configPath) {
		this.configPath = configPath;
	}
	
    public void run() throws Exception {
        // 1) load configuration
        Properties systemConfig = loadUnifiedConfig(configPath);  
        //ask to PipeComposer which models to start
        List<ZeroMQWA> modelsToRun = setupComposition(systemConfig);
 
        // 2) Starting Model threads separately
        List<Thread> threads = new ArrayList<>();
        for (ZeroMQWA zeroMqWrapper : modelsToRun) {          
                 	
            Thread modelThread = new Thread(() -> zeroMqWrapper.run(), "Thread-" + modelsToRun);            
            threads.add(modelThread);
            modelThread.start();
        }
        
        // 3) Pausa per connessione Modelli
            System.out.println(">>> Attendo 2 secondi per permettere registrazione e connessioni...");
            Thread.sleep(2000); 
      
        // 7) Avvio Environment
            startEnvironment();         
        System.out.println("Simulazione avviata correttamente.");
        for (Thread t : threads) t.join();
    }

    protected void startEnvironment() {
        Thread envThread = new Thread(() -> {
            try {
                environment.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Environment-Thread");
        
        envThread.start();
    }
    
    protected abstract List<ZeroMQWA> setupComposition(Properties systemConfig) throws Exception;
    
    protected Properties loadUnifiedConfig(String path) throws IOException {
        Properties globalConfig = new Properties();
        try (InputStream configFileStream = getClass().getClassLoader().getResourceAsStream(path)) {
            globalConfig.load(configFileStream);
        }
        return globalConfig;
    }

    protected Properties extractSection(Properties globalConfig, String modelName) {
        Properties out = new Properties();
        String modelPrefix = modelName + ".";
        for (String propertyKey : globalConfig.stringPropertyNames()) {
            if (propertyKey.startsWith(modelPrefix)) {
                out.put(propertyKey.substring(modelPrefix.length()), globalConfig.getProperty(propertyKey));
            } else if (propertyKey.startsWith("common.")) {
                out.put(propertyKey.substring(7), globalConfig.getProperty(propertyKey));
            }
        }
        return out;
    }
}