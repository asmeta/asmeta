package org.asmeta.refinement;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SCP2016RefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "../../../../asm_examples/examples/hemodialysisDevice/";
	}

	@Test
	public void test01FirstRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = true;
		proveRefinement("HemodialysisGround.asm", "HemodialysisRef1_MC.asm", true, true);
	}

	@Test
	public void test02SecondRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = false;
		proveRefinement("HemodialysisRef1_MC.asm", "HemodialysisRef2_MC.asm", true, true);
	}

	@Test
	public void test03ThirdRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = false;
		proveRefinement("HemodialysisRef2_MC.asm", "HemodialysisRef3_MC.asm", true, true);
	}

	@Test
	public void test04GroundSecondRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = true;
		proveRefinement("HemodialysisGround.asm", "HemodialysisRef2_MC.asm", true, true);
	}

	@Test
	public void test05GroundThirdRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = true;
		proveRefinement("HemodialysisGround.asm", "HemodialysisRef3_MC.asm", true, true);
	}

	@Test
	public void test06FirstRefinementThirdRefinement() throws Exception {
		RefinementProof.addMonitoredInAbstract = false;
		proveRefinement("HemodialysisRef1_MC.asm", "HemodialysisRef3_MC.asm", true, true);
	}
}