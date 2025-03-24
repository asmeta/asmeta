package org.asmeta.avallaxt.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmetaPrintInfo;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.asmeta.xt.validator.RuleEvalWCov;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestSingleFile extends TestValidator {

	@BeforeClass
	static public void setuplogger() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);		
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.ALL);		
	}

	@Test
	public void testASMWithTime() throws Exception {
		// translation
		test("scenariosfortest/usingtime/scenario.avalla", false, false, true);
		// the TimeLibrabry is not translated
		String[] fileNames = tempAsmPath.list();
		assertFalse(Arrays.toString(fileNames),Arrays.asList(fileNames).stream().anyMatch(fn ->fn.contains("TimeLibrary")));
		// execution
		Environment.timeMngt = TimeMngt.auto_increment;
		test("scenariosfortest/usingtime/scenario.avalla", true, false, true);		
	}

	@Test
	public void testASMImportewithmain() throws Exception {		
		// translation
		test("scenariosfortest/asmwithmain/test.avalla", false, false, true);
		// execution of the validation
		test("scenariosfortest/asmwithmain/test.avalla", true, false, true);
	}

	
	@Test
	public void testLift() throws Exception {		
		test("scenariosfortest/lift.avalla", false, false, true);
	}

	@Test
	public void testMonitored() throws Exception {		
		test("scenariosfortest/mon.avalla");
	}

	@Test
	public void testLiftMonitored() throws Exception {		
		test("scenariosfortest/lift_extramon.avalla", false, false, true);
	}
	
	@Test
	public void testEmptyScenario() throws Exception {	
		test("scenariosfortest/emptyScenario.avalla", false, false, false);
	}
	
	@Test
	public void testNoStepNoCheckScenario() throws Exception {	
		test("scenariosfortest/noStepNoCheckScenario.avalla", false, false, false);
	}
	
	@Test
	public void testError() throws Exception {	
		// these two should be fixed - as the are are not 
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFonAmbientLight.avalla", true, false);
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFPowerOFFKey.avalla", true, false);
		test("scenariosfortest/u_dir/test.avalla", true, false, true);
	}
	
	@Test
	public void testBuilerWithSpaces() throws Exception {		
		// the path has a space in it
		Path model = Paths.get("scenariosfortest/sub dir/Lift.asm");
		assert Files.exists(model);
		// transform to absolute
		String modelAbsPath = model.toAbsolutePath().toString();
		assert modelAbsPath.contains(" ");
		assert Files.exists(Paths.get(modelAbsPath));
		System.out.println(modelAbsPath);
		modelAbsPath = modelAbsPath.replace("\\", "\\\\\\\\");
		// put in the lift2.avalla
		// tale the template
		Path templateAvalla = Paths.get("scenariosfortest/lift2_avalla.template");
		assert Files.exists(templateAvalla);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(templateAvalla), charset);
		content = content.replaceAll("LIFTASM_AS_ABSPATH", "\"" +modelAbsPath + "\"");
		System.out.println(content);
		Path avalla = Paths.get("scenariosfortest/lift2.avalla");
		if (!Files.exists(avalla)) Files.createFile(avalla);
		assert Files.exists(avalla);
		Files.write(avalla, content.getBytes(charset));
		test(avalla.toString(), false, false, true);
	}

	@Test
	public void testBuilerAbs() throws Exception {
		
		test("scenariosfortest/lift3.avalla", false, false, true);
	}

	@Test
	public void testBuilerSameDir() throws Exception {
		
		test("scenariosfortest/lift4.avalla", false, false, true);
	}

	
	@Test
	public void testBuilerblock() throws Exception {
		
		test("scenariosfortest/block.avalla", false, false, true);
	}

	@Test
	public void testBuilerExecblock() throws Exception {
		
		test("scenariosfortest/block2.avalla", false, false, true);
	}

	@Test
	public void testBuilerExternalExecblock() throws Exception {
		
		test("scenariosfortest/block3.avalla", false, false, true);
	}


	
	@Test
	public void testCipher() throws Exception {		
		test("scenariosfortest/fh/extend.avalla", false, false, true);
	}
	
	@Test
	public void testInvariant() throws Exception {		
		test("scenariosfortest/invariants/scenario_inv.avalla");
	}

	@Test
	public void testCarInclude() throws Exception {		
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/AdaptativeHighBeamDecreasing.avalla", true, true, true);
	}
	
	@Test
	public void testSLE() throws Exception {		
		test(ASM_EXAMPLES_EXAMPLES +"fsmsemantics/Sle/testEven1.avalla", false, false, true);
	}
	
	@Test
	public void testATM1() throws Exception {
		test("scenariosforexamples/atm/atm1.avalla", false, false, true);
	}

	@Test
	public void testModule1() throws Exception {		
		test("scenariosfortest/lift.avalla", false, false, true);
	}


	@Test
	public void testABZ2020CruiseCtrl() throws Exception {		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		//test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\scenarios\\CarSystem006scenario003.avalla",true, false);
	}

	@Test
	public void testABZ2020_module1() throws Exception {
		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test(ASM_EXAMPLES_EXAMPLES +  "ABZ2020/CarSystemModule/CarSystem001/scenari/HWExecutedRunning.avalla");
	}

	
	@Test
	public void testMon1() throws Exception {
		//TODO a way to check that the scenario succeeds
		// monitored set in the avalla AND in the initial state
		test("scenariosfortest/mon/scenario.avalla"); // ->check succeeded: a = 1
	}

	@Test
	public void testMon2() throws Exception {
		// monitored set only in the avalla - no intial state
		test("scenariosfortest/mon/scenario2.avalla"); //->check succeeded: a = 1 
	}

	
	// with the use of import/modules
	@Test
	public void testMod1() throws Exception {
		test("scenariosfortest/withmodules/scenario1.avalla");		
	}
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testMod2() throws Exception {
		test("scenariosfortest/withmodules/scenarios/scenario2.avalla");		
	}
	// import nested
	@Test
	public void testMod3() throws Exception {
		test("scenariosfortest/withmodules/scenario2i.avalla");		
	}

	@Test
	public void testMod3bis() throws Exception {
		test("scenariosfortest/withmodules/scenario2im.avalla");		
	}
	
	// import from a subdir
	@Test
	public void testMod4() throws Exception {
		test("scenariosfortest/withmodules/scenario3.avalla", false, false, true);		
	}

	// diamond
	@Test
	public void testDiamond() throws Exception {
		test("scenariosfortest/diamondimport/scenario1.avalla", false, false, true);
		// check the only 1 file for the common root is translated
	}


	// flaky tests
	@Test
	public void testFlaky() throws Exception {
		test("scenariosfortest/flaky/scenario_noflaky.avalla", true, false, true);
	}
	
	@Test
	public void testPickChooseMultipleVars() throws Exception {
		test("scenariosfortest/flaky/scenarioChooseMultipleVars.avalla", true, false, true);
	}
	
	@Test
	public void testPickNestedChoose() throws Exception {
		test("scenariosfortest/flaky/scenarioNestedChoose.avalla", true, false, true);
	}
	
	@Test
	public void testPickOverloading() throws Exception {
		test("scenariosfortest/flaky/scenarioOverloading.avalla", true, false, true);
	}
	
	@Test
	public void testPickWithStepUntil() throws Exception {
		test("scenariosfortest/flaky/scenarioPickWithStepUntil.avalla", true, false, true);
	}
	
	@Test
	public void testCoffeVendingMachineFlaky() throws Exception {
		test("scenariosfortest/flaky/scenario1.avalla", true, false, true);
		test("scenariosfortest/flaky/scenario2.avalla", true, false, true);
		test("scenariosfortest/flaky/scenario3.avalla", true, false, true);
		test("scenariosfortest/flaky/scenario4.avalla", true, false, true);
	}

	@Test
	public void testCoffeVendingMachineChooseOne() throws Exception {
		test("scenariosfortest/flaky/scenario_pickchooseone.avalla", true, false, true);
		// to unpick the value.
		test("scenariosfortest/flaky/scenario_pickchooseone2.avalla", true, false, true);
	}
	
	@Test
	public void testMultipleChoose() throws Exception {
		test("scenariosfortest/flaky/scenario_multiplechoose.avalla", true, false, true);
	}
	
	@Test
	public void testChooseInteger() throws Exception {
		test("scenariosfortest/flaky/scenario_ci_fail.avalla", true, false, false);
	}

	@Test
	public void testPillbox() throws Exception {
		test(ASM_EXAMPLES + "PillBox/Level0/pillbox_0_scenario1.avalla", false, false, true);		
	}

	@Test
	public void testlampada() throws Exception {
		ASMParser.getResultLogger().setLevel(Level.OFF);
		// questo mi d� errori strani
		try {
//			test("D:/AgHome\\Dropbox\\code\\didattica\\tvsw\\unibg_tvsw\\codice_lezioni\\6_atgt\\test.avalla", false, false);		
			test("scenariosfortest/u_dir/test.avalla", false, false, true);
		} catch(IllegalStateException ise) {
			System.out.println(ise.getMessage());
			fail();
		}
	}
	@Test
	@Ignore // works onlyon Angelo's PC
	public void testCarSystem() throws Exception {
		String baseDIR = "D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\asmeta_papers_github\\ABZ2020_casestudy\\Casestudy\\ASM model";
		String scenarios =  baseDIR +"\\Car System\\scenarios";
		//String asmetaFile = "\ASM model\Car System";
		String models = baseDIR + "\\Car System module";
		// stampo per ogni asmeta
		List<Path> result;
        try (Stream<Path> walk = Files.walk(Path.of(models))) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
		for(Path asm: result){
			String filename = asm.toString();
			if (!filename.endsWith(ASMParser.ASM_EXTENSION)) continue;
			System.out.println(filename);
			AsmetaPrintInfo info = new AsmetaPrintInfo(filename);
			System.out.println(info.getInfo().ruleNamesList);
		}
		// stampo per ogni scenario
		result.clear();
		try (Stream<Path> walk = Files.walk(Path.of(scenarios))) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
		for(Path asm: result){
			String filename = asm.toString();
			if (!filename.endsWith(".avalla")) continue;
			System.out.println(filename);
			try{
				test(filename, true, true, true);		
			} catch(Throwable e) {
				System.out.println("skipping " + filename);
			}
		}
	}	
	
}
