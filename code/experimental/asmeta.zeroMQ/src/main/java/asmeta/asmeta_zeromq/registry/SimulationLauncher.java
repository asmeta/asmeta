package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.common.environment;

import java.io.*;
import java.util.*;

public abstract class SimulationLauncher {

    public enum ExecutionMode {
        CHOREOGRAPHED,
        ORCHESTRATED;

        public static ExecutionMode parse(String s) {
            if (s == null) {
                throw new IllegalArgumentException("Execution mode is null");
            }
            return ExecutionMode.valueOf(s.trim().toUpperCase());
        }
    }

    protected String configPath;

    public SimulationLauncher(String configPath) {
        this.configPath = configPath;
    }

    public static SimulationLauncher create(String configPath, ExecutionMode mode) {
        switch (mode) {
            case CHOREOGRAPHED:
                return new ChoreographyLauncher(configPath);
            case ORCHESTRATED:
                return new OrchestrationLauncher(configPath);
            default:
                throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println(
                "Usage: java " + SimulationLauncher.class.getName()
                + " <configPath> <CHOREOGRAPHED|ORCHESTRATED>");
            System.err.println(
                "Example: java " + SimulationLauncher.class.getName()
                + " configs/MRM/zmq_config_MRM.properties ORCHESTRATED");
            System.exit(1);
        }

        String configPath = args[0];
        try {
            ExecutionMode mode = ExecutionMode.parse(args[1]);
            System.out.println("[SimulationLauncher] config = " + configPath);
            System.out.println("[SimulationLauncher] mode   = " + mode);
            SimulationLauncher.create(configPath, mode).run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }


    public void run() throws Exception {
        Properties systemConfig = loadUnifiedConfig(configPath);
        List<ZeroMQWA> modelsToRun = setupComposition(systemConfig);

        List<Thread> threads = new ArrayList<>();
        for (ZeroMQWA zeroMqWrapper : modelsToRun) {
            Thread modelThread = new Thread(() -> zeroMqWrapper.run());
            threads.add(modelThread);
            modelThread.start();
        }

        System.out.println(" wait 2 seconds to allow for registration and connections");
        Thread.sleep(2000);

        startEnvironment();
        System.out.println("Simulation started successfully.");
        for (Thread t : threads) t.join();
    }

    protected void startEnvironment() {
        Thread envThread = new Thread(() -> {
            try {
                environment.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        envThread.start();
    }

    protected abstract List<ZeroMQWA> setupComposition(Properties systemConfig) throws Exception;

    protected Properties loadUnifiedConfig(String configPath) throws IOException {
        Properties globalConfig = new Properties();
        try (InputStream configFileStream = getClass().getClassLoader().getResourceAsStream(configPath)) {
            globalConfig.load(configFileStream);
        }
        return globalConfig;
    }

    public static Properties extractSection(Properties globalConfig, String modelName) {
        Properties modelProperties = new Properties();
        String modelPrefix = modelName + ".";
        for (String propertyKey : globalConfig.stringPropertyNames()) {
            if (propertyKey.startsWith(modelPrefix)) {
                modelProperties.put(propertyKey.substring(modelPrefix.length()), globalConfig.getProperty(propertyKey));
            } else if (propertyKey.startsWith("common.")) {
                modelProperties.put(propertyKey.substring(7), globalConfig.getProperty(propertyKey));
            }
        }
        return modelProperties;
    }
}



