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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.util.UnresolvedReferenceException;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.structure.DomainDefinition;

/**
 * Test for Simulator class.
 *
 */
public class InterpreterTest extends BaseTest {

	Function f;
	Domain d;
	Value v;

	public static Collection<Boolean> data() {
		return Arrays.asList(Boolean.TRUE, Boolean.FALSE);
	}

	public void initInterpreterTest(boolean allowLazyEval) {
		TermEvaluator.setAllowLazyEval(allowLazyEval);
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// AsmParserTest.setUpLogger();
	}

	@AfterAll
	static void ripristina() throws Exception {
		// rirèroinstin il valore standard
		TermEvaluator.setAllowLazyEval(false);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test01(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ArithmeticExpr01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test02(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		assertThrows(ParseException.class,
				() -> sim = Util.getSimulatorForTestSpec("test/errors/np/ArithmeticExpr02.asm"));
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test03(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ArithmeticExpr03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(27), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test04(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/test_ge.asm");
		sim.run(1);
		f = searchFunction("geval");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test05(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/test_lt.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test06(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
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

	@MethodSource("data")
	@ParameterizedTest
	public void test07(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
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

	@MethodSource("data")
	@ParameterizedTest
	public void test08(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/BooleanExpr01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test09(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/RelationalExpr01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test10(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CondTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(22) }));
		assertEquals(new IntegerValue(11), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test11(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CondTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(1) }));
		assertEquals(new IntegerValue(11), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test12(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(new IntegerValue(22)))), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test13(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(3) }));
		assertEquals(new IntegerValue(33), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test14(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CaseTermPair.asm");
		sim.run(1);
		f = searchFunction("pos");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(12), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test15(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/LetTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(6), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test16(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/LetTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(33), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test17(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/SetCt01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(new IntegerValue(1), new IntegerValue(12),
				new IntegerValue(18), new IntegerValue(2), new IntegerValue(3), new IntegerValue(36),
				new IntegerValue(4), new IntegerValue(6), new IntegerValue(9)))), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test18(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/SetCt02.asm");
		sim.run(1);

		d = searchDomain("Orders");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(
				Arrays.asList(new ReserveValue("o1"), new ReserveValue("o2"), new ReserveValue("o3")))), v);

		d = searchDomain("Products");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(new ReserveValue("p1"), new ReserveValue("p2")))),
				v);

		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new SetValue(new HashSet<Value>(Arrays.asList(new ReserveValue("o1"), new ReserveValue("o2")))),
				v);

		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("o1") }));
		assertEquals(new EnumValue("PENDING"), v);

		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("o1") }));
		assertEquals(new EnumValue("PENDING"), v);

		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("o2") }));
		assertEquals(new EnumValue("PENDING"), v);

		f = searchFunction("orderState");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("o3") }));
		assertEquals(new EnumValue("PENDING"), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test19(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ExistTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test20(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ExistTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@Disabled
	@MethodSource("data") // ANGELO, ignore this test, I'm not sure it is correct 2021.05
	@ParameterizedTest
	public void test21(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ForallTerm01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test22(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ForallTerm02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.FALSE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test23(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/UpdateRule01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(13) }));
		assertEquals(new IntegerValue(-1), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test24(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
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

	@MethodSource("data")
	@ParameterizedTest
	public void test25(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter01.asm");
		sim.run(4);
		f = searchFunction("counter");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(200), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test26(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter02.asm");
		sim.run(3);
		f = searchFunction("counter1");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);

		f = searchFunction("counter2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(60), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test27(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/Counter03.asm");
		sim.run(1);
		f = searchFunction("counter2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(0), v);

		f = searchFunction("counter");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test28(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/CondRule01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(99), v);

		f = searchFunction("g");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(44), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test29(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/LetRule01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(90), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test30(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
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

	@MethodSource("data")
	@ParameterizedTest
	public void test31(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/LetRule03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(UndefValue.UNDEF, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test32(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ForRule01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(1), new IntegerValue(6) }));
		assertEquals(new IntegerValue(6), v);

		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(1), new IntegerValue(8) }));
		assertEquals(new IntegerValue(8), v);

		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(3), new IntegerValue(6) }));
		assertEquals(new IntegerValue(18), v);

		v = sim.currentState.read(new Location(f, new Value[] { new IntegerValue(3), new IntegerValue(8) }));
		assertEquals(new IntegerValue(24), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test33(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/ForRule02.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test38(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule01.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(17), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test39(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule02.asm");
		assertThrows(UpdateClashException.class, () -> sim.run(1));
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test40(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/MacroCallRule03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(37), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test41(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/TermAsRule.asm");
		sim.run(1);
		// nothing to assert, expected a correct parsing
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test42(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/SeqRuleATD.asm");
		sim.run(1);
		d = searchDomain("NumCard");
		v = sim.currentState.read(d);
		assertEquals(new SetValue(new HashSet<Value>(
				Arrays.asList(new ReserveValue("card1"), new ReserveValue("card2"), new ReserveValue("card3")))), v);

		f = searchFunction("check");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test44(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/domains/extendDomains.asm");
		sim.run(1);
		f = searchFunction("f1");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), v);
		f = searchFunction("f2");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(2), v);
		ConcreteDomain dom1 = (ConcreteDomain) searchDomain("Dom1");
		// TODO the dom1 should be Dom1={dom1!2} or something similar

	}

	@MethodSource("data")
	@ParameterizedTest
	public void test45(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro03.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt") }));
		assertEquals(new IntegerValue(3), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt2") }));
		assertEquals(new IntegerValue(7), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanBool") }));
		assertEquals(new IntegerValue(25), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanBool2") }));
		assertEquals(new IntegerValue(1000), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test46(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro04.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt") }));
		assertEquals(new IntegerValue(3), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt2") }));
		assertEquals(new IntegerValue(7), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanBool") }));
		assertEquals(new IntegerValue(25), v);

		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanBool2") }));
		assertEquals(new IntegerValue(1000), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test47(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro05.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt") }));
		assertEquals(new IntegerValue(3), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test48(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		File f2 = new File(TestOneSpec.FILE_BASE + "/test/errors/np/macro06.asm");
		assertTrue(f2.exists());
		assertThrows(ParseException.class, () ->
		// ERROR: Unresolved reference to r_write(Integer, Chan)
		ASMParser.setUpReadAsm(f2));
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test49(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		File f2 = new File(TestOneSpec.FILE_BASE + "/test/simulator/macro/macro07.asm");
		assertTrue(f2.exists());
		AsmCollection p = ASMParser.setUpReadAsm(f2);
		assertTrue(p != null);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test50(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro08.asm");
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[] { new ReserveValue("chanInt") }));
		assertEquals(new IntegerValue(3), v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testString01(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/StringExpr01.asm");
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(new StringValue("pippo"), v);// se le stringhe sono ritornate senza doppi apici
		sim.run(1);
		f = searchFunction("f");
		v = sim.currentState.read(new Location(f, new Value[0]));
		assertEquals(BooleanValue.TRUE, v);
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testString02(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/StringExpr03.asm");
		Value me = sim.currentState.readAbstractConst("me");
		assertTrue(me instanceof ReserveValue, me.getClass().toString());
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[] { me }));
		assertEquals(new StringValue("angelo"), v);// se le stringhe sono ritornate senza i doppi apici
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testTEMP(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		// sim = Util.getSimulatorForTestSpec("examples/fsmSle/__temp.asm");
		sim = Util.getSimulatorForTestSpec("examples/fsmsemantics/Sle/ASM_even.asm");
		Value fsm = sim.currentState.readAbstractConst("evenFsm");
		assertTrue(fsm instanceof ReserveValue, fsm.getClass().toString());
		f = searchFunction("currentState");
		Value cs = sim.currentState.read(new Location(f, new Value[] { fsm }));
		assertEquals(new ReserveValue("even"), cs);
		f = searchFunction("name");
		v = sim.currentState.read(new Location(f, new Value[] { cs }));
		assertNotNull(v);
		assertTrue(v instanceof StringValue, v.getClass().toString());
		assertEquals(new StringValue("even"), v);// se le stringhe sono ritornate senza i doppi apici
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test_sequence_insertAt(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/STDL/test_insertAt_Sequence.asm");
		sim.runUntilEmpty();
		f = searchFunction("list");
		Value sequence = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("[4,1,1,2,3,4]", sequence.toString());
	}

	@MethodSource("data")
	@ParameterizedTest
	public void test_count(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/STDL/test_Count.asm");
		sim.run(1);
		f = searchFunction("countA");
		Value value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("1", value.toString());

		f = searchFunction("countB");
		value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("0", value.toString());

		f = searchFunction("count1");
		value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("2", value.toString());

		f = searchFunction("count2");
		value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("3", value.toString());

		f = searchFunction("count3");
		value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("3", value.toString());
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testConcrDomDef(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/parser/concrDomDef.asm");
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		Assertions.assertDoesNotThrow(() -> {
			sim.run(10);
		}, "Un invariante del modello e' stato violato.");
		f = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("0", value.toString());
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testConcrDomEnv(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
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

	@MethodSource("data")
	@ParameterizedTest
	public void testExtendSeqProblem(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/extendSeqProblem.asm");
		sim.setShuffleFlag(true);
		try {
			sim.run(100);
		} catch (UnresolvedReferenceException e) {
			// e.printStackTrace();
			fail("Errata gestione della extend e del seq. Vedere bug su SourceForge.");
		} finally {
			sim.setShuffleFlag(false);
		}
	}

	@MethodSource("data")
	@ParameterizedTest
	public void testWhile(boolean allowLazyEval) throws Exception {
		initInterpreterTest(allowLazyEval);
		sim = Util.getSimulatorForTestSpec("test/simulator/turbo/while.asm");
		sim.run(1);
		f = searchFunction("x");
		Value value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("10", value.toString());
		sim.run(10);
		value = sim.currentState.read(new Location(f, new Value[] {}));
		assertEquals("10", value.toString());
	}
}
