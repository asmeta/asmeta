package asmeta.asmeta_zeromq.registry;
import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.producerconsumer.environment;

import java.io.*;
import java.util.*;

public class SimulationLauncher {

    private static final String CONFIG_PATH = "configs/producerconsumer/zmq_config_unified.properties";
    public  static void main(String[] args) throws Exception {
            new SimulationLauncher().run();    
    }

    public void run() throws IOException, InterruptedException {
        // 1) Caricamento configurazione
        Properties systemConfig = loadUnifiedConfig(CONFIG_PATH);            
 
     // 2) Traduzione della formula        
        String compositionalFormula = systemConfig.getProperty("setup.setup_comp");
        if (compositionalFormula == null || !compositionalFormula.contains("|")) {
            throw new IllegalStateException("Formula setup_comp mancante o malformata.");
        }

        String[] orderedModels = compositionalFormula.replaceAll("[()\\s]", "").split("\\|");
        String host = systemConfig.getProperty("common.HOST", "127.0.0.1");

        // 5) Avvio dei thread dei Modelli
        List<Thread> threads = new ArrayList<>();
        for (String modelName : orderedModels) {          
            System.out.println("Inizializzazione e avvio modello " + modelName + "...");
            Properties modelSpecificConfig = extractSection(systemConfig, modelName);
            modelSpecificConfig.setProperty("RUNTIME_MODEL_PATH", systemConfig.getProperty("setup." + modelName));
            modelSpecificConfig.setProperty("ASM_ENVIRONMENT_ADDRESS", "tcp://" + host + ":5563");
            
            ZeroMQWA zeroMqWrapper = new ZeroMQWA(modelSpecificConfig, modelName);            
            Thread modelThread = new Thread(() -> zeroMqWrapper.run(), "Thread-" + modelName);            
            threads.add(modelThread);
            modelThread.start();
        }
        
        // 6) Pausa per connessione Modelli
            System.out.println(">>> Attendo 2 secondi per permettere registrazione e connessioni...");
            Thread.sleep(2000); 
      
        // 7) Avvio Environment
            Thread envThread =  new Thread(() -> {
            try {
                environment.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Environment-Thread");
            
            threads.add(envThread);
            envThread.start();
        System.out.println("Simulazione avviata correttamente.");
        for (Thread t : threads) t.join();

    }

    private Properties loadUnifiedConfig(String path) throws IOException {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IOException("File di configurazione non trovato: " + path);
            props.load(is);
        }
        return props;
    }

    private Properties extractSection(Properties unified, String sectionName) {
        Properties out = new Properties();
        String prefix = sectionName + ".";
        for (String k : unified.stringPropertyNames()) {
            if (k.startsWith(prefix)) {
                out.put(k.substring(prefix.length()), unified.getProperty(k));
            } else if (k.startsWith("common.")) {
                out.put(k.substring(7), unified.getProperty(k));
            }
        }
        return out;
    }
}