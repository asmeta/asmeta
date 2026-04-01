package org.asmeta.avallaxt.validator;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import org.junit.jupiter.api.BeforeAll;

public class TestValidator {

	protected static final String ASM_EXAMPLES = "../../../../asm_examples/";

	protected static final String ASM_EXAMPLES_EXAMPLES = ASM_EXAMPLES +"examples/";
	
	private static String pathname = "temp/";
	
	// keep the trsnslation local for inspection
	protected static File tempAsmPath = new File(pathname); //Files.createTempFile("__tempAsmetaV", ASMParser.asmExtension).toFile();


	private static Logger log = Logger.getLogger(TestValidator.class);

	@BeforeAll
	static void testExamplesDir() throws IOException{
		assertTrue(new File(ASM_EXAMPLES).exists());
		assertTrue(new File(ASM_EXAMPLES_EXAMPLES).exists());
	}


	@BeforeAll
	static void cleanup() throws IOException{
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
		    if (file.getName().endsWith(AsmetaParserUtility.ASM_EXTENSION)) 
		        file.delete();
		    if (file.isDirectory()) {
		    	file.delete();
		    }
		}
	}
	// test with and without the validator but with success
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
			log.debug("executing " + scenarioPath);
			// it should be runnable
			List<String> result = AsmetaV.execValidation(scenarioPath, computeCoverage);
			if (expectedSuccess) assertTrue(result.isEmpty(), "failed " + result);
			else assertFalse(result.isEmpty(), scenarioPath + " must fail but it is not"); 
		} else {
			//
			log.debug("translating " + scenarioPath);
			// delete if exists
			org.asmeta.xt.validator.AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(scenarioPath, tempAsmPath);
			builder.save();
			// the files exists
			assertTrue(tempAsmPath.exists());
			assertTrue(builder.getTempAsmPath().exists() && builder.getTempAsmPath().isFile() && builder.getTempAsmPath().getName().endsWith(AsmetaParserUtility.ASM_EXTENSION));
			// it should be parsable:
			AsmCollection asmc = ASMParser.setUpReadAsm(builder.getTempAsmPath());
			List<String> errors = ASMParser.getResultLogger().errors;
			if (!errors.isEmpty()) log.debug(errors);
			assertTrue(errors.isEmpty());
			assertNotNull(asmc);
		}
	
	}

}