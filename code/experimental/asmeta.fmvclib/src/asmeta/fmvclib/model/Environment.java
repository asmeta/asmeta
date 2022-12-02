package asmeta.fmvclib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

public class Environment {
	Map<VariableTerm, String> varsValues;
	public List<String> usedLoc, usedDerLoc;
	List<String> orderVars;// it should be used for building an order file. Currently it is not used.
	Map<String, Rule> agentRule;// it memorises the rule of an agent
	boolean inSeqRule;

	boolean inDerivedVisitor;
	public TermVisitor tv;
	String self;
	/**
	 * It associates derived/static locations with their definitions. We can use it
	 * to replace occurrences of derived/static locations with their definitions.
	 */
	public Map<String, String> inLineFunctions;

	Environment() {
		varsValues = new HashMap<VariableTerm, String>();
		usedLoc = new ArrayList<String>();
		orderVars = new ArrayList<String>();
		usedDerLoc = new ArrayList<String>();
		inSeqRule = false;// se non sono in una seq rule posso memorizzare gli aggiornamenti
		agentRule = new HashMap<String, Rule>();
		inDerivedVisitor = false;
	}

	/**
	 * It adds to the map "varValues" the variables "vars" with the corresponding
	 * values "values".
	 * 
	 * @param vars   variables
	 * @param values values
	 */
	void setVarsValues(List<VariableTerm> vars, Object[] values) {
		assert vars.size() == values.length : "vars.size() = " + vars.size() + "\nvalues.length = " + values.length;
		int i = 0;
		for (VariableTerm var : vars) {
			// varsValues.put(var, values[i++].toString().toUpperCase());
			setVarValue(var, values[i++]);
		}
	}

	/**
	 * It associates the value "value" to variable "var".
	 * 
	 * @param var
	 * @param value
	 */
	void setVarValue(VariableTerm var, Object value) {
		varsValues.put(var, value.toString().toUpperCase());
	}

	String getVarValue(VariableTerm var) {
		return varsValues.get(var);
	}

	/**
	 * It removes from map "varsValues" the variables "vars".
	 * 
	 * @param vars the vars
	 */
	void removeVars(List<VariableTerm> vars) {
		for (VariableTerm var : vars) {
			varsValues.remove(var);
		}
	}

}
