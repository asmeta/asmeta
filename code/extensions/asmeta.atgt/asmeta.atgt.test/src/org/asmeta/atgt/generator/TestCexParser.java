package org.asmeta.atgt.generator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestCexParser {

	// ATTENZIONE IL MODEL CHECKER POTREBBE CAMBIARE IL TESTO DEI CONTROESEMPI 
	// AD ESEMPIO CON LE MAPPE DELLE LOCAZIONI
	
	@Test
	public void testSMC() throws FileNotFoundException, IOException {
		TestGenerationWithNuSMV.useLTLandBMC = false;
		String path = "cexes\\cex_mvm1.txt";
		Counterexample cex = TestGenerationWithNuSMV.parseCounterExample(new BufferedReader(new FileReader(path)));
		assertEquals(6, cex.length());		
		assertEquals("CLOSED",cex.getState(6).getVarValue("iValve"));		
	}


	
	@Test
	public void testBMC() throws FileNotFoundException, IOException {
		TestGenerationWithNuSMV.useLTLandBMC = true;
		String path = "cexes\\cex_bmc_mvm.txt";
		Counterexample cex = TestGenerationWithNuSMV.parseCounterExample(new BufferedReader(new FileReader(path)));
		//assertEquals(6, cex.length());		
		//assertEquals("CLOSED",cex.getState(6).getVarValue("iValve"));		
	}

	
	
}
