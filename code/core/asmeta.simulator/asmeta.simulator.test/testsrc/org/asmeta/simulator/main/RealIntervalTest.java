package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.asmeta.simulator.State;
import org.asmeta.simulator.value.SetValue;
import org.junit.jupiter.api.Test;

import asmeta.definitions.domains.Domain;

/**
 * Test for Simulator class.
 *
 */
class RealIntervalTest extends BaseTest {

	@Test void test01() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/testRanges1.asm");
		Domain dom = searchDomain("DiceValue2");
		SetValue values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0.6,1.6,2.6,3.6,4.6,5.6}", values.toString());
		dom = searchDomain("DiceValue3");
		values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0,1,2,3,4,5,6}", values.toString());
		dom = searchDomain("DiceValue4");
		values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{4,5,6}", values.toString());
		sim.run(1);
		State s = sim.getCurrentState();
		System.out.println(s);
	}

	@Test void test02() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/testRanges2.asm");
		Domain dom = searchDomain("DiceValue2");
		SetValue values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0.6,0.7,0.8,0.9}", values.toString());
		dom = searchDomain("DiceValue3");
		values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0,2,4,6}", values.toString());
		dom = searchDomain("DiceValue4");
		values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0,2,4,6}", values.toString());
		dom = searchDomain("DiceValue5");
		values = sim.ruleEvaluator.termEval.getValues(dom);
		assertEquals("{0.6}", values.toString());
		//
		sim.run(1);
		State s = sim.getCurrentState();
		System.out.println(s);

	}

}
