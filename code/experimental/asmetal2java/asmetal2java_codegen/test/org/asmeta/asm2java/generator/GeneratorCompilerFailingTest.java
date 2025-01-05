package org.asmeta.asm2java.generator;


import static org.junit.Assert.assertFalse;

import java.nio.file.Path;

import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.junit.Test;

public class GeneratorCompilerFailingTest {

	// disable the formatter
	private TranslatorOptions options= new TranslatorOptionsImpl(false, true, true);
	
	private final Path dirCompilazione = GeneratorCompilerUtil.dirCompilazione;
	private final Path dirTraduzione = GeneratorCompilerUtil.dirTraduzione;

     @Test 
     public void testFibonacci() throws Exception { 
         String asmspec = "examples/fibonacci.asm"; 
         assertFalse(GeneratorCompilerUtil.genandcompile(asmspec, options, dirTraduzione, dirCompilazione).getSuccess());
     }
     
     @Test 
     public void testBattleship() throws Exception { 
         String asmspec = "examples/battleship.asm"; 
         assertFalse(GeneratorCompilerUtil.genandcompile(asmspec, options, dirTraduzione, dirCompilazione).getSuccess()); 
     }

}
