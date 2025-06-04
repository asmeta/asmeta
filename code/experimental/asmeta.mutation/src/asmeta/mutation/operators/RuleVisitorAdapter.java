package asmeta.mutation.operators;

import org.asmeta.simulator.RuleVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

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


// it makes nothing but it is recurive call
// if it is not mutated return the same rule // OR NULL TO THNINK !!! OR EMPTY LIST???
public class RuleVisitorAdapter extends RuleVisitor<List<Rule>> {

	@Override
	public List<Rule> visit(SkipRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(UpdateRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(TermAsRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(BlockRule block) {
		List<Rule> mutatedRules = new ArrayList<>();
		EList<Rule> rules = block.getRules();
		for (int i = 0; i < rules.size(); i++) {
			// mutate the i-the rule 
			List<Rule> mutatedRule = visit(rules.get(i));
			if (noMutationOccurred(rules.get(i),mutatedRule)){
				// add the original one
				// mutatedRules.add(block);
			} else {
				for (Rule mr: mutatedRule) {
					List<Rule> rulesN = new ArrayList<>(EcoreUtil.copyAll(rules));
					// 	TODO call recursively
					// 	create a skip rule (or it can be reused?) to check
					rulesN.set(i,mr);
					BlockRule newBlock = BasictransitionrulesFactory.eINSTANCE.createBlockRule();
					newBlock.getRules().addAll(rulesN);
					mutatedRules.add(newBlock);
				}
			}
		}
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(SeqRule seq) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(ConditionalRule rule) {
		List<Rule> thenpart = this.visit(rule.getThenRule());
		List<Rule> elsePart = rule.getElseRule() != null ? this.visit(rule.getElseRule()) : Collections.singletonList(null);
		// check if no mutation has been done
		if (noMutationOccurred(rule.getThenRule(), thenpart)) {
			// then part immutated and else null
			if (elsePart == null) 
				return Collections.singletonList(rule);
			// elsePart not null and immutated 
			if (noMutationOccurred(rule.getElseRule(),elsePart))
				return Collections.singletonList(rule);
		}
		List<Rule> mutated = new ArrayList<Rule>();
		// some mutation occurred
		for (Rule mutThen: thenpart) {
			for (Rule mutElse: elsePart) {
				// recreate the if condition with the mutations
				ConditionalRule newRule = EcoreUtil.copy(rule);
				newRule.setThenRule(mutThen);
				if  (mutElse != null) {
					newRule.setThenRule(mutElse);					
				} else {
					newRule.setElseRule(null);
				}
				mutated.add(newRule);
			}			
		}
		return mutated;
	}

	private boolean noMutationOccurred(Rule rule, List<Rule> ruleMutations) {
		return ruleMutations.size() == 1 && ruleMutations.get(0) == rule;
	}

	@Override
	public List<Rule> visit(ExtendRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(LetRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(ChooseRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(ForallRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(MacroCallRule rule) throws Exception {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<Rule> visit(CaseRule rule) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
