package org.asmeta.atgt.testoptimizer;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import atgt.specification.location.Function;
import atgt.specification.location.Location;

public class UnecessaryChangesRemoverTest {

	@Test
	public void testOptimize() throws Exception {
		NuSMVtestGenerator.removeUnaskedChanges = false;
		NuSMVtestGenerator.removeUnChangedControlles = false;
		
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.OFF);		
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);	
		Logger.getLogger("org.asmeta.simulator.main.Simulator").setLevel(Level.OFF);
		
		TestGenerationWithNuSMV.useLTLandBMC = true;
		
		//String ex = "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		//String ex = "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		
				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		//AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "BR_r_Main_T");//|BR_r_Main_FFFTT15");
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "BR_r_Main_TFFFTFF102");
		//
		AsmTestSequence asmTest = result.getTests().get(0);
		printtovideo(asmTest);
				
		// TEMP for debugging check(result.getTests().get(0).allInstructions().get(4));
		//
		System.out.println("removing unchanged functions (both monitored and controlled)");
		//UnchangedRemover.allRemover.optimize(asmTest);		
		UnchangedRemover.conRemover.optimize(asmTest);
		printtovideo(asmTest);
//		//
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		System.out.println("removing what is not asked");
		UnecessaryChangesRemover eucr  = new UnecessaryChangesRemover(asms);
		eucr.optimize(asmTest);
		printtovideo(asmTest);
		
	}

	private void check(Map<Location, String> map) {
		for(Entry<Location, String> l: map.entrySet()) {
			Location key = l.getKey();
			if (key.getName().startsWith("start")) {
				System.out.print(key + " -> " + l.getValue() + " " + key.getIdExpression() + " " + (key.getIdExpression().hashCode()) + "  ");
				System.out.println(((Function)key).hashCode());
			}
		}
		
		
	}

	/**
	 * @param asmTest
	 */
	private void printtovideo(AsmTestSequence asmTest) {
		List<Map<Location, String>> allInstructions = asmTest.allInstructions();
		for(int i = 0; i < 10 && i < allInstructions.size(); i++) {
			System.out.println( i + "->" +allInstructions.get(i).size() + " " + allInstructions.get(i));
			// print duplicates 
			Set<String> uniques = new HashSet<>();
			List<String> locations = allInstructions.get(i).keySet().stream().map(x -> x.toString())
					.collect(Collectors.toList());
			 System.out.println("duplicates locations " + locations.stream()
		        .filter(e -> !uniques.add(e))
		        .collect(Collectors.toSet()));
			
		}
		List<String> names = asmTest.getState(0).keySet().stream().map(x -> x.toString())
				.collect(Collectors.toList());
		Set<String> uniques = new HashSet<>();
	    System.out.println("duplicates " + names.stream()
	        .filter(e -> !uniques.add(e))
	        .collect(Collectors.toSet()));
	}

}
