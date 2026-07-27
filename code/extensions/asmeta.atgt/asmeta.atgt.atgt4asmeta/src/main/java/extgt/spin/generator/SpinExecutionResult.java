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
package extgt.spin.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import tgtlib.generator.MCExecutionResultReader;

/**
 * models the execution result from spin : - the output produced by the
 * execution (by the superclass) - assertion violated? - memory required
 * 
 * execution result from the model checker step 3 and spin -t non posso passare
 * semplicemente l'inputstream non basta, perché spin ha bisogno di più fasi e
 * che l'asserzione sia violata la so durante una fase
 * 
 * @author garganti
 * 
 */
public class SpinExecutionResult extends MCExecutionResultReader {

	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(SpinExecutionResult.class);

	private static final int N_LINES_SPIN_OUT = 100;
	private ResultKind resultKind;
	public double memory;
	private StringBuffer errorMessage;
	
	public double timeElapsed; // time elapsed during the execution as reported by CMD

	/** build a SpinExec result starting from the input stream
	 * be careful to close the stream
	 * @param in
	 * @throws FileNotFoundException
	 */
	public SpinExecutionResult(InputStream in) throws FileNotFoundException {
		super(in);
		resultKind = ResultKind.UNKNOWN;
	}

	/** copy constructor 
	 * @throws FileNotFoundException */
	public SpinExecutionResult(SpinExecutionResult step3Result, InputStream in) throws FileNotFoundException {
		this(in);
		resultKind = step3Result.resultKind;
		memory = step3Result.memory;
	}

	/**
	 * analyze the execution (of spin_XX.exec - step 3) and check if the
	 * assertion is violated or not change the value of assertionViolated
	 * 
	 * @param fin
	 * @throws IOException
	 */
	public static SpinExecutionResult analyzeExecution(final InputStream fin)
			throws IOException {
		SpinExecutionResult result = new SpinExecutionResult(fin);
		// DETECT IF SOMETHING IS WRONG
		BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		// br.mark(1024);
		String firstLine;
		// skip intro part
		for (;;) {
			firstLine = br.readLine().trim();
			if (firstLine.startsWith("Depth="))
				continue;
			if (firstLine.startsWith("warning:"))
				continue;
			if (firstLine.startsWith("pan: resizing hashtable to"))
				continue;
			// there is the case where there is an error and the assertion is
			// still violated
			if (firstLine.startsWith("error:")) {
				result.resultKind = ResultKind.ERROR;
				result.errorMessage = new StringBuffer(firstLine + "\n");
				continue;
			}
			// if there is empty line, skip
			if (firstLine.isEmpty())
				continue;
			// Spin version starts line
			if (firstLine.startsWith("(Spin Version"))
				continue;
			// skip some extra input
			if (firstLine.startsWith("+ Partial Order Reduction"))
				continue;
			if (firstLine.startsWith("+ Using Breadth-First Search"))
				continue;						
			// new spin
			if (firstLine.startsWith("+ Breadth-First Search"))
				continue;						
			break;
		}
		logger.debug("intro finished, response is "+ firstLine);
		// do some diagnosis !!!
		// and try to set the final result!
		if (firstLine.startsWith("pan: assertion violated")||
				// in Spin version 525 there is a "1"
				firstLine.startsWith("pan:1: assertion violated")) {
			// br.reset();
			result.resultKind = ResultKind.ASSERTION_VIOLATED;
		} else if (firstLine.startsWith("pan: out of memory")) {
			result.resultKind = ResultKind.ERROR;
			result.errorMessage = new StringBuffer("out of memory");
		} else if (firstLine.startsWith("Full statespace search for:")) {
			// if there is not error, then the tp is unfeasible
			if (result.getResult() == ResultKind.UNKNOWN)
				result.resultKind = ResultKind.UNFEASIBLE;
		}
		// diagnosis terminated
		// is it still UNKNOWN???
		if (result.getResult() == ResultKind.UNKNOWN) {
			// do not know the type of error
			// it may be even not an error
			result .resultKind = ResultKind.ERROR;
			result.errorMessage = new StringBuffer("DO NOT KNOW THE ERROR\n");
		}
		// in case of error add the lines
		if (result.getResult() == ResultKind.ERROR) {
			result.errorMessage.append("analyze the following spin output:\n");
			// get the output message
			// 
			br.mark(N_LINES_SPIN_OUT * 100);
			int nLines = 0;
			while ((firstLine != null) && (nLines++ < N_LINES_SPIN_OUT)) {
				result.errorMessage.append(nLines).append("-")
						.append(firstLine).append('\n');
				firstLine = br.readLine();
			}
			// reqinf the spin output to allow further automatic analysis
			br.reset();
		}
	
		// read the rest to get the memory usage
		for (;;) {
			firstLine = br.readLine();
			if (firstLine == null)
				break;
			if (firstLine.contains("total actual memory usage")
					|| firstLine.contains("memory usage (Mbyte)")) {
				// take the amount of memory
				try {
					String string = firstLine.trim();
					string = string.split("\t")[0];
					if (string.contains(" "))
						string = string.substring(0, string.indexOf(" ")); 
					result.memory = Double.parseDouble(string);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		// do not close? br.close();
		return result;
	}

	public ResultKind getResult() {
		return resultKind;
	}

	public void setErrorMessage(StringBuffer errorMessage) {
		this.errorMessage = errorMessage;
	}

	public StringBuffer getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString(){
		return resultKind.toString();
	}
	
	// UNKNOWN --> initial state
	// assertion violated: all is fine, counter example found
	// unfeasible --> found unfeasible
	// error --> do not know (state explosion)
	public enum ResultKind {
		UNKNOWN, ASSERTION_VIOLATED, UNFEASIBLE, ERROR
	}
}
