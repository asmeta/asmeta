package asmeta.mutation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.eclipse.emf.common.util.EList;

import asmeta.transitionrules.TransitionRulesFactory;
import asmeta.transitionrules.basictransitionrules.BlockRule;

import asmeta.transitionrules.basictransitionrules.Rule;

public class RuleMutator extends ReflectiveVisitor<Iterator<Rule>>{

	private TransitionRulesFactory rulesPack;

	public RuleMutator() {
		this.rulesPack = TransitionRulesFactory.eINSTANCE;
	}

	
	public Iterator<Rule> visit(BlockRule blockRule) {
		EList<Rule> rules = blockRule.getRules();
		Iterator<Rule> it = rules.iterator();
		return new Iterator<Rule>(){
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
			@Override
			public Rule next() {
				// 
				Rule current = it.next();
				// TODO call recursively
				// copy the list of the rules
				List<Rule> copy_rules = new ArrayList<>(rules);
				// remove the current one
				copy_rules.remove(current);
				//
				BlockRule newBlockRule = rulesPack.getBasicTransitionRules().createBlockRule();
				newBlockRule.getRules().addAll(copy_rules);
				return newBlockRule;
			}
		};
	}

}
