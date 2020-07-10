package org.asmeta.tocpp.abstracttestgenerator;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import asmeta.AsmCollection;
import asmetal2cpp_boostunit.ExampleTaker;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.specification.location.Location;

public class AsmTestGeneratorBySimulationTest {

	@Test
	public void test0() throws Exception {
		String asmspec = "examples/byhand/Arduino/Bare/Bare.asm";
		genTestsandTranslate(asmspec,1);
	}

	@Test
	public void testPillBox() throws Exception {
		String asmspec = "D:\\AgHome\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\pillbox.asm";
		genTestsandTranslate(asmspec,1);
	}

	
	
	@Test
	public void test1() throws Exception {
		String asmspec = "examples/asmeta_examples/Counter.asm";
		genTestsandTranslate(asmspec,10);
	}

	@Test
	public void testAdvancedClock() throws Exception {
		String asmspec = "examples/AdvancedClock.asm";
		genTestsandTranslate(asmspec,30);
	}
	
	@Test
	public void test3() throws Exception {
		String asmspec = "../asmetal2cpp_codegen/examples/coffeeVendingMachineNC.asm";
		genTestsandTranslate(asmspec,5);
	}

	
	@Test
	public void test4() throws Exception {
		String asmspec = "examples/sluiceGateGround.asm";
		genTestsandTranslate(asmspec,5);
	}
	
	@Test
	public void test5() throws Exception {
		String asmspec = "examples/euclideMCD_init.asm";
		genTestsandTranslate(asmspec,5);
	}

	@Test
	public void test6() throws Exception {
		String asmspec = "examples/ProdDomain.asm";
		genTestsandTranslate(asmspec,5);
	}
	@Test
	public void test7() throws Exception {
		String asmspec = "examples/MapDomain.asm";
		genTestsandTranslate(asmspec,5);
	}
	
	public static void genTestsandTranslate(String asmspec, int stepNumber) throws Exception {
		AsmCollection asm = ExampleTaker.getExample(asmspec);
		AsmTestGeneratorBySimulation tg = new AsmTestGeneratorBySimulation(asm,stepNumber, 1);
		AsmTestSuite ts = tg.getTestSuite();
		for (AsmTestSequence tsq : ts.getTests()) {
			List<Map<Location, String>> list = tsq.allInstructions();
			System.out.println("================ init - s0");
			for (int i = 0; i < list.size(); i++) {
				Map<Location, String> state = list.get(i);
				for (Location loc: state.keySet()) {
					assert loc.getVarKind() != null;
				}
				System.out.println(state);
				System.out.println("======" +(i+1));
			}
		}
	}

}
