package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.producerconsumer.environment;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimulationLauncher {

    private static final String CONFIG_PATH = "configs/producerconsumer/zmq_config_unified.properties";

    public static void main(String[] args) {
        try {
            new SimulationLauncher().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore fatale durante l'avvio della simulazione: " + e.getMessage());
        }
    }

    public void run() throws IOException, InterruptedException {
        // 1) Caricamento configurazione
        Properties unified = loadUnifiedConfig(CONFIG_PATH);
            
        PortManager portAllocator = new PortManager(unified); 

        Map<String, String> aliases = new HashMap<>(); 
        String leftAlias = "";
        String rightAlias = "";

        Pattern setupPattern = Pattern.compile("setup\\.(\\w+)=(.+)");
        Pattern formulaPattern = Pattern.compile("setup\\.setup_comp=\\((\\w+)\\|(\\w+)\\)");

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource(CONFIG_PATH).getPath())))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                Matcher formulaMatcher = formulaPattern.matcher(line);
                if (formulaMatcher.matches()) {
                    leftAlias = formulaMatcher.group(1);
                    rightAlias = formulaMatcher.group(2);
                    continue;
                }

                Matcher setupMatcher = setupPattern.matcher(line);
                if (setupMatcher.matches()) {
                    aliases.put(setupMatcher.group(1), setupMatcher.group(2).trim());
                }
            }
        }

        if (leftAlias.isEmpty() || rightAlias.isEmpty()) {
            throw new IllegalStateException("Formula di composizione non trovata o malformata.");
        }

        // 3) Configurazione delle Istanze
        Map<String, Properties> instanceConfigs = new HashMap<>();
        String[] pipeline = {leftAlias, rightAlias};
        String host = unified.getProperty("common.HOST", "127.0.0.1");

        for (String alias : pipeline) {
            Properties cfg = extractSection(unified, alias);
            
            // Setup parametri base
            cfg.setProperty("RUNTIME_MODEL_PATH", aliases.get(alias));
            
            // Allocazione Porta 
            String pubPort = portAllocator.allocatePortFor(alias);
            cfg.setProperty("ZMQ_PUB_SOCKET", "tcp://*:" + pubPort);
            
            // Configurazione indirizzo Environment
            cfg.setProperty("ASM_ENVIRONMENT_ADDRESS", "tcp://" + host + ":5563");
            
            instanceConfigs.put(alias, cfg);
        }

        // 4) CABLAGGIO PIPE (M1 -> M2)
        Properties leftProps = instanceConfigs.get(leftAlias);
        Properties rightProps = instanceConfigs.get(rightAlias);

        String leftBindAddress = leftProps.getProperty("ZMQ_PUB_SOCKET");
        String leftEndpoint = leftBindAddress.replace("*", host);
        
        String existingSubs = rightProps.getProperty("ZMQ_SUB_CONNECT_ADDRESSES", "");
        if (existingSubs.isEmpty()) {
            rightProps.setProperty("ZMQ_SUB_CONNECT_ADDRESSES", leftEndpoint);
        } else {
            rightProps.setProperty("ZMQ_SUB_CONNECT_ADDRESSES", existingSubs + "," + leftEndpoint);
        }
        
        System.out.println("[PIPE] M2 si sottoscriverà a M1 su: " + leftEndpoint);

        // 5) Avvio dei thread dei Modelli
        List<Thread> threads = new ArrayList<>();
        for (String alias : pipeline) {
            Properties finalCfg = instanceConfigs.get(alias);
            
            System.out.println("Inizializzazione modello " + alias + "...");
            ZeroMQWA wa = new ZeroMQWA(finalCfg, alias); 
            
            Thread t = new Thread(() -> {
                try {
                    wa.run(); 
                } catch (Exception e) {
                    System.err.println("Errore nel thread " + alias + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }, "Thread-" + alias);
            
            threads.add(t);
            t.start();
        }
        
        // 6) Pausa per connessione Modelli
        try {
            System.out.println(">>> Attendo 2 secondi per permettere registrazione e connessioni...");
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 7) Avvio Environment
        new Thread(() -> {
            try {
                environment.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Environment-Thread").start();

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