package org.asmeta.atgt.testoptimizer;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;

public class UnecessaryChangesRemoverTest {

	@Test
	public void testOptimize() throws Exception {
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.OFF);		
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);	
		Logger.getLogger("org.asmeta.simulator.main.Simulator").setLevel(Level.OFF);
		
		TestGenerationWithNuSMV.useLTLandBMC = true;
		
		//String ex = "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		//String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
		
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true,
				Collections.singleton(CriteriaEnum.BASIC_RULE.criteria));
		//AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "BR_r_Main_T");//|BR_r_Main_FFFTT15");
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(1, "BR_r_Main_TFT2");
		//
		AsmTestSequence asmTest = result.getTests().get(0);
		printtovideo(asmTest);
		//
		System.out.println("removing unchanged functions (both monitored and controlled)");
		UnchangedRemover.eInstanceAll.optimize(asmTest);		
		printtovideo(asmTest);
		//
		System.out.println("removing what is not asked");
		UnecessaryChangesRemover eucr  = new UnecessaryChangesRemover(asms);
		eucr.optimize(asmTest);
		printtovideo(asmTest);
		
	}

	/**
	 * @param asmTest
	 */
	private void printtovideo(AsmTestSequence asmTest) {
		System.out.println("0->" +asmTest.allInstructions().get(0).size() + " " + asmTest.allInstructions().get(0));
		System.out.println("1->" +asmTest.allInstructions().get(1).size() + " " + asmTest.allInstructions().get(1));
		System.out.println("2->" +asmTest.allInstructions().get(2).size() + " " + asmTest.allInstructions().get(2));
		System.out.println("3->" +asmTest.allInstructions().get(3).size() + " " + asmTest.allInstructions().get(3));
		List<String> names = asmTest.getState(0).keySet().stream().map(x -> x.toString())
				.collect(Collectors.toList());
		Set<String> uniques = new HashSet<>();
	    System.out.println("duplicates " + names.stream()
	        .filter(e -> !uniques.add(e))
	        .collect(Collectors.toSet()));
	}

}
