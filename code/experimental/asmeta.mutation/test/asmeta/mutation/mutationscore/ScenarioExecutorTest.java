package asmeta.mutation.mutationscore;

import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScenarioExecutorTest {

	@BeforeClass
	public static void setUpLogger() throws Exception {
		Logger.getLogger(org.asmeta.simulator.main.Simulator.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.xt.validator.AsmetaV").setLevel(Level.OFF);
	}
	@AfterClass
	public static void cleanTemp() throws Exception {
		File dir = new File("temp/");
		for(File file: dir.listFiles()) 
	        file.delete();
	}

	
	@Test
	public void testComputeMutationScoreChooseInteger() throws Exception {
		// sc.computeMutationScore("examples/r2s_scenario1.avalla");
		computeMC("examples/chooseinteger_scenario2_flaky.avalla");
		computeMC("examples/chooseinteger_scenario1_fail.avalla");
		computeMC("examples/chooseinteger_scenario1_pick.avalla");
	}

	@Test
	public void testComputeMutationExperimentsNFM() throws Exception {
		computeMC("experiments_nfm25/scenario_ground_pick.avalla", 100);
		computeMC("experiments_nfm25/scenario_ground_flaky.avalla", 100);
		computeMC("experiments_nfm25/scenario_ground_nofail.avalla", 100);
	}
	
	private void computeMC(String scenarioPath, int nTimes) throws Exception {
		ScenarioExecutor sc = new ScenarioExecutor();
		if (nTimes == 1) {
			double res = sc.computeMutationScore(scenarioPath);
			System.out.println(scenarioPath + "mutation score " + res);
		} else {
			double res = 0;
			for (int i = 0; i < nTimes; i++) {
				res += sc.computeMutationScore(scenarioPath);
			}
			System.out.println(scenarioPath + " average mutation score " + res / nTimes);
		}
	}

	private void computeMC(String scenarioPath) throws Exception {
		computeMC(scenarioPath, 1);
	}

}
