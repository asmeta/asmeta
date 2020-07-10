package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class GeneratorCompilerTestSingleFile extends GeneratorCompilerTest2 {


	/*
	 * @Test public void testBare() throws IOException, Exception { String asmspec =
	 * "examples/Bare/Bare.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * @Test public void testBasicDomain() throws IOException, Exception { String
	 * asmspec = "examples/BasicDomain.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * @Test public void testAdvancedClock() throws IOException, Exception { String
	 * asmspec = "examples/AdvancedClock.asm"; if (!test(asmspec, options).success) fail(); }
	 */

	@Test
	public void testCoffeeVendingMachine() throws IOException, Exception {
		String asmspec = "examples/coffeeVendingMachine.asm";
		if (!test(asmspec,options).success)
			fail();
	}

	@Test
	public void testCoffeeVendingMachineNC() throws IOException, Exception {
		String asmspec = "examples/coffeeVendingMachineNC.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testSIS() throws IOException, Exception {
		String asmspec = "examples/SIS.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testATM3() throws IOException, Exception {
		String asmspec = "examples/ATM3.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testmoncontr() throws IOException, Exception {
		String asmspec = "examples/moncontr.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testFLIPFLOP() throws IOException, Exception {
		String asmspec = "examples/FLIP_FLOP_0.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testSetDomain() throws IOException, Exception {
		String asmspec = "examples/SetDomain.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testMapDomain() throws IOException, Exception {
		String asmspec = "examples/MapDomain.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	

	@Test
	public void testFlipFlop() throws IOException, Exception {
		String asmspec = "examples/FLIP_FLOP_0.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testHemodialysis() throws IOException, Exception {
		String asmspec = "examples/Hemodialysis_ref4_forMC.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testEuclide() throws IOException, Exception {
		String asmspec = "examples/euclideMCD.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testFattoriale() throws IOException, Exception {
		String asmspec = "examples/fattoriale.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testFibonacci() throws IOException, Exception {
		String asmspec = "examples/fibonacci.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testPopulation_raff1_twoPersons() throws IOException, Exception {
		String asmspec = "examples/population_raff1_twoPersons.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	@Test
	public void testsluiceGate() throws IOException, Exception {
		String asmspec = "examples/sluiceGateGround.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testSort() throws IOException, Exception {
		String asmspec = "examples/QuickSort.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testExtend() throws IOException, Exception {
		String asmspec = "examples/ExtendRule1.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	
	@Test
	public void testRoulette() throws IOException, Exception {
		String asmspec = "examples/roulette.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	
	@Test
	public void testSwapSort() throws IOException, Exception {
		String asmspec = "examples/SwapSort.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	

	@Test
	public void testPopulation() throws IOException, Exception {
		String asmspec = "examples/population.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testAbstractDom() throws IOException, Exception {
		String asmspec = "examples/AbstractDom.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	
	@Test
	public void testProdDomain() throws IOException, Exception {
		String asmspec = "examples/ProdDomain.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testgameOfLife() throws IOException, Exception {
		String asmspec = "examples/gameOfLife.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	
	@Test
	public void testLGSHM() throws IOException, Exception {
		String asmspec = "examples/LGS_HM.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testLGS3L() throws IOException, Exception {
		String asmspec = "examples/LGS_3L.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testcounter() throws IOException, Exception {
		String asmspec = "examples/asmeta_examples/Counter.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	@Test
	public void testPB() throws IOException, Exception {
		String asmspec = "../../../../asm_examples/PillBox/Level0/pillbox_0.asm";
		if (!test(asmspec, options).success)
			fail();
	}
	
	
	@Test
	public void testNot() throws IOException, Exception {
		String asmspec = "examples/notTest.asm";
		if (!test(asmspec, options).success)
			fail();
	}

	
	/*
	 * @Test public void testQuickSort() throws IOException, Exception { String
	 * asmspec = "examples/QuickSort.asm"; if (!test(asmspec, options).success) fail(); }
	 */
 
	@Test
	public void testAll() throws IOException, Exception {
		List<File> allUasmFiles = new ArrayList<>();
		List<CompileResult> results = new ArrayList<>();
		listf("examples", allUasmFiles);
		for (File f : allUasmFiles) {
			results.add(test(f.getPath(),options));
		}
		for (int i = 0; i < allUasmFiles.size(); i++) {
			File f = allUasmFiles.get(i);
			System.out.println(f.getName() + " --> " + results.get(i));
		}
		double avg = 0;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).success)
				avg = avg + 1;
		}
		System.out.println("SUCCESS RATE: " + (avg * 100) / results.size() + "%");
	}
}
