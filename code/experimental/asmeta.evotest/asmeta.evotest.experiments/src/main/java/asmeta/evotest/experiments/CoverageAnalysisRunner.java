package asmeta.evotest.experiments;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.RuleEvalWCov;

import asmeta.AsmCollection;
import asmeta.evotest.experiments.model.ModelDataCollector;
import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.validator.ScenarioValidator;
import asmeta.evotest.experiments.scenario.validator.ValidationDataCollector;
import asmeta.evotest.experiments.utils.CsvManager;
import asmeta.evotest.experiments.utils.YamlManager;

public class CoverageAnalysisRunner {
	private static final String DATA_CSV = "data.csv";

	/**
	 * Entry point for aggregating scenario-generation and validation results into a CSV.
	 * <p>
	 * Given a base directory (containing subfolders like {@code randomtests/},
	 * {@code atgttests/}, {@code evoavallatests/}), this method walks each approach
	 * directory, then each generated scenario suite directory, and for each suite:
	 * <ol>
	 *   <li>Reads {@code metadata.yaml} (ASM name/path and execution time).</li>
	 *   <li>Parses the ASM model and collects model metrics.</li>
	 *   <li>Collects Avalla scenario metrics (n_step, n_check, n_set, n_scenarios).</li>
	 *   <li>Runs validation to produce coverage CSV, then collects coverage metrics.</li>
	 *   <li>Appends a row with all metrics to {@code data.csv} under {@code baseDir}.</li>
	 * </ol>
	 *
	 * @param args command-line arguments; {@code args[0]} must be the base directory to scan
	 * @throws Exception if file I/O, parsing, or validation fails
	 */
	public static void main(String[] args) throws Exception {
		// Validate input directory argument
		if (args.length < 1)
			throw new RuntimeException("Missing argument: directory to search for scenarios.");
		String baseDir = args[0];
		// Prepare the output CSV (clean sibling temp if present CSVs and write header)
		CsvManager csvManager = new CsvManager(baseDir + File.separator + DATA_CSV);
		csvManager.clean();
		csvManager.setup();
		// Iterate over approach directories (randomtests/, atgttests/, evoavallatests/)
		File[] approachDirs = new File(baseDir).listFiles();
		for (File approachDir : approachDirs) {
			if (approachDir.isDirectory() && approachDir.listFiles().length > 0) {
				String approach = approachDir.getName().replace("tests", "");
				// Iterate over individual scenario-suite directories
				File[] scenarioDirs = approachDir.listFiles();
				for (File scenarioDir : scenarioDirs) {
					if (scenarioDir.isDirectory() && scenarioDir.listFiles().length > 0) {
						// Find and read the metadata YAML (exec time, ASM name, ASM path)
						File metadataFile = Arrays
								.stream(scenarioDir.listFiles(file -> file.getName().endsWith(".yaml")))
								.findFirst() // first (and only) YAML file
								.orElseThrow(() -> new RuntimeException("No .yaml file found in " + scenarioDir));
						Map<String, Object> metadata = YamlManager.load(metadataFile);
						String asmName = (String) metadata.get(YamlManager.ASM_NAME);
						String asmPath = (String) metadata.get(YamlManager.ASM_PATH);
						float execTime = ((Number) metadata.get(YamlManager.EXEC_TIME)).floatValue();
						// Resolve ASM absolute path and parse the model
						Path basePath = Path.of(scenarioDir.getCanonicalPath());
						String asmAbsolutePath = basePath.resolve(Paths.get(asmPath)).normalize().toAbsolutePath()
								.toString();
						AsmCollection asm = ASMParser.setUpReadAsm(new File(asmAbsolutePath));
						// Collect model data
						Map<String, String> modelData = ModelDataCollector.collectModelData(asm);
						// Collect scenario data
						String dir = scenarioDir.toString();
						Map<String, Integer> avallaData = ScenarioDataCollector.collectAvallaData(dir);
						int nScenario = ScenarioDataCollector.getNumberOfScenario(dir);
						// Run validation to compute coverage, data is stored in a temporary CSV
						String csvPath = csvManager.getParentDir() + File.separator + "temp.csv";
						int valErrors = ScenarioValidator.computeCoverageFromAvalla(scenarioDir.getPath(), csvPath);
						// Reset rule evaluation state before processing the next suite
						RuleEvalWCov.reset();
						// Aggregate coverage metrics from the temporary CSV
						Map<String, String> covData = ValidationDataCollector.collectCoverageData(csvPath, asmName,
								modelData);
						// Append a consolidated row to data.csv
						csvManager.writeData(modelData, avallaData, covData, asmName, approach, execTime, nScenario,
								valErrors);
						// Clean the temporary CSV
						csvManager.clean();
					}
				}
			}
		}
	}

}
