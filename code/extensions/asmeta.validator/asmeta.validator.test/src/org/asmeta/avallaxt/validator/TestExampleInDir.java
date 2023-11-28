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

public class TestExampleInDir extends TestValidator {
	
	@Test
	public void testAllAvallaInAsmExamples() throws Exception {
		testInDir(ASM_EXAMPLES_EXAMPLES);
	}

	@Test
	public void testAllForExamples() throws Exception {
		testInDir("scenariosforexamples");
	}

	@Test
	public void testAllTest() throws Exception {
		Logger.getLogger(AsmetaPrinterForAvalla.class).setLevel(Level.ALL);
		testInDir("scenariosfortest");
	}
	
	// TODO fix these scenarios (or models)
	static final String[] SKIP_AVALLAS = {
			"LowBeamOFFonAmbientLight.avalla", 
			"LowBeamOFFPowerOFFKey.avalla", 
			"trace_scenario1.avalla",
			"trace_scenario2.avalla", 
			"lift.avalla", 
			"sluiceGateMotorCtl.avalla"};


	
	private void testInDir(String dirPath) throws IOException, Exception {
		List<Path> failures = new ArrayList<>();
		Path examplePath = Paths.get(dirPath);
		Assert.assertTrue("dir not found "+  Paths.get(".").toAbsolutePath(),Files.isDirectory(examplePath));
		Iterator<Path> files = Files.walk(examplePath).iterator();
		while (files.hasNext()) {
			Path fileToRead = files.next();
			String scenarioName = fileToRead.toString();
			// skip some preblematic files 
			if (Arrays.asList(SKIP_AVALLAS).contains(fileToRead.getFileName().toString())) {
				System.out.println("skipping "+ fileToRead);				
				continue;
			}
			System.out.println("testing "+ fileToRead);
/*			if (scenarioName.contains("old"))
				continue;
			if (scenarioName.contains("DAS"))
				continue;*/
			if (scenarioName.endsWith(".avalla") && Files.isRegularFile(fileToRead)) {
				try{
					test(scenarioName, true, false);
				}catch (Throwable e) {
					e.printStackTrace();
					failures.add(fileToRead);
				}
			}
		}
		assertTrue(failures.toString(), failures.isEmpty());
	}

}
