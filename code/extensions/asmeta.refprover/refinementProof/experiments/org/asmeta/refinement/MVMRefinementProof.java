package org.asmeta.refinement;

import static org.junit.Assert.assertTrue;

import org.asmeta.refinement.RefinementProof;
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

}