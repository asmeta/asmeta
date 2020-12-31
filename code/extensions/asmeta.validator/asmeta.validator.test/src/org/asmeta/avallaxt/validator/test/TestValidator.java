package org.asmeta.avallaxt.validator.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;
import org.junit.BeforeClass;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;

import asmeta.AsmCollection;

public class TestValidator {

	static int i = 0;

	public TestValidator() {
		super();
	}

	@BeforeClass
	public static void cleanup(){
		i = 0;
		File dir = new File("temp");
		assert dir.exists() && dir.isDirectory();
		// clean directory
		for(File file: dir.listFiles())
		    if (file.getName().endsWith(".asm")) 
		        file.delete();		
	}
	/**
	 * 
	 * @param scenarioPath
	 * @param runValidator
	 * @throws IOException
	 * @throws Exception
	 */
	protected void test(String scenarioPath, boolean runValidator) throws IOException, Exception {
		if (runValidator) {
			// it should be runnable
			AsmetaV.execValidation(scenarioPath, false);
		} else {
			//
			System.out.println("transating " + scenarioPath);
			String tempAsmPath = "temp/temp_spec" + (i++) + ".asm";
			// delete if exists
			Path path_tempAsm = Paths.get(tempAsmPath);
			assert ! Files.exists(path_tempAsm);
			org.asmeta.xt.validator.AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(scenarioPath, tempAsmPath);
			builder.save();
			// the files exists
			assertTrue(Files.exists(path_tempAsm));
			// it should be parsable:
			AsmCollection asmc = ASMParser.setUpReadAsm(new File(tempAsmPath.toString()));
			System.out.println(ASMParser.getResultLogger().errors);
			assertNotNull(asmc);
		}
	
	}

}