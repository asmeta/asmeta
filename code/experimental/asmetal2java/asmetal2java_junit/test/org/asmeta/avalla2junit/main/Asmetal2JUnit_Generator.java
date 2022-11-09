package org.asmeta.avalla2junit.main;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.asmeta.asm2code.main.GeneratorCompilerTest;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import com.google.inject.Injector;
import asmetal2java_junit.AvallaToString;

public class Asmetal2JUnit_Generator {
	
	@Test
	public void testAsmToJunit() throws Exception {
		String asmspec = "examples/tombola.asm";
		extracted(asmspec);
	}
	@Test
	public void testAsmToJunit_r() throws Exception {
		String asmspec = "examples/Rubrica.asm";
		extracted(asmspec);
	}

	private void extracted(String asmspec) throws Exception, IOException {
		GeneratorCompilerTest gen = new GeneratorCompilerTest();
		TranslatorOptions options = new TranslatorOptions(true, true, true);
		gen.test(asmspec, options);
		
		String avallaSpec = "examples/rubrica.avalla";
		assert new File(avallaSpec).exists();

		// convertiamo .avalla in oggetto Scenario
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(avallaSpec), true);
		resource.load(rs.getLoadOptions());
		Scenario sc = (Scenario) resource.getContents().get(0);
		assertNotNull(resource);
		assertNotNull(sc);
		// a questo punto lo converto in caso di test
		// Al costruttore passo sia lo scenario che la specifica
		AvallaToString converter = new AvallaToString(sc, sc.getSpec());
		converter.parseCommands(sc, 1);
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

