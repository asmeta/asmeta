package atgt.generator.testsuite;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.RootCoverage;
import atgt.coverage.SkipCoveredTCFilter;
import atgt.coverage.TestCondition;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.SpinTSeqGenerator;
import atgt.generator.SpinTSuiteGenForTC;
import atgt.parser.ExampleLoader;
import atgt.preferences.ATGToolPreferences;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import tgtlib.preferences.TGLibPreferences;
import tgtlib.util.CmdExecutor;
import tgtlib.util.SimpleCmdExecutor;

class TestSuiteGeneratorChooseTest {

	@BeforeAll
	static void setuplogger(){
		Logger.getLogger(SpinTSuiteGenForTC.class).setLevel(Level.ALL);
		Logger.getLogger(SpinTSeqGenerator.class).setLevel(Level.ALL);
		Logger.getLogger(SimpleCmdExecutor.class).setLevel(Level.ALL);
		Logger.getLogger(CmdExecutor.class).setLevel(Level.ALL);
	}

	@BeforeAll
	static void setPrefs(){
		ATGToolPreferences.BITSTATE.setChecked(false);
		TGLibPreferences.DELETE_TMP.setChecked(false);
		ATGToolPreferences.USE_D_STEP.setChecked(false);
	}

	@Test void chooseTest() throws Exception {
		ASMSpecification spec = ExampleLoader.getSpec("fuzzyCounter.asm");
		// costruisci i tp
		AsmCoverage ct = RootCoverage.ROOT.getTPTree(spec);
		List<AsmTestSequence> testcases = new ArrayList<AsmTestSequence>();
		for(TestCondition tc: ct.allTPs()) {
			// lo seleziono per la generazione
			tc.setToVerify(true);
			// only the first one
			break;
		}
		// costruisco il progetto
		AsmProject pro = new AsmProject(spec, ct);
		// prendo il generatore
		SpinTSuiteGenForTC spt = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(pro);
		spt.setSearchCommonCoverage(false);
		spt.setTestConditionFilter(SkipCoveredTCFilter.SkipCoveredTCFilter);
		AsmTestSuite testsuite = ct.accept(spt);
		testcases.addAll(testsuite.getTests());
	}

	@Test void choose2Test() throws Exception {
		ASMSpecification spec = ExampleLoader.getSpec("fuzzyCounterChoose.asm");
		AsmCoverage ct = RootCoverage.ROOT.getTPTree(spec);
		// costruisco il progetto
		AsmProject pro = new AsmProject(spec, ct);
		// build the generator
		SpinTSuiteGenForTC spt = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(pro);
		// seleziona il tp
		int i = 0;
		for(TestCondition tc: ct.allTPs()) {
			tc.setToVerify(true);
			if (i++ > 10) break;
			assertSame(tc.getStatus(), TestConditionState.Queued);
			AsmTestSuite testsuite = ct.accept(spt);
			assertTrue(tc.getStatus() == TestConditionState.AssertViolated || tc.getStatus() == TestConditionState.UNFEASIBLE); 
		}
	}

	@Test void choose3Test() throws Exception {
		ASMSpecification spec = ExampleLoader.getSpec("fuzzyCounter3ForSpin.asm");
		AsmCoverage ct = RootCoverage.ROOT.getTPTree(spec);
		// costruisco il progetto
		AsmProject pro = new AsmProject(spec, ct);
		// build the generator
		SpinTSuiteGenForTC spt = SpinTSuiteGenForTC.createFlatSpinTSuiteGenForTC(pro);
		// seleziona il tp
		for(TestCondition tc: ct.allTPs()) {
			tc.setToVerify(true);
			System.out.println(tc.getName() + " " + tc.getCondition());
			assertSame(tc.getStatus(), TestConditionState.Queued);
			AsmTestSuite testsuite = ct.accept(spt);
			assertTrue(tc.getStatus() == TestConditionState.AssertViolated || tc.getStatus() == TestConditionState.UNFEASIBLE); 
			if (tc.getName().equals("BR_r_Main_TF1") && tc.getStatus() == TestConditionState.UNFEASIBLE) break;
		}
	}
}