package org.asmeta.refinement;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MVMRefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "../../../../../mvm-asmeta/asm_models/MVM APPFM/ref/";
	}

	@Test
	public void test01FirstRefinementMVM() throws Exception {
		proveRefinement("MVMcontroller00.asm", "MVMcontroller01.asm", true, true);
	}
	@Test
	public void test02SecondRefinementMVM() throws Exception {
		proveRefinement("MVMcontroller01.asm", "MVMcontroller02.asm", true, true);
	}
	@Test
	public void test03ThirdRefinementMVM() throws Exception {
		proveRefinement("MVMcontroller02.asm", "MVMcontroller03.asm", true, true);
	}
	
	@Test
	public void testMVM_2() throws Exception {
		String path = "F:\\Dati-Andrea\\GitHub\\RATE\\Case_studies\\MVM\\ASM\\";
		RefinementProof proof = new RefinementProof(path+"MVM_00\\Ventilatore0.asm", path +"MVM_01\\Ventilatore01.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		proof = new RefinementProof(path+"MVM_01\\Ventilatore01.asm", path +"MVM_02\\Ventilatore02.asm");
		result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		proof = new RefinementProof(path+"MVM_02\\Ventilatore02.asm", path +"MVM_3\\Ventilatore4SimpleTimeLtdY.asm");
		result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

}