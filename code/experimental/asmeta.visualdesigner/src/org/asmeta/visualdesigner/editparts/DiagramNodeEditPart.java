package org.asmeta.visualdesigner.editparts;

import org.asmeta.visualdesigner.model.DiagramNode;
import org.eclipse.gef.NodeEditPart;

public interface DiagramNodeEditPart extends NodeEditPart {

    DiagramNode getDiagramNode();
}