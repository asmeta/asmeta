package org.asmeta.visualdesigner.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.asmeta.visualdesigner.figures.StartPointFigure;
import org.asmeta.visualdesigner.model.DiagramNode;
import org.asmeta.visualdesigner.model.StartNode;
import org.asmeta.visualdesigner.policies.RuleGraphicalNodeEditPolicy;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class StartNodeEditPart extends AbstractGraphicalEditPart
        implements PropertyChangeListener, DiagramNodeEditPart {

    @Override
    protected IFigure createFigure() {
        return new StartPointFigure();
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new RuleGraphicalNodeEditPolicy());
    }

    @Override
    protected void refreshVisuals() {
        StartNode node = getStartNode();

        Rectangle bounds = new Rectangle(
            node.getX(),
            node.getY(),
            node.getWidth(),
            node.getHeight()
        );

        ((GraphicalEditPart) getParent()).setLayoutConstraint(
            this,
            getFigure(),
            bounds
        );
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getStartNode().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            getStartNode().removePropertyChangeListener(this);
            super.deactivate();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (DiagramNode.PROPERTY_LAYOUT.equals(event.getPropertyName())) {
            refreshVisuals();
        }

        if (DiagramNode.PROPERTY_SOURCE_CONNECTIONS.equals(event.getPropertyName())) {
            refreshSourceConnections();
        }

        if (DiagramNode.PROPERTY_TARGET_CONNECTIONS.equals(event.getPropertyName())) {
            refreshTargetConnections();
        }
    }

    @Override
    protected List<?> getModelSourceConnections() {
        return getStartNode().getSourceTransitions();
    }

    @Override
    protected List<?> getModelTargetConnections() {
        return getStartNode().getTargetTransitions();
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    @Override
    public DiagramNode getDiagramNode() {
        return getStartNode();
    }

    public StartNode getStartNode() {
        return (StartNode) getModel();
    }
}