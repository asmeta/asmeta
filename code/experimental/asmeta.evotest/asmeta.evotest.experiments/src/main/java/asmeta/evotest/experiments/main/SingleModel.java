package asmeta.evotest.experiments.main;

// try with a single model
public class SingleModel {
	
	
	public static void main(String[] args) {
		String spec = "src\\main\\resources\\models\\SafeCombination.asm";
		TestExperiments.generateTestsAndComputeCoverage(spec );
	}

}
