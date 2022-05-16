package org.asmeta.avallaxt.validator.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.junit.BeforeClass;

import asmeta.AsmCollection;

public class TestValidator {

	static int i = 0;

	static String pathname = "temp/";

	
	public TestValidator() {
		super();
	}

	@BeforeClass
	public static void cleanup(){
		i = 0;
		File dir = new File(pathname);
		assert dir.exists() && dir.isDirectory();
		// clean directory
		for(File file: dir.listFiles()) {
		    if (file.getName().endsWith(".asm")) 
		        file.delete();
		    if (file.isDirectory()) {
		    	file.delete();
		    }
		}
	}

	protected void test(String scenarioPath) throws IOException, Exception {
		test(scenarioPath,false,false);
		test(scenarioPath,true,false);
	}

	/**
	 * 
	 * @param scenarioPath
	 * @param runValidator
	 * @param computeCoverage TODO
	 * @throws IOException
	 * @throws Exception
	 */
	protected void test(String scenarioPath, boolean runValidator, boolean computeCoverage) throws IOException, Exception {
		if (runValidator) {
			System.out.println("executing " + scenarioPath);
			// it should be runnable
			List<String> result = AsmetaV.execValidation(scenarioPath, computeCoverage);
			assertTrue("failed " + result, result.isEmpty());
		} else {
			//
			System.out.println("translating " + scenarioPath);
			File tempAsmPath = new File("temp"); //Files.createTempFile("__tempAsmetaV", ".asm").toFile();
			// delete if exists
			org.asmeta.xt.validator.AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(scenarioPath, tempAsmPath);
			builder.save();
			// the files exists
			assertTrue(tempAsmPath.exists());
			assertTrue(builder.getTempAsmPath().exists() && builder.getTempAsmPath().isFile() && builder.getTempAsmPath().getName().endsWith(".asm"));
			// it should be parsable:
			AsmCollection asmc = ASMParser.setUpReadAsm(builder.getTempAsmPath());
			System.out.println(ASMParser.getResultLogger().errors);
			assertNotNull(asmc);
		}
	
	}

}