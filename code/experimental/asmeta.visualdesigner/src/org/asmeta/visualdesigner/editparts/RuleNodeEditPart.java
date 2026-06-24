package org.asmeta.visualdesigner.editparts;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import org.asmeta.visualdesigner.figures.RuleFigure;
import org.asmeta.visualdesigner.figures.RuleFigureFactory;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.asmeta.visualdesigner.policies.RuleComponentEditPolicy;
import org.eclipse.gef.EditPolicy;
import java.util.List;

import org.asmeta.visualdesigner.policies.RuleGraphicalNodeEditPolicy;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.asmeta.visualdesigner.model.DiagramNode;

public class RuleNodeEditPart extends AbstractGraphicalEditPart
implements PropertyChangeListener, DiagramNodeEditPart {
    @Override
    protected IFigure createFigure() {
        RuleNode node = (RuleNode) getModel();
        return RuleFigureFactory.createFigure(node);
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new RuleComponentEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new RuleGraphicalNodeEditPolicy());
        
    }

    @Override
    protected void refreshVisuals() {
        RuleNode node = (RuleNode) getModel();

        Rectangle bounds = new Rectangle( node.getX(), node.getY(), node.getWidth(), node.getHeight());
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getRuleNode().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            getRuleNode().removePropertyChangeListener(this);
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

        if (RuleNode.PROPERTY_RULE_DATA.equals(event.getPropertyName())) {
            refreshRuleText();
        }
    }
    
    /*private void refreshRuleFigure() {
        getRuleFigure().setRuleName(getRuleNode().getName());
        getFigure().revalidate();
        getFigure().repaint();
    }*/

    public RuleNode getRuleNode() {
        return (RuleNode) getModel();
    }

    public RuleFigure getRuleFigure() {
        return (RuleFigure) getFigure();
    }
    
    @Override
    protected List<?> getModelSourceConnections() {
        return getRuleNode().getSourceTransitions();
    }

    @Override
    protected List<?> getModelTargetConnections() {
        return getRuleNode().getTargetTransitions();
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
        return getRuleNode();
    }
    
    private void refreshRuleText() {
        getRuleFigure().setRuleText(getRuleNode().getDisplayText());
    }
    
}