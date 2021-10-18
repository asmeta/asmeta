package org.asmeta.atgt.generator2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.Location.VarKind;
import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;

public class AsmTestGeneratorBySimulation extends AsmTestGenerator {

	private int stepNumber;
	private AsmCollection asm;
	private IdExpressionCreator icc;
	private int testNumer;

	public static tgtlib.definitions.expression.type.Type dummyType = new DummyType("dummy");

	RandomMFReaderMemory randomMFReader;

	/**
	 * 
	 * @param asm
	 * @param stepNumber
	 * @param testNumber
	 */
	public AsmTestGeneratorBySimulation(AsmCollection asm, int stepNumber, int testNumber) {
		this(asm, stepNumber, testNumber, new RandomMFReaderMemory());
	}

	/**
	 * 
	 * @param asm
	 * @param stepNumber
	 * @param testNumber
	 */
	public AsmTestGeneratorBySimulation(AsmCollection asm, int stepNumber, int testNumber, RandomMFReaderMemory rnd) {
		this.stepNumber = stepNumber;
		this.asm = asm;
		this.testNumer = testNumber;
		// to collect info about the spec
		icc = new IdExpressionCreator();
		//
		randomMFReader = rnd;		
		// TODO add variables
	}

	@Override
	public AsmTestSuite getTestSuite() {
		try {
			AsmTestSuite testSuite = new AsmTestSuite();
			for (int test = 0; test < testNumer; test++) {
				// get the name
				String modelName = asm.getMain().getName();
				// build the random environment
				Environment env = new Environment(randomMFReader);
				// build the simulator
				Simulator simulator = new Simulator(modelName, asm, env);
				// do not use real random values when choosing
				simulator.setShuffleFlag(false);
				// simulator.createSimulatorRnd(modelName);
				//
				AsmTestSequence testsequence = new AsmTestSequence(new AsmTestCondition("test" + test, null));
				State state;
				int currentStep = 0;
				while (true) {
					// get the current state
					state = new State(simulator.getCurrentState());
					// execute the step
					try {
						simulator.runNoCatchInv(1);
					} catch (InvalidInvariantException e) {
						System.out.println("Invariant violation");
						break;
					}
					// get previous controlled part
					state.locationMap.putAll(simulator.previousState.locationMap);
					// get the monitored value of the previous step
					state.locationMap.putAll(randomMFReader.values);
					// add this (previous) state to the sequence
					addState(testsequence, state);
					// go to the next state
					currentStep++;
					if (currentStep >= stepNumber - 1) {
						// add also the last step and exit
						addState(testsequence, simulator.getCurrentState());
						break;
					}
					// restart the env
					randomMFReader.values.clear();
				}
				testSuite.addTest(testsequence);
			}
			return testSuite;
		} catch (AsmModelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AsmTestSuite.getEmptyTestSuite();
		} catch (MainRuleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AsmTestSuite.getEmptyTestSuite();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AsmTestSuite.getEmptyTestSuite();
		}

	}

	// add this state to the test sequence
	private void addState(AsmTestSequence testsequence, State state) {
		testsequence.addState();
		for (Entry<Location, Value> stateValues : state.locationMap.entrySet()) {
			Location location = stateValues.getKey();
			// TODO store the variables somewhere
			// check if monitored or controlled
			Function function = location.getSignature();
			boolean monitored = function instanceof MonitoredFunction;
			assert monitored || function instanceof ControlledFunction;
			Value[] elements = location.getElements();
			boolean isvar = elements.length == 0;
			if (isvar) {
				// create ID (with dummy type which is wrong)
				IdExpression varId = icc.createIdExpression(location.toString(), dummyType);
				atgt.specification.location.Variable var = new atgt.specification.location.Variable(varId, null);
				if (monitored)
					var.setMonitored();
				else
					var.setControlled();
				testsequence.addAssignment(var, stateValues.getValue().toString());
			} else {
				assert elements.length >= 1 : Arrays.toString(elements);
				List<IdExpression> args = new ArrayList<>();
				for (Value v : elements)
					args.add(icc.createIdExpression(v.toString(), dummyType));
				IdExpression name = icc.createIdExpression(location.getSignature().getName(), dummyType);
				// type

				FunctionTerm ft = new FunctionTerm(name, dummyType, args);
				try {
					testsequence.addAssignment(ft, stateValues.getValue().toString(),
							monitored ? VarKind.MONITORED : VarKind.CONTROLLED);
				} catch (NullPointerException npe) {
					npe.printStackTrace();
					System.err.println(" ft " + ft + " id " + ft.getFunction() + " domain " + ft.getCoDomain());
				}
			}
			// System.out.println("PRINT STATE" +
			// System.identityHashCode(simulator.getCurrentState()));
			// System.out.println("PRINT STATE" + simulator.getCurrentState());
			// System.out.println("PRINT TS " + testsequence);
			// System.out.println("PRINT" + testsequence.get(i).locationMap);
		}
	}
}
