package atgt.coverage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import atgt.coverage.tpstatus.TestConditionState;

public class TestConditionTest {

	@Test
	public void testIsAssertViolatedRunning() {
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
