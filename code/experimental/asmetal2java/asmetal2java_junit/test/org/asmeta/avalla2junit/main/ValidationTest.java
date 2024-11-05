package org.asmeta.avalla2junit.main;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.formatter.FormatterImpl;
import org.asmeta.asm2java.generator.GeneratorCompilerTest;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.parser.ASMParser;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import com.google.inject.Injector;
import asmeta.AsmCollection;
import asmetal2java_junit.AvallaToString;
import atgt.coverage.AsmTestSuite;



public class ValidationTest {
	

	//pathTF - path test Folder
	public static final String pathTF = "src-gen/";
	//path examples
	public static final String pathExamples = "examples/";
	//estensione file JUnit
	public static final String JUnit_EXT = ".java";
	//estensione file Avalla
	public static final String Avalla_EXT = ".avalla";
	//Folder output
	private static final String CLASS_FOLDER ="src-gen/";
	
	
	//Validation Test ascensore.asm
	@Test
	public void testAsmToJunit_asc() throws Exception {
		//Path specifica
		String asmspec = "examples/ascensore.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}
	
	//Validate Test Rubrica.asm
	//PROBLEMA CON OUTMEX
//	@Test
//	public void testAsmToJunit_r() throws Exception {
//		String asmspec = "examples/Rubrica.asm";
//		//Scelta step contenuti in uno scenario  
//		int counter_step = 4;
//		//Numero di scenari da generare
//		int num_file = 1;
//		validateTest(asmspec,counter_step,num_file);
//	}
		
	//Validate Test SIS.asm 
	@Test
	public void testAsmToJunit_sis() throws Exception {
		String asmspec = "examples/SIS.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}
	
	
	//Validate Test LGS_GM.asm
	@Test
	public void testAsmToJunit() throws Exception {
		String asmspec = "examples/LGS_GM.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}

	//Validate Test Contatore_U_DA_H.asm
	@Test
	public void testAsmToJunit_contU() throws Exception {
		String asmspec = "examples/Contatore_U_DA_H.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}

	//Validate Test euclideMCD.asm
	@Test
	public void testAsmToJunit_euclide() throws Exception {
		String asmspec = "examples/euclideMCD.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}
	
	//Validate Test AdvancedClock.asm
	@Test
	public void testAsmToJunit_advanceClock() throws Exception {
		String asmspec = "examples/AdvancedClock.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}
	
	
	//Validate Test forno.asm
	@Test
	public void testAsmToJunit_forno() throws Exception {
		String asmspec = "examples/forno.asm";
		//Scelta step contenuti in uno scenario  
		int counter_step = 4;
		//Numero di scenari da generare
		int num_file = 2;
		validateTest(asmspec,counter_step,num_file);
	}
			
	
	public void validateTest(String asmSpec,int counterStep, int numFile) throws Exception {
		
		// a. legge la spec di asmeta
		// 2. la traduce con codice casati
		
		
		GeneratorCompilerTest gen = new GeneratorCompilerTest();
		TranslatorOptions options = new TranslatorOptions(true, true, true);
		gen.test(asmSpec, options);
	
		
		File asmFile = new File(asmSpec); 
		assert asmFile.exists();
		
		// 3. genera l'avalla usando AsmTestGeneratorBySimulation
		// opzionale lo salva ma se non serv enon salvarlo
		
		AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		AsmTestGeneratorBySimulation asmTestG = new AsmTestGeneratorBySimulation(model,counterStep,numFile);
		AsmTestSuite testSuite = asmTestG.getTestSuite();
		
		
		//Path in cui salvare lo/gli scenari
		String path_avalla = pathExamples + model.getMain().getName() + "_"  + Avalla_EXT;
		File fileScen = new File(path_avalla);
		String result = SaveResults.getAvallaResults(testSuite, fileScen.getAbsolutePath(), model.getMain().getName()).toString();
		
		for(int i = 0; i <= numFile - 1; i++) {
			//Path scenario
			String path_resScen = pathExamples + model.getMain().getName() + "_" + i + Avalla_EXT;
			createScenObjectAndValidate(model, path_resScen, i);
		}

		// 4. traduce gli scenari in casi di test junit

		// 5. esegue i casi di test (compila il tutto e esegue)
	}
	
	
	public void createScenObjectAndValidate(AsmCollection model,String pathAv, int numFil) throws Exception {
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(pathAv), true);
		resource.load(rs.getLoadOptions());
		Scenario sc = (Scenario) resource.getContents().get(0);
		assertNotNull(resource);
		assertNotNull(sc);
		AvallaToString converter = new AvallaToString(sc,model.getMain().getName());
		String fileTestForm = converter.parseCommands(sc, numFil);
		String pathJUnit_res = pathTF + model.getMain().getName() + "_Test" + "_" + numFil + JUnit_EXT;
		FileWriter fileTest = new FileWriter(pathJUnit_res);
		fileTest.write(FormatterImpl.formatCode(fileTestForm));
		fileTest.close();
		String junitTest = pathJUnit_res;
		String temp = junitTest.replace("src-gen", "").substring(1).replace(".java", "");
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new RunListener());
		Result result2 = junitCore.run(getClassFromFile(temp));
		System.out.println("Test name: " + temp);
		System.out.println("Test run: " + result2.getRunCount() + " Test fail: " + result2.getFailureCount() + "\n");
		
	}

	private static Class getClassFromFile(String fullClassName) throws Exception {
	    URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file://" + CLASS_FOLDER) });
	    return loader.loadClass(fullClassName);
	}

}
