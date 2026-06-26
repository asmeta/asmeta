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
    private final java.util.IdentityHashMap<FullBiPipeNode, List<String>> lastLeftByFull  = new java.util.IdentityHashMap<>();
    private final java.util.IdentityHashMap<FullBiPipeNode, List<String>> lastRightByFull = new java.util.IdentityHashMap<>();
    private final ConcurrentHashMap<String, String> bootstrapState;
    private final java.util.List<String> committedThisStep =
            java.util.Collections.synchronizedList(new java.util.ArrayList<>());

    public void resetStepTracking() { committedThisStep.clear(); }

    public java.util.List<String> getCommittedThisStep() {
        synchronized (committedThisStep) { return new java.util.ArrayList<>(committedThisStep); }
    }
    
    public static class UnsafeExecutionException extends RuntimeException {
        public UnsafeExecutionException(String message) { super(message); }
    }

    public OrchestrationVisitor(ZMQ.Socket orchPub, ConcurrentHashMap<String, String> mailbox,ConcurrentHashMap<String, String> bootstrapState) {
        this.orchPub = orchPub;
        this.mailbox = mailbox;
        this.bootstrapState = bootstrapState;
    }

    @Override
    public List<String> visit(ModelNode node, List<String> inputTopics) {
        String modelName = node.getModelName();
        System.out.println("[Orchestrator] Request for instructions on: " + modelName);

      
        List<String> enrichedInput = new ArrayList<>();
        if (bootstrapState != null) {
            enrichedInput.addAll(bootstrapState.values());
        }
        enrichedInput.addAll(inputTopics);

        Map<String, Object> command = new HashMap<>();
        command.put("cmd", "STEP");
        command.put("data", enrichedInput);

        synchronized (orchPub) {
            orchPub.sendMore("CMD_" + modelName);
            orchPub.send(gson.toJson(command));
        }
        
        String expectedTopic = "STATUS_" + modelName;
        int notReadyRetries = 0;
        final int MAX_NOT_READY_RETRIES = 50; 

        while (!Thread.currentThread().isInterrupted()) {
            if (mailbox.containsKey(expectedTopic)) {
                String payload = mailbox.remove(expectedTopic);

                Map<String, Object> response = gson.fromJson(payload, new TypeToken<Map<String, Object>>(){}.getType());
                String status = String.valueOf(response.get("asm_status"));

                if ("NOT_READY".equals(status)) {
           
                    notReadyRetries++;
                    if (notReadyRetries > MAX_NOT_READY_RETRIES) {
                        System.err.println("[Orchestrator] Model " + modelName + " still NOT_READY after " + MAX_NOT_READY_RETRIES + " retries.");
                        throw new UnsafeExecutionException(modelName);
                    }
                    synchronized (orchPub) {
                        orchPub.sendMore("CMD_" + modelName);
                        orchPub.send(gson.toJson(command));
                    }
                    try { Thread.sleep(20); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    continue;
                }

              
                if ("UNSAFE".equals(response.get("asm_status"))) {
                    throw new UnsafeExecutionException(modelName);
                }
                if ("SAFE".equals(response.get("asm_status"))) {   
                    committedThisStep.add(modelName);
                }
               

                System.out.println("[Orchestrator]  Model " + modelName + " has finished (SAFE).");

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
            System.out.println("[BiPipe] inject " + previousRightOutput.size()  + "out of the right channel from the previous step in the left channel input");
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
        List<String> previousRight = lastRightByFull.get(node);
        List<String> previousLeft  = lastLeftByFull.get(node);

        List<String> leftInput = new ArrayList<>(inputTopics);
        if (previousRight != null) leftInput.addAll(previousRight);

        List<String> rightInput = new ArrayList<>(inputTopics);
        if (previousLeft != null) rightInput.addAll(previousLeft);

        if (previousLeft != null || previousRight != null) {
            System.out.println("[FullBiPipe] inject feedback from the previous step in both channels");
        }

        CompletableFuture<List<String>> leftFuture = CompletableFuture.supplyAsync(() ->
            node.getLeftChild().accept(this, leftInput));
        CompletableFuture<List<String>> rightFuture = CompletableFuture.supplyAsync(() ->
            node.getRightChild().accept(this, rightInput));

        List<String> leftOutput;
        List<String> rightOutput;
        try {
            leftOutput  = leftFuture.get();   
            rightOutput = rightFuture.get();  
        } catch (Exception e) {
            if (e.getCause() instanceof UnsafeExecutionException) {
                throw (UnsafeExecutionException) e.getCause();  
            }
            throw new RuntimeException(e);
        }

        
        lastLeftByFull.put(node, leftOutput);
        lastRightByFull.put(node, rightOutput);

        List<String> combined = new ArrayList<>(leftOutput);
        combined.addAll(rightOutput);
        return combined;
    }
}