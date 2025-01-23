package org.asmeta.nuxmv;

import java.io.File;

import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.Test;

public class TranslatorTester {
	
	@Test
	public void test() throws Exception {
		boolean simplify = true;
		boolean execute = false;
		boolean checkInteger = false;
		boolean useNuXmv = false;
		boolean useNuXmvTime = false;
		String file = "D:\\AgHome\\progettidaSVNGIT\\ricerca\\abz2025_casestudy_autonomous_driving\\asmeta spec\\SafetyEnforcer.asm";
		AsmetaSMV asmetaSVM = new AsmetaSMV(new File(file),simplify, execute, checkInteger, useNuXmv, useNuXmvTime);
		asmetaSVM.executeNuSMV();
	}

}
