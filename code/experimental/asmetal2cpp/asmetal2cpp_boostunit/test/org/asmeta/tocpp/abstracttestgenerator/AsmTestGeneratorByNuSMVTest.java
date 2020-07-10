package org.asmeta.tocpp.abstracttestgenerator;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import asm_mbtJunit.nusmv.TestGenerationWithNuSMV;
import asmetal2cpp_boostunit.ExampleTaker;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.Location;
import tgtlib.coverage.CovBuilderBySubCov;

public class AsmTestGeneratorByNuSMVTest {

	@Test
	public void test0() throws Exception {
		String asmspec = "../asmetal2cpp_codegen/examples/examples/byhand/Arduino/Bare/Bare.asm";
		genTestsandTranslate(asmspec,1);
	}

	@Test
	public void test1() throws Exception {
		String asmspec = "../asmetal2cpp_codegen/examples/examples/asmeta_examples/Counter.asm";
		genTestsandTranslate(asmspec,10);
	}

	@Test
	public void test2() throws Exception {
		String asmspec = "../asmetal2cpp_codegen/examples/examples/AdvancedClock.asm";
		genTestsandTranslate(asmspec,30);
	}
	
	@Test
	public void test3() throws Exception {
		Logger.getLogger(CovBuilderBySubCov.class).setLevel(Level.DEBUG);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);
		String asmspec = "../asmetal2cpp_codegen/examples/coffeeVendingMachineNC.asm";
		genTestsandTranslate(asmspec,5);
	}


	
	
	public static void genTestsandTranslate(String asmspec, int stepNumber) throws Exception {
		File file = ExampleTaker.getAsmFile(asmspec);
		System.out.println(file.getAbsolutePath());
		AsmTSGeneratorByNuSMV tg = new AsmTSGeneratorByNuSMV(file.getAbsolutePath());
		AsmTestSuite ts = tg.getTestSuite();
		for (AsmTestSequence tsq : ts.getTests()) {
			System.out.println("================ init");
			for(Map<Location, String> state: tsq.allInstructions()) {
				System.out.println(state);
				System.out.println("======");
			}
		}
	}

}
