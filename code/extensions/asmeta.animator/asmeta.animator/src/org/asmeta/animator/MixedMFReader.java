package org.asmeta.animator;

import java.io.InputStream;
import java.io.PrintStream;

import org.asmeta.animator.dialog.RandomMFReaderMemory;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.Value;

/**
 * double behavior MF reader: it can be random or interactive (with memory)
 *
 */
class MixedMFReader extends  MonFuncReader{
	
	RandomMFReaderMemory random;
	private InteractiveMFReaderMemory interactive;	
	
	enum Mode {RANDOM, INTERACTIVE} 
	
	private Mode mode;

	public MixedMFReader(InputStream in, PrintStream out, VisualizationSimulation v) {		
		random = new RandomMFReaderMemory();
		interactive = new InteractiveMFReaderMemory(in, out, v);
	}

	@Override
	public Value readValue(Location location, State state) {
		if (mode == Mode.RANDOM)
			return random.read(location, state);
		else 
			return interactive.read(location, state);
	}

	public void clear() {
		// clear both
		random.values.clear();
		interactive.values.clear();
	}

	public void setRandom() {
		mode = Mode.RANDOM;		
	}

	public void setInteractive() {
		mode = Mode.INTERACTIVE;		
	}	
	
	@Override
	public boolean supportsLazyTermEval() {
		//only when interactive
		return mode == Mode.INTERACTIVE;
	}
	
}
