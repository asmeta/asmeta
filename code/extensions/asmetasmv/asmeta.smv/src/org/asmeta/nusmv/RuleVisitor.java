package org.asmeta.nusmv;

import static org.asmeta.nusmv.util.Util.and;
import static org.asmeta.nusmv.util.Util.checkRuleDomain;
import static org.asmeta.nusmv.util.Util.getDomainName;
import static org.asmeta.nusmv.util.Util.implies;
import static org.asmeta.nusmv.util.Util.not;
import static org.asmeta.nusmv.util.Util.notEquals;
import static org.asmeta.nusmv.util.Util.or;
import static org.asmeta.nusmv.util.Util.trueString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.asmeta.nusmv.util.ConditionStack;
import org.asmeta.nusmv.util.Util;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.IRuleVisitor;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class RuleVisitor extends ReflectiveVisitor<Void> implements IRuleVisitor<Void> {
	private MapVisitor mv;
	private Environment env;
	private TermVisitor tp;
	private Stack<String> conditions;

	private int chooseCounter;
	private Map<VariableTerm, String> chooseVar;
	private Map<ChooseRule, String> chooseInstance;
	private Stack<String> chooseStack;//ha un senso metterlo qui, ma ora non mi ricordo perche'. Credo che sia dovuto ai casi con choose innestate

	RuleVisitor(MapVisitor mapVisitor) {
		mv = mapVisitor;
		env = mapVisitor.env;
		tp = mapVisitor.tp;
		chooseVar = new HashMap<VariableTerm, String>();
		chooseCounter = 0;
		chooseInstance = new HashMap<ChooseRule, String>();
		chooseStack = new Stack<String>();
		conditions = mapVisitor.getConditions();
	}

	/**
	 * Visita la block rule. Ogni regola viene visitata singolarmente.
	 * 
	 * @param blockRule
	 */
	@Override
	public Void visit(BlockRule blockRule) {
		for (Rule rule: blockRule.getRules()) {
			visit(rule);
		}
		return null;
	}

	/**
	 * Visit the forall rule.
	 * 
	 * @param forallRule the forall rule
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	@Override
	public Void visit(ForallRule forallRule) throws AsmNotSupportedException {
		String cond;
		List<String> conds = new ArrayList<String>();		
		List<VariableTerm> vars = forallRule.getVariable();
		Term guard = forallRule.getGuard();
		Rule doRule = forallRule.getDoRule();
		ArrayList<String[]> values = new ArrayList<String[]>();
		Map<ChooseRule, String> oldChooseInstance;
		Map<VariableTerm, String> oldChooseVar;
		combineValues(vars, 0, values, new String[vars.size()]);
		for(String[] value: values) {
			env.setVarsValues(vars, value);
			//Map<ChooseRule, String> oldChooseInstance = chooseInstance;
			//Map<VariableTerm, String> oldChooseVar = chooseVar;
			oldChooseInstance = chooseInstance;
			oldChooseVar = chooseVar;
			chooseInstance = new HashMap<ChooseRule, String>();
			chooseVar = new HashMap<VariableTerm, String>();
			
			cond = tp.visit(guard);
			updateVisitRestore(cond, doRule);
			chooseInstance = oldChooseInstance;
			chooseVar = oldChooseVar;
			conds.add(cond);
		}

		if(AsmetaSMVOptions.doAsmetaMA) {
			//AsmetaMA: Every forall set is not always empty
			//AsmetaMA: Every forall set is always not empty
			if (mv.forallRuleSetIsEmpty.containsKey(forallRule)) {
				mv.forallRuleSetIsEmpty.get(forallRule).add(and(or(conds), and(conditions)));
			}
			else {
				List<String> condsTemp = new ArrayList<String>();
				condsTemp.add(and(or(conds), and(conditions)));
				mv.forallRuleSetIsEmpty.put(forallRule, condsTemp);
			}
		}
		return null;
	}

	//TODO: quando la condizione e' "true", si puo' semplificare la traduzione.
	/**
	 * Visit the choose rule.
	 * 
	 * @param chooseRule the choose rule
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public Void visit(ChooseRule chooseRule){
		if(env.inSeqRule) {
			throw new AsmNotSupportedException("Chooserule in seqrule non supportato.");
		}
		List<VariableTerm> vars = chooseRule.getVariable();
		Term cond = chooseRule.getGuard();
		Rule doRule = chooseRule.getDoRule();
		Rule ifNoneRule = chooseRule.getIfnone();
		ArrayList<String[]> values = new ArrayList<String[]>();
		String condStr, value, ifNoneCondStr, varName;
		Stack<String> conds = new Stack<String>();
		Stack<String> ifNoneCond = new Stack<String>();
		Stack<String> chooseCond = new Stack<String>();
		String chooseName = "";
		//e' la prima volta che incontriamo questa choose rule. Se sono in una
		//forall rule, posso incontrare la choose rule molte volte.
		if (!chooseInstance.containsKey(chooseRule)) {
			chooseName = "chooseRule" + chooseCounter;
			chooseInstance.put(chooseRule, chooseName);
			for (VariableTerm var : vars) {
				for(VariableTerm oldVar: chooseVar.keySet()) {
					if(oldVar.getName().equals(var.getName())) {
						System.out.println("WARNING: in your ASM model there " +
						"are two choose rules that have a logic variable\n" +
						"with the same name " + oldVar.getName() +
						". In order to make the NuSMV code more readable, we " +
						"suggest\nyou to change the name of one of them.");
					}
				}

				//Le variabili della choose rule diventano variabili in NuSMV.
				//Poiche' ci potrebbero essere due choose rule con un nome di
				//variabile uguale, nel nome della variabile NuSMV dobbiamo
				//aggiungere l'identificativo numerico della choose rule.
				varName = "var_" + var.getName() + "_" + chooseCounter;
				chooseVar.put(var, varName);
				mv.nusmvNameToLocation.put(varName, varName + " (variabile " +
						var.getName() + " della choose rule)");
				//varsDecl.put(varName, domainSmv.get(getDomainName(var.getDomain())));
				mv.chooseVarsDecl.put(varName, mv.domainSmv.get(getDomainName(var.getDomain())));
				//env.usedLocation.add(varName);
			}
			chooseCounter++;
		}
		else {
			chooseName = chooseInstance.get(chooseRule);//il nome e' quello che e' stato impostato in precedenza
		}
		VariableTerm var;
		combineValues(vars, 0, values, new String[vars.size()]);//calcola tutte le combinazioni possibili dei valori delle variabili
		for (int i = 0; i < values.size(); i++) {
			for (int j = 0; j < vars.size(); j++) {
				var = vars.get(j);
				value = values.get(i)[j];
				env.varsValues.put(var, value);//associa alla variabile var il valore value
				//env.setVarValue(var, value);//associa alla variabile var il valore value
				chooseStack.push(Util.equals(chooseVar.get(var), value));
			}
			conds.addAll(chooseStack);
			condStr = tp.visit(cond);//la condizione con i valori attuali
			conds.push(condStr);
			updateVisitRestore(conds, doRule);//visita la doRule
			ifNoneCond.push(not(condStr));
			chooseCond.push(and(conds));
			conds.clear();
			for (int j = 0; j < vars.size(); j++) {
				chooseStack.pop();
			}
		}

		if(AsmetaSMVOptions.doAsmetaMA) {
			//AsmetaMA: Every choose set is not always empty
			//AsmetaMA: Every choose set is always not empty
			if (!mv.chooseRuleSetIsEmpty.containsKey(chooseRule)) {
				mv.chooseRuleSetIsEmpty.put(chooseRule, new ArrayList<String[]>());
			}
			mv.chooseRuleSetIsEmpty.get(chooseRule).add(new String[]{and(conditions), or(chooseCond)});
		}

		ifNoneCondStr = and(ifNoneCond);
		if (ifNoneRule != null) {
			updateVisitRestore(ifNoneCondStr, ifNoneRule);
		}
		chooseCond.push(ifNoneCondStr);
		condStr = or(chooseCond);
		if(mv.invars.containsKey(chooseName)) {
			//System.out.println("inVar.containsKey("+chooseName+")");
			//inVar.put(chooseName, and(inVar.get(chooseName), condStr));
			//da verificare: una choose di una choose va con l'and o con l'or?
			mv.invars.put(chooseName, or(mv.invars.get(chooseName), condStr));
		}
		else {
			mv.invars.put(chooseName, condStr);
		}
		return null;
	}

	/**
	 * Visit a macro call rule.
	 * 
	 * @param rule the macro call rule
	 */
	@Override
	public Void visit(MacroCallRule macroCallRule) {
		MacroDeclaration calledMacro = macroCallRule.getCalledMacro();
		Iterator<Term> params = macroCallRule.getParameters().iterator();
		for (VariableTerm var: calledMacro.getVariable()) {
			env.varsValues.put(var, tp.visit(params.next()));
		}
		visit(calledMacro.getRuleBody());

		if(AsmetaSMVOptions.doAsmetaMA) {
			//AsmetaMA
			//dovrebbe essere questo
			//macroRuleCalled.put(calledMacro, macroRuleCalled.get(calledMacro) + 1);
			//ma per problemi di riferimento deve essere fatta cosi'
			for(MacroDeclaration r: mv.macroRuleCalled.keySet()) {
				if(r.getName().equals(calledMacro.getName())) {
					mv.macroRuleCalled.put(r, mv.macroRuleCalled.get(r) + 1);
				}
			}
		}
		return null;
	}

	/**
	 * Visita una conditional rule.
	 * Si aggiunge sullo stack la condizione e si visita il ramo then.
	 * Poi si aggiunge allo stack la condizione negata e si visita il ramo else.
	 * 
	 * @param rule the conditional rule
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public Void visit(ConditionalRule conditionalRule) {
		String condStr = tp.visit(conditionalRule.getGuard());
		//System.err.println("condRule Guard = " + condStr);
		updateVisitRestore(condStr, conditionalRule.getThenRule());
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			updateVisitRestore(not(condStr), elseRule);
		}

		if(AsmetaSMVOptions.doAsmetaMA) {
			if (elseRule != null) {
				//AsmetaMA: se c'e' il ramo else la conditional rule e' di sicuro completa
				List<String> conds = new ArrayList<String>();
				conds.add(Util.trueString);
				mv.condRuleIsComplete.put(conditionalRule, conds);
			}
			else {
				//AsmetaMA: cond rule is complete
				//La proprieta' deve essere valutata quando la conditional rule
				//viene effettivamente eseguita.
				//Cond e' vera se la conditional rule e' irraggiugibile
				//lungo questo percorso (and(conditions) e' false).
				String cond = implies(and(conditions), condStr);
				if(mv.condRuleIsComplete.containsKey(conditionalRule)) {
					mv.condRuleIsComplete.get(conditionalRule).add(cond);
				}
				else {
					List<String> conds = new ArrayList<String>();
					conds.add(cond);
					mv.condRuleIsComplete.put(conditionalRule, conds);
				}
			}
			//AsmetaMA: CondRuleEvalToTrue
			String reachCond = and(conditions);//condizione d'arrivo
			String condThen = and(reachCond, condStr);
			String condElse = and(reachCond, not(condStr));
			if(mv.condRuleEvalToTrueThen.containsKey(conditionalRule)) {
				mv.condRuleEvalToTrueThen.get(conditionalRule).add(condThen);
				if(conditionalRule.getElseRule() != null) {
					mv.condRuleEvalToTrueElse.get(conditionalRule).add(condThen);
				}
			}
			else {
				List<String> condsThen = new ArrayList<String>();
				condsThen.add(condThen);
				mv.condRuleEvalToTrueThen.put(conditionalRule, condsThen);
				if(conditionalRule.getElseRule() != null) {
					List<String> condsElse = new ArrayList<String>();
					condsElse.add(condElse);
					mv.condRuleEvalToTrueElse.put(conditionalRule, condsElse);
				}
			}
		}
		return null;
	}

	/**
	 * Visita della case rule. Ogni ramo viene visitato aggiungendo la
	 * condizione opportuna nello stack delle condizioni.
	 * 
	 * @param caseRule the case rule
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public Void visit(CaseRule caseRule){
		String location = tp.visit(caseRule.getTerm());
		Iterator<Term> caseTerms = caseRule.getCaseTerm().iterator();
		Rule otherRule = caseRule.getOtherwiseBranch();
		Stack<String> condsOther = new Stack<String>();
		Stack<String> conds = new Stack<String>();
		String value;
		for (Rule branch: caseRule.getCaseBranches()) {
			value = tp.visit(caseTerms.next());
			updateVisitRestore(Util.equals(location, value), branch);
			condsOther.push(notEquals(location, value));
			conds.push(Util.equals(location, value));
		}
		if (otherRule != null) {
			updateVisitRestore(condsOther, otherRule);
		}

		if(AsmetaSMVOptions.doAsmetaMA) {
			if (otherRule != null) {
				//AsmetaMA: caseRule is complete
				List<String> newConds = new ArrayList<String>();
				newConds.add(trueString);
				mv.caseRuleIsComplete.put(caseRule, newConds);
			}
			else {
				//AsmetaMA: caseRule is complete
				//La proprieta' deve essere valutata quando la case rule viene
				//effettivamente eseguita.
				//Cond e' vera se la case rule e' irraggiugibile
				//lungo questo percorso (and(conditions) e' false).
				String cond = implies(and(conditions), or(conds));
				if(mv.caseRuleIsComplete.containsKey(caseRule)) {
					mv.caseRuleIsComplete.get(caseRule).add(cond);
				}
				else {
					List<String> newConds = new ArrayList<String>();
					newConds.add(cond);
					mv.caseRuleIsComplete.put(caseRule, newConds);
				}
			}
		}
		return null;
	}

	/**
	 * Visit the let rule.
	 * 
	 * @param letRule the let rule
	 * @throws AsmNotSupportedException 
	 */
	@Override
	public Void visit(LetRule letRule) throws AsmNotSupportedException {
		List<VariableTerm> vars = letRule.getVariable();
		ArrayList<String[]> values = new ArrayList<String[]>();
		combineValues(vars, 0, values, new String[vars.size()]);
		for(String[] value: values) {
			//System.err.println(Arrays.toString(vars.toArray()));
			//System.err.println(Arrays.toString(value));
			env.setVarsValues(vars, value);
			Iterator<Term> terms = letRule.getInitExpression().iterator();
			ArrayList<String> conds = new ArrayList<String>();
			for(String v: value) {
				conds.add(Util.equals(v, tp.visit(terms.next())));
			}
			updateVisitRestore(conds, letRule.getInRule());
		}
		return null;
	}

	/**
	 * Visita l'update rule memorizzando nell'updateMap l'aggiornamento.
	 * 
	 * @param updateRule the update rule
	 * @throws Exception 
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public Void visit(UpdateRule updateRule){
		Term loc = updateRule.getLocation();
		String location;
		if(loc instanceof LocationTerm) {
			location = tp.locationName((LocationTerm) loc);
		}
		else {
			location = tp.visit(loc);
		}
		//String location = tp.visit(updateRule.getLocation());
		String value = tp.visit(updateRule.getUpdatingTerm());
		if(value!= null && value.equals("undef")) {
			String undef = mv.getUndefValue(mv.locationDomain.get(location));
			if(undef != null) {
				value = undef;
			}
			else {
				//System.out.println(mv.getUndefValue());
				//System.out.println(mv.locationDomain);
				throw new AsmNotSupportedException("The location " + location + 
					" can not be updated to the undef value, because its " + 
					"codomain " + mv.locationDomain.get(location) + " does " +
					"not have an undef value for NuSMV.");
			}
		}
		if(env.inSeqRule) {
			env.srv.seqRuleUpdate(location, value);
		}
		else {
			mv.updateMap.update(location, conditions, value);
		}
		return null;
	}

	/**
	 * Visita una seq rule.
	 * 
	 * @param seqRule the seq rule
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public Void visit(SeqRule seqRule){
		SeqRuleVisitor srv, oldSrv;
		srv = new SeqRuleVisitor(mv, tp, env);
		oldSrv = env.srv;
		if(oldSrv != null) {
			throw new AsmNotSupportedException("Nested SeqRule are not supported");
		}
		env.srv = srv;
		srv.visit(seqRule);
		env.srv = oldSrv;
		return null;
	}

	/**
	 * Visita una regola generica.
	 * 
	 * @param rule the rule
	 * @throws Exception 
	 */
	@Override
	public Void visit(Rule rule) {
		// visit((Object) rule);
		//System.out.println("visit "+rule);
		invokeMethod(rule, "visit");

		if(AsmetaSMVOptions.doAsmetaMA) {
			//AsmetaMA model advisor: Every rule can eventually fire
			String cond = and(mv.getConditions());
			if(mv.ruleCond.containsKey(rule)) {
				mv.ruleCond.get(rule).add(cond);
			}
			else {
				List<String> conds = new ArrayList<String>();
				conds.add(cond);
				mv.ruleCond.put(rule, conds);
			}
		}
		return null;
	}

	/**
	 * Visita la skip rule.
	 * 
	 * @param skipRule the skip rule
	 */
	@Override
	public Void visit(SkipRule skipRule) {
		//do nothing
		//System.err.println("skip rule " + conditions);
		return null;
	}

	@Override
	public Void visit(ExtendRule extendRule) throws AsmNotSupportedException {
		throw new AsmNotSupportedException("ExtendRule not supported");
	}
	
	@Override
	public Void visit(TermAsRule r){
		tp.visit(r.getTerm());
		return null;
	}

	/**
	 * Combine values.
	 * 
	 * @param vars the vars
	 * @param domains the domains
	 * @param index the index
	 * @param result the result
	 * @param tupla the tupla
	 * @throws AsmNotSupportedException 
	 */
	public void combineValues(List<VariableTerm> vars, List<Domain> domains, int index, ArrayList<String[]> result, String[] tupla) throws AsmNotSupportedException {
		Domain domain = domains.get(index);
		SortedSet<String> elements = (SortedSet<String>) asSet(domain);
		Iterator<String> i = elements.iterator();
		while (i.hasNext()) {
			tupla[index] = i.next();
			if (vars.size() == index + 1) {
				result.add(tupla.clone());
			}
			else {
				combineValues(vars, index + 1, result, tupla);
			}
		}
	}

	/**
	 * Combine values.
	 * 
	 * @param vars the vars
	 * @param index the index
	 * @param result the result
	 * @param tupla the tupla
	 * @throws AsmNotSupportedException 
	 */
	public void combineValues(List<VariableTerm> vars, int index, ArrayList<String[]> result, String[] tupla) throws AsmNotSupportedException {
		VariableTerm var = vars.get(index);
		Domain domain = var.getDomain();
		//System.out.println("var = " + var.getName() + "  domain = " + domain.getName());
		//Controlla se la let � eseguita su una funzione con dominio ammesso o no
		checkRuleDomain(domain.getName());
		SortedSet<String> elements = (SortedSet<String>) asSet(domain);
		for(String element: elements) {
			tupla[index] = element;
			if (vars.size() == index + 1) {
				result.add(tupla.clone());
			}
			else {
				combineValues(vars, index + 1, result, tupla);
			}
		}
	}

	public Object asSet(Domain d) {
		return invokeMethod(d, "asSet");
	}
	
	public SortedSet<String> asSet(IntegerDomain domain){
		return mv.domainSet.get(domain.getName());
	}

	public SortedSet<String> asSet(NaturalDomain domain){
		//System.out.println(domain.getName());
		return mv.domainSet.get(domain.getName());
	}

	public SortedSet<String> asSet(BooleanDomain domain){
		return mv.domainSet.get(domain.getName());
	}

	public SortedSet<String> asSet(EnumTd domain){
		return mv.domainSet.get(getDomainName(domain));
	}

	public SortedSet<String> asSet(AbstractTd domain){
		return mv.domainSet.get(getDomainName(domain));
	}

	public SortedSet<String> asSet(AgentDomain domain){
		return mv.domainSet.get(getDomainName(domain));
	}

	public SortedSet<String> asSet(ConcreteDomain domain){
		return mv.domainSet.get(getDomainName(domain));
	}

	public SortedSet<String> asSet(PowersetDomain pD) {
		return (SortedSet<String>) asSet(pD.getBaseDomain());
	}

	/**
	 * Aggiunge la condizione condition allo stack conditions, visita la 
	 * regola rule e, infine, rimuove la condizione dallo stack.
	 * 
	 * @param condition la condizione da aggiungere allo stack
	 * @param rule la regola da visitare
	 */
	private void updateVisitRestore(String condition, Rule rule) {
		ConditionStack.updateCondition(conditions, condition);
		visit(rule);
		ConditionStack.restoreCondition(conditions);
	}

	/**
	 * Aggiunge la lista di condizioni conds allo stack conditions, visita la 
	 * regola rule e, infine, rimuove la lista di condizioni dallo stack.
	 * 
	 * @param conds la lista di condizioni da aggiungere allo stack
	 * @param rule la regola da visitare
	 */
	private void updateVisitRestore(List<String> conds, Rule rule) {
		ConditionStack.updateCondition(conditions, conds);
		visit(rule);
		ConditionStack.restoreCondition(conditions, conds);
	}
}