package org.asmeta.atgt.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.asmeta.atgt.generator.coverage.AsmetaAsSpec;

import atgt.coverage.AsmTestSequence;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import tgtlib.definitions.NavigableInputSequence;
import tgtlib.util.IterableEnumeration;
import tgtlib.util.Pair;

/**
 * The Class NavigableAsmInputs.
 */
public class NavigableAsmetaInputs implements NavigableInputSequence {

	/** The test. */
	AsmTestSequence test;

	/** The current state. */
	int currentState = 0;

	/** The monitored vars. */
	List<String> monitoredVars;

	/**
	 * Instantiates a new navigable asm inputs.
	 * 
	 * @param test2
	 *            the test2
	 * @param spec
	 *            the spec necessaria per distiguere montiored, TODO no longer necessary 
	 */
	public NavigableAsmetaInputs(AsmTestSequence test2, AsmetaAsSpec spec) {
		test = test2;
		monitoredVars = new Vector<String>();
		for (Location v : new IterableEnumeration<Variable>(spec.allVariables())) {
			if (v.isMonitored())
				monitoredVars.add(v.getName());
		}
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.definitions.NavigableInputSequence#getInputs()
	 */
	@Override
	public List<Pair<String, String>> getInputs() {
		// take all the instructions
		List<Pair<String, String>> l = new ArrayList<Pair<String, String>>();
		if (isEmpty()) {
			System.err.println("Warning in atgt.coverage.evalc.NavigableAsmInputs: the test instructions are empty, maybe something is wrong");
			return l; // MR: otherwise throw exception?
		}
		Map<Location, String> currentStateMap = test.allInstructions().get(currentState);
		for (Entry<Location, String> pair : currentStateMap.entrySet()) {
			Location var = pair.getKey();
			if (var.isMonitored()){
				assert monitoredVars.contains(var.getName()) : var.getName() + " is not set in " + test.allInstructions();
				l.add(new Pair<String, String>(var.getName(), pair.getValue()));
			}
		}
		return l;
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.definitions.NavigableInputSequence#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return test.allInstructions().size() == 0;
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.definitions.NavigableInputSequence#nextState()
	 */
	@Override
	public int nextState() {
		if (currentState < test.allInstructions().size() - 1)
			return (++currentState);
		else
			return -1;
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.definitions.NavigableInputSequence#reset()
	 */
	@Override
	public void reset() {
		currentState = 0;
	}

	public AsmTestSequence getAsmTesSequence() {
		return test;
	}

}
