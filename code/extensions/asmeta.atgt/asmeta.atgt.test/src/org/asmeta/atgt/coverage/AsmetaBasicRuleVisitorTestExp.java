package org.asmeta.atgt.coverage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.asmeta.parser.ASMParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverage;

public class AsmetaBasicRuleVisitorTestExp {

	private static final String PROCESSED_MODELS_CSV = "processed_models.csv";

	@BeforeAll
	public static void setup() {
//		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);
//		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
//		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
//		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
	}
	static String FILE_BASE = "../../../../asm_examples/";

	@Test
	void testAllExperimentsEvoavalla() throws IOException {
		// Logger.getLogger(RuleFlattener.class).setLevel(Level.DEBUG);
		Path listFile = Path.of("model_list.txt");
		// set the output file
		Path outputFile = Path.of(PROCESSED_MODELS_CSV);
		// put the results in a file
		java.util.List<String> processedModels = Files.exists(outputFile) ? Files.readAllLines(outputFile)
				: new java.util.ArrayList<>();
		int consideredModels = 0;
		try (BufferedReader paths = Files.newBufferedReader(listFile)) {
			// if the outpput file is new, write the header
			if (Files.notExists(outputFile)) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile(), true))) {
					writer.write("model_path\tnum_tps\ttime_taken_ms");
					writer.newLine();
					writer.flush();
				}
			}
			String line;
			while ((line = paths.readLine()) != null) {
				String filePath = line.trim();
				if (filePath.isEmpty() || filePath.startsWith("//")) {
					continue;
				}
				consideredModels++;
				String ex = FILE_BASE + filePath;
				if (processedModels.stream().anyMatch(p -> p.startsWith(filePath))) {
					System.out.println("already processed " + ex);					
					continue;
				}
				System.out.println("generating tps for " + ex);
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile(), true))) {
					writer.write(filePath + "\t");
					writer.flush();
					// start a timer to measure the time taken for each model
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							System.err.println("raggiunti 10 secondi per " + ex);
							try {
								writer.write("timeout 10 seconds");
								writer.newLine();
								writer.flush();
							} catch (IOException e) {
								e.printStackTrace();
							}
							System.exit(1);
						}
					}, 	10000);
					Instant start = Instant.now();
					Optional<Integer> numTp = AsmetaBasicRuleVisitorTest.generateCoverageFor(ex);
					// write the result to the output file
					System.out.println("number of tps for " + ex + ": " + numTp);
					writer.write(numTp.isPresent() ? numTp.get().toString() : "error");
					// write time taken to process the model
					Instant finish = Instant.now();
					writer.write("\t" + Duration.between(start, finish).toMillis());
					writer.newLine();
					timer.cancel();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print the results to the console
		System.out.println("Processed " + consideredModels);
		long count = processedModels.stream().filter(p -> (!p.startsWith("//") && p.contains("timeout"))).count();
		System.out.println("Timeouts: "  + count);
		long count2 = processedModels.stream().filter(p -> (!p.startsWith("//") && p.contains("error"))).count();
		System.out.println("Errors: "  + count2);
		System.out.println("Valid: "  + (consideredModels - count - count2));
	}


}
