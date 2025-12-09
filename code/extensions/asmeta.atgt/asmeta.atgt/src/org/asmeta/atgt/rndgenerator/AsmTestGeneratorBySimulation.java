package org.asmeta.atgt.rndgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.Location.VarKind;
import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.TypeVisitorI;

/**
 * random generation by random simulation
 */
public class AsmTestGeneratorBySimulation extends AsmTestGenerator {

	private int stepNumber;
	private AsmCollection asm;
	private IdExpressionCreator icc;
	private int numberofTests;
	/**
	 * progressive number among succesive test generation runs
	 */
	private int testNumberOffset;

	public static tgtlib.definitions.expression.type.Type dummyType = new DummyType("dummy");

	private static tgtlib.definitions.expression.type.Type stringType = new tgtlib.definitions.expression.type.Type(
			"String") {

		@Override
		public int range() {
			throw new RuntimeException();
		}

		@Override
		public <T> T accept(TypeVisitorI<T> ask) {
			throw new RuntimeException();
		}

	};

	private RandomMFReaderMemory randomMFReader;

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
		this.numberofTests = testNumber;
		this.testNumberOffset = 0;
		// to collect info about the spec
		icc = new IdExpressionCreator();
		//
		randomMFReader = rnd;
		// TODO add variables
		idContains = icc.createIdExpression("contains", BoolType.BOOLTYPE);
	}

	@Override
	public AsmTestSuite getTestSuite() {
		// do not use real random values when choosing
		return getTestSuite(false);
	}

	public AsmTestSuite getTestSuite(boolean shuffle) {
		try {
			AsmTestSuite testSuite = new AsmTestSuite();
			for (int test = 0; test < numberofTests; test++) {
				// get the name
				String modelName = asm.getMain().getName();
				// build the random environment
				Environment env = new Environment(randomMFReader);
				// build the simulator
				Simulator simulator = new Simulator(modelName, asm, env);
				simulator.setShuffleFlag(shuffle);
				// simulator.createSimulatorRnd(modelName);
				String testName = "test" + Math.addExact(test, testNumberOffset);
				AsmTestSequence testsequence = new AsmTestSequence(new AsmTestCondition(testName, null));
				State state;
				int currentStep = 0;
				while (true) {
					// get the current state - duplicate
					state = new State(simulator.getCurrentState());
					// execute the step
					try {
						simulator.runNoCatchInv(1);
					} catch (InvalidInvariantException e) {
						System.out.println("Invariant violation");
						break;
					}
					// get previous controlled part
					state.applyLocationUpdates(simulator.previousState.getLocationMap());
					// get the monitored value of the previous step
					state.applyLocationUpdates(randomMFReader.getValues());
					// add this (previous) state to the sequence
					addState(testsequence, state);
					// if no step was required
					if (stepNumber <= 0) {
						break;
					}
					// reached the end of the sequence steps
					if (currentStep >= stepNumber - 1) {
						// add also the last step and exit
						addState(testsequence, simulator.getCurrentState());
						break;
					}
					// go to the next state
					currentStep++;
					// restart the env
					randomMFReader.clear();
				}
				testNumberOffset += 1;
				// add all the dynamic sets in the last state for now
				EList<Domain> domains = asm.getMain().getHeaderSection().getSignature().getDomain();
				for (Domain d : domains) {
					if ((d instanceof ConcreteDomain cd) && cd.getIsDynamic())
						addDomainElementsInStateAsContains(simulator.getCurrentState(), testsequence, cd);
					else if ((d instanceof AbstractTd atd) && atd.getIsDynamic())
						addDomainElementsInStateAsContains(simulator.getCurrentState(), testsequence, atd);
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

	private IdExpression idContains;

	private void addDomainElementsInStateAsContains(State state, AsmTestSequence testsequence, Domain d) {
		// create the Id for the set
		IdExpression idSet = icc.createIdExpression(d.getName(), dummyType);
		// get the values in domain d
		SetValue setV = state.read(d);
		setV.forEach(x -> {
			// if the element and the domain start with the name letters
			// then add as element of the domain
			if (x.toString().toLowerCase().startsWith(d.getName().toLowerCase())) {
				IdExpression idx = icc.createIdExpression(x.toString(), null);
				// contains(Set,elementx)
				FunctionTerm ft = new FunctionTerm(idContains, BoolType.BOOLTYPE, Arrays.asList(idSet, idx));
				testsequence.addAssignment(ft, "true", VarKind.CONTROLLED);
			}
		});
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public void setAsm(AsmCollection asm) {
		this.asm = asm;
	}

	public void setNumberofTests(int testNumer) {
		this.numberofTests = testNumer;
	}

	// add this state to the test sequence
	private void addState(AsmTestSequence testsequence, State state) {
		testsequence.addState();
		for (Entry<Location, Value> stateValues : state.getLocationMap().entrySet()) {
			Location location = stateValues.getKey();
			// TODO store the variables somewhere
			// check if monitored or controlled
			Function function = location.getSignature();
			boolean monitored = function instanceof MonitoredFunction;
			assert monitored || function instanceof ControlledFunction;
			Value[] elements = location.getElements();
			boolean isvar = elements.length == 0;
			String value = stateValues.getValue().toString();
			if (isvar) {
				Type type;
				if (function.getCodomain() instanceof asmeta.definitions.domains.StringDomain) {
					type = stringType;
					value = "\"" + value + "\"";
				} else {
					type = dummyType;
				}
				// create ID (with dummy type which is wrong)
				IdExpression varId = icc.createIdExpression(location.toString(), type);
				atgt.specification.location.Variable var = new atgt.specification.location.Variable(varId, null);
				if (monitored) {
					var.setMonitored();
				} else {
					var.setControlled();
				}
				testsequence.addAssignment(var, value);
			} else {
				assert elements.length >= 1 : Arrays.toString(elements);
				List<IdExpression> args = new ArrayList<>();
				for (Value v : elements) {
					Number n = IdExpressionCreator.parse(v.toString());
					if (v == null) {
						args.add(icc.createIdExpression(v.toString(), dummyType));
					} else {
						args.add(icc.createIdExpression(v.toString(), IntegerType.INTEGER_TYPE));
					}
				}
				IdExpression name = icc.createIdExpression(location.getSignature().getName(), dummyType);
				// type

				FunctionTerm ft = new FunctionTerm(name, dummyType, args);
				try {
					testsequence.addAssignment(ft, value, monitored ? VarKind.MONITORED : VarKind.CONTROLLED);
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
