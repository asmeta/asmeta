package org.asmeta.visualdesigner.model;

public class Transition {

	private RuleNode source;
    private RuleNode target;

    public Transition(RuleNode source, RuleNode target) {
        this.source = source;
        this.target = target;
    }

    public RuleNode getSource() {
        return source;
    }

    public RuleNode getTarget() {
        return target;
    }
	
}
