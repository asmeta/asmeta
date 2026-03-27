package asmeta.mutation.mutationscore;

import org.junit.jupiter.api.Test;


public class TestForMutScoreSingleFile {


	@Test
	public void test1file() throws Exception {
		String avallaTotest = TestForCoverageShortICTSS.base_dir + "/results/run1/atgttests/Lift/testRG_r_Main_TTRG2.avalla";
		MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
		var res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
	}

	@Test
	public void test2file() throws Exception {
		String avallaTotest = TestForCoverageShortICTSS.base_dir + "/results/run1/atgttests/CoffeeVendingMachine/testRG_r_Main_F.avalla";
		MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
		var res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
		avallaTotest = TestForCoverageShortICTSS.base_dir + "/results/run1/randomtests/CoffeeVendingMachine/testtest2.avalla";
		res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
	}

}
