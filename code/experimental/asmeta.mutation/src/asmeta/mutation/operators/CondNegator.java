package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
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

//
// removes a condition from a conditiona rule ( betetr tTRUE AND FALSE)
// 
public class CondNegator extends RuleBasedMutator {

	public CondNegator() {
		super(new RuleVisitorAdapter(new NegateCond()));
	}

	// every rule (except skip) is converted to skip
	public static class NegateCond extends RuleVisitor<List<Rule>> {

		@Override
		public List<Rule> visit(UpdateRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(SkipRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(SeqRule seq) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			List<Rule> mutated  = new ArrayList<Rule>();
			// Create a new conditional rule
			ConditionalRule newRule = BasictransitionrulesFactory.eINSTANCE.createConditionalRule();
			// Negate the condition
			Term guard = EcoreUtil.copy(rule.getGuard());

			newRule.setGuard(guard);
			if (rule.getElseRule() == null)
				newRule.setThenRule(BasictransitionrulesFactory.eINSTANCE.createSkipRule());
			else
				newRule.setThenRule(EcoreUtil.copy(rule.getElseRule()));
			newRule.setElseRule(EcoreUtil.copy(rule.getThenRule()));
			mutated.add(newRule);
			return mutated;
		}

		@Override
		public List<Rule> visit(ExtendRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(LetRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ChooseRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			return Collections.EMPTY_LIST;
		}
	}
}
