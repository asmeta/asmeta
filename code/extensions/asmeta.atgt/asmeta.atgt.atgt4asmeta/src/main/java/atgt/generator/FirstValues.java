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
package atgt.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpression;

// TODO: Auto-generated Javadoc
/**
 * similar to Test Sequence: keep track only of the first identical to
 * AsmTestSequence, except the new state does nothing it is used by SAL when
 * only the first is the correct (still to check) in case on no temporal
 * constraints.
 */
class FirstValues extends
		atgt.coverage.AsmTestSeqContentInterface {

	/* to memorize the last value and to add only changes */
	/** The first values. */
	private Map<Location, String> firstValues;

	enum Status {
		EMPTY, FIRST_SATE, FOLLOWING
	};

	private Status status;

	/**
	 * Instantiates a new first values.
	 */
	public FirstValues() {
		status = Status.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#addEvent(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addAssignment(Variable var, String value) {
		if (status == Status.FIRST_SATE)
			firstValues.put(var, value);
		else if (status == Status.EMPTY){
			throw new RuntimeException("call add first");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#addState()
	 */
	@Override
	public void addState() {
		if (status == Status.EMPTY) {
			firstValues = new HashMap<Location, String>();
			status = Status.FIRST_SATE;
		} else if (status == Status.FIRST_SATE) {
			status = Status.FOLLOWING;
		}
		// otherwise do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#close()
	 */
	@Override
	public void close() {
		// NOTHING
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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.coverage.AsmTestSeqContentInterface#allInstructions()
	 */
	@Override
	public List<Map<Location, String>> allInstructions() {
		return Collections.singletonList(firstValues);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.coverage.AsmTestSeqContentInterface#toStringBuffer()
	 */
	@Override
	public StringBuffer toStringBuffer() {
		StringBuffer s = new StringBuffer();
		for (Map.Entry<Location, String> element : firstValues.entrySet())
			s.append(" ").append(element.toString()).append("\t\t");
		s.append("\n");
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.coverage.AsmTestSeqContentInterface#toVideo(java.io
	 * .PrintStream)
	 */
	@Override
	public StringBuffer toVideo() {
		return toStringBuffer();
	}

	@Override
	public void addAssignment(Function var, List<IdExpression> args, String value) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
