package org.asmeta.visualdesigner.selection;

import org.asmeta.visualdesigner.model.RuleNode;

public class SelectionManager {

    private RuleNode selectedNode;

    public RuleNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(RuleNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public boolean isSelected(RuleNode node) {
        return selectedNode == node;
    }
}