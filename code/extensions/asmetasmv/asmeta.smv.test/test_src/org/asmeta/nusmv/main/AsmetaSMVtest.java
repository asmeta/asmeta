package org.asmeta.nusmv.main;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;

import asmeta.definitions.CtlSpec;
import asmeta.definitions.Property;
import asmeta.definitions.TemporalProperty;

// standard SMV test with CTL properties!
public class AsmetaSMVtest {

	@BeforeClass
	public static void testNuSMVInstallation(){
		//
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.CTL;
		// try to execute NUSMV
		String solverName = AsmetaSMV.getSolverName();
		Process proc = null;
		boolean failed = false;
		try {
			List<String> cmdarray = Arrays.asList(solverName, "-h");
			ProcessBuilder pb = new ProcessBuilder(cmdarray);			
			proc = pb.start();
			// outputRunNuSMV = getOutput(smvFileName);
		} catch (Exception e) {
			out.println("Execution error\n" + e);
			out.println("OS: " + System.getProperty("os.name") + " VERSION: " + System.getProperty("os.version"));
			out.println("path: " + System.getProperty("PATH"));			
			failed = true; 			
		} finally {
			if (proc != null) proc.destroy();
		}
		if (failed) fail("execution of " + solverName + " failed");
	}
	
	
	
	protected AsmetaSMV execNuSMV(String file) {
		AsmetaSMV as = null;
		AsmetaSMVOptions asmetaOptions = new AsmetaSMVOptions();
		AsmetaSMVOptions.keepNuSMVfile = true;
		try {
			as = new AsmetaSMV(new File(file),asmetaOptions);
			as.translation();
			as.createNuSMVfile();
			AsmetaSMVOptions.setPrintNuSMVoutput(false);
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
			AsmetaSMVOptions.setPrintNuSMVoutput(false);
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error in the NuSMV model execution.");
		}
		return as;
	}

	public List<Boolean> getResults(String file) {
		return execNuSMV(file).mv.getNuSmvPropsResults();
	}

	/**
	 * Checks that all the CTL properties declared in the ASM model are
	 * satisfied.
	 * 
	 * @param asm an ASM model
	 */
	protected void testAllCtlPropsAreTrue(String asm) {
		testAllCtlPropsAre(true,asm);
	}

	
	private void testAllCtlPropsAre(boolean desiredValue, String asm) {
		AsmetaSMV execNuSMV = execNuSMV(asm);
		// check that the number of properties is correct
		EList<Property> props = execNuSMV.asm.getBodySection().getProperty();
		// count how many are CTL
		long nCTLProp = props.stream().filter(x -> x instanceof CtlSpec).count();
		Map<TemporalProperty, Boolean> mapResults = execNuSMV.mv.getMapPropResult();
		// for every property there is a result
		// TODO questa fallisce perchè alcune volte diverse proprietà di asmeta sono mappate sulle stesse proprietà di
		// nusmv (per la semplificazione) quindi quelle con risultati sono di meno
		// dovrei controllare che ogni propreità ha un risultato.
		assertEquals(nCTLProp, mapResults.size());
		// all are true
		for(Entry<TemporalProperty, Boolean> prop: mapResults.entrySet()) {
			assertEquals("The property " + prop.getKey() + " should be "+ desiredValue 
					+", instead is "+ (! desiredValue)+ ".", desiredValue, prop.getValue());
		}
	}

	
	/**
	 * Checks that all the CTL properties declared in the ASM model are
	 * not satisfied.
	 * 
	 * @param asm an ASM model
	 */
	protected void testAllCtlPropsAreFalse(String asm) {
		testAllCtlPropsAre(false,asm);
	}
}
