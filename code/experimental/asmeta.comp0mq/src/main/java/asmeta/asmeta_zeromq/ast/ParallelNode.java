package asmeta.asmeta_zeromq.ast;

public class ParallelNode implements ISimulationNode {
    private final ISimulationNode leftChild;
    private final ISimulationNode rightChild;

    public ParallelNode(ISimulationNode leftChild, ISimulationNode rightChild) {
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
        return "(" + leftChild.getFormula() + " || " + rightChild.getFormula() + ")";
    }
    
    @Override
    public java.util.List<String> accept(ISimulationVisitor visitor, java.util.List<String> inputTopics) {
        return visitor.visit(this, inputTopics);
    }
}