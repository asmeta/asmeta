package org.asmeta.atgt;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.atgt.testoptimizer.UnchangedRemover;
import org.asmeta.atgt.testoptimizer.UnecessaryChangesRemover;
import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSeqContent;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.toAvalla;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;

/**
 * tests for the asmeta generator
 *
 */
public class AsmTestGeneratorTest {

	public static final String FILE_BASE = "../../../../asm_examples/";

	@Test
	public void generateSafeCombination() throws Exception {
		String asmPath = "examples\\SafeCombination.asm";
		// in both case we reach openSafe=true
		// LTL and BMC
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
		generateSafe(asmPath, 6);
		// CTL and FMC		
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.CTL;
		generateSafe(asmPath, 7);
	}

	@Test
	public void generateTCAS() throws Exception {
		String asmPath = "examples\\Tcas.asm";
		// in both case we reach openSafe=true
		// LTL and BMC
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
		generateSafe(asmPath, 2);
		// CTL and FMC		
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.CTL;
		generateSafe(asmPath, 3);
	}

	
	private void generateSafe(String asmPath, int exlength) throws Exception {
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "BR_r_Main_T2");
		assertEquals(1, result.getTests().size());
		int length = result.getTests().get(0).allInstructions().size();
		System.err.println(result.getTests().get(0).allInstructions().get(length-1));
		assertEquals(exlength, length);
		// translate to avalla
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		toAvalla ta = new toAvalla(output, result.getTests().get(0), "filename", "scenarioname");
		ta.saveToStream();
		System.out.println(output.toString());
	}

	@Test
	public void generateTestAllCriteriaPHD() throws Exception {
		String asmPath = FILE_BASE + "PHD/phd_master_flat2_v1.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath);
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC ;
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");

	}

	@Test
	public void generate3CombaPHD6() throws Exception {
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmTestSeqContent.class).setLevel(Level.DEBUG);
		Logger.getLogger(StdPairwiseCovBuild.class).setLevel(Level.DEBUG);
		String asmPath = FILE_BASE + "PHD/phd_master_flat2_v6.asm";
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(CriteriaEnum.THREEWISE_ALL);
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true);
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC ;
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(coverageCriteria,Integer.MAX_VALUE, "gg.*");
		for (AsmTestSequence t : result.getTests()) {
			System.out.println("test sequence generated for " + t.getGeneratedFor().getName());
			System.out.println("test sequence " + t.toVideo());
			System.out.print("tp covered: ");
			t.tpCovered().forEach(x -> System.out.print(x.getName()));
			System.out.println();
		}

	}

	@Test
	public void generateTestMonitoringPHD() throws Exception {
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(AsmTestSeqContent.class).setLevel(Level.DEBUG);
		String asmPath = FILE_BASE + "PHD/phd_master_flat2_v1.asm";
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(CriteriaEnum.BASIC_RULE);
		for (boolean v : new boolean[] { true, false }) {
			NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, v);
			TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC ;
			AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(coverageCriteria,Integer.MAX_VALUE,
					"BR_r_Main_TBR_r_Unassociated__REQ_ASSOC_REL_T1");
			System.out.println("test sequence " + result.getTests().get(0).toVideo());
			System.out.print("tp covered: ");
			result.getTests().get(0).tpCovered().forEach(x -> System.out.print(x.getName()));
			System.out.println();
		}
	}

	@Test
	public void generateTestExampleNOT() throws Exception {
		String asmPath = "examples/exwnot.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");

	}

	@Test
	public void generateCombinatorial() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String asmPath = "examples/asmenum.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria),Integer.MAX_VALUE, ".*");
	}

	@Test
	public void generateWinvariants() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String asmPath = "examples/phd_master_flat2_v00.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(
				Collections.singleton(CriteriaEnum.BASIC_RULE.criteria),Integer.MAX_VALUE, ".*");
	}

	@Test
	public void generateCombinatorialPHD() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String ex = "D:\\AgDocuments\\progettiDaSVN\\Rate4PHD\\ASM\\newPHD\\phd_master_flat2_v0.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(
				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria),Integer.MAX_VALUE, ".*");
	}

	@Test
	public void generateTLCS() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String ex = "examples\\TLCS\\TrafficLight_1.asm";
		NuSMVtestGenerator.removeUnaskedChanges = false;
		NuSMVtestGenerator.removeUnChangedControlles = false;
		ConverterCounterExample.IncludeUnchangedVariables = false;
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Collections.singleton(CriteriaEnum.BASIC_RULE.criteria),Integer.MAX_VALUE, ".*");
		AsmTestSequence x = result.getTests().get(0);
		System.out.println(x.allInstructions().get(0));
		System.out.println(x.allInstructions().get(1));
		// every step has the same number of variables (add also those that are unchanged)
		assertEquals(x.allInstructions().get(0).size(), x.allInstructions().get(1).size());
	}

	
	
	@Test
	public void generateMVM() throws Exception {
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);		
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);	
		
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC ;
		AsmetaSMV.BMCLength = 100;
		
		//String ex = "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		//String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		
		//String ex = "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
		
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		NuSMVtestGenerator.removeUnaskedChanges = false;
		NuSMVtestGenerator.removeUnChangedControlles = false;
		ConverterCounterExample.IncludeUnchangedVariables = false;
		//AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "BR_r_Main_T");//|BR_r_Main_FFFTT15");
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(1, "BR_r_Main_TFFFTT12");
		//AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(	Collections.singleton(CriteriaEnum.BASIC_RULE.criteria),Integer.MAX_VALUE, ".*");
		
		//toAvalla toavalla = new toAvalla(new PrintStream(System.out), result.getTests().get(0), "", "");
		//toavalla.saveToStream();
		
		SaveResults.saveResults(result,ex,Collections.singleton(FormatsEnum.AVALLA), "");
		// the same tests polished
		List<AsmTestSequence> tests = result.getTests();
		UnecessaryChangesRemover eucr  = new UnecessaryChangesRemover(asms);
		for(int i = 0; i < tests.size(); i++) {			
			UnchangedRemover.conRemover.optimize(tests.get(i));
			eucr.optimize(tests.get(i));
		}
		SaveResults.saveResults(result,ex,Collections.singleton(FormatsEnum.AVALLA), "");
	}

	@Test
	public void generateMVMFunctions() throws Exception {
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);		
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);	
		
		TestGenerationWithNuSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC ;
		
		//String ex = "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		//String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdYFun.asm";
		
		//String ex = "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
		
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);

		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Collections.singleton(CriteriaEnum.BASIC_RULE.criteria),1, ".*");
		
	}
	
	@Test
	public void generateASEExperiments() throws Exception {
		String folderPath = "../../../experimental/asmeta.evotest/asmeta.evotest.experiments/src/main/resources/models/";
		//
		//String ex = folderPath + "Contatore_U_DA_H.asm"; //-> h is a keyword in NuSMV
		// These names are reserved for the LTL temporal operators
		//
		//String ex = folderPath + "TrafficLightv2.asm";
		// keywords count ( basic_expr_list ) -- count of TRUE boolean expressions
		// 
		//String ex = folderPath + "CoffeeVendingMachineNC.asm";
		//String ex = folderPath + "Tcas.asm";
		
		//String ex = folderPath + "SafeCombination.asm";
		//String ex = folderPath + "Contatore_U_DA_H.asm";
		//String ex = folderPath + "LGS_3L.asm";
		//String ex = folderPath + "SmartHome.asm"; // this contain agents, it won't work		
		//String ex = folderPath + "SiGistica.asm";
		String ex = folderPath + "Ferryman.asm";
		
		var f = new File(ex);
		assert f.exists() : Paths.get(f.getCanonicalPath()).normalize() + " does not exists";		
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(f);				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Collections.singleton(CriteriaEnum.RULE_GUARD.criteria),1, ".*");
		
	}

	
}
