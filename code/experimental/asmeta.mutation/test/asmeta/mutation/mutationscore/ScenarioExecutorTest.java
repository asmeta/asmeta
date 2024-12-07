package asmeta.mutation.mutationscore;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ScenarioExecutorTest {

	@Test
	public void testComputeMutationScore() throws Exception {
		ScenarioExecutor sc = new ScenarioExecutor();
		//sc.computeMutationScore("examples/r2s_scenario1.avalla");
		sc.computeMutationScore("examples/chooseinteger_scenario2_flaky.avalla");
	}
	
	@Test
	public void testComputeMutationScoreFail() throws Exception {
		ScenarioExecutor sc = new ScenarioExecutor();
		//sc.computeMutationScore("examples/r2s_scenario1.avalla");
		sc.computeMutationScore("examples/chooseinteger_scenario1_fail.avalla");
	}

	@Test
	public void testComputeMutationScorePick() throws Exception {
		ScenarioExecutor sc = new ScenarioExecutor();
		//sc.computeMutationScore("examples/r2s_scenario1.avalla");
		sc.computeMutationScore("examples/chooseinteger_scenario1_pick.avalla");
	}

	
}
