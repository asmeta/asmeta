package org.asmeta.visualdesigner.commands;

import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;

public class SetTransitionLabelCommand extends Command {

    private final Transition transition;
    private final String newValue;

    private String oldValue;

    public SetTransitionLabelCommand(Transition transition, String newValue) {
        setLabel("Edit switch case");
        this.transition = transition;
        this.newValue = newValue;
    }

    @Override
    public boolean canExecute() {
        return transition != null && transition.isSwitchBranch();
    }

    @Override
    public void execute() {
        oldValue = transition.getLabel();
        redo();
    }

    @Override
    public void redo() {
        transition.setLabel(newValue);
    }

    @Override
    public void undo() {
        transition.setLabel(oldValue);
    }
}