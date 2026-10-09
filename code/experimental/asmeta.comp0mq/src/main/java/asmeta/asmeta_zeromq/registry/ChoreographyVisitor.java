package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ast.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//The visitor is the calculation logic of the wiring
public class ChoreographyVisitor implements ISimulationVisitor {
	
	//A map that at the end of the visit will contain a list of subscriptions to be configured for each model name
    private final Map<String, List<String>> modelSubscriptions = new HashMap<>();

    //base case: leaf
    @Override
    public List<String> visit(ModelNode node, List<String> inputTopics) {
    	//This model must be subscribed to all topics received in inputTopics. the outputs from the upstream
    	//nodes in the causal graph, plus any environmental topics
    	
    	//Model X is subscribed to these topics
        modelSubscriptions.put(node.getModelName(), new ArrayList<>(inputTopics)); 
        String myOutputTopic = "OUT_" + node.getModelName(); //declare his output 
        System.out.println("[Wiring] " + node.getModelName() + " SUB: " + inputTopics + " | PUB: [" + myOutputTopic + "]");
        
        //This list is what the caller (a operator) will use to propagate the value to subsequent children.
        return List.of(myOutputTopic);
    }
    
    //calculate network dependencies (the PUB and SUB topics) when the parser encounters an operator
    //Simplex Pipe:
    //The left child is visited with the current input and produces a certain output. That output becomes the 
    //input for the right child. The return value of the pipe is the output of the right child.
    @Override
    public List<String> visit(PipeNode node, List<String> inputTopics) {
        List<String> leftOutput = node.getLeftChild().accept(this, inputTopics);
        return node.getRightChild().accept(this, leftOutput);
    }

    //Fork-Join:
    /*Both receive the same input topics from the upstream environment or context, and do not exchange any data with each other. 
     *The output of the Parallel node is the union of the outputs of the two children, 
     *because any downstream entity must subscribe to both.
     */
    @Override
    public List<String> visit(ParallelNode node, List<String> inputTopics) {
    
        List<String> leftOutput = node.getLeftChild().accept(this, inputTopics);
        List<String> rightOutput = node.getRightChild().accept(this, inputTopics);
        
        List<String> combinedOutput = new ArrayList<>(leftOutput);
        combinedOutput.addAll(rightOutput);
        return combinedOutput;
    }

    // Half-Duplex Bidirectional Pipe
    @Override
    public List<String> visit(HalfBiPipeNode node, List<String> inputTopics) {
        return buildBidirectionalWiring(node.getLeftChild(), node.getRightChild(), inputTopics);
    }

    //Full-Duplex Bidirectional Pipe
    @Override
    public List<String> visit(FullBiPipeNode node, List<String> inputTopics) {
        return buildBidirectionalWiring(node.getLeftChild(), node.getRightChild(), inputTopics);
    }

    /* It achieves symmetry in the bipipe architecture by first determining the topology (who publishes what) without side effects,
     *  and then performing the actual visit with cross-inputs that include the output from the sibling 
     */
    private List<String> buildBidirectionalWiring(ISimulationNode left, ISimulationNode right, List<String> inputTopics) {
        List<String> leftExpectedOut = peekOutputTopics(left);
        List<String> rightExpectedOut = peekOutputTopics(right);

        // M1 reads Env + M2
        List<String> leftInputs = new ArrayList<>(inputTopics);
        leftInputs.addAll(rightExpectedOut);

        // M2 reads Env + M1
        List<String> rightInputs = new ArrayList<>(inputTopics);
        rightInputs.addAll(leftExpectedOut);

        List<String> leftActualOut = left.accept(this, leftInputs);
        List<String> rightActualOut = right.accept(this, rightInputs);

        List<String> combinedOutput = new ArrayList<>(leftActualOut);
        combinedOutput.addAll(rightActualOut);
        return combinedOutput;
    }

    /*Recursively traverses the tree structure to deduce the names of the output topics. It is used by buildBidirectionalWiring
     * to break the circular dependency of the bipipes.
     * what topics he would post about to the outside
     */ 
    private List<String> peekOutputTopics(ISimulationNode node) {
    	//leaf
        if (node instanceof ModelNode) return List.of("OUT_" + ((ModelNode)node).getModelName());
        //pipe:What the pipe exposes to the outside is simply the final output of c2
        if (node instanceof PipeNode) return peekOutputTopics(((PipeNode)node).getRightChild());
        
        List<String> combined = new ArrayList<>();
        /*Nodes downstream can subscribe to the output of one, the other, or both. 
         * Therefore, the node's output is the union of the two outputs.
        */
        if (node instanceof ParallelNode) {
            combined.addAll(peekOutputTopics(((ParallelNode)node).getLeftChild()));
            combined.addAll(peekOutputTopics(((ParallelNode)node).getRightChild()));
        } else if (node instanceof HalfBiPipeNode) {
            combined.addAll(peekOutputTopics(((HalfBiPipeNode)node).getLeftChild()));
            combined.addAll(peekOutputTopics(((HalfBiPipeNode)node).getRightChild()));
        } else if (node instanceof FullBiPipeNode) {
            combined.addAll(peekOutputTopics(((FullBiPipeNode)node).getLeftChild()));
            combined.addAll(peekOutputTopics(((FullBiPipeNode)node).getRightChild()));
        }
        return combined;
    }
//Returns a reference to the map populated during the visit. It must be called after astRoot.accept(visitor, envTopics),
//because the map is not complete until that point
    
    public Map<String, List<String>> getModelSubscriptions() {
        return modelSubscriptions;
    }
}