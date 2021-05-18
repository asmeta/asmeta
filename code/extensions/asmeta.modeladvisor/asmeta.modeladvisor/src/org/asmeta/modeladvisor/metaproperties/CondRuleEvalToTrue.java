package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.modeladvisor.texpr.SometimeExpression;
import org.asmeta.modeladvisor.texpr.TemporalExpression;
import org.asmeta.nusmv.Environment;
import org.asmeta.nusmv.util.Util;

import asmeta.transitionrules.basictransitionrules.ConditionalRule;

public class CondRuleEvalToTrue extends Checker {
	private Map<ConditionalRule, String> smvPropMapThen, smvPropMapElse;
	private Map<ConditionalRule, List<String>> condRuleEvalToTrueThen,
														condRuleEvalToTrueElse;
	Set<ConditionalRule> neverElse, neverThen, neverThenElse;
	private AsmPrinter ap;

	public CondRuleEvalToTrue(Map<ConditionalRule, List<String>> condRuleEvalToTrueThen, Map<ConditionalRule, List<String>> condRuleEvalToTrueElse, Environment env) {
		this.condRuleEvalToTrueThen = condRuleEvalToTrueThen;
		this.condRuleEvalToTrueElse = condRuleEvalToTrueElse;
		ap = new AsmPrinter(env);
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropMapThen = new HashMap<ConditionalRule, String>();
		smvPropMapElse = new HashMap<ConditionalRule, String>();
		for(ConditionalRule condRule: condRuleEvalToTrueThen.keySet()){
			//sometimes
			//"EF(" +Util.or(condRuleEvalToTrueThen.get(condRule))+ ")"
			//"AG(!(" +Util.or(condRuleEvalToTrueThen.get(condRule))+ "))"
			property = new SometimeExpression(Util.or(condRuleEvalToTrueThen.get(condRule)));//il risultato deve essere invertito
			smvProp.add(property);
			smvPropMapThen.put(condRule, property.getSMV());
			if(condRuleEvalToTrueElse.containsKey(condRule)){
				//sometimes
				//"EF(" +Util.or(condRuleEvalToTrueElse.get(condRule))+ ")"
				//"AG(!(" +Util.or(condRuleEvalToTrueElse.get(condRule))+ "))"
				property = new SometimeExpression(Util.or(condRuleEvalToTrueElse.get(condRule)));//il risultato deve essere invertito
				smvProp.add(property);
				smvPropMapElse.put(condRule, property.getSMV());
			}
		}
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results){
		boolean thenResult, elseResult;
		neverElse = new HashSet<ConditionalRule>();
		neverThen = new HashSet<ConditionalRule>();
		neverThenElse = new HashSet<ConditionalRule>();
		for(ConditionalRule condRule: smvPropMapThen.keySet()){
			thenResult = results.get(smvPropMapThen.get(condRule));
			if(smvPropMapElse.containsKey(condRule)) {
				elseResult = results.get(smvPropMapElse.get(condRule));
				if(thenResult) {
					if(elseResult){
						neverThenElse.add(condRule);
					}
					else {
						neverThen.add(condRule);
					}
				}
				else {
					if(elseResult) {
						neverElse.add(condRule);
					}
				}
			}
			else {
				if(thenResult) {
					neverThen.add(condRule);
				}
			}
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP3: Conditional rule eval to true");
		boolean noViolation = true;
		if(neverThen.size()>0 || neverElse.size()>0 || neverThenElse.size()>0) {
			for(ConditionalRule condRule: neverThen) {
				out.println("ConditionalRule \""+ap.visitCondRule(condRule)+
										"\" never executes the then branch");
				noViolation = false;
			}
			for(ConditionalRule condRule: neverElse) {
				out.println("ConditionalRule \"" + ap.visitCondRule(condRule) +
										"\" never executes the else branch");
				noViolation = false;
			}
			for(ConditionalRule condRule: neverThenElse) {
				out.println("ConditionalRule \"" + ap.visitCondRule(condRule) +
				"\" never executes the then branch neither the else branch");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}

	public Set<ConditionalRule> getNeverThen() {
		return neverThen;
	}

	public Set<ConditionalRule> getNeverElse() {
		return neverElse;
	}

	public Set<ConditionalRule> getNeverThenElse() {
		return neverThenElse;
	}
}