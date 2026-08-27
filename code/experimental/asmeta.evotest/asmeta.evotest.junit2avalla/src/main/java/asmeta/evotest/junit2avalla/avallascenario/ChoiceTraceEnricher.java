package asmeta.evotest.junit2avalla.avallascenario;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.AvallaPickTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.evotest.junit2avalla.model.terms.AvallaTerm;

/** Adds the choices recorded by EvoSuite to the corresponding scenarios. */
public class ChoiceTraceEnricher {

	public void enrich(List<Scenario> scenarios, Path tracePath) throws IOException {
		Properties trace = new Properties();
		try (InputStream input = Files.newInputStream(tracePath)) {
			trace.load(input);
		}

		int testCount = readInt(trace, "test.count");
		if (testCount > scenarios.size()) {
			throw new IllegalArgumentException(
					"Choice trace contains " + testCount + " tests, but only " + scenarios.size() + " scenarios were found");
		}

		for (int testIndex = 0; testIndex < testCount; testIndex++) {
			Map<Integer, List<AvallaPickTerm>> picksByStep = readPicks(trace, testIndex);
			addPicks(scenarios.get(testIndex), testIndex, picksByStep);
		}
	}

	private Map<Integer, List<AvallaPickTerm>> readPicks(Properties trace, int testIndex) {
		String testPrefix = "test." + testIndex;
		int choiceCount = readInt(trace, testPrefix + ".choice.count");
		Map<Integer, List<AvallaPickTerm>> picksByStep = new HashMap<>();

		for (int choiceIndex = 0; choiceIndex < choiceCount; choiceIndex++) {
			String choicePrefix = testPrefix + ".choice." + choiceIndex;
			int step = readInt(trace, choicePrefix + ".step");
			if (step < 0) {
				throw new IllegalArgumentException("Negative step in property " + choicePrefix + ".step");
			}

			AvallaPickTerm pick = new AvallaPickTerm(required(trace, choicePrefix + ".variable"),
					required(trace, choicePrefix + ".rule"), required(trace, choicePrefix + ".value"));
			picksByStep.computeIfAbsent(step, key -> new ArrayList<>()).add(pick);
		}

		return picksByStep;
	}

	private void addPicks(Scenario scenario, int testIndex, Map<Integer, List<AvallaPickTerm>> picksByStep) {
		List<AvallaTerm> enrichedTerms = new ArrayList<>();
		int step = 0;

		for (AvallaTerm term : scenario.getScenarioList()) {
			if (term instanceof AvallaStepTerm) {
				List<AvallaPickTerm> picks = picksByStep.remove(step);
				if (picks != null) {
					enrichedTerms.addAll(picks);
				}
				step++;
			}
			enrichedTerms.add(term);
		}

		if (!picksByStep.isEmpty()) {
			throw new IllegalArgumentException(
					"Choice trace for test" + testIndex + " refers to a step that is not present in scenario" + testIndex);
		}

		scenario.getScenarioList().clear();
		scenario.getScenarioList().addAll(enrichedTerms);
	}

	private static int readInt(Properties properties, String key) {
		String value = required(properties, key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Property " + key + " is not a valid integer: " + value, e);
		}
	}

	private static String required(Properties properties, String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("Missing property: " + key);
		}
		return value;
	}
}
