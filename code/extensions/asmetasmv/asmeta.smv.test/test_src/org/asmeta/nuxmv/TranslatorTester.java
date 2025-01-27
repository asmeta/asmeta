package org.asmeta.nuxmv;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.Ignore;
import org.junit.Test;

public class TranslatorTester {
	
	@Ignore
	@Test
	public void testABZ25CaseStudy() throws Exception {
		boolean simplify = true;
		boolean execute = false;
		boolean checkInteger = false;
		boolean useNuXmv = true;
		boolean useNuXmvTime = false;
		String file = "D:\\AgHome\\progettidaSVNGIT\\ricerca\\abz2025_casestudy_autonomous_driving\\asmeta spec\\models\\SafetyEnforcer.asm";
		File file2 = new File(file);
		assertTrue(file2.exists());
		AsmetaSMV asmetaSVM = new AsmetaSMV(file2,simplify, execute, checkInteger, useNuXmv, useNuXmvTime);
		AsmetaSMVOptions.FLATTEN = false;
		asmetaSVM.translation();
	}

}
