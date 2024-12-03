package org.asmeta.xt.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.asmeta.simulator.RuleVoidVisitor;

import asmeta.definitions.RuleDeclaration;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class TopologicalSort extends RuleVoidVisitor {

	static boolean[] marked;
	static RuleDeclaration[] rules;
	static TopologicalSort visitor;
	static List<RuleDeclaration> sorted;

	public static RuleDeclaration[] sort(Collection<RuleDeclaration> dcls) {
		rules = dcls.toArray(new RuleDeclaration[0]);
		marked = new boolean[rules.length];
		sorted = new ArrayList<>();
		visitor = new TopologicalSort();
		for (RuleDeclaration dcl : rules) {
			visitor.visit(dcl);
		}
		RuleDeclaration[] tmp = sorted.toArray(new RuleDeclaration[0]);
		return tmp;
	}

	void visit(RuleDeclaration dcl) {
		int i = search(dcl);
		if (i != -1 && !marked[i]) {
			marked[i] = true;
			Rule rule = dcl.getRuleBody();
			visit(rule);
			sorted.add(dcl);
		}
	}

	@Override
	public Void visit(MacroCallRule macro) {
		MacroDeclaration dcl = macro.getCalledMacro();
		visit(dcl);
		return null;
	}

	int search(RuleDeclaration dcl) {
		for (int i = 0; i < rules.length; i++) {
			RuleDeclaration dcl2 = rules[i];
			if (dcl == dcl2) {
				return i;
			}
		}
		return -1;
	}
}
