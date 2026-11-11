package asmeta.asmeta_zeromq.ast;

import java.util.List;

//AST Visitor Agreement
//defines the operational semantics associated with each node type once it is reached during the traversal

public interface ISimulationVisitor {
    List<String> visit(ModelNode node, List<String> inputTopics);
    List<String> visit(PipeNode node, List<String> inputTopics);
    List<String> visit(ParallelNode node, List<String> inputTopics);
    List<String> visit(HalfBiPipeNode node, List<String> inputTopics);
    List<String> visit(FullBiPipeNode node, List<String> inputTopics);
   
}