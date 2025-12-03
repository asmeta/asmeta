package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.asmeta.simulator.util.StdlFunction;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.impl.BasictermsFactoryImpl;
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
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;

public class ForAllMutator extends RuleBasedMutator {

	static Asm asm;
	
	public ForAllMutator(Asm asm) {
		super(new RuleVisitorAdapter(new ForAllMut()));
		this.asm = asm;
	}
	
	// every rule (except skip) is converted to skip
	public static class ForAllMut extends RuleVisitor<List<Rule>> {

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
			return Collections.EMPTY_LIST;
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
			List<Rule> mutated  = new ArrayList<Rule>();
			
			//mutation that negate the guard
			ForallRule notGuardRule = BasictransitionrulesFactory.eINSTANCE.createForallRule();
			notGuardRule.setDoRule(rule.getDoRule());
			Term guard = EcoreUtil.copy(rule.getGuard());
			StdlFunction f = new StdlFunction(asm);
			notGuardRule.setGuard(f.not(guard));
			mutated.add(notGuardRule);
			
			//mutation that set guard as true
			ForallRule trueGuardRule = BasictransitionrulesFactory.eINSTANCE.createForallRule();
			trueGuardRule.setDoRule(rule.getDoRule());
			trueGuardRule.setGuard(BasictermsFactoryImpl.eINSTANCE.createBooleanTerm(true));
			mutated.add(trueGuardRule);
			
			//mutation that set guard as false
			ForallRule falseGuardRule = BasictransitionrulesFactory.eINSTANCE.createForallRule();
			falseGuardRule.setDoRule(rule.getDoRule());
			falseGuardRule.setGuard(BasictermsFactoryImpl.eINSTANCE.createBooleanTerm(false));
			mutated.add(falseGuardRule);
			
			return mutated;
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
