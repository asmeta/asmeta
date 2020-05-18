package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.AlwaysExpression;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nusmv.Environment;
import org.asmeta.nusmv.util.Util;

import asmeta.transitionrules.basictransitionrules.ConditionalRule;

/**
 * In a conditional rule R = if c then Rthen endif, without else,
 * the condition c must be true if R is evaluated.
 *
 */
public class CondRuleIsComplete extends Checker {
	private Map<ConditionalRule, List<String>> conditions; //condizioni
	private Map<ConditionalRule, String> smvPropMap;
	Map<ConditionalRule, Boolean> condRuleComplete; //risultati;
	AsmPrinter ap;

	public Map<ConditionalRule, Boolean> getCondRuleComplete() {
		return condRuleComplete;
	}

	public CondRuleIsComplete(Map<ConditionalRule, List<String>> condRuleIsComplete, Environment env) {
		this.conditions = condRuleIsComplete;
		ap = new AsmPrinter(env);
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropMap = new HashMap<ConditionalRule, String>();
		for(ConditionalRule condRule: conditions.keySet()) {
			//I singoli elementi della lista conditions.get(caseRule)
			//sono sicuramente vere quando la conditional rule e'
			//irraggiungibile lungo quel percorso.
			//Nel caso in cui la conditional rule sia raggiugibile
			//lungo quel percorso, l'elemento e' vero se e' vera la condizione
			//della conditional rule.
			//"AG("+Util.and(conditions.get(condRule))+")";
			property = new AlwaysExpression(Util.and(conditions.get(condRule)));
			smvProp.add(property);
			smvPropMap.put(condRule, property.getSMV());
		}
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results) {
		condRuleComplete = new HashMap<ConditionalRule, Boolean>();
		String property;
		for(ConditionalRule condRule: smvPropMap.keySet()){
			property = smvPropMap.get(condRule);
			condRuleComplete.put(condRule, results.get(property));
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP2: Every conditional rule must be complete");
		boolean noViolation = true;
		if(condRuleComplete.size() > 0) {
			for(ConditionalRule condRule: condRuleComplete.keySet()){
				if(!condRuleComplete.get(condRule)) {
					out.println("ConditionalRule " +
							ap.visitCondHeader(condRule) + " is not complete.");
					noViolation = false;
				}
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}