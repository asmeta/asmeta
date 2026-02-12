package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.util.UnresolvedReferenceException;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.structure.DomainDefinition;

import org.asmeta.parser.ParseException;

/**
 * Test for Simulator class.  
 *
 */
public class RealIntervalTest extends BaseTest {
	
	@Test
	public void test01() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/testRanges1.asm");
		Domain dom = searchDomain("DiceValue2");
		SetValue values = sim.ruleEvaluator.termEval.getValues(dom);	
		sim.run(1);
		State s = sim.getCurrentState();
		System.out.println(s);
	}
	
	@Test
	public void test02() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/testRanges2.asm");
		Domain dom = searchDomain("DiceValue2");
		SetValue values = sim.ruleEvaluator.termEval.getValues(dom);		
		System.out.println(values); // dovrebbero essere con 0.1 invece sbaglia. ...

	}

}
