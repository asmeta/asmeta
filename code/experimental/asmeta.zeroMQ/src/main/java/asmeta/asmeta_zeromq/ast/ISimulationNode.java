package asmeta.asmeta_zeromq.ast;
import java.util.List;

//Basic contract for a co-simulation element. It can represent a single model or a complex composition.

public interface ISimulationNode {
    //Returns a formal representation of the node.
    String getFormula();
    //double dispatch: node type and simulation type, accept a visitor's visit and return  the list of output
    //topics that this sub-tree publishes to the outside
    List<String> accept(ISimulationVisitor visitor, List<String> inputTopics);
}