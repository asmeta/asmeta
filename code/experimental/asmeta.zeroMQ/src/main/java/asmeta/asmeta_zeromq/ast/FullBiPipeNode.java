package asmeta.asmeta_zeromq.ast;

import java.util.List;

public class FullBiPipeNode implements ISimulationNode {
    private final ISimulationNode leftChild;
    private final ISimulationNode rightChild;

    public FullBiPipeNode(ISimulationNode leftChild, ISimulationNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public ISimulationNode getLeftChild() { return leftChild; }
    public ISimulationNode getRightChild() { return rightChild; }

    @Override
    public String getFormula() {
        return "(" + leftChild.getFormula() + " <||> " + rightChild.getFormula() + ")";
    }

    @Override
    public List<String> accept(ISimulationVisitor visitor, List<String> inputTopics) {
        return visitor.visit(this, inputTopics);
    }
}