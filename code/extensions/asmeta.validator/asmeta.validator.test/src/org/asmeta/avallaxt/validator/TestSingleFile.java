package org.asmeta.avallaxt.validator;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.fail;

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
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.parser.util.AsmetaPrintInfo;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.asmeta.xt.validator.RuleEvalWCov;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TestSingleFile extends TestValidator {

	@BeforeAll
	static void setuplogger() throws Exception {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);		
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.ALL);		
	}

	@Test void asmWithTime() throws Exception {
		// translation
		test("scenariosfortest/usingtime/scenario.avalla", false, false, true);
		// the TimeLibrabry is not translated
		String[] fileNames = tempAsmPath.list();
		assertFalse(Arrays.asList(fileNames).stream().anyMatch(fn ->fn.contains("TimeLibrary")),Arrays.toString(fileNames));
		// execution
		Environment.timeMngt = TimeMngt.auto_increment;
		test("scenariosfortest/usingtime/scenario.avalla", true, false, true);		
	}

	@Test void asmImportewithmain() throws Exception {		
		// translation
		test("scenariosfortest/asmwithmain/test.avalla", false, false, true);
		// execution of the validation
		test("scenariosfortest/asmwithmain/test.avalla", true, false, true);
	}


	@Test void lift() throws Exception {		
		test("scenariosfortest/lift.avalla", false, false, true);
	}

	@Test void monitored() throws Exception {		
		test("scenariosfortest/mon.avalla");
	}

	@Test void liftMonitored() throws Exception {		
		test("scenariosfortest/lift_extramon.avalla", false, false, true);
	}

	@Test void emptyScenario() throws Exception {	
		test("scenariosfortest/emptyScenario.avalla", false, false, false);
	}

	@Test void noStepNoCheckScenario() throws Exception {	
		test("scenariosfortest/noStepNoCheckScenario.avalla", false, false, false);
	}

	@Test void error() throws Exception {	
		// these two should be fixed - as the are are not 
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFonAmbientLight.avalla", true, false);
		//test("../../../../asm_examples/examples/ABZ2020/CarSystemModule/CarSystem002/scenari/LowBeamOFFPowerOFFKey.avalla", true, false);
		test("scenariosfortest/u_dir/test.avalla", true, false, true);
	}

	@Test void builerWithSpaces() throws Exception {		
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

	@Test void builerAbs() throws Exception {
		
		test("scenariosfortest/lift3.avalla", false, false, true);
	}

	@Test void builerSameDir() throws Exception {
		
		test("scenariosfortest/lift4.avalla", false, false, true);
	}


	@Test void builerblock() throws Exception {
		
		test("scenariosfortest/block.avalla", false, false, true);
	}

	@Test void builerExecblock() throws Exception {
		
		test("scenariosfortest/block2.avalla", false, false, true);
	}

	@Test void builerExternalExecblock() throws Exception {
		
		test("scenariosfortest/block3.avalla", false, false, true);
	}


	@Test void cipher() throws Exception {		
		test("scenariosfortest/fh/extend.avalla", false, false, true);
	}

	@Test void invariant() throws Exception {
		InvariantTreament temp = Simulator.checkInvariants;
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		// failure because the invariant is violated
		test("scenariosfortest/invariants/scenario_inv.avalla", true, false, false);
		// failure because the invariant is violated
		test("scenariosfortest/invariants/scenario_inv2.avalla", true, false, false);
		Simulator.checkInvariants = temp;
	}

	@Test void carInclude() throws Exception {		
		test(ASM_EXAMPLES_EXAMPLES + "ABZ2020/CarSystemModule/CarSystem003/scenari/AdaptativeHighBeamDecreasing.avalla", true, true, true);
	}

	@Test void sle() throws Exception {		
		test(ASM_EXAMPLES_EXAMPLES +"fsmsemantics/Sle/testEven1.avalla", false, false, true);
	}

	@Test void atm1() throws Exception {
		test("scenariosforexamples/atm/atm1.avalla", false, false, true);
	}

	@Test void module1() throws Exception {		
		test("scenariosfortest/lift.avalla", false, false, true);
	}


	@Test void abz2020CruiseCtrl() throws Exception {		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		//test("D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\scenarios\\CarSystem006scenario003.avalla",true, false);
	}

	@Test void abz2020Module1() throws Exception {
		
		//Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);
		//test("C:\\Users\\garganti\\Dropbox\\Documenti\\ricerca\\asm\\ABZ2020_casestudy\\Casestudy\\ASM model\\scenarios\\CarSystem004scenario001.avalla", true);
		test(ASM_EXAMPLES_EXAMPLES +  "ABZ2020/CarSystemModule/CarSystem001/scenari/HWExecutedRunning.avalla");
	}


	@Test void mon1() throws Exception {
		//TODO a way to check that the scenario succeeds
		// monitored set in the avalla AND in the initial state
		test("scenariosfortest/mon/scenario.avalla"); // ->check succeeded: a = 1
	}

	@Test void mon2() throws Exception {
		// monitored set only in the avalla - no intial state
		test("scenariosfortest/mon/scenario2.avalla"); //->check succeeded: a = 1 
	}


	// with the use of import/modules
	@Test void mod1() throws Exception {
		test("scenariosfortest/withmodules/scenario1.avalla");		
	}

	@Test
	@Tag("TestToMavenSkip") void mod2() throws Exception {
		test("scenariosfortest/withmodules/scenarios/scenario2.avalla");		
	}

	// import nested
	@Test void mod3() throws Exception {
		test("scenariosfortest/withmodules/scenario2i.avalla");		
	}

	@Test void mod3bis() throws Exception {
		test("scenariosfortest/withmodules/scenario2im.avalla");		
	}

	// import from a subdir
	@Test void mod4() throws Exception {
		test("scenariosfortest/withmodules/scenario3.avalla", false, false, true);		
	}

	// diamond
	@Test void diamond() throws Exception {
		test("scenariosfortest/diamondimport/scenario1.avalla", false, false, true);
		// check the only 1 file for the common root is translated
	}


	/* -- pick tests -- */
	@Test void pick() throws Exception {
		test("scenariosfortest/flaky/coffee_vending_machine/scenario_noflaky.avalla", true, false, true);
		test("scenariosfortest/flaky/coffee_vending_machine/scenario_noflaky2.avalla", true, false, true);
	}

	@Test void pickErrors() throws Exception {
		try {
			test("scenariosfortest/flaky/coffee_vending_machine/scenario_noflaky_VAL_ERR.avalla", true, false, false); // Macro rule of picked variable not defined
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			// expected
		}
		try {
			test("scenariosfortest/flaky/coffee_vending_machine/scenario_noflaky_VAL_ERR2.avalla", true, false, false); // Picked variable not defined
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			// expected
		}
	}

	@Test void pickChooseMultipleVars() throws Exception {
		test("scenariosfortest/flaky/scenarioChooseMultipleVars.avalla", true, false, true);
	}

	@Test void pickWithImports() throws Exception {
		test("scenariosfortest/flaky/asm_w_import/scenarioPickWithImport.avalla", true, false, true);
	}

	@Test void pickNestedChoose() throws Exception {
		test("scenariosfortest/flaky/scenarioNestedChoose.avalla", true, false, true);
	}

	@Test void pickOverloading() throws Exception {
		test("scenariosfortest/flaky/scenarioOverloading.avalla", true, false, true);
	}

	@Test void pickWithStepUntil() throws Exception {
		test("scenariosfortest/flaky/scenarioPickWithStepUntil.avalla", true, false, true);
	}

	@Test void coffeVendingMachineFlaky() throws Exception {
		test("scenariosfortest/flaky/coffee_vending_machine/scenario1.avalla", true, false, true);
		test("scenariosfortest/flaky/coffee_vending_machine/scenario2.avalla", true, false, true);
		test("scenariosfortest/flaky/coffee_vending_machine/scenario3.avalla", true, false, true);
		test("scenariosfortest/flaky/coffee_vending_machine/scenario4.avalla", true, false, true);
	}

	@Test void coffeVendingMachineChooseOne() throws Exception {
		test("scenariosfortest/flaky/coffee_vending_machine/scenario_pickchooseone.avalla", true, false, true);
		// to unpick the value.
		test("scenariosfortest/flaky/coffee_vending_machine/scenario_pickchooseone2.avalla", true, false, true);
	}

	@Test void multipleChoose() throws Exception {
		test("scenariosfortest/flaky/scenario_multiplechoose.avalla", true, false, true);
	}

	@Test void chooseUnboundedDomains() throws Exception {
		// 'with true' guard
		test("scenariosfortest/flaky/unbounded_domains/pickInt.avalla", true, true, true);
		test("scenariosfortest/flaky/unbounded_domains/pickInt_Trivial.avalla", true, true, true);
		// omitted 'with' (default 'true' guard)
		test("scenariosfortest/flaky/unbounded_domains/pickReal.avalla", true, true, true);
		// 'with false' guard -> fails
		test("scenariosfortest/flaky/unbounded_domains/pickNat.avalla", true, true, false);
	}

	@Test void chooseWithoutGuard() throws Exception {
		test("scenariosfortest/flaky/scenario_multiplechoose.avalla", true, true, true);
	}

	@Test void choosePickOutOfDomain() throws Exception {
		// Out of range only throws an Exception only if the picked variable is used in the guard
		test("scenariosfortest/flaky/choose_integer/scenario_out_of_range.avalla", true, false, false);
		try {
			// Out of DomainTerm always throws an Exception
			test("scenariosfortest/flaky/choose_integer/scenario_out_of_domain.avalla", true, false, false);
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			// expected
		}
	}

	@Test void pickInTinyScheduler() throws Exception {
		test("scenariosfortest/flaky/tiny_scheduler/correct_scenarios/check_ifnone_no_pick.avalla", true, false, true);
		test("scenariosfortest/flaky/tiny_scheduler/correct_scenarios/check_ifnone_w_pick.avalla", true, false, true);
		test("scenariosfortest/flaky/tiny_scheduler/check_ifnone_w_pick_false_guard.avalla", true, false, false); // must fail
		try {
			test("scenariosfortest/flaky/tiny_scheduler/check_ifnone_pick_undef.avalla", true, false, false);
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			// expected
		}
	}

	/* -- end pick tests -- */

	@Test void pillbox() throws Exception {
		test(ASM_EXAMPLES + "PillBox/Level0/pillbox_0_scenario1.avalla", false, false, true);		
	}

	/*	
		/ Added to test ! in function IDs
		Test
		public void testPopulation() throws Exception {
			// using extends 
	//		String scenario = "scenariosforexamples/population/withextend.avalla";
	//		test(scenario, true, false, true);
			
			ASMParser.setUpReadAsm(new File("scenariosforexamples/population/population__tempAsmetaV6005497458607192670.asm"));
			
		}
	*/
	
	@Test void testlampada() throws Exception {
		ASMParser.getResultLogger().setLevel(Level.OFF);
		// questo mi da errori strani
		try {
//			test("D:/AgHome\\Dropbox\\code\\didattica\\tvsw\\unibg_tvsw\\codice_lezioni\\6_atgt\\test.avalla", false, false);		
			test("scenariosfortest/u_dir/test.avalla", false, false, true);
		} catch(IllegalStateException ise) {
			System.out.println(ise.getMessage());
			fail();
		}
	}

	// works onlyon Angelo's PC
	@Test
	@Disabled void carSystem() throws Exception {
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
			if (!filename.endsWith(AsmetaParserUtility.ASM_EXTENSION)) continue;
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

	// this is failing since it is not possible to test extend rules with avalla
	@Test @Disabled void extend() throws Exception {
		test("scenariosfortest/use_extend/useextend.avalla", false, false, true);		
	}

	// 
	@Test @Disabled void abz26CaseStudy() throws Exception {
		String scenarioPath = ASM_EXAMPLES + "/../asmeta_models\\ABZ2026_CaseStudy\\SecondGeneration_AfterSimulation\\abstractests\\testtest0.avalla";
		test(scenarioPath, false, false, true);		
	}

	// 
	@Test @Disabled void abz26CaseStudyChatGPT() throws Exception {
		String scenarioPath = ASM_EXAMPLES + "/../asmeta_models/ABZ2026_CaseStudy/SecondGeneration_AfterSimulation/scenarios_gpt/scenario0_fixed.avalla";
		test(scenarioPath, false, false, true);		
	}

	@Test @Disabled void abz26CaseStudyFolder() throws Exception {
		String scenarioPath = ASM_EXAMPLES + "/../asmeta_models/ABZ2026_CaseStudy/SecondGeneration_AfterSimulation/abstracttests/";
		test(scenarioPath, true, true, true);		
	}

	
}
