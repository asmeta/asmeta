/*
 * 
 */
package org.asmeta.nusmv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.nusmv.util.Util;
import org.asmeta.parser.Defs;
import org.asmeta.simulator.Location;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.NaturalTerm;

/**
 * For mapping both derived and static functions.
 * Indeed, they are mapped to NuSMV in the same way.
 *
 */
public class DerivedVisitor extends org.asmeta.parser.util.ReflectiveVisitor<String> {
	private Node currentNode;
	
	private Environment env;
	Set<String> usedStatDer, usedContrMon;
	private RuleVisitor rv;
	private Map<String, String> undefValue;
	private Function currentFunction = null;

	private class Node {
		String value;
		Map<String, Node> children;

		Node() {
			children = new HashMap<>();
		}

		Node(String value) {
			this();
			this.value = value;
		}
	}

	public DerivedVisitor(Environment env, RuleVisitor rv, Map<String, String> undefValue) {
		this.env = env;
		usedStatDer = new HashSet<String>();
		usedContrMon = new HashSet<String>();
		this.rv = rv;
		this.undefValue = undefValue;
	}

	/**
	 * Visit.
	 * 
	 * @param loc the loc
	 * 
	 * @return the map< string, string>
	 * 
	 * @throws Exception the exception
	 */
	public Map<String, String> visit(Location loc) throws Exception {
  Node root;
		SortedMap<String, String> updateMap = new TreeMap<>();
		FunctionDefinition func = loc.getSignature().getDefinition();
		if(func != null) {
			currentFunction = func.getDefinedFunction();
			env.setVarsValues(func.getVariable(), loc.getElements());
			root = new Node(null);
			currentNode = root;
			root.value = visit(func.getBody());
			createUpdateMap(root, new Stack<>(), updateMap);
			currentFunction = null;
		}
		else {
			throw new Exception("The definition of " + loc.getSignature().getName() + " has not been found.");
		}
		return updateMap;
	}

	/**
	 * Creates the update map.
	 * 
	 * @param node the node
	 * @param conds the conds
	 * @param updateMap the update map
	 * 
	 * @throws Exception the exception
	 */
	private void createUpdateMap(Node node, Stack<String> conds, Map<String, String> updateMap) throws Exception{
		Map<String, Node> nodeMap = node.children;
		if(nodeMap.size() > 0) {
			for(Entry<String, Node> nodeMapEntrySet: nodeMap.entrySet()) {
				if(!nodeMapEntrySet.getKey().equals(Util.falseString)) {
					//System.err.println(nodeMapEntrySet.getKey());
					conds.push(nodeMapEntrySet.getKey());
					createUpdateMap(nodeMapEntrySet.getValue(), conds, updateMap);
					conds.pop();
				}
			}
		}
		else {
			if(!conds.isEmpty()) { 
				updateMap.put(Util.and(conds), node.value);
			}
			else {
				updateMap.put(Util.trueString, node.value);
			}
		}
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(Term term) {
		return visit((Object) term);
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ConditionalTerm term) throws Exception {
		String guard = visit(term.getGuard());
		Term thenTerm = term.getThenTerm();
		Term elseTerm = term.getElseTerm();
		Node child, oldNode;
		child = new Node(/*currentNode*/);
		oldNode = currentNode;
		currentNode = child;
		oldNode.children.put(guard, child);
		child.value = visit(thenTerm);
		currentNode = oldNode;
		if(elseTerm != null) {
			child = new Node(/*currentNode*/);
			oldNode = currentNode;
			currentNode = child;
			oldNode.children.put(Util.not(guard), child);
			child.value = visit(elseTerm);
			currentNode = oldNode;
		}
		return "";
	}

	//TODO: to fix. Look at the model asm_examples/examples/petriNets/forAsmetaSMV/petriNet_forNuSMV_reversable.asm
	public String visit(TupleTerm term) throws Exception {
		List<Term> terms = term.getTerms();
		StringBuilder sb = new StringBuilder();
		sb.append("(" + visit(terms.get(0)));
		for(int i = 1; i < terms.size(); i++) {
			sb.append(", " + terms.get(i));
		}
		sb.append("");
		return sb.toString();
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(CaseTerm term) throws Exception{
		String location = visit(term.getComparedTerm());
		List<Term> comparingTerms = term.getComparingTerm();
		Iterator<Term> resultTerms = term.getResultTerms().iterator();
		Node child, oldNode;
		for(Term comparingTerm: comparingTerms) {
			child = new Node(/*currentNode*/);
			oldNode = currentNode;
			currentNode = child;
			oldNode.children.put(Util.equals(location, visit(comparingTerm)), child);
			child.value = visit(resultTerms.next());
			currentNode = oldNode;
		}
		Term otherTerm = term.getOtherwiseTerm();
		if(otherTerm != null) {
			List<String> conds = new ArrayList<String>();
			for(Term comparingTerm: comparingTerms) {
				conds.add(Util.notEquals(location, visit(comparingTerm)));
			}
			child = new Node();
			oldNode = currentNode;
			currentNode = child;
			oldNode.children.put(Util.and(conds), child);
			child.value = visit(otherTerm);
			currentNode = oldNode;
		}
		return "";
	}

	//TODO: finire. Le variabili non hanno un dominio associato.
	public void visit(LetTerm letTerm) throws AsmNotSupportedException {
		ArrayList<String[]> values = new ArrayList<String[]>();
		List<VariableTerm> vars = letTerm.getVariable();
		//System.out.println("vars " + vars);
		//System.out.println("AssignmentTerm " + letTerm.getAssignmentTerm());
		//System.out.println("TermAsRule " + letTerm.getTermAsRule());
		rv.combineValues(vars, 0, values, new String[vars.size()]);
		Node child, oldNode;
		for(String[] value: values) {
			/*for(String v: value) {
				System.out.print(v + " ");
			}
			System.out.println();*/
			env.setVarsValues(vars, value);
			Iterator<Term> terms = letTerm.getAssignmentTerm().iterator();
			ArrayList<String> conds = new ArrayList<String>();
			for(String v: value) {
				conds.add(Util.equals(v, visit(terms.next())));
			}
			child = new Node(/*currentNode*/);
			oldNode = currentNode;
			currentNode = child;
			oldNode.children.put(Util.and(conds), child);
			child.value = visit(letTerm.getBody());
			currentNode = oldNode;
		}
	}

	/**
	 * Visit.
	 * 
	 * @param function the function
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(FunctionTerm functionTerm) throws Exception {
		Function function = functionTerm.getFunction();
		String str = env.tv.visit(functionTerm);
		if(Defs.isDerived(function) || (Defs.isStatic(function) &&
										!Defs.isBuiltIn(function))) {
			usedStatDer.add(str);
		}
		return str;
	}

	/**
	 * Visit.
	 * 
	 * @param location the location
	 * 
	 * @return the string
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	public String visit(LocationTerm location) throws AsmNotSupportedException {
		Function function = location.getFunction();
		String str = env.tv.visit(location);
		if(Defs.isControlled(function) || Defs.isMonitored(function)) {
			usedContrMon.add(str);
		}
		if(Defs.isDerived(function) || (Defs.isStatic(function) && !Defs.isBuiltIn(function))) {
			usedStatDer.add(str);
		}
		return str;
	}

	/**
	 * Visit.
	 * 
	 * @param variable the variable
	 * 
	 * @return the string
	 */
	public String visit(VariableTerm variable) {
		return env.tv.visit(variable);
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ExistTerm term) throws Exception {
		return env.tv.visit(term);
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ExistUniqueTerm term) throws Exception {
		return env.tv.visit(term);
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ForallTerm term) throws Exception {
		return env.tv.visit(term);
	}

	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public String visit(NaturalTerm number) {
		return env.tv.visit(number);
	}

	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public String visit(IntegerTerm number) {
		return env.tv.visit(number);
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(EnumTerm term) {
		return env.tv.visit(term);
	}

	/**
	 * Visit.
	 * 
	 * @param bool the bool
	 * 
	 * @return the string
	 */
	public String visit(BooleanTerm bool) {
		return env.tv.visit(bool);
	}

	/**
	 * Visit.
	 * 
	 * @param undef the undef
	 * 
	 * @return the string
	 */
	public String visit(UndefTerm undef) {
		Domain codomain = currentFunction.getCodomain();
		return undefValue.get(codomain.getName());
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(DomainTerm term) {
		return env.tv.visit(term);
	}
}