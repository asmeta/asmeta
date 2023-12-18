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

public class AsmTGBySimulationOnAction extends AsmTestGeneratorBySimulation {

	public AsmTGBySimulationOnAction(AsmCollection asm, int stepNumber, int testNumber) {
		super(asm, stepNumber, testNumber);
		// TODO Auto-generated constructor stub
	}

}
