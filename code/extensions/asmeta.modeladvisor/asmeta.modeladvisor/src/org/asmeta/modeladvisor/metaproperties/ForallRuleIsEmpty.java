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
import org.asmeta.nusmv.Environment;
import org.asmeta.nusmv.util.Util;

import asmeta.transitionrules.basictransitionrules.ForallRule;

public class ForallRuleIsEmpty extends Checker {
	private Map<ForallRule, String> smvPropNotAlwaysEmptyMap, smvPropAlwaysNotEmptyMap;
	private Map<ForallRule, List<String>> forallRuleSetIsEmpty;
	private AsmPrinter ap;
	public Set<ForallRule> forallRuleAlwaysNotEmpty;
	public Set<ForallRule> forallRuleNotAlwaysEmpty;
	public Set<ForallRule> forallRuleAlwaysEmpty;

	public ForallRuleIsEmpty(Map<ForallRule, List<String>> forallRuleSetIsEmpty, Environment env) {
		ap = new AsmPrinter(env);
		this.forallRuleSetIsEmpty = forallRuleSetIsEmpty;
	}

	@Override
	public
	Set<Expression> createNuSmvProperties() {
		TemporalExpression property;
		smvPropNotAlwaysEmptyMap = new HashMap<ForallRule, String>();
		smvPropAlwaysNotEmptyMap = new HashMap<ForallRule, String>();
		String cond;
		for(ForallRule forallRule: forallRuleSetIsEmpty.keySet()){			
			cond = Util.or(forallRuleSetIsEmpty.get(forallRule));

			//smv4val: Every forall set is not always empty
			//sometimes EF( cond )
			//"AG(!(" + cond + "))";
			property = new SometimeExpression(cond);//il risultato deve essere invertito
			smvProp.add(property);
			smvPropNotAlwaysEmptyMap.put(forallRule, property.getSMV());

			//smv4val: Every forall set is always not empty
			//"AG(" + cond + ")";
			property = new AlwaysExpression(cond);
			smvProp.add(property);
			smvPropAlwaysNotEmptyMap.put(forallRule, property.getSMV());
		}
		return smvProp;
	}

	@Override
	public
	void evaluation(Map<String, Boolean> results) {
		forallRuleAlwaysNotEmpty = new HashSet<ForallRule>();
		forallRuleNotAlwaysEmpty = new HashSet<ForallRule>();
		forallRuleAlwaysEmpty = new HashSet<ForallRule>();
		boolean notAlwaysEmpty, alwaysNotEmpty;
		for (ForallRule rule: forallRuleSetIsEmpty.keySet()) {
			notAlwaysEmpty = !results.get(smvPropNotAlwaysEmptyMap.get(rule));
			alwaysNotEmpty = results.get(smvPropAlwaysNotEmptyMap.get(rule));
			if (alwaysNotEmpty) { //AG(cond) = true
				forallRuleAlwaysNotEmpty.add(rule);
			}
			else if(notAlwaysEmpty) { //(AG(cond) = false) and (EF(cond) = true)
				forallRuleNotAlwaysEmpty.add(rule);
			}
			else { //(AG(cond) = false) and (EF(cond) = false)
				forallRuleAlwaysEmpty.add(rule);
			}
		}
	}

	@Override
	public
	void printResults() {
		out.println("MP3: Forall rule is always/sometimes/never not empty");
		boolean noViolation = true;
		if(forallRuleAlwaysEmpty.size()>0 || forallRuleAlwaysNotEmpty.size()>0 ||
				 					forallRuleNotAlwaysEmpty.size() > 0) {
			for (ForallRule rule: forallRuleAlwaysEmpty) {
				out.println(ap.visitForallRuleHeader(rule) + " is always empty.");
				noViolation = false;
			}
			for (ForallRule rule: forallRuleNotAlwaysEmpty) {
				out.println(ap.visitForallRuleHeader(rule) + " is sometimes empty (but not always).");
				noViolation = false;
			}
			for (ForallRule rule: forallRuleAlwaysNotEmpty) {
				out.println(ap.visitForallRuleHeader(rule) + " is always not empty.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE");
		}
		out.println();
	}
}