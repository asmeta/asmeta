package org.asmeta.avalla2junit.main;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import asmetal2java_junit.*;
import org.asmeta.asm2code.main.GeneratorCompilerTest;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.google.inject.Injector;
import asmetal2java_junit.AvallaToString;
import asmetal2java_junit.Formatter;
import static asmetal2java_junit.Formatter.formatCode;

public class Asmetal2JUnit_Generator {
	
	//NEW
	//pathTF - path test Folder
	public static final String pathTF = "src-gen/";
	//estensione file JUnit
	public static final String JUnit_EXT = ".java";
	public String spec = "";
	
	//Test su ascensore
	@Test
	public void testAsmToJunit_asc() throws Exception {
		String asmspec = "examples/ascensore.asm";
		String avaTest = "examples/scenario2.avalla";
		extracted(asmspec,avaTest);
	}
	
	//Test su rubrica
	@Test
	public void testAsmToJunit_r() throws Exception {
		String asmspec = "examples/Rubrica.asm";
		String avaTest = "examples/rubrica.avalla";
		extracted(asmspec,avaTest);
	}
	
	//Test su sis
	@Test
	public void testAsmToJunit_sis() throws Exception {
		String asmspec = "examples/SIS.asm";
		String avaTest = "examples/sis.avalla";
		extracted(asmspec,avaTest);
	}
	
	//Test su LGS_GM
	@Test
	public void testAsmToJunit() throws Exception {
		String asmspec = "examples/LGS_GM.asm";
		String avaTest = "examples/testBR.avalla";
		extracted(asmspec,avaTest);
	}

	//Test su Contatore_U_DA_H
	@Test
	public void testAsmToJunit_contU() throws Exception {
		String asmspec = "examples/Contatore_U_DA_H.asm";
		String avaTest = "examples/contatore.avalla";
		extracted(asmspec,avaTest);
	}
	
	//Test su tombola
	@Test
	public void testAsmToJunit_tomb() throws Exception {
		String asmspec = "examples/tombola.asm";
		String avaTest = "examples/test1.avalla";
		extracted(asmspec,avaTest);
	}
	
	//Test su coffeeVendingMachine
	@Test
	public void testAsmToJunit_coffeeV() throws Exception {
		String asmspec = "examples/coffeeVendingMachine.asm";
		String avaTest = "examples/cm.avalla";
		extracted(asmspec,avaTest);
	}
	//Test su euclideMCD
	@Test
	public void testAsmToJunit_euclide() throws Exception {
		String asmspec = "examples/euclideMCD.asm";
		String avaTest = "examples/euclide.avalla";
		extracted(asmspec,avaTest);
	}
	


	

	private void extracted(String asmspec,String avaTest) throws Exception, IOException {
		GeneratorCompilerTest gen = new GeneratorCompilerTest();
		TranslatorOptions options = new TranslatorOptions(true, true, true);
		gen.test(asmspec, options);
		
		//String avallaSpec = "examples/rubrica.avalla";
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
		fileTest.write(formatCode(fileTestForm));
		fileTest.close();

	}	 
}
	
	
	
//	@Test
//	public void testLoop() throws Exception {
//		GeneratorCompilerTest gen = new GeneratorCompilerTest();
//		gen.testBasicDomain();
//
//		// Idea parsere tutti i file in una folder
//		// Da asmeta -> avalla
//		// Automaticamente converte tutti i file
//		File dir = new File("examples/");
//		File[] files = dir.listFiles();
//		String pathInp = "examples/";
//		// Loop sulla folder result
//		int i = 1;
//		for (File file : files) {
//			if (file.isFile()) {
//				// Controllo estensione
//				String file2 = file.getName();
//				String ext = file2.substring(file2.lastIndexOf(".") + 1, file2.length());
//				if (ext.equals("avalla")) {
//					assert file.exists();
//					String pathResource = pathInp + file2;
//
//					// convertiamo .avalla in oggetto Scenario
//					Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
//					XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
//					rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
//					Resource resource = rs.getResource(URI.createFileURI(pathResource), true);
//					resource.load(rs.getLoadOptions());
//					Scenario sc = (Scenario) resource.getContents().get(0);
//					assertNotNull(resource);
//					assertNotNull(sc);
//					// a questo punto lo converto in caso di test
//					// Al costruttore passo sia lo scenario che la specifica
//					AvallaToString converter = new AvallaToString(sc, sc.getSpec());
//					converter.parseCommands(sc, i);
//					i += 1;
//				}
//			}
//		}
//		
//	}
