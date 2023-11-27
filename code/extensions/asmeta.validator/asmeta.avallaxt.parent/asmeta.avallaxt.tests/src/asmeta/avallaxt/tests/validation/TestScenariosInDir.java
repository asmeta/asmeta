package asmeta.avallaxt.tests.validation;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestScenariosInDir extends TestParserAndValidation {

	// test all the scenario in the examples
	@Test
	public void testAllExamples() throws IOException {
		testAvallasInDir("../../../../../asm_examples");
	}

	@Test
	public void testAllExamplesExamples() throws IOException {
		// subsumed by the previous onme
		//testAvallasInDir("../../../../../asm_examples/examples");
	}

	@Test
	public void testAllAll() throws IOException {
		//testAvallasInDir("../../../..");
	}

	@Test
	public void testAllAvallaXTTestExamples() throws IOException {
		// skip these beasue they contain errors for testing
		//testAvallasInDir("example");
	}

	@Test
	public void testAllAvallaXTTest() throws IOException {
		testAvallasInDir("../../asmeta.validator.test/scenariosforexamples");
	}

	// ABZ2020
	@Test
	public void testABZ2020() throws IOException {
		testAvallasInDir("example/abz2020/scenarios");
	}

	
	private void testAvallasInDir(String dirPath) throws IOException {
		List<String> filexWithErrors = new ArrayList<>();
		Path examplePath = Paths.get(dirPath);
		Assert.assertTrue(Files.isDirectory(examplePath));
		Iterator<Path> files = Files.walk(examplePath).iterator();
		while (files.hasNext()) {
			Path fileToRead = files.next();
			String scenarioName = fileToRead.toString();
			if (scenarioName.endsWith(".avalla") && Files.isRegularFile(fileToRead)) {
				if (checkPossibleFaults(scenarioName) != PossibleFaults_NONE) {
					filexWithErrors.add(scenarioName);
				}
			}
		}
		System.err.println(filexWithErrors);
		assertTrue(filexWithErrors.isEmpty());
	}	
}
