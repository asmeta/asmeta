package org.asmeta.visualdesigner.commands;

import org.asmeta.visualdesigner.model.DiagramNode;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public class MoveDiagramNodeCommand extends Command {

    private final DiagramNode node;
    private final Rectangle newBounds;

    private Rectangle oldBounds;

    public MoveDiagramNodeCommand(DiagramNode node, Rectangle newBounds) {
        this.node = node;
        this.newBounds = newBounds.getCopy();
    }

    @Override
    public boolean canExecute() {
        return node != null && newBounds != null;
    }

    @Override
    public void execute() {
        oldBounds = new Rectangle(node.getX(), node.getY(), node.getWidth(), node.getHeight());
        redo();
    }

    @Override
    public void redo() {
        node.setLayout(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
    }

    @Override
    public void undo() {
        node.setLayout(oldBounds.x, oldBounds.y, oldBounds.width, oldBounds.height);
    }
}