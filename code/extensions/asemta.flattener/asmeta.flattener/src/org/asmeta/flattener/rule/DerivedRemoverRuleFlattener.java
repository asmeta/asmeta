package org.asmeta.flattener.rule;

import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public abstract class DerivedRemoverRuleFlattener extends ReflectiveVisitor<Void> implements AsmetaFlattener {
	
	protected Asm asm;

	public DerivedRemoverRuleFlattener(Asm asm) {
		this.asm = asm;
	}

	@Override
	public Asm flattenASM() {
		List<RuleDeclaration> rules = asm.getBodySection().getRuleDeclaration();
		for (RuleDeclaration r : rules) {
			visit(r.getRuleBody());
		}
		return asm;
	}

	public Void visit(BlockRule blockRule) {
		for (Rule rule : blockRule.getRules()) {
			visit(rule);
		}
		return null;
	}

	public Void visit(SeqRule seqRule) {
		for (Rule rule : seqRule.getRules()) {
			visit(rule);
		}
		return null;
	}

	public Void visit(CaseRule caseRule) {
		return null;
	}

	public Void visit(ChooseRule chooseRule) {
		visit(chooseRule.getDoRule());
		return null;
	}

	public Void visit(ConditionalRule conditionalRule) {
		return null;
	}

	public Void visit(ExtendRule extendRule) {
		return null;
	}

	public Void visit(ForallRule forallRule) {
		return null;
	}

	public Void visit(LetRule letRule) {
		return null;
	}

	public Void visit(MacroCallRule macroCallRule) {
		return null;
	}

	public Void visit(SkipRule skipRule) {
		return null;
	}

	public Void visit(TermAsRule termAsRule) {
		return null;
	}

	public Void visit(UpdateRule updateRule) {
		return null;
	}
}
