package org.asmeta.atgt.rndgenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.asmeta.atgt.generator.AsmTestGeneratorTest;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.RuleEvaluatorObserver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.testseqexport.toAvalla;
import tgtlib.definitions.expression.type.Variable;

public class AsmTestGeneratorRandWPick {

	@Test
	public void testWithChooseWPick() throws Exception {
		String test = AsmTestGeneratorBySimulationTest.generateFor("examples/simplechoose.asm", true);
		//System.out.println(test);
		assertTrue(test.contains("pick $i in r_Main"));
	}
	
	@Test
	public void testWithChooseWOPick() throws Exception {
		String test = AsmTestGeneratorBySimulationTest.generateFor("examples/simplechoose.asm", false);
		System.out.println(test);
		assertFalse(test.contains("pick $i in r_Main"));
		assertTrue(test.contains("check myLetter = AA"));
	}

	
}
