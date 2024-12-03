package asmeta.mutation.mutationscore;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ScenarioExecutorTest {

	@Test
	public void testComputeMutationScore() throws Exception {
		ScenarioExecutor sc = new ScenarioExecutor();
		//sc.computeMutationScore("examples/r2s_scenario1.avalla");
		sc.computeMutationScore("examples/choose_scenario1.avalla");
	}

}
