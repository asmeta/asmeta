package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.avallaxt.validator.TestValidator;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ParToSeqMutator;

class MutationScoreExecutorTest extends TestValidator {

	private static final String COFFEE_VENDING_MACHINE_SCENARIOS =
			"scenariosforexamples/coffeeVendingMachine";
	private static final String ADVANCED_CLOCK_SCENARIO =
			"scenariosforexamples/advancedClock/advancedClock1.avalla";

	@Test
	void testComputeMutationScoreFromSingleScenarioUsesAllStandardOperators() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		var result = executor.computeMutationScoreFromScenarios(ADVANCED_CLOCK_SCENARIO);

		assertStandardOperators(result.keySet());
		assertValidScores(result.entrySet());
	}

	@Test
	void testComputeMutationScoreFromScenarioDirectory() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		var result = executor.computeMutationScoreFromScenarios(COFFEE_VENDING_MACHINE_SCENARIOS);

		assertStandardOperators(result.keySet());
		assertValidScores(result.entrySet());
	}

	@Test
	void testGenerateParToSeqMutantsFromAsmPath() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		List<AsmCollection> mutants = executor.generateMutants(COFFEE_VENDING_MACHINE_SCENARIOS, new ParToSeqMutator());

		assertFalse(mutants.isEmpty());
	}

	private void assertStandardOperators(Set<String> operatorNames) {
		assertEquals(Set.of("CaseMutator", "ChooseRuleMutator", "CondNegator", "CondRemover", "ForAllMutator",
				"ParToSeqMutator", "RuleRemover", "SeqToParMutator"), operatorNames);
	}

	private void assertValidScores(Set<Entry<String, Entry<Integer, Integer>>> scores) {
		for (Entry<String, Entry<Integer, Integer>> score : scores) {
			int killedMutants = score.getValue().getKey();
			int generatedMutants = score.getValue().getValue();
			assertTrue(generatedMutants >= 0, score.getKey());
			assertTrue(killedMutants >= 0, score.getKey());
			assertTrue(killedMutants <= generatedMutants, score.getKey());
		}
	}
}
