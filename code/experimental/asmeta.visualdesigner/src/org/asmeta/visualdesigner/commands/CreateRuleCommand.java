package org.asmeta.visualdesigner.commands;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class CreateRuleCommand extends Command {

    private final DiagramModel diagram;
    private final RuleNode node;
    private final Rectangle bounds;

    public CreateRuleCommand(DiagramModel diagram, RuleNode node, Rectangle bounds) {
        this.diagram = diagram;
        this.node = node;
        this.bounds = bounds.getCopy();
    }

    @Override
    public boolean canExecute() {
        return diagram != null && node != null && bounds != null;
    }

    @Override
    public void execute() {
        int width = bounds.width > 0 ? bounds.width : node.getWidth();
        int height = bounds.height > 0 ? bounds.height : node.getHeight();

        if (width <= 0) {
            width = 100;
        }

        if (height <= 0) {
            height = 50;
        }

        node.setLayout(bounds.x, bounds.y, width, height);
        diagram.addRule(node);
    }

    @Override
    public void undo() {
        diagram.removeRule(node);
    }

    @Override
    public void redo() {
        diagram.addRule(node);
    }
}