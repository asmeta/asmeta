package org.asmeta.nusmv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.nusmv.util.Util;

public class AsmetaSMVtest {

	protected AsmetaSMV execNuSMV(String file) {
		AsmetaSMV as = null;
		try {
			as = new AsmetaSMV(file);
			Util.keepNuSMVfile = true;//per non cancellare il file NuSMV prodotto
			as.translation();
			as.createNuSMVfile();
			Util.setPrintNuSMVoutput(false);
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error while executing the NuSMV model.");
		}
		return as;
	}

	protected AsmetaSMV execNuSMV(String file, String nusmvFileName, boolean simplify) {
		AsmetaSMV as = null;
		try {
			as = new AsmetaSMV(file, simplify);
			as.translation();
			as.createNuSMVfile(nusmvFileName);
			Util.setPrintNuSMVoutput(false);
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error in the NuSMV model execution.");
		}
		return as;
	}

	public List<Boolean> getResults(String file) {
		return execNuSMV(file).mv.nuSmvPropsResults;
	}

	public Map<String, Boolean> getMapResults(String file) {
		return execNuSMV(file).mv.getMapPropResult();
	}

	/**
	 * Checks that all the CTL properties declared in the ASM model are
	 * satisfied.
	 * 
	 * @param asm an ASM model
	 * @param numProperties number of properties to be proven
	 */
	protected void testAllCtlPropsAreTrue(String asm, int numProperties) {
		Map<String, Boolean> mapResults = getMapResults(asm);
		assertEquals(numProperties, mapResults.size());
		for(Entry<String, Boolean> prop: mapResults.entrySet()) {
			assertTrue("The property " + prop.getKey() + " should be true, instead is false.", prop.getValue());
		}
	}

	/**
	 * Checks that all the CTL properties declared in the ASM model are
	 * not satisfied.
	 * 
	 * @param asm an ASM model
	 * @param numProperties TODO
	 */
	protected void testAllCtlPropsAreFalse(String asm, int numProperties) {
		Map<String, Boolean> mapResults = getMapResults(asm);
		assertEquals(numProperties, mapResults.size());
		for(Entry<String, Boolean> prop: mapResults.entrySet()) {
			assertFalse("The property " + prop.getKey() + " should be true, instead is false.", prop.getValue());
		}
	}
}
