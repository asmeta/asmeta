package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;

/**
 * Translate and compile all the local examples inside the ./example/ folder.
 * <p>
 * 
 * It generates the java code for all the examples (local) except some that are
 * excluded and it compiles them and it check that there are no errors the code
 * is generated and compiled locally in a subdir of the examples
 */
public class GeneratorCompilerAllLocalExamplesTest extends GeneratorCompileTest {


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

	@Test
	public void testSingleExamplewithproblems() throws Exception {
		String fileName = Paths.get("examples", "ATM3.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		System.out.println(genandcompile);
	}

	@Test
	public void testSingleExamplewithproblems2() throws Exception {
		String fileName = Paths.get("examples", "QuickSort.asm").toString();
		CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName , options,
				GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
		System.out.println(genandcompile);
	}	
}
