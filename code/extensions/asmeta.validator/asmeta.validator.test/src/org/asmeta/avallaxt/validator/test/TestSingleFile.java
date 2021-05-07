package org.asmeta.avallaxt.validator.test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ParserResultLogger;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.asmeta.xt.validator.SimulatorWCov;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSingleFile extends TestValidator {

	@Test
	public void testBuiler() throws Exception {
		
		test("scenariosfortest/lift.avalla", false, false);
	}

	@Test
	public void testBuilerWithSpaces() throws Exception {		
		//
		Path model = Paths.get("scenariosfortest\\sub dir\\Lift.asm");
		assert Files.exists(model);
		// transform to absolute
		String modelAbsPath = model.toAbsolutePath().toString();
		assert modelAbsPath.contains(" ");
		assert Files.exists(Paths.get(modelAbsPath));
		System.out.println(modelAbsPath);
		modelAbsPath = modelAbsPath.replace("\\", "\\\\\\\\");
		// put in the lift2.avalla
		Path templateAvalla = Paths.get("scenariosfortest\\lift2_avalla.template");
		assert Files.exists(templateAvalla);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(templateAvalla), charset);
		content = content.replaceAll("LIFTASM_AS_ABSPATH", "\"" +modelAbsPath + "\"");
		System.out.println(content);
		Path avalla = Paths.get("scenariosfortest\\lift2.avalla");
		assert Files.exists(avalla);
		Files.write(avalla, content.getBytes(charset));
		test(avalla.toString(), false, false);
	}

	@Test
	public void testBuilerAbs() throws Exception {
		
		test("scenariosfortest/lift3.avalla", false, false);
	}

	@Test
	public void testBuilerSameDir() throws Exception {
		
		test("scenariosfortest/lift4.avalla", false, false);
	}

	
	@Test
	public void testBuilerblock() throws Exception {
		
		test("scenariosfortest/block.avalla", false, false);
	}

	@Test
	public void testBuilerExecblock() throws Exception {
		
		test("scenariosfortest/block2.avalla", false, false);
	}

	@Test
	public void testBuilerExternalExecblock() throws Exception {
		
		test("scenariosfortest/block3.avalla", false, false);
	}


	
	@Test
	public void testCipher() throws Exception {
		
		test("example/fh/extend.avalla", false, false);
	}
	
	@Test
	public void testInvariant() throws Exception {
		
		test("scenariosfortest\\invariants\\scenario_inv.avalla", true, false);
	}

	
	
	@Test
	public void testSLE() throws Exception {
		
		test("..\\..\\..\\..\\asm_examples\\examples\\fsmsemantics\\Sle\\testEven1.avalla", false, false);
	}
	@Test
	public void testATM1() throws Exception {
		
		test("scenariosforexamples\\atm\\atm1.avalla", false, false);
	}

	@Test
	public void testModule1() throws Exception {
		
		test("scenariosfortest\\lift.avalla", false, false);
	}

	@Test
	public void testModule2() throws Exception {
		
		test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\module\\myscenario.avalla", false, false);
	}

	@Test
	public void testPillBox() throws Exception {		
		test("D:\\AgHome\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\onlyred_level2\\pillbox_2_scenario1.avalla", false, false);
	}

	@Test
	public void testABZ2020CruiseCtrl() throws Exception {
		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\scenarios\\CarSystem006scenario003.avalla",true, false);
	}

	@Test
	public void testABZ2020_module1() throws Exception {
		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test("..\\..\\..\\..\\asm_examples\\examples\\ABZ2020\\CarSystemModule\\CarSystem001\\scenari\\HWExecutedRunning.avalla",false, false);
	}

	
	@Test
	public void testMon() throws Exception {
		
		test("scenariosfortest\\mon\\scenario.avalla", false, false);
	}
// with the use of import/modules
	@Test
	public void testMod1() throws Exception {
		
		test("scenariosfortest\\withmodules\\scenario1.avalla", false, false);		
	}
	
	@Test
	public void testMod2() throws Exception {
		test("scenariosfortest\\withmodules\\scenarios\\scenario2.avalla", false, false);		
	}
	// import nested
	@Test
	public void testMod3() throws Exception {
		test("scenariosfortest\\withmodules\\scenario2i.avalla", true, false);		
	}
	
	// import from a subdir
	@Test
	public void testMod4() throws Exception {
		test("scenariosfortest\\withmodules\\scenario3.avalla", false, false);		
	}
	
	
	@Test
	public void testPillbox() throws Exception {
		test("D:\\AgHome\\progettidaSVNGIT\\asmeta\\asmeta\\asm_examples\\PillBox\\Level0\\pillbox_0_scenario1.avalla", false, false);		
	}

	@BeforeClass
	static public void setuplogger() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);		
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
	}
	
	
}
