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
import org.asmeta.simulator.wrapper.RuleFactory;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;

public class TestExperiments {
	
	private static final String RESOURCES = "resources";

	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);

		
//		String targetDir = "./" + RESOURCES + "/mytest.avalla";
//		AsmetaV.execValidation(targetDir, true);
		
//		String asm = "./" + RESOURCES + "/" + "pillbox_1.asm";
		String asm = "./" + RESOURCES + "/" + "vascaidro.asm";
//		String asm = "./" + RESOURCES + "/" + "myasm.asm";
		AsmCollection asms = ASMParser.setUpReadAsm(new File(asm));		
		
		System.out.println("RANDOM");
		// Come decidere i parametri della random simulation?
		AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asms, 3, 3);
		AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
		computeCoverage(asm, randomSuite, "randomtests");
//		
//		System.out.println("NuSMV");
//		NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asm, true);
//		AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(
//				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria), Integer.MAX_VALUE, ".*");
//		computeCoverage(asm, nusmvSuite, "nusmvtests");
	}
	
	private static void /*Coverage*/ computeCoverage(String asm, AsmTestSuite suite, String dir) {
		String targetDir = "./" + RESOURCES + "/" + dir;
		new File(targetDir).mkdir();
		SaveResults.saveResults(suite, asm, Collections.singleton(FormatsEnum.AVALLA), "", new File(targetDir).getAbsolutePath());
		try {
			AsmetaV.execValidation(targetDir, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
