package org.asmeta.visualdesigner.commands;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.RuleType;
import org.asmeta.visualdesigner.model.StartNode;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;

public class CreateTransitionCommand extends Command {

    private final DiagramModel diagram;

    private DiagramNode source;
    private DiagramNode target;
    private Transition transition;

    public CreateTransitionCommand(DiagramModel diagram) {
        this.diagram = diagram;
        setLabel("Create transition");
    }

    public void setSource(DiagramNode source) {
        this.source = source;
    }

    public void setTarget(DiagramNode target) {
        this.target = target;
    }

    @Override
    public boolean canExecute() {
        return diagram != null
            && source != null
            && target != null
            && source != target
            && !(target instanceof StartNode)
            && canAddTransitionFromSource();
    }

    private boolean canAddTransitionFromSource() {
        boolean canAdd = diagram.canAddTransitionFrom(source);

        if (source instanceof RuleNode) {
            RuleNode sourceRule = (RuleNode) source;

            if (sourceRule.getType() == RuleType.PAR) {
                canAdd = true;
            }
        }

        return canAdd;
    }

    @Override
    public void execute() {
        String label = diagram.getNextTransitionLabel(source);
        transition = new Transition(source, target, label);
        redo();
    }

    @Override
    public void redo() {
        diagram.addTransition(transition);
    }

    @Override
    public void undo() {
        diagram.removeTransition(transition);
    }
}