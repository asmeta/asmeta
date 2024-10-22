package asmeta.evotest.experiments;

import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

import java.io.File;
import java.util.Collections;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;

import org.asmeta.xt.validator.AsmetaV;

public class TestExperiments {
	
	private static final String RESOURCES = "resources";

	public static void main(String[] args) throws Exception {
		String asm = "./" + RESOURCES + "/" + "vascaidro.asm";
//		String asm = "./" + RESOURCES + "/" + "smarthome.asm";
		AsmCollection asms = ASMParser.setUpReadAsm(new File(asm));
		
		System.out.println("RANDOM");
		// Come decidere i parametri della random simulation?
		AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asms, 10, 10);
		AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
		computeCoverage(asm, randomSuite, "randomtests");
		
		System.out.println("NuSMV");
		NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asm, true);
		AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(
				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria), Integer.MAX_VALUE, ".*");
		computeCoverage(asm, nusmvSuite, "nusmvtests");
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
