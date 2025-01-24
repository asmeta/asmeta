package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class GeneratorCompilerSmartPillBoxTest extends GeneratorCompilerTest{

	@Test
	public void SmartPillBoxLevel0Test() throws Exception, IOException
	{
		String asmspec = "..//..//..//..//asm_examples//PillBox//Level0//pillbox_0.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec, testOptions, false).success)
			fail();
	}
	
	//@Test
	public void SmartPillBoxLevel1Test() throws Exception, IOException
	{
		String asmspec = "..//..//..//..//asm_examples//PillBox//Level1//pillbox_1.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec, testOptions, false).success)
			fail();
	}
	
	//@Test
	public void SmartPillBoxLevel2Test() throws Exception, IOException
	{
		String asmspec = "..//..//..//..//asm_examples//PillBox//Level2//pillbox_2.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec, testOptions, false).success)
			fail();
	}

	//@Test
	public void SmartPillBoxLevel3Test() throws Exception, IOException
	{
		String asmspec = "..//..//..//..//asm_examples//PillBox//Level3//pillbox_FULL.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec, testOptions, false).success)
			fail();
	}
}
