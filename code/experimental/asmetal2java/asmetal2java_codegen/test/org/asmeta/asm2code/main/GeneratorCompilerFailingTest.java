package org.asmeta.asm2code.main;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.main.*;
//import org.asmeta.asm2java.main.JavaGenerator;
//import org.asmeta.asm2java.main.JavaWindowGenerator;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class GeneratorCompilerFailingTest {

	
	private TranslatorOptions options= new TranslatorOptions(true, true, true);

     @Test 
     public void testFibonacci() throws IOException, Exception { 
         String asmspec = "examples/fibonacci.asm"; 
         assertTrue(GeneratorCompilerTest.test(asmspec, options).success); 
     } 

}
