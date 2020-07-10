package org.asmeta.tocpp.tocunit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.tocpp.abstracttestgenerator.AsmTestGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;

public class Experiment extends AsmToBoostModuleTest{

	@BeforeClass
	public static void setUpLogger() {
		Logger.getLogger(Simulator.class).setLevel(Level.OFF);
		Logger.getLogger(org.asmeta.parser.ASMParser.class).setLevel(Level.ALL);
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
	}
	
	@Test
	public void experiments() throws Exception {
		//** Hemodialysis_ref4_forMC.asm
		//testSpec("examples/Hemodialysis_ref4_forMC.asm", NuSMV); // -> fail
		//testSpec("examples/Hemodialysis_ref4_forMC.asm", SIMULATOR,"100","10");
		//testSpec("examples/Hemodialysis_ref4_forMC.asm", SIMULATOR,"1000","10");// fail 
		//DEBUG smeta.asm2code.compiler.CppCompiler  - cc1plus: out of memory allocating 536936447 bytes after a total of 38404096 bytes
		//** AdvancedClock
		//testSpec("examples/AdvancedClock.asm", NuSMV);
		//testSpec("examples/AdvancedClock.asm", SIMULATOR,"523","7");
		//testSpec("examples/AdvancedClock.asm", SIMULATOR,"3600","7");
		//testSpec("examples/AdvancedClock.asm", SIMULATOR,"10","1");
		//** coffeeVendingMachineNC
		//testSpec("examples/coffeeVendingMachineNC.asm", NuSMV);
		//testSpec("../asmetal2cpp_codegen/examples/coffeeVendingMachineNC.asm", SIMULATOR,"8","7");
		//testSpec("examples/pillbox_FULL.asm", SIMULATOR,"8","10");
		testSpec("examples/pillbox_1.asm", NuSMV,"8","10");
		//testSpec("D:\\AgHome\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\pillbox.asm", NuSMV ,"8","7");
		//testSpec("examples/coffeeVendingMachineNC.asm", SIMULATOR,"26","7");
		//** sluiceGateGround --- non funziona to CPP
		// testSpec("examples/sluiceGateGround.asm", NuSMV); --> error
		//testSpec("examples/sluiceGateGround.asm", SIMULATOR,"100","1"); 
		// testSpec("examples/sluiceGateGround.asm", SIMULATOR,"1000","10");
		//** SIS.asm
		//testSpec("examples/SIS.asm", NuSMV);
		//testSpec("examples/SIS.asm", SIMULATOR,"3","32");
		//testSpec("examples/SIS.asm", SIMULATOR,"6","32");
		//testSpec("examples/SIS.asm", SIMULATOR,"1000","10");
		//** oneWayTrafficLight --> NON TRADUCIBILE
		//** certifier_nochoose_noundef 
		//testSpec("examples/certifier_nochoose_noundef.asm", NuSMV);
		//testSpec("examples/certifier_nochoose_noundef.asm", SIMULATOR,"9","80");
		//testSpec("examples/certifier_nochoose_noundef.asm", SIMULATOR,"5","80");
		//** LGS_3L -- ora funziona con anche derivate
		//testSpec("examples/LGS_3L.asm", NuSMV);
		//testSpec("examples/LGS_3L.asm", SIMULATOR,"1000","10");
		//testSpec("examples/LGS_3L.asm", SIMULATOR,"20","1");		
		//** LGS_GM
		//testSpec("examples/LGS_GM.asm", NuSMV);
		//testSpec("examples/LGS_GM.asm", SIMULATOR,"56","8");
		//testSpec("examples/LGS_GM.asm", SIMULATOR,"28","8");
		//** LIFT --> NON TRADUCIBILE (abstract domain)
		//** CashPoint --> NON TRADUCIBILE (abstract domain)
		//** ferrymanSimulator
		//testSpec("examples/ferrymanSimulator.asm", NuSMV);
		//testSpec("examples/ferrymanSimulator.asm", SIMULATOR,"1000","10");
	}	
	
//	@Override
/*	protected void computeCoverageAndWriteData(String specpath, File destDir, AsmTestGenerator tg, AsmToBoostModule trans,
			String specname, long starttime) throws IOException {
		// compute coverage
		//runexample("gcov", destDir, TEST_NAME, "");
		String linecoverage;
		try{
			StringBuffer coverage = runexample("gcov", destDir, specname + ".cpp", "-m");
			linecoverage = Double.toString(anaylzeCoverage(coverage.toString(), specname + ".cpp"));
		} catch (CoverageNotFoundException e) {
			linecoverage = "error"; 
		}
		// 
		AsmTestSuite ts = trans.generatedTests();
		// compute some data about ts
		int toNumSteps = 0, nTests = 0, maxLenght= 0;
		for(AsmTestSequence t: ts.getTests()) {
			int size = t.getContent().allInstructions().size();
			if (size > maxLenght) maxLenght = size;
			toNumSteps += size;
			nTests++;
		}
		String testData = "toNumSteps:" + toNumSteps;
		testData += "\tnTests:" + nTests;  
		testData += "\tmaxLength:" + maxLenght; 
		testData += "\ttime:" + (System.currentTimeMillis() -starttime); 
		testData += "\t" +linecoverage;
		// append to a file containign the results
		Files.write(Paths.get("experiments/results_dec17.txt"), (specpath + "\t" + tg + "\t" + testData +"\n").getBytes(), StandardOpenOption.APPEND);
	}*/

	private double anaylzeCoverage(String output, String cppFile) throws CoverageNotFoundException {
		StringTokenizer st = new StringTokenizer(output,"\n");
		while(st.hasMoreElements()) {
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

	/*protected boolean isCovEnabled() {
		return true;
	}*/
}
