package org.asmeta.visualdesigner.commands;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;

public class DeleteRuleCommand extends Command {

    private final DiagramModel diagram;
    private final RuleNode node;

    private int oldRuleIndex;

    private final List<Transition> removedTransitions = new ArrayList<>();
    private final List<Integer> removedTransitionIndexes = new ArrayList<>();

    public DeleteRuleCommand(DiagramModel diagram, RuleNode node) {
        this.diagram = diagram;
        this.node = node;
        setLabel("Delete rule");
    }

    @Override
    public boolean canExecute() {
        return diagram != null && node != null && diagram.getRules().contains(node);
    }

    @Override
    public void execute() {
        oldRuleIndex = diagram.getRules().indexOf(node);

        removedTransitions.clear();
        removedTransitionIndexes.clear();

        Set<Transition> connectedTransitions = new LinkedHashSet<>();
        connectedTransitions.addAll(node.getSourceTransitions());
        connectedTransitions.addAll(node.getTargetTransitions());

        for (Transition transition : connectedTransitions) {
            removedTransitions.add(transition);
            removedTransitionIndexes.add(diagram.getTransitions().indexOf(transition));
        }

        redo();
    }

    @Override
    public void redo() {
        for (Transition transition : new ArrayList<>(removedTransitions)) {
            diagram.removeTransition(transition);
        }

        diagram.removeRule(node);
    }

    @Override
    public void undo() {
        diagram.addRule(oldRuleIndex, node);

        for (int i = 0; i < removedTransitions.size(); i++) {
            Transition transition = removedTransitions.get(i);
            int index = removedTransitionIndexes.get(i);

            diagram.addTransition(index, transition);
        }
    }
}