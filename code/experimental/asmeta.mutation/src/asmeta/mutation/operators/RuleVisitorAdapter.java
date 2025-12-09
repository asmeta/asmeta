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
final public class RuleVisitorAdapter extends RuleVisitor<List<Rule>> {

	private RuleVisitor<List<Rule>> rulemutator;

	RuleVisitorAdapter(RuleVisitor<List<Rule>> rulemutator){
		this.rulemutator = rulemutator;
	} 
	
	@Override
	public List<Rule> visit(SkipRule rule) {		
		return rulemutator.visit(rule);
	}

	@Override
	public List<Rule> visit(UpdateRule rule) {
		return rulemutator.visit(rule);
	}

	@Override
	public List<Rule> visit(TermAsRule rule) {
		return rulemutator.visit(rule);
	}

	@Override
	public List<Rule> visit(BlockRule block) {
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(block));
		// mutate those inside
		EList<Rule> rules = block.getRules();
		for (int i = 0; i < rules.size(); i++) {
			// mutate the i-the rule 
			List<Rule> mutatedRule = this.visit(rules.get(i));
			if (mutatedRule.isEmpty()){
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
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(seq));
		// mutate those inside
		EList<Rule> rules = seq.getRules();
		for (int i = 0; i < rules.size(); i++) {
			// mutate the i-the rule 
			List<Rule> mutatedRule = this.visit(rules.get(i));
			if (mutatedRule.isEmpty()){
				// add the original one
				// mutatedRules.add(block);
			} else {
				for (Rule mr: mutatedRule) {
					List<Rule> rulesN = new ArrayList<>(EcoreUtil.copyAll(rules));
					// 	TODO call recursively
					// 	create a skip rule (or it can be reused?) to check
					rulesN.set(i,mr);
					SeqRule newSeqRule =  asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory.eINSTANCE.createSeqRule();
					newSeqRule.getRules().addAll(rulesN);
					mutatedRules.add(newSeqRule);
				}
			}
		}
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(ConditionalRule rule) {
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(rule));
		// mutate then and elsepart
		List<Rule> thenPart = this.visit(rule.getThenRule());
		// if else is empty, get the empty collection
		List<Rule> elsePart = rule.getElseRule() != null ? this.visit(rule.getElseRule()) : Collections.EMPTY_LIST;
		// check if no mutation has been done
		if (thenPart.isEmpty() && elsePart.isEmpty()) {
			// finish
			return mutatedRules;
		}
		// mutate the then part 
		for (Rule mutThen: thenPart) {
			// recreate the if condition with the mutations
			ConditionalRule newRule = EcoreUtil.copy(rule);
			newRule.setThenRule(mutThen);
			mutatedRules.add(newRule);
		}
		// mutate the else part
		for (Rule mutElse: elsePart) {
			// recreate the if condition with the mutations
			ConditionalRule newRule = EcoreUtil.copy(rule);
			newRule.setElseRule(mutElse);					
			mutatedRules.add(newRule);
		}
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(ExtendRule rule) {
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(rule));
		
		// apply inside
		//mutatedRules.addAll(EcoreUtil.copyAll(this.visit(rule.getDoRule()))); //check
		
		// apply inside CHECK with angelo.
		List<Rule> newDoRules = this.visit(rule.getDoRule());
		for (Rule r : newDoRules) {
			ExtendRule newMutation = EcoreUtil.copy(rule);
			newMutation.setDoRule(r);
			mutatedRules.add(newMutation);
		}
		
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(LetRule rule) {
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(rule));
		// apply inside
		//mutatedRules.addAll(EcoreUtil.copyAll(this.visit(rule.getInRule()))); //check
		
		// apply inside CHECK with angelo.
		List<Rule> newInRules = this.visit(rule.getInRule());
		for (Rule r : newInRules) {
			LetRule newMutation = EcoreUtil.copy(rule);
			newMutation.setInRule(r);
			mutatedRules.add(newMutation);
		}
		
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(ChooseRule rule) {
		List<Rule> mutatedRules = new ArrayList<>();
		// first mutate this one
		mutatedRules.addAll(rulemutator.visit(rule));
		// apply inside
		//mutatedRules.addAll(EcoreUtil.copyAll(this.visit(rule.getDoRule()))); //check
		
		List<Rule> newDoRules = this.visit(rule.getDoRule());
		for (Rule r : newDoRules) {
			ChooseRule newMutation = EcoreUtil.copy(rule);
			newMutation.setDoRule(r);
			mutatedRules.add(newMutation);
		}
		
		Rule ifnoneRule = rule.getIfnone();
		if (ifnoneRule != null) {
			List<Rule> newIfnoneRules = this.visit(ifnoneRule);
			for (Rule r : newIfnoneRules) {
				ChooseRule newMutation = EcoreUtil.copy(rule);
				newMutation.setIfnone(r);
				mutatedRules.add(newMutation);
			}
		}
		
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(ForallRule rule) {
		// TODO Auto-generated method stub
		//throw new RuntimeException("not implemented yet");
		
		//first mutate this rule
		List<Rule> mutatedRules = new ArrayList<>();
		mutatedRules.addAll(rulemutator.visit(rule));
		
		//mutatedRules.addAll(this.visit(rule.getDoRule()));
		
		//visit the rule of for-all's body
		List<Rule> newBodies = this.visit(rule.getDoRule());
		for (Rule r : newBodies) {
			ForallRule newMutation = EcoreUtil.copy(rule);
			newMutation.setDoRule(r);
			mutatedRules.add(newMutation);
		}
		
		return mutatedRules;
	}

	@Override
	public List<Rule> visit(MacroCallRule rule) throws Exception {
		return rulemutator.visit(rule);
	}

	@Override
	public List<Rule> visit(CaseRule rule) {
		//First mutate this case
		List<Rule> mutatedRules = new ArrayList<>();
		mutatedRules.addAll(rulemutator.visit(rule));
		//Now visit each rule for each branch
		int index = 0;
		for (Rule r : rule.getCaseBranches()) {
			List<Rule> mutatedCase = this.visit(r);
			for (Rule mutatedRule : mutatedCase) {
				CaseRule newRule = EcoreUtil.copy(rule);				
				newRule.getCaseBranches().set(index, EcoreUtil.copy(mutatedRule)); //not sure if this copy is required.
				mutatedRules.add(newRule);
			}
			index++;
		}
		//if otherwise then visit the rule 
		if (rule.getOtherwiseBranch() != null ) {
			List<Rule> mutatedOW = this.visit(rule.getOtherwiseBranch());
			for (Rule mutatedRule: mutatedOW) {
				CaseRule newRule = EcoreUtil.copy(rule);
				newRule.setOtherwiseBranch(EcoreUtil.copy(mutatedRule)); 
				mutatedRules.add(newRule);
			}
		}		
		return mutatedRules;
	}

}
