package org.asmeta.atgt.coverage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.asmeta.atgt.coverage.AsmetaAsSpec;
import org.asmeta.atgt.coverage.AsmetaBasicRuleVisitor;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import atgt.coverage.AsmCoverage;

public class AsmetaBasicRuleVisitorTest {

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testGetTPTree() throws Exception {
		// String ex =
		// "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		// String ex =
		// "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		generateCoverageFor(ex);
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testGetTPTreeMVM() throws Exception {
		generateCoverageFor("examples\\mvm0.asm");
	}
	
	@Test
	public void testGetTPTreeChoose() throws Exception {
		int tps = generateCoverageFor("examples\\SpecWithChoose.asm");
		// one tp: $i = 0 
		assertEquals(1, tps);
	}

	/**
	 * @param ex
	 * @return 
	 * @throws Exception
	 */
	private int generateCoverageFor(String ex) throws Exception {
		// String ex =
		// "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		AsmetaBasicRuleVisitor tpbuilder = new AsmetaBasicRuleVisitor();
		AsmCoverage tp = tpbuilder.getTPTree(new AsmetaAsSpec(asms));
		tp.allTPs().forEach(x -> System.out.println(x.getCondition()));
		return tp.getNumberofTPs();
	}

	@BeforeClass
	public static void setup() {
//		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);
//		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
//		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
//		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
	}

}
