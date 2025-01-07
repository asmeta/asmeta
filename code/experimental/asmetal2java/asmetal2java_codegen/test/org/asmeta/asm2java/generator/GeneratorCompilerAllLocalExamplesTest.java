package org.asmeta.asm2java.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Translate and compile all the local examples inside the ./example/ folder.
 * <p>
 * 
 * It generates the java code for all the examples (local) except some that are
 * excluded and it compiles them and it check that there are no errors the code
 * is generated and compiled locally in a subdir of the examples
 */
public class GeneratorCompilerAllLocalExamplesTest {

	/**
	 * formatter = true, shuffleRandom = true, optimizeSeqRule = true
	 */
	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

	/**
	 * {@code True} to stop the generation at the first error, {@code False}
	 * otherwise
	 */
	static boolean failOnError = false;

	/** Files to exclude from the translation */
	static List<String> excludeFiles = new ArrayList<>();

	@BeforeClass
	public static void setup() {
		excludeFiles.addAll(GeneratorCompilerUtil.libraries);
		excludeFiles.addAll(GeneratorCompilerUtil.parseException);
		excludeFiles.addAll(GeneratorCompilerUtil.runtimeException);
		excludeFiles.addAll(GeneratorCompilerUtil.errors);
	}

	@Test
	public void testAllLocalExamples() throws Exception {
		List<String> failures = new ArrayList<>();
		Path path = GeneratorCompilerUtil.dirExamples;
		assertTrue(path.toFile().exists() && path.toFile().isDirectory());
		// build where to put the .java and the .class
		File dirCompF = GeneratorCompilerUtil.dirCompilazione.toFile();
		File dirTradF = GeneratorCompilerUtil.dirTraduzione.toFile();
		// create if it does not exists
		GeneratorCompilerUtil.checkDir(dirCompF);
		GeneratorCompilerUtil.checkDir(dirTradF);
		System.out.println("Translating and compiling all the local examples inside the " + path + ":");
		// limit depth to 1
		Stream<Path> walk = Files.walk(path, 1);
		walk.forEach(x -> {
			try {
				String fileName = x.toFile().toString();
				if (fileName.endsWith(ASMParser.ASM_EXTENSION)
						&& excludeFiles.stream().filter(tX -> fileName.contains(tX)).count() == 0) {
					CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName, options,
							GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
					if (!genandcompile.getSuccess()) {
						if (failOnError)
							fail();
						failures.add(fileName);
						System.err.println("failing for " + fileName);
					}
				} else {
					System.err.println("skipping " + fileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		walk.close();
		assertTrue(failures.size() + ": " + failures.toString(), failures.isEmpty());
	}

}
