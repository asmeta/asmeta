package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.testoptimizer.AvallaOptimizer;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.evotest.experiments.utils.YamlManager;

public class OptimizerRunner {

	private static final Logger LOG = Logger.getLogger(OptimizerRunner.class);

	/**
	 * Optimize existing test suites, preserving their directory structure.
	 *
	 * @param args command-line arguments
	 *             <ul>
	 *             <li>{@code args[0]} – the base directory with the existing test
	 *             suites</li>
	 *             <li>{@code args[1]} – the target directory</li>
	 *             <li>{@code args[2]} – the base directory with the asm files</li>
	 *             </ul>
	 */
	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));

		if (args.length != 3) {
			LOG.error("Expected arguments: source directory, target directory, asm base directory.");
			return;
		}

		try {
			Path sourceDir = Path.of(args[0]).toRealPath();
			Files.createDirectories(Path.of(args[1]));
			Path targetDir = Path.of(args[1]).toRealPath();
			Path asmBaseDir = Path.of(args[2]).toRealPath();
			if (!Files.isDirectory(sourceDir) || !Files.isDirectory(asmBaseDir) || targetDir.startsWith(sourceDir)
					|| sourceDir.startsWith(targetDir)) {
				LOG.error("Source and asm paths must be directories; source and target must not overlap.");
				return;
			}

			Environment.timeMngt = TimeMngt.auto_increment;

			// Walk through the source directory and optimize each test suite
			try (Stream<Path> paths = Files.walk(sourceDir)) {
				for (Path suiteDir : paths.filter(Files::isDirectory).toList()) {
					Path outputDir = targetDir.resolve(sourceDir.relativize(suiteDir));
					Files.createDirectories(outputDir);
					if (Files.exists(suiteDir.resolve(YamlManager.FILE_NAME)))
						optimizeSuite(suiteDir, outputDir);
				}
			}
			LOG.info("Optimization completed.");
		} catch (Exception e) {
			LOG.error("Error while optimizing test suites.", e);
		}
	}

	private static void optimizeSuite(Path sourceDir, Path targetDir) {
		LOG.info("\nOptimizing " + sourceDir);
		Map<String, Object> metadata = null;
		float execTime = Float.NaN;
		try {
			metadata = YamlManager.load(sourceDir.resolve(YamlManager.FILE_NAME).toFile());
			File[] scenarios = sourceDir.toFile()
					.listFiles((dir, name) -> name.endsWith(AsmetaV.SCENARIO_EXTENSION));
			if (scenarios == null)
				throw new IOException("Cannot read scenarios in " + sourceDir);

			Instant start = Instant.now();
			boolean failed = false;
			int optimizedCount = 0;
			AvallaOptimizer optimizer = new AvallaOptimizer();
			for (File scenario : scenarios) {
				try {
					optimizer.optimize(scenario.getPath(), targetDir.toString());
					optimizedCount++;
				} catch (Exception e) {
					failed = true;
					LOG.error("Error while optimizing " + scenario, e);
				}
			}
			LOG.info("Optimized " + optimizedCount + "/" + scenarios.length + " scenarios successfully in " + sourceDir);
			execTime = failed ? Float.NaN : Duration.between(start, Instant.now()).toMillis();
		} catch (Exception e) {
			LOG.error("Error while optimizing suite " + sourceDir, e);
		} finally {
			if (metadata != null) {
				try {
					Path asmPath = sourceDir.resolve((String) metadata.get(YamlManager.ASM_PATH)).normalize();
					targetDir.relativize(asmPath);
					YamlManager.write(
							targetDir.toString(),
							(String) metadata.get(YamlManager.ASM_NAME),
							asmPath.toString(),
							execTime, LocalDateTime.now().toString()
						);
				} catch (IOException | IllegalArgumentException e) {
					LOG.error("Error while writing metadata in " + targetDir, e);
				}
			}
		}
	}
}
