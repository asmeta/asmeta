package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.parser.Defs;

import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Every macro call rule is eventually fired
 *
 */
public class MacroCallRuleIsReached extends Checker {
	public Map<Rule, List<String>> ruleCond;
	public RuleIsReached ruleIsReached;

	public MacroCallRuleIsReached(Map<Rule, List<String>> ruleCond) {
		this.ruleCond = new HashMap<Rule, List<String>>();
		for(Entry<Rule, List<String>> entryRuleListConds: ruleCond.entrySet()) {
			if(Defs.isMacroCallRule(entryRuleListConds.getKey())) {
				this.ruleCond.put(entryRuleListConds.getKey(), entryRuleListConds.getValue());
			}
		}
		ruleIsReached = new RuleIsReached(this.ruleCond);
	}

	/**
	 * In uno stato, una macro call rule puo' essere raggiunta seguendo
	 * diversi percorsi. Le condizioni che portano alla macro call rule
	 * in questi percorsi sono (di solito) differenti.
	 * Quindi una macro call rule e' raggiunta in uno stato se almeno una (or)
	 * delle condizioni (conditions) dei vari cammini e' soddisfatta.
	 * Alla fine della visita dell'asm avremo in macroCallRuleIsReached
	 * una condizione macroCond = cond1|..|dcondN
	 * Se vogliamo che la macro call rule sia sempre raggiunta in ogni stato
	 * alla fine scriveremo AG(macroCond).
	 * Se invece vogliamo che la regola sia eseguita almeno in uno stato
	 * scriveremo EF(macroCond).
	 * 
	 * @param macroCallRule la macro call rule
	 */
	@Override
	public
	Set<Expression> createNuSmvProperties() {
		return ruleIsReached.createNuSmvProperties();
	}

	@Override
	public void evaluation(Map<String, Boolean> results) {
		ruleIsReached.evaluation(results);
	}

	@Override
	public
	void printResults() {
		out.println("MP: Macro rule is reached.");
		boolean noViolation = true;
		if(ruleIsReached.neverReachedRule.size() > 0 ||
			ruleIsReached.notAlwaysReachedRule.size() > 0) {
			for(Rule rule: ruleIsReached.neverReachedRule) {
				out.println("Macro rule " +
						((MacroCallRule)rule).getCalledMacro().getName() +
						//rule +
												" has never been reached.");
				noViolation = false;
			}
			for(Rule rule: ruleIsReached.notAlwaysReachedRule) {
				out.println("Macro rule " +
						((MacroCallRule)rule).getCalledMacro().getName() +
						//rule +		
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