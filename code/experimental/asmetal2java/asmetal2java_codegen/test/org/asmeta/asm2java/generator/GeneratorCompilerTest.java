package org.asmeta.asm2java.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.compiler.CompileResultImpl;
import org.asmeta.asm2java.compiler.Compiler;
import org.asmeta.asm2java.compiler.CompilerImpl;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.asmeta.asm2java.generator.*;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;
import asmeta.AsmCollection;

public class GeneratorCompilerTest {

	private static final String SRC_GEN = "../asmetal2java_examples/src/";

	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();
	// static private JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	// static private JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();

	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);
	
	/**
	 * List of libraries that should be excluded from testing
	 */
	static List<String> libraries = List.of(
			"StandardLibrary.asm",
			"LTLLibrary.asm",
			"CTLLibrary.asm");
	
	/**
	 * List of asm files with known issues:
	 * these files have compilation errors related to the translation.
	 */
	static List<String> errors = List.of(
			"battleship.asm",
			"fibonacci.asm",
			"QuickSort.asm", 
			"testSignature.asm",
			"SIS.asm",
			"testDefinition3.asm");
	
	/**
	 * The following files have compilation errors already in the .asm file 
	 * and asmetal2java correctly throws exceptions
	 */
	static List<String> parseException = List.of(/* Empty */);
	
	/**
	 * The following classes have runtime errors
	 */
	static List<String> runtimeException = List.of(
			"gameOfLife.asm", 
			"groundModel_v2.asm",
			"Bare.asm",
			"ProdDomain.asm",
			"population.asm",
			"LIFT.asm");
	
	// -------------------------------------------------------------------------------------------------------------------------------------------------
	// -----
	// -----
	// Elenco dei casi di test risolti dall'applicazione
	// -----
	// -----
	// -------------------------------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testBasicDomain() throws Exception {
		String asmspec = "examples/RegistroDiCassa.asm";
		if (!test(asmspec, options).getSuccess())
			fail();
	}

	@Test
	public void testDado() throws Exception {
		String asmspec = "examples/dado.asm";
		if (!test(asmspec, options).getSuccess())
			fail();
	}
	
	/*
	@Test
	public void testQuickSort() throws IOException, Exception {
		String asmspec = "examples/QuickSort.asm";
		if (!test(asmspec, options).getSuccess())
			fail();
	}
	*/
	
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
		for (File f : allUasmFiles) {
			results.add(test(f.getPath(), options));
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

			if (!exclude(file.getName())) {
				if (file.isFile() && file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
					files.add(file);
				} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
				}
			}
		}

	}
	
	/**
	 * check if the current file should be excluded from testing.
	 * 
	 * @param fileName the name of the file to check
	 * @return {@code true} if the file is to exclude, {@code false} otherwise.
	 */
	private boolean exclude(String fileName) {
		return libraries.contains(fileName) ||
				parseException.contains(fileName) ||
				runtimeException.contains(fileName) ||
				errors.contains(fileName);
	}
	
	@BeforeClass
	static public void checkExistsPath() {
		File dest = new File(SRC_GEN);
		assertTrue(dest.exists() && dest.isDirectory());
	}
	

	/**
	 * 
	 * @param <jGenerator>
	 * @param asmspec      the path of the spec
	 * @return true if success
	 * 
	 * @throws Exception
	 */

	public static CompileResult test(String asmspec, TranslatorOptions userOptions) throws Exception {
		//
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser

		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		File dir = asmFile.getParentFile();
		assert dir.exists() && dir.isDirectory();

		String dirCompilazione = asmFile.getParentFile().getPath() + "/compilazione";
//		String dirEsecuzione = asmFile.getParentFile().getPath() + "/esecuzione";
//		String dirWin = asmFile.getParentFile().getPath() + "/window";
//		
//		String dirTraduzione = asmFile.getParentFile().getPath() + "/Traduzione";

		
		// AC
		File javaFile = new File(SRC_GEN + File.separator + name + ".java");
//		File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");
//		File javaFileExe = new File(dirEsecuzione + File.separator + name + "_Exe.java");
//		File javaFileExeN = new File(dirEsecuzione + File.separator + name + ".java");
//		File javaFileWin = new File(dirWin + File.separator + name + "_Win.java");
//		File javaFileWinN = new File(dirWin + File.separator + name + ".java");
//		
//		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");
//		File javaFileExeT = new File(dirTraduzione + File.separator + name + "_Exe.java");
//		File javaFileWinT = new File(dirTraduzione + File.separator + name + "_Win.java");

		// Check if the file exists and delete it
		deleteExisting(javaFile);
		deleteExisting(javaFileCompilazione);
		
		/*
		deleteExisting(javaFileExe);
		deleteExisting(javaFileExeN);
		deleteExisting(javaFileWin);
		deleteExisting(javaFileWinN);
		deleteExisting(javaFileT);
		deleteExisting(javaFileExeT);
		deleteExisting(javaFileWinT);
		*/

		System.out.println("\n\n===" + name + " ===================");

		try {

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResultImpl(false, e.getMessage());
		}

		// write java

		try {
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(), javaFileCompilazione.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExe.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileExeN.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWin.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileWinN.getCanonicalPath(), userOptions);

//			jGenerator.compileAndWrite(model.getMain(), javaFileT.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExeT.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWinT.getCanonicalPath(), userOptions);

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResultImpl(false, e.getMessage());
		}

		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
		System.out.println("Generated java file: " + javaFileCompilazione.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileExeN.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileWinN.getCanonicalPath());
//		System.out.println("Generated java file for the execution: " + javaFileExe.getCanonicalPath());
//		System.out.println("Generated java file for window: " + javaFileWin.getCanonicalPath());

//		System.out.println("All java files Generated in: " + javaFileT.getCanonicalPath());

		Compiler compiler = new CompilerImpl();
		return compiler.compileFile(name + ".java", Paths.get(dirCompilazione), true, "17");
	}
	
	
	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 * @throws IOException if an IO error occurs.
	 */
	private static void deleteExisting(File file) throws IOException {
		if (file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
				fail("File not found : " + file.getPath());
			}
		}
		assert !file.exists();
	}

}