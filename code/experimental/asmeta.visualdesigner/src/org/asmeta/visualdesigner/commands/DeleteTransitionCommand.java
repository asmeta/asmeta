package org.asmeta.visualdesigner.commands;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;

public class DeleteTransitionCommand extends Command {

    private final DiagramModel diagram;
    private final Transition transition;

    private int oldIndex = -1;
    private boolean removedByThisCommand = false;

    public DeleteTransitionCommand(DiagramModel diagram, Transition transition) {
        this.diagram = diagram;
        this.transition = transition;
        setLabel("Delete transition");
    }

    @Override
    public boolean canExecute() {
        return diagram != null
            && transition != null
            && diagram.getTransitions().contains(transition);
    }

    @Override
    public void execute() {
        oldIndex = diagram.getTransitions().indexOf(transition);
        redo();
    }

    @Override
    public void redo() {
        if (diagram.getTransitions().contains(transition)) {
            diagram.removeTransition(transition);
            removedByThisCommand = true;
        } else {
            removedByThisCommand = false;
        }
    }

    @Override
    public void undo() {
        if (removedByThisCommand) {
            diagram.addTransition(oldIndex, transition);
        }
    }
}