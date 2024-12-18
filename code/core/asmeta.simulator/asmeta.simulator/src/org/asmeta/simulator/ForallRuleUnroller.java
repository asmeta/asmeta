package org.asmeta.simulator;

import org.apache.log4j.Logger;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * take a rule and unrolls the forall
 * forall $x in P with t($x) do R($) diventa a conditional with many else
 * if t(p1) the do R(p1)
 * else if t(p2) then do(p2) 
 * else if ...
 * 
 * @author garganti
 *
 */
public class ForallRuleUnroller extends RuleTransformer{

	private static Logger logger = Logger.getLogger(ForallRuleUnroller.class);

	
	public ForallRuleUnroller(RuleFactory rf) {
		super(rf);
	}
	
	@Override
	public Rule visit(ForallRule fr) {
		logger.debug("unrolling a forall rule");
		EList<VariableTerm> vars = fr.getVariable();
		if (vars.size() != 1) throw new RuntimeException("multiple vars not supported yet");
		VariableTerm var = vars.get(0);
		Term range = fr.getRanges().get(0);	
		System.out.println(range.getClass());
		if (!(range instanceof DomainTerm))
			throw new RuntimeException("only vars in simple domain are supported");
		Domain domain = ((DomainTerm) range).getDomain();
		// only powerset domains
		PowersetDomain pw = (PowersetDomain) domain;
		if (!(pw.getBaseDomain() instanceof EnumTd))
			throw new RuntimeException("only enums are supported");
		EnumTd enumtd = (EnumTd) pw.getBaseDomain();
		// do rule
		Rule doRule = fr.getDoRule();
		// condition 
		Term condition = fr.getGuard();
		// the resulting rule
		Rule firstConditional = null;
		ConditionalRule mostInner = null;
		// get the elements
		EList<EnumElement> element = enumtd.getElement();
		for (int i = 0; i < element.size()-1; i++) {
			EnumElement e = element.get(i);
			RuleSubstitution substitution = buildSubstituion(var, e, enumtd);
			// build new rule
			Rule newRule =  substitution.visit(doRule);
			// build the new condition
			Term newGurd = substitution.visit(condition);
			// add new condition
			ConditionalRule cr = ruleFactory.createConditionalRule();
			cr.setGuard(newGurd);
			cr.setThenRule(newRule);
			// if it is the firs
			if (firstConditional == null){
				firstConditional = cr;
			} else{
				// the secondo and so on
				mostInner.setElseRule(cr);
			}
			mostInner = cr;
		}
		// add the last one without condition
		RuleSubstitution substitution = buildSubstituion(var, element.get(element.size()-1), enumtd);
		// build new rule and set as final else
		mostInner.setElseRule(substitution.visit(doRule));
		return firstConditional;
	}

	private RuleSubstitution buildSubstituion(VariableTerm var, EnumElement e, EnumTd enumtd) {
		logger.debug("replace " + var.getName()  + " with " + e.getSymbol());
		TermAssignment macroAssignment = new TermAssignment();
		EnumTerm et = ruleFactory.createEnumTerm();
		et.setSymbol(e.getSymbol());
		et.setDomain(enumtd);
		macroAssignment.put(var, et);
		RuleSubstitution substitution = new RuleSubstitution(macroAssignment, ruleFactory);
		return substitution;
	}

}
