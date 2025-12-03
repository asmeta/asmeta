package org.asmeta.atgt.generator.nusmv;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.junit.Test;

public class TestCexParser {

	// ATTENZIONE IL MODEL CHECKER POTREBBE CAMBIARE IL TESTO DEI CONTROESEMPI
	// AD ESEMPIO CON LE MAPPE DELLE LOCAZIONI

	@Test
	public void testSMC() throws FileNotFoundException, IOException {
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.CTL;
		String path = "cexes\\cex_mvm1.txt";
		Counterexample cex = TestGenerationWithNuSMV.parseCounterExample(new BufferedReader(new FileReader(path)));
		assertEquals(6, cex.length());
		assertEquals("CLOSED",cex.getState(6).getVarValue("iValve"));
	}



	@Test
	public void testBMC() throws FileNotFoundException, IOException {
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
		String path = "cexes\\cex_bmc_mvm.txt";
		Counterexample cex = TestGenerationWithNuSMV.parseCounterExample(new BufferedReader(new FileReader(path)));
		//assertEquals(6, cex.length());
		//assertEquals("CLOSED",cex.getState(6).getVarValue("iValve"));
	}



}
