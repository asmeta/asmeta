package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.AlwaysExpression;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nuxmv.util.Util;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Every rule is eventually fired
 *
 */
public class RuleIsReached extends Checker {
	public Map<Rule, List<String>> conditions; //conditions
	public Map<Rule, String> smvPropReached;
	public Map<Rule, String> smvPropAlwaysReached;
	public Set<Rule> neverReachedRule;
	public Set<Rule> notAlwaysReachedRule;
	private int numOfRules;
	private boolean checkAlwaysReached;
	
	public RuleIsReached(Map<Rule, List<String>> ruleCond) {
		this(ruleCond, true);
	}

	public RuleIsReached(Map<Rule, List<String>> ruleCond, boolean checkAlwaysReached) {
		conditions = ruleCond;
		numOfRules = ruleCond.keySet().size();
		this.checkAlwaysReached = checkAlwaysReached;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		// smv4val: Every rule is eventually fired
		String cond;
		smvPropReached = new HashMap<Rule, String>(numOfRules);
		smvPropAlwaysReached = new HashMap<Rule, String>(numOfRules);
		TemporalExpression property;
		for (Rule rule: conditions.keySet()) {
			cond = Util.or(conditions.get(rule));
			// sometimes
			//EF( + cond + );
			//AG(!( + cond + ))
			property = new SometimeExpression(cond);
			smvProp.add(property);
			smvPropReached.put(rule, property.getSMV());

			if(checkAlwaysReached) {
				//property = "AG(" + cond + ")";
				property = new AlwaysExpression(cond);
				smvProp.add(property);
				smvPropAlwaysReached.put(rule, property.getSMV());
			}
		}
		return smvProp;
	}

	/**
	 * Prints whether a rule is reachable or not.
	 */
	@Override
	public void evaluation(Map<String, Boolean> results) {
		neverReachedRule = new HashSet<Rule>();
		notAlwaysReachedRule = new HashSet<Rule>();
		boolean alwaysReached, sometimesReached;
		for(Rule rule: conditions.keySet()) {
			String key = smvPropAlwaysReached.get(rule);
			assert key != null;
			alwaysReached = results.get(key);
			sometimesReached = !results.get(smvPropReached.get(rule));
			assert !alwaysReached || sometimesReached: "ERROR"; 
			if(!sometimesReached) {
				neverReachedRule.add(rule);
			}
			else if(!alwaysReached) {
				notAlwaysReachedRule.add(rule);
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP: Rule is reached.");
		boolean noViolation = true;
		if(neverReachedRule.size() > 0 || notAlwaysReachedRule.size() > 0) {
			for(Rule rule: neverReachedRule) {
				out.println("Rule " + rule + " has never been reached.");
				noViolation = false;
			}
			for(Rule rule: notAlwaysReachedRule) {
				out.println("Rule " + rule +
							" has sometimes (but not always) been reached.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE.");
		}
		out.println();
	}
}