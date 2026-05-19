package asmeta.asmeta_zeromq.ast;

import java.util.List;

public class ModelNode implements ISimulationNode {
    private final String modelName;

    //It receives the name and stores it
    public ModelNode(String modelName) {
        this.modelName = modelName.trim();
    }
    //Displays the model name at ChoreographyVisitor where the node is translated into a ZeroMQ entity
    public String getModelName() {
        return modelName;
    }
    //it's a ModelNode name
    @Override
    public String getFormula() {
        return modelName;
    }
    //The node informs the visitor of its exact identity
    //'this' allows the visitor 
    // to access ModelNode methods such as getModelName() or getFormula()
    @Override
    public List<String> accept(ISimulationVisitor visitor, List<String> inputTopics) {
        return visitor.visit(this, inputTopics);
    }    
}
