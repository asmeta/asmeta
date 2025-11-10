package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.mutation.operators.CondRemover.RemoveCond;
import asmeta.structure.Asm;
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
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;

public class CaseMutator extends RuleBasedMutator {

	
	CaseMutator(){
		super(new RuleVisitorAdapter(new CaseMutOp()));
	}
	
	public static class CaseMutOp extends RuleVisitor<List<Rule>> {

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
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			List<Rule> mutations = new ArrayList<Rule>();
			List<Rule> branches = (List<Rule>) EcoreUtil.copyAll(rule.getCaseBranches());
			List<Term> cases = (List<Term>) EcoreUtil.copyAll(rule.getCaseTerm());
			int i = 0;
			for (Rule r : branches) {
				List<Rule> copyBranches = (List<Rule>) EcoreUtil.copyAll(branches);
				List<Term> copyCases = (List<Term>) EcoreUtil.copyAll(cases);
				copyBranches.remove(i);
				copyCases.remove(i);
				i++;
				CaseRule mutant = DerivedtransitionrulesFactory.eINSTANCE.createCaseRule();
				mutant.setTerm(rule.getTerm());
				mutant.getCaseTerm().addAll(copyCases);
				mutant.getCaseBranches().addAll(copyBranches);
				
				if (rule.getOtherwiseBranch() != null) {
					mutant.setOtherwiseBranch(EcoreUtil.copy(rule.getOtherwiseBranch()));
				}
				
				mutations.add(mutant);
			}
			//If otherwise is used, then a case without otherwise is added.
			if (rule.getOtherwiseBranch() != null) {
				CaseRule mutant = DerivedtransitionrulesFactory.eINSTANCE.createCaseRule();
				mutant.setTerm(rule.getTerm());
				mutant.getCaseTerm().addAll(cases);
				mutant.getCaseBranches().addAll(branches);
				mutations.add(mutant);
			}
			//ask if Term should be a copy too   ????
			
			return mutations;
		}

	}
}