package org.asmeta.animator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.asmeta.animator.VisualizationSimulation;
import org.junit.Test;

/*
 * Update inconsistenti
 * Violazione invarianti
 */
public class AsmetaAnimatorTest { 

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, Exception {
	//	testMixGenerateeuclideInit();
		testMixHemodialysis();
	}

	@Test
	public  void testLight() throws FileNotFoundException, IOException, InterruptedException, Exception {
		String asmspec = "examples/light.asm";
		testAnimator(asmspec);
	}

	
	@Test
	public  void testMixCoffeeVendingMachineNC() throws FileNotFoundException, IOException, InterruptedException, Exception {
		String asmspec = "examples/coffeeVendingMachineNC.asm";
		testAnimator(asmspec);
	}

	
	public static void testMixHemodialysis() throws IOException, Exception {
		String asmspec = "examples/Hemodialysis_ref4_forMC.asm";
		testAnimator(asmspec);
	}
	
	
	@Test
	public void testMixGenerateATM3() throws IOException, Exception {
		String asmspec = "examples/ATM3.asm";
		testAnimator(asmspec);
	}
	
	@Test
	public void testMixGenerateeuclideInit() throws IOException, Exception {
		String asmspec = "examples/euclideMCD_init.asm";
		testAnimator(asmspec);
	}

	
	
	/*public void testGenerate0() throws Exception {
		testAnimator("examples/Bare/Bare.asm", 10);
	}

	public void testGenerateCounter() throws Exception {
		testAnimator("examples/asmeta_examples/Counter.asm", 10);
	}

	public void testGenerateBasicDomain() throws Exception {
		String asmspec = "examples/BasicDomain.asm";
		testAnimator(asmspec, 1);
		// to compile g++ -std=c++11 AdvancedClock.cpp test.cpp -O0 -g3 -Wall -c
		// -fmessage-length=0 -MMD -MP -MF -lboost_unit_test_framework
	}

	public void testGenerateClock() throws Exception {
		String asmspec = "examples/AdvancedClock.asm";
		testAnimator(asmspec, 80);
	}

	public void testGenerateCoffee() throws Exception {
		String asmspec = "examples/coffeeVendingMachine.asm";
		testAnimator(asmspec, 30);
	}

	public void testGenerateCoffeeNC() throws Exception {
		String asmspec = "examples/coffeeVendingMachineNC.asm";
		testAnimator(asmspec, 5);
	}

	public void testGenerateSIS() throws Exception {
		String asmspec = "examples/SIS.asm";
		testAnimator(asmspec, 20);
	}

	public void testGenerateATM3() throws IOException, Exception {
		String asmspec = "examples/ATM3.asm";
		testAnimator(asmspec, 10);
	}

	public void testGenerateHemodialysis() throws IOException, Exception {
		String asmspec = "examples/Hemodialysis_ref4_forMC.asm";
		testAnimator(asmspec, 50);
	}

	public void testGenerateeuclide() throws Exception {
		String asmspec = "examples/euclideMCD.asm";
		testAnimator(asmspec, 10);
	}

	public void testGenerateFattoriale() throws IOException, Exception {
		String asmspec = "examples/fattoriale.asm";
		testAnimator(asmspec, 5);
	}

	public void testGenerateFibonacci() throws IOException, Exception {
		String asmspec = "examples/fibonacci.asm";
		testAnimator(asmspec, 5);
	}

	public void testGeneratePopulation_raff1_twoPersons() throws IOException, Exception {
		String asmspec = "examples/population_raff1_twoPersons.asm";
		testAnimator(asmspec, 20);
	}

	public void testGenerateQuickSort() throws IOException, Exception {
		String asmspec = "examples/QuickSort.asm";
		testAnimator(asmspec, 10);
	}
*/
	
	public static void testAnimator(String specpath)
			throws Exception, FileNotFoundException, IOException, InterruptedException {

		//AsmCollection asm = ExampleTaker.getExample(specpath);
	//	String asmPath = ExampleTaker.getAsmFile(specpath).getAbsolutePath();

		VisualizationSimulation.showView(new File(specpath));
		// geneate the unit test
		//AsmToGraphicalR trans = new AsmToGraphicalR();
		// test name
		// String testPath = "temp/test.cpp";
	//	trans.generate(asm);
		// generate also the spec
		// header
	}

	
	/*public static void testAnimator(String specpath, SimulationType simType)
			throws Exception, FileNotFoundException, IOException, InterruptedException {

		AsmCollection asm = ExampleTaker.getExample(specpath);
		String asmPath = ExampleTaker.getAsmFile(specpath).getAbsolutePath();

		// geneate the unit test
		AsmToGraphicalR trans = new AsmToGraphicalR();
		// test name
		// String testPath = "temp/test.cpp";
		trans.generate(asm);
		// generate also the spec
		// header
	}*/

/*	public static void testAnimator(String specpath)
			throws Exception, FileNotFoundException, IOException, InterruptedException {

		AsmCollection asm = ExampleTaker.getExample(specpath);
		String asmPath = ExampleTaker.getAsmFile(specpath).getAbsolutePath();

		// geneate the unit test
		AsmToGraphicalR trans = new AsmToGraphicalR();
		// test name
		// String testPath = "temp/test.cpp";
		trans.generate(asm);
		// generate also the spec
		// header
	}*/

}
