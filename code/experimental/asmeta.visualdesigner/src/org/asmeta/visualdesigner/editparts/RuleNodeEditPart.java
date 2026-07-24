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

import org.asmeta.visualdesigner.figures.ParRuleFigure;
import org.asmeta.visualdesigner.model.RuleType;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;

import org.asmeta.visualdesigner.figures.SkipRuleFigure;

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

        Rectangle bounds = createFigureBounds(node);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
    }

    private Rectangle createFigureBounds(RuleNode node) {
        int width = node.getWidth();
        int height = node.getHeight();

        if (node.getType() == RuleType.PAR) {
            width = ParRuleFigure.PREFERRED_WIDTH;
            height = ParRuleFigure.PREFERRED_HEIGHT;
        } else if (node.getType() == RuleType.SKIP) {
            width = SkipRuleFigure.PREFERRED_WIDTH;
            height = SkipRuleFigure.PREFERRED_HEIGHT;
        }

        return new Rectangle(
                node.getX(),
                node.getY(),
                width,
                height
        );
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
    /*
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return getRuleFigure().getSourceConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return getRuleFigure().getTargetConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return getRuleFigure().getSourceConnectionAnchor();
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return getRuleFigure().getTargetConnectionAnchor();
    }
    */
    
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        RuleNode rule = getRuleNode();
        AnchorPosition position = AnchorPosition.BOTTOM;
        int xOffset = 0;

        if (rule.getType() == RuleType.PAR) {
            position = AnchorPosition.RIGHT;
        } else if (rule.getType() == RuleType.CONDITIONAL && connection.getModel() instanceof Transition) {
            Transition transition = (Transition) connection.getModel();

            if ("true".equals(transition.getLabel())) {
                xOffset = -20;
            } else if ("false".equals(transition.getLabel())) {
                xOffset = 20;
            }
        }

        return createFixedAnchor(getFigure(), position, xOffset, 0);
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        RuleNode rule = getRuleNode();
        AnchorPosition position = AnchorPosition.BOTTOM;

        if (rule.getType() == RuleType.PAR) {
            position = AnchorPosition.RIGHT;
        }

        return createFixedAnchor(getFigure(), position, 0, 0);
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return createFixedAnchor(getFigure(), AnchorPosition.TOP, 0, 0);
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return createFixedAnchor(getFigure(), AnchorPosition.TOP, 0, 0);
    }
    
    @Override
    public DiagramNode getDiagramNode() {
        return getRuleNode();
    }
    
    private void refreshRuleText() {
        getRuleFigure().setRuleText(getRuleNode().getDisplayText());
    }
    
    private enum AnchorPosition {
        TOP,
        BOTTOM,
        RIGHT
    }
    
    private ConnectionAnchor createFixedAnchor(IFigure figure, AnchorPosition position, int xOffset, int yOffset) {
        return new AbstractConnectionAnchor(figure) {
            @Override
            public Point getLocation(Point reference) {
                Rectangle bounds = getOwner().getBounds().getCopy();
                getOwner().translateToAbsolute(bounds);

                Point location = new Point(
                        bounds.x + bounds.width / 2,
                        bounds.y + bounds.height / 2
                );

                if (position == AnchorPosition.TOP) {
                    location = new Point(
                            bounds.x + bounds.width / 2,
                            bounds.y
                    );
                } else if (position == AnchorPosition.BOTTOM) {
                    location = new Point(
                            bounds.x + bounds.width / 2,
                            bounds.y + bounds.height
                    );
                } else if (position == AnchorPosition.RIGHT) {
                    location = new Point(
                            bounds.x + bounds.width,
                            bounds.y + bounds.height / 2
                    );
                }

                location.translate(xOffset, yOffset);

                return location;
            }
        };
    }
   
}