package org.asmeta.animator;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.value.Value;
import org.eclipse.swt.widgets.Text;

import asmeta.AsmCollection;

/** scenario execution with random values */
class AsmTestGeneratorMixedSimulation{
	private VisualizationSimulation v;
	private MixedMFReader mixedMFReader;
	private AsmCollection asm;
	private State state;
	private SimulatorForAnimator simulator;

	private AsmTestGeneratorMixedSimulation(AsmCollection asm) {
		this.asm = asm;
	}

	public AsmTestGeneratorMixedSimulation(AsmCollection asm, VisualizationSimulation v) {
		this(asm);
		this.v = v;
	}

	void initializeSimulation(VisualizationSimulationI t) throws AsmModelNotFoundException, MainRuleNotFoundException {
		// get the name
		String modelName = asm.getMain().getName();
		// build the random environment
		mixedMFReader = new MixedMFReader(System.in, System.out, v);
		Environment env = new Environment(mixedMFReader);
		// build the simulator
		simulator = new SimulatorForAnimator(modelName, asm, env, t);
		state = new State(simulator.getCurrentState());
		// state.locationMap.putAll(mixedMFReader.random.values);
		mixedMFReader.clear();
		state = simulator.getCurrentState();
	}

	/** run one step and returns the new state*/
	public MyState runSimulation() {
		mixedMFReader.clear();
		// System.out.println("state.getMonLocs " + state.getMonLocsState());
		// System.out.println("mixedMFReader " +
		// mixedMFReader.interactive.values.toString());
		// InputStream input = new
		// ByteArrayInputStream(v.getTextMonitored().getText().getBytes());
		simulator.run(1);
		// state.locationMap.putAll(mixedMFReader.random.values);
		mixedMFReader.clear();
		// update the state
		state = simulator.getCurrentState();
		return new MyState(state.getContrLocs(false), state.getMonLocs());
	}

	public MyState runSimulation(int stepnumber) {
		mixedMFReader.clear();
		// InputStream input = new
		// ByteArrayInputStream(v.getTextMonitored().getText().getBytes());
		simulator.run(1);
		// state.locationMap.putAll(mixedMFReader.random.values);
		Map<Location, Value> monitored = new HashMap<>(mixedMFReader.random.values);
		// state.locationMap.putAll(mixedMFReader.);
		mixedMFReader.clear();
		// update the state
		state = simulator.getCurrentState();
		// return new MyState(state.getContrLocs(), state.getMonLocs());
		return new MyState(state.getContrLocs(false), monitored);
	}

	public void setRandom() {
		mixedMFReader.setRandom();
	}

	public void setInteractive() {
		mixedMFReader.setInteractive();
	}

	MyState getPreviousState() {
		return new MyState(simulator.previousState.getContrLocs(false),simulator.previousState.getMonLocs());  
	}
	
	
}
