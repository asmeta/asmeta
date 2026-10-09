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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
            envSub.subscribe("ENV_END".getBytes(ZMQ.CHARSET));
            ZMQ.Poller environmentPoller = context.createPoller(1);
            environmentPoller.register(envSub, ZMQ.Poller.POLLIN);
            long environmentReceiveTimeoutMs = Long.parseLong(
                    systemConfig.getProperty("setup.environment_receive_timeout_ms", "1000"));


            ConcurrentHashMap<String, BlockingQueue<String>> mailbox = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, String> bootstrapState = new ConcurrentHashMap<>();
            CountDownLatch readyLatch = new CountDownLatch(modelsToSpawn.size());
            long receiverPollTimeoutMs = Long.parseLong(
                    systemConfig.getProperty("setup.receiver_poll_timeout_ms", "1000"));
            Thread receiverThread = new Thread(() -> {
                ZMQ.Poller receiverPoller = context.createPoller(1);
                receiverPoller.register(orchSub, ZMQ.Poller.POLLIN);
                while (!Thread.currentThread().isInterrupted()) {
                    int events = receiverPoller.poll((int) receiverPollTimeoutMs);
                    if (events < 0) break;
                    if (receiverPoller.pollin(0)) {
                        String topic = orchSub.recvStr();
                        String payload = orchSub.recvStr();
                        if (topic != null && payload != null) {
                            if (topic.startsWith("READY_")) {
                                if (bootstrapState.putIfAbsent(topic, payload) == null) {
                                    readyLatch.countDown();
                                }
                            } else {
                                mailbox.computeIfAbsent(topic,
                                        key -> new LinkedBlockingQueue<>()).offer(payload);
                            }
                        }
                    }
                }
            });
          
            
            receiverThread.start();

            long handshakeTimeoutMs = Long.parseLong(
                    systemConfig.getProperty("setup.handshake_timeout_ms", "15000"));
            long handshakeDeadline = System.nanoTime()
                    + TimeUnit.MILLISECONDS.toNanos(handshakeTimeoutMs);
            boolean allWorkersReady = false;
            while (!(allWorkersReady = readyLatch.getCount() == 0)) {
                long remainingMs = TimeUnit.NANOSECONDS.toMillis(
                        handshakeDeadline - System.nanoTime());
                if (remainingMs <= 0) break;
                synchronized (orchPub) {
                    orchPub.sendMore("CMD:READY");
                    orchPub.send("READY");
                }
                // A timed latch wait is a condition wait, not a coordination sleep.
                readyLatch.await(Math.min(remainingMs, 250L), TimeUnit.MILLISECONDS);
            }
            allWorkersReady = allWorkersReady || readyLatch.getCount() == 0;
            if (!allWorkersReady) {
                throw new IllegalStateException("Timeout waiting for worker READY messages: "
                        + readyLatch.getCount() + " worker(s) missing");
            }
            System.out.println("[Orchestrator] Handshake completed: " + modelsToSpawn.size()
                             + "/" + modelsToSpawn.size());

            long stepResponseTimeoutMs = Long.parseLong(
                    systemConfig.getProperty("setup.step_response_timeout_ms", "30000"));
            OrchestrationVisitor visitor = new OrchestrationVisitor(
                    orchPub, mailbox, bootstrapState, stepResponseTimeoutMs);


            startEnvironment();
            System.out.println("[Orchestrator] RESPONSIVENESS ACTIVATED. Awaiting input from the Environment...");

            int stepCorrente = 1;
            boolean endReceived = false;
            long simulationStartNanos = System.nanoTime();

            while (!Thread.currentThread().isInterrupted()) {

                int environmentEvents = environmentPoller.poll((int) environmentReceiveTimeoutMs);
                if (environmentEvents < 0) {
                    break;
                }
                if (!environmentPoller.pollin(0)) {
                    continue;
                }
                String topic = envSub.recvStr();
                String payload = envSub.recvStr();
                if (payload == null) {
                    continue;
                }

                if ("ENV_END".equals(topic)) {
                    endReceived = true;
                    break;
                }

                List<String> envPayloads = new ArrayList<>();
                envPayloads.add(payload);
                while (environmentPoller.poll(0) > 0 && environmentPoller.pollin(0)) {
                    String nextTopic = envSub.recvStr();
                    String nextPayload = envSub.recvStr();
                    if (nextTopic != null && nextPayload != null) {
                        envPayloads.add(nextPayload);
                    }
                }
                
                List<String> envData = new ArrayList<>(envPayloads);

                visitor.resetStepTracking();
                long stepStartNanos = System.nanoTime();
                try {
                    astRoot.accept(visitor, envData);
                } catch (OrchestrationVisitor.UnsafeExecutionException
                       | java.util.concurrent.CompletionException e) {

                    java.util.List<String> toRollback = visitor.getCommittedThisStep();
                    System.err.println("[Orchestrator] STEP " + stepCorrente
                                     + " UNSAFE -> targeted rollback of: " + toRollback);

                    synchronized (orchPub) {
                        for (String model : toRollback) {
                            orchPub.sendMore("CMD_" + model);
                            orchPub.send("{\"cmd\":\"ROLLBACK\"}");
                        }
                    }
                } finally {
                    double stepMillis = (System.nanoTime() - stepStartNanos) / 1_000_000.0;
                    System.out.printf("[ORCHESTRATED] Step %d: %.3f ms%n", stepCorrente, stepMillis);
                }
                stepCorrente++;
            }

            if (endReceived) {
                // Stop all workers after the environment has published the
                // final ENV_END notification.
                synchronized (orchPub) {
                    orchPub.sendMore("CMD:END");
                    orchPub.send("END");
                }
                for (Process p : activeProcesses) {
                    p.waitFor();
                }
                double totalMillis = (System.nanoTime() - simulationStartNanos) / 1_000_000.0;
                System.out.printf("[ORCHESTRATED] Tempo totale di esecuzione: %.3f ms%n", totalMillis);
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

    private int countModelsWithInitialOutputs(Properties systemConfig, List<String> modelNames) {
        int count = 0;
        for (String model : modelNames) {
            String key = model + ".INITIAL_OUTPUTS";
            String val = systemConfig.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                count++;
            }
        }
        return count;
    }


}
