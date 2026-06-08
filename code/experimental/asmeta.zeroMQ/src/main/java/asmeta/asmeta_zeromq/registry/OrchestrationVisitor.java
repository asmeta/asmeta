package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ast.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.zeromq.ZMQ;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class OrchestrationVisitor implements ISimulationVisitor {

    private final ZMQ.Socket orchPub; 
    private final ConcurrentHashMap<String, String> mailbox;
    private final Gson gson = new Gson();
    private final java.util.IdentityHashMap<HalfBiPipeNode, List<String>> lastRightOutputByNode = new java.util.IdentityHashMap<>();

    public static class UnsafeExecutionException extends RuntimeException {
        public UnsafeExecutionException(String message) { super(message); }
    }

    public OrchestrationVisitor(ZMQ.Socket orchPub, ConcurrentHashMap<String, String> mailbox) {
        this.orchPub = orchPub;
        this.mailbox = mailbox;
    }

    @Override
    public List<String> visit(ModelNode node, List<String> inputTopics) {
        String modelName = node.getModelName();
        System.out.println("[Orchestrator] Request for instructions on: " + modelName);

        Map<String, Object> command = new HashMap<>();
        command.put("cmd", "STEP");
        command.put("data", inputTopics); 

        synchronized (orchPub) {
            orchPub.sendMore("CMD_" + modelName);
            orchPub.send(gson.toJson(command));
        }

        String expectedTopic = "STATUS_" + modelName;

        while (!Thread.currentThread().isInterrupted()) {
            if (mailbox.containsKey(expectedTopic)) {
                String payload = mailbox.remove(expectedTopic);

                Map<String, Object> response = gson.fromJson(payload, new TypeToken<Map<String, Object>>(){}.getType());

                if ("UNSAFE".equals(response.get("asm_status"))) {
                    System.err.println("[Orchestrator] Model " + modelName + " failed (UNSAFE)!");
                    throw new UnsafeExecutionException(modelName); 
                }

                System.out.println("[Orchestratore]  Model " + modelName + "has finished  (SAFE).");
                
                List<String> outputData = new ArrayList<>();
                outputData.add(gson.toJson(response.get("out_data")));
                return outputData;
            }
            try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> visit(PipeNode node, List<String> inputTopics) {
        List<String> leftOutput = node.getLeftChild().accept(this, inputTopics);
        return node.getRightChild().accept(this, leftOutput);
    }

    @Override
    public List<String> visit(ParallelNode node, List<String> inputTopics) {
        System.out.println("[Orchestrator] Start Fork-Join");
        
        CompletableFuture<List<String>> leftFuture = CompletableFuture.supplyAsync(() -> 
            node.getLeftChild().accept(this, inputTopics)
        );
        CompletableFuture<List<String>> rightFuture = CompletableFuture.supplyAsync(() -> 
            node.getRightChild().accept(this, inputTopics)
        );

        CompletableFuture.allOf(leftFuture, rightFuture).join();

        List<String> combinedOutput = new ArrayList<>();
        try {
            combinedOutput.addAll(leftFuture.get());
            combinedOutput.addAll(rightFuture.get());
        } catch (Exception e) {
            if (e.getCause() instanceof UnsafeExecutionException) {
                throw (UnsafeExecutionException) e.getCause(); 
            }
        }
        return combinedOutput;
    }

    @Override
    public List<String> visit(HalfBiPipeNode node, List<String> inputTopics) {
        List<String> leftInput = new ArrayList<>(inputTopics);
        List<String> previousRightOutput = lastRightOutputByNode.get(node);
        if (previousRightOutput != null) {
            leftInput.addAll(previousRightOutput);
            System.out.println("[BiPipe] inject " + previousRightOutput.size() 
                              + "out of the right channel from the previous step in the left channel input");
        }
        
        List<String> leftOutput = node.getLeftChild().accept(this, leftInput);
        
        List<String> combinedInput = new ArrayList<>(inputTopics);
        combinedInput.addAll(leftOutput);
        List<String> rightOutput = node.getRightChild().accept(this, combinedInput);

        lastRightOutputByNode.put(node, rightOutput);
        
        List<String> finalOut = new ArrayList<>(leftOutput);
        finalOut.addAll(rightOutput);
        return finalOut;
    }

    @Override
    public List<String> visit(FullBiPipeNode node, List<String> inputTopics) {
        return visit(new ParallelNode(node.getLeftChild(), node.getRightChild()), inputTopics);
    }
}