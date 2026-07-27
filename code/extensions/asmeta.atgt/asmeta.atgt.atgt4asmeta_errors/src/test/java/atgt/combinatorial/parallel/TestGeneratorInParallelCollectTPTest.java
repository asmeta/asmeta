package atgt.combinatorial.parallel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.combinatorial.AsmCombCovBuilder;
import atgt.combinatorial.yices.YicesModelGenExec;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.TestSequenceState;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.reduction.TestSuiteRed;

public class TestGeneratorInParallelCollectTPTest {

	/**
	 * Inits the spin generator
	 */
	@BeforeClass
	public static void initLogger() {
		Logger.getLogger(TestSuiteGeneratorInParallelCollectTP.class).setLevel(Level.DEBUG);
	}

	
	@Test
	public void testForCoverage() {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		//ASMSpecification SP = AsmMLoaderTest.BasicBillingSystem();
		//AsmCoverage tps = AsmCombCovBuilder.createNWiseCovBuilder(2).getTPTree(SP);
		AsmCoverage tps = AsmCombCovBuilder.makePairwiseCovBuilder().getTPTree(SP);
		for(TestCondition tp: tps.allTPs()){
			tp.setToVerify(true);
		}
		TestSequenceGenerator generator = new YicesModelGenExec(SP.getVariables(), SP.getAxiom(), AsmTestSequence.factory);
		TestSuiteGeneratorInParallelCollectTP gen = new TestSuiteGeneratorInParallelCollectTP(new AsmProject(SP, tps),generator , AsmTestSuite.getAsmTestSuiteFactory());
		AsmTestSuite ts = (AsmTestSuite) gen.generateTestsWait();		
		System.out.println(ts.size());	
		TestSuiteRed<AsmTestCondition, AsmTestSequence> red = new TestSuiteRed<AsmTestCondition, AsmTestSequence>();
		red.analyzeAsmTestSuite(ts);
		// get only those necessary
		for(AsmTestSequence t : ts.getTests()){
			if (t.getState() == TestSequenceState.TEST_DISCARDED) 
				System.out.println("dddd");
		}
	}

}
