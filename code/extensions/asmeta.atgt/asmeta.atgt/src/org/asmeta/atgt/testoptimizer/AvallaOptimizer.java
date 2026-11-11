package org.asmeta.atgt.testoptimizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.SaveResults;

import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.avallaxt.avalla.Check;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;

public class AvallaOptimizer {

	/**
	 * Optimize an avalla removing unecessary set and check statements. The new
	 * optimized avalla is placed in the same directory of the original one.
	 */
	public void optimize(String avallaPath) throws Exception {
		optimize(avallaPath, new File(avallaPath).getAbsoluteFile().getParentFile().getAbsolutePath());
	}

	/**
	 * Optimize an avalla and save the optimized scenario in the target directory.
	 */
	public void optimize(String avallaPath, String targetDir) throws Exception {
		// check avallaPath exists and is .avalla
		File avalla = new File(avallaPath);
		if (!avalla.exists())
			throw new FileNotFoundException(avallaPath +  " does not exist");
		if (!avalla.getName().endsWith(AsmetaV.SCENARIO_EXTENSION))
			throw new RuntimeException(avallaPath + " is not a valid .avalla file");
		
		// Convert to AsmTestSequence to do optimization
		AvallaToTestConverter converter = new AvallaToTestConverter(avallaPath);
		AsmTestSequence test = converter.convert();
		AsmTestSuite ts = AsmTestSuite.getAsmTestSuiteFactory().buildEmptyTestSuite();
		ts.addTest(test);
		
		// Get the path to the asm (from the load statement) and parse it
		Scenario s = converter.getScenario();
		String asmPath = s.getSpec();
		File asmFile = new File(asmPath).isAbsolute() ? new File(asmPath) : new File(avalla.getParentFile().getAbsolutePath(), asmPath);
		AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
		
		// Remove redundant checks
		TestOptimizer optimizer = UnchangedRemover.allRemover;
		optimizer.optimize(test);
		
		// Remove redundant sets
		boolean previous = UnecessaryChangesRemover.skipLastStep;
		UnecessaryChangesRemover.skipLastStep = true;
		try {
		    optimizer = new UnecessaryChangesRemover(asm);
		    optimizer.optimize(test);
		} finally {
		    UnecessaryChangesRemover.skipLastStep = previous;
		}
		
		// Write a new Avalla with the optimized test sequence
		String name = avalla.getName();
		String optimizedName = name.replace(AsmetaV.SCENARIO_EXTENSION, "_optimized" + AsmetaV.SCENARIO_EXTENSION);
		File generatedFile = new File(targetDir, test.getName().replace("@", "") + ".avalla");
		File optimizedFile = new File(targetDir, optimizedName);
		SaveResults.saveResults(ts, asmFile.getAbsolutePath(), Collections.singleton(FormatsEnum.AVALLA), "", targetDir, false);
		Files.copy(generatedFile.toPath(), optimizedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		Files.delete(generatedFile.toPath());

		// Clean up the generated header and use the optimized file name as scenario name.
		List<String> lines = Files.readAllLines(optimizedFile.toPath()).stream()
				.filter(line -> !line.trim().startsWith("////"))
				.collect(Collectors.toList());
		lines.set(0, "scenario " + optimizedName.replace(AsmetaV.SCENARIO_EXTENSION, ""));
		
		// Add non equality checks (not used for optimization) from the original scenario
		// directly adding new lines to the optimized file
		List<String> result = new ArrayList<>();
		int state = 0;
		for (String line : lines) {
		    if (line.trim().equals("step")) {
		        for (Check check : converter.getOtherChecks().getOrDefault(state, Collections.emptyList())) {
		            result.add("check " + check.getExpression() + ";");
		        }
		        state++;
		    }
		    result.add(line);
		}
		// Add non equality checks for the last state
		for (Check check : converter.getOtherChecks().getOrDefault(state, Collections.emptyList())) {
            result.add("check " + check.getExpression() + ";");
        }
		
		// Write the final avalla file
		Files.write(optimizedFile.toPath(), result);
	}
}
