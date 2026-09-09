package atgt.coverage;

import atgt.parser.ExampleLoader;

import org.junit.jupiter.api.Test;
import atgt.specification.ASMSpecification;

class RootCoverageTest {

	@Test void withFuzzyCounter() throws Exception {
		ASMSpecification fuzzyCounter = ExampleLoader.getSpec("fuzzyCounter.asm");
		
		AsmCoverage tptree = RootCoverage.ROOT.getTPTree(fuzzyCounter);
		for(AsmTestCondition tc : tptree.allTPs()){
			System.out.println(tc.getName() + " " + tc.getCondition());
			
		}
	}

}
