package org.asmeta.refinement;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestExamplesRefinementProof extends ProofTest {
	
	@Before
	public void beforeTest() {
		basePath = "./refinement/";
	}

	@Test
	public void testAddedState() throws Exception {
		proveRefinement("addedStateExampleAbstract.asm", "addedStateExampleRefined.asm", true, true);
	}

	@Test
	public void testChoose() throws Exception {
		proveRefinement("chooseAbstract.asm", "chooseRefined.asm", true, true);
	}

	@Test
	public void testChooseNoCorrectRef() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>();
		functionsToCheck.add("x0");
		proveRefinement("chooseAbstractNoCorrectRef.asm", "chooseRefinedNoCorrectRef.asm", functionsToCheck, true, false);
	}

	//@Test
	public void testDerivedUpdate() throws Exception {
		proveRefinement("derivedInUpdateAbstract.asm", "derivedInUpdateRefined.asm", true, true);
	}

	@Test
	public void testIncrement_ref1() throws Exception {
		proveRefinement("increment.asm", "increment_ref1.asm", true, true);
	}

	@Test
	public void testIncrement_ref1_altVersion() throws Exception {
		proveRefinement("increment.asm", "increment_ref1_ref_altVersion.asm", true, true);
	}

	@Test
	public void testIncrement_ref1_refAnotherMon() throws Exception {
		proveRefinement("increment.asm", "increment_ref1_refAnotherMon.asm", true, true);
	}

	@Test
	public void testIncrement_ref2() throws Exception {
		proveRefinement("increment.asm", "increment_ref2.asm", true, true);
	}

	@Test
	public void testIncrement_ref3() throws Exception {
		proveRefinement("increment.asm", "increment_ref3.asm", true, true);
	}

	@Test
	public void testInitState() throws Exception {
		proveRefinement("initStateAbstract.asm", "initStateRefined.asm", true, true);
	}

	@Test
	public void testFreeMonitored() throws Exception {
		proveRefinement("freeMonitoredAbstract.asm", "freeMonitoredRefined.asm", true, true);
	}
}