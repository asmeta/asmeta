package org.asmeta.atgt.rndgenerator;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class AsmTestGeneratorRandExamples {

	@Test
	public void testExamplesForICST27() throws Exception {
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		Logger.getLogger("org.asmeta.simulator.main.Simulator").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Path model_list = Path.of("../../../experimental/asmeta.evotest/asmeta.evotest.experiments/data/icst-27-exp/",
				"model_list.txt");
		assertTrue(model_list.toFile().exists());
		String exampleDir = "../../../../asm_examples/";
		assertTrue(new File(exampleDir).exists());
		List<String> errors = new ArrayList<>();
		try (Stream<String> lines = Files.lines(model_list)) {
			lines.map(String::trim).filter(line -> !line.isEmpty() && !line.startsWith("//")).forEach(filename -> {
				System.out.println("Opening: " + filename);
				try {
					String completeFileName = exampleDir + filename;
					String test = AsmTestGeneratorBySimulationTest.generateFor(completeFileName, false);
				} catch (Throwable e) {
					errors.add(filename + "\t" + e.getMessage());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		IntStream.range(0, errors.size()).forEach(i -> System.err.println(i + ": " + errors.get(i)));
	}

}
