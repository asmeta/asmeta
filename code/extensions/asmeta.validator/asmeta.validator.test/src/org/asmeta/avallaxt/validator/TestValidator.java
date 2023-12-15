package org.asmeta.avallaxt.validator;

import static org.junit.Assert.assertFalse;
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

	protected static final String ASM_EXAMPLES = "../../../../asm_examples/";

	protected static final String ASM_EXAMPLES_EXAMPLES = ASM_EXAMPLES +"examples/";
	
	private static String pathname = "temp/";

	
	public TestValidator() {
		super();
	}

	@BeforeClass
	public static void testExamplesDir() throws IOException{
		assertTrue(new File(ASM_EXAMPLES).exists());
		assertTrue(new File(ASM_EXAMPLES_EXAMPLES).exists());
	}

	
	@BeforeClass
	public static void cleanup() throws IOException{
		File dir = new File(pathname);
		// if it exists is a directory
		assert ! dir.exists() || dir.isDirectory();
		// if it does not exist, create it
		if (!dir.exists()) {
			assertTrue(dir.mkdir());
		}
		assert dir.exists() && dir.isDirectory();
		// clean directory
		for(File file: dir.listFiles()) {
		    if (file.getName().endsWith(ASMParser.ASM_EXTENSION)) 
		        file.delete();
		    if (file.isDirectory()) {
		    	file.delete();
		    }
		}
	}
	// test with and wthout the validator
	protected void test(String scenarioPath) throws IOException, Exception {
		// without the execution
		test(scenarioPath,false,false, true);
		//( with the execution of the validator
		test(scenarioPath,true,false, true);
	}

	/**
	 * 
	 * @param scenarioPath
	 * @param runValidator
	 * @param computeCoverage TODO
	 * @param expectedSuccess TODO
	 * @throws IOException
	 * @throws Exception
	 */
	protected void test(String scenarioPath, boolean runValidator, boolean computeCoverage, boolean expectedSuccess) throws IOException, Exception {
		if (runValidator) {
			System.out.println("executing " + scenarioPath);
			// it should be runnable
			List<String> result = AsmetaV.execValidation(scenarioPath, computeCoverage);
			if (expectedSuccess) assertTrue("failed " + result, result.isEmpty());
			else assertFalse(scenarioPath + " must fail but it is not", result.isEmpty()); 
		} else {
			//
			System.out.println("translating " + scenarioPath);
			File tempAsmPath = new File("temp"); //Files.createTempFile("__tempAsmetaV", ASMParser.asmExtension).toFile();
			// delete if exists
			org.asmeta.xt.validator.AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(scenarioPath, tempAsmPath);
			builder.save();
			// the files exists
			assertTrue(tempAsmPath.exists());
			assertTrue(builder.getTempAsmPath().exists() && builder.getTempAsmPath().isFile() && builder.getTempAsmPath().getName().endsWith(ASMParser.ASM_EXTENSION));
			// it should be parsable:
			AsmCollection asmc = ASMParser.setUpReadAsm(builder.getTempAsmPath());
			System.out.println(ASMParser.getResultLogger().errors);
			assertNotNull(asmc);
		}
	
	}

}