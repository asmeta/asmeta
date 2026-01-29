package asmeta.evotest.experiments.dataprep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.io.FileUtils;

import asmeta.evotest.experiments.RandomGeneratorRunner;
import asmeta.evotest.experiments.ScenarioGeneratorRunner;
import asmeta.evotest.experiments.utils.YamlManager;

public class TestSuiteAggregator {

	/*
	 * Change the following private fields as needed
	 */
	private final static String RESULTS_DIR = "data/icst-26-exp/results";
	private final static String ASM_EXAMPLES = "../../../../asm_examples";
	private static final String MODEL_LIST = "data/icst-26-exp/model_lists/model_list_all_valid.txt";
	private static final List<List<String>> AGGREGATED_TEST_SUITES = List.of(
			List.of(
					ScenarioGeneratorRunner.EVOAVALLA_DIR,
					ScenarioGeneratorRunner.ATGT_DIR
					),
			List.of(ScenarioGeneratorRunner.EVOAVALLA_DIR,
					RandomGeneratorRunner.RANDOM_EVOAVALLA_DIR
					),
			List.of(ScenarioGeneratorRunner.EVOAVALLA_DIR,
					RandomGeneratorRunner.RANDOM_ATGT_DIR
					)
		);

	public static void main(String[] args) throws IOException {
		// Read the file with the list of models
		List<String> lines;
		lines = Files.readAllLines(Paths.get(MODEL_LIST));
		// For each model in the list...
		int count = 0;
		for (String line : lines) {
			if (line.isEmpty() || line.startsWith("//"))
				continue;
			String asmPath = Paths.get(ASM_EXAMPLES).resolve(line).toString().replace('\\', '/');
			String asmName = asmPath.substring(asmPath.lastIndexOf('/') + 1).replace(".asm", "");
			System.out.println("\nAggregating test suites for: " + asmName + ".");
			// For each aggregated test suite to be populated...
			for (List<String> testSuiteNames : AGGREGATED_TEST_SUITES) {
				System.out.println("Aggregating: " + testSuiteNames);
				String aggTestSuitePath = RESULTS_DIR + File.separator + String.join("-", testSuiteNames) + File.separator + count + "_" +  asmName;
				File aggTestSuiteFile = new File(aggTestSuitePath);
				if (aggTestSuiteFile.exists()) {
					FileUtils.deleteDirectory(aggTestSuiteFile);
					System.out.println("Old scenarios removed.");
				}
				aggTestSuiteFile.mkdirs();
				// For each approach to be used to populate the aggregated test suite...
				for (String testSuiteName : testSuiteNames) {
					String testSuitePath = RESULTS_DIR + File.separator + testSuiteName;
					File[] testSuites = new File(testSuitePath).listFiles();
					// Search the target original test suite...
					// FIXME: not working if multiple asm schare the same name
					for (File tsFile : testSuites) {
						String tsDir = tsFile.getPath();
						String tsAsmName = tsDir.substring(tsDir.indexOf("_") + 1);
						if (tsAsmName.equals(asmName)) {
							// Copy all avalla files from the original test suite to the aggregated one
							File[] avallaFiles = tsFile.listFiles((dir, name) -> name.endsWith(".avalla"));
							if (avallaFiles != null) {
								System.out.println("Copying from " + testSuiteName);
								for (File avFile : avallaFiles) {
									String newName = testSuiteName + "_" + avFile.getName();
									File dest = new File(aggTestSuitePath, newName);
									Files.copy(avFile.toPath(), dest.toPath());
								}
							}
							YamlManager.write(aggTestSuitePath, asmName, asmPath, Float.NaN, LocalDateTime.now().toString());
							break;
						}
					}
				}
			}
			count++;
		}
	}

}
