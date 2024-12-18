package org.asmeta.refinement;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ABZ2016RefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		//basePath = "../../../../asm_examples/ABZ2016/";
		basePath = "./refinement/ABZ2016/";
	}

	@Test
	public void test01FirstRefinement() throws Exception {
		proveRefinement("Hemodialysis_GM.asm", "Hemodialysis_ref1_forMC.asm", true, true);
	}

	@Test
	public void test02SecondRefinement() throws Exception {
		proveRefinement("Hemodialysis_ref1_forMC.asm", "Hemodialysis_ref2_forMC.asm", true, true);
	}

	@Test
	public void test03ThirdRefinement() throws Exception {
		proveRefinement("Hemodialysis_ref2_forMC.asm", "Hemodialysis_ref3_forMC.asm", true, true);
	}

	@Test
	public void test04ThirdRefinement() throws Exception {
		proveRefinement("Hemodialysis_ref3_forMC.asm", "Hemodialysis_ref4_forMC.asm", true, true);
	}

	@Test
	public void testNewHemo1() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/ABZ2016/New/HemodialysisGround.asm", "refinement/ABZ2016/New/HemodialysisRaff1_MCReq.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	public static void main(String[] args) throws Exception {
		ABZ2016RefinementProof f = new ABZ2016RefinementProof();
		f.beforeTest();
		f.test02SecondRefinement();
	}
}