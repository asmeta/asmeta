package org.asmeta.tocpp.tocunit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.asm2code.main.TranslatorOptions;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.tocpp.abstracttestgenerator.AsmTSGeneratorByNuSMV;
import org.asmeta.tocpp.abstracttestgenerator.AsmTestGenerator;
import org.asmeta.tocpp.abstracttestgenerator.AsmTestGeneratorBySimulation;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;
import asmetal2cpp_boostunit.ExampleTaker;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;

public class AsmToUnitModuleTest {

	private static final String TEST_NAME = "test.cpp";
	protected static final String NuSMV = "nusmv";
	protected static final String SIMULATOR = "simulator";
	private static TranslatorOptions userOptions = new TranslatorOptions(false, false, true, false);
	protected static boolean isCovEnabled = false;

	@BeforeClass
	public static void setUpLogger() {
		Logger.getLogger(Simulator.class).setLevel(Level.OFF);
		Logger.getLogger(org.asmeta.parser.ASMParser.class).setLevel(Level.OFF);
		Logger.getLogger(CppCompiler.class).setLevel(Level.OFF);
	}

	@Test
	public void testGenerate0() throws Exception {
		testSpec(UNITFM.BOOST, "examples/Bare/Bare.asm", NuSMV);
	}

	@Test
	public void testTrafficLight() throws Exception {
		testSpec(UNITFM.BOOST, "../../../../asm_examples/examples/traffic_light/oneWayTrafficLight.asm", SIMULATOR, "1",
				"5");
	}

	@Test
	public void testMVM() throws Exception {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		// testSpec(UNITFM.BOOST,
		// "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\Ventilatore000.asm",SIMULATOR,"1",
		// "5");
		testSpec(UNITFM.CATCH2, "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\Ventilatore000.asm",SIMULATOR, "1", "5");
		//testSpec(UNITFM.CATCH2, "F:\\Dati-Andrea\\GitHub\\mvm-asmeta\\VentilatoreASM\\Ventilatore000.asm",SIMULATOR, "1", "5");
	}

	@Test
	public void testGenerateCounter() throws Exception {
		testSpec(UNITFM.BOOST, "examples/asmeta_examples/Counter.asm", SIMULATOR, "1", "5");
	}

	@Test
	public void testFerryMan() throws Exception {
		testSpec(UNITFM.BOOST, "examples/ferrymanSimulator.asm", SIMULATOR, "3", "8");
	}

	@Test
	public void testProdDomain() throws Exception {
		testSpec(UNITFM.BOOST, "examples/ProdDomain.asm", SIMULATOR, "3", "8");
	}

	@Test
	public void testGenerateBasicDomain() throws Exception {
		String asmspec = "examples/BasicDomain.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
		// to compile g++ -std=c++11 AdvancedClock.cpp test.cpp -O0 -g3 -Wall -c
		// -fmessage-length=0 -MMD -MP -MF -lboost_unit_test_framework
	}

	@Test
	public void testGenerateClock() throws Exception {
		String asmspec = "examples/AdvancedClock.asm";
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "3", "8");
	}

	@Test
	public void testGenerateCoffee() throws Exception {
		String asmspec = "examples/coffeeVendingMachine.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateCoffeeNC() throws Exception {
		String asmspec = "../asmetal2cpp_codegen/examples/coffeeVendingMachineNC.asm";
		// testSpec(asmspec, 5, NuSMV);
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "7");
		// testSpec(asmspec, SIMULATOR,"3","7");
	}

	@Test
	public void testmonContr() throws Exception {
		String asmspec = "examples/moncontr.asm";
		// testSpec(asmspec, 5, NuSMV);
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "7");
		// testSpec(asmspec, SIMULATOR,"3","7");
	}

	@Test
	public void testGenerateSIS() throws Exception {
		String asmspec = "examples/SIS.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateATM3() throws IOException, Exception {
		String asmspec = "examples/ATM3.asm";
		// testSpec(asmspec, NuSMV); // non funziona che ha un exists ter,
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "5");
	}

	@Test
	public void testGenerateHemodialysis() throws IOException, Exception {
		String asmspec = "examples/Hemodialysis_ref4_forMC.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateeuclide() throws Exception {
		String asmspec = "examples/euclideMCD.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateFattoriale() throws IOException, Exception {
		String asmspec = "examples/fattoriale.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateFibonacci() throws IOException, Exception {
		String asmspec = "examples/fibonacci.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGeneratePopulation_raff1_twoPersons() throws IOException, Exception {
		String asmspec = "examples/population_raff1_twoPersons.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testGenerateProd() throws IOException, Exception {
		String asmspec = "examples/ProdDomain.asm";
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "5");
	}

	@Test
	public void testGenerateQuickSort() throws IOException, Exception {
		String asmspec = "examples/QuickSort.asm";
		testSpec(UNITFM.BOOST, asmspec, NuSMV);
	}

	@Test
	public void testRoulette() throws Exception {
		String asmspec = "examples/roulette.asm";
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "5");
	}

	@Test
	public void testMapDomain() throws Exception {
		String asmspec = "examples/MapDomain.asm";
		testSpec(UNITFM.BOOST, asmspec, SIMULATOR, "5", "5");
	}

	enum UNITFM {
		BOOST, CATCH2
	}

	/**
	 * 
	 * @param unitfm   TODO
	 * @param specpath
	 * @param tgs
	 * @param options: in case of simulation the number of steps and the number of
	 *                 tests
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void testSpec(UNITFM unitfm, String specpath, String tgs, String... options)
			throws Exception, FileNotFoundException, IOException, InterruptedException {
		// start timer
		// register the starting time
		long starttime = System.currentTimeMillis();
		//
		File destDir = new File("./temp");
		assert destDir.exists() && destDir.isDirectory();
		// load della ASM
		AsmCollection asm = ExampleTaker.getExample(specpath);
		String asmPath = ExampleTaker.getAsmFile(specpath).getAbsolutePath();
		// clean the temp dir (useful to check if the generation worked)
		// clean(destDir.getPath());
		//
		// generate also the code for the machine
		// header
		HeaderGenerator hgen = new HeaderGenerator(userOptions);
		String specname = asm.getMain().getName();
		hgen.generate(asm.getMain(), destDir.getPath() + File.separator + specname + ".h");
		// build the generator
		AsmTestGenerator tg = tgs == SIMULATOR
				? new AsmTestGeneratorBySimulation(asm, Integer.parseInt(options[0]), Integer.parseInt(options[1]))
				: new AsmTSGeneratorByNuSMV(asmPath);
		// generate the test suite with the Generator
		AsmTestSuite testsuite;
		try {
			testsuite = tg.getTestSuite();
		} catch (Throwable e) {
			e.printStackTrace();
			testsuite = AsmTestSuite.getEmptyTestSuite();
		}
		// generate the unit test
		// test name
		String testname = /* specname+ */TEST_NAME;
		String testPath = destDir.getPath() + File.separator + testname;
		boolean useBoost = (unitfm==UNITFM.BOOST ? true : false);
		AsmToBoostModule trans = new AsmToBoostModule(testsuite, asm, asmPath, unitfm);
		trans.generateAndSave(testPath);
		System.out.println("*****" + testPath);
		// compile the test.cpp
		CompileResult result = CppCompiler.compile(testname, destDir.getPath(), true, isCovEnabled, useBoost);
		System.out.println(result);
		// compiled?
		if (result.success) {
			// now the main class
			CppGenerator cppgen = new CppGenerator(userOptions);
			cppgen.generate(asm.getMain(), destDir.getPath() + File.separator + specname + ".cpp");
			//
			// compile the asm.cpp (with the coverage)
			result = CppCompiler.compile(specname + ".cpp", destDir.getPath(), true, isCovEnabled, useBoost);
			System.out.println(result);
			//
			// compiliamo e linkiamo file cpp il tutto (rende un po' inutile quello prima
			result = CppCompiler.compile("*.o", destDir.getPath(), false, isCovEnabled, useBoost);
			System.out.println(result);
			if (result.success) {
				// esegui
				System.out.println("executing test cases");
				// printFilesList();
				String executableName = destDir.getAbsolutePath() + "/a.exe";
				StringBuffer resultexec = runexample(executableName, destDir);
				System.out.println(resultexec.toString());
				if (!resultexec.toString().contains("*** No errors detected") && !resultexec.toString().contains("All tests passed"))
					throw new RuntimeException("test failed " + resultexec.toString());
			} else {
				throw new RuntimeException(".o compiling/linking failure");
			}
		}
		if (isCovEnabled) computeCoverageAndWriteData(specpath, destDir, tg, trans, specname, starttime);
	}


	protected static void computeCoverageAndWriteData(String specpath, File destDir, AsmTestGenerator tg,
			AsmToBoostModule trans, String specname, long starttime) throws IOException {
		// compute coverage
		// runexample("gcov", destDir, TEST_NAME, "");
		String linecoverage;
		try {
			StringBuffer coverage = runexample("gcov", destDir, specname + ".cpp", "-m");
			linecoverage = Double.toString(anaylzeCoverage(coverage.toString(), specname + ".cpp"));
		} catch (CoverageNotFoundException e) {
			linecoverage = "error";
		}
		//
		AsmTestSuite ts = trans.generatedTests();
		// compute some data about ts
		int toNumSteps = 0, nTests = 0, maxLenght = 0;
		for (AsmTestSequence t : ts.getTests()) {
			int size = t.getContent().allInstructions().size();
			if (size > maxLenght)
				maxLenght = size;
			toNumSteps += size;
			nTests++;
		}
		String testData = "toNumSteps:" + toNumSteps;
		testData += "\tnTests:" + nTests;
		testData += "\tmaxLength:" + maxLenght;
		testData += "\ttime:" + (System.currentTimeMillis() - starttime);
		testData += "\t" + linecoverage;
		// append to a file containign the results
		Files.write(Paths.get("experiments/results_dec17.txt"),
				(specpath + "\t" + tg + "\t" + testData + "\n").getBytes(), StandardOpenOption.APPEND);

	}

	private static double anaylzeCoverage(String output, String cppFile) throws CoverageNotFoundException {
		StringTokenizer st = new StringTokenizer(output, "\n");
		while (st.hasMoreElements()) {
			String line = st.nextToken();
			if (line.contains(cppFile)) {
				String coverage = st.nextToken();
				String[] data = coverage.split("[:|\\s|%]");
				System.out.println(Arrays.toString(data));
				return Double.parseDouble(data[2]);
			}
		}
		throw new CoverageNotFoundException();
	}

	private static void clean(String dir) {
		System.out.println("cleaning " + dir);
		File tempDir = new File(dir);
		assert tempDir.exists() && tempDir.isDirectory();
		// delete the files
		for (File f : tempDir.listFiles()) {
			assert f.delete();
		}
	}

	void printFilesList() {
		File folder = new File("./temp");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println("File " + file.getName());
			}
		}
	}

	/**
	 * run the command name and returns the output prodcued by it
	 * 
	 * @return the output
	 * 
	 */
	protected static StringBuffer runexample(String name, File dir, String... extraCommands) {
		StringBuffer result = new StringBuffer();
		List<String> command = new ArrayList<>();
		command.add(name);
		command.addAll(Arrays.asList(extraCommands));
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		builder.directory(dir);
		try {
			Process process = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				result.append(line + "\n");
			}
			r.close();
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Done");
		return result;
	}

}
