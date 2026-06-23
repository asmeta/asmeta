package org.asmeta.visualdesigner.editparts;

import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.StartNode;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.asmeta.visualdesigner.model.Transition;

public class AsmEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
	    EditPart part = null;

	    if (model instanceof DiagramModel) {
	        part = new DiagramEditPart();
	    } else if (model instanceof StartNode) {
	        part = new StartNodeEditPart();
	    } else if (model instanceof RuleNode) {
	        part = new RuleNodeEditPart();
	    } else if (model instanceof Transition) {
	        part = new TransitionEditPart();
	    }

	    if (part != null) {
	        part.setModel(model);
	    }
	    

	    return part;
	}
    
    
}