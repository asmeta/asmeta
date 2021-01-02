package org.asmeta.avallaxt.validator.test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.SimulatorWCov;
import org.junit.Test;

public class TestSingleFile extends TestValidator {

	@Test
	public void testBuiler() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/lift.avalla", false);
	}

	@Test
	public void testBuilerWithSpaces() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		//
		Path model = Paths.get("scenariosfortest\\sub dir\\Lift.asm");
		assert Files.exists(model);
		// transform to absolute
		String modelAbsPath = model.toAbsolutePath().toString();
		assert modelAbsPath.contains(" ");
		assert Files.exists(Path.of(modelAbsPath));
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
		test(avalla.toString(), false);
	}

	@Test
	public void testBuilerAbs() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/lift3.avalla", false);
	}

	@Test
	public void testBuilerSameDir() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/lift4.avalla", false);
	}

	
	@Test
	public void testBuilerblock() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/block.avalla", false);
	}

	@Test
	public void testBuilerExecblock() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/block2.avalla", false);
	}

	@Test
	public void testBuilerExternalExecblock() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest/block3.avalla", false);
	}


	
	@Test
	public void testCipher() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("example/fh/extend.avalla", false);
	}
	@Test
	public void testSLE() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("..\\..\\..\\..\\asm_examples\\examples\\fsmsemantics\\Sle\\testEven1.avalla", false);
	}
	@Test
	public void testATM1() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosforexamples\\atm\\atm1.avalla", false);
	}

	@Test
	public void testModule1() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\lift.avalla", false);
	}

	@Test
	public void testModule2() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\module\\myscenario.avalla", false);
	}

	@Test
	public void testPillBox() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("D:\\AgHome\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\onlyred_level2\\pillbox_2_scenario1.avalla", false);
	}

	@Test
	public void testABZ2020CruiseCtrl() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\scenarios\\CarSystem006scenario003.avalla",true);
	}

	@Test
	public void testABZ2020_module1() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test("..\\..\\..\\..\\asm_examples\\examples\\ABZ2020\\CarSystemModule\\CarSystem001\\scenari\\HWExecutedRunning.avalla",false);
	}

	
	@Test
	public void testMon() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\mon\\scenario.avalla", false);
	}
// with the use of import/modules
	@Test
	public void testMod1() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\withmodules\\scenario1.avalla", false);		
	}
	
	@Test
	public void testMod2() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\withmodules\\scenarios\\scenario2.avalla", false);		
	}
	// import nested
	@Test
	public void testMod3() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\withmodules\\scenario2i.avalla", false);		
	}
	
	// import from a subdir
	@Test
	public void testMod4() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		test("scenariosfortest\\withmodules\\scenario3.avalla", false);		
	}
	
}
