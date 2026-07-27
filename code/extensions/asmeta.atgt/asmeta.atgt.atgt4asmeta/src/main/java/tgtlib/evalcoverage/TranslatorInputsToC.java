/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.evalcoverage;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import tgtlib.definitions.NavigableInputSequence;
import tgtlib.specification.Specification;
import tgtlib.util.Pair;

/**
 * traduce una input sequence in programma C che calcola gli output e le
 * coverage Lo schema e' il seguente (TODO print degli output). TODO: stato
 * iniziale: In SCR la sequenza e' portata avanti quando stampo in ASM c'e' da
 * decidere e controllaere se la seq contiene lo stato iniziale o no
 * 
 * 
 * <pre>
 *   SCR (primedInputs)					ASM (!primedInputs)
 * init m (from inputseq o init)e c		init m (from inputseq o init) e c
 * ($) set mP from input seq						
 * compute cP = f(m,mP,c)				($) compute cP = f(m,c)
 * check tp(m,mP,c,cP)					check tp(m,c)
 * copy m:=mP, c:=cP					copy c:=cP
 * stateCounter ++						stateCounter ++
 * 										set m from input seq 
 * goto $								goto $
 * </pre>
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
public abstract class TranslatorInputsToC<S extends Specification> {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(TranslatorInputsToC.class);

	static final String stateCounterName = "stateCounterForCoverage";

	private static final String coveredprefix = "covered";

	public static final String PRIMED_SUFFIX = "P";

	/** specification to translate */
	protected S mySpec;

	/**
	 * the inputs must be updated as primed values or not? in SCR yes, in ASM no
	 * !!
	 */
	protected boolean primedInputs;

	/**
	 * Instantiates a new translator inputs to c.
	 * 
	 * @param s
	 *            the specification
	 * @param p
	 *            inputs must be updated as primed values?
	 * 
	 */
	public TranslatorInputsToC(S s, boolean p) {
		mySpec = s;
		primedInputs = p;
	}

	/**
	 * given a test Tree and a test sequence containing only inputs ?? returns
	 * the translation IMPORTANT: inputSequence must contain the initial state
	 * and must be conforms with that of the spec the program will check;
	 * 
	 * @param inputSequence
	 *            NavigableInputSequence
	 * @return StringBuffer
	 */
	public final StringBuffer translate(NavigableInputSequence inputSequence) {
		log.debug("translating  inputSequence to C");
		StringBuffer result = new StringBuffer();

		result.append("/* c code generated for coverage of " + inputSequence.toString() + "*/\n");

		result.append("#include <stdio.h>\n\n");

		// DEFINE

		result.append("/* constants defined and enumerations */\n");

		// get the constants
		Map<String, String> DEFINED = getConstantsDefs();

		// add the constants for the enumerations
		int ej = 0;
		for (String enumC : getEnumConstDefs()) {
			if (!DEFINED.containsKey(enumC)) {
				DEFINED.put(enumC, String.valueOf(ej++));
			}
		}

		for (Entry<String, String> e : DEFINED.entrySet()) {
			result.append("#define " + e.getKey() + " " + e.getValue() + "\n");
		}

		// variables

		result.append("\n/* variable declaration (two states) */\n\n");
		for (Entry<String, String> e : getTwoStateVarsDecl().entrySet()) {
			appendVarDeclaration(result, e, true, inputSequence);
		}
		result.append("\n/* variable declaration (one state) */\n\n");
		for (Entry<String, String> e : getOneStateVarsDecl().entrySet()) {
			appendVarDeclaration(result, e, false, inputSequence);
		}

		// add another variable
		result.append("\nint " + stateCounterName + " = 0;\n\n");

		// add the bools for coverage Tree

		Map<String, String> tps = getTestPredicates();
		for (Entry<String, String> e : tps.entrySet()) {
			result.append("short " + coveredprefix + e.getKey() + " = 0;\n");
		}

		// while
		result.append("int main(){\n\n for(;;){\n");

		/* TEST SEQUENCE */
		if (primedInputs) {
			// skip to the second state !!!
			// ad dthe
			addMonVarsUpdate(inputSequence, result);
		}

		// compute cP
		result.append("/* transaction part */\n");
		result.append(getTransactionPart());
		// test predicates (SCR two states, ASM : one state)*/
		// check tp
		addTestPredicates(result, tps);
		// update variables
		addVarUpdates(result, getTwoStateVarsDecl());
		// increment state counter
		result.append("   " + stateCounterName + "++;\n");
		// TEST SEQUENCE if not using primed */
		if (!primedInputs)
			addMonVarsUpdate(inputSequence, result);
		// end for or
		result.append("  }/*close for */\n");

		for (Entry<String, String> e : tps.entrySet()) {
			String tpName = e.getKey();
			result.append("  if( " + coveredprefix + tpName + ") ");
			result.append("printf(\"" + coveredprefix + tpName + "\\n\");\n");
		}

		// end main
		result.append("}/*close main */\n");
		return result;
	}

	/**
	 * add the declaration and initial definition of a variable * @param result
	 * StringBuffer
	 * 
	 * @param e
	 *            Entry<String,String>
	 * @param addPrimed
	 *            boolean
	 * @param inputSequence
	 *            NavigableInputSequence
	 */
	private void appendVarDeclaration(StringBuffer result, Entry<String, String> e, boolean addPrimed,
			NavigableInputSequence inputSequence) {
		String varname = e.getKey();
		String var = varname;
		String initValSpec = e.getValue();
		if (initValSpec != null && initValSpec.equals(""))
			initValSpec = null;
		// check and extract initVal from inputSeq
		String initValSeq = null;
		for (Pair<String, String> assi : inputSequence.getInputs())
			if (assi.getFirst().equals(var)) {
				initValSeq = stringToC(assi.getSecond());
				break;
			}
		// check the init val:
		if (initValSpec != null && initValSeq != null && !initValSeq.equals(initValSpec)) {
			log.debug("result of the translation so far\n" + result);
			throw new InitValueInTestException("init values of " + var + " from spec and form seq differ !!! \n"
					+ "variable " + var + " in seq:" + initValSeq + " in spec:" + initValSpec);
		}
		// a questo punto se sono entrmabi non nulli allora coincidono
		String initVal = null;
		if (initValSpec != null)
			initVal = initValSpec;
		else if (initValSeq != null)
			initVal = initValSeq;
		// write the declaration
		result.append("int " + varname);
		if (initVal != null)
			result.append(" = " + initVal);
		result.append(";\n");
		// varP da evitare per le monitored se non uso primedInputs
		if (addPrimed) {
			String primedVarName;
			// in cas eit is an array
			if (varname.contains("[")) {
				int splitpoint = varname.indexOf('[');
				primedVarName = "int " + varname.substring(0, splitpoint) + PRIMED_SUFFIX
						+ varname.substring(splitpoint);
			} else {
				primedVarName = "int " + varname + PRIMED_SUFFIX;
			}
			result.append(primedVarName);
			// IMPORTANT add in any case the initial value, so if it is not
			// update the copy of P works
			if (initVal != null)
				result.append(" = " + initVal);
			result.append(";\n");
		}
	}

	/**
	 * convert a string to its C equivalent * @param value String
	 * 
	 * @return String t
	 */
	private static String stringToC(String value) {
		if (value.equalsIgnoreCase("true"))
			return "1";
		else if (value.equalsIgnoreCase("false"))
			return "0";
		return value;
	}

	/**
	 * 
	 * 
	 * @param result
	 * @param inputSequence
	 *            NavigableInputSequence
	 */
	private void addMonVarsUpdate(NavigableInputSequence inputSequence, StringBuffer result) {
		StringBuffer inputs = new StringBuffer("\n/* reading from the test sequence*/\n");
		// for every monitored
		inputSequence.reset();
		// if SCR, skip the first state
		if (primedInputs) {
			inputSequence.nextState();
		}
		int i = 0;
		do {
			inputs.append("   if ( " + stateCounterName + " == " + i++ + " ){ ");
			for (Pair<String, String> in : inputSequence.getInputs()) {
				// if getFirst() is empty
				if (in.getFirst().length() == 0) {
					// empty string. don't do anything
				} else {
					inputs.append(in.getFirst()).append(primedInputs ? PRIMED_SUFFIX : "").append(" = ");
					// convert TRUE/FALSE in case
					String value = stringToC(in.getSecond());
					inputs.append(value + "; ");
				}
			}
			inputs.append("}\n   else ");
		} while (inputSequence.nextState() != -1);
		inputs.append(" break;\n");
		result.append(inputs);
	}

	/**
	 * Method addVarUpdates.
	 * 
	 * @param update_S
	 *            StringBuffer
	 * @param VARS
	 *            Map<String,String>
	 */
	private void addVarUpdates(StringBuffer update_S, Map<String, String> VARS) {
		update_S.append("\n   /* update variables */\n\n");
		for (Entry<String, String> e : VARS.entrySet()) {
			String varName = e.getKey();
			if (varName.contains("[")) {
				// if it is an array
				String numCells = varName.substring(varName.indexOf('[') + 1, varName.indexOf(']'));
				varName = varName.substring(0, varName.indexOf('['));
				// use memcopy???
				update_S.append(
						"   memcpy(" + varName + "," + varName + PRIMED_SUFFIX + "," + numCells + "*sizeof(int));\n");

			} else {
				update_S.append("   " + varName + " = " + varName + PRIMED_SUFFIX + ";\n");
			}
		}
	}

	/**
	 * Method addTestPredicates.
	 * 
	 * @param result
	 *            StringBuffer
	 * @param tps
	 *            Map<String,String>
	 */
	private void addTestPredicates(StringBuffer result, Map<String, String> tps) {
		// test predicates;
		result.append("\n/* part for the coverage */\n");
		// add the bool for coverage Tree
		for (Entry<String, String> e : tps.entrySet()) {
			log.debug("adding coverage for " + e.getKey());
			// check if already covered TODO
			String coverageID = coveredprefix + e.getKey();
			result.append("   " + coverageID + " = " + coverageID + "||").append(e.getValue()).append(";\n");
		}
	}

	/**
	 * Method getTransactionPart.
	 * 
	 * @return StringBuffer
	 */
	abstract protected StringBuffer getTransactionPart();

	/**
	 * get the declaration for variables : their name and their initial value
	 * both x and X'
	 * 
	 * @return Map<String,String>
	 */
	abstract protected Map<String, String> getTwoStateVarsDecl();

	/**
	 * get the declaration for x (non x')
	 * 
	 * @return Map<String,String>
	 */
	abstract protected Map<String, String> getOneStateVarsDecl();

	/**
	 * get enumeration constants: names * @return List<String>
	 */
	abstract protected List<String> getEnumConstDefs();

	/**
	 * get the constants: name and value * @return Map<String,String>
	 */
	abstract protected Map<String, String> getConstantsDefs();

	/**
	 * get the list of test goals and their expression (in C)
	 * 
	 * 
	 * @return Map<String,String>
	 */
	abstract protected Map<String, String> getTestPredicates();

	/**
	 * 
	 * @return the coveredprefix
	 */
	public static String getCoveredprefix() {
		return coveredprefix;
	}
}