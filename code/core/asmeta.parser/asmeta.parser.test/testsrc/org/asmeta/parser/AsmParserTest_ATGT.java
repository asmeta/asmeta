package org.asmeta.parser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;


/**
 * test the examples for ATGT
 * it works only on AG computers - sorry
 *
 * @author garganti
 *
 */
public class AsmParserTest_ATGT extends AsmParserTest{

	// @Test
	public void testExamples(){
		//testDir("examples/agents/");
		testDir("../../prj_root/atgt/source/atgt_examples/");
	}

	@Test
	public void testCC(){
		//testDir("examples/agents/");
		AsmCollection asms = testOneSpec("../../prj_root/atgt/source/atgt_examples/cruiseControl.asm");
		assertNotNull(asms);
		Asm main = asms.getMain();
		assertNotNull(main);
		assertEquals("cruiseControl", main.getName());
		for (FunctionInitialization fi : main.getDefaultInitialState().getFunctionInitialization()){
			System.out.println("f " + fi.getInitializedFunction() + " value "+ fi.getBody());
			assertNotNull( fi.getBody());
		}
	}

}