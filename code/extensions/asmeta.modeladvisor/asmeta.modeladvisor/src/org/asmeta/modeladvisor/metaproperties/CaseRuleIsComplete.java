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

import asmeta.transitionrules.derivedtransitionrules.CaseRule;

public class CaseRuleIsComplete extends Checker {
	private Map<CaseRule, List<String>> conditions; //condizioni
	private Map<CaseRule, String> smvPropMap;
	Map<CaseRule, Boolean> caseRuleComplete; //risultati
	AsmPrinter ap;

	public CaseRuleIsComplete(Map<CaseRule, List<String>> caseRuleIsComplete, Environment env) {
		conditions = caseRuleIsComplete;
		ap = new AsmPrinter(env);
	}

	public Map<CaseRule, Boolean> getCaseRuleComplete() {
		return caseRuleComplete;
	}

	@Override
	public Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropMap = new HashMap<CaseRule, String>();
		for(CaseRule caseRule: conditions.keySet()){
			//I singoli elementi della lista caseRuleIsComplete.get(caseRule)
			//sono sicuramente vere quando la case rule e'
			//irraggiungibile lungo quel percorso.
			//Nel caso in cui la casee rule sia raggiugibile
			//lungo quel percorso, l'elemento e' vero se e' completa la condizione
			//della case rule.
			property = new AlwaysExpression(Util.and(conditions.get(caseRule)));
			smvProp.add(property);
			smvPropMap.put(caseRule, property.getSMV());
		}
		return smvProp;
	}

	@Override
	public void evaluation(Map<String, Boolean> results){
		String property;
		caseRuleComplete = new HashMap<CaseRule, Boolean>();
		for(CaseRule caseRule: smvPropMap.keySet()){
			property = smvPropMap.get(caseRule);
			caseRuleComplete.put(caseRule, results.get(property));
		}
	}

	@Override
	public void printResults() {
		out.println("MP2: Every case rule without otherwise must be complete");
		boolean noViolation = true;
		for(CaseRule caseRule: caseRuleComplete.keySet()){
			if(!caseRuleComplete.get(caseRule)) {
				out.println("Case rule " + ap.visitCaseHeader(caseRule) + 
						" is not complete.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}