package org.asmeta.atgt.generator2;
import atgt.coverage.AsmTestSuite;

public abstract class AsmTestGenerator {

	/** return the test suite as a sequence of sequences of states*/
	public abstract AsmTestSuite getTestSuite();
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
