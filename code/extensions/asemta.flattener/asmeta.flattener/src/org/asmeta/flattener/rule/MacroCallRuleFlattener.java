package org.asmeta.flattener.rule;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.parser.util.AsmPrinter;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;

public class MacroCallRuleFlattener extends RuleFlattener {
	private Set<MacroDeclaration> macroRulesToKeep;

	public MacroCallRuleFlattener(Asm asm) {
		super(asm);
		macroRulesToKeep = new HashSet<>();
	}

	@Override
	public Asm flattenASM() {
		/*
		 * for (Initialization is : asm.getInitialState()) { for (AgentInitialization ag
		 * : is.getAgentInitialization()) {
		 * macroRulesToKeep.add(ag.getProgram().getCalledMacro()); } }
		 */
		EList<RuleDeclaration> ruleDeclaration = asm.getBodySection().getRuleDeclaration();
		// all the rules including the main rule
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visit(asm);
		// System.out.println(sw.toString());
		for (RuleDeclaration r : ruleDeclaration) {
			logger.debug("rule " + r.getName());
			r.setRuleBody(visit(r.getRuleBody()));
			sw = new StringWriter();
			writer = new PrintWriter(sw);
			ap = new AsmPrinter(writer);

			ap.visit(asm);
			// System.out.println(sw.toString());
		}
		MacroDeclaration mainRule = asm.getMainrule();
		// ruleDeclaration.clear();
		Iterator<RuleDeclaration> it = ruleDeclaration.iterator();
		while (it.hasNext()) {
			RuleDeclaration macro = it.next();
			if (!macroRulesToKeep.contains(macro)) {
				it.remove();
			}
		}
		ruleDeclaration.add(mainRule);
		for (Initialization is : asm.getInitialState()) {
			is.getAgentInitialization().clear();
		}
		return asm;
	}

	@Override
	public Rule visit(TermAsRule termAsRule) {
		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());
		Term term = termAsRule.getTerm();
		if (term instanceof FunctionTerm) {
			FunctionTerm f = (FunctionTerm) term;
			if (f.getFunction().getName().equals("program")) {
				Term agent = f.getArguments().getTerms().get(0);
				for (Initialization is : asm.getInitialState()) {
					for (AgentInitialization ag : is.getAgentInitialization()) {
						MacroDeclaration calledMacro = ag.getProgram().getCalledMacro();
						if (ag.getDomain() == agent.getDomain()) {
							trv.currentSelf = agent;
							rename = true;
							Rule newRule = visit(calledMacro.getRuleBody());
							rename = false;
							trv.currentSelf = null;
							return newRule;
						}
					}
				}
			}
		}
		return termAsRule;
	}

	@Override
	public Rule visit(MacroCallRule macroCallRule) {
		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());
		rename = true;
		MacroDeclaration calledMacro = macroCallRule.getCalledMacro();
		if (macroRulesToKeep.contains(calledMacro)) {
			return macroCallRule;
		}
		logger.debug(macroCallRule.getCalledMacro().getName() + " called");

		Map<VariableTerm, Term> variableMap = new HashMap<VariableTerm, Term>();
		EList<VariableTerm> vars = calledMacro.getVariable();
		EList<Term> values = macroCallRule.getParameters();
		assert vars.size() == values.size();
		for (int i = 0; i < vars.size(); i++) {
			assert vars.get(i) != null;
			assert values.get(i) != null;
			logger.debug(vars.get(i) + " -> " + trv.visit(values.get(i)));
			variableMap.put(vars.get(i), trv.visit(values.get(i)));
		}
		trv.addMap(variableMap);
		Rule newRule = visit(calledMacro.getRuleBody());
		trv.removeMap(variableMap);
		rename = false;
		return newRule;
	}

	@Override
	public String getCode() {
		return "MCR";
	}
}
