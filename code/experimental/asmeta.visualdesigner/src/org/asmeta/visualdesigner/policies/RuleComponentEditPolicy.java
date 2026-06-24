package org.asmeta.visualdesigner.policies;

import org.asmeta.visualdesigner.commands.DeleteRuleCommand;
import org.asmeta.visualdesigner.editparts.RuleNodeEditPart;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class RuleComponentEditPolicy extends ComponentEditPolicy {

  
    @Override
    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        RuleNode rule = (RuleNode) getHost().getModel();
        DiagramModel diagram = (DiagramModel) getHost().getParent().getModel();

        return new DeleteRuleCommand(diagram, rule);
    }
}