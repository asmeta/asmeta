package org.asmeta.visualdesigner.commands;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;

public class DeleteRuleCommand extends Command {

    private final DiagramModel diagram;
    private final RuleNode rule;

    private int oldRuleIndex;

    private final List<Transition> removedTransitions = new ArrayList<>();
    private final List<Integer> removedTransitionIndexes = new ArrayList<>();

    public DeleteRuleCommand(DiagramModel diagram, RuleNode rule) {
        this.diagram = diagram;
        this.rule = rule;
        setLabel("Delete rule");
    }

    @Override
    public boolean canExecute() {
        return diagram != null && rule != null;
    }

    @Override
    public void execute() {
        oldRuleIndex = diagram.getRules().indexOf(rule);

        removedTransitions.clear();
        removedTransitionIndexes.clear();

        List<Transition> transitionsSnapshot = new ArrayList<>(diagram.getTransitions());

        for (int i = 0; i < transitionsSnapshot.size(); i++) {
            Transition transition = transitionsSnapshot.get(i);

            if (transition.getSource() == rule || transition.getTarget() == rule) {
                removedTransitions.add(transition);
                removedTransitionIndexes.add(i);
            }
        }

        redo();
    }

    @Override
    public void redo() {
        for (Transition transition : removedTransitions) {
            diagram.removeTransition(transition);
        }

        diagram.removeRule(rule);
    }

    @Override
    public void undo() {
        diagram.addRule(oldRuleIndex, rule);

        for (int i = 0; i < removedTransitions.size(); i++) {
            Transition transition = removedTransitions.get(i);
            int index = removedTransitionIndexes.get(i);

            diagram.addTransitionAt(index, transition);
        }
    }
}