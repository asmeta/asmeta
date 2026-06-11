package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ScenarioExecutorTest {

	private static final String CHOOSE_INTEGER_DIR = "scenariosforexamples/mutationScore/chooseinteger";
	private static final String NFM_DIR = "scenariosforexamples/mutationScore/nfm25";

	@BeforeAll
	static void setUpLogger() {
		Logger.getLogger(org.asmeta.simulator.main.Simulator.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.xt.validator.AsmetaV").setLevel(Level.OFF);
	}

	@Test
	void testComputeMutationScoreChooseIntegerScenarios() throws Exception {
		Utils.assertValidScores(compute(Path.of(CHOOSE_INTEGER_DIR, "chooseinteger_scenario1_pick.avalla")));
		Utils.assertValidScores(compute(Path.of(CHOOSE_INTEGER_DIR, "chooseinteger_scenario2_flaky.avalla")));
		Utils.assertValidScores(compute(Path.of(CHOOSE_INTEGER_DIR, "chooseinteger_scenario1_fail.avalla")));
	}

	@Test
	void testComputeMutationExperimentsNfmGround() throws Exception {
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ground_pick.avalla")));
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ground_flaky.avalla")));
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ground_nofail.avalla")));
	}

	@Test
	void testComputeMutationExperimentsNfmRefined() throws Exception {
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ref_pick.avalla")));
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ref_flaky.avalla")));
		Utils.assertValidScores(compute(Path.of(NFM_DIR, "scenario_ref_nofail.avalla")));
	}

	private static Set<Entry<String, Entry<Integer, Integer>>> compute(Path scenario) throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();
		return executor.computeMutationScoreFromScenarios(scenario.toString()).entrySet();
	}
}
