package org.asmeta.nusmv.main;

import java.io.File;
import java.util.Arrays;

import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("TestToMavenSkip")
public class AsmetaSMVMVM extends AsmetaSMVtestTranslateBase{

	static String basePath = "../../../../../";
	
	@BeforeAll
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
		//AsmetaSMV.modelCheckerMode = ModelCheckerMode.CTL;
		// non metto assert perchè non sono sicroc che questo fiel esista
		testOneSpec(basePath+"mvm-asmeta\\asm_models\\MVM APPFM\\Ventilatore01.asm", options);
	}

}
