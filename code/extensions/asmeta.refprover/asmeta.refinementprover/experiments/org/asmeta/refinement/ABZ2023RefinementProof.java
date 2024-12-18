package org.asmeta.refinement;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ABZ2023RefinementProof extends ProofTest {

	@Test
	public void testAMAN() throws Exception {
		String path = "refinement/abz2023/";
		RefinementProof proof = new RefinementProof(path+"aman0.asm", path +"aman1.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		
	}

}