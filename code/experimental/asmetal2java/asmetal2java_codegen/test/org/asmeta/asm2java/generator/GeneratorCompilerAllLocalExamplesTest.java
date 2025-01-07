package org.asmeta.asm2java.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

	static boolean failOnError = false;

	/** File to exclude from the translation */
	static List<String> excludeFiles = new ArrayList<>();

	@BeforeClass
	public static void setup() {
		excludeFiles.addAll(GeneratorCompilerTestInProjectTest.libraries);
		excludeFiles.addAll(GeneratorCompilerTestInProjectTest.parseException);
		excludeFiles.addAll(GeneratorCompilerTestInProjectTest.runtimeException);
		excludeFiles.addAll(GeneratorCompilerTestInProjectTest.errors);
	}

	@Test
	public void testAllLocalExamples() throws Exception {
		List<String> failures = new ArrayList<>();
		Path path = Paths.get("examples/");
		assertTrue(path.toFile().exists() && path.toFile().isDirectory());
		// build where to put the .java and the .class
		String dirCompilazione = path.toString() + File.separator + "compilazione";
		String dirTraduzione = path.toString() + File.separator + "traduzione";
		File dirCompF = new File(dirCompilazione);
		File dirTradF = new File(dirCompilazione);
		// create if it does not exists
		GeneratorCompilerUtil.checkDir(dirCompF);
		GeneratorCompilerUtil.checkDir(dirTradF);
		System.out.println("Translating and compiling all the local examples inside the " + path +":");
		// limit depth to 1
		Stream<Path> walk = Files.walk(path, 1);
		walk.forEach(x -> {
			try {
				String fileName = x.toFile().toString();
				if (fileName.endsWith(ASMParser.ASM_EXTENSION)
						&& excludeFiles.stream().filter(tX -> fileName.contains(tX)).count() == 0) {
					CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName, options,
							Path.of(dirTraduzione), Path.of(dirCompilazione));
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
