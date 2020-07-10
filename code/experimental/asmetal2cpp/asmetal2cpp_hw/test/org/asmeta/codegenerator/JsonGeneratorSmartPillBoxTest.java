package org.asmeta.codegenerator;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class JsonGeneratorSmartPillBoxTest extends JsonGeneratorAbstractClass {

	@Test
	public void SmartPillBoxLevel0Test() throws Exception, IOException
	{
		String asmspec = "C:\\asmeta\\asm_examples\\PillBox\\Level0\\pillbox_0.asm";
		testGenerator(asmspec, true);
	}
	
	@Test
	public void SmartPillBoxLevel1Test() throws Exception, IOException
	{
		String asmspec = "C:\\asmeta\\asm_examples\\PillBox\\Level1\\pillbox_1.asm";
		testGenerator(asmspec, true);
	}
	
	@Test
	public void SmartPillBoxLevel2Test() throws Exception, IOException
	{
		String asmspec = "C:\\asmeta\\asm_examples\\PillBox\\Level2\\pillbox_2.asm";
		testGenerator(asmspec, true);
	}
	
	@Test
	public void SmartPillBoxLevel3Test() throws Exception, IOException
	{
		String asmspec = "C:\\asmeta\\asm_examples\\PillBox\\Level3\\pillbox_FULL.asm";
		testGenerator(asmspec, true);
	}
}
