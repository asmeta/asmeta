package org.asmeta.animator;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.value.Value;

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

	/** run one step and returns the new state (mix current and previous)*/
	public MyState runSimulation(boolean random) {
		mixedMFReader.clear();
		// exec one step
		simulator.run(1);
		// take the state
		state = simulator.getCurrentState();
		// get the previous values
		Map<Location, Value> monitored;
		if (simulator.previousState != null)
			monitored = simulator.previousState.getMonLocs();
		else 
			monitored = new HashMap<>();
		// if changed by the random reader, update the values
		if (random)
			monitored.putAll(mixedMFReader.random.values);		
		// useless?
		// mixedMFReader.clear();
		return new MyState(state.getContrLocs(false), monitored);
	}

	public void setRandom() {
		mixedMFReader.setRandom();
	}

	public void setInteractive() {
		mixedMFReader.setInteractive();
	}
		
}
