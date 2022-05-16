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
import java.util.HashSet;
import java.util.Iterator;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
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
import org.junit.Ignore;
import org.junit.Test;

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
public class InterpreterTest extends BaseTest {

	Function f;
	Domain d;
	Value v;

	@Test
	public void test01() throws Exception {		
		sim = Util.getSimulatorForTestSpec("test/simulator/ArithmeticExpr01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}
	
	@Test(expected=ParseException.class)
	public void test02() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/errors/np/ArithmeticExpr02.asm");		
		sim.run(1);	
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(40), v);
	}
	
	@Test
	public void test03() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ArithmeticExpr03.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(27), v);
	}
	
	@Test
	public void test04() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/test_ge.asm");
		sim.run(1);
		f = searchFunction("geval");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}
	
	@Test
	public void test05() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/test_lt.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}
	
	@Test
	public void test06() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/nat_int_numbers.asm");
		sim.run(1);
		f = searchFunction("f1");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(0), v);
		f = searchFunction("f2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
		f = searchFunction("f3");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(0), v);
	}
	
	@Test
	public void test07() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ntoi.asm");
		sim.run(1);
		f = searchFunction("counter2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
		f = searchFunction("counter3");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
		f = searchFunction("counter");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}
	
	@Test
	public void test08() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/BooleanExpr01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);
	}
	
	@Test
	public void test09() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/RelationalExpr01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}
	
	@Test
	public void test10() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CondTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[]{new IntegerValue(22)}));
		assertEquals(new IntegerValue(11), v);
	}
	
	@Test
	public void test11() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CondTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[]{new IntegerValue(1)}));
		assertEquals(new IntegerValue(11), v);
	}
	
	@Test
	public void test12() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new IntegerValue(22)))), 
				v);
	}
	
	@Test
	public void test13() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[]{new IntegerValue(3)}));
		assertEquals(new IntegerValue(33), v);
	}
	
	@Test
	public void test14() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTermPair.asm");
		sim.run(1);
		f = searchFunction("pos");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(12), v);
	}
	
	@Test
	public void test15() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/LetTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(6), v);
	}
	
	@Test
	public void test16() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/LetTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(33), v);
	}
	
	@Test
	public void test17() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/SetCt01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new IntegerValue(1), new IntegerValue(12), new IntegerValue(18), 
				new IntegerValue(2), new IntegerValue(3), new IntegerValue(36), 
				new IntegerValue(4), new IntegerValue(6), new IntegerValue(9)))), 
				v);
	}

	@Test
	public void test18() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/SetCt02.asm");
		sim.run(1);
		
		d = searchDomain("Orders");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new ReserveValue("o1"), new ReserveValue("o2"), new ReserveValue("o3")))), 
				v);
		
		d = searchDomain("Products");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new ReserveValue("p1"), new ReserveValue("p2")))), 
				v);
		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new ReserveValue("o1"), new ReserveValue("o2")))), 
				v);
		
		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] {new ReserveValue("o1")}));
		assertEquals(new EnumValue("PENDING"), v);
		
		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] {new ReserveValue("o1")}));
		assertEquals(new EnumValue("PENDING"), v);		

		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] {new ReserveValue("o2")}));
		assertEquals(new EnumValue("PENDING"), v);
		
		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] {new ReserveValue("o3")}));
		assertEquals(new EnumValue("PENDING"), v);		
	}

	@Test
	public void test19() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ExistTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Test
	public void test20() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ExistTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);	
	}

	@Ignore // ANGELO, ignore this test, I'm not sure it is correct 2021.05
	@Test
	public void test21() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ForallTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);			
	}

	@Test
	public void test22() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ForallTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);			
	}

	@Test
	public void test23() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/UpdateRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {new IntegerValue(13)}));
		assertEquals(new IntegerValue(-1), v);	
	}

	@Test
	public void test24() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/SeqRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(33), v);
		
		f = searchFunction("g");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(15), v);
		
		f = searchFunction("z");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(150), v);		
	}

	@Test
	public void test25() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter01.asm");
		sim.run(4);		
		f = searchFunction("counter");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(200), v);
	}

	@Test
	public void test26() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter02.asm");
		sim.run(3);		
		f = searchFunction("counter1");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
		
		f = searchFunction("counter2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(60), v);
	}

	@Test
	public void test27() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter03.asm");
		sim.run(1);		
		f = searchFunction("counter2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(0), v);
		
		f = searchFunction("counter");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
	}

	@Test
	public void test28() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/CondRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(99), v);
		
		f = searchFunction("g");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(44), v);
	}

	@Test
	public void test29() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/LetRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(90), v);
	}

	@Test
	public void test30() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/LetRule02.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(9), v);
		
		f = searchFunction("g");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(100), v);

		f = searchFunction("z");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(63), v);

	}

	@Test
	public void test31() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/LetRule03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(UndefValue.UNDEF, v);
	}

	@Test
	public void test32() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ForRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(1), new IntegerValue(6)}));
		assertEquals(new IntegerValue(6), v);
		
		v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(1), new IntegerValue(8)}));
		assertEquals(new IntegerValue(8), v);		

		v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(3), new IntegerValue(6)}));
		assertEquals(new IntegerValue(18), v);		

		v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(3), new IntegerValue(8)}));
		assertEquals(new IntegerValue(24), v);
	}

	@Test
	public void test33() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ForRule02.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}

	@Test
	public void test34() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ChooseInConcrete.asm");
		sim.setShuffleFlag(false);
		sim.run(1);		
		f = searchFunction("y");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}

	@Test
	public void test35() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ChooseRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(20), v);
	}

	@Test
	public void test36() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ChooseRule02.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new IntegerValue(2), new IntegerValue(5)}));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Test
	public void test37() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ChooseRule03.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(UndefValue.UNDEF, v);
	}

	@Test
	public void test38() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule01.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(17), v);
	}

	@Test (expected = UpdateClashException.class)
	public void test39() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule02.asm");
		sim.run(1);
	}

	@Test
	public void test40() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule03.asm");
		sim.run(1);		
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(37), v);
	}

	@Test
	public void test41() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/TermAsRule.asm");
		sim.run(1);
		// nothing to assert, expected a correct parsing
	}

	@Test
	public void test42() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/SeqRuleATD.asm");
		sim.run(1);		
		d = searchDomain("NumCard");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(
				new ReserveValue("card1"), new ReserveValue("card2"), new ReserveValue("card3")))), 
				v);

		f = searchFunction("check");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Test
	public void test43() throws Exception {
		sim = Util.getSimulatorForTestSpec(
				"test/simulator/monitoredTest.asm",
				"test/simulator/monitoredTest01.env");
		sim.run(1);		
		f = searchFunction("g1");
		v = sim.currentState.read(new Location(f, new Value[0]));
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
	
	@Test
	public void test44() throws Exception {		
		sim = Util.getSimulatorForTestSpec("test/simulator/domains/extendDomains.asm");
		sim.run(1);		
		f = searchFunction("f1");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
		f = searchFunction("f2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
	}

	@Test
	public void test45() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt")}));
		assertEquals(new IntegerValue(3), v);
		
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt2")}));
		assertEquals(new IntegerValue(7), v);

		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanBool")}));
		assertEquals(new IntegerValue(25), v);

		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanBool2")}));
		assertEquals(new IntegerValue(1000), v);
	}
	
	@Test
	public void test46() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro04.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt")}));
		assertEquals(new IntegerValue(3), v);
		
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt2")}));
		assertEquals(new IntegerValue(7), v);

		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanBool")}));
		assertEquals(new IntegerValue(25), v);

		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanBool2")}));
		assertEquals(new IntegerValue(1000), v);
	}
	
	@Test
	public void test47() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro05.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt")}));
		assertEquals(new IntegerValue(3), v);		
	}
	
	@Test(expected=ParseException.class)
	public void test48() throws Exception {
		File f2 = new File(TestOneSpec.FILE_BASE +  "/test/errors/np/macro06.asm");
		assertTrue(f2.exists());
		AsmCollection p = ASMParser.setUpReadAsm(f2);
		// ERROR: Unresolved reference to r_write(Integer, Chan)
		assertTrue(p == null);
	}

	@Test
	public void test49() throws Exception {
		File f2 = new File(TestOneSpec.FILE_BASE + "/test/simulator/macro/macro07.asm");
		assertTrue(f2.exists());
		AsmCollection p = ASMParser.setUpReadAsm(f2);
		assertTrue(p != null);
	}

	@Test
	public void test50() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro08.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] {
				new ReserveValue("chanInt")}));
		assertEquals(new IntegerValue(3), v);		
	}

	@Test
	public void testString01() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/StringExpr01.asm");
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new StringValue("pippo"), v);//se le stringhe sono ritornate senza doppi apici
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Test
	public void testString02() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/StringExpr03.asm");
		Value me = sim.currentState.readAbstractConst("me");
		assertTrue(me.getClass().toString(),me instanceof ReserveValue);
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[] {me}));
		assertEquals(new StringValue("angelo"), v);//se le stringhe sono ritornate senza i doppi apici
	}

	@Test
	public void testTEMP() throws Exception {
		//sim = Util.getSimulatorForTestSpec("examples/fsmSle/__temp.asm");
		sim = Util.getSimulatorForTestSpec("examples/fsmsemantics/Sle/ASM_even.asm");
		Value fsm = sim.currentState.readAbstractConst("evenFsm");
		assertTrue(fsm.getClass().toString(),fsm instanceof ReserveValue);
		f = searchFunction("currentState");
		Value cs = sim.currentState.read(new Location(f, new Value[] {fsm}));
		assertEquals(new ReserveValue("even"), cs);
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[] {cs}));
		assertNotNull(v);
		assertTrue(v.getClass().toString(), v instanceof StringValue);
		assertEquals(new StringValue("even"), v);//se le stringhe sono ritornate senza i doppi apici		
	}

	@Test
	public void test_sequence_insertAt() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/STDL/test_insertAt_Sequence.asm");
		sim.runUntilEmpty();
		f = searchFunction("list");
		Value sequence = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("[4,1,1,2,3,4]", sequence.toString());
	}

	@Test
	public void test_count() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/STDL/test_Count.asm");
		sim.run(1);
		f = searchFunction("countA");
		Value value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("1", value.toString());
		
		f = searchFunction("countB");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("0", value.toString());
		
		f = searchFunction("count1");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("2", value.toString());
		
		f = searchFunction("count2");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("3", value.toString());
		
		f = searchFunction("count3");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("3", value.toString());
	}

	@Test
	public void testConcrDomDef() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/parser/concrDomDef.asm");
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		try {
			sim.run(10);
		}
		catch(InvalidInvariantException e) {
			fail("Un invariante del modello e' stato violato.");
		}
		f = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("0", value.toString());
	}
	@Test
	public void testConcrDomEnv() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/ConcreteDomain.asm");
		f = searchFunction("moneta");		
		assertNotNull(f);
		ConcreteDomain domain = (ConcreteDomain) f.getCodomain();
		DomainDefinition def = domain.getDefinition();
		TermEvaluator eval = new TermEvaluator(sim.currentState, null, null);
		SetValue set = (SetValue) eval.visit(def.getBody());		
		assertTrue(set.getValue().contains(new IntegerValue(50)));
		assertTrue(set.getValue().contains(new IntegerValue(200)));
	}

	@Test
	public void testExtendSeqProblem() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/extendSeqProblem.asm");
		sim.setShuffleFlag(true);
		try {
			sim.run(100);
		}
		catch(UnresolvedReferenceException e) {
			//e.printStackTrace();
			fail("Errata gestione della extend e del seq. Vedere bug su SourceForge.");
		}
		finally {
			sim.setShuffleFlag(false);
		}
	}

	@Test
	public void testChooseBoolean() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/chooseBoolean.asm");
		sim.setShuffleFlag(true);
		sim.run(1000);//la probabilita' che il test fallisca (anche se il simulatore e' corretto) e' 1/(2^1000)
		f = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(f, new Value[]{}));
		//System.out.println("fooA = " + value.toString());
		assertFalse(value.toString().equals("0"));
		f = searchFunction("fooB");
		value = sim.currentState.read(new Location(f, new Value[]{}));
		//System.out.println("fooB = " + value.toString());
		assertFalse(value.toString().equals("0"));
	}

	@Test
	public void testWhile() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/turbo/while.asm");
		sim.run(1);
		f = searchFunction("x");
		Value value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("10", value.toString());
		sim.run(10);
		value = sim.currentState.read(new Location(f, new Value[]{}));
		assertEquals("10", value.toString());
	}
}
