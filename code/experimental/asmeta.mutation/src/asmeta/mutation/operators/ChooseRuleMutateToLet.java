package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;

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
import asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.transitionrules.basictransitionrules.*;

public class ChooseRuleMutateToLet extends RuleBasedMutator {

	public ChooseRuleMutateToLet() {
		super(new ChoseRuleToLet());
	}

	static public class ChoseRuleToLet extends RuleVisitor<List<Rule>> {

		@Override
		public List<Rule> visit(SkipRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(UpdateRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(SeqRule seq) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ExtendRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(LetRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ChooseRule rule) {
			// assuming
			// only 1 variable in choose
			List<Rule> result = new ArrayList<>();
			// convert to let rule
			LetRule let = BasictransitionrulesFactory.eINSTANCE.createLetRule();
			let.setInRule(rule.getDoRule());
			let.getVariable().add(rule.getVariable().get(0));
			// set the init expression a random value (integer for now)
			IntegerTerm i = asmeta.terms.furtherterms.FurthertermsFactory.eINSTANCE.createIntegerTerm();
			i.setSymbol("1");
			// 
			let.getInitExpression().add(i);
			//
			result.add(let);
			return result;

		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			throw new RuntimeException("not implemented yet");
		}

	}

}
