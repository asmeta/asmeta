package org.asmeta.avalla2junit.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;

//import org.asmeta.asm2java.compiler.CompilatoreJava;
//import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import asmeta.AsmCollection;

public class Launcher_Test {

	String junitTestPath = "asmetal2java_junit/src-gen/ascensore_Test_1.java";

	@Test
	public void testAll() throws IOException, Exception {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		System.out.println(junitTestPath);
		junit.run(junitTestPath.getClass());
//		//junit.run(junitTestPath.getClass());
//		File dir = new File("src-gen/");
//		File[] files = dir.listFiles();
//		for(File f : files) {
//
//			if(f.toString().contains("_Test")) {
//				Request request = Request.method(f.getClass(), "methodName");
//				org.junit.runner.Result result = new JUnitCore().run(request);
//				System.out.println(f);
//				org.junit.runner.Result result1 = JUnitCore.runClasses(f.getClass());
//				//junit.run(f.getClass());
//			}
//		}

//		JUnitCore junit = new JUnitCore();
//		junit.addListener(new TextListener(System.out));
//		org.junit.runner.Result result = junit.run(
//		  Ascensore_Test_1.class, 
//		  Ascensore_Test.class);
	}
}

