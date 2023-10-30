package org.asmeta.nusmv;

import java.io.File;
import java.util.Arrays;

import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.BeforeClass;
import org.junit.Test;

public class AsmetaSMVMVM extends AsmetaSMVtestTranslate{

	static String basePath = "../../../../../";
	
	@BeforeClass
	public static void checkbaseDir() {
		assert new File(basePath).exists();
		System.out.println(Arrays.toString(new File(basePath).listFiles()));
		assert Arrays.toString(new File(basePath).listFiles()).contains("asmeta");
	}
	
	
	@Test
	public void test() {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		AsmetaSMVOptions.setUseNuXmv(true);
		AsmetaSMVOptions.FLATTEN = false; 
		AsmetaSMVOptions.simplifyDerived  = true;
		AsmetaSMVOptions.keepNuSMVfile = true;
		testOneSpec(basePath+"mvm-asmeta\\asm_models\\MVM APPFM\\Ventilatore01.asm", options);
	}

}
