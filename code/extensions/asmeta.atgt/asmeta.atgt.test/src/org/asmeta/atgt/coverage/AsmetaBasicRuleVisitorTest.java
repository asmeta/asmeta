package org.asmeta.atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.flattener.rule.RuleFlattener;
import org.asmeta.parser.ASMParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverage;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public class AsmetaBasicRuleVisitorTest {

	@BeforeAll
	public static void setup() {
//		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);
//		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
//		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
//		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
	}

	@Test
	@Tag("TestToMavenSkip")
	public void testGetTPTree() throws Exception {
		// String ex =
		// "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		// String ex =
		// "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		generateCoverageFor(ex);
	}

	@Test
	@Tag("TestToMavenSkip")
	public void testGetTPTreeMVM() throws Exception {
		generateCoverageFor("examples\\mvm0.asm");
	}

	@Test
	public void testGetTPTreeChoose() throws Exception {
		int tps = generateCoverageFor("examples\\SpecWithChoose.asm").get();
		// one tp: $i = 0
		assertEquals(1, tps);
	}

	static String FILE_BASE = "../../../../asm_examples/";

	@Test
	void testAllExperimentsEvoavalla() throws IOException {
		// Logger.getLogger(RuleFlattener.class).setLevel(Level.DEBUG);
		Path listFile = Path.of("model_list.txt");
		// set the output file
		Path outputFile = Path.of("processed_models.txt");
		// put the results in a file
		java.util.List<String> processedModels = Files.exists(outputFile) ? Files.readAllLines(outputFile)
				: new java.util.ArrayList<>();
		try (BufferedReader paths = Files.newBufferedReader(listFile)) {
			String line;
			while ((line = paths.readLine()) != null) {
				String filePath = line.trim();
				if (filePath.isEmpty() || filePath.startsWith("//")) {
					continue;
				}
				String ex = FILE_BASE + filePath;
				if (processedModels.stream().anyMatch(p -> p.startsWith(filePath))) {
					System.out.println("already processed " + ex);
					continue;
				}
				System.out.println("generating tps for " + ex);
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile(), true))) {
					writer.write(filePath + " ");
					writer.flush();
					// start a timer to measure the time taken for each model
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							System.err.println("raggiunti 10 secondi per " + ex);
						}
					}, 	10000);
					Optional<Integer> numTp = generateCoverageFor(ex);
					// write the result to the output file
					System.out.println("number of tps for " + ex + ": " + numTp);
					writer.write(numTp.isPresent() ? numTp.get().toString() : "error");
					writer.newLine();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	private Optional<Integer> generateCoverageFor(String ex) throws Exception {
		// String ex =
		// "C:\\Users\\garganti\\code_from_repos\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtd.asm";
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		AsmetaBasicRuleVisitor tpbuilder = new AsmetaBasicRuleVisitor();
		try {
			AsmetaAsSpec spec = new AsmetaAsSpec(asms);
			AsmCoverage tp = tpbuilder.getTPTree(spec);
			tp.allTPs().forEach(x -> System.out.println(x.getCondition()));
			return Optional.of(tp.getNumberofTPs());
		} catch (Throwable t) {
			System.err.println("spec not analyzable");
			return Optional.empty();
		}
	}

}
