package org.asmeta.atgt.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.AsmetaSMVOptions;

import tgtlib.definitions.expression.Expression;

/**
 * generates the test by Nusmv
 *
 */
public class TestGenerationWithNuSMV extends AsmetaSMV {

	static private Logger logger = Logger.getLogger(TestGenerationWithNuSMV.class);

	public static boolean useLTLandBMC = false;

	/**
	 * 
	 * @param asmPath
	 * @param expression
	 * @throws Exception
	 */
	public TestGenerationWithNuSMV(String asmPath) throws Exception {
		super(asmPath);
		// set the options (now statci, in the future could be an object)
		AsmetaSMVOptions.keepNuSMVfile = true;
		AsmetaSMVOptions.simplifyDerived = false;
		AsmetaSMVOptions.setPrintCounterExample(true);
		AsmetaSMVOptions.useCoi = false;
		AsmetaSMVOptions.FLATTEN = false;
		clearProperties();
	}

	private void clearProperties() {
		// now remove all the properties of this ASM
		asm.getBodySection().getProperty().clear();
	}

	public Map<String, String> getVariablesMap() {
		return mv.nusmvNameToLocation;
	}

	private void buildNuSMV(Expression tp) throws Exception {
		logger.debug("add cex and remove the other properties");
		Set<String> trapProps = new HashSet<String>();
		String tpS = tp.accept(ExpressionToSMV.EXPR_TO_SMV).toString();
		if (useLTLandBMC) {
			// use LTL
			trapProps.add("G(!((" + tpS + ") & X(TRUE)))");
			addLtlProperties(trapProps);
		} else {
			// normal trap property
			trapProps.add("AG(!(" + tpS + "))");
			addCtlProperties(trapProps);
		}
		createNuSMVfile();
		logger.debug("asmetasmv file built in " + getSmvFileName());
	}

	/**
	 * It checks the test predicate with id "tpId". If the test predicate is
	 * infeasible it returns null, otherwise counterexample.
	 * 
	 * @param tpId
	 * @return
	 * @throws Exception
	 */
	public Counterexample checkTpWithModelChecker(Expression tp) throws Exception {
		useBMC = useLTLandBMC;
		// clear all the previous properties.
		clearProperties();
		translation();
		//
		buildNuSMV(tp);
		//
		executeNuSMV(); BufferedReader br = new BufferedReader(new
		StringReader(outputRunNuSMVreplace));
		/*
		 * runNuXMV(); BufferedReader br = new BufferedReader(new StringReader(outputRunNuXMVreplace));
		 */
		return parseCounterExample(br);
	}

	/**
	 * reads the counter example from the reader
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
	static Counterexample parseCounterExample(BufferedReader br) throws IOException {
		// skip first lines
		for (;;) {
			String line = br.readLine();
			logger.debug(line);
			if (line == null) {
				br.close();
				return Counterexample.EMPTY;
			}	
			if (line.contains("is true")) {
				assert line.startsWith("-- specification");
				br.close();
				return Counterexample.INFEASIBLE;
			}
			if (line.contains("is false")) {
				assert line.startsWith("-- specification");
				break;
			}
		}
		String line;
		Counterexample counterexample = new Counterexample();
		ModelCheckerState nusmvState = null;
		boolean loopStart = false;
		while ((line = br.readLine()) != null) {
			if (line.matches(" *-> State: [0-9]+.[0-9]+ <-")) {
				if (nusmvState != null) {
					counterexample.addState(nusmvState);
				}
				nusmvState = new ModelCheckerState(loopStart);
				loopStart = false;
			} else if (line.contains("-- Loop starts here")) {
				loopStart = true;
			} else if (TestGenerationWithNuSMV.useLTLandBMC && line.startsWith("--")) {
				continue;
			} else {
				String[] varValue = line.replaceAll(" ", "").split("=");
				if (varValue.length == 2) {
					assert nusmvState != null : "line \"" + line + "\"";
					nusmvState.addVarValue(varValue[0], varValue[1]);
				}
			}
		}
		if (nusmvState != null) {
			counterexample.addState(nusmvState);
		}
		br.close();
		completeCounterExample(counterexample);
		return counterexample;
	}

	/**
	 * It completes a counterexample trace. Indeed, if the value of a variable is
	 * unchanged passing from state s_{i} to state s_{i+1}, in state s_{i+1} it is
	 * not printed.
	 * 
	 * non so se è necessario che adesso il generatore può farlo lui ....
	 */
	static protected void completeCounterExample(Counterexample counterexample) {
		for (int i = 1; i < counterexample.length(); i++) {
			ModelCheckerState currentState = counterexample.getState(i);
			ModelCheckerState nextState = counterexample.getState(i + 1);
			Set<String> nextStateVars = nextState.getVars();
			for (String var : currentState.getVars()) {
				if (!nextStateVars.contains(var)) {
					nextState.addVarValue(var, currentState.getVarValue(var));
				}
			}
		}
	}
}