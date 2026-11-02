package org.asmeta.visualdesigner.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.graphics.Font;


public class TransitionEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {
	
    private Label label;
    private LocalResourceManager labelResources;
    private Font caseLabelFont;

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();

            getTransition().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            getTransition().removePropertyChangeListener(this);

            super.deactivate();
        }
    }

    private Transition getTransition() {
        return (Transition) getModel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (Transition.PROPERTY_LABEL.equals(event.getPropertyName())) {
            refreshVisuals();
        }
    }

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

        if (transition.hasLabel() || transition.isSwitchBranch()) {
            label = new Label(transition.getDisplayLabel());

            if (transition.isSwitchBranch()) {
                label.setFont(getCaseLabelFont());
                label.setBorder(new MarginBorder(2, 4, 2, 4));
                label.setOpaque(true);
                label.setBackgroundColor(getViewer().getControl().getBackground());
                ConnectionLocator locator = new ConnectionLocator(connection, ConnectionLocator.MIDDLE);
                connection.add(label, locator);
            } else {
                ConnectionEndpointLocator locator = new ConnectionEndpointLocator(connection, false);
                connection.add(label, locator);
            }
        }
    }


    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());

        installEditPolicy(EditPolicy.COMPONENT_ROLE, new TransitionComponentEditPolicy());
    }
    
    private Font getCaseLabelFont() {
        if (caseLabelFont == null) {
            labelResources = new LocalResourceManager(JFaceResources.getResources(), getViewer().getControl());
            Font baseFont = getFigure().getFont();

            int height = Math.max(1, baseFont.getFontData()[0].getHeight() - 1);
            FontDescriptor descriptor = FontDescriptor.createFrom(baseFont).setHeight(height);

            caseLabelFont = labelResources.createFont(descriptor);
        }

        return caseLabelFont;
    }
    
}
