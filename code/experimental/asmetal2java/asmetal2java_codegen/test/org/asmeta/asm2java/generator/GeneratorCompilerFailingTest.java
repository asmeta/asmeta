package org.asmeta.asm2java.generator;


import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.impl.TranslatorOptionsImpl;
import org.junit.Test;

public class GeneratorCompilerFailingTest {

	
	private TranslatorOptions options= new TranslatorOptionsImpl(true, true, true);

     @Test 
     public void testFibonacci() throws IOException, Exception { 
         String asmspec = "examples/fibonacci.asm"; 
         assertTrue(GeneratorCompilerTest.test(asmspec, options).getSuccess()); 
     } 

}