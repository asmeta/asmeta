package org.asmeta.atgt.testoptimizer;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class AvallaOptimizerTest {

	@Test
	@Ignore
	// TODO: complete when the classpath conflict in AvallaOptimizer is fixed
	public void testFromAvallaToOptimizedAvalla() {
		AvallaOptimizer optimizer = new AvallaOptimizer();
		try {
			optimizer.optimize("examples/optimizer/scenario.avalla");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
