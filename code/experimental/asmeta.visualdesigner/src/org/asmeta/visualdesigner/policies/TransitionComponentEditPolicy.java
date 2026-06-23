package org.asmeta.visualdesigner.policies;

import org.asmeta.visualdesigner.commands.DeleteTransitionCommand;
import org.asmeta.visualdesigner.editparts.TransitionEditPart;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.Transition;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class TransitionComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
	    TransitionEditPart transitionPart = (TransitionEditPart) getHost();
	    Transition transition = (Transition) transitionPart.getModel();

	    if (transitionPart.getSource() == null) {
	        return null;
	    }

	    DiagramModel diagram = (DiagramModel) transitionPart.getSource().getParent().getModel();

	    return new DeleteTransitionCommand(diagram, transition);
	}
}