package org.asmeta.avallaxt.validator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.junit.Assert;
import org.junit.Test;

// test all the avallas in some directories (with the validator but not coverage
//
public class TestExampleInDir extends TestValidator {

	// TODO fix these scenarios (or models) - some actually could be there to see that fail
	static final String[] SKIP_AVALLAS_ASM_EXAMPLES = {
			"LowBeamOFFonAmbientLight.avalla", 
			"LowBeamOFFPowerOFFKey.avalla", 
			"trace_scenario1.avalla",
			"trace_scenario2.avalla", 
			"sluiceGateMotorCtl.avalla",
			"lift.avalla"};
	
	@Test
	public void testAllAvallaInAsmExamples() throws Exception {
		// example of example
		//testInDir(ASM_EXAMPLES_EXAMPLES, SKIP_AVALLAS_ASM_EXAMPLES);
	}		
	// TODO fix these scenarios (or models) - some actually could be there to see that fail
	static final String[] SKIP_AVALLAS_EXAMPLES = {
				"completetherapy.avalla", 
				"completeTherapyGround_fail.avalla", 
				"smartHomeNoMultiChannel1.avalla", 
				"error2level56nocert.avalla", 
				"exit.avalla"};
	
	@Test
	public void testAllForExamples() throws Exception {
		// scenario 
		//testInDir("scenariosforexamples",SKIP_AVALLAS_EXAMPLES);
	}

	
	private void testInDir(String dirPath, String ... skipthese) throws IOException, Exception {
		List<Path> failures = new ArrayList<>();
		Path examplePath = Paths.get(dirPath);
		Assert.assertTrue("dir not found "+  Paths.get(".").toAbsolutePath(),Files.isDirectory(examplePath));
		Iterator<Path> files = Files.walk(examplePath).iterator();
		while (files.hasNext()) {
			Path fileToRead = files.next();
			String scenarioName = fileToRead.toString();
			// skip some problematic files 
			if (Arrays.asList(skipthese).contains(fileToRead.getFileName().toString())) {
				System.out.println("skipping "+ fileToRead);				
				continue;
			}
			if (scenarioName.endsWith(".avalla") && Files.isRegularFile(fileToRead)) {
				System.out.println("testing "+ fileToRead);
				try{
					// those marked for failure must fail
					test(scenarioName, true, false, ! scenarioName.contains("fail"));
				} catch (Throwable e) {
					e.printStackTrace();
					// 
					failures.add(fileToRead);
				}
			}
		}
		assertTrue(failures.toString(), failures.isEmpty());
	}

}
