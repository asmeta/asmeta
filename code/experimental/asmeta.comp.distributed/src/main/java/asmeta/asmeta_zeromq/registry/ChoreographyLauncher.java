package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.ast.ISimulationNode;
import asmeta.asmeta_zeromq.parser.CompositionParser;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//Launcher for co-simulation in Choreographed mode. 
//Dynamically generates the AST, calculates the sockets and launches the ASMs in separate JVM processes

public class ChoreographyLauncher extends SimulationLauncher {

    public ChoreographyLauncher(String configPath) {
        super(configPath);
    }

    @Override
    protected List<ZeroMQWA> setupComposition(Properties systemConfig) throws Exception {
        return new ArrayList<>(); 
    }

    @Override
    public void run() throws Exception {
        Properties systemConfig = loadUnifiedConfig(configPath);
        long simulationStartNanos = System.nanoTime();

        // Reading the formula and parsing AST
        String compositionalFormula = systemConfig.getProperty("setup.setup_comp");
        if (compositionalFormula == null || compositionalFormula.isEmpty()) {
            throw new IllegalArgumentException("Missing composition formula.");
        }
        System.out.println("[ChoreographyLauncher] : Parsing the formula" + compositionalFormula);

        CompositionParser parser = new CompositionParser();
        //the reference to the root of tree
        ISimulationNode astRoot = parser.parse(compositionalFormula);

        //Wiring calculation via Visitor
        ChoreographyVisitor visitor = new ChoreographyVisitor();
        
        String envFuncs = systemConfig.getProperty("common.ASM_ENVIRONMENT_FUNCTIONS", "");
        List<String> envTopics = new ArrayList<>();
        if (!envFuncs.isEmpty()) {
            envTopics = List.of(envFuncs.split(","));
        } else {
            envTopics.add("env_output");
        }
        //Start the recursive traversal of the tree
        astRoot.accept(visitor, envTopics);
        //a map that associates each model name with the list of topics it must subscribe to, generates by Visitor
        Map<String, List<String>> subscriptionsMap = visitor.getModelSubscriptions();

        try (ZContext context = new ZContext()) {
            List<Process> activeProcesses = new ArrayList<>();
            List<ZMQ.Socket> readySockets = new ArrayList<>();
            List<String> readyModels = new ArrayList<>();
            ZMQ.Poller readyPoller = context.createPoller(subscriptionsMap.size());

            // Connect the launcher to every model publisher before starting
            // the workers. The publisher can bind later; ZeroMQ queues the
            // connection request until it becomes available.
            for (String modelName : subscriptionsMap.keySet()) {
                String pubAddress = systemConfig.getProperty(modelName + ".ZMQ_PUB_SOCKET");
                if (pubAddress == null || pubAddress.trim().isEmpty()) {
                    throw new IllegalArgumentException("Missing ZMQ_PUB_SOCKET for model " + modelName);
                }
                ZMQ.Socket readySocket = context.createSocket(ZMQ.SUB);
                readySocket.connect(pubAddress.trim().replace("*", "127.0.0.1"));
                readySocket.subscribe(("READY_" + modelName).getBytes(ZMQ.CHARSET));
                readyPoller.register(readySocket, ZMQ.Poller.POLLIN);
                readySockets.add(readySocket);
                readyModels.add(modelName);
            }

            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            String mainClassName = "asmeta.asmeta_zeromq.registry.ModelProcess";

            for (Map.Entry<String, List<String>> entry : subscriptionsMap.entrySet()) {
                String modelName = entry.getKey();
                List<String> requiredInputTopics = entry.getValue();

                String argMode = "CHOREOGRAPHED";
                String argPub = "OUT_" + modelName;
                // The worker parses calculated_sub_topics using ';'.
                String argSub = requiredInputTopics.isEmpty() ? "NONE" : String.join(";", requiredInputTopics);

                ProcessBuilder builder = new ProcessBuilder(
                    javaBin, "-cp", classpath, mainClassName,
                    configPath, modelName,
                    argMode, argPub, argSub
                );

                builder.inheritIO();
                activeProcesses.add(builder.start());
            }

            long handshakeTimeoutMs = Long.parseLong(
                    systemConfig.getProperty("setup.handshake_timeout_ms", "15000"));
            long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(handshakeTimeoutMs);
            Set<String> ready = new HashSet<>();
            while (ready.size() < readyModels.size()) {
                long remainingMs = TimeUnit.NANOSECONDS.toMillis(deadline - System.nanoTime());
                if (remainingMs <= 0) {
                    throw new IllegalStateException("Timeout waiting for choreographed worker READY messages: "
                            + (readyModels.size() - ready.size()) + " worker(s) missing");
                }
                int events = readyPoller.poll((int) Math.min(remainingMs, 1000L));
                if (events < 0) {
                    throw new IllegalStateException("READY poller interrupted");
                }
                for (int i = 0; i < readySockets.size(); i++) {
                    if (!readyPoller.pollin(i)) continue;
                    String topic = readySockets.get(i).recvStr();
                    String payload = readySockets.get(i).recvStr();
                    if (topic != null && payload != null) {
                        ready.add(readyModels.get(i));
                    }
                }
            }
            System.out.println("[ChoreographyLauncher] All workers READY: " + ready.size());

            Thread environmentThread = startEnvironment();
            System.out.println("[ChoreographyLauncher] Environment started");

            for (Process p : activeProcesses) {
                int exitCode = p.waitFor();
                if (exitCode != 0) {
                    throw new IllegalStateException("A choreographed model process terminated with exit code " + exitCode);
                }
            }
            environmentThread.join();
        }

        double totalMillis = (System.nanoTime() - simulationStartNanos) / 1_000_000.0;
        System.out.printf("[CHOREOGRAPHED] Tempo totale di esecuzione: %.3f ms%n", totalMillis);
    }

}
