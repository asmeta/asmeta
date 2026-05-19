package asmeta.asmeta_zeromq.ast;

import java.util.List;

public class PipeNode implements ISimulationNode {
    private final ISimulationNode leftChild; //represents the element that precedes it in the execution and generates the data
    private final ISimulationNode rightChild;//represents the element that follows it or consumes the data

    //When instantiating a PipeNode, you can pass a leaf or another subtree to its left and right
    public PipeNode(ISimulationNode leftChild, ISimulationNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public ISimulationNode getLeftChild() { 
    	return leftChild; 
    	}
    public ISimulationNode getRightChild() {
    	return rightChild;
    	}

    @Override
    public String getFormula() {
        return "(" + leftChild.getFormula() + " | " + rightChild.getFormula() + ")";
    }
    
    @Override
    public List<String> accept(ISimulationVisitor visitor, List<String> inputTopics) {
        return visitor.visit(this, inputTopics);
    }
    
}