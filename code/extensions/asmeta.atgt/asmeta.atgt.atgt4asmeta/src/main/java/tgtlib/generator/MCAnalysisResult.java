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
package tgtlib.generator;

/**
 * represents the result of the analysis of the output of the model checker now
 * it is never used since are used only methods from TestSequence (like
 * unfeasinle) Now a test sequence may be unfeasible.
 */
public final class MCAnalysisResult {

	enum Response {
		UNFEASIBLE, FOUND, ERROR
	}

	private String message; // null is not error

	private Response verdict;

	// time taken to get this result
	private long time;

	/**
	 * Instantiates a new mC analysis result.
	 * 
	 * @param ts2
	 *            the test sequence (null is unfeasible or not found)
	 * @param string
	 *            the message (null if no message)
	 * @param b
	 *            the unfeasible, found or error?
	 */
	private MCAnalysisResult(String string, Response b) {
		message = string;
		verdict = b;
	}

	/**
	 */
	public static MCAnalysisResult found() {
		return new MCAnalysisResult(null, Response.FOUND);
	}

	public static MCAnalysisResult notFound(String message) {
		return new MCAnalysisResult(message, Response.ERROR);
	}

	/** returns a standard unfreasible result */
	public static MCAnalysisResult unfeasible() {
		return new MCAnalysisResult(null, Response.UNFEASIBLE);
	}

	public boolean isUnfeasible() {
		return verdict == Response.UNFEASIBLE;
	}

	public boolean isTestFound() {
		return verdict == Response.FOUND;
	}

	public void setTime(long l) {
		time = l;
	}

	/**
	 * in case of error, return the cause fo the error
	 * 
	 * @return
	 */
	public String getMessage() {
		assert verdict == Response.ERROR;
		return message;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		switch (verdict) {
		case FOUND:
			return "found";
		case UNFEASIBLE:
			return "unfeasible";
		case ERROR:
			return "error " + message;
		}
		throw new RuntimeException();
	}

}
