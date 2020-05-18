package org.asmeta.toyices;

import static org.asmeta.toyices.Utils.parseDomainNameOrDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.basicterms.impl.VariableTermImpl;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

public class RuleVisitor extends org.asmeta.parser.util.ReflectiveVisitor<String> {
	//private static Logger logger = LogManager.getLogger(RuleVisitor.class.getName());
	private TermVisitor tv;
	public YicesModel yicesModel;
	Map<String, ArrayList<String>> updateConds;
	Map<String, ArrayList<String>> locationUpdates;
	private Stack<String> conds;
	private Map<String, Rule> agentRules;
	private int chooseVarCounter = 0;
	private Map<VariableTerm, String> chooseVarMap = new HashMap<VariableTerm, String>();
	ArrayList<String[]> chooseVarDeclarations = new ArrayList<String[]>();
	//public Map<String, String> chooseVarFormulae = new HashMap<String, String>();
	private Map<String, String> domainWithoutUndef;
	
	//needed for refinement proof
	public LinkedHashMap<String, Domain> chooseVariablesDomains;

	public RuleVisitor(YicesModel yicesModel, Map<String, Rule> agentRules, Map<String, String> domainWithoutUndef) {
		this.yicesModel = yicesModel;
		tv = new TermVisitor(yicesModel);
		updateConds = new HashMap<String, ArrayList<String>>();
		locationUpdates = new HashMap<String, ArrayList<String>>();
		conds = new Stack<String>();
		this.agentRules = agentRules;
		this.domainWithoutUndef = domainWithoutUndef;
		chooseVariablesDomains = new LinkedHashMap<String, Domain>();
	}

	public String visit(BlockRule blockRule) {
		List<Rule> rules = blockRule.getRules();
		List<String> operands = new ArrayList<String>();
		for(int i = 0; i < rules.size(); i++) {
			operands.add(visit(rules.get(i)));
		}
		return yicesModel.and(operands);
	}

	public String visitVariablesList(List<VariableTerm> variables) {
		StringBuilder sb = new StringBuilder("(");
		for(int i = 0; i < variables.size() - 1; i++) {
			VariableTerm var = variables.get(i);
			String varName = var.getName();
			String varDomainName = var.getDomain().getName();
			sb.append(varName + "::" + varDomainName + " ");
		}
		VariableTerm var = variables.get(variables.size() - 1);
		sb.append(var.getName() + "::" + var.getDomain().getName() + ")");
		return sb.toString();
	}

	public String visit(ForallRule forallRule) {
		List<String> operands = new ArrayList<String>();
		ArrayList<String[]> valuesCombinations = getValuesCombinations(forallRule.getVariable());
		for(String[] values: valuesCombinations) {
			setVarsValues(forallRule.getVariable(), values);
			String guard = tv.visit(forallRule.getGuard());
			if(!guard.equals("true")) {
				conds.push(guard);
			}
			String doRule = visit(forallRule.getDoRule());
			if(!guard.equals("true")) {
				doRule = yicesModel.implies(guard, doRule);
				conds.pop();
			}
			operands.add(doRule);
		}
		String forallYices = yicesModel.and(operands);
		return forallYices;
	}

	private void setVarsValues(List<VariableTerm> vars, String[] values) {
		for(int i = 0; i < vars.size(); i++) {
			String varName = vars.get(i).getName();
			tv.varValues.put(varName, values[i]);
		}
	}

	public ArrayList<String[]> getValuesCombinations(List<VariableTerm> vars) {
		List<Domain> domains = new ArrayList<Domain>();
		for(VariableTerm var: vars) {
			domains.add(var.getDomain());
		}
		return yicesModel.getValuesCombinationsFromDoms(domains);
	}

	private String buildChooseRuleCanFire(List<VariableTerm> varTerms, Term guard) {
		if(tv.visit(guard).equals("true")) {
			return "true";
		}
		ArrayList<String[]> valuesCombinations = getValuesCombinations(varTerms);
		ArrayList<String> orOperands = new ArrayList<String>();
		for(String[] values: valuesCombinations) {
			for(int i = 0; i < varTerms.size(); i++) {
				tv.varValues.put(varTerms.get(i).getName(), values[i]);
			}
			orOperands.add(tv.visit(guard));
		}
		return yicesModel.or(orOperands);
	}

	/**
	 * ASM:
	 * choose $x1 in D1, ..., $xn in Dn with cond($x1, ..., $xn) do
	 *         R[$x1, ..., $xn]
	 *    [ifnone
	 *         R_other]
	 * 
	 * Yices:
	 * if (exists $x1 in D1, ..., $xn in Dn : cond($x1, ..., $xn)) then
	 *    (exists $x1 in D1, ..., $xn in Dn : cond($x1, ..., $xn) &
	 *                                         visit(R[$x1, ..., $xn]) )
	 * else
	 *  visit(R_other)
	 * 
	 * @param chooseRule
	 * @return the yices formulas as string
	 * @throws Exception
	 */
	public String visit(ChooseRule chooseRule) throws Exception {
		assert chooseWithVariables;
		if(chooseWithVariables) {
			return chooseVersionWithVariables(chooseRule);
		}
		else {
			//TODO: remove it because it does not work (or fix it in order to make it work)
			//without variables
			//this version does not always work, because the wrong
			//conditions are added to the computation of the unchanged functions
			//Possible solution: in the unchanged functions, we add as conditions
			//the updates, not the guards (probably it does not work)
			//e' anche sbagliato fare lo xor sugli aggiornamenti. e'
			//possibile che un aggiornamento scatti (o valga) per altri motivi
			return chooseVersionWithoutVariables(chooseRule);
		}
	}

	public static boolean chooseWithVariables = true; 

	private String chooseVersionWithoutVariables(ChooseRule chooseRule) {
		List<VariableTerm> variables = chooseRule.getVariable();
		String chooseBodyCanFire = buildChooseRuleCanFire(variables, chooseRule.getGuard());
		Term guard = chooseRule.getGuard();
		ArrayList<String[]> valuesCombinations = getValuesCombinations(variables);
		ArrayList<String> orOperands = new ArrayList<String>();
		for(String[] values: valuesCombinations) {
			for(int i = 0; i < variables.size(); i++) {
				tv.varValues.put(variables.get(i).getName(), values[i]);
			}
			String guardString = tv.visit(guard);
			conds.push(guardString);
			String ruleString = visit(chooseRule.getDoRule());
			conds.pop();
			orOperands.add(yicesModel.and(guardString, ruleString));
		}
		Rule ifNoneRule = chooseRule.getIfnone();
		String choose;
		String guardAndRule = yicesModel.xor(orOperands);
		if(ifNoneRule != null) {
			conds.push(yicesModel.not(chooseBodyCanFire));
			String ifNone = visit(ifNoneRule);
			conds.pop();
			choose = yicesModel.ifThenElse(chooseBodyCanFire, guardAndRule, ifNone);			
		}
		else {
			choose = yicesModel.implies(chooseBodyCanFire, guardAndRule);
		}
		return choose;
	}

	private String chooseVersionWithVariables(ChooseRule chooseRule) {
		List<VariableTerm> variables = chooseRule.getVariable();
		String chooseBodyCanFire = buildChooseRuleCanFire(variables, chooseRule.getGuard());
		for(VariableTerm var: variables) {
			createChooseVariable(var);
		}
		String guard = tv.visit(chooseRule.getGuard());
		conds.push(guard);
		ArrayList<String> operands = new ArrayList<String>();
		String rule = visit(chooseRule.getDoRule());
		operands.add(rule);
		conds.pop();
		operands.add(guard);
		Rule ifNoneRule = chooseRule.getIfnone();
		String choose;

		//versione con exists
		String guardAndRule = yicesModel.and(operands);
		if(ifNoneRule != null) {
			conds.push(yicesModel.not(guard));
			String ifNone = visit(ifNoneRule);
			conds.pop();
			choose = yicesModel.ifThenElse(chooseBodyCanFire, guardAndRule, ifNone);			
		}
		else {
			choose = yicesModel.implies(chooseBodyCanFire, guardAndRule);
		}
		return choose;
	}

	private void createChooseVariable(VariableTerm var) {
		String varName = var.getName();
		String newVarName = varName.substring(1) + chooseVarCounter + "State" + Utils.currentStateSymbol;
		tv.varValues.put(varName, newVarName);
		chooseVarCounter++;
		chooseVarMap.put(var, newVarName);
		Domain domain = var.getDomain();
		chooseVarDeclarations.add(new String[]{newVarName, getDomainNameForFunctionDomain(domain)});

		chooseVariablesDomains.put(newVarName, var.getDomain());
	}

	public String visit(MacroCallRule macroCallRule) {
		MacroDeclaration calledMacro = macroCallRule.getCalledMacro();
		Iterator<Term> paramsIterator = macroCallRule.getParameters().iterator();
		assert macroCallRule.getParameters().size() == calledMacro.getVariable().size();
		Set<String> vars = new HashSet<String>();
		for(Term v: calledMacro.getVariable()) {
			//assert paramsIterator.hasNext();
			String var = tv.visit(v);
			vars.add(var);
			Term varValue = paramsIterator.next();
			tv.varValuesTerms.put(var, varValue);
			tv.varValues.put(var, tv.visit(varValue));
		}
		String calledMacroYices = visit(calledMacro.getRuleBody());
		for(String var: vars) {
			tv.varValues.remove(var);
		}
		return calledMacroYices;
	}

	public String visit(ConditionalRule conditionalRule) throws Exception {
		String cond = tv.visit(conditionalRule.getGuard());
		conds.push(cond);
		String thenBranch = visit(conditionalRule.getThenRule());
		conds.pop();
		Rule elseRule = conditionalRule.getElseRule();
		String condRuleYices;
		if(elseRule != null) {
			String notCond = yicesModel.not(cond);
			conds.push(notCond);
			String elseBranch = visit(elseRule);
			conds.pop();
			condRuleYices = yicesModel.ifThenElse(cond, thenBranch, elseBranch);
		}
		else {
			condRuleYices = yicesModel.implies(cond, thenBranch);
		}
		return condRuleYices;
	}

	public String visit(CaseRule caseRule) throws Exception {
		String term = tv.visit(caseRule.getTerm());
		List<Term> cases = caseRule.getCaseTerm();
		List<Rule> branches = caseRule.getCaseBranches();
		List<String> implies = new ArrayList<String>();
		List<String> neqs = new ArrayList<String>();
		for(int i = 0; i < branches.size(); i++) {
			String value = tv.visit(cases.get(i));
			String eqCase = yicesModel.eq(term, value);
			conds.push(eqCase);
			implies.add(yicesModel.implies(eqCase, visit(branches.get(i))));
			conds.pop();
			neqs.add(yicesModel.neq(term, value));//for the otherwise
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if(otherwise != null) {
			String neqsAnd = yicesModel.and(neqs);
			conds.push(neqsAnd);
			implies.add(yicesModel.implies(neqsAnd, visit(otherwise)));
			conds.pop();
		}
		return yicesModel.and(implies);
	}

	public String visit(LetRule letRule) {
		List<VariableTerm> vars = letRule.getVariable();
		Iterator<Term> initExprIterator = letRule.getInitExpression().iterator();
		assert vars.size() == letRule.getInitExpression().size();
		for(VariableTerm v: vars) {
			String varName = v.getName();
			String init = tv.visit(initExprIterator.next());
			tv.varValues.put(varName, init);
		}
		String inRuleYices = visit(letRule.getInRule());
		for(VariableTerm v: vars) {
			tv.varValues.remove(v.getName());
		}
		return inRuleYices;
	}

	public String visit(UpdateRule updateRule) throws Exception {
		LocationTerm locTerm = null;
		if(updateRule.getLocation() instanceof VariableTermImpl) {
			VariableTermImpl v = (VariableTermImpl)updateRule.getLocation();
			locTerm = (LocationTerm) tv.varValuesTerms.get(v.getName());
		}
		else {
			assert updateRule.getLocation() instanceof LocationTerm: updateRule.getLocation() + " is not a location term.\nIt is a " + updateRule.getLocation().getClass().getSimpleName();
			locTerm = (LocationTerm)updateRule.getLocation();
		}
		String location = tv.visitLocationUpdateRule(locTerm);
		String updatingTerm = tv.visit(updateRule.getUpdatingTerm());
		String updateRuleYices = yicesModel.eq(location, updatingTerm);

		if(chooseWithVariables) {
			computeUnchangedLocationsWithChooseVariables(locTerm, location);
		}
		else {
			computeUnchangedLocationsWithoutChooseVariables(locTerm, location, updatingTerm);
		}
		return updateRuleYices;
	}

	private void computeUnchangedLocationsWithChooseVariables(LocationTerm locTerm, String location) {
		Function function = locTerm.getFunction();
		if(function.getArity() > 0) {
			List<Domain> argDomains = Utils.splitDomains(function.getDomain());
			ArrayList<String[]> values = yicesModel.getValuesCombinationsFromDoms(argDomains);
			List<String> args = new ArrayList<String>();
			for(Term arg: locTerm.getArguments().getTerms()) {
				args.add(tv.visit(arg));
			}
			String functionName = function.getName();
			for(String[] value: values) {
				List<String> operands = new ArrayList<String>();
				for(int i = 0; i < value.length; i++) {
					operands.add(yicesModel.eq(args.get(i), value[i]));
				}
				location = Utils.location(functionName, Utils.nextStateSymbol, value);
				ArrayList<String> condsList;
				if(!updateConds.containsKey(location)) {
					condsList = new ArrayList<String>();
					updateConds.put(location, condsList);
				}
				else {
					condsList = updateConds.get(location);
				}
				conds.push(yicesModel.and(operands));
				condsList.add(yicesModel.and(conds));
				//System.err.println("adding\n" + yicesModel.and(conds) + "\nfor " + location);
				conds.pop();
			}
		}
		else {
			ArrayList<String> condsList;
			if(!updateConds.containsKey(location)) {
				condsList = new ArrayList<String>();
				updateConds.put(location, condsList);
			}
			else {
				condsList = updateConds.get(location);
			}
			condsList.add(yicesModel.and(conds));
		}
	}

	private void computeUnchangedLocationsWithoutChooseVariables(LocationTerm locTerm, String location, String updatingTerm) {
		Function function = locTerm.getFunction();
		if(function.getArity() > 0) {
			List<Domain> argDomains = Utils.splitDomains(function.getDomain());
			ArrayList<String[]> values = yicesModel.getValuesCombinationsFromDoms(argDomains);
			List<String> args = new ArrayList<String>();
			for(Term arg: locTerm.getArguments().getTerms()) {
				args.add(tv.visit(arg));
			}
			String functionName = function.getName();
			for(String[] value: values) {
				location = Utils.location(functionName, Utils.nextStateSymbol, value);
				ArrayList<String> updateList;
				if(!locationUpdates.containsKey(location)) {
					updateList = new ArrayList<String>();
					locationUpdates.put(location, updateList);
				}
				else {
					updateList = locationUpdates.get(location);
				}
				updateList.add(yicesModel.eq(location, updatingTerm));
			}
		}
		else {
			ArrayList<String> updateList;
			if(!locationUpdates.containsKey(location)) {
				updateList = new ArrayList<String>();
				locationUpdates.put(location, updateList);
			}
			else {
				updateList = locationUpdates.get(location);
			}
			updateList.add(yicesModel.eq(location, updatingTerm));
		}
	}

	public String visit(TermAsRule termAsRule) {
		Term term = termAsRule.getTerm();
		if(term instanceof FunctionTerm && ((FunctionTerm)term).getFunction().getName().equals("program")) {
			List<Term> args = ((FunctionTerm)term).getArguments().getTerms();
			assert args.size() == 1;
			Term arg = args.get(0);
			Domain domain = arg.getDomain();
			String agentDomain;
			if(domain instanceof AgentDomain) {
				agentDomain = "Agent";
			}
			else if(domain instanceof ConcreteDomain && (((ConcreteDomain)domain).getTypeDomain() instanceof AgentDomain)) {
				agentDomain = domain.getName();
			}
			else {
				throw new Error("Agent domain not found");
			}
			String agent = tv.visit(arg);
			tv.selfAgent = agent;
			String ruleAgent = visit(agentRules.get(agentDomain));
			tv.selfAgent = null;
			return ruleAgent;
		}
		throw new Error("TermAsRule not expected.");
	}

	public String visit(Rule rule) {
		return invokeMethod(rule, "visit");
	}

	public String visit(SkipRule skipRule) {
		return "true";
	}

	public String getDomainNameForFunctionDomain(Domain domain) {
		if(domainWithoutUndef.containsKey(domain.getName())) {
			return domainWithoutUndef.get(domain.getName());
		}
		else {
			return parseDomainNameOrDef(domain, yicesModel);
		}
	}
}