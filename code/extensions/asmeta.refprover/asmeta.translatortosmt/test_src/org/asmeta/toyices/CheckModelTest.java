package org.asmeta.toyices;

import static org.asmeta.toyices.Utils.location;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckModelTest extends TestMethods {

	@Test
	public void testBoard() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(and (= (board0 1) false) (= (board0 2) false))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(or (and (= (board1 1) false) (= (board1 2) true)) (and (= (board1 1) true) (= (board1 2) false)))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(and (= (board2 1) true) (= (board2 2) true))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(and (= (board3 1) true) (= (board3 2) true))");
		commands.add(state);
		testAssertCommands("models/board.asm", commands, true);
	}

	@Test
	public void testBoard2() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(and (not " + location("board0", new String[]{"1", "1"}) + ") (not " + location("board0", new String[]{"1", "2"}) + ") (not " + location("board0", new String[]{"2", "1"})+ ") (not " + location("board0", new String[]{"2", "2"}) + "))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(and (/= " + location("board1", new String[]{"1", "1"}) + " " + location("board1", new String[]{"1", "2"}) + ") (/= " + location("board1", new String[]{"2", "1"})+ " " + location("board1", new String[]{"2", "2"}) + "))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(and " + location("board2", new String[]{"1", "1"}) + " " + location("board2", new String[]{"1", "2"}) + " " + location("board2", new String[]{"2", "1"})+ " " + location("board2", new String[]{"2", "2"}) + ")");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(and " + location("board3", new String[]{"1", "1"}) + " " + location("board3", new String[]{"1", "2"}) + " " + location("board3", new String[]{"2", "1"})+ " " + location("board3", new String[]{"2", "2"}) + ")");
		commands.add(state);
		testAssertCommands("models/board2.asm", commands, true);
	}

	@Test
	public void testCaseRule() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		test("models/caseRule.asm", expectedValues, true);
	}

	@Test
	public void testCaseRule2() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		test("models/caseRule2.asm", expectedValues, true);
	}

	@Test
	public void testCaseRule3() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		test("models/caseRule3.asm", expectedValues, true);
	}

	@Test
	public void testCaseTerm() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		test("models/caseTerm.asm", expectedValues, true);
	}

	@Test
	public void testCaseTerm2() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		test("models/caseTerm2.asm", expectedValues, true);
	}

	@Test
	public void testCaseTerm3() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "EnumDomUNDEF"});
		expectedValues.add(state);
		test("models/caseTerm3.asm", expectedValues, true);
	}

	@Test
	public void testCaseTerm4() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "EnumDomUNDEF"});
		expectedValues.add(state);
		test("models/caseTerm4.asm", expectedValues, true);
	}

	@Test
	public void testChoose() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo0", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo1", "EnumDom", "BB", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo2", "EnumDom", "BB", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo3", "EnumDom", "BB", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo4", "EnumDom", "BB", "CC"});
		expectedValues.add(state);
		testAssertOrEqs("models/choose.asm", expectedValues, true);
	}

	@Test
	public void testChoose2() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo0", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo1", "EnumDom", "BB", "CC"});
		expectedValues.add(state);
		testAssertOrEqs("models/choose2.asm", expectedValues, true);
	}

	@Test
	public void testChoose3() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(= foo0 1)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(/= foo1 3)");
		state.add("(or (= foo1 1) (= foo1 2))");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(/= foo2 3)");
		state.add("(or (= foo2 1) (= foo2 2))");
		commands.add(state);
		testAssertCommands("models/choose3.asm", commands, true);
	}

	@Test
	public void testChoose5() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 false) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 true) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, false);
	}

	@Test
	public void testChoose5_1() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(or "
				+ "(and (= (foo2 false) (foo1 false)) (= (foo2 true) (not (foo1 true))))"
				+ "(and (= (foo2 false) (not (foo1 false))) (= (foo2 true) (foo1 true)))"
				+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(not (or "
				+ "(and (= (foo2 false) (foo1 false)) (= (foo2 true) (not (foo1 true))))"
				+ "(and (= (foo2 false) (not (foo1 false))) (= (foo2 true) (foo1 true)))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose5.asm", commandExpRes, false);
	}

	@Test
	public void testChoose6() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 false) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 true) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, false);
	}

	@Test
	public void testChoose6_1() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(and (foo2 false) (foo2 true))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(not (and (foo2 false) (foo2 true)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose6.asm", commandExpRes, false);
	}

	@Test
	public void testChoose7() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose7.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 false) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose7.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 true) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose7.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose7.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (and (= (foo0 false) false) (= (foo0 true) false)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose7.asm", commandExpRes, false);
	}

	@Test
	public void testChoose8() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 false) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 true) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo1 false) true) (= (foo1 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (and (= (foo1 false) true) (= (foo1 true) false)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(and (= (foo2 false) true) (= (foo2 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(not (and (= (foo2 false) true) (= (foo2 true) false)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);

		for(int i = 2; i <= 10; i++) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			for(int j = 1; j <= i; j++) {
				commandExpRes.add(new ArrayList<String[]>());
			}
			state = new ArrayList<String[]>();//(i + 1)th step
			state.add(new String[]{"(and (= (foo" + (i + 1) + " false) true) (= (foo" + (i + 1) + " true) false))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);

			commandExpRes = new ArrayList<List<String[]>>();//new run
			for(int j = 1; j <= i; j++) {
				commandExpRes.add(new ArrayList<String[]>());
			}
			state = new ArrayList<String[]>();//(i + 1)th step
			state.add(new String[]{"(not (and (= (foo" + (i + 1) + " false) true) (= (foo" + (i + 1) + " true) false)))", "unsat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/choose8.asm", commandExpRes, false);
		}
	}

	@Test
	public void testChoose9() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) false) (= (foo0 true) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose9.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 false) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose9.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 true) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose9.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose9.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
								+ "(and (= (foo1 false) false) (= (foo1 true) true))"
								+ "(and (= (foo1 false) true) (= (foo1 true) false))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose9.asm", commandExpRes, false);
	}

	@Test
	public void testChoose10() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 AA) false) (= (foo0 BB) false) (= (foo0 CC) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 AA) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 BB) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 CC) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 AA) true) (= (foo1 BB) false) (= (foo1 CC) false))"
								+ "(and (= (foo1 AA) false) (= (foo1 BB) true) (= (foo1 CC) false))"
								+ "(and (= (foo1 AA) false) (= (foo1 BB) false) (= (foo1 CC) true))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
									+ "(and (= (foo1 AA) true) (= (foo1 BB) false) (= (foo1 CC) false))"
									+ "(and (= (foo1 AA) false) (= (foo1 BB) true) (= (foo1 CC) false))"
									+ "(and (= (foo1 AA) false) (= (foo1 BB) false) (= (foo1 CC) true))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose10.asm", commandExpRes, false);
	}

	@Test
	public void testChoose11() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 AA) false) (= (foo0 BB) false) (= (foo0 CC) false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 AA) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 BB) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= (foo0 CC) true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 AA) true) (= (foo1 BB) false) (= (foo1 CC) false))"
								+ "(and (= (foo1 AA) false) (= (foo1 BB) true) (= (foo1 CC) false))"
								+ "(and (= (foo1 AA) false) (= (foo1 BB) false) (= (foo1 CC) true))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
									+ "(and (= (foo1 AA) true) (= (foo1 BB) false) (= (foo1 CC) false))"
									+ "(and (= (foo1 AA) false) (= (foo1 BB) true) (= (foo1 CC) false))"
									+ "(and (= (foo1 AA) false) (= (foo1 BB) false) (= (foo1 CC) true))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);
		
		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(or "
								+ "(and (= (foo2 AA) true) (= (foo2 BB) true) (= (foo2 CC) false))"
								+ "(and (= (foo2 AA) true) (= (foo2 BB) false) (= (foo2 CC) true))"
								+ "(and (= (foo2 AA) false) (= (foo2 BB) true) (= (foo2 CC) true))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(not (or "
									+ "(and (= (foo2 AA) true) (= (foo2 BB) true) (= (foo2 CC) false))"
									+ "(and (= (foo2 AA) true) (= (foo2 BB) false) (= (foo2 CC) true))"
									+ "(and (= (foo2 AA) false) (= (foo2 BB) true) (= (foo2 CC) true))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		state = new ArrayList<String[]>();//third step
		state.add(new String[]{"(and (= (foo3 AA) true) (= (foo3 BB) true) (= (foo3 CC) true))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		state = new ArrayList<String[]>();//third step
		state.add(new String[]{"(not (and (= (foo3 AA) true) (= (foo3 BB) true) (= (foo3 CC) true)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose11.asm", commandExpRes, false);
	}

	@Test
	public void testChoose12() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) AA) (= (foo0 true) AA))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(/= (foo0 false) AA)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(/= (foo0 true) AA)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 false) BB) (= (foo1 true) AA))"
								+ "(and (= (foo1 false) AA) (= (foo1 true) BB))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo1 false) BB) (= (foo1 true) AA))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo1 false) AA) (= (foo1 true) BB))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
									+ "(and (= (foo1 false) BB) (= (foo1 true) AA))"
									+ "(and (= (foo1 false) AA) (= (foo1 true) BB))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose12.asm", commandExpRes, false);
	}

	@Test
	public void testChoose13() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= (foo0 false) AA) (= (foo0 true) AA))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(/= (foo0 false) AA)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(/= (foo0 true) AA)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= (foo1 false) BB) (= (foo1 true) BB))"
								+ "(and (= (foo1 false) AA) (= (foo1 true) BB))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo1 false) BB) (= (foo1 true) BB))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= (foo1 false) AA) (= (foo1 true) BB))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
									+ "(and (= (foo1 false) BB) (= (foo1 true) BB))"
									+ "(and (= (foo1 false) AA) (= (foo1 true) BB))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose13.asm", commandExpRes, false);
	}

	@Test
	public void testChoose14() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(and (= fooA0 false) (= fooB0 false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= fooA0 true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(= fooB0 true)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(or "
								+ "(and (= fooA1 true) (= fooB1 false))"
								+ "(and (= fooA1 false) (= fooB1 true))"
								+ ")", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= fooA1 true) (= fooB1 false))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(and (= fooA1 false) (= fooB1 true))", "sat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, false);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//first step
		state.add(new String[]{"(not (or "
									+ "(and (= fooA1 true) (= fooB1 false))"
									+ "(and (= fooA1 false) (= fooB1 true))"
								+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/choose14.asm", commandExpRes, false);
	}

	@Test
	public void testChooseOtherwise() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		test("models/chooseOtherwise.asm", expectedValues, true);
	}

	@Test
	public void testDerived() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(= foo0 AA)");
		state.add("(= der0 CC)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo1 CC)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo2 CC)");
		state.add("(= der1 BB)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo3 CC)");
		state.add("(= der2 BB)");
		commands.add(state);
		testAssertCommands("models/derived.asm", commands, true);
	}

	@Test
	public void testDerived2() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(= foo0 0)");
		state.add("(= der0 1)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo1 1)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo2 2)");
		state.add("(= der1 2)");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= foo3 0)");
		state.add("(= der2 0)");
		commands.add(state);
		testAssertCommands("models/derived2.asm", commands, true);
	}

	@Test
	public void testForall() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		test("models/forall.asm", expectedValues, true);
	}

	@Test
	public void testForall2() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"(foo0 AA)", "EnumDom", "AA"});
		state.add(new String[]{"(foo0 BB)", "EnumDom", "AA"});
		state.add(new String[]{"(foo0 CC)", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"(foo1 AA)", "EnumDom", "BB"});
		state.add(new String[]{"(foo1 BB)", "EnumDom", "BB"});
		state.add(new String[]{"(foo1 CC)", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"(foo2 AA)", "EnumDom", "BB"});
		state.add(new String[]{"(foo2 BB)", "EnumDom", "BB"});
		state.add(new String[]{"(foo2 CC)", "EnumDom", "BB"});
		expectedValues.add(state);
		testAssertEqs("models/forall2.asm", expectedValues, true);
	}

	@Test
	public void testForallForall() throws Exception {
		String[] enumValues = {"AA", "BB", "CC"};
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo0", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", "AA"});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo1", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", "BB"});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo2", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", "CC"});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo3", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", "AA"});
			}
		}
		expectedValues.add(state);
		testAssertEqs("models/forallForall.asm", expectedValues, true);
	}

	@Test
	public void testForallForall2() throws Exception {
		String[] enumValues = {"AA", "BB", "CC"};
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo0", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", v1});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo1", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", v2});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo2", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", v1});
			}
		}
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		for(String v1: enumValues) {
			for(String v2: enumValues) {
				state.add(new String[]{location("foo3", new String[]{String.valueOf(v1), String.valueOf(v2)}), "EnumDom", v2});
			}
		}
		expectedValues.add(state);
		testAssertEqs("models/forallForall2.asm", expectedValues, true);
	}

	@Test
	public void testFunctionOverload() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo00", "Boolean", "true"});
		state.add(new String[]{"(foo10 true)", "Boolean", "true"});
		state.add(new String[]{"(foo10 false)", "Boolean", "true"});
		state.add(new String[]{"(foo20 false false)", "Boolean", "true"});
		state.add(new String[]{"(foo20 false true)", "Boolean", "true"});
		state.add(new String[]{"(foo20 true false)", "Boolean", "true"});
		state.add(new String[]{"(foo20 true true)", "Boolean", "true"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo01", "Boolean", "false"});
		state.add(new String[]{"(foo11 true)", "Boolean", "true"});
		state.add(new String[]{"(foo11 false)", "Boolean", "false"});
		state.add(new String[]{"(foo21 false false)", "Boolean", "false"});
		state.add(new String[]{"(foo21 false true)", "Boolean", "true"});
		state.add(new String[]{"(foo21 true false)", "Boolean", "true"});
		state.add(new String[]{"(foo21 true true)", "Boolean", "true"});
		expectedValues.add(state);
		testAssertEqs("models/functionOverload.asm", expectedValues, true);

		ArrayList<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();
		state = new ArrayList<String[]>();
		state.add(new String[]{"(= foo00 false)", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/functionOverload.asm", commandExpRes, true);

		String[] booleanValues = {"false", "true"}; 
		for(String value: booleanValues) {
			commandExpRes = new ArrayList<List<String[]>>();
			state = new ArrayList<String[]>();
			state.add(new String[]{"(= (foo10 " + value + ") false)", "unsat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/functionOverload.asm", commandExpRes, true);

			for(String value2: booleanValues) {
				commandExpRes = new ArrayList<List<String[]>>();
				state = new ArrayList<String[]>();
				state.add(new String[]{"(= (foo20 " + value + " " + value2 + ") false)", "unsat"});
				commandExpRes.add(state);
				testAssertCommandsExpResult("models/functionOverload.asm", commandExpRes, true);
			}
		}
	}

	@Test
	public void testInitUndef() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"(foo0 a1)", "EnumDom", "EnumDomUNDEF"});
		state.add(new String[]{"(foo0 a2)", "EnumDom", "EnumDomUNDEF"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"(foo1 a1)", "EnumDom", "AA"});
		state.add(new String[]{"(foo1 a2)", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"(foo2 a1)", "EnumDom", "BB"});
		state.add(new String[]{"(foo2 a2)", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"(foo3 a1)", "EnumDom", "BB"});
		state.add(new String[]{"(foo3 a2)", "EnumDom", "BB"});
		expectedValues.add(state);
		testAssertEqs("models/initUndef.asm", expectedValues, true);
	}

	@Test
	public void testRoulette() throws Exception {
		List<List<String>> commands = new ArrayList<List<String>>();
		ArrayList<String> state = new ArrayList<String>();
		state.add("(= playerMoney0 5)");
		state.add("(= bancoMoney0 5)");
		state.add("(= (color0 0) GREEN)");
		for(int i = 1; i <= 36; i++) {
			state.add("(= (color0 " +  i + ") " + (i%2==0?"RED":"BLACK") + ")");
		}
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= 10 (+ playerMoney1 bancoMoney1))");
		state.add("(or "
				+ "(and (= playerMoney1 (+ playerMoney0 2)) (= bancoMoney1 (- bancoMoney0 2)))"
				+ "(and (= playerMoney1 (+ playerMoney0 1)) (= bancoMoney1 (- bancoMoney0 1)))"
				+ "(and (= playerMoney1 (- playerMoney0 1)) (= bancoMoney1 (+ bancoMoney0 1)))"
				+ ")");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= 10 (+ playerMoney2 bancoMoney2))");
		state.add("(or "
				+ "(and (= playerMoney2 (+ playerMoney1 2)) (= bancoMoney2 (- bancoMoney1 2)))"
				+ "(and (= playerMoney2 (+ playerMoney1 1)) (= bancoMoney2 (- bancoMoney1 1)))"
				+ "(and (= playerMoney2 (- playerMoney1 1)) (= bancoMoney2 (+ bancoMoney1 1)))"
				+ ")");
		commands.add(state);
		state = new ArrayList<String>();
		state.add("(= 10 (+ playerMoney2 bancoMoney2))");
		state.add("(or "
				+ "(and (= playerMoney3 (+ playerMoney2 2)) (= bancoMoney3 (- bancoMoney2 2)))"
				+ "(and (= playerMoney3 (+ playerMoney2 1)) (= bancoMoney3 (- bancoMoney2 1)))"
				+ "(and (= playerMoney3 (- playerMoney2 1)) (= bancoMoney3 (+ bancoMoney2 1)))"
				+ "(and (= playerMoney3 playerMoney2) (= bancoMoney3 bancoMoney2))"
				+ ")");
		commands.add(state);
		testAssertCommands("models/roulette.asm", commands, true);
	}

	/**
	 * Init state is correct.
	 */
	@Test
	public void testRoulette_1() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"(not (= playerMoney0 5))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();
		state = new ArrayList<String[]>();
		state.add(new String[]{"(not (= bancoMoney0 5))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();
		state = new ArrayList<String[]>();
		state.add(new String[]{"(not (= (color0 0) GREEN))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);

		for(int i = 1; i <= 36; i++) {
			commandExpRes = new ArrayList<List<String[]>>();
			state = new ArrayList<String[]>();
			state.add(new String[]{"(not (= (color0 " +  i + ") " + (i%2==0?"RED":"BLACK") + "))", "unsat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The first step is correct.
	 */
	@Test
	public void testRoulette_2() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		ArrayList<String[]> state = new ArrayList<String[]>();//first step
		//after one step the sum of the budgets must be 10
		state.add(new String[]{"(not (= 10 (+ playerMoney1 bancoMoney1)))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);

		commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		state = new ArrayList<String[]>();//first step
		//in the first step, three possible situations can occur:
		//- the player wins two coins, and the croupier loses two coins
		//- the player wins one coin, and the croupier loses one coin
		//- the player loses one coin, and the croupier wins one coin
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney1 (+ playerMoney0 2)) (= bancoMoney1 (- bancoMoney0 2)))"
				+ "(and (= playerMoney1 (+ playerMoney0 1)) (= bancoMoney1 (- bancoMoney0 1)))"
				+ "(and (= playerMoney1 (- playerMoney0 1)) (= bancoMoney1 (+ bancoMoney0 1)))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
	}

	/**
	 * The second step is correct.
	 */
	@Test
	public void testRoulette_3() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state = new ArrayList<String[]>();//second step
		//in the second step, three possible situations can occur:
		//- the player wins two coins, and the croupier loses two coins
		//- the player wins one coin, and the croupier loses one coin
		//- the player loses one coin, and the croupier wins one coin
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney2 (+ playerMoney1 2)) (= bancoMoney2 (- bancoMoney1 2)))"
				+ "(and (= playerMoney2 (+ playerMoney1 1)) (= bancoMoney2 (- bancoMoney1 1)))"
				+ "(and (= playerMoney2 (- playerMoney1 1)) (= bancoMoney2 (+ bancoMoney1 1)))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
	}

	/**
	 * The third step is correct.
	 */
	@Test
	public void testRoulette_4() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		ArrayList<String[]> state = new ArrayList<String[]>();//third step
		//in the third step, four possible situations can occur:
		//- the player wins two coins, and the croupier loses two coins
		//- the player wins one coin, and the croupier loses one coin
		//- the player loses one coin, and the croupier wins one coin
		//- the player and the croupier cannot play because they do not
		//  have enough money. So, they do not change their budgets.
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney3 (+ playerMoney2 2)) (= bancoMoney3 (- bancoMoney2 2)))"
				+ "(and (= playerMoney3 (+ playerMoney2 1)) (= bancoMoney3 (- bancoMoney2 1)))"
				+ "(and (= playerMoney3 (- playerMoney2 1)) (= bancoMoney3 (+ bancoMoney2 1)))"
				+ "(and (= playerMoney3 playerMoney2) (= bancoMoney3 bancoMoney2))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
	}

	/**
	 * The first step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_5() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		ArrayList<String[]> state = new ArrayList<String[]>();//first step
		//in the first step, three possible situations can occur:
		//- the player has 7 coins, and the croupier has 3 coins
		//- the player has 6 coins, and the croupier has 4 coins
		//- the player has 4 coins, and the croupier has 6 coins
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney1 7) (= bancoMoney1 3))"
				+ "(and (= playerMoney1 6) (= bancoMoney1 4))"
				+ "(and (= playerMoney1 4) (= bancoMoney1 6))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);

		//we check that all the three combinations can be obtained
		String[][] valuesCombs = new String[][]{{"7", "3"}, {"6", "4"}, {"4", "6"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			state = new ArrayList<String[]>();//first step
			state.add(new String[]{"(and (= playerMoney1 " + values[0] + ") (= bancoMoney1 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The second step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_6() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state = new ArrayList<String[]>();//second step
		//in the second step, six possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney2 9) (= bancoMoney2 1))"
				+ "(and (= playerMoney2 8) (= bancoMoney2 2))"
				+ "(and (= playerMoney2 7) (= bancoMoney2 3))"
				+ "(and (= playerMoney2 6) (= bancoMoney2 4))"
				+ "(and (= playerMoney2 5) (= bancoMoney2 5))"
				+ "(and (= playerMoney2 3) (= bancoMoney2 7))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the six combinations can be obtained
		String[][] valuesCombs = new String[][]{{"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"3", "7"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			state = new ArrayList<String[]>();//second step
			state.add(new String[]{"(and (= playerMoney2 " + values[0] + ") (= bancoMoney2 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The third step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_7() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		ArrayList<String[]> state = new ArrayList<String[]>();//third step
		//in the third step, eight possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney3 10) (= bancoMoney3 0))"
				+ "(and (= playerMoney3 9) (= bancoMoney3 1))"
				+ "(and (= playerMoney3 8) (= bancoMoney3 2))"
				+ "(and (= playerMoney3 7) (= bancoMoney3 3))"
				+ "(and (= playerMoney3 6) (= bancoMoney3 4))"
				+ "(and (= playerMoney3 5) (= bancoMoney3 5))"
				+ "(and (= playerMoney3 4) (= bancoMoney3 6))"
				+ "(and (= playerMoney3 2) (= bancoMoney3 8))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the eight combinations can be obtained
		String[][] valuesCombs = new String[][]{{"10", "0"}, {"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"4", "6"}, {"2", "8"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			commandExpRes.add(new ArrayList<String[]>());//second step
			state = new ArrayList<String[]>();//third step
			state.add(new String[]{"(and (= playerMoney3 " + values[0] + ") (= bancoMoney3 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The fourth step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_8() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		commandExpRes.add(new ArrayList<String[]>());//third step
		ArrayList<String[]> state = new ArrayList<String[]>();//fourth step
		//in the fourth step, nine possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney4 10) (= bancoMoney4 0))"
				+ "(and (= playerMoney4 9) (= bancoMoney4 1))"
				+ "(and (= playerMoney4 8) (= bancoMoney4 2))"
				+ "(and (= playerMoney4 7) (= bancoMoney4 3))"
				+ "(and (= playerMoney4 6) (= bancoMoney4 4))"
				+ "(and (= playerMoney4 5) (= bancoMoney4 5))"
				+ "(and (= playerMoney4 4) (= bancoMoney4 6))"
				+ "(and (= playerMoney4 3) (= bancoMoney4 7))"
				+ "(and (= playerMoney4 1) (= bancoMoney4 9))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the nine combinations can be obtained
		String[][] valuesCombs = new String[][]{{"10", "0"}, {"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"4", "6"}, {"3", "7"}, {"1", "9"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			commandExpRes.add(new ArrayList<String[]>());//second step
			commandExpRes.add(new ArrayList<String[]>());//third step
			state = new ArrayList<String[]>();//fourth step
			state.add(new String[]{"(and (= playerMoney4 " + values[0] + ") (= bancoMoney4 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The fifth step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_9() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		commandExpRes.add(new ArrayList<String[]>());//third step
		commandExpRes.add(new ArrayList<String[]>());//fourth step
		ArrayList<String[]> state = new ArrayList<String[]>();//fifth step
		//in the fifth step, ten possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney5 10) (= bancoMoney5 0))"
				+ "(and (= playerMoney5 9) (= bancoMoney5 1))"
				+ "(and (= playerMoney5 8) (= bancoMoney5 2))"
				+ "(and (= playerMoney5 7) (= bancoMoney5 3))"
				+ "(and (= playerMoney5 6) (= bancoMoney5 4))"
				+ "(and (= playerMoney5 5) (= bancoMoney5 5))"
				+ "(and (= playerMoney5 4) (= bancoMoney5 6))"
				+ "(and (= playerMoney5 3) (= bancoMoney5 7))"
				+ "(and (= playerMoney5 2) (= bancoMoney5 8))"
				+ "(and (= playerMoney5 0) (= bancoMoney5 10))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the ten combinations can be obtained
		String[][] valuesCombs = new String[][]{{"10", "0"}, {"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"4", "6"}, {"3", "7"}, {"2", "8"}, {"0", "10"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			commandExpRes.add(new ArrayList<String[]>());//second step
			commandExpRes.add(new ArrayList<String[]>());//third step
			commandExpRes.add(new ArrayList<String[]>());//fourth step
			state = new ArrayList<String[]>();//fifth step
			state.add(new String[]{"(and (= playerMoney5 " + values[0] + ") (= bancoMoney5 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The sixth step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_10() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		commandExpRes.add(new ArrayList<String[]>());//third step
		commandExpRes.add(new ArrayList<String[]>());//fourth step
		commandExpRes.add(new ArrayList<String[]>());//fifth step
		ArrayList<String[]> state = new ArrayList<String[]>();//sixth step
		//in the sixth step, eleven possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney6 10) (= bancoMoney6 0))"
				+ "(and (= playerMoney6 9) (= bancoMoney6 1))"
				+ "(and (= playerMoney6 8) (= bancoMoney6 2))"
				+ "(and (= playerMoney6 7) (= bancoMoney6 3))"
				+ "(and (= playerMoney6 6) (= bancoMoney6 4))"
				+ "(and (= playerMoney6 5) (= bancoMoney6 5))"
				+ "(and (= playerMoney6 4) (= bancoMoney6 6))"
				+ "(and (= playerMoney6 3) (= bancoMoney6 7))"
				+ "(and (= playerMoney6 2) (= bancoMoney6 8))"
				+ "(and (= playerMoney6 1) (= bancoMoney6 9))"
				+ "(and (= playerMoney6 0) (= bancoMoney6 10))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the eleven combinations can be obtained
		String[][] valuesCombs = new String[][]{{"10", "0"}, {"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"4", "6"}, {"3", "7"}, {"2", "8"}, {"1", "9"}, {"0", "10"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			commandExpRes.add(new ArrayList<String[]>());//second step
			commandExpRes.add(new ArrayList<String[]>());//third step
			commandExpRes.add(new ArrayList<String[]>());//fourth step
			commandExpRes.add(new ArrayList<String[]>());//fifth step
			state = new ArrayList<String[]>();//sixth step
			state.add(new String[]{"(and (= playerMoney6 " + values[0] + ") (= bancoMoney6 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	/**
	 * The seventh step is correct. Control of the budgets.
	 */
	@Test
	public void testRoulette_11() throws Exception {
		List<List<String[]>> commandExpRes = new ArrayList<List<String[]>>();//new run
		commandExpRes.add(new ArrayList<String[]>());//init state
		commandExpRes.add(new ArrayList<String[]>());//first step
		commandExpRes.add(new ArrayList<String[]>());//second step
		commandExpRes.add(new ArrayList<String[]>());//third step
		commandExpRes.add(new ArrayList<String[]>());//fourth step
		commandExpRes.add(new ArrayList<String[]>());//fifth step
		commandExpRes.add(new ArrayList<String[]>());//sixth step
		ArrayList<String[]> state = new ArrayList<String[]>();//seventh step
		//in the seventh step, eleven possible situations can occur:
		state.add(new String[]{"(not (or "
				+ "(and (= playerMoney7 10) (= bancoMoney7 0))"
				+ "(and (= playerMoney7 9) (= bancoMoney7 1))"
				+ "(and (= playerMoney7 8) (= bancoMoney7 2))"
				+ "(and (= playerMoney7 7) (= bancoMoney7 3))"
				+ "(and (= playerMoney7 6) (= bancoMoney7 4))"
				+ "(and (= playerMoney7 5) (= bancoMoney7 5))"
				+ "(and (= playerMoney7 4) (= bancoMoney7 6))"
				+ "(and (= playerMoney7 3) (= bancoMoney7 7))"
				+ "(and (= playerMoney7 2) (= bancoMoney7 8))"
				+ "(and (= playerMoney7 1) (= bancoMoney7 9))"
				+ "(and (= playerMoney7 0) (= bancoMoney7 10))"
				+ "))", "unsat"});
		commandExpRes.add(state);
		testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		//we check that all the eleven combinations can be obtained
		String[][] valuesCombs = new String[][]{{"10", "0"}, {"9", "1"}, {"8", "2"}, {"7", "3"}, {"6", "4"}, {"5", "5"}, {"4", "6"}, {"3", "7"}, {"2", "8"}, {"1", "9"}, {"0", "10"}};
		for(String[] values: valuesCombs) {
			commandExpRes = new ArrayList<List<String[]>>();//new run
			commandExpRes.add(new ArrayList<String[]>());//init state
			commandExpRes.add(new ArrayList<String[]>());//first step
			commandExpRes.add(new ArrayList<String[]>());//second step
			commandExpRes.add(new ArrayList<String[]>());//third step
			commandExpRes.add(new ArrayList<String[]>());//fourth step
			commandExpRes.add(new ArrayList<String[]>());//fifth step
			commandExpRes.add(new ArrayList<String[]>());//sixth step
			state = new ArrayList<String[]>();//seventh step
			state.add(new String[]{"(and (= playerMoney7 " + values[0] + ") (= bancoMoney7 " + values[1] + "))", "sat"});
			commandExpRes.add(state);
			testAssertCommandsExpResult("models/roulette.asm", commandExpRes, true);
		}
	}

	@Test
	public void testStereoacuityGround() throws Exception {
		//I can receive the certification only after at least three steps
		for(int i = 0; i <= 2; i++) {
			ArrayList<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
			for(int j = 1; j <= i; j++) {
				commandsExpRes.add(new ArrayList<String[]>());
			}
			ArrayList<String[]> state = new ArrayList<String[]>();
			state.add(new String[]{"(= outMessage" + (i + 1) + " CERTIFICATE)", "unsat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("../../../Asmeta/asm_examples/stereoacuity/certifierGround.asm", commandsExpRes, false);
		}
	}

	@Test
	public void testSimpleModel() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		test("models/simpleModel.asm", expectedValues, true);
	}

	@Test
	public void testSimpleModel2() throws Exception {
		List<List<String[]>> expectedValues = new ArrayList<List<String[]>>();
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "AA"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "BB"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		state = new ArrayList<String[]>();
		state.add(new String[]{"foo", "EnumDom", "CC"});
		expectedValues.add(state);
		test("models/simpleModel2.asm", expectedValues, true);
	}

	/**
	 * Init state is correct.
	 */
	@Test
	public void testTicTacToe_forMonitoring() throws Exception {
		//We check that the derived function noSquareLeft0 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(not endOfGame0)", "sat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/ticTacToe_forMonitoring.asm", commandsExpRes, true);
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"endOfGame0", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/ticTacToe_forMonitoring.asm", commandsExpRes, true);

		//all the cells can be empty
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				state.add(new String[]{"(= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "sat"});
				
			}
		}
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/ticTacToe_forMonitoring.asm", commandsExpRes, true);

		//all the cells can only be empty
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				state = new ArrayList<String[]>();
				state.add(new String[]{"(/= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/ticTacToe_forMonitoring.asm", commandsExpRes, true);
			}
		}
	}

	/**
	 * Init state is correct.
	 */
	@Test
	public void testTictactoe() throws Exception {
		//We check that the derived function noSquareLeft0 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//init state
		state.add(new String[]{"(not noSquareLeft0)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, true);
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"noSquareLeft0", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, true);

		//all the cells can be empty
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				state.add(new String[]{"(= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "sat"});
			}
		}
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, true);

		//all the cells can only be empty
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				state = new ArrayList<String[]>();
				state.add(new String[]{"(/= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, true);
			}
		}
	}

	/**
	 * First step is correct.
	 * The user makes a move.
	 */
	@Test
	public void testTictactoe1() throws Exception {
		//We check that the derived function noSquareLeft1 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state;
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft1, we have to do the second step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"(not noSquareLeft1)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft1, we have to do the second step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"noSquareLeft1", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that all possible combinations that can be reached in one step are actually obtained
		ArrayList<String> possibleStates = new ArrayList<String>();
		for(int h = 1; h <= 3; h++) {
			for(int k = 1; k <= 3; k++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				state = new ArrayList<String[]>();//first step
				ArrayList<String> cells = new ArrayList<String>();
				for(int i = 1; i <= 3; i++) {
					for(int j = 1; j <= 3; j++) {
						String cell;
						if((i == h) && (j == k)) {
							cell = "(= " + location("board1", new String[]{String.valueOf(i), String.valueOf(j)}) + " CROSS)";
							state.add(new String[]{cell, "sat"});
						}
						else {
							cell = "(= " + location("board1", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)";
							state.add(new String[]{cell, "sat"});
						}
						cells.add(cell);
					}
				}
				String and = "(and";
				for(int i = 0; i < cells.size() - 1; i++) {
					and = and + " " + cells.get(i);
				}
				and = and + " " + cells.get(cells.size() - 1) + ")";
				possibleStates.add(and);
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
		//Only the states having one cell with CROSS can be obtained after one step. 
		String notOr = "(not (or";
		for(int i = 0; i < possibleStates.size() - 1; i++) {
			notOr = notOr + " " + possibleStates.get(i);
		}
		notOr = notOr + " " + possibleStates.get(possibleStates.size() - 1) + "))";
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();
		state.add(new String[]{notOr, "unsat"});//first step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After one step we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner1 RUN)", "unsat"});//first step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that in the next state the machine must check the board of the user
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status1 CHECK_USER)", "unsat"});//first step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
	}

	/**
	 * Second step is correct.
	 * The board of the user is checked.
	 */
	@Test
	public void testTictactoe2() throws Exception {
		//We check that the derived function noSquareLeft2 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		ArrayList<String[]> state;
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft2, we have to do the third step
		state = new ArrayList<String[]>();//third step
		state.add(new String[]{"(not noSquareLeft2)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft2, we have to do the third step
		state = new ArrayList<String[]>();//third step
		state.add(new String[]{"noSquareLeft2", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After two steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner2 RUN)", "unsat"});//second step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that, after the second step, the computer is forced to make a move
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status2 TURN_PC)", "unsat"});//second step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//The board must be the same as in the previous state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				state = new ArrayList<String[]>();//second step
				state.add(new String[]{"(/= " + location("board1", new String[]{String.valueOf(i), String.valueOf(j)}) + " " + location("board2", new String[]{String.valueOf(i), String.valueOf(j)}) + ")", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Third step is correct.
	 * The computer makes a move.
	 */
	@Test
	public void testTictactoe3() throws Exception {
		//We check that the derived function noSquareLeft2 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		ArrayList<String[]> state;
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft3, we have to do the fourth step
		state = new ArrayList<String[]>();//fourth step
		state.add(new String[]{"(not noSquareLeft3)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft3, we have to do the fourth step
		state = new ArrayList<String[]>();//fourth step
		state.add(new String[]{"noSquareLeft3", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that all possible combinations that can be reached in three steps are actually obtained
		ArrayList<String> possibleStates = new ArrayList<String>();
		for(int h = 1; h <= 3; h++) {
			for(int k = 1; k <= 3; k++) {
				for(int n = 1; n <= 3; n++) {
					for(int m = 1; m <= 3; m++) {
						if((h != n) || (k != m)) {
							commandsExpRes = new ArrayList<List<String[]>>();//new run
							commandsExpRes.add(new ArrayList<String[]>());//first step
							commandsExpRes.add(new ArrayList<String[]>());//second step
							state = new ArrayList<String[]>();//third step
							ArrayList<String> cells = new ArrayList<String>();
							for(int i = 1; i <= 3; i++) {
								for(int j = 1; j <= 3; j++) {
									String cell;
									if((i == h) && (j == k)) {
										cell = "(= " + location("board3", new String[]{String.valueOf(i), String.valueOf(j)}) + " NOUGHT)";
										state.add(new String[]{cell, "sat"});
									}
									else if((i == n) && (j == m)) {
										cell = "(= " + location("board3", new String[]{String.valueOf(i), String.valueOf(j)}) + " CROSS)";
										state.add(new String[]{cell, "sat"});
									}
									else {
										cell = "(= " + location("board3", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)";
										state.add(new String[]{cell, "sat"});
									}
									cells.add(cell);
								}
							}
							String and = "(and";
							for(int i = 0; i < cells.size() - 1; i++) {
								and = and + " " + cells.get(i);
							}
							and = and + " " + cells.get(cells.size() - 1) + ")";
							possibleStates.add(and);
							commandsExpRes.add(state);
							testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
						}
					}
				}
			}
		}
		//Only the states having one cell with CROSS and one cell with NOUGHT
		//can be obtained after three steps. 
		String notOr = "(not (or";
		for(int i = 0; i < possibleStates.size() - 1; i++) {
			notOr = notOr + " " + possibleStates.get(i);
		}
		notOr = notOr + " " + possibleStates.get(possibleStates.size() - 1) + "))";
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		state = new ArrayList<String[]>();
		state.add(new String[]{notOr, "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After three steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner3 RUN)", "unsat"});//third step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that in the next state the machine must check the board of the computer
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status3 CHECK_PC)", "unsat"});//third step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				commandsExpRes.add(new ArrayList<String[]>());//second step
				state = new ArrayList<String[]>();//third step
				String previousStateLocation = location("board2", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board3", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Fourth step is correct.
	 * The board of the computer is checked.
	 */
	@Test
	public void testTictactoe4() throws Exception {
		//We check that the derived function noSquareLeft4 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 4; i++) {//four steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft4, we have to do the third step
		ArrayList<String[]> state = new ArrayList<String[]>();//fifth step
		state.add(new String[]{"(not noSquareLeft4)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 4; i++) {//four steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft4, we have to do the third step
		state = new ArrayList<String[]>();//fifth step
		state.add(new String[]{"noSquareLeft4", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After four steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 3; i++) {//three steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner4 RUN)", "unsat"});//fourth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that, after the fourth step, the user is forced to make a move
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 3; i++) {//three steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status4 TURN_USER)", "unsat"});//fourth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//The board must be the same as in the previous state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 3; k++) {//three steps
					commandsExpRes.add(new ArrayList<String[]>());//step i
				}
				state = new ArrayList<String[]>();//fourth step
				state.add(new String[]{"(/= " + location("board3", new String[]{String.valueOf(i), String.valueOf(j)}) + " " + location("board4", new String[]{String.valueOf(i), String.valueOf(j)}) + ")", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Fifth step is correct.
	 * The user makes a move.
	 */
	@Test
	public void testTictactoe5() throws Exception {
		//We check that the derived function noSquareLeft2 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 5; i++) {//five steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft5, we have to do the sixth step
		ArrayList<String[]> state = new ArrayList<String[]>();//sixth step
		state.add(new String[]{"(not noSquareLeft5)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 5; i++) {//five steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft5, we have to do the sixth step
		state = new ArrayList<String[]>();//sixth step
		state.add(new String[]{"noSquareLeft5", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that all possible combinations that can be reached in three steps are actually obtained
		ArrayList<String> possibleStates = new ArrayList<String>();
		for(int a = 1; a <= 3; a++) {
			for(int b = 1; b <= 3; b++) {
				for(int h = 1; h <= 3; h++) {
					for(int k = 1; k <= 3; k++) {
						if((h != a) || (k != b)) {
							for(int n = 1; n <= 3; n++) {
								for(int m = 1; m <= 3; m++) {
									if(((h != n) || (k != m)) && ((a != n) || (b != m))) {
										//System.err.println(a +" " + b+" "+ h+" "+k+" "+n+" "+m);
										commandsExpRes = new ArrayList<List<String[]>>();//new run
										for(int i = 1; i <= 4; i++) {//four steps
											commandsExpRes.add(new ArrayList<String[]>());//step i
										}
										state = new ArrayList<String[]>();//fifth step
										ArrayList<String> cells = new ArrayList<String>();
										for(int i = 1; i <= 3; i++) {
											for(int j = 1; j <= 3; j++) {
												String cell;
												if((i == h) && (j == k)) {
													cell = "(= " + location("board5", new String[]{String.valueOf(i), String.valueOf(j)}) + " NOUGHT)";
													state.add(new String[]{cell, "sat"});
												}
												else if(((i == n) && (j == m)) || (i == a) && (j == b)) {
													cell = "(= " + location("board5", new String[]{String.valueOf(i), String.valueOf(j)}) + " CROSS)";
													state.add(new String[]{cell, "sat"});
												}
												else {
													cell = "(= " + location("board5", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)";
													state.add(new String[]{cell, "sat"});
												}
												cells.add(cell);
											}
										}
										String and = "(and";
										for(int i = 0; i < cells.size() - 1; i++) {
											and = and + " " + cells.get(i);
										}
										and = and + " " + cells.get(cells.size() - 1) + ")";
										possibleStates.add(and);
										commandsExpRes.add(state);
										//It takes a lot of time
										testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
									}
								}
							}
						}
					}
				}
			}
		}
		//Only the states having one cell with CROSS and one cell with NOUGHT
		//can be obtained after three steps. 
		String notOr = "(not (or";
		for(int i = 0; i < possibleStates.size() - 1; i++) {
			notOr = notOr + " " + possibleStates.get(i);
		}
		notOr = notOr + " " + possibleStates.get(possibleStates.size() - 1) + "))";
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 4; i++) {//four steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{notOr, "unsat"});//fifth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After five steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 4; i++) {//four steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner5 RUN)", "unsat"});//fifth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that in the next state the machine must check the board of the user
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 4; i++) {//four steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status5 CHECK_USER)", "unsat"});//fifth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 4; k++) {//four steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				String previousStateLocation = location("board4", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board5", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);//fifth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Sixth step is correct.
	 * The board of the user is checked.
	 */
	@Test
	public void testTictactoe6() throws Exception {
		//We check that the derived function noSquareLeft6 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 6; i++) {//six steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft6, we have to do the seventh step
		ArrayList<String[]> state = new ArrayList<String[]>();//seventh step
		state.add(new String[]{"(not noSquareLeft6)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 6; i++) {//six steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft6, we have to do the seventh step
		state = new ArrayList<String[]>();//seventh step
		state.add(new String[]{"noSquareLeft6", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After six steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 5; i++) {//five steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner6 RUN)", "unsat"});//sixth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that, after the sixth step, the computer is forced to make a move
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 5; i++) {//five steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status6 TURN_PC)", "unsat"});//sixth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//The board must be the same as in the previous state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 5; k++) {//five steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				state.add(new String[]{"(/= " + location("board5", new String[]{String.valueOf(i), String.valueOf(j)}) + " " + location("board6", new String[]{String.valueOf(i), String.valueOf(j)}) + ")", "unsat"});
				commandsExpRes.add(state);//sixth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Seventh step is correct.
	 * The computer makes a move.
	 */
	@Test
	public void testTictactoe7() throws Exception {
		//We check that the derived function noSquareLeft7 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 7; i++) {//seven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft7, we have to do the eighth step
		ArrayList<String[]> state = new ArrayList<String[]>();//eighth step
		state.add(new String[]{"(not noSquareLeft7)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 7; i++) {//seven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft7, we have to do the eighth step
		state = new ArrayList<String[]>();//eighth step
		state.add(new String[]{"noSquareLeft7", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After seven steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 6; i++) {//six steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner7 RUN)", "unsat"});//seventh step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that in the next state the machine must check the board of the computer
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 6; i++) {//six steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status7 CHECK_PC)", "unsat"});//seventh step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 6; k++) {//six steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				String previousStateLocation = location("board6", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board7", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);//seventh step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Eighth step is correct.
	 * The board of the computer is checked.
	 */
	@Test
	public void testTictactoe8() throws Exception {
		//We check that the derived function noSquareLeft8 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 8; i++) {//eight steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft8, we have to do the ninth step
		ArrayList<String[]> state = new ArrayList<String[]>();//ninth step
		state.add(new String[]{"(not noSquareLeft8)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 8; i++) {//eight steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft8, we have to do the ninth step
		state = new ArrayList<String[]>();//ninth step
		state.add(new String[]{"noSquareLeft8", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After eight steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 7; i++) {//seven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner8 RUN)", "unsat"});//eighth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that, after the seventh step, the user is forced to make a move
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 7; i++) {//seven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status8 TURN_USER)", "unsat"});//eighth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//The board must be the same as in the previous state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 7; k++) {//seven steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				state.add(new String[]{"(/= " + location("board7", new String[]{String.valueOf(i), String.valueOf(j)}) + " " + location("board8", new String[]{String.valueOf(i), String.valueOf(j)}) + ")", "unsat"});
				commandsExpRes.add(state);//eighth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Ninth step is correct.
	 * The user makes a move.
	 */
	@Test
	public void testTictactoe9() throws Exception {
		//We check that the derived function noSquareLeft9 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 9; i++) {//nine steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft9, we have to do the tenth step
		ArrayList<String[]> state = new ArrayList<String[]>();//tenth step
		state.add(new String[]{"(not noSquareLeft9)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 9; i++) {//nine steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft9, we have to do the tenth step
		state = new ArrayList<String[]>();//tenth step
		state.add(new String[]{"noSquareLeft9", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//After nine steps we cannot have a winner
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 8; i++) {//eight steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= winner9 RUN)", "unsat"});//ninth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//We check that in the next state the machine must check the board of the user
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 8; i++) {//eight steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(/= status9 CHECK_USER)", "unsat"});//ninth step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 8; k++) {//eight steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				String previousStateLocation = location("board8", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board9", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);//ninth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Tenth step is correct.
	 * The board of the user is checked.
	 */
	@Test
	public void testTictactoe10() throws Exception {
		//We check that the derived function noSquareLeft10 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 10; i++) {//ten steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft10, we have to do the eleventh step
		ArrayList<String[]> state = new ArrayList<String[]>();//eleventh step
		state.add(new String[]{"(not noSquareLeft10)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 10; i++) {//ten steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft10, we have to do the eleventh step
		state = new ArrayList<String[]>();//eleventh step
		state.add(new String[]{"noSquareLeft10", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		
		//After ten steps:
		//- the user can win
		//- the computer cannot win
		//- we cannot have a tie
		//- it's possible that we do not have a winner, but it's possible
		//  to continue playing
		String[][] winnerExpResults = new String[][]{{"PLAYER", "sat"}, {"PC", "unsat"}, {"TIE", "unsat"}, {"RUN", "sat"}};
		for(String[] winnerExpResult: winnerExpResults) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			for(int i = 1; i <= 9; i++) {//nine steps
				commandsExpRes.add(new ArrayList<String[]>());//step i
			}
			state = new ArrayList<String[]>();
			state.add(new String[]{"(= winner10 "+ winnerExpResult[0] +")", winnerExpResult[1]});//tenth step
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		}

		//These are the possible next states
		String[][] nextExpStates = new String[][]{{"TURN_PC", "sat"}, {"GAMEOVER", "sat"}, {"CHECK_USER", "unsat"}, {"CHECK_PC", "unsat"}};
		for(String[] nextExpState: nextExpStates) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			for(int i = 1; i <= 9; i++) {//nine steps
				commandsExpRes.add(new ArrayList<String[]>());//step i
			}
			state = new ArrayList<String[]>();
			state.add(new String[]{"(= status10 "+ nextExpState[0] + ")", nextExpState[1]});//tenth step
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		}

		//The board must be the same as in the previous state
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 9; k++) {//nine steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				state.add(new String[]{"(/= " + location("board9", new String[]{String.valueOf(i), String.valueOf(j)}) + " " + location("board10", new String[]{String.valueOf(i), String.valueOf(j)}) + ")", "unsat"});
				commandsExpRes.add(state);//tenth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Eleventh step is correct.
	 * The computer may make a move (if the user has not win).
	 */
	@Test
	public void testTictactoe11() throws Exception {
		//We check that the derived function noSquareLeft11 behaves as expected
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 11; i++) {//eleven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft11, we have to do the 12th step
		ArrayList<String[]> state = new ArrayList<String[]>();//12th step
		state.add(new String[]{"(not noSquareLeft11)", "sat"});//the board can be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 11; i++) {//eleven steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft10, we have to do the 12th step
		state = new ArrayList<String[]>();//12th step
		state.add(new String[]{"noSquareLeft11", "unsat"});//the board can only be not full
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		
		//After eleven steps:
		//- the user can win
		//- the computer cannot win
		//- we cannot have a tie
		//- it's possible that we do not have a winner, but it's possible
		//  to continue playing
		String[][] winnerExpResults = new String[][]{{"PLAYER", "sat"}, {"PC", "unsat"}, {"TIE", "unsat"}, {"RUN", "sat"}};
		for(String[] winnerExpResult: winnerExpResults) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			for(int i = 1; i <= 10; i++) {//ten steps
				commandsExpRes.add(new ArrayList<String[]>());//step i
			}
			state = new ArrayList<String[]>();
			state.add(new String[]{"(= winner11 "+ winnerExpResult[0] +")", winnerExpResult[1]});//eleventh step
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		}

		//These are the possible next states
		String[][] nextExpStates = new String[][]{{"TURN_PC", "unsat"}, {"GAMEOVER", "sat"}, {"CHECK_USER", "unsat"}, {"CHECK_PC", "sat"}};
		for(String[] nextExpState: nextExpStates) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			for(int i = 1; i <= 10; i++) {//ten steps
				commandsExpRes.add(new ArrayList<String[]>());//step i
			}
			state = new ArrayList<String[]>();
			state.add(new String[]{"(= status11 "+ nextExpState[0] + ")", nextExpState[1]});//eleventh step
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
		}

		//If the user has won in the previous state, the winner must not change
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 10; i++) {//ten steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(and (= winner10 PLAYER) (/= winner11 PLAYER))", "unsat"});//eleventh step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		//If the user has won in the previous state, the status (GAMEOVER) must not change
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		for(int i = 1; i <= 10; i++) {//ten steps
			commandsExpRes.add(new ArrayList<String[]>());//step i
		}
		state = new ArrayList<String[]>();
		state.add(new String[]{"(and (= status10 GAMEOVER) (/= status11 GAMEOVER))", "unsat"});//eleventh step
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				String previousStateLocation = location("board10", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board11", new String[]{String.valueOf(i), String.valueOf(j)});

				//If the user has won in the previous state, the board must be unchanged
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 10; k++) {//ten steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				state.add(new String[]{"(and (= winner11 PLAYER) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);//eleventh step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);

				//If the user has not won, the cells different from empty must be unchanged
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				for(int k = 1; k <= 10; k++) {//ten steps
					commandsExpRes.add(new ArrayList<String[]>());//step k
				}
				state = new ArrayList<String[]>();
				state.add(new String[]{"(and (= winner11 RUN) (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);//ninth step
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Init state is correct.
	 */
	@Test
	public void testTictactoeSimple() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();
		state.add(new String[]{"(not noSquareLeft0)", "sat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, true);

		commandsExpRes = new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		state.add(new String[]{"noSquareLeft0", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, true);

		new ArrayList<List<String[]>>();//new run
		state = new ArrayList<String[]>();//init state
		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				state.add(new String[]{"(= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "sat"});
			}
		}
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, true);

		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				state = new ArrayList<String[]>();//init state
				state.add(new String[]{"(/= " + location("board0", new String[]{String.valueOf(i), String.valueOf(j)}) + " EMPTY)", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/Tictactoe.asm", commandsExpRes, true);
			}
		}
	}

	/**
	 * First step is correct.
	 */
	@Test
	public void testTictactoeSimple1() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		ArrayList<String[]> state = new ArrayList<String[]>();//first step
		//this four combinations are allowed
		state.add(new String[]{"(not (or " 
								+ "(and (= " + location("board1", new String[]{"1", "1"}) + " CROSS) (= " + location("board1", new String[]{"1", "2"}) + " EMPTY) (= " + location("board1", new String[]{"2", "1"}) + " EMPTY) (= " + location("board1", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board1", new String[]{"1", "1"}) + " EMPTY) (= " + location("board1", new String[]{"1", "2"}) + " CROSS) (= " + location("board1", new String[]{"2", "1"}) + " EMPTY) (= " + location("board1", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board1", new String[]{"1", "1"}) + " EMPTY) (= " + location("board1", new String[]{"1", "2"}) + " EMPTY) (= " + location("board1", new String[]{"2", "1"}) + " CROSS) (= " + location("board1", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board1", new String[]{"1", "1"}) + " EMPTY) (= " + location("board1", new String[]{"1", "2"}) + " EMPTY) (= " + location("board1", new String[]{"2", "1"}) + " EMPTY) (= " + location("board1", new String[]{"2", "2"}) + " CROSS))"
								+ "))", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//we check that all the four combinations can be obtained
		String[][] valuesCombs = new String[][]{{"CROSS", "EMPTY", "EMPTY", "EMPTY"}, {"EMPTY", "CROSS", "EMPTY", "EMPTY"}, {"EMPTY", "EMPTY", "CROSS", "EMPTY"}, {"EMPTY", "EMPTY", "EMPTY", "CROSS"}};
		for(String[] values: valuesCombs) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			state = new ArrayList<String[]>();//first step
			state.add(new String[]{"(and (= " + location("board1", new String[]{"1", "1"}) + " " + values[0] + ") (= " + location("board1", new String[]{"1", "2"}) + " " + values[1] + ") (= " + location("board1", new String[]{"2", "1"}) + " " + values[2] + ") (= " + location("board1", new String[]{"2", "2"}) + " " + values[3] + "))", "sat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
		}

		//we check that the derived function noSquareLeft1 behaves as expected
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft1, we have to do the second step
		state = new ArrayList<String[]>();//second step
		state.add(new String[]{"noSquareLeft1", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
	}

	/**
	 * Second step is correct.
	 */
	@Test
	public void testTictactoeSimple2() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		ArrayList<String[]> state = new ArrayList<String[]>();//second step
		//this twelve combinations are allowed
		state.add(new String[]{"(not (or "
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " CROSS) (= " + location("board2", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " CROSS) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " CROSS) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"1", "2"}) + " CROSS) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " CROSS) (= " + location("board2", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " CROSS) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " CROSS) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "1"}) + " CROSS) (= " + location("board2", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " CROSS) (= " + location("board2", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "1"}) + " EMPTY) (= " + location("board2", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board2", new String[]{"1", "1"}) + " EMPTY) (= " + location("board2", new String[]{"1", "2"}) + " EMPTY) (= " + location("board2", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board2", new String[]{"2", "2"}) + " CROSS))"
								+ "))", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//we check that all the twelve combinations can be obtained
		String[][] valuesCombs = new String[][]{{"CROSS", "NOUGHT", "EMPTY", "EMPTY"}, {"CROSS", "EMPTY", "NOUGHT", "EMPTY"}, {"CROSS", "EMPTY", "EMPTY", "NOUGHT"},
												{"NOUGHT", "CROSS", "EMPTY", "EMPTY"}, {"EMPTY", "CROSS", "NOUGHT", "EMPTY"}, {"EMPTY", "CROSS", "EMPTY", "NOUGHT"},
												{"NOUGHT", "EMPTY", "CROSS", "EMPTY"}, {"EMPTY", "NOUGHT", "CROSS", "EMPTY"}, {"EMPTY", "EMPTY", "CROSS", "NOUGHT"}, 
												{"NOUGHT", "EMPTY", "EMPTY", "CROSS"}, {"EMPTY", "NOUGHT", "EMPTY", "CROSS"}, {"EMPTY", "EMPTY", "NOUGHT", "CROSS"}};
		for(String[] values: valuesCombs) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			commandsExpRes.add(new ArrayList<String[]>());//first step
			state = new ArrayList<String[]>();//second step
			state.add(new String[]{"(and (= " + location("board2", new String[]{"1", "1"}) + " " + values[0] + ") (= " + location("board2", new String[]{"1", "2"}) + " " + values[1] + ") (= " + location("board2", new String[]{"2", "1"}) + " " + values[2] + ") (= " + location("board2", new String[]{"2", "2"}) + " " + values[3] + "))", "sat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
		}

		//we check that the derived function noSquareLeft2 behaves as expected
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft2, we have to do the third step
		state = new ArrayList<String[]>();//third step
		state.add(new String[]{"noSquareLeft2", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				state = new ArrayList<String[]>();//second step
				String previousStateLocation = location("board1", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board2", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Third step is correct.
	 */
	@Test
	public void testTictactoeSimple3() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		ArrayList<String[]> state = new ArrayList<String[]>();//third step
		//this 24 combinations are allowed
		state.add(new String[]{"(not (or "
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " EMPTY))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " EMPTY) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "1"}) + " CROSS) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " CROSS) (= " + location("board3", new String[]{"1", "2"}) + " EMPTY) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board3", new String[]{"1", "1"}) + " EMPTY) (= " + location("board3", new String[]{"1", "2"}) + " CROSS) (= " + location("board3", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board3", new String[]{"2", "2"}) + " CROSS))"
								+ "))", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//we check that all the 24 combinations can be obtained
		String[][] valuesCombs = new String[][]{{"CROSS", "NOUGHT", "CROSS", "EMPTY"}, {"CROSS", "NOUGHT", "EMPTY", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "EMPTY"}, {"CROSS", "EMPTY", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "EMPTY", "NOUGHT"}, {"CROSS", "EMPTY", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "EMPTY"}, {"NOUGHT", "CROSS", "EMPTY", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "EMPTY"}, {"EMPTY", "CROSS", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "EMPTY", "NOUGHT"}, {"EMPTY", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "EMPTY"}, {"NOUGHT", "EMPTY", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "CROSS", "EMPTY"}, {"EMPTY", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "EMPTY", "CROSS", "NOUGHT"}, {"EMPTY", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "EMPTY", "CROSS"}, {"NOUGHT", "EMPTY", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "EMPTY", "CROSS"}, {"EMPTY", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "EMPTY", "NOUGHT", "CROSS"}, {"EMPTY", "CROSS", "NOUGHT", "CROSS"}};
		for(String[] values: valuesCombs) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			commandsExpRes.add(new ArrayList<String[]>());//first step
			commandsExpRes.add(new ArrayList<String[]>());//second step
			state = new ArrayList<String[]>();//third step
			state.add(new String[]{"(and (= " + location("board3", new String[]{"1", "1"}) + " " + values[0] + ") (= " + location("board3", new String[]{"1", "2"}) + " " + values[1] + ") (= " + location("board3", new String[]{"2", "1"}) + " " + values[2] + ") (= " + location("board3", new String[]{"2", "2"}) + " " + values[3] + "))", "sat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
		}

		//we check that the derived function noSquareLeft3 behaves as expected
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft3, we have to do the fourth step
		state = new ArrayList<String[]>();//fourth step
		state.add(new String[]{"noSquareLeft3", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				commandsExpRes.add(new ArrayList<String[]>());//second step
				state = new ArrayList<String[]>();//third step
				String previousStateLocation = location("board2", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board3", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Fourth step is correct.
	 */
	@Test
	public void testTictactoeSimple4() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		ArrayList<String[]> state = new ArrayList<String[]>();//fourth step
		//this 24 combinations are allowed
		state.add(new String[]{"(not (or "
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " CROSS) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " CROSS) (= " + location("board4", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board4", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"1", "2"}) + " CROSS) (= " + location("board4", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board4", new String[]{"2", "2"}) + " CROSS))"
								+ "))", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//we check that all the 24 combinations can be obtained
		String[][] valuesCombs = new String[][]{{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"CROSS", "NOUGHT", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"CROSS", "NOUGHT", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"CROSS", "NOUGHT", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "NOUGHT"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"NOUGHT", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "NOUGHT"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"NOUGHT", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "NOUGHT", "CROSS"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "NOUGHT", "CROSS"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "NOUGHT", "CROSS"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"}};
		for(String[] values: valuesCombs) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			commandsExpRes.add(new ArrayList<String[]>());//first step
			commandsExpRes.add(new ArrayList<String[]>());//second step
			commandsExpRes.add(new ArrayList<String[]>());//third step
			state = new ArrayList<String[]>();//fourth step
			state.add(new String[]{"(and (= " + location("board4", new String[]{"1", "1"}) + " " + values[0] + ") (= " + location("board4", new String[]{"1", "2"}) + " " + values[1] + ") (= " + location("board4", new String[]{"2", "1"}) + " " + values[2] + ") (= " + location("board4", new String[]{"2", "2"}) + " " + values[3] + "))", "sat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
		}

		//we check that the derived function noSquareLeft4 behaves as expected
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		commandsExpRes.add(new ArrayList<String[]>());//fourth step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft4, we have to do the fifth step
		state = new ArrayList<String[]>();//fifth step
		state.add(new String[]{"noSquareLeft4", "sat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//if in the previous state a location is different from empty,
		//in the current state it must have the same value
		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				commandsExpRes.add(new ArrayList<String[]>());//second step
				commandsExpRes.add(new ArrayList<String[]>());//third step
				state = new ArrayList<String[]>();//fourth step
				String previousStateLocation = location("board3", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board4", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(and (/= " + previousStateLocation + " EMPTY) (/= " + previousStateLocation + " " + currentStateLocation + "))", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
			}
		}
	}

	/**
	 * Fifth step is correct. Nothing changes.
	 */
	@Test
	public void testTictactoeSimple5() throws Exception {
		List<List<String[]>> commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		commandsExpRes.add(new ArrayList<String[]>());//fourth step
		ArrayList<String[]> state = new ArrayList<String[]>();//fifth step
		//this 24 combinations are allowed
		state.add(new String[]{"(not (or "
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " NOUGHT))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " CROSS) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " CROSS) (= " + location("board5", new String[]{"1", "2"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "(and (= " + location("board5", new String[]{"1", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"1", "2"}) + " CROSS) (= " + location("board5", new String[]{"2", "1"}) + " NOUGHT) (= " + location("board5", new String[]{"2", "2"}) + " CROSS))"
								+ "))", "unsat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//we check that all the 24 combinations can be obtained
		String[][] valuesCombs = new String[][]{{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"CROSS", "NOUGHT", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"CROSS", "NOUGHT", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"CROSS", "NOUGHT", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "NOUGHT"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"},
												{"CROSS", "CROSS", "NOUGHT", "NOUGHT"}, {"NOUGHT", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "CROSS", "NOUGHT"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "CROSS", "NOUGHT"}, {"NOUGHT", "CROSS", "CROSS", "NOUGHT"},
												{"NOUGHT", "CROSS", "NOUGHT", "CROSS"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "NOUGHT", "CROSS"}, {"NOUGHT", "NOUGHT", "CROSS", "CROSS"},
												{"CROSS", "NOUGHT", "NOUGHT", "CROSS"}, {"NOUGHT", "CROSS", "NOUGHT", "CROSS"}};
		for(String[] values: valuesCombs) {
			commandsExpRes = new ArrayList<List<String[]>>();//new run
			commandsExpRes.add(new ArrayList<String[]>());//first step
			commandsExpRes.add(new ArrayList<String[]>());//second step
			commandsExpRes.add(new ArrayList<String[]>());//third step
			commandsExpRes.add(new ArrayList<String[]>());//fourth step
			state = new ArrayList<String[]>();//fifth step
			state.add(new String[]{"(and (= " + location("board5", new String[]{"1", "1"}) + " " + values[0] + ") (= " + location("board5", new String[]{"1", "2"}) + " " + values[1] + ") (= " + location("board5", new String[]{"2", "1"}) + " " + values[2] + ") (= " + location("board5", new String[]{"2", "2"}) + " " + values[3] + "))", "sat"});
			commandsExpRes.add(state);
			testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
		}

		//we check that the derived function noSquareLeft5 behaves as expected
		commandsExpRes = new ArrayList<List<String[]>>();//new run
		commandsExpRes.add(new ArrayList<String[]>());//first step
		commandsExpRes.add(new ArrayList<String[]>());//second step
		commandsExpRes.add(new ArrayList<String[]>());//third step
		commandsExpRes.add(new ArrayList<String[]>());//fourth step
		commandsExpRes.add(new ArrayList<String[]>());//fifth step
		//Derived functions are printed before the step.
		//So, in order to print noSquareLeft5, we have to do the sixth step
		state = new ArrayList<String[]>();//sixth step
		state.add(new String[]{"noSquareLeft5", "sat"});
		commandsExpRes.add(state);
		testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);

		//all the locations have the same value of the previous state
		for(int i = 1; i <= 2; i++) {
			for(int j = 1; j <= 2; j++) {
				commandsExpRes = new ArrayList<List<String[]>>();//new run
				commandsExpRes.add(new ArrayList<String[]>());//first step
				commandsExpRes.add(new ArrayList<String[]>());//second step
				commandsExpRes.add(new ArrayList<String[]>());//third step
				commandsExpRes.add(new ArrayList<String[]>());//fourth step
				state = new ArrayList<String[]>();//fifth step
				String previousStateLocation = location("board4", new String[]{String.valueOf(i), String.valueOf(j)});
				String currentStateLocation = location("board5", new String[]{String.valueOf(i), String.valueOf(j)});
				state.add(new String[]{"(/= " + previousStateLocation + " " + currentStateLocation + ")", "unsat"});
				commandsExpRes.add(state);
				testAssertCommandsExpResult("models/TictactoeSimple.asm", commandsExpRes, false);
			}
		}
	}
}