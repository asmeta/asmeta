package org.asmeta.visualdesigner.policies;

import org.asmeta.visualdesigner.commands.CreateTransitionCommand;
import org.asmeta.visualdesigner.editparts.DiagramNodeEditPart;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.editparts.RuleNodeEditPart;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.Request;
import org.eclipse.swt.SWT;

public class RuleGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
	    DiagramNodeEditPart sourcePart = (DiagramNodeEditPart) getHost();
	    DiagramNode source = sourcePart.getDiagramNode();

	    DiagramModel diagram = (DiagramModel) sourcePart.getParent().getModel();

	    CreateTransitionCommand command = new CreateTransitionCommand(diagram);
	    command.setSource(source);

	    request.setStartCommand(command);

	    return command;
	}

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
	    DiagramNodeEditPart targetPart = (DiagramNodeEditPart) getHost();
	    DiagramNode target = targetPart.getDiagramNode();

	    CreateTransitionCommand command = (CreateTransitionCommand) request.getStartCommand();
	    command.setTarget(target);

	    return command;
	}
    
    @Override
    protected Connection createDummyConnection(Request request) {
        PolylineConnection connection = new PolylineConnection();

        connection.setLineStyle(SWT.LINE_DASH);
        connection.setLineWidth(1);

        PolylineDecoration decoration = new PolylineDecoration();

        PointList points = new PointList();
        points.addPoint(-1, 1);
        points.addPoint(0, 0);
        points.addPoint(-1, -1);

        decoration.setTemplate(points);
        decoration.setScale(10, 5);

        connection.setTargetDecoration(decoration);

        return connection;
    }

    @Override
    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        return null;
    }

    @Override
    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        return null;
    }
}