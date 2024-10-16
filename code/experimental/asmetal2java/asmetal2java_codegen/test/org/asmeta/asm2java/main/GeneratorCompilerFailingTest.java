package org.asmeta.asm2java.main;


import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class GeneratorCompilerFailingTest {

	
	private TranslatorOptions options= new TranslatorOptions(true, true, true, false, false, false, false);

     @Test 
     public void testFibonacci() throws IOException, Exception { 
         String asmspec = "examples/fibonacci.asm"; 
         assertTrue(GeneratorCompilerTest.test(asmspec, options).getSuccess()); 
     } 

}
