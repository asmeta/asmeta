package asmeta.evotest.experiments;

import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

import java.io.File;
import java.util.Collections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;

import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;

public class TestExperiments {
	
	private static final String RESOURCES = "resources";
	private static final String MYASM_DIR = "myasm";
	private static final String RANDOM_DIR = "randomtests";
	private static final String ATGT_DIR = "atgttests";

	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		String targetDir = "./" + RESOURCES + "/" + MYASM_DIR + "/mytest.avalla";
		computeCoverageFromAvalla(targetDir, RESOURCES + "/manualtests/report.csv");

//		String asm = "./" + RESOURCES + "/" + MYASM_DIR + "/myasm.asm";		
//		String asm = "./" + RESOURCES + "/" + "pillbox_1.asm";
//		String asm = "./" + RESOURCES + "/" + "vascaidro.asm";
//		AsmCollection asms = ASMParser.setUpReadAsm(new File(asm));
//		
//		System.out.println("Random");
//		// Come decidere i parametri della random simulation?
//		AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asms, 1, 1);
//		AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
//		computeCoverageFromAsmTestSuite(asm, randomSuite, RANDOM_DIR, RANDOM_DIR + "/report.csv");
//		
//		System.out.println("ATGT - NuSMV");
//		NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asm, true);
//		AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(
//				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria), Integer.MAX_VALUE, ".*");
//		computeCoverageFromAsmTestSuite(asm, nusmvSuite, ATGT_DIR, ATGT_DIR + "/report.csv");
		
//		System.out.println("EvoAsmetaAtgt");
//		// Chiamata al generatore che usa EvoSuite
//		computeCoverageFromAvalla(targetDir);
	}
	
	private static void computeCoverageFromAsmTestSuite(String asm, AsmTestSuite suite, String dir, String csvPath) {
		String targetDir = RESOURCES + "/" + dir;
		String csvDir = RESOURCES + "/" + csvPath;
		new File(targetDir).mkdir();
		// Genera gli avalla
		SaveResults.saveResults(suite, asm, Collections.singleton(FormatsEnum.AVALLA), "", new File(targetDir).getAbsolutePath());
		computeCoverageFromAvalla(targetDir, csvDir);
	}
	
	private static void computeCoverageFromAvalla(String targetDir, String csvPath) {
		try {
			// Esegui gli avalla generati (stampa info sulla coverage in csv)
			AsmetaV.execValidation(targetDir, true, csvPath);
			// Per evitare che i risultati ottenuti da una metodologia siano la base di partenza per la successiva
			RuleEvalWCov.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
