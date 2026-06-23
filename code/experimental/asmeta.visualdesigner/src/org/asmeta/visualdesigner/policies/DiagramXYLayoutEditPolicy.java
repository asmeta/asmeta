package org.asmeta.visualdesigner.policies;

import org.asmeta.visualdesigner.commands.CreateRuleCommand;
import org.asmeta.visualdesigner.commands.MoveDiagramNodeCommand;
import org.asmeta.visualdesigner.editparts.DiagramNodeEditPart;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.commands.MoveRuleCommand;
import org.asmeta.visualdesigner.editparts.RuleNodeEditPart;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class DiagramXYLayoutEditPolicy extends XYLayoutEditPolicy {

    @Override
    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
    	if (child instanceof DiagramNodeEditPart && constraint instanceof Rectangle) {
    	    DiagramNodeEditPart nodePart = (DiagramNodeEditPart) child;
    	    DiagramNode node = nodePart.getDiagramNode();

    	    return new MoveDiagramNodeCommand(node, (Rectangle) constraint);
    	}

        return null;
    }

    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        return new NonResizableEditPolicy();
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        Object newObject = request.getNewObject();

        if (!(newObject instanceof RuleNode)) {
            return null;
        }

        RuleNode node = (RuleNode) newObject;
        DiagramModel diagram = (DiagramModel) getHost().getModel();

        Rectangle bounds = (Rectangle) getConstraintFor(request);

        if (bounds.width <= 0) {
            bounds.width = 100;
        }

        if (bounds.height <= 0) {
            bounds.height = 50;
        }

        return new CreateRuleCommand(diagram, node, bounds);
    }
}