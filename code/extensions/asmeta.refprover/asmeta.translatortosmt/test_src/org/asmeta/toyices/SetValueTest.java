package org.asmeta.toyices;

import static org.asmeta.toyices.Utils.location;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SetValueTest {

	@Test
	public void testBoard() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/board.asm");
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();
		visitor.parseAndAssertCommand("(and (board1 1) (not (board1 2)))");
		visitor.assertRules();
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}

	/**
	 * Monitoring a correct implementation.
	 */
	@Test
	public void testTictactoe() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/Tictactoe.asm");
		visitor.setLogger();
		visitor.visitASM();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"1", "1"}) + " CROSS)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board3", new String[]{"1", "2"}) + " NOUGHT)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board5", new String[]{"2", "1"}) + " CROSS)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board7", new String[]{"1", "3"}) + " NOUGHT)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board9", new String[]{"3", "1"}) + " CROSS)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= winner10 PLAYER)");
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}

	/**
	 * Monitoring a wrong implementation.
	 */
	@Test
	public void testTictactoe1() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/Tictactoe.asm");
		visitor.setLogger();
		visitor.visitASM();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"1", "1"}) + " CROSS)");
		assertEquals("sat", visitor.check());

		visitor.assertRules();

		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board3", new String[]{"1", "1"}) + " NOUGHT)");//wrong move
		assertEquals("unsat", visitor.check());
		visitor.shutdownLogger();
	}

	/**
	 * Setting the monitored functions.
	 */
	@Test
	public void testRoulette() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/roulette.asm");
		visitor.setLogger();
		visitor.visitASM();

		//TODO
		visitor.shutdownLogger();
	}

	@Test
	public void testRushHourSimple() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/RushHourSimple.asm");
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"1", "0"}) + " 1)");
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}

	@Test
	public void testRushHourSimple2() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/RushHourSimple2.asm");
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"1", "0"}) + " 1)");
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}

	@Test
	public void testRushHour2() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/RushHour2.asm");
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"2", "0"}) + " 1)");
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}

	@Test
	public void testRushHour3() throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator("models/RushHour3.asm");
		visitor.setLogger();
		visitor.visitASM();
		visitor.assertRules();
		visitor.parseAndAssertCommand("(= " + location("board1", new String[]{"0", "1"}) + " 1)");
		assertEquals("sat", visitor.check());
		visitor.shutdownLogger();
	}
}