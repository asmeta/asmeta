package org.asmeta.xt.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

// it extracts all the rules contained in a rule (including itself)
// by building a new list of rules (it could be avoided by filling up a given list of rules - more efficient?)
// includes also itself and its rules.
//
public class RuleExtractorFromMacroDecl {

	public static List<Rule> getAllContainedRules(MacroDeclaration md) {
		return new RuleExtractor().visit(md.getRuleBody());
	}

	// the real visitor
	// using an inner static class, so it can be exposed only the public method
	// so if one wants to fill in a given array it easier
	// must be public beacuse it is visitor
	public static class RuleExtractor extends RuleVisitor<List<Rule>> {

		@Override
		public List<Rule> visit(SkipRule rule) {
			return Collections.singletonList(rule);
		}

		@Override
		public List<Rule> visit(UpdateRule rule) {
			return Collections.singletonList(rule);
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(BlockRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			for (Rule r : rule.getRules()) {
				result.addAll(visit(r));
			}
			return result;
		}

		@Override
		public List<Rule> visit(SeqRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			for (Rule r : rule.getRules()) {
				result.addAll(visit(r));
			}
			return result;
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			result.addAll(visit(rule.getThenRule()));
			Rule elseR = rule.getElseRule();
			if (elseR != null) {
				result.addAll(visit(elseR));
			}
			return result;
		}

		@Override
		public List<Rule> visit(ExtendRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			result.addAll(visit(rule.getDoRule()));
			return result;
		}

		@Override
		public List<Rule> visit(LetRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			result.addAll(visit(rule.getInRule()));
			return result;
		}

		@Override
		public List<Rule> visit(ChooseRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			result.addAll(visit(rule.getDoRule()));
			Rule ifNoneR = rule.getIfnone();
			if (ifNoneR != null) {
				result.addAll(visit(ifNoneR));
			}
			return result;
		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			result.addAll(visit(rule.getDoRule()));
			return result;
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			return Collections.singletonList(rule);
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			ArrayList<Rule> result = new ArrayList<>();
			result.add(rule);
			for (Rule r : rule.getCaseBranches()) {
				result.addAll(visit(r));
			}
			result.add(rule.getOtherwiseBranch());
			return result;
		}

	}
}