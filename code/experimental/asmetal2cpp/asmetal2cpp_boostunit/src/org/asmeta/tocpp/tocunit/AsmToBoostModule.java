package org.asmeta.tocpp.tocunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.asmeta.tocpp.tocunit.AsmToUnitModuleTest.UNITFM;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

// asm to boost module
public class AsmToBoostModule {
	
	private AsmCollection asm;
	AsmTestSuite testsuite;
	private UNITFM unitfm;
	
	/**
	 * 
	 * @param testsuite test suite
	 * @param asmPath the complete path of the ASM
	 * @param asm 
	 * @param unitfm 
	 */
	AsmToBoostModule(AsmTestSuite testsuite, AsmCollection asm, String asmPath, UNITFM unitfm){
		this.testsuite = testsuite; 
		this.asm = asm;
		this.unitfm = unitfm;
	}
	/**
	 * 
	 * @param testPath where to save the test cpp file
	 * @throws FileNotFoundException
	 */
	public void generateAndSave(String testPath) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(testPath);
		writer.println(generate());
		writer.println();
		writer.close();
		if (!new File(testPath).exists())
			throw new RuntimeException("unable to build the c++ boost unit file");
	}
	
	private CharSequence generate() {
		// translate to the boost module
		TestSuiteTranslator translator = null;
		if (unitfm == UNITFM.BOOST) {
			translator = new AsmTsToBOOSTModule(asm);
		} else if (unitfm == UNITFM.CATCH2) {
			translator = new AsmTsToCatch2Test(asm);
		} else {
			throw new RuntimeException("which translator");
		}
		CharSequence result = translator.convertTestSuite(testsuite);
		return result;
	}

	public AsmTestSuite generatedTests() {return testsuite;}
	
}
