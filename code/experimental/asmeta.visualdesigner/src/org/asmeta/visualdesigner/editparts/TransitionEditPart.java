package org.asmeta.visualdesigner.editparts;

import org.asmeta.visualdesigner.model.Transition;
import org.asmeta.visualdesigner.policies.TransitionComponentEditPolicy;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

public class TransitionEditPart extends AbstractConnectionEditPart {

    private Label label;

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

        return connection;
    }

    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();

        Transition transition = (Transition) getModel();
        PolylineConnection connection = (PolylineConnection) getFigure();

        if (label != null) {
            connection.remove(label);
            label = null;
        }

        if (transition.hasLabel()) {
            label = new Label(transition.getLabel());
            ConnectionEndpointLocator locator = new ConnectionEndpointLocator(connection, false);

            connection.add(label, locator);
        }
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new TransitionComponentEditPolicy());
    }
}
