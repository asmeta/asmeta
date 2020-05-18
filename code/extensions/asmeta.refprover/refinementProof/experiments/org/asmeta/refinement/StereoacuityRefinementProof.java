package org.asmeta.refinement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StereoacuityRefinementProof extends ProofTest {

	@Before
	public void beforeTest() {
		basePath = "../../../../asm_examples/stereoacuity/";
	}

	@Test
	public void test1_FirstRefinement() throws Exception {
		//proveRefinement("certifierGround.asm", "certifierRaff1.asm", true, true);
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"levelTest0", "levelCertificate0", "outMessage0"}));
		proveRefinement("certifierGround.asm", "certifierRaff1.asm", functionsToCheck, true, true);
	}

	@Test
	public void test2_SecondRefinement() throws Exception {
		//proveRefinement("certifierRaff1.asm", "certifierRaff2.asm", true, true);
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"levelTest0", "levelCertificate0", "outMessage0"}));
		proveRefinement("certifierRaff1.asm", "certifierRaff2.asm", functionsToCheck, true, true);
	}

	@Test
	public void test3_ThirdRefinement() throws Exception {
		//proveRefinement("certifierRaff2.asm", "certifierRaff3.asm", true, true);
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"levelTest0", "levelCertificate0", "outMessage0"}));
		proveRefinement("certifierRaff2.asm", "certifierRaff3.asm", functionsToCheck, true, true);
	}

	@Test
	public void test4_FourthRefinement() throws Exception {
		//proveRefinement("certifierRaff3.asm", "certifierRaff4ForSMTproof.asm", true, true);
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"levelTest0", "levelCertificate0", "outMessage0"}));
		proveRefinement("certifierRaff3.asm", "certifierRaff4ForSMTproof.asm", functionsToCheck, true, true);
	}

	@Test
	public void test5_FifthRefinement() throws Exception {
		//proveRefinement("certifierRaff4.asm", "certifierRaff5ForSMTproof.asm", true, true);
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"levelTest0", "levelCertificate0", "outMessage0"}));
		proveRefinement("certifierRaff4.asm", "certifierRaff5ForSMTproof.asm", functionsToCheck, true, true);
	}
}