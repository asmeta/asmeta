package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class TestForMutScoreSingleFile {

	@Test
	void testOneFile() throws Exception {
		String scenario = Path.of("scenariosforexamples/mutationScore/nfm25/scenario_ref_pick.avalla").toString();
		MutationTestUtils.assertValidScores(MutationScoreExecutor.createTempExecutor().computeMutationScoreFromScenarios(scenario)
				.entrySet());
	}

	@Test
	void testTwoFilesWithFreshExecutors() throws Exception {
		String firstScenario = Path.of("scenariosforexamples/mutationScore/nfm25/scenario_ground_pick.avalla").toString();
		String secondScenario = Path.of("scenariosforexamples/mutationScore/chooseinteger", "chooseinteger_scenario1_pick.avalla").toString();

		MutationTestUtils.assertValidScores(MutationScoreExecutor.createTempExecutor().computeMutationScoreFromScenarios(firstScenario).entrySet());
		MutationTestUtils.assertValidScores(MutationScoreExecutor.createTempExecutor().computeMutationScoreFromScenarios(secondScenario).entrySet());
	}
}
