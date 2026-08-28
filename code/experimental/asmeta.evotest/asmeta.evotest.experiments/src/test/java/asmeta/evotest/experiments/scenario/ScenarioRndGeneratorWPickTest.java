package asmeta.evotest.experiments.scenario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.rndgenerator.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.AsmetaV.CoverageRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.toAvalla;

class ScenarioRndGeneratorWPickTest {

	private static final String MODEL_LIST = "data\\icst_27_exp\\model_list.txt";
	private static final String source = "../../../../asm_examples/";
	private static final String PROCESSED_MODELS_CSV = "random_generation.csv";

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

	public static void main(String[] args) throws IOException {
		Logger.getLogger(org.asmeta.simulator.main.Simulator.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		// enable assertions
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
		// Read the file with the list of asms to be processed
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(MODEL_LIST));
		} catch (IOException e) {
			System.err.println("Falied to load " + MODEL_LIST + "." + e);
			return;
		}
		Path outputFile = Path.of(PROCESSED_MODELS_CSV);
		if (Files.notExists(outputFile)) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile(), true))) {
				writer.write("model_path\tnum_tps\ttime_taken_ms\tNUSMV?");
				writer.newLine();
				writer.flush();
				writer.close();
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile(), true));
		// For each asm in the list: generate tests -> run validation -> run mutation
		int specCounter = 0;
		for (String line : lines) {
			// Skip commented asms
			if (!line.isEmpty() && !line.startsWith("//")) {
				try {
					String asmPath = Paths.get(source).resolve(line).toString().replace('\\', '/');
					// check if the model contains choose rule
					AsmCollection asms = ASMParser.setUpReadAsm(new File(asmPath));
					if (asmeta.evotest.experiments.dataprep.RuleCounter.containsInternalNonDeterminism(asms)) {
						writer.write(specCounter + "\t" + asmPath);
						// generate
						AsmTestGeneratorBySimulation rndgen = new AsmTestGeneratorBySimulation(asms, 4, 1);
						try {
							AsmTestSuite ts = rndgen.getTestSuiteException(true);
							writer.write("\t" + ts.getNActualTest());
							// execute the scenario
							if (ts.getNActualTest() == 1) {
								// save avalla
								AsmTestSequence test = ts.getTests().getFirst();
								String scenarioName = "scenarios/scenario_" + String.format("%03d", specCounter)
										+ ".avalla";
								toAvalla exporter = new toAvalla(new File(scenarioName), test,
										new File(asmPath).toString());
								exporter.saveToStream();
								// call the validator
								boolean shuffle = true;
								List<String> results = AsmetaV.execValidation(scenarioName,
										AsmetaV.doNotcomputeCoverage, shuffle);
								assert results != null;
								writer.write("\t" + results.toString());
							}
						} catch (Throwable t) {
							if (t instanceof java.lang.AssertionError) {
								t.printStackTrace();
							}
							writer.write("\t" + t);
						}
						// Next specification
						specCounter++;
						//
						writer.write("\n");
						writer.flush();
					}
				} catch (Throwable t) {
					System.err.println("Unexpected error while processing ASM line '" + line
							+ "'. Continuing with next ASM.\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
					t.printStackTrace();
					System.exit(-1);
				}
			}
		}
	}

}
