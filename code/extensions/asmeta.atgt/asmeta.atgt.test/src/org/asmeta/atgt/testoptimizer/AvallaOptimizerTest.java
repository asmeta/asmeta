package org.asmeta.atgt.testoptimizer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class AvallaOptimizerTest {
	
	private void deleteOptimizedFiles() throws Exception {
		Files.deleteIfExists(Path.of("examples/optimizer/cvm_scenario_optimized.avalla"));
		Files.deleteIfExists(Path.of("examples/optimizer/counter_scenario_optimized.avalla"));
	}

	@BeforeEach
	public void deleteBefore() throws Exception {
		deleteOptimizedFiles();
	}
	
	@AfterEach
	public void deleteAfter() throws Exception {
		deleteOptimizedFiles();
	}

	@Test
	public void testFromAvallaToOptimizedAvalla() throws Exception {
		AvallaOptimizer optimizer = new AvallaOptimizer();
		optimizer.optimize("examples/optimizer/cvm_scenario.avalla");
		assertTrue(new File("examples/optimizer/cvm_scenario_optimized.avalla").exists());
	}

	@Test
	public void testFromAvallaToOptimizedAvallaAdditionalStep() throws Exception {
		AvallaOptimizer optimizer = new AvallaOptimizer();
		optimizer.optimize("examples/optimizer/counter_scenario.avalla");
		assertTrue(new File("examples/optimizer/counter_scenario_optimized.avalla").exists());
	}

}
