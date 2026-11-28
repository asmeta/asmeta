package org.asmeta.modeladvisor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.modeladvisor.AsmetaMA.ExecCheck;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.transitionrules.basictransitionrules.ChooseRule;

/* Given a choose rule like:
 *  choose $x in D with F($x,ENV) do R
 * it must be possible to execute R, so ... TODO  
 */


public class AsmetaMATestChoose {

	@Test
	void chooseBoolean() throws Exception {
		Logger.getLogger(AsmetaMA.class).setLevel(Level.DEBUG);
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseBoolean.asm");
		asmetaMA.activateExecCheck(ExecCheck.execRuleIsReached,true);
		Map<String, Boolean> result = asmetaMA.runCheck();
		System.out.println(result);
		assertEquals(0, asmetaMA.ruleIsReached.neverReachedRule.size());
		assertEquals(2, asmetaMA.ruleIsReached.notAlwaysReachedRule.size());
	}

	@Test
	void chooseAlwaysNotEmpty() throws Exception {
		// with alse passes, with true it does not 
		// AsmetaSMVOptions.FLATTEN_CHOOSE_TOINV = false;
		// this now fails because I changed the way choose is translated
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseAlwaysNotEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty, true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(alwaysNot.size(), expectedAlwaysNot.size());
		for (ChooseRule choooseRule : alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(0, notAlways.size());
		assertEquals(0, always.size());
	}

	@Test
	void chooseAlwaysNotEmpty2() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseAlwaysNotEmptyIfNone.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty, true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(alwaysNot.size(), expectedAlwaysNot.size());
		for (ChooseRule choooseRule : alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(0, notAlways.size());
		assertEquals(0, always.size());
	}

	@Test
	void chooseAlwaysEmpty() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		Set<String> expectedNotAlways = new HashSet<String>();
		Set<String> expectedAlways = new HashSet<String>();
		expectedAlways.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseAlwaysEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty, true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(0, alwaysNot.size());
		for (ChooseRule choooseRule : alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(notAlways.size(), expectedNotAlways.size());
		for (ChooseRule choooseRule : notAlways) {
			assertTrue(expectedNotAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(always.size(), expectedAlways.size());
		for (ChooseRule choooseRule : always) {
			assertTrue(expectedAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
	}

	@Test
	@Tag("TestToMavenSkip")
	void chooseRuleIsEmpty() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$o");
		expectedAlwaysNot.add("$h");
		expectedAlwaysNot.add("$x");
		Set<String> expectedNotAlways = new HashSet<String>();
		expectedNotAlways.add("$k");
		Set<String> expectedAlways = new HashSet<String>();
		expectedAlways.add("$z");
		expectedAlways.add("$w");
		expectedAlways.add("$p");
		Set<String> expectedNeverExecuted = new HashSet<String>();
		expectedNeverExecuted.add("$y");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty, true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		Set<ChooseRule> never = asmetaMA.chooseRuleIsEmpty.chooseRuleNeverExecuted;
		assertEquals(expectedAlwaysNot.size(), alwaysNot.size());
		for (ChooseRule choooseRule : alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedNotAlways.size(), notAlways.size());
		for (ChooseRule choooseRule : notAlways) {
			assertTrue(expectedNotAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedAlways.size(), always.size());
		for (ChooseRule choooseRule : always) {
			assertTrue(expectedAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedNeverExecuted.size(), never.size());
		for (ChooseRule choooseRule : never) {
			assertTrue(expectedNeverExecuted.contains(choooseRule.getVariable().get(0).getName()));
		}
	}

	@Test
	void chooseNeverExecuted() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseNeverExecuted.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty, true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(0, alwaysNot.size());
		assertEquals(0, notAlways.size());
		assertEquals(0, always.size());
	}

}
