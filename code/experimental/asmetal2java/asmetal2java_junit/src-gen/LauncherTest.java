import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.asmeta.asm2java.main.JavaGenerator;
import org.asmeta.asm2java.main.TranslatorOptions;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.avallaxt.avalla.impl.ScenarioImpl;
import org.asmeta.avallaxt.services.AvallaGrammarAccess.ScenarioElements;
import org.asmeta.avallaxt.validation.ScenarioUtility;
import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.apache.*;
import com.google.inject.Injector;

import asmeta.AsmCollection;
import asmetal2java_junit.AvallaToString;


public class LauncherTest {
	
//	
//	@Test
//	public void avallaToJUnit() throws IOException {
//		String avallaSpec = "examples/scenario2.avalla";
//		assert new File(avallaSpec).exists();
//
//		// convertiamo .avalla in oggetto Scenario
//		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
//		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
//		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
//		Resource resource = rs.getResource(URI.createFileURI(avallaSpec), true);
//		resource.load(rs.getLoadOptions());
//		Scenario sc = (Scenario) resource.getContents().get(0);
//		assertNotNull(resource);
//		assertNotNull(sc);
//		// a questo punto lo converto in caso di test
//		// Al costruttore passo sia lo scenario che la specifica
//		AvallaToString converter = new AvallaToString(sc, sc.getSpec());
//		converter.parseCommands(sc, 1);
//	}
	
	@Test
	public void test_ese() {
		
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		junit.run(Rubrica_Test_1.class);
	}

}
