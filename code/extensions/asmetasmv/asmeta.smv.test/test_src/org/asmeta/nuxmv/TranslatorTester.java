package org.asmeta.nuxmv;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.Ignore;
import org.junit.Test;

public class TranslatorTester {

	@Test
	public void testABZ25CaseStudy2() throws Exception {
		boolean simplify = false;
		boolean execute = true;
		boolean checkInteger = false;
		boolean useNuXmv = true;
		boolean useNuXmvTime = false;
		String file = "exampleXMV/SafetyEnforcerSuperSafe.asm";
		//String file = "D:\\GitHub\\ABZ 2025\\abz2025_casestudy_autonomous_driving\\asmeta spec\\models\\forMC\\SafetyEnforcerSuperSafeMC.asm";
		File file2 = new File(file);
		assertTrue(file2.exists());
		AsmetaSMV asmetaSMV = new AsmetaSMV(file2,simplify, execute, checkInteger, useNuXmv, useNuXmvTime);
		AsmetaSMVOptions.FLATTEN = false;
		AsmetaSMVOptions.setPrintNuSMVoutput(true);
		AsmetaSMVOptions.keepNuSMVfile = true;
		asmetaSMV.translation();
		AsmetaSMVOptions.keepNuSMVfile = true;
		asmetaSMV.createNuSMVfile();
		System.out.println(Paths.get(asmetaSMV.getSmvFileName()));
		//asmetaSVM.executeNuSMV();
	}
}
