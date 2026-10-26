/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.coverage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Location.VarKind;
import atgt.specification.location.Variable;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.util.TestSeqContentInterface;

/**
 * Questa classe si occupa della memorizzazione dei risultati di un caso di
 * test. Contiene il risultato della verifica, ovvero l'esito dell'istruzione
 * assert (se e' stata violata o no?). Memorizza il controesempio per la
 * specifica. Nel caso in cui e' stata richiesta la ricerca per gli altri casi
 * di test coperti da questo test predicate, memorizza l'elenco di questi casi
 * 
 * ATTENZIONE E' una test sequence e un content handler insieme
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */
public class AsmTestSequence extends TestResult implements Comparable<AsmTestSequence>, TestSeqContentInterface {

	/** The content. */
	protected AsmTestSeqContentInterface content;

	/** time taken to produce this test, in millesec. */
	public long time = 0;
	
	/**
	 * the name of this test: normally the name of the test condition it is
	 * generated for.
	 */
	String name = null;

	/**
	 * the name of this test: normally the name of the test condition it is
	 * generated for.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * build a test sequence generated for the test condition tc.
	 * 
	 * @param tc
	 *            the tc
	 */
	public AsmTestSequence(AsmTestCondition tc) {
		this((TestCondition<AsmTestSequence>)tc);
	}

	/**
	 * build a test sequence generated for the test condition tc.
	 * 
	 * @param tc
	 *            the tc
	 */
	public AsmTestSequence(TestCondition<AsmTestSequence> tc) {
		super(tc);
		this.name = "test@" + tc.getName();
		content = new AsmTestSeqContent();
		
	}

	/**
	 * returns the factory for AsmTestSequences
	 * 
	 */
	static final public TestSequenceFactory<AsmTestSequence, TestCondition<AsmTestSequence>> factory = new TestSequenceFactory<AsmTestSequence, TestCondition<AsmTestSequence>>() {

		@Override
		public AsmTestSequence buildTestSequence(TestCondition<AsmTestSequence> tp) {
			return new AsmTestSequence(tp);
		}
	};

	// these can be specific for a single ASMETA spec
	/** store all the variables */
	private static Map<String, Variable> variables;
	/** store their ids to avoid unnecessary duplications of ids */
	private static IdExpressionCreator iecc;
	// the functions already build in this test
	// TODO avoid function altogether
	static private Map<IdExpression,Function> functions;

	
	public static void resetForAnewSPEC() {
		variables = new HashMap<String, Variable>();
		iecc = new IdExpressionCreator();
		functions = new HashMap<>();		
	}
	
	static {
		resetForAnewSPEC();
	}
	
	
	/**
	 * Adds the assignment. Use variables instead
	 * 
	 * @param var
	 *            the var
	 * @param val
	 *            the val
	 */
	@Deprecated
	public void addAssignment(String varS, String val, VarKind vt) {
		addAssignment(getVar(varS, vt), val);
	}

	/**
	 * 
	 * @param location
	 *            the function that is updated
	 * @param domain
	 *            the domain of this function (since function term has no
	 *            function information, only the codomain (value)
	 * @param val
	 */
	public void addAssignment(tgtlib.definitions.expression.FunctionTerm location, String val, VarKind kind) {
		IdExpression funId = location.getFunction();
		// search for an already existing function
		Function f = functions.get(funId);
		if (f == null) {
			f = new Function(funId, funId.getType(), location.getCoDomain(), null);
			functions.put(funId, f);
		}
		// check that is the same funnction
		assert f.getIdExpression() == funId;
		assert f.getCodomain() == location.getCoDomain();
		if (kind == VarKind.CONTROLLED) f.setControlled();
		else {
			assert kind == VarKind.MONITORED;
			f.setMonitored();
		}
		assert location.getArguments() != null;
		assert location.getArguments().get(0) != null;
		content.addAssignment(f, (List<IdExpression>) location.getArguments(), val);
	}

	@Override
	public void addAssignment(tgtlib.definitions.expression.type.Variable varS, String val) {
		assert !varS.getName().contains("(");
		// logger.debug(( varS + "("+varS.getType()+")::::" + val);
		if (varS.getType() == BoolType.BOOLTYPE) {
			// in spin the translation uses single chars instead
			// Promela follows the C-convention that the boolean false-condition
			// corresponds with the value 0;
			// any non-zero value denotes truth.
			if (val.equals("T") || val.equals("1"))
				val = BoolType.TRUE_STR;
			else if (val.equals("F") || val.equals("0"))
				val = BoolType.FALSE_STR;
			// sometime TRUE e FALSE are passed along
			val = val.toLowerCase();
			assert val.equals(BoolType.FALSE_STR) || val.equals(BoolType.TRUE_STR);
		}
		content.addAssignment((Variable) varS, val);
	}

	@Override
	protected tgtlib.definitions.expression.type.Variable getVar(String varS) {
		return getVar(varS, VarKind.MONITORED);

	}

	private tgtlib.definitions.expression.type.Variable getVar(String varS, VarKind vt) {
		// get the variable
		Variable var = variables.get(varS);
		if (var == null) {
			// create a new var (type null)
			// TODO read the right type from somewhere .... spec???
			var = new Variable(iecc.createIdExpression(varS, null), null, null);
			if (vt == VarKind.CONTROLLED)
				var.setControlled();
			else
				var.setMonitored();
			variables.put(varS, var);
		}
		return var;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#addState()
	 */
	@Override
	public void addState() {
		content.addState();
	}

	/**
	 * All instructions. TODO make as iterbale
	 * 
	 * @return the list< map< string, string>>
	 */
	public List<Map<Location, String>> allInstructions() {
		return content.allInstructions();
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public AsmTestSeqContentInterface getContent() {
		return content;
	}

	@Override
	public int numberOfStates() {
		return content.allInstructions().size();
	}

	/**
	 * return the complete description of this test sequence the content +
	 * coverage info
	 * 
	 * @return TODO return the string
	 */
	public StringBuffer toVideo() {
		return content.toVideo();// .append(coverageInfo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AsmTestSequence arg0) {
		return this.getName().compareTo(arg0.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#close()
	 */
	@Override
	public void close() {
		content.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#setNotFound(java.lang.String)
	 */
	@Override
	public void setNotFound(String message) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#setUnfeasible()
	 */
	@Override
	public void setUnfeasible() {
		// throw new RuntimeException("it does nothing");
	}

	@Override
	public boolean equals(Object obj) {
		AsmTestSequence otherObj;
		if (obj instanceof AsmTestSequence) {
			otherObj = (AsmTestSequence) obj;
		} else {
			return false;
		}
		List<Map<Location, String>> thisObjInstructions = content.allInstructions();
		List<Map<Location, String>> otherObjInstructions = otherObj.content.allInstructions();
		int thisObjInstructionsSize = thisObjInstructions.size();
		int otherObjInstructionsSize = otherObjInstructions.size();
		if (thisObjInstructionsSize == otherObjInstructionsSize) {
			Map<Location, String> thisObjInstruction, otherObjInstruction;
			int thisObjInstructionSize, otherObjInstructionSize;
			for (int i = 0; i < thisObjInstructionsSize; i++) {
				thisObjInstruction = thisObjInstructions.get(i);
				otherObjInstruction = otherObjInstructions.get(i);
				thisObjInstructionSize = thisObjInstruction.size();
				otherObjInstructionSize = otherObjInstruction.size();
				if (thisObjInstructionSize == otherObjInstructionSize) {
					for (Entry<Location, String> entry : thisObjInstruction.entrySet()) {
						if (!(otherObjInstruction.containsKey(entry.getKey())
								&& otherObjInstruction.get(entry.getKey()).equals(entry.getValue()))) {
							return false;
						}
					}
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (Map<Location, String> instruction : content.allInstructions()) {
			for (Entry<Location, String> step : instruction.entrySet()) {
				result = prime * result + step.getKey().hashCode();
				result = prime * result + step.getValue().hashCode();
			}
		}
		// System.out.println(result);
		return result;
	}

	@Override
	public Map<? extends tgtlib.definitions.expression.type.Variable, String> getState(int stateNum) {
		return content.allInstructions().get(stateNum);
	}
}
