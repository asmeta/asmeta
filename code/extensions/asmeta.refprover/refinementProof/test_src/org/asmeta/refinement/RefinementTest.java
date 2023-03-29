package org.asmeta.refinement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.asmeta.refinement.RefinementProof;
import org.junit.Test;

public class RefinementTest {
	
	@Test
	public void testIncrement_ref1() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/increment_ref1.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrement_ref2() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/increment_ref2.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		//we are not able to detect unreachable states
		//the generic step is not conformant because it
		//is not conformant in an unreachable step
		assertFalse(result[1]);
	}

	@Test
	public void testIncrement_ref1_refAnotherMon() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment_ref1.asm", "refinement/increment_ref1_refAnotherMon.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrement_ref1_ref() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment_ref1.asm", "refinement/increment_ref1_ref.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	//a derived function is used to specify the relation between abstract inputs
	//and refined inputs
	//this is marked as a correct refinement
	@Test
	public void testIncrement_ref1_ref_altVersion() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment_ref1.asm", "refinement/increment_ref1_ref_altVersion.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrement_ref3() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment_ref1.asm", "refinement/increment_ref3.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrement_ref3ToFirstModel() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/increment_ref3.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrementNonRefinement() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/incrementNonRefinementAbstract.asm", "refinement/incrementNonRefinementRefinement.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertFalse(result[1]);
	}

	@Test
	public void testIncrementUnnecessaryFunction() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/incrementUnnecessaryFunction.asm", "refinement/increment.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrementUnnecessaryFunctionLimited() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/incrementUnnecessaryFunctionLimited.asm", "refinement/incrementLimited.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testFreeMonitored() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/freeMonitoredAbstract.asm", "refinement/freeMonitoredRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testInitState() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/initStateAbstract.asm", "refinement/initStateRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
	}

	@Test
	public void testAddedState() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/addedStateExampleAbstract.asm", "refinement/addedStateExampleRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testDerived() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/derivedAbstract.asm", "refinement/derivedRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	//@Test
	public void testDerivedInUpdate() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/derivedInUpdateAbstract.asm", "refinement/derivedInUpdateRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testChoose() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/chooseAbstract.asm", "refinement/chooseRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testChooseNoChoose() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/chooseRefined.asm", "refinement/chooseRefinedRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testChooseNoChoose2() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/chooseRefined.asm", "refinement/chooseRefinedRefined2.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testChooseNoCorrectRef() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/chooseAbstractNoCorrectRef.asm", "refinement/chooseRefinedNoCorrectRef.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertFalse(result[1]);
	}

	@Test
	public void testIncrementNeedInvariant() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/incrementRefNeedInvariant.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testIncrementBounded() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/incrementBoundedAbstract.asm", "refinement/incrementBoundedRefined.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}
	
	@Test
	public void testPillBoxLocal() throws Exception {
		RefinementProof proof = new RefinementProof("refinement/pillbox_0.asm", "refinement/pillbox_1_for_RefProv.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	
	@Test
	public void testPillBox() throws Exception {
		String basePath = "D:\\AgHome\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\";
		RefinementProof proof = new RefinementProof(basePath+"onlyred_level0/pillbox_0.asm", basePath+"onlyred_level1/pillbox_1_for_RefProv.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testPillBox2() throws Exception {
		RefinementProof proof = new RefinementProof("pillbox/pillbox_0.asm", "pillbox/pillbox_1_for_RefProv.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testMVM() throws Exception {
		String path = "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\";
		RefinementProof proof = new RefinementProof(path+"Ventilatore0.asm", path +"Ventilatore1.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		proof = new RefinementProof(path+"Ventilatore1.asm", path +"Ventilatore2.asm");
		result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	@Test
	public void testMVM_2() throws Exception {
		String path = "F:\\Dati-Andrea\\GitHub\\RATE\\Case_studies\\MVM\\ASM\\";
		RefinementProof proof = new RefinementProof(path+"MVM_00\\Ventilatore0.asm", path +"MVM_01\\Ventilatore1.asm");
		boolean[] result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		proof = new RefinementProof(path+"MVM_01\\Ventilatore1.asm", path +"MVM_02\\Ventilatore2.asm");
		result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
		proof = new RefinementProof(path+"MVM_02\\Ventilatore2.asm", path +"MVM_3\\Ventilatore4SimpleTimeLtdY.asm");
		result = proof.buildProof();
		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	
}