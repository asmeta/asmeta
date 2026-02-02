package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.producerconsumer.environment;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Launcher for the producer–consumer simulation based on a compositional formula
 * defined in the unified configuration (zmq_config_unified.properties).
 *
 * It:
 *  - starts the PortManager (registry of ports),
 *  - parses models and composition (e.g. m1 | m2),
 *  - builds per-model Properties (common + model.*),
 *  - applies PIPE semantics to set SUB_ROLES,
 *  - starts environment, producer and consumer.
 */
public class SimulationLauncher {

    private static final String UNIFIED_CONFIG_PATH = "configs/producerconsumer/zmq_config_unified.properties";

    public static void main(String[] args) {
        try {
            new SimulationLauncher().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Simulation failed: " + e.getMessage());
        }
    }

    public void run() throws IOException, InterruptedException {
    	// 1) Load the unified file
        Properties unified = loadUnifiedConfig(UNIFIED_CONFIG_PATH);

        // 2) Read available models
        List<String> models = parseCsv(unified.getProperty("models", ""));

     // 3) Read instance -> model mapping (m1=producer, m2=consumer, ...)
        Map<String, String> instanceToModel =
                parseInstanceMapping(unified.getProperty("composition.instances", ""));

     // 4) Read formula and operator
        String operator = unified.getProperty("composition.operator", "PIPE").trim();
        String formula  = unified.getProperty("composition.formula", "").trim();
        if (formula.isEmpty()) {
            throw new IllegalStateException("composition.formula is empty");
        }

     // Instances in the formula: [m1, m2]
        List<String> instancesInFormula = Arrays.stream(formula.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

     // Map to model names: [producer, consumer]
        List<String> pipelineModels = instancesInFormula.stream()
                .map(inst -> {
                    String model = instanceToModel.get(inst);
                    if (model == null) {
                        throw new IllegalStateException("No model mapped for instance: " + inst);
                    }
                    return model;
                })
                .collect(Collectors.toList());

     // 5) Build Properties for each model (common + model.*)
        Map<String, Properties> modelConfigs = new HashMap<>();
        for (String model : models) {
            Properties cfg = extractSection(unified, model);
            modelConfigs.put(model, cfg);
        }

        // 6) Applica l'operatore PIPE: ogni modello di destra si sottoscrive a quello di sinistra
        if ("PIPE".equalsIgnoreCase(operator)) {
            applyPipeComposition(pipelineModels, modelConfigs);
        } else {
            throw new UnsupportedOperationException("Unsupported composition operator: " + operator);
        }

        // 7) Avvia PortManager in un thread separato
        startPortManager();

        // breve pausa per permettere al registry di bindare la socket
        Thread.sleep(500);

        // 8) Avvia l'environment (usa ancora il suo main, che legge la sezione environment.*)
        Thread envThread = new Thread(() -> environment.main(new String[0]), "environment-thread");
        envThread.start();
        System.out.println("Started environment");

        // 9) Avvia i modelli ZeroMQWA (producer, consumer) in base alla composizione
        List<Thread> modelThreads = new ArrayList<>();
        for (String modelName : pipelineModels) {
            if ("environment".equalsIgnoreCase(modelName)) {
                // l'environment lo abbiamo già avviato separatamente
                continue;
            }
            Properties cfg = modelConfigs.get(modelName);
            ZeroMQWA instance = new ZeroMQWA(cfg, modelName);
            Thread t = new Thread(instance::run, modelName + "-thread");
            t.start();
            modelThreads.add(t);
            System.out.println("Started model: " + modelName);
        }

        // opzionale: se vuoi che il launcher aspetti la fine dei modelli
        /*
        for (Thread t : modelThreads) {
            t.join();
        }
        envThread.join();
        */
    }

   
    // Utility methods
    
    private Properties loadUnifiedConfig(String classpathPath) throws IOException {
        Properties props = new Properties();
        try (InputStream is = SimulationLauncher.class.getClassLoader()
                .getResourceAsStream(classpathPath)) {
            if (is == null) {
                throw new IOException("Unified config not found on classpath: " + classpathPath);
            }
            props.load(is);
        }
        return props;
    }

    private List<String> parseCsv(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(csv.split("\\s*,\\s*"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Parsing di "m1=producer,m2=consumer" -> {m1=producer, m2=consumer}
     */
    private Map<String, String> parseInstanceMapping(String mappingRaw) {
        Map<String, String> map = new HashMap<>();
        if (mappingRaw == null || mappingRaw.trim().isEmpty()) {
            return map;
        }
        String[] pairs = mappingRaw.split("\\s*,\\s*");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();
                if (!key.isEmpty() && !value.isEmpty()) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * Estrae una sezione (es. "producer") dal file unificato, includendo anche le chiavi common.*
     *  - common.HOST -> HOST
     *  - producer.RUNTIME_MODEL_PATH -> RUNTIME_MODEL_PATH
     */
    private Properties extractSection(Properties all, String sectionPrefix) {
        Properties out = new Properties();
        String sec = sectionPrefix + ".";
        String common = "common.";

        for (String k : all.stringPropertyNames()) {
            String v = all.getProperty(k);
            if (k.startsWith(sec)) {
                out.put(k.substring(sec.length()), v);
            } else if (k.startsWith(common)) {
                out.put(k.substring(common.length()), v);
            }
        }
        return out;
    }

    /**
     * PIPE: per ogni coppia consecutiva (left -> right) nella pipeline,
     * il modello di destra si sottoscrive a quello di sinistra.
     *
     * Esempio: [producer, consumer]
     *   consumer.SUB_ROLES = producer
     */
    private void applyPipeComposition(List<String> pipelineModels,
                                      Map<String, Properties> modelConfigs) {
        for (int i = 0; i < pipelineModels.size() - 1; i++) {
            String leftModel  = pipelineModels.get(i);
            String rightModel = pipelineModels.get(i + 1);

            Properties rightCfg = modelConfigs.get(rightModel);
            if (rightCfg == null) {
                throw new IllegalStateException("Missing config for model: " + rightModel);
            }

            String existingSubs = rightCfg.getProperty("SUB_ROLES", "").trim();
            if (existingSubs.isEmpty()) {
                rightCfg.setProperty("SUB_ROLES", leftModel);
            } else {
                rightCfg.setProperty("SUB_ROLES", existingSubs + "," + leftModel);
            }
        }
    }

    /**
     * Avvia PortManager in un thread separato usando la sezione common.* del file unificato.
     */
    private void startPortManager() {
        Properties commonProps = PortManager.loadCommon(UNIFIED_CONFIG_PATH);
        PortManager pm = new PortManager(commonProps);
        Thread t = new Thread(pm::run, "PortManager-thread");
        t.setDaemon(true); // opzionale: il registry non blocca la chiusura dell'app
        t.start();
        System.out.println("Started PortManager on " + commonProps.getProperty("REGISTRY_ADDR"));
    }
}
