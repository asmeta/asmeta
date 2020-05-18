package org.asmeta.toyices;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jna.Pointer;

public class TestMethods {
	private static Logger logger = LogManager.getLogger(Test.class.getName());
	
	@BeforeClass
	public static void setLogger() {
		Logger.getLogger(SMTbasedASMsimulator.class).setLevel(Level.INFO);
		Logger.getLogger(Test.class).setLevel(Level.OFF);
	}

	protected void test(String asmFile, List<List<String[]>> expectedValues, boolean initState) throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		visitor.visitASM();
		int i = 0;
		if(initState) {
			for(String[] value: expectedValues.get(0)) {
				String actualValue = visitor.getValue(value[0] + 0, value[1]);
				assertEquals("state " + i + " - " + value[0] + 0 + " should be " + value[2] + ", not " + actualValue, value[2], actualValue);
			}
			i++;
		}
		for(; i < expectedValues.size(); i++) {
			visitor.assertRules();
			for(String[] value: expectedValues.get(i)) {
				String actualValue = visitor.getValue(value[0] + i, value[1]);
				assertEquals("state " + i + " - " + value[0] + i + " should be " + value[2] + ", not " + actualValue, value[2], actualValue);
			}
		}
	}

	protected void testAssertCommands(String asmFile, List<List<String>> commands, boolean initState) throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		visitor.visitASM();
		int i = 0;
		if(initState) {
			logger.debug(";; testing asserts - state " + 0);
			for(String command: commands.get(0)) {
				visitor.parseAndAssertCommand(command);
				assertEquals(command + " violated", "sat", visitor.check());
			}
			i++;
		}
		for(; i < commands.size(); i++) {
			visitor.assertRules();
			logger.debug(";; testing asserts - state " + i);
			for(String command: commands.get(i)) {
				visitor.parseAndAssertCommand(command);
				assertEquals(command + " violated", "sat", visitor.check());
			}
		}
	}

	protected void testAssertCommandsExpResult(String asmFile, List<List<String[]>> commands, boolean initState) throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		visitor.visitASM();
		int i = 0;
		if(initState) {
			logger.debug(";; testing asserts - state " + 0);
			for(String[] commandExpRes: commands.get(0)) {
				visitor.parseAndAssertCommand(commandExpRes[0]);
				String res = visitor.check();
				assertEquals(commandExpRes[0] + " violated", commandExpRes[1], res);
				if(commandExpRes[1].equals("unsat")) {
					return;
				}
			}
			i++;
		}
		for(; i < commands.size(); i++) {
			visitor.assertRules();
			logger.debug(";; testing asserts - state " + i);
			for(String[] commandExpRes: commands.get(i)) {
				visitor.parseAndAssertCommand(commandExpRes[0]);
				String res = visitor.check();
				assertEquals(commandExpRes[0] + " violated", commandExpRes[1], res);
				if(commandExpRes[1].equals("unsat")) {
					return;
				}
			}
		}
		visitor.shutdownLogger();
	}

	protected void testAssertEqs(String asmFile, List<List<String[]>> expectedValues, boolean initState) throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		visitor.visitASM();
		int i = 0;
		if(initState) {
			for(String[] value: expectedValues.get(0)) {
				visitor.assertEq(value[0], value[2]);
				assertEquals(value[0] + " should be " + value[2], "sat", visitor.check());
			}
			i++;
		}
		for(; i < expectedValues.size(); i++) {
			visitor.assertRules();
			for(String[] value: expectedValues.get(i)) {
				visitor.assertEq(value[0], value[2]);
				assertEquals(value[0] + " should be " + value[2], "sat", visitor.check());
			}
		}
	}

	protected void testAssertOrEqs(String asmFile, List<List<String[]>> expectedValues, boolean initState) throws Exception {
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		visitor.visitASM();
		int i = 0;
		if(initState) {
			for(String[] value: expectedValues.get(0)) {
				ArrayList<String> orOperands = new ArrayList<String>();
				for(int j = 2; j < value.length; j++) {
					orOperands.add(visitor.yicesModel.eq(value[0], value[j]));
				}
				String command = visitor.yicesModel.or(orOperands);
				Pointer exp = visitor.yices.yices_parse_expression(visitor.ctx, command);
				visitor.yices.yices_assert(visitor.ctx, exp);
				logger.debug("(assert " + command + ")");
			}
			assertEquals("sat", visitor.check());
			i++;
		}
		for(; i < expectedValues.size(); i++) {
			visitor.assertRules();
			for(String[] value: expectedValues.get(i)) {
				ArrayList<String> orOperands = new ArrayList<String>();
				for(int j = 2; j < value.length; j++) {
					orOperands.add(visitor.yicesModel.eq(value[0], value[j]));
				}
				String command = visitor.yicesModel.or(orOperands);
				Pointer exp = visitor.yices.yices_parse_expression(visitor.ctx, command);
				visitor.yices.yices_assert(visitor.ctx, exp);
				logger.debug("(assert " + command + ")");
			}
			assertEquals("sat", visitor.check());
		}
	}
}