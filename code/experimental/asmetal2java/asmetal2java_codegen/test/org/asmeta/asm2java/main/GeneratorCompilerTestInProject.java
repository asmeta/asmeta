package org.asmeta.asm2java.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2java.compiler.CompileResult;
//import org.asmeta.asm2java.main.JavaGenerator;
//import org.asmeta.asm2java.main.JavaExeGenerator;
//import org.asmeta.asm2java.main.JavaWindowGenerator;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;

// generates the code in a eclispe project and it does not compile it
// eclipse will take care of the compilation

public class GeneratorCompilerTestInProject {

	private static final String SRC_GEN = "../asmetal2java_examples/src/";

	private TranslatorOptions options = new TranslatorOptions(true, true, true);

	private static final Path SRC_GEN_path = Path.of(SRC_GEN);

	@BeforeClass
	static public void checkExistsPath() {
		File dest = new File(SRC_GEN);
		assertTrue(dest.exists() && dest.isDirectory());
		assertTrue(Files.exists(SRC_GEN_path));
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// -----
	// -----
	// Elenco dei casi di test risolti dall'applicazione
	// -----
	// -----
	// -------------------------------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testBasicDomain() throws IOException, Exception {
		String asmspec = "examples/RegistroDiCassa.asm";
		if (!GeneratorCompilerUtil.genandcompile(asmspec, options, SRC_GEN_path, null).getSuccess())
			fail();
	}

	@Test
	public void testDado() throws IOException, Exception {
		String asmspec = "examples/QuickSort.asm";
		if (!GeneratorCompilerUtil.genandcompile(asmspec, options, SRC_GEN_path, null).getSuccess())
			fail();
	}

	@Test
	public void testConcreteDom2Init() throws IOException, Exception {
		String asmspec = "examples/ConcreteDom2Init.asm";
		CompileResult test = GeneratorCompilerUtil.genandcompile(asmspec, options, SRC_GEN_path, null);
		if (!test.getSuccess())
			fail();
		// get the java
		File javaTrans = new File("examples/compilazione/ConcreteDom2Init.java");
		assertTrue(javaTrans.exists());
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testAdvancedClock() throws IOException, Exception { String
	 * asmspec = "examples/AdvancedClock.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * @Test public void testSignature() throws IOException, Exception { String
	 * asmspec = "examples/testSignature.asm"; if (!test(asmspec,options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * @Test public void testCoffeeVendingMachineNC() throws IOException, Exception
	 * { String asmspec = "examples/coffeeVendingMachineNC.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * 
	 * @Test public void testtestDefinition() throws IOException, Exception { String
	 * asmspec = "examples/testDefinition.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * @Test public void testtestDefinition2() throws IOException, Exception {
	 * String asmspec = "examples/testDefinition2.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * @Test public void testtestDefinition3() throws IOException, Exception {
	 * String asmspec = "examples/testDefinition3.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * @Test public void testtestDefinition4() throws IOException, Exception {
	 * String asmspec = "examples/testDefinition4.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * @Test public void testStaticFun() throws IOException, Exception { String
	 * asmspec = "examples/testStaticFun.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * @Test public void testCoffeeVendingMachine() throws IOException, Exception {
	 * String asmspec = "examples/coffeeVendingMachine.asm"; if
	 * (!test(asmspec,options).success) fail(); }
	 * 
	 * 
	 * @Test public void testSIS() throws IOException, Exception { String asmspec =
	 * "examples/SIS.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * @Test public void testATM3() throws IOException, Exception { String asmspec =
	 * "examples/ATM3.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * @Test public void testmoncontr() throws IOException, Exception { String
	 * asmspec = "examples/moncontr.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testMapDomain() throws IOException, Exception { String
	 * asmspec = "examples/MapDomain.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * @Test public void testFlipFlop() throws IOException, Exception { String
	 * asmspec = "examples/FLIP_FLOP_0.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * @Test public void testHemodialysis() throws IOException, Exception { String
	 * asmspec = "examples/Hemodialysis_ref4_forMC.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * @Test public void testEuclide() throws IOException, Exception { String
	 * asmspec = "examples/euclideMCD.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * @Test public void testFattoriale() throws IOException, Exception { String
	 * asmspec = "examples/fattoriale.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testsluiceGate() throws IOException, Exception { String
	 * asmspec = "examples/sluiceGateGround.asm"; if (!test(asmspec,
	 * options).success) fail(); }
	 * 
	 * 
	 * @Test public void testAbstractDom() throws IOException, Exception { String
	 * asmspec = "examples/AbstractDom.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * @Test public void testProdDomain() throws IOException, Exception { String
	 * asmspec = "examples/ProdDomain.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testLGSHM() throws IOException, Exception { String asmspec
	 * = "examples/LGS_HM.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * @Test public void testLGS3L() throws IOException, Exception { String asmspec
	 * = "examples/LGS_3L.asm"; if (!test(asmspec, options).success) fail(); }
	 */

	/*
	 * //Da sistemare, scrive il codice ma sbagliato!
	 * 
	 * @Test public void testSwapSort() throws IOException, Exception { String
	 * asmspec = "examples/SwapSort.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 */

	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// -----
	// -----
	// Elenco di casi da risolvere dell'applicazione
	// -----
	// -----
	// -------------------------------------------------------------------------------------------------------------------------------------------------

	/*
	 *
	 * 
	 * //Classe vuota
	 * 
	 * @Test public void testBare() throws IOException, Exception { String asmspec =
	 * "examples/Bare/Bare.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * 
	 */

	/*
	 * Non traduce correttamente la parte definition //Classe vuota
	 * 
	 * @Test public void testSetDomain() throws IOException, Exception { String
	 * asmspec = "examples/SetDomain.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 */

	/*
	 * Non traduce correttamente la parte definition //Classe vuota
	 * 
	 * @Test public void testFibonacci() throws IOException, Exception { String
	 * asmspec = "examples/fibonacci.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 */

	/*
	 * Non traduce correttamente la parte definition //Classe vuota
	 * 
	 * @Test public void testSort() throws IOException, Exception { String asmspec =
	 * "examples/QuickSort.asm"; if (!test(asmspec, options).success) fail(); }
	 */

	/*
	 * Non traduce correttamente la parte definition
	 * 
	 * ////Classe vuota
	 * 
	 * @Test public void testPopulation() throws IOException, Exception { String
	 * asmspec = "examples/population.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * 
	 * // Non traduce correttamente la parte definition //Classe vuota
	 * 
	 * @Test public void testgameOfLife() throws IOException, Exception { String
	 * asmspec = "examples/gameOfLife.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 * 
	 * //Classe vuota
	 * 
	 * @Test public void testLIFT() throws IOException, Exception { String asmspec =
	 * "examples/LIFT.asm"; if (!test(asmspec, options).success) fail(); }
	 * 
	 * //Classe vuota
	 * 
	 * @Test public void testCashPoint() throws IOException, Exception { String
	 * asmspec = "examples/CashPoint.asm"; if (!test(asmspec, options).success)
	 * fail(); }
	 */

	@Test
	public void testAll() throws IOException, Exception {

		System.out.println("CASO TEST ALL \n\n\n\n");
		List<File> allUasmFiles = new ArrayList<>();
		List<CompileResult> results = new ArrayList<>();

		listf("examples", allUasmFiles);

		allUasmFiles.toString();
		for (File asmspec : allUasmFiles) {
			results.add(GeneratorCompilerUtil.genandcompile(asmspec.toString(), options, SRC_GEN_path, null));
		}

		System.out.println("\n\n\n\n\n");
		System.out.println("Elenco dei casi di test con relativi risultati");
		System.out.println("\n\n");

		for (int i = 0; i < allUasmFiles.size(); i++) {
			File f = allUasmFiles.get(i);
			System.out.println(f.getName() + " --> " + results.get(i));
		}
		double avg = 0;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getSuccess())
				avg = avg + 1;
		}
		System.out.println("\n\n\n");
		System.out.println("SUCCESS RATE: " + (avg * 100) / results.size() + "%");
		System.out.println("\n\n\n");
	}

	// resturn all the file in a directory
	void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);

		File[] fList = directory.listFiles();

		for (File file : fList) {

			if (!file.getName().equals("StandardLibrary.asm")) {
				if (file.isFile() && file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
					files.add(file);
				} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
				}
			}
		}

	}
}
