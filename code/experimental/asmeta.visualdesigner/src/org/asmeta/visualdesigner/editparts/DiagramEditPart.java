package org.asmeta.visualdesigner.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.policies.DiagramXYLayoutEditPolicy;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class DiagramEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

    @Override
    protected IFigure createFigure() {
        FreeformLayer layer = new FreeformLayer();
        layer.setLayoutManager(new FreeformLayout());
        return layer;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramXYLayoutEditPolicy());
    }

    @Override
    protected List<?> getModelChildren() {
        DiagramModel diagram = (DiagramModel) getModel();
        return diagram.getNodes();
    }

    @Override
    public void activate() {
        if (!isActive()) {
            super.activate();
            getDiagramModel().addPropertyChangeListener(this);
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            getDiagramModel().removePropertyChangeListener(this);
            super.deactivate();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (DiagramModel.PROPERTY_CHILDREN.equals(event.getPropertyName())) {
            refreshChildren();
        }
    }

    private DiagramModel getDiagramModel() {
        return (DiagramModel) getModel();
    }
}