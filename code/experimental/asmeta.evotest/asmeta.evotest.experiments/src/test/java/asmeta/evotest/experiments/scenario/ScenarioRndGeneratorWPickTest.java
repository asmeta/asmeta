package asmeta.evotest.experiments.scenario;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import asmeta.evotest.experiments.dataprep.ModelDirectoryScanner;

class ScenarioRndGeneratorWPickTest {

	private static final String MODEL_LIST = "data\\icst_27_exp\\model_list.txt";
	private static final String source = "../../../../asm_examples/";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRunRandomWithPick() {
		//
	}
	
	public static void main(String[] args) {
		// Read the file with the list of asms to be processed
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(MODEL_LIST));
		} catch (IOException e) {
			System.err.println("Falied to load " + MODEL_LIST + "." + e);
			return;
		}
		// For each asm in the list: generate tests -> run validation -> run mutation
		int specCounter = 0;
		for (String line : lines) {
			// Skip commented asms
			if (!line.isEmpty() && !line.startsWith("//")) {
				try {
					String asmPath = Paths.get(source).resolve(line).toString().replace('\\', '/');
					// check if the model contains choose rule
					if (asmeta.evotest.experiments.dataprep.RuleCounter.containsInternalNonDeterminism(new File(asmPath))) {
						System.out.println(specCounter + " " + asmPath);
						// Next specification
						specCounter++;
						// TOD generate random
					}
				} catch (Throwable t) {
					System.err.println(
							"Unexpected error while processing ASM line '" + line + "'. Continuing with next ASM.\n"
									+ t.getClass().getSimpleName() + ": " + t.getMessage());
				}
			}
		}
	}

}
