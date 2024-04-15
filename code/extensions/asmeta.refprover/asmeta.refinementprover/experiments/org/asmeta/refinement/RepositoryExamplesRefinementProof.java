package org.asmeta.refinement;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryExamplesRefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "./refinement/";
	}

	@Test
	public void testSluiceGateNoInv() throws Exception {
		proveRefinement("sluiceGateGround.asm", "sluiceGateMotorCtl.asm", true, true);
	}

	@Test
	public void testSluiceGateWithInv() throws Exception {
		proveRefinement("sluiceGateGround.asm", "sluiceGateMotorCtlWithInv.asm", true, true);
	}

	//@Test
	public void testSafetyInjectionSystem() throws Exception {
		proveRefinement("SISAbstract.asm", "SISRefined.asm", true, true);
	}

	@Test
	public void testDerivedNumber() throws Exception {
		proveRefinement("SIS.asm", "SISrefWithDerived.asm", true, true);
	}

	@Test
	public void testTrafficLight() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>();
		functionsToCheck.add("phase0");
		proveRefinement("oneWayTrafficLight.asm", "oneWayTrafficLight_refinedForRefinement.asm", functionsToCheck, true, true);
	}

	//@Test
	public void testTrafficLight_simpl() throws Exception {
		Set<String> functionsToCheck = new HashSet<String>();
		functionsToCheck.add("phase0");
		proveRefinement("oneWayTrafficLightSimpl.asm", "oneWayTrafficLight_refinedForRefinementSimpl.asm", functionsToCheck, true, true);
	}
}