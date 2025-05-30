package asmeta.evotest.experiments.main;

public class RunMultipleExperiments {
	
	/**
	 * Execute 10 test runs over all models in data/ase-exp/models
	 * 
	 * @param args The first argument is mandatory and must be the path to the Java
	 *             JDK version 8.
	 * @throws Exception if any exception occurs
	 */
	public static void main(String[] args) throws Exception {
		// Run the experiments 10 times
		for (int i = 0; i < 10; i++) {
			TestExperiments.main(new String[] {args[0], "data/ase-exp/models", "../results/run"+i});
		}
	}
}
