package org.asmeta.atgt.testoptimizer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;



public class AvallaOptimizerTest {

	@Test
	public void testFromAvallaToOptimizedAvalla() {
		AvallaOptimizer optimizer = new AvallaOptimizer();
		try {
			optimizer.optimize("examples/optimizer/scenario.avalla");
			assertTrue(new File("examples/optimizer/scenario_optimized.avalla").exists());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
