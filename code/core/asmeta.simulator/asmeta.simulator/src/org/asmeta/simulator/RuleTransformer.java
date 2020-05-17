package org.asmeta.simulator;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

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

/**
 * transform a rule in another rule
 * This is an adapter and makes nothing (returns the identical rule normally)
 * For rules containing other rules, it applies itself to the contained rules 
 * Extensions can change the behavior
 * @author garganti
 *
 */
public class RuleTransformer extends ReflectiveVisitor<Rule> implements IRuleVisitor<Rule>{

	RuleFactory ruleFactory;
	
	protected RuleTransformer(RuleFactory rf){
		ruleFactory = rf;
	}
	
	@Override
	public Rule visit(Rule rule) {
		return visit((Object) rule);
	}

	@Override
	public Rule visit(SkipRule rule) {
		return rule;
	}

	@Override
	public Rule visit(UpdateRule rule) {
		return rule;
	}

	@Override
	public Rule visit(TermAsRule rule) {
		return rule;
	}

	@Override
	public Rule visit(BlockRule block) {
		EList<Rule> rules = block.getRules();
		List<Rule> trrules = transformRuleCollection(rules);
		if (trrules == rules) return block;
		// otherwise build a new block rule
		BlockRule newBlockRule = ruleFactory.createBlockRule();
		newBlockRule.getRules().addAll(trrules);
		return newBlockRule;
	}

	private List<Rule> transformRuleCollection(EList<Rule> rules){
		List<Rule> newRules = new ArrayList<>();
		boolean changed = false;
		for(Rule r:rules){
			Rule resultingR = visit(r);
			if (resultingR != r){
				changed = true;
			} 
			newRules.add(resultingR);
		}
		if (changed){
			return newRules;
		} else{
			return rules;
		}
	}
	
	@Override
	public Rule visit(SeqRule seq) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(ConditionalRule rule) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(ExtendRule rule) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(LetRule rule) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(ChooseRule rule) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(ForallRule rule) {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(MacroCallRule rule) throws Exception {
		throw new RuntimeException("not supported yet");
	}

	@Override
	public Rule visit(CaseRule rule) {
		throw new RuntimeException("not supported yet");
	}

}
