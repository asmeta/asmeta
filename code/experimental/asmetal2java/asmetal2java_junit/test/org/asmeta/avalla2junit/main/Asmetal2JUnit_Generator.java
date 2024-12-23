package org.asmeta.avalla2junit.main;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.asmeta.asm2java.main.GeneratorCompilerTest;
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
import asmetal2java_junit.AvallaToString;
import org.asmeta.asm2java.formatter.Formatter;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Asmetal2JUnit_Generator {
	
	//pathTF - path test Folder
	public static final String pathTF = "src-gen/";
	//estensione file JUnit
	public static final String JUnit_EXT = ".java";
	//nome specifica
	public String spec = "";
	
	
	
	
	//Test su ascensore
	@Test
	public void testAsmToJunit_asc() throws Exception {
		String asmspec = "examples/ascensore.asm";
		String avaTest = "examples/scenario2.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su rubrica
	@Test
	public void testAsmToJunit_r() throws Exception {
		String asmspec = "examples/Rubrica.asm";
		String avaTest = "examples/rubrica.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su sis
	@Test
	public void testAsmToJunit_sis() throws Exception {
		String asmspec = "examples/SIS.asm";
		String avaTest = "examples/sis.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su LGS_GM
	@Test
	public void testAsmToJunit() throws Exception {
		String asmspec = "examples/LGS_GM.asm";
		String avaTest = "examples/testBR.avalla";
		generateTest(asmspec,avaTest);
	}

	//Test su Contatore_U_DA_H
	@Test
	public void testAsmToJunit_contU() throws Exception {
		String asmspec = "examples/Contatore_U_DA_H.asm";
		String avaTest = "examples/contatore.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su tombola
	@Test
	public void testAsmToJunit_tomb() throws Exception {
		String asmspec = "examples/tombola.asm";
		String avaTest = "examples/test1.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su coffeeVendingMachine
	@Test
	public void testAsmToJunit_coffeeV() throws Exception {
		String asmspec = "examples/coffeeVendingMachine.asm";
		String avaTest = "examples/cm.avalla";
		generateTest(asmspec,avaTest);
	}
	//Test su euclideMCD
	@Test
	public void testAsmToJunit_euclide() throws Exception {
		String asmspec = "examples/euclideMCD.asm";
		String avaTest = "examples/euclide.avalla";
		generateTest(asmspec,avaTest);
	}
	
	//Test su AdvancedClock
	@Test
	public void testAsmToJunit_advanceClock() throws Exception {
		String asmspec = "examples/AdvancedClock.asm";
		String avaTest = "examples/advance.avalla";
		generateTest(asmspec,avaTest);
	}
	
	
	//Test su forno
	@Test
	public void testAsmToJunit_forno() throws Exception {
		String asmspec = "examples/forno.asm";
		String avaTest = "examples/scenario1_forno.avalla";
		generateTest(asmspec,avaTest);
	}
	


	private void generateTest(String asmspec,String avaTest) throws Exception, IOException {
		
		GeneratorCompilerTest gen = new GeneratorCompilerTest();
		TranslatorOptions options = new TranslatorOptions(true, true, true);
		gen.test(asmspec, options);
		

		assert new File(avaTest).exists();
		// convertiamo .avalla in oggetto Scenario
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(avaTest), true);
		resource.load(rs.getLoadOptions());
		Scenario sc = (Scenario) resource.getContents().get(0);
		assertNotNull(resource);
		assertNotNull(sc);
		
		// a questo punto lo converto in caso di test
		// Al costruttore passo sia lo scenario che la specifica
		
		
		if(sc.getSpec().trim().contains("./..")) {
			String temp1 = sc.getSpec().replace("./..","");
			String temp2 = temp1.substring(1);
			spec = temp2.replace(".asm","");
		}
		else {
			spec = sc.getSpec().replace(".asm","");
		}
		
		
		AvallaToString converter = new AvallaToString(sc,spec);
		String fileTestForm = converter.parseCommands(sc, 1);
		
		
		
		FileWriter fileTest = new FileWriter(pathTF + spec + "_Test" + "_" + 1 + JUnit_EXT);
		fileTest.write(Formatter.formatCode(fileTestForm));
		fileTest.close();
		
	}

	//********************************************************************************************************************
	
	//Questo test permette di eseguire in modo automatico tutti i casi di test
	//sotto la folder "src-gen/"
	@Test
	public void testRun_all_test() throws Exception {
		File dir = new File("src-gen/");
		File[] files = dir.listFiles();
		for(File f : files) {
			if(f.toString().contains("_Test_") && !f.toString().contains(".class")) {
				String temp = f.toString().replace("src-gen", "").substring(1).replace(".java", "");
				JUnitCore junitCore = new JUnitCore();
				junitCore.addListener(new RunListener());
				Result result = junitCore.run(getClassFromFile(temp));
				System.out.println("Test name: " + temp);
				System.out.println("Test run: " + result.getRunCount() + " Test fail: " + 
				result.getFailureCount() + "\n");
			}
		}
	}
	
	//********************************************************************************************************************

	private static final String CLASS_FOLDER ="src-gen/";

	private static Class getClassFromFile(String fullClassName) throws Exception {
	    URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file://" + CLASS_FOLDER) });
	    return loader.loadClass(fullClassName);
	}
}
	
	
	


