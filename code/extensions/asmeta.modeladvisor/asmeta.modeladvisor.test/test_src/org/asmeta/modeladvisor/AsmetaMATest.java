package org.asmeta.modeladvisor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.modeladvisor.AsmetaMA.ExecCheck;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.Test;

import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmetaMATest {

	@Test
	public void testCaseRuleIsComplete() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/caseIsComplete.asm");
		asmetaMA.activateExecCheck(ExecCheck.execCaseRuleIsComplete,true);
		asmetaMA.runCheck();
		int numOfComplete = 0;
		for(boolean result: asmetaMA.caseRuleIsCompl.getCaseRuleComplete().values()) {
			if(result) {
				numOfComplete++;
			}
		}
		assertEquals(4, numOfComplete);
	}

	@Test
	public void testCaseCertifier() throws Exception {
		// model advisor applied to the stereoacuity certifier
		// used to work but now it does not produce anything
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/certifierRaff5.asm");
		asmetaMA.setAllMetapropertiesExecution();
		Map<String, Boolean> result = asmetaMA.runCheck();
		System.out.println(result);
	}

	
	@Test
	public void testChooseBoolean() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseBoolean.asm");
		asmetaMA.activateExecCheck(ExecCheck.execRuleIsReached,true);
		Map<String, Boolean> result = asmetaMA.runCheck();
		System.out.println(result);
		assertEquals(0, asmetaMA.ruleIsReached.neverReachedRule.size());
		assertEquals(2, asmetaMA.ruleIsReached.notAlwaysReachedRule.size());
	}

	@Test
	public void testChooseAlwaysNotEmpty() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseAlwaysNotEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty,true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(alwaysNot.size(), expectedAlwaysNot.size());
		for(ChooseRule choooseRule: alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(0, notAlways.size());
		assertEquals(0, always.size());
	}

	@Test
	public void testChooseAlwaysEmpty() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		Set<String> expectedNotAlways = new HashSet<String>();
		Set<String> expectedAlways = new HashSet<String>();
		expectedAlways.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseAlwaysEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty,true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(0, alwaysNot.size());
		for(ChooseRule choooseRule: alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(notAlways.size(), expectedNotAlways.size());
		for(ChooseRule choooseRule: notAlways) {
			assertTrue(expectedNotAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(always.size(), expectedAlways.size());
		for(ChooseRule choooseRule: always) {
			assertTrue(expectedAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
	}

	@Test
	public void testChooseRuleIsEmpty() throws Exception {
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
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty,true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		Set<ChooseRule> never = asmetaMA.chooseRuleIsEmpty.chooseRuleNeverExecuted;
		assertEquals(expectedAlwaysNot.size(), alwaysNot.size());
		for(ChooseRule choooseRule: alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedNotAlways.size(), notAlways.size());
		for(ChooseRule choooseRule: notAlways) {
			assertTrue(expectedNotAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedAlways.size(), always.size());
		for(ChooseRule choooseRule: always) {
			assertTrue(expectedAlways.contains(choooseRule.getVariable().get(0).getName()));
		}
		assertEquals(expectedNeverExecuted.size(), never.size());
		for(ChooseRule choooseRule: never) {
			assertTrue(expectedNeverExecuted.contains(choooseRule.getVariable().get(0).getName()));
		}
	}

	@Test
	public void testChooseNeverExecuted() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/chooseNeverExecuted.asm");
		asmetaMA.activateExecCheck(ExecCheck.execChooseRuleEmpty,true);
		asmetaMA.runCheck();
		Set<ChooseRule> alwaysNot = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysNotEmpty;
		Set<ChooseRule> notAlways = asmetaMA.chooseRuleIsEmpty.chooseRuleNotAlwaysEmpty;
		Set<ChooseRule> always = asmetaMA.chooseRuleIsEmpty.chooseRuleAlwaysEmpty;
		assertEquals(0, alwaysNot.size());
		assertEquals(0, notAlways.size());
		assertEquals(0, always.size());
	}

	@Test
	public void testCondRuleEvalToTrue() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/condIsEvalToTrue.asm");
		asmetaMA.activateExecCheck(ExecCheck.execCondRuleEvalToTrue,true);
		asmetaMA.runCheck();
		assertEquals(3, asmetaMA.condRuleEval.getNeverThen().size());
		assertEquals(1, asmetaMA.condRuleEval.getNeverElse().size());
		assertEquals(1, asmetaMA.condRuleEval.getNeverThenElse().size());
	}

	@Test
	public void testCondRuleIsComplete() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/condIsComplete.asm");
		asmetaMA.activateExecCheck(ExecCheck.execCondRuleIsComplete,true);
		asmetaMA.runCheck();
		int numTrue = 0;
		int numFalse = 0;
		for(Boolean result: asmetaMA.condRuleIsCompl.getCondRuleComplete().values()) {
			if(result) {
				numTrue++;
			}
			else {
				numFalse++;
			}
		}
		assertEquals(8, numTrue);
		assertEquals(2, numFalse);
	}

	@Test
	public void testContrLocCouldBeStatic() throws Exception {
		Set<String> locCouldBeStaticExpected = new HashSet<String>();
		locCouldBeStaticExpected.add("fooA");
		locCouldBeStaticExpected.add("fooC");
		locCouldBeStaticExpected.add("fooE_AA");
		locCouldBeStaticExpected.add("fooE_BB");
		locCouldBeStaticExpected.add("fooE_CC");
		locCouldBeStaticExpected.add("fooF_AA");
		locCouldBeStaticExpected.add("fooG");
		Set<String> funcCouldBeStaticExpected = new HashSet<String>();
		funcCouldBeStaticExpected.add("fooA");
		funcCouldBeStaticExpected.add("fooC");
		funcCouldBeStaticExpected.add("fooE");
		funcCouldBeStaticExpected.add("fooG");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/controlledLocationCouldBeStatic.asm");
		asmetaMA.activateExecCheck(ExecCheck.execContrLocCouldBeStatic,true);
		asmetaMA.runCheck();
		assertEquals(asmetaMA.contrLocStatic.locCouldBeStatic.size(), locCouldBeStaticExpected.size());
		for(String location: asmetaMA.contrLocStatic.locCouldBeStatic) {
			assertTrue(locCouldBeStaticExpected.contains(location));
		}
		assertEquals(asmetaMA.contrLocStatic.funcCouldBeStatic.size(), funcCouldBeStaticExpected.size());
		for(String function: asmetaMA.contrLocStatic.funcCouldBeStatic) {
			assertTrue(funcCouldBeStaticExpected.contains(function));
		}
	}

	@Test
	public void testContrLocIsUpdated() throws Exception {
		Set<String> neverUpdated = new HashSet<String>();
		neverUpdated.add("fooB");
		neverUpdated.add("fooE");
		neverUpdated.add("fooF_BB");
		neverUpdated.add("fooG");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/contrLocIsUpdated.asm");
		asmetaMA.activateExecCheck(ExecCheck.execContrLocIsUpdated,true);
		asmetaMA.runCheck();
		for(String location: asmetaMA.contrLocIsUpdated.neverUpdatedLocation) {
			assertTrue(neverUpdated.contains(location));
		}
	}

	@Test
	public void testContrLocTakesEveryValue() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/contrLocAllValues.asm");
		asmetaMA.activateExecCheck(ExecCheck.execContrLocTakesEveryValue,true);
		asmetaMA.runCheck();

		Map<String, List<Integer>> funcCouldBeResized = new HashMap<String, List<Integer>>();
		funcCouldBeResized.put("fooB", new ArrayList<Integer>(2));
		funcCouldBeResized.get("fooB").add(2);
		funcCouldBeResized.get("fooB").add(1);
		funcCouldBeResized.put("fooF", new ArrayList<Integer>(2));
		funcCouldBeResized.get("fooF").add(1);
		funcCouldBeResized.get("fooF").add(2);
		funcCouldBeResized.put("fooH", new ArrayList<Integer>(2));
		funcCouldBeResized.get("fooH").add(2);
		funcCouldBeResized.get("fooH").add(1);
		for(String function: asmetaMA.contrLocTakes.funcCouldBeResized.keySet()) {
			assertTrue(funcCouldBeResized.containsKey(function));
			assertEquals(funcCouldBeResized.get(function).get(0).intValue(),
				asmetaMA.contrLocTakes.funcCouldBeResized.get(function).get(0).size());
			assertEquals(funcCouldBeResized.get(function).get(1).intValue(),
				asmetaMA.contrLocTakes.funcCouldBeResized.get(function).get(1).size());
		}

		Map<String, List<Integer>> locCouldBeResized = new HashMap<String, List<Integer>>();
		locCouldBeResized.put("fooB", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooB").add(2);
		locCouldBeResized.get("fooB").add(1);
		locCouldBeResized.put("fooF", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooF").add(1);
		locCouldBeResized.get("fooF").add(2);
		locCouldBeResized.put("fooH_FALSE", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooH_FALSE").add(2);
		locCouldBeResized.get("fooH_FALSE").add(1);
		locCouldBeResized.put("fooH_TRUE", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooH_TRUE").add(2);
		locCouldBeResized.get("fooH_TRUE").add(1);
		locCouldBeResized.put("fooI_FALSE", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooI_FALSE").add(2);
		locCouldBeResized.get("fooI_FALSE").add(1);
		locCouldBeResized.put("fooI_TRUE", new ArrayList<Integer>(2));
		locCouldBeResized.get("fooI_TRUE").add(2);
		locCouldBeResized.get("fooI_TRUE").add(1);
		for(String location: asmetaMA.contrLocTakes.locCouldBeResized.keySet()) {
			assertTrue(locCouldBeResized.containsKey(location));
			assertEquals(locCouldBeResized.get(location).get(0).intValue(),
				asmetaMA.contrLocTakes.locCouldBeResized.get(location).get(0).size());
			assertEquals(locCouldBeResized.get(location).get(1).intValue(),
				asmetaMA.contrLocTakes.locCouldBeResized.get(location).get(1).size());
		}
		Set<String> initAndNeverUsedLocation = new HashSet<String>();
		initAndNeverUsedLocation.add("fooD");
		initAndNeverUsedLocation.add("fooE");
		initAndNeverUsedLocation.add("fooL_TRUE");
		for(String location: asmetaMA.contrLocTakes.initAndNeverUsedLocation) {
			assertTrue(initAndNeverUsedLocation.contains(location));
		}
		//il set delle locazioni mai usate dovrebbe essere sempre vuoto,
		//perche' anche se non viene inizializzata ed usata, da AsmetaSMV viene
		//inizializzata ad undef e, quindi, viene classificata come
		//initAndNeverUsedLocation.
		assertTrue(asmetaMA.contrLocTakes.neverUsedLocation.size()==0);		
	}

	@Test
	public void testDomainAllUsed() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/usedDomain.asm");
		asmetaMA.activateExecCheck(ExecCheck.execDomainAllUsed,true);
		asmetaMA.runCheck();
		Map<String, Set<String>> expectedUsedVals = new HashMap<String, Set<String>>();
		expectedUsedVals.put("EnumDom", new HashSet<String>());
		expectedUsedVals.get("EnumDom").add("AA");
		expectedUsedVals.get("EnumDom").add("BB");
		expectedUsedVals.put("EnumDom2", new HashSet<String>());
		expectedUsedVals.put("EnumDom3", new HashSet<String>());
		expectedUsedVals.get("EnumDom3").add("GG");
		expectedUsedVals.get("EnumDom3").add("HH");
		expectedUsedVals.get("EnumDom3").add("II");
		expectedUsedVals.put("EnumDom4", new HashSet<String>());
		expectedUsedVals.get("EnumDom4").add("LL");
		expectedUsedVals.get("EnumDom4").add("MM");
		expectedUsedVals.get("EnumDom4").add("NN");
		expectedUsedVals.put("EnumDom5", new HashSet<String>());
		expectedUsedVals.put("SubDom", new HashSet<String>());
		expectedUsedVals.get("SubDom").add("1");
		expectedUsedVals.get("SubDom").add("2");
		expectedUsedVals.get("SubDom").add("3");
		expectedUsedVals.get("SubDom").add("4");
		expectedUsedVals.put("SubDomAll", new HashSet<String>());
		expectedUsedVals.get("SubDomAll").add("1");
		expectedUsedVals.get("SubDomAll").add("2");
		expectedUsedVals.put("SubDomAll2", new HashSet<String>());
		for(int i=1;i<=10;i++) {
			expectedUsedVals.get("SubDomAll2").add(String.valueOf(i));
		}
		expectedUsedVals.put("SubDomAll3", new HashSet<String>());
		for(int i=1;i<=9;i++) {
			expectedUsedVals.get("SubDomAll3").add(String.valueOf(i));
		}
		for(String domain: asmetaMA.domainAllUsed.usedVals.keySet()) {
			assertTrue(expectedUsedVals.containsKey(domain));
			for(String value: asmetaMA.domainAllUsed.usedVals.get(domain)) {
				assertTrue(expectedUsedVals.get(domain).contains(value));
			}
		}
		Map<String, Set<String>> expectedNotUsedVals = new HashMap<String, Set<String>>();
		expectedNotUsedVals.put("EnumDom", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom").add("CC");
		expectedNotUsedVals.put("EnumDom2", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom2").add("DD");
		expectedNotUsedVals.get("EnumDom2").add("EE");
		expectedNotUsedVals.get("EnumDom2").add("FF");
		expectedNotUsedVals.put("EnumDom3", new HashSet<String>());
		expectedNotUsedVals.put("EnumDom4", new HashSet<String>());
		expectedNotUsedVals.put("SubDom", new HashSet<String>());
		expectedNotUsedVals.get("SubDom").add("5");
		expectedNotUsedVals.get("SubDom").add("6");
		expectedNotUsedVals.put("SubDomAll", new HashSet<String>());
		expectedNotUsedVals.put("SubDomAll2", new HashSet<String>());
		expectedNotUsedVals.put("SubDomAll3", new HashSet<String>());
		for(int i=10;i<=20;i++) {
			expectedNotUsedVals.get("SubDomAll3").add(String.valueOf(i));
		}
		for(String domain: asmetaMA.domainAllUsed.notUsedVals.keySet()) {
			assertTrue(expectedNotUsedVals.containsKey(domain));
			for(String value: asmetaMA.domainAllUsed.notUsedVals.get(domain)) {
				assertTrue("domain " + expectedNotUsedVals.get(domain) + " should contain " + value, expectedNotUsedVals.get(domain).contains(value));
			}
		}
	}

	@Test
	public void testDomainAllUsed2() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/usedDomain2.asm");
		asmetaMA.activateExecCheck(ExecCheck.execDomainAllUsed,true);
		asmetaMA.runCheck();
		Map<String, Set<String>> expectedUsedVals = new HashMap<String, Set<String>>();
		expectedUsedVals.put("EnumDom", new HashSet<String>());
		expectedUsedVals.get("EnumDom").add("AA");
		expectedUsedVals.get("EnumDom").add("CC");
		expectedUsedVals.put("EnumDom2", new HashSet<String>());
		expectedUsedVals.get("EnumDom2").add("EE");
		expectedUsedVals.put("EnumDom3", new HashSet<String>());
		expectedUsedVals.get("EnumDom3").add("II");
		expectedUsedVals.get("EnumDom3").add("GG");
		expectedUsedVals.put("EnumDom4", new HashSet<String>());
		expectedUsedVals.get("EnumDom4").add("LL");
		expectedUsedVals.put("EnumDom5", new HashSet<String>());
		assert asmetaMA.domainAllUsed.usedVals != null;
		for(String domain: asmetaMA.domainAllUsed.usedVals.keySet()) {
			assertTrue(expectedUsedVals.containsKey(domain));
			for(String value: asmetaMA.domainAllUsed.usedVals.get(domain)) {
				assertTrue(expectedUsedVals.get(domain).contains(value));
			}
		}
		Map<String, Set<String>> expectedNotUsedVals = new HashMap<String, Set<String>>();
		expectedNotUsedVals.put("EnumDom", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom").add("BB");
		expectedNotUsedVals.put("EnumDom2", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom2").add("DD");
		expectedNotUsedVals.get("EnumDom2").add("FF");
		expectedNotUsedVals.put("EnumDom3", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom3").add("HH");
		expectedNotUsedVals.put("EnumDom4", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom4").add("MM");
		expectedNotUsedVals.get("EnumDom4").add("NN");
		expectedNotUsedVals.put("EnumDom5", new HashSet<String>());
		expectedNotUsedVals.get("EnumDom5").add("PP");
		expectedNotUsedVals.get("EnumDom5").add("OO");
		for(String domain: asmetaMA.domainAllUsed.notUsedVals.keySet()) {
			assertTrue(expectedNotUsedVals.containsKey(domain));
			for(String value: asmetaMA.domainAllUsed.notUsedVals.get(domain)) {
				assertTrue(expectedNotUsedVals.get(domain).contains(value));
			}
		}
	}

	@Test
	public void testForallRuleIsEmpty() throws Exception {
		Set<String> expectedAlwaysNot = new HashSet<String>();
		expectedAlwaysNot.add("$x");
		expectedAlwaysNot.add("$w");
		expectedAlwaysNot.add("$s");
		expectedAlwaysNot.add("$q");
		Set<String> expectedNotAlways = new HashSet<String>();
		expectedNotAlways.add("$i");
		expectedNotAlways.add("$f");
		Set<String> expectedAlways = new HashSet<String>();
		expectedAlways.add("$y");
		expectedAlways.add("$d");
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/forallEmpty.asm");
		asmetaMA.activateExecCheck(ExecCheck.execForallRuleEmpty,true);
		asmetaMA.runCheck();
		Set<ForallRule> alwaysNot = asmetaMA.forallRuleIsEmpty.forallRuleAlwaysNotEmpty;
		Set<ForallRule> notAlways = asmetaMA.forallRuleIsEmpty.forallRuleNotAlwaysEmpty;
		Set<ForallRule> always = asmetaMA.forallRuleIsEmpty.forallRuleAlwaysEmpty;
		assertEquals(alwaysNot.size(), expectedAlwaysNot.size());
		for(ForallRule forallRule: alwaysNot) {
			assertTrue(expectedAlwaysNot.contains(forallRule.getVariable().get(0).getName()));
		}
		assertEquals(notAlways.size(), expectedNotAlways.size());
		for(ForallRule forallRule: notAlways) {
			assertTrue(expectedNotAlways.contains(forallRule.getVariable().get(0).getName()));
		}
		assertEquals(always.size(), expectedAlways.size());
		for(ForallRule forallRule: always) {
			assertTrue(expectedAlways.contains(forallRule.getVariable().get(0).getName()));
		}
	}

	@Test
	public void testInconsistentUpdate() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/inconsistentUpdates.asm");
		asmetaMA.activateExecCheck(ExecCheck.execInconsistentUpdates,true);
		asmetaMA.runCheck();
		Map<String, Integer> exptectedInconUpdate = new HashMap<String, Integer>();
		exptectedInconUpdate.put("fooA", 6);
		exptectedInconUpdate.put("fooC", 1);
		exptectedInconUpdate.put("fooD", 3);
		exptectedInconUpdate.put("fooE", 1);
		exptectedInconUpdate.put("fooF", 1);
		exptectedInconUpdate.put("fooG", 1);
		for(String location: asmetaMA.inconUpd.inconUpdate.keySet()) {
			assertTrue(exptectedInconUpdate.keySet().contains(location));
			assertEquals(asmetaMA.inconUpd.inconUpdate.get(location).size(),
					exptectedInconUpdate.get(location).intValue());
		}
	}

	@Test
	public void testLocationCouldBeRemoved() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/locationCouldBeRemoved.asm");
		asmetaMA.activateExecCheck(ExecCheck.execLocationCouldBeRemoved,true);
		asmetaMA.runCheck();
		Set<String> initButNeverUsedControlledLocation = new HashSet<String>();
		initButNeverUsedControlledLocation.add("fooA");
		for(String location: asmetaMA.locCouldBeRem.initButNeverUsedControlledLocation) {
			assertTrue(initButNeverUsedControlledLocation.contains(location));
		}
		Set<String> initButUsedInUnreachContrLoc = new HashSet<String>();
		initButUsedInUnreachContrLoc.add("fooD");
		initButUsedInUnreachContrLoc.add("fooH_CC");
		for(String location: asmetaMA.locCouldBeRem.initButUsedInUnreachContrLoc) {
			assertTrue(initButUsedInUnreachContrLoc.contains(location));
		}
		Set<String> neverUsedControlledLocation = new HashSet<String>();
		neverUsedControlledLocation.add("fooC");
		for(String location: asmetaMA.locCouldBeRem.neverUsedControlledLocation) {
			assertTrue(neverUsedControlledLocation.contains(location));
		}
		Set<String> neverUsedMonitoredLocation = new HashSet<String>();
		neverUsedMonitoredLocation.add("mon2");
		for(String location: asmetaMA.locCouldBeRem.neverUsedMonitoredLocation) {
			assertTrue(neverUsedMonitoredLocation.contains(location));
		}
		Set<String> usedInUnreachContrLoc = new HashSet<String>();
		usedInUnreachContrLoc.add("fooE");
		for(String location: asmetaMA.locCouldBeRem.usedInUnreachContrLoc) {
			assertTrue(usedInUnreachContrLoc.contains(location));
		}
		Set<String> usedInUnreachMonLoc = new HashSet<String>();
		usedInUnreachMonLoc.add("mon3");
		for(String location: asmetaMA.locCouldBeRem.usedInUnreachMonLoc) {
			assertTrue(usedInUnreachMonLoc.contains(location));
		}
	}

	@Test
	public void testMacroCallRuleIsReached() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/macroRuleReached.asm");
		asmetaMA.activateExecCheck(ExecCheck.execMacroCallRuleIsReached,true);
		asmetaMA.runCheck();
		Set<String> neverReachedRule = new HashSet<String>();
		neverReachedRule.add("r_e");
		for(Rule r: asmetaMA.mcrIsReached.ruleIsReached.neverReachedRule) {
			assertTrue(neverReachedRule.contains(((MacroCallRule)r).getCalledMacro().getName()));
		}
		Set<String> notAlwaysReachedRule = new HashSet<String>();
		notAlwaysReachedRule.add("r_a");
		for(Rule r: asmetaMA.mcrIsReached.ruleIsReached.notAlwaysReachedRule) {
			assertTrue(notAlwaysReachedRule.contains(((MacroCallRule)r).getCalledMacro().getName()));
		}
	}

	@Test
	public void testMacroRuleCalled() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/macroRuleCalled.asm");
		asmetaMA.activateExecCheck(ExecCheck.execMacroRuleCalled,true);
		asmetaMA.runCheck();
		String ruleName;
		Map<MacroDeclaration, Integer> numOfCalls = asmetaMA.macroRuleCalled.numOfCalls;
		for(MacroDeclaration rule: asmetaMA.macroRuleCalled.numOfCalls.keySet()) {
			ruleName = rule.getName();
			if(ruleName.equals("r_a")) {
				assertEquals(numOfCalls.get(rule).intValue(), 0);
			}
			else if(ruleName.equals("r_b")) {
				assertEquals(numOfCalls.get(rule).intValue(), 0);
			}
			else if(ruleName.equals("r_c")) {
				assertEquals(numOfCalls.get(rule).intValue(), 1);
			}
			else if(ruleName.equals("r_d")) {
				assertEquals(numOfCalls.get(rule).intValue(), 1);
			}
			else if(ruleName.equals("r_e")) {
				assertEquals(numOfCalls.get(rule).intValue(), 1);
			}
		}
	}

	@Test
	public void testRuleIsReached() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/ruleIsReached.asm");
		asmetaMA.activateExecCheck(ExecCheck.execRuleIsReached,true);
		asmetaMA.runCheck();
		assertEquals(1, asmetaMA.ruleIsReached.neverReachedRule.size());
		assertEquals(1, asmetaMA.ruleIsReached.notAlwaysReachedRule.size());
	}

	@Test
	public void testStatDerIsUsed() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/statDerIsUsed.asm");
		asmetaMA.activateExecCheck(ExecCheck.execStatDerIsUsed,true);
		asmetaMA.runCheck();

		Set<String> expectedDerFuncNeverUsed = new HashSet<String>();
		expectedDerFuncNeverUsed.add("der_der");
		expectedDerFuncNeverUsed.add("der_doubleif");
		expectedDerFuncNeverUsed.add("der_doubleswitch");
		expectedDerFuncNeverUsed.add("der_ifswitch");
		expectedDerFuncNeverUsed.add("der_switchif");
		expectedDerFuncNeverUsed.add("fooDerG");
		for(String function: asmetaMA.statDerIsUsed.derFuncNeverUsed) {
			//System.err.println(function);
			assertTrue(expectedDerFuncNeverUsed.contains(function));
		}

		Set<String> derFuncUsedInUnreachCode = new HashSet<String>();
		derFuncUsedInUnreachCode.add("fooDerB");
		for(String function: asmetaMA.statDerIsUsed.derFuncUsedInUnreachCode) {
			assertTrue(derFuncUsedInUnreachCode.contains(function));
		}
		
		Set<String> derLocNotUsed = new HashSet<String>();
		derLocNotUsed.add("fooDerB");
		derLocNotUsed.add("der_der");
		derLocNotUsed.add("der_doubleif");
		derLocNotUsed.add("der_doubleswitch");
		derLocNotUsed.add("der_ifswitch");
		derLocNotUsed.add("der_switchif");
		derLocNotUsed.add("fooDerG");
		derLocNotUsed.add("fooDer_FALSE");
		for(String location: asmetaMA.statDerIsUsed.derLocNotUsed) {
			assertTrue(derLocNotUsed.contains(location));
		}
		
		Set<String> derLocUsedInUnreachCode = new HashSet<String>();
		derLocUsedInUnreachCode.add("fooDerA_TRUE");
		derLocUsedInUnreachCode.add("fooDerB_FALSE");
		derLocUsedInUnreachCode.add("fooDerB_TRUE");
		for(String location: asmetaMA.statDerIsUsed.derLocUsedInUnreachCode) {
			assertTrue(derLocUsedInUnreachCode.contains(location));
		}

		Set<String> statFuncNeverUsed = new HashSet<String>();
		statFuncNeverUsed.add("statFunc");
		for(String function: asmetaMA.statDerIsUsed.statFuncNeverUsed) {
			assertTrue(statFuncNeverUsed.contains(function));
		}

		Set<String> statFuncUsedInUnreachCode = new HashSet<String>();
		statFuncUsedInUnreachCode.add("fooStatB");
		for(String function: asmetaMA.statDerIsUsed.statFuncUsedInUnreachCode) {
			assertTrue(statFuncUsedInUnreachCode.contains(function));
		}

		Set<String> statLocNotUsed = new HashSet<String>();
		statLocNotUsed.add("statFunc");
		for(String location: asmetaMA.statDerIsUsed.statLocNotUsed) {
			assertTrue(statLocNotUsed.contains(location));
		}

		Set<String> statLocUsedInUnreachCode = new HashSet<String>();
		statLocUsedInUnreachCode.add("fooStatA_TRUE");
		statLocUsedInUnreachCode.add("fooStatB_FALSE");
		statLocUsedInUnreachCode.add("fooStatB_TRUE");
		for(String location: asmetaMA.statDerIsUsed.statLocUsedInUnreachCode) {
			assertTrue(statLocUsedInUnreachCode.contains(location));
		}
	}

	@Test
	public void testTrivialUpdate() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/trivialUpdate.asm");
		asmetaMA.activateExecCheck(ExecCheck.execNoTrivialUpdate,true);
		asmetaMA.runCheck();
		assertEquals(asmetaMA.trivialUpdate.trivialUpdate.size(), 6);
		assertArrayEquals(new String[]{"fooF", "(TRUE & !(fooB = AA))", "BB"}, asmetaMA.trivialUpdate.trivialUpdate.get(0));
		assertArrayEquals(new String[]{"fooG", "(TRUE & (fooB = AA))", "AA"}, asmetaMA.trivialUpdate.trivialUpdate.get(1));
		assertArrayEquals(new String[]{"fooG", "(TRUE & !(fooB = AA))", "AA"}, asmetaMA.trivialUpdate.trivialUpdate.get(2));
		assertArrayEquals(new String[]{"fooA", "TRUE", "AA"}, asmetaMA.trivialUpdate.trivialUpdate.get(3));
		assertArrayEquals(new String[]{"fooD", "(TRUE & !(fooA = BB))", "AA"}, asmetaMA.trivialUpdate.trivialUpdate.get(4));
		assertArrayEquals(new String[]{"fooE", "TRUE", "fooA"}, asmetaMA.trivialUpdate.trivialUpdate.get(5));
	}

	//non c'e' questo file
	/*@Test
	public void testTrivialUpdate2() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/trivialUpdate2.asm");
		asmetaMA.activateExecCheck(ExecCheck.execNoTrivialUpdate,true);
		asmetaMA.runCheck();
		assertEquals(asmetaMA.trivialUpdate.trivialUpdate.size(), 1);
		String[] res = asmetaMA.trivialUpdate.trivialUpdate.get(0);
		assertEquals(new String[]{"fooA_BB", "(TRUE & mon)", "fooA_CC"}, res);
	}*/

	/*@Test
	public void testRuleIsssssReached() throws Exception {
		Smv4Val asmetaMA = new Smv4Val("../tosmv/examples/MAS_progetti/irrigazione/mine3agents/main.asm");
		asmetaMA.activateExecCheck(ExecCheck.execInconsistentUpdates,true);
		asmetaMA.runCheck();
	}*/
	
	@Test
	public void testLet() throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("../../../../asm_examples/Casestudy/ASM model/CarSystem_adaptive_high_beam2.asm");
		asmetaMA.activateExecCheck(ExecCheck.execInconsistentUpdates,true);
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		asmetaMA.runCheck();
	}
	
	@Test
	public void main() throws Exception {
		Set<String> funcNamesForMP1 = new HashSet<String>();
		funcNamesForMP1.add("stateSHC");
		AsmetaMA s =  AsmetaMA.buildAsmetaMA("trafficMonitoringForExperimentsModelReview4.asm");		
		//, funcNamesForMP1
		s.setMetapropertiesExecution(true, false, false, false, false, false, false);
		s.runCheck();
	}

	
	@Test
	public void testIncosistentUpdate() throws Exception {
		// questo elimina lil consiteupdate durante la traduzione
		//TOFIX
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/inconsistentUpdatesSimple.asm");
		asmetaMA.activateExecCheck(ExecCheck.execInconsistentUpdates,true);
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		asmetaMA.runCheck();		
		System.out.println(asmetaMA.inconUpd.inconUpdate);
	}

	@Test
	public void testIncosistentUpdate2() throws Exception {
		// this is correct
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA("examples/inconsistentUpdatesSimple2.asm");
		asmetaMA.activateExecCheck(ExecCheck.execInconsistentUpdates,true);
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		asmetaMA.runCheck();		
		assertTrue(asmetaMA.inconUpd.inconUpdate.containsKey("fooG"));
	}


}