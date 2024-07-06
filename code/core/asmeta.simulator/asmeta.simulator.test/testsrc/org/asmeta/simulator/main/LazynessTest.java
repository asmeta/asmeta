/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
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
public class LazynessTest extends BaseTest {

	    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        TermEvaluator.setAllowLazyEval(true);
	}

	@AfterClass
	public static void ripristina() throws Exception {
		// rirèroinstin il valore standard
		TermEvaluator.recoverAllowLazyEval();
	}

	@Test
	public void testLazy() throws Exception{
		// f1 --> TRUE
		//main rule r_main =	g1 := f1 and f2
		MonFuncReader monFuncReader = new MonFuncReader() {
			@Override
			public Value readValue(Location location, State state) {
				System.out.println("location " + location);
				if (location.toString().equals("f1")) return BooleanValue.FALSE;
				fail("do not ask other");
				return null;
			}
			@Override
			public boolean supportsLazyTermEval() {
				return true;
			}
		};
		Environment env = new Environment(monFuncReader);
		sim = Simulator.createSimulator(TestOneSpec.FILE_BASE + "test/simulator/monitoredLazy.asm", env);
		sim.run(1);		
		Function f = searchFunction("g1");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);
	}
	
	@Test
	public void testLazyDerived() throws Exception{
		// f1 --> TRUE
		//main rule r_main =	g1 := d1
		MonFuncReader monFuncReader = new MonFuncReader() {
			@Override
			public Value readValue(Location location, State state) {
				System.out.println("location " + location);
				if (location.toString().equals("f1")) return BooleanValue.FALSE;
				fail("do not ask other");
				return null;
			}
			@Override
			public boolean supportsLazyTermEval() {
				return true;
			}
		};
		Environment env = new Environment(monFuncReader);
		sim = Simulator.createSimulator(TestOneSpec.FILE_BASE + "test/simulator/monitoredLazyDer.asm", env);
		sim.run(1);		
		Function f = searchFunction("g1");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);
	}
	

	// a test with the ennvironment--> No lazyness should be 
	@Test
	public void testLazyEnv() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/monitoredTest.asm",
				"test/simulator/monitoredTest01.env");
		sim.run(1);		
		Function f = searchFunction("g1");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1234), v);
		
		f = searchFunction("g2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);

		f = searchFunction("g3");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SequenceValue(new ArrayList<Value>(Arrays.asList(
				new IntegerValue(1), new IntegerValue(2), new IntegerValue(4567)))), 
				v);

		f = searchFunction("g4");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SequenceValue(new ArrayList<Value>(Arrays.asList(
				new TupleValue(new IntegerValue(0), BooleanValue.FALSE), 
				new TupleValue(new IntegerValue(1), BooleanValue.TRUE)))), 
				v);
	}
}
