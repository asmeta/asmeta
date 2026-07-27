package atgt.coverage;

import java.io.IOException;

import org.junit.Test;

import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

public class RootCoverageTest {

	@Test
	public void testWithFuzzyCounter() throws IOException, ParseException {
		ASMSpecification fuzzyCounter = ExampleLoader.getSpec("fuzzyCounter.asm");
		
		AsmCoverage tptree = RootCoverage.ROOT.getTPTree(fuzzyCounter);
		for(AsmTestCondition tc : tptree.allTPs()){
			System.out.println(tc.getName() + " " + tc.getCondition());
			
		}
	}

}
