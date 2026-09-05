package atgt.generator.testsuite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import atgt.combinatorial.CollectedNWiseTC;

import org.junit.jupiter.api.Test;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.collection.CollectedTestCondition;

class CollectedTestConditionTest {

	@Test void setAssertViolated() {
		CollectedTestCondition ctc = new CollectedNWiseTC();
		// se è running e poi assertviolated
		ctc.setRunning();
		assertEquals(TestConditionState.Running, ctc.getStatus());
		ctc.setAssertViolated(true);
		assertEquals(TestConditionState.AssertViolated, ctc.getStatus());
		assertTrue(ctc.isAssertViolated());
		// se a questo punto faccio il covered
		// lo stato non cambia
		AsmTestSequence testCase = new AsmTestSequence(null);
		ctc.bindTestSeqTestPred(testCase);
		assertEquals(TestConditionState.AssertViolated, ctc.getStatus());
		// e el'informazione non viene registrata !!!
		assertEquals(0,ctc.allCoveredBy().size());
		assertEquals(0,testCase.tpCovered().size());		
	}
}
