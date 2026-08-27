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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import atgt.specification.location.Function;
import atgt.specification.location.FunctionApplication;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpression;

/**
 * represents the content of a ASM TEST Sequence*.
 */
public class AsmTestSeqContent extends AsmTestSeqContentInterface {
	
	    
	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(AsmTestSeqContent.class);

	/**
	 * Il vettore per la memorizzazione delle istruzioni del controesempio
	 * assegnamenti e anche nuovo stato ogni elemento rappresenta uno stato
	 * memorizza solo i cambiamneti rispetto alla stato precedente.
	 */
	protected List<Map<Location, String>> instructions;

	/** point to the last state (map). */
	private int lastStateIndex = -1;
	
	/*
	 * to memorize the last value (may be not in the last state and to add only
	 * changes)
	 */
	private Map<Location, String> lastValues;
	
	
	public static boolean addOnlyChangeValues = true;
	
	
	/**
	 * Instantiates a new asm test seq content.
	 */
	public AsmTestSeqContent() {
		this.instructions = new LinkedList<Map<Location, String>>();
		// do not add the first vector of state
		// the user must call as first add NewState
		this.lastValues = new HashMap<Location, String>();
	}

	@Override
	public void addAssignment(Variable var, String value) {
		addAssignmentLocation(var, value);
	}

	private void addAssignmentLocation(Location var, String value) {
		assert lastStateIndex != -1 : "call addState as first instruction";
		String lastValue = this.lastValues.get(var);
		// if it is really a change - commit only changes
		if (lastValue == null || !lastValue.equals(value)) {
			// check if it already assigned
			Map<Location, String> lastState = instructions.get(lastStateIndex);
			if (lastState.get(var) != null)
				try {
					throw new InconsistentUpdateException(
							"inconsistent update found: in this state (" + instructions.size() + ") the var " + var
									+ " has already assigned old: " + lastState.get(var) + " new: " + value);
				} catch (InconsistentUpdateException e) {
					System.err.println(e.getLocalizedMessage());
					logger.error(e.getMessage());
				}
			// var has no value in this state:
			lastValues.put(var, value);
			lastState.put(var, value);
		}
	}

	@Override
	public void addAssignment(Function func, List<IdExpression> args, String value) {
		FunctionApplication fa = new FunctionApplication(func, args);
		if (func.isControlled())
			fa.setControlled();
		else {
			assert func.isMonitored();
			fa.setMonitored();
		}
		addAssignmentLocation(fa, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#addState()
	 */
	@Override
	public void addState() {
		completeOldState();
		// build a new state
		instructions.add(new HashMap<Location, String>());
		lastStateIndex ++;		
	}
	// complete the state with previosu valeus not changed if necessary
	private void completeOldState() {
		if (addOnlyChangeValues || lastStateIndex == -1) return;
		// copy the values in last values:
		logger.debug("completing the last state " + instructions.get(lastStateIndex) + " with"  + lastValues);
		instructions.get(lastStateIndex).putAll(lastValues);
	}

	/**
	 * all the content of the test sequence.
	 * 
	 * @return the list< map< string, string>>
	 */
	@Override
	public List<Map<Location, String>> allInstructions() {
		return Collections.unmodifiableList(this.instructions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.util.TestSeqContentInterface#close()
	 */
	@Override
	public void close() {
		logger.debug("closing the state");
		completeOldState();
		// if the last is empty, remove it
		if (lastStateIndex != -1 && instructions.get(lastStateIndex).isEmpty()) {
			logger.debug("removing the last empty state");
			this.instructions.remove(this.instructions.size() - 1);
		}
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

	/**
	 * print to System.out the test sequence TODO modify to String
	 * 
	 * @param ps
	 *            the ps
	 * 
	 * @return TODO
	 */
	@Override
	public StringBuffer toVideo() {
		boolean first = true;
		StringBuffer s = new StringBuffer();
		for (Map<Location, String> v : this.instructions) {
			if (!first) {
				s.append(" NEW STATE ");
				first = false;
			}
			s.append("[");
			for (Map.Entry<Location, String> element : v.entrySet())
				s.append(" " + element.toString());
			s.append(" ]");
		}
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.AsmTestSeqContentInterface#toStringBuffer()
	 */
	@Override
	public StringBuffer toStringBuffer() {
		StringBuffer s = new StringBuffer();
		for (Map<Location, String> v : this.instructions) {
			for (Map.Entry<Location, String> element : v.entrySet())
				s.append(" ").append(element.toString()).append("\t\t");
			s.append("\n");
		}
		return s;
	}

}
