package org.asmeta.visualdesigner.editparts;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.asmeta.visualdesigner.policies.TransitionComponentEditPolicy;


public class TransitionEditPart extends AbstractConnectionEditPart {

    @Override
    protected IFigure createFigure() {
        PolylineConnection connection = new PolylineConnection();

        PolylineDecoration decoration = new PolylineDecoration();
        PointList points = new PointList();
        points.addPoint(-1, 1);
        points.addPoint(0, 0);
        points.addPoint(-1, -1);
        decoration.setTemplate(points);
        decoration.setScale(10, 5);

        connection.setTargetDecoration(decoration);

        Label label = new Label("transition");
        ConnectionEndpointLocator locator = new ConnectionEndpointLocator(connection, false);
        connection.add(label, locator);

        return connection;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new TransitionComponentEditPolicy());
    }
}
