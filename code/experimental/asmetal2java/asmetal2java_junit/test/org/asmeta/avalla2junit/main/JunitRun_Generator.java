package org.asmeta.avalla2junit.main;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.asmeta.asm2code.main.GeneratorCompilerTest;
import org.asmeta.asm2java.formatter.Formatter;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import com.google.inject.Injector;

import asmetal2java_junit.AvallaToString;

public class JunitRun_Generator {
	
	
	
	//Run Test ascensore
	@Test
	public void testAsmToJunit_asc() throws Exception {
		String junitTest = "src-gen/ascensore_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test rubrica
	@Test
	public void testAsmToJunit_r() throws Exception {
		String junitTest = "src-gen/Rubrica_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test sis
	@Test
	public void testAsmToJunit_sis() throws Exception {
		String junitTest = "src-gen/SIS_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test LGS_GM
	@Test
	public void testAsmToJunit() throws Exception {
		String junitTest = "src-gen/LGS_GM_Test_1.java";
		extracted(junitTest);
	}

	//Run Test Contatore_U_DA_H
	@Test
	public void testAsmToJunit_contU() throws Exception {
		String junitTest = "src-gen/Contatore_U_DA_H_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test tombola
	@Test
	public void testAsmToJunit_tomb() throws Exception {
		String junitTest = "src-gen/tombola_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test coffeeVendingMachine
	@Test
	public void testAsmToJunit_coffeeV() throws Exception {
		String junitTest = "src-gen/coffeeVendingMachine_Test_1.java";
		extracted(junitTest);
	}
	//Run Test euclideMCD
	@Test
	public void testAsmToJunit_euclide() throws Exception {
		String junitTest = "src-gen/euclideMCD_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test advanceClock
	@Test
	public void testAsmToJunit_advanceClock() throws Exception {
		String junitTest = "src-gen/AdvancedClock_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test sluiceGateGround
	@Test
	public void testAsmToJunit_sluice() throws Exception {
		String junitTest = "src-gen/sluiceGateGround_Test_1.java";
		extracted(junitTest);
	}
	
	//Run Test forno
	@Test
	public void testAsmToJunit_forno() throws Exception {
		String junitTest = "src-gen/forno_Test_1.java";
		extracted(junitTest);
	}
	
	
	//*************************************
	//Run all Test
	@Test
	public void runAll() throws Exception {
		run_all_Test();
	}
	//**************************************

	
	
	//Single Test
	//JunitTestP: Path test junit
	private void extracted(String junitTestP) throws Exception, IOException {
		
		String temp = junitTestP.replace("src-gen", "").substring(1).replace(".java", "");
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new RunListener());
		Result result = junitCore.run(getClassFromFile(temp));
		System.out.println("Test name: " + temp);
		System.out.println("Test run: " + result.getRunCount() + " Test fail: " + result.getFailureCount() + "\n");
		
	}
	
	//All Test
	private void run_all_Test() throws Exception,IOException {
		File dir = new File("src-gen/");
		File[] files = dir.listFiles();
		for(File f : files) {
			if(f.toString().contains("_Test_") && !f.toString().contains(".class")) {
				String temp = f.toString().replace("src-gen", "").substring(1).replace(".java", "");
				JUnitCore junitCore = new JUnitCore();
				junitCore.addListener(new RunListener());
				Result result = junitCore.run(getClassFromFile(temp));
				System.out.println("Test name: " + temp);
				System.out.println("Test run: " + result.getRunCount() + " Test fail: " + result.getFailureCount() + "\n");
			}
		}
	}
	
	
	
	private static final String CLASS_FOLDER ="src-gen/";

	private static Class getClassFromFile(String fullClassName) throws Exception {
	    URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file://" + CLASS_FOLDER) });
	    return loader.loadClass(fullClassName);
	}
}
