package org.asmeta.atgt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.atgt.generator.AsmTestGenerator.MBTCoverage;
import org.asmeta.atgt.testoptimizer.UnchangedRemover;
import org.asmeta.atgt.testoptimizer.UnecessaryChangesRemover;
import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.generator.AsmMonitoredDataExtractor;

/**
 * tests for the asmeta generator
 *
 */
public class ExperimentsMVM_ICTSS2021 {

	static FileWriter fw; 
	
	@BeforeClass
	public static void setup() throws IOException{
		String fileName = "expriments_data" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)+ ".txt";
		fw = new FileWriter(fileName);
		fw.write("XX");
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.OFF);
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.ERROR);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger(AsmMonitoredDataExtractor.class).setLevel(Level.OFF);
		TestGenerationWithNuSMV.useLTLandBMC = true;		
		NuSMVtestGenerator.removeUnaskedChanges = false;
		NuSMVtestGenerator.removeUnChangedControlles = false;
		ConverterCounterExample.IncludeUnchangedVariables = false;
	}
	
	
	@Test
	public void generateMVM() throws Exception {

		// String ex =
		// "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		// String ex =
		// "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		// String ex =
		// "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";

		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));

		List<Collection<AsmCoverageBuilder>> criteria = new ArrayList<>();
		List<AsmCoverageBuilder> allcriteria = new ArrayList<>();
		for(CriteriaEnum c: CriteriaEnum.values()) {
			// skip 3 wise and two wise monitored
			if (c == CriteriaEnum.COMBINATORIAL_ALL) continue;
			if (c == CriteriaEnum.THREEWISE) continue;
			criteria.add(Collections.singleton(c.criteria));
			allcriteria.add(c.criteria);
		}
		criteria.add(allcriteria);
				
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);

		for (Collection<AsmCoverageBuilder> asmcb : criteria) {

			String name = asmcb.stream().map(x -> x.getCoveragePrefix()).collect(Collectors.joining());
			if (name.length() > 8) name = "ALL";
			println(name);
			// generate the tests
			Instant start = Instant.now();
			AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(asmcb,1, "CR.*");
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			println("time:" + Long.toString(timeElapsed));
			// same not optimezed
//			SaveResults.saveResults(result, ex, Collections.singleton(FormatsEnum.AVALLA), name,"");
//			// the same tests polished
//			List<AsmTestSequence> tests = result.getTests();
//			UnecessaryChangesRemover eucr = new UnecessaryChangesRemover(asms);
//			for (int i = 0; i < tests.size(); i++) {
//				UnchangedRemover.conRemover.optimize(tests.get(i));
//				eucr.optimize(tests.get(i));
//			}
//			SaveResults.saveResults(result, ex, Collections.singleton(FormatsEnum.AVALLA), name+ "opt", "");
		}

	}

	private void println(String s) throws IOException {
		fw.append(s+ "\n");
		System.out.println(s);		
	}

}
