package org.asmeta.flattener.nesting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.term.Utils;
import org.asmeta.flattener.util.StdlFunction;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class RemoveNestingFlattener extends ReflectiveVisitor<Void> implements AsmetaFlattener {
	static final Logger logger = Logger.getLogger(RemoveNestingFlattener.class);
	private StdlFunction stdlFunction;
	private RuleFactory ruleFact;
	private Stack<Term> guardsStack;
	private ArrayList<Term> currentGuard;
	private Map<ArrayList<Term>, ArrayList<Rule>> guardsRulesMap;
	Asm asm;

	public RemoveNestingFlattener(Asm asm) {
		stdlFunction = new StdlFunction(asm);
		ruleFact = new RuleFactory();
		guardsStack = new Stack<>();
		guardsRulesMap = new HashMap<ArrayList<Term>, ArrayList<Rule>>();
		currentGuard = new ArrayList<Term>();
		// visit main rule of asm and load the information on guardsRulesMap
		this.asm = asm;
	}

	private void addRule(Rule rule) {
		ArrayList<Rule> rulesList = guardsRulesMap.get(currentGuard);
		if (rulesList == null) {
			// initialize the list
			rulesList = new ArrayList<Rule>();
		}
		rulesList.add(rule);
		guardsRulesMap.put((ArrayList<Term>) currentGuard.clone(), rulesList);
	}

	// it creates the current guard from the union of the nested guards (stored into
	// the stack)
	private void calcCurrentGuard() {
		currentGuard.clear();
		for (Term t : guardsStack) {
			currentGuard.add(t);
		}
	}

	public static boolean PROPAGATE_EQ = false;
	
	private Rule createNewRule() {
		// create a PAR of IFs
		BlockRule par = ruleFact.createBlockRule();
		// assert guardsRulesMap.keySet().size() > 0;
		List<Rule> parRules = par.getRules();
		for (ArrayList<Term> bt : guardsRulesMap.keySet()) {
			// set updates without condition
			ArrayList<Rule> rulesList = guardsRulesMap.get(bt);
			if (bt.size() == 0) {
				if (rulesList.size() == 1) {
					parRules.add(rulesList.get(0));
				} else {
					// if there are more rules
					BlockRule par2 = ruleFact.createBlockRule();
					List<Rule> par2rules = par2.getRules();
					for (Rule r : rulesList) {
						par2rules.add(r);
					}
					parRules.add(par2);
				}
			} else {
				ConditionalRule condRule = ruleFact.createConditionalRule();
				// set guard for every conditional rule, it's created by combining(and) each
				// "nested" guard
				ArrayList<Term> newList;
				if(PROPAGATE_EQ) {
					newList = Utils.propagateEq(bt);
				}
				else {
					newList = bt;
				}
				condRule.setGuard(stdlFunction.and(newList));
				// set rule/rules
				if (rulesList.size() == 1) {
					// if there is a single rule
					condRule.setThenRule(rulesList.get(0));
				} else {
					// if there are more rules
					BlockRule par2 = ruleFact.createBlockRule();
					List<Rule> par2rules = par2.getRules();
					for (Rule r : rulesList) {
						par2rules.add(r);
					}
					condRule.setThenRule(par2);
				}
				// add every IF to the PAR
				parRules.add(condRule);
			}
		}
		if (parRules.size() > 1) {
			return par;
		} else if (parRules.size() == 1) {
			return parRules.get(0);
		}
		return null;
	}

	@Override
	public Asm flattenASM() {
		/*
		 * MacroDeclaration mainrule = asm.getMainrule(); Rule mainRuleBody =
		 * mainrule.getRuleBody(); visit(mainRuleBody); Rule newRule = createNewRule();
		 * mainrule.setRuleBody(newRule); return asm;
		 */
		List<RuleDeclaration> rules = asm.getBodySection().getRuleDeclaration();
		for (RuleDeclaration r : rules) {
			logger.debug("visiting " + r.getName());
			visit(r.getRuleBody());
			Rule newRule = createNewRule();
			if (r != null) {
				r.setRuleBody(newRule);
			}
			guardsRulesMap.clear();
			assert guardsStack.isEmpty();
			assert currentGuard.isEmpty();
		}
		return asm;
	}

	public Map<ArrayList<Term>, ArrayList<Rule>> getGuardsRulesMap() {
		return guardsRulesMap;
	}

	public Void visit(BlockRule blockRule) {
		for (Rule rule : blockRule.getRules()) {
			if (rule != null) {
				this.visit(rule);
			}
		}
		return null;
	}

	public Void visit(CaseRule caseRule) {
		// flattening CaseBranches
		RemoveNestingFlattener rnf;
		CaseRule newCaseRule = ruleFact.createCaseRule();
		List<Rule> branches = newCaseRule.getCaseBranches();
		for (Rule rule : caseRule.getCaseBranches()) {
			assert rule != null;
			rnf = new RemoveNestingFlattener(asm);
			rnf.visit(rule);
			branches.add(rnf.createNewRule());
		}

		// flattening OtherviseBranch
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			rnf = new RemoveNestingFlattener(asm);
			rnf.visit(otherwise);
			newCaseRule.setOtherwiseBranch(rnf.createNewRule());
		}
		newCaseRule.setTerm(caseRule.getTerm());
		newCaseRule.getCaseTerm().addAll(caseRule.getCaseTerm());

		// add to map
		addRule(newCaseRule);
		return null;
	}

	public Void visit(ChooseRule chooseRule) {
		Rule doRule = chooseRule.getDoRule();

		assert doRule != null;

		// flattening doRule
		RemoveNestingFlattener rnf = new RemoveNestingFlattener(asm);
		rnf.visit(doRule);
		Rule r = rnf.createNewRule();

		// create a ChooseRule with the flattened doRule inside
		ChooseRule cr = ruleFact.createChooseRule();
		cr.setDoRule(r);
		cr.setGuard(chooseRule.getGuard());
		cr.getVariable().addAll(chooseRule.getVariable());
		cr.getRanges().addAll(chooseRule.getRanges());

		// add to map
		addRule(cr);

		return null;
	}

	public Void visit(ConditionalRule conditionalRule) {
		guardsStack.push(conditionalRule.getGuard());
		@SuppressWarnings("unchecked")
		ArrayList<Term> oldGuard = (ArrayList<Term>) currentGuard.clone();
		this.calcCurrentGuard();

		// then branch
		Rule thenRule = conditionalRule.getThenRule();
		// check if then rule is present
		assert thenRule != null;

		this.visit(thenRule);

		// else branch
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			Term pop = guardsStack.pop();
			assert pop != null : "The stack is empty";
			guardsStack.push(stdlFunction.not(pop));
			this.calcCurrentGuard();
			this.visit(elseRule);
		}

		guardsStack.pop();
		// restore the guard that it was present before this conditional rule
		// currentGuard = (ArrayList<Term>) oldGuard.clone();
		currentGuard = oldGuard;
		return null;
	}

	public Void visit(ExtendRule extendRule) {
		Rule doRule = extendRule.getDoRule();
		if (doRule != null) {
			this.visit(doRule);
		}
		return null;
	}

	public Void visit(ForallRule forallRule) {
		Rule doRule = forallRule.getDoRule();
		// flattening doRule rule
		RemoveNestingFlattener rnf = new RemoveNestingFlattener(asm);
		rnf.visit(doRule);
		// create a par of visited(and flattened) rules
		Rule r = rnf.createNewRule();

		// create a forall with the flattened doRule inside
		ForallRule fr = ruleFact.createForallRule();
		fr.setDoRule(r);
		fr.setGuard(forallRule.getGuard());
		fr.getVariable().addAll(forallRule.getVariable());
		fr.getRanges().addAll(forallRule.getRanges());
		// add to map
		addRule(fr);
		return null;
	}

	public Void visit(LetRule letRule) {
		Rule inRule = letRule.getInRule();

		assert inRule != null;

		// flattening inRule
		RemoveNestingFlattener rnf = new RemoveNestingFlattener(asm);
		rnf.visit(inRule);
		Rule r = rnf.createNewRule();

		// create a LetRule with the flattened inRule inside
		LetRule cr = ruleFact.createLetRule();
		cr.getInitExpression().addAll(letRule.getInitExpression());
		cr.getVariable().addAll(letRule.getVariable());
		cr.setInRule(r);

		addRule(cr);
		return null;
	}

	public Void visit(MacroCallRule macroCallRule) {
		addRule(macroCallRule);
		return null;
	}

	public Void visit(SeqRule seqRule) {
		SeqRule newSeqRule = ruleFact.createSeqRule();
		for (Rule rule : seqRule.getRules()) {
			RemoveNestingFlattener rnf = new RemoveNestingFlattener(asm);
			rnf.visit(rule);
			Rule r = rnf.createNewRule();
			newSeqRule.getRules().add(r);
		}
		addRule(newSeqRule);
		return null;
	}

	public Void visit(SkipRule skipRule) {
		addRule(skipRule);
		return null;
	}

	public Void visit(TermAsRule termAsRule) {
		addRule(termAsRule);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Void visit(UpdateRule updateRule) {
		addRule(updateRule);
		// show data inside guardsRulesMap every time there is an update
		// System.out.println(guardsRulesMap);
		return null;
	}

	@Override
	public String getCode() {
		return "NR";
	}

	public StdlFunction getStdlFunction() {
		return stdlFunction;
	}
}
