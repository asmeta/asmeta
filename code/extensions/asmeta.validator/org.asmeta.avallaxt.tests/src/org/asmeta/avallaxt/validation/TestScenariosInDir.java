package org.asmeta.avallaxt.validation;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestScenariosInDir extends TestParserAndValidation {

	// test all the scenario in the examples
	@Test void allExamples() throws Exception {
		testAvallasInDir("../../../../asm_examples");
		testAvallasInDir("../../../../asmeta_models/tutorials");
	}

	@Test void allExamplesExamples() throws Exception {
		// subsumed by the previous onme
		//testAvallasInDir("../../../../../asm_examples/examples");
	}

	@Test void allAll() throws Exception {
		//testAvallasInDir("../../../..");
	}

	@Test void allAvallaXTTestExamples() throws Exception {
		// skip these beasue they contain errors for testing
		//testAvallasInDir("example");
	}

	@Test void allinValidatorTestExamples() throws Exception {
		testAvallasInDir("../asmeta.validator.test/scenariosforexamples");
	}


	// ABZ2020
	@Test void abz2020() throws Exception {
		//testAvallasInDir("example/abz2020/scenarios");
	}

	
	private void testAvallasInDir(String dirPath) throws IOException {
		List<String> filexWithErrors = new ArrayList<>();
		Path examplePath = Paths.get(dirPath);
		assertTrue(Files.isDirectory(examplePath));
		Iterator<Path> files = Files.walk(examplePath).iterator();
		while (files.hasNext()) {
			Path fileToRead = files.next();
			String scenarioName = fileToRead.toString();
			if (scenarioName.contains("workspaceMSL")) continue;
			if (scenarioName.endsWith(".avalla") && Files.isRegularFile(fileToRead)) {
				if (checkPossibleFaults(scenarioName) != PossibleFaults_NONE) {
					filexWithErrors.add(scenarioName);
				}
			}
		}
		System.err.println(filexWithErrors);
		assertTrue(filexWithErrors.isEmpty(),filexWithErrors.toString());
	}	
}
