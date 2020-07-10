package org.asmeta.tocpp.tocunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

// asm to boost module
public class AsmToBoostModule {
	
	private AsmCollection asm;
	AsmTestSuite testsuite;
	
	/**
	 * 
	 * @param testsuite test generator (Nusmv or simulator)
	 * @param asmPath the complete path of the ASM
	 * @param asm 
	 */
	AsmToBoostModule(AsmTestSuite testsuite, AsmCollection asm, String asmPath){
		this.testsuite = testsuite; 
		this.asm = asm;
		
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
		AsmTsToBOOSTModule translator = new AsmTsToBOOSTModule(asm);
		CharSequence result = translator.convertTestSuite(testsuite);
		return result;
	}

	public AsmTestSuite generatedTests() {return testsuite;}
	
}
