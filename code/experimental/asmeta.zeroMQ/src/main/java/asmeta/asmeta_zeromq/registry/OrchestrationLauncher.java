package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.ast.ISimulationNode;
import asmeta.asmeta_zeromq.parser.CompositionParser;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class OrchestrationLauncher extends SimulationLauncher {

    public OrchestrationLauncher(String configPath) {
        super(configPath);
    }

    @Override
    protected List<ZeroMQWA> setupComposition(Properties systemConfig) throws Exception {
        return new ArrayList<>(); 
    }

    @Override
    public void run() throws Exception {
        Properties systemConfig = loadUnifiedConfig(configPath);
        String formula = systemConfig.getProperty("setup.setup_comp");
        
        CompositionParser parser = new CompositionParser();
        ISimulationNode astRoot = parser.parse(formula);

        List<String> modelsToSpawn = extractModelNames(astRoot);
        List<Process> activeProcesses = new ArrayList<>();

        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String mainClassName = "asmeta.asmeta_zeromq.registry.ModelProcess";


        for (String modelName : modelsToSpawn) {
            ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, mainClassName, 
                configPath, modelName, "ORCHESTRATED", "NONE", "NONE"
            );
            builder.inheritIO();
            activeProcesses.add(builder.start());
        }

        System.out.println("wait 4 seconds for the workers to start up.");
        Thread.sleep(4000);

        try (ZContext context = new ZContext()) {
           //sends commands to the models
            ZMQ.Socket orchPub = context.createSocket(org.zeromq.SocketType.PUB);
            orchPub.bind("tcp://*:5550"); 
            //receives responses from the models
            ZMQ.Socket orchSub = context.createSocket(org.zeromq.SocketType.SUB);
            orchSub.bind("tcp://*:5551"); 
            orchSub.subscribe("".getBytes(ZMQ.CHARSET)); 
            
            ZMQ.Socket envSub = context.createSocket(org.zeromq.SocketType.SUB);
            String envAddress = systemConfig.getProperty("environment.address", "tcp://*:5570")
                    .replace("*", "127.0.0.1");
            		envSub.connect(envAddress);
           // envSub.connect("tcp://127.0.0.1:5570"); 
            String envFuncs = systemConfig.getProperty("environment.env_functions", "systemTime");
            for (String func : envFuncs.split(",")) {
                envSub.subscribe(func.trim().getBytes(ZMQ.CHARSET));
            }


            ConcurrentHashMap<String, String> mailbox = new ConcurrentHashMap<>();
            Thread receiverThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    String topic = orchSub.recvStr(ZMQ.DONTWAIT);
                    if (topic != null) {
                        String payload = orchSub.recvStr();
                        mailbox.put(topic, payload);
                    } else {
                        try { Thread.sleep(5); } catch (InterruptedException e) { break; }
                    }
                }
            });
            receiverThread.start();

            OrchestrationVisitor visitor = new OrchestrationVisitor(orchPub, mailbox);


            startEnvironment();
            System.out.println("[Orchestrator] RESPONSIVENESS ACTIVATED. Awaiting input from the Environment...");

            int stepCorrente = 1;

            while (!Thread.currentThread().isInterrupted()) {

                String topic = envSub.recvStr();
                String payload = envSub.recvStr();

                try { Thread.sleep(50); } catch (InterruptedException e) {}

                List<String> envPayloads = new ArrayList<>();
                envPayloads.add(payload);
                while (true) {
                    String nextTopic = envSub.recvStr(ZMQ.DONTWAIT);
                    if (nextTopic == null) break;
                    String nextPayload = envSub.recvStr();
                    envPayloads.add(nextPayload);
                }

                System.out.println("\n--- GLOBAL STEP " + stepCorrente 
                                  + " (from env: " + topic 
                                  + ", " + envPayloads.size());
                
                List<String> envData = new ArrayList<>(envPayloads);

                try {
                    astRoot.accept(visitor, envData);
                    System.out.println("--- STEP " + stepCorrente + " OK ---");
                } catch (OrchestrationVisitor.UnsafeExecutionException e) {
                    System.err.println("[Orchestratore] ROLLBACK GLOBALE!");
                    synchronized (orchPub) {
                        orchPub.sendMore("CMD:ROLLBACK_ALL");
                        orchPub.send("RESET");
                    }
                    break; 
                }

                stepCorrente++;
            }
            receiverThread.interrupt();
        }
    }

    private List<String> extractModelNames(ISimulationNode node) {
        List<String> names = new ArrayList<>();
        if (node instanceof asmeta.asmeta_zeromq.ast.ModelNode) names.add(((asmeta.asmeta_zeromq.ast.ModelNode) node).getModelName());
        else if (node instanceof asmeta.asmeta_zeromq.ast.PipeNode) {
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.PipeNode) node).getLeftChild()));
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.PipeNode) node).getRightChild()));
        } else if (node instanceof asmeta.asmeta_zeromq.ast.ParallelNode) {
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.ParallelNode) node).getLeftChild()));
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.ParallelNode) node).getRightChild()));
        } else if (node instanceof asmeta.asmeta_zeromq.ast.HalfBiPipeNode) {
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.HalfBiPipeNode) node).getLeftChild()));
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.HalfBiPipeNode) node).getRightChild()));
        } else if (node instanceof asmeta.asmeta_zeromq.ast.FullBiPipeNode) {
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.FullBiPipeNode) node).getLeftChild()));
            names.addAll(extractModelNames(((asmeta.asmeta_zeromq.ast.FullBiPipeNode) node).getRightChild()));
        }
        return names;
    }


}