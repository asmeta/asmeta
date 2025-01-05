package org.asmeta.asm2java.main;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Test;

public class GeneratorCompilerFailingTest {

	
	private TranslatorOptions options= new TranslatorOptions(true, true, true);

     @Test 
     public void testFibonacci() throws IOException, Exception { 
         String asmspec = "examples/fibonacci.asm"; 
         Path dirCompilazione = Path.of("examples/compilazione/");
         assertFalse(GeneratorCompilerUtil.genandcompile(asmspec, options,dirCompilazione, null).getSuccess()); 
     } 
     
     
     
     @Test 
     public void testATM3() throws IOException, Exception { 
         String asmspec = "examples/ATM3.asm"; 
         Path dirCompilazione = Path.of("examples/compilazione/");
         assertFalse(GeneratorCompilerUtil.genandcompile(asmspec, options,dirCompilazione, dirCompilazione).getSuccess()); 
     } 


}
