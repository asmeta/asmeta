package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import atgt.coverage.tpstatus.TestConditionState;

import org.junit.jupiter.api.Test;

class TestConditionTest {

	@Test void isAssertViolatedRunning() {
		AsmTestCondition tc = new AsmTestCondition("prova",null);
		// se è running
		tc.setRunning();
		tc.setAssertViolated(true);
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
		assertTrue(tc.isAssertViolated());
		// se a questo punto faccio il covered
		// lo stato non cambia
		tc.status.setCovered(true);
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
		// se aggiungo un test lo stato non cambia !!
		AsmTestSequence testCase = new AsmTestSequence(tc);
		tc.bindTestSeqTestPred(testCase);
		assertEquals(TestConditionState.AssertViolated, tc.getStatus());
		// e el'informazione non viene registrata !!!
		assertEquals(1,tc.allCoveredBy().size());
		assertTrue(tc.allCoveredBy().contains(testCase));
		assertEquals(1,testCase.tpCovered().size());		
		assertTrue(testCase.tpCovered().contains(tc));
	}
}
