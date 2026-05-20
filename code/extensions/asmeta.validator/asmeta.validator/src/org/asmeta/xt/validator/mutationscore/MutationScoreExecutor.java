package org.asmeta.xt.validator.mutationscore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.ValidationResult;

import asmeta.AsmCollection;
import asmeta.mutation.operators.AsmetaMutationOperator;
import asmeta.mutation.operators.CaseMutator;
import asmeta.mutation.operators.ChooseRuleMutator;
import asmeta.mutation.operators.CondNegator;
import asmeta.mutation.operators.CondRemover;
import asmeta.mutation.operators.ForAllMutator;
import asmeta.mutation.operators.ParToSeqMutator;
import asmeta.mutation.operators.RuleRemover;
import asmeta.mutation.operators.SeqToParMutator;
import asmeta.structure.Asm;

/**
 * Generates ASM mutants and executes Avalla scenarios over them to compute
 * mutation scores.
 */
public class MutationScoreExecutor {

	private static final Logger LOG = Logger.getLogger(MutationScoreExecutor.class);

	private Path tempDirectory;

	public static MutationScoreExecutor createTempExecutor() {
		return new MutationScoreExecutor();
	}

	private MutationScoreExecutor() {
	}

	/**
	 * Computes mutation score from one Avalla scenario or a directory of scenarios
	 * using all standard mutation operators.
	 */
	public HashMap<String, Entry<Integer, Integer>> computeMutationScoreFromScenarios(String scenarioOrDirectoryPath)
			throws Exception {
		try {
			List<Path> scenarios = requireAvallaFiles(scenarioOrDirectoryPath);
			AsmCollection originalAsm = loadAsmFromFirstScenario(scenarios);
			HashMap<String, Entry<Integer, Integer>> results = new LinkedHashMap<>();
			for (AsmetaMutationOperator operator : standardOperators(originalAsm.getMain())) {
				List<AsmCollection> mutants = operator.mutate(originalAsm);
				Set<Integer> killedMutations = updateKilledMutations(mutants, new HashSet<>(), scenarios);
				results.put(operator.getName(), new AbstractMap.SimpleEntry<>(killedMutations.size(), mutants.size()));
			}
			return results;
		} finally {
			deleteTempDirectory();
		}
	}

	/**
	 * Generates mutants for all standard mutation operators.
	 */
	public HashMap<String, List<AsmCollection>> generateMutants(AsmCollection asms) {
		HashMap<String, List<AsmCollection>> allMutants = new LinkedHashMap<>();
		for (AsmetaMutationOperator operator : standardOperators(asms.getMain())) {
			allMutants.put(operator.getName(), operator.mutate(asms));
		}
		return allMutants;
	}

	/**
	 * Generates mutants from an ASM file or homogeneous scenario suite using one
	 * operator.
	 */
	public List<AsmCollection> generateMutants(String asmOrScenarioPath, AsmetaMutationOperator operator)
			throws Exception {
		try {
			AsmCollection originalAsm = loadAsmFromPathOrScenario(asmOrScenarioPath);
			return operator.mutate(originalAsm);
		} finally {
			deleteTempDirectory();
		}
	}

	/**
	 * Computes mutation score for a suite and returns a newly created set of killed
	 * mutant indexes.
	 */
	public Set<Integer> computeMutationScore(List<AsmCollection> mutants, String testSuitePath) throws Exception {
		return computeMutationScore(mutants, new HashSet<>(), testSuitePath);
	}

	/**
	 * Computes mutation score for a suite and updates the supplied cumulative set of
	 * killed mutant indexes.
	 */
	public Set<Integer> computeMutationScore(List<AsmCollection> mutants, Set<Integer> killedMutations,
			String testSuitePath) throws Exception {
		try {
			return updateKilledMutations(mutants, killedMutations, findAvallaFiles(Path.of(testSuitePath)));
		} finally {
			deleteTempDirectory();
		}
	}

	/**
	 * Runs the scenarios and adds newly killed mutant indexes to the supplied set.
	 */
	private Set<Integer> updateKilledMutations(List<AsmCollection> mutants, Set<Integer> killedMutations,
			List<Path> scenarios) throws Exception {
		for (int i = 0; i < mutants.size(); i++) {
			if (killedMutations.contains(i)) {
				continue;
			}
			if (isKilledByAnyScenario(mutants.get(i), scenarios)) {
				killedMutations.add(i);
			}
		}
		return killedMutations;
	}

	/**
	 * Runs all scenarios against one mutant and returns true at the first failure.
	 */
	private boolean isKilledByAnyScenario(AsmCollection mutant, List<Path> scenarios) throws Exception {
		for (Path avalla : scenarios) {
			try {
				AsmetaMutatedFromAvalla asmetaBuilder =
						new AsmetaMutatedFromAvalla(avalla.toString(), ensureTempDirectory());
				Map<String, Boolean> allCoveredRules = new HashMap<>();
				asmetaBuilder.setAsmeta(mutant);
				asmetaBuilder.save();
				ValidationResult result = AsmetaV.executeAsmetaFromAvalla(AsmetaV.doNotcomputeCoverage, allCoveredRules,
						asmetaBuilder.getTempAsmPath(), false);
				if (!result.isCheckSucceeded()) {
					LOG.info("Validation failed => killed mutant.");
					return true;
				}
			} catch (Exception e) {
				LOG.info("Validation or scenario translation error for the mutated ASM => killed mutant.\n"
						+ e.getClass().getSimpleName() + ": " + e.getMessage());
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads the ASM referenced by the first scenario in the suite.
	 */
	private AsmCollection loadAsmFromFirstScenario(List<Path> scenarios) throws Exception {
		AsmetaMutatedFromAvalla builder =
				new AsmetaMutatedFromAvalla(scenarios.get(0).toString(), ensureTempDirectory());
		return ASMParser.setUpReadAsm(builder.getModelPath().toFile());
	}

	/**
	 * Loads an ASM directly, or from the first scenario in a homogeneous suite.
	 */
	private AsmCollection loadAsmFromPathOrScenario(String asmOrScenarioPath) throws Exception {
		Path path = Path.of(asmOrScenarioPath);
		if (Files.isDirectory(path) || asmOrScenarioPath.endsWith(AsmetaV.SCENARIO_EXTENSION)) {
			return loadAsmFromFirstScenario(requireAvallaFiles(asmOrScenarioPath));
		}
		return ASMParser.setUpReadAsm(new File(asmOrScenarioPath));
	}

	/**
	 * Resolves scenarios and fails when the path does not contain any.
	 */
	private List<Path> requireAvallaFiles(String scenarioOrDirectoryPath) throws IOException {
		List<Path> scenarios = findAvallaFiles(Path.of(scenarioOrDirectoryPath));
		if (scenarios.isEmpty()) {
			throw new RuntimeException("No " + AsmetaV.SCENARIO_EXTENSION + " files found in "
					+ scenarioOrDirectoryPath);
		}
		return scenarios;
	}

	/**
	 * Resolves a single scenario file or all scenarios inside a directory.
	 */
	private List<Path> findAvallaFiles(Path scenarioOrDirectoryPath) throws IOException {
		if (Files.isDirectory(scenarioOrDirectoryPath)) {
			try (Stream<Path> stream = Files.walk(scenarioOrDirectoryPath)) {
				return stream.filter(Files::isRegularFile)
						.filter(path -> path.toString().endsWith(AsmetaV.SCENARIO_EXTENSION))
						.sorted()
						.collect(Collectors.toList());
			}
		}
		if (scenarioOrDirectoryPath.toString().endsWith(AsmetaV.SCENARIO_EXTENSION)
				&& Files.isRegularFile(scenarioOrDirectoryPath)) {
			return List.of(scenarioOrDirectoryPath);
		}
		return List.of();
	}

	/**
	 * Returns the complete default mutation operator set.
	 */
	private List<AsmetaMutationOperator> standardOperators(Asm asm) {
		return List.of(new CaseMutator(), new ChooseRuleMutator(asm), new CondNegator(), new CondRemover(),
				new ForAllMutator(asm), new ParToSeqMutator(), new SeqToParMutator(), new RuleRemover());
	}

	/**
	 * Creates the temporary directory on demand and returns it.
	 */
	private File ensureTempDirectory() {
		try {
			if (tempDirectory == null || Files.notExists(tempDirectory)) {
				tempDirectory = Files.createTempDirectory("asmeta-mutation");
			}
			return tempDirectory.toFile();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Deletes the configured temporary directory and all generated files.
	 */
	private void deleteTempDirectory() {
		if (tempDirectory != null && Files.exists(tempDirectory)) {
			try (Stream<Path> stream = Files.walk(tempDirectory)) {
				stream.sorted(Comparator.reverseOrder())
						.forEach(this::deleteTempPath);
			} catch (IOException e) {
				LOG.warn("Unable to walk mutation temp directory for cleanup: " + tempDirectory, e);
			} finally {
				tempDirectory = null;
			}
		}
	}

	/**
	 * Deletes one generated temp path without failing mutation scoring.
	 */
	private void deleteTempPath(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			LOG.debug("Unable to delete mutation temp path: " + path, e);
		}
	}

	static class AsmetaMutatedFromAvalla extends AsmetaFromAvallaBuilder {

		AsmetaMutatedFromAvalla(String scenarioPath, File tempAsmPathDir) throws Exception {
			super(scenarioPath, tempAsmPathDir);
		}

		Path getModelPath() {
			return getLoadedAsmPath();
		}

		void setAsmeta(AsmCollection mutant) {
			asmCollection = mutant;
			asm = asmCollection.getMain();
		}
	}
}
