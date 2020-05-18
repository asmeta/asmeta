package org.asmeta.atgt;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.junit.Test;

import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSeqContent;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import extgt.coverage.combinatorial.StdPairwiseCovBuild;
import tgtlib.definitions.TestSequence;

/**
 * tests for the asmeta generator
 *
 */
public class AsmTestGeneratorTest {

	public static final String FILE_BASE = "../../../../asm_examples/";

	@Test
	public void generateTestAllCriteriaPHD() throws Exception {
		String asmPath = FILE_BASE + "PHD/phd_master_flat2_v1.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath);
		TestGenerationWithNuSMV.useLTLandBMC = true;
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");

	}

	@Test
	public void generate3CombaPHD6() throws Exception {
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmTestSeqContent.class).setLevel(Level.DEBUG);
		Logger.getLogger(StdPairwiseCovBuild.class).setLevel(Level.DEBUG);
		String asmPath = FILE_BASE + "PHD/phd_master_flat2_v6.asm";
		List<AsmCoverageBuilder> coverageCriteria = CriteriaEnum.getCoverageCriteria(CriteriaEnum.THREEWISE);
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true, coverageCriteria);
		TestGenerationWithNuSMV.useLTLandBMC = true;
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, "gg.*");
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
			NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, v, coverageCriteria);
			TestGenerationWithNuSMV.useLTLandBMC = true;
			AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE,
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
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true,
				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria));
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");
	}

	@Test
	public void generateWinvariants() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String asmPath = "examples/phd_master_flat2_v00.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(asmPath, true,
				Collections.singleton(CriteriaEnum.BASIC_RULE.criteria));
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");
	}

	@Test
	public void generateCombinatorialPHD() throws Exception {
		Logger.getLogger(AsmTestGeneratorTest.class).setLevel(Level.DEBUG);
		String ex = "D:\\AgDocuments\\progettiDaSVN\\Rate4PHD\\ASM\\newPHD\\phd_master_flat2_v0.asm";
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true,
				Collections.singleton(CriteriaEnum.COMBINATORIAL_ALL.criteria));
		AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(Integer.MAX_VALUE, ".*");
	}

}
