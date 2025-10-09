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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.definitions.Function;

public class ChooseTest extends BaseTest {
	
	private static final String TEST_SIMULATOR_CHOOSERULE = "test/simulator/chooserule/";

	@BeforeClass
	public static void setUpLogger(){
		//AsmParserTest.setUpLogger();
		//
//		Logger log = Logger.getLogger("org.asmeta.simulator");
//		if (!log.getAllAppenders().hasMoreElements())
//			log.addAppender(new ConsoleAppender(new SimpleLayout()));
//		log.setLevel(Level.ALL);
	}
	
	static final String chooseExample = "chooseIntWithGuard.asm"; 
	
	@Test
	public void testNonDetermistic() throws Exception{
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE+chooseExample);
		sim.setShuffleFlag(true);
		List<Long> integers = new ArrayList<>();
		for(int i = 1; i < 10; i++){
			UpdateSet updateSet = sim.doOneStep();	
			Function f = searchFunction("a");
			Value value = sim.currentState.read(new Location(f, new Value[]{}));
			integers.add((Long) (value.getValue()));
		}
		checkRandomIntegers(integers);
		integers.stream().forEach(x -> assertTrue(x > 5));
	} 
	
	@Test
	public void testDetermistic() throws Exception{
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE+chooseExample);
		sim.setShuffleFlag(false);
		List<Long> integers = new ArrayList<>();
		for(int i = 1; i < 10; i++){
			UpdateSet updateSet = sim.doOneStep();	
			Function f = searchFunction("a");
			Value value = sim.currentState.read(new Location(f, new Value[]{}));
			integers.add((Long) (value.getValue()));
		}
		integers.stream().forEach(x -> assertTrue(x == 6));
	} 
	
	@Test
	public void testRandomIntWithTrue() throws Exception{
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseRandomInt.asm");
		List<Long> integers = new ArrayList<>();
		for(int i = 1; i < 10; i++){
			UpdateSet updateSet = sim.doOneStep();
			Function f = searchFunction("myInt");
			Value value = sim.currentState.read(new Location(f, new Value[]{}));
			integers.add((Long) (value.getValue()));
		}
		checkRandomIntegers(integers);
	}

	private void checkRandomIntegers(List<Long> integers) {
		//there are not two pairs of equals number (itis vwery unlikely) 
		assertTrue(integers.get(0) != integers.get(1) || integers.get(2) != integers.get(3));
	} 
	
	@Test
	public void testIntWithCond() throws Exception{
		Simulator sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseOverInteger.asm");
		try{
			UpdateSet updateSet = sim.doOneStep();	
		} catch(RuntimeException re) {
			assertTrue(re.getMessage().contains("Infinite"));
		}
	}
	@Test
	public void testChooseBoolean() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "chooseBoolean.asm");
		sim.setShuffleFlag(true);
		sim.run(1000);//la probabilita' che il test fallisca (anche se il simulatore e' corretto) e' 1/(2^1000)
		Function f = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(f, new Value[]{}));
		//System.out.println("fooA = " + value.toString());
		assertFalse(value.toString().equals("0"));
		f = searchFunction("fooB");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		//System.out.println("fooB = " + value.toString());
		assertFalse(value.toString().equals("0"));
	}
	@Test
	public void test34() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseInConcrete.asm");
		sim.setShuffleFlag(false);
		sim.run(1);		
		Function f = searchFunction("y");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}

	@Test
	public void test35() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE +"ChooseRule01.asm");
		sim.run(1);		
		Function f = searchFunction("f");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(20), v);
	}

	@Test
	public void test36() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseRule02.asm");
		sim.run(1);		
		Function f = searchFunction("f");
		Value<?> v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(2), new IntegerValue(5)}));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Test
	public void test37() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseRule03.asm");
		sim.run(1);		
		Function f = searchFunction("f");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(UndefValue.UNDEF, v);
	}
	@Test
	public void testCondFalseIfNone() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseRule03IfNone.asm");
		sim.run(1);		
		Function f = searchFunction("f");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(0), v);
	}
	@Test
	public void testAD() throws Exception {
		sim = Util.getSimulatorForTestSpec(TEST_SIMULATOR_CHOOSERULE + "ChooseRuleAD.asm");
		sim.run(1);		
		Function f = searchFunction("a");
		Value<?> v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals("o", v.toString());
	}

}
