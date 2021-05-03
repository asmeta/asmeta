package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.AlwaysExpression;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nuxmv.Environment;
import org.asmeta.nuxmv.util.Util;

import asmeta.transitionrules.basictransitionrules.ChooseRule;

public class ChooseRuleIsEmpty extends Checker {
	private Map<ChooseRule, String> smvPropNotAlwaysEmptyMap, smvPropAlwaysNotEmptyMap, smvPropAlwaysChooseRuleFiresMap;
	private Map<ChooseRule, List<String[]>> chooseRuleSetIsEmpty;
	public Set<ChooseRule> chooseRuleAlwaysNotEmpty;
	public Set<ChooseRule> chooseRuleNotAlwaysEmpty;
	public Set<ChooseRule> chooseRuleAlwaysEmpty;
	public Set<ChooseRule> chooseRuleNeverExecuted;
	AsmPrinter ap;

	public ChooseRuleIsEmpty(Map<ChooseRule, List<String[]>> chooseRuleSetIsEmpty, Environment env) {
		ap = new AsmPrinter(env);
		this.chooseRuleSetIsEmpty = chooseRuleSetIsEmpty;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropNotAlwaysEmptyMap = new HashMap<ChooseRule, String>();
		smvPropAlwaysNotEmptyMap = new HashMap<ChooseRule, String>();
		smvPropAlwaysChooseRuleFiresMap = new HashMap<ChooseRule, String>();
		for(ChooseRule chooseRule: chooseRuleSetIsEmpty.keySet()){
			//each element is {"condition that brings to the execution of the choose rule", "choose rule guard"} 
			List<String[]> pathAndChooseConds = chooseRuleSetIsEmpty.get(chooseRule);
			ArrayList<String> impliesConds = new ArrayList<String>();
			ArrayList<String> andConds = new ArrayList<String>();
			ArrayList<String> chooseExecutionConds = new ArrayList<String>();
			for(String[] pathCondChooseCond: pathAndChooseConds) {
				impliesConds.add(Util.implies(pathCondChooseCond[0], pathCondChooseCond[1]));
				andConds.add(Util.and(pathCondChooseCond[0], pathCondChooseCond[1]));
				chooseExecutionConds.add(pathCondChooseCond[0]);
			}

			//smv4val: Every choose rule is always not empty
			//whenever a choose rule fires, its guard must be satisfiable
			property = new AlwaysExpression(Util.and(impliesConds));
			smvProp.add(property);
			smvPropAlwaysNotEmptyMap.put(chooseRule, property.getSMV());

			//smv4val: Every choose rule is not always empty
			//It requires to verify two properties.
			//The first one checks if the choose rule can fire
			property = new SometimeExpression(Util.or(chooseExecutionConds));//il risultato deve essere invertito
			smvProp.add(property);
			smvPropAlwaysChooseRuleFiresMap.put(chooseRule, property.getSMV());
			//The second one checks that at least once the rule fires and the guard is satisfied
			property = new SometimeExpression(Util.or(andConds));//il risultato deve essere invertito
			smvProp.add(property);
			smvPropNotAlwaysEmptyMap.put(chooseRule, property.getSMV());
		}
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results){
		chooseRuleAlwaysNotEmpty = new HashSet<ChooseRule>();
		chooseRuleNotAlwaysEmpty = new HashSet<ChooseRule>();
		chooseRuleAlwaysEmpty = new HashSet<ChooseRule>();
		chooseRuleNeverExecuted = new HashSet<ChooseRule>();
		boolean notAlwaysEmpty, alwaysNotEmpty, doesTheChooseRuleFire;
		for (ChooseRule rule: chooseRuleSetIsEmpty.keySet()) {
			doesTheChooseRuleFire = !results.get(smvPropAlwaysChooseRuleFiresMap.get(rule));
			if(doesTheChooseRuleFire) {
				//when the choose rule is executed, the guard is satisfied
				alwaysNotEmpty = results.get(smvPropAlwaysNotEmptyMap.get(rule));
				if (alwaysNotEmpty) {
					chooseRuleAlwaysNotEmpty.add(rule);
				}
				else {
					notAlwaysEmpty = !results.get(smvPropNotAlwaysEmptyMap.get(rule));
					if(notAlwaysEmpty) {
						chooseRuleNotAlwaysEmpty.add(rule);
					}
					else {
						chooseRuleAlwaysEmpty.add(rule);
					}
				}
			}
			else {
				chooseRuleNeverExecuted.add(rule);
			}
		}
	}

	@Override
	public void printResults() {
		out.println("MP3: Choose rule is always/sometimes/never not empty");
		if(chooseRuleSetIsEmpty.keySet().size() > 0) {
			for (ChooseRule rule: chooseRuleNeverExecuted) {
				out.println(ap.visitChooseRuleHeader(rule) + " is never executed.");
			}
			for (ChooseRule rule: chooseRuleAlwaysEmpty) {
				out.println(ap.visitChooseRuleHeader(rule) + " (when executed) is always empty.");
			}
			for (ChooseRule rule: chooseRuleNotAlwaysEmpty) {
				out.println(ap.visitChooseRuleHeader(rule) + " (when executed) is sometimes empty (but not always).");
			}
			for (ChooseRule rule: chooseRuleAlwaysNotEmpty) {
				out.println(ap.visitChooseRuleHeader(rule) + " (when executed) is always not empty.");
			}
		}
		else {
			out.println("NONE");
		}
		out.println();
	}
}