package org.asmeta.visualizer.graphViewer;

import asmeta.definitions.RuleDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

/** different types of nodes of the ASM graph */
class Node {

	public Node() {}
}

class LabeledNode extends Node {
	String label;

	public LabeledNode(String label) {
		this.label = label;
	}
}

class PointNode extends Node {

	public PointNode() {
		super();
	}
}

class LabeledPointNode extends LabeledNode {

	public LabeledPointNode(String label) {
		super(label);
	}
}

class ConditionalNode extends LabeledNode {

	public ConditionalNode(String visit) {
		super(visit);
	}
}

class CaseNode extends LabeledNode {

	public CaseNode(String visit) {
		super(visit);
	}
}

class ForallNode extends LabeledNode {

	public ForallNode(String visit) {
		super(visit);
	}
}

class MacroCallNode extends LabeledNode {
	Rule calledMacroBody;
	RuleDeclaration calledMacro;

	public MacroCallNode(RuleDeclaration rd) {
		super(rd.getName());
		calledMacro = rd;
		calledMacroBody = rd.getRuleBody();
	}
}

class StateNode extends LabeledNode {

	public StateNode(String state, String value) {
		super(state + " = " + value);
	}
}