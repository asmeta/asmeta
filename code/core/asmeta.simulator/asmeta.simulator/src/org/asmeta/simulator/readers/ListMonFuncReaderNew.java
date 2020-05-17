package org.asmeta.simulator.readers;

import java.io.PrintStream;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * PA For monitoring.
 * 
 */
public class ListMonFuncReaderNew extends MonFuncReader {
	private MonitoredValues in;
	public static boolean randomValueTaken = false;

	public ListMonFuncReaderNew(MonitoredValues map) {
		in = map;
	}

	@Override
	public Value readValue(Location location, State state) {
		Function func = location.getSignature();
		String value = in.getMap().get(location);
		// System.out.println("ListMonFuncReaderNew in.getMap() = " +
		// in.getMap());
		// System.out.println("ListMonFuncReaderNew set " + location + " to " +
		// value);
		// FOR TESTING AND MONITORING
		//Since we need to evaluate the test predicates after each step, it is possible that
		//such predicates contain monitored functions. If the functions have not been required
		//by the step, it means that they are not reachable: so the test predicate should be for sure false
		//given any value for the monitored functions.
		if (value == null) {
			randomValueTaken = true;
			RandomMFReader rndReader = null;
			rndReader = new RandomMFReader(state, new PrintStream(new java.io.OutputStream ( ) { @Override
			public void write ( int b ) { } } ));
			value = rndReader.readValue(location, state).toString();
			//System.out.println(value);
			//System.out.println("value of " + location.toString() + " selected randomly");
		}
		try {
			return new Parser(value).visit(func.getCodomain());
		} catch (InputMismatchException e) {
			throw new RuntimeException(e);
		}
	}
}