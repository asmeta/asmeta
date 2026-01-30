package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.Test;

import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;

/**
 * Translate and compile the domain test examples inside the
 * ./example/domainTest folder.
 */
public class GeneratorCompilerDomainTest {

	/**
	 * Path of the directory where the domain test files are stored
	 */
	private Path domainTestPath = GeneratorCompilerUtil.dirExamples.resolve("domainTests");

	/**
	 * formatter = true, shuffleRandom = true, optimizeSeqRule = true
	 */
	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

	/**
	 * {@code True} to stop the generation at the first error, {@code False}
	 * otherwise
	 */
	static boolean failOnError = false;

	@Before
	public void setup() {
		GeneratorCompilerUtil.setupFolders(domainTestPath);
	}

	@Test
	public void translateAndCompileDomainTests() throws Exception {
		List<String> failures = new ArrayList<>();
		System.out.println("Translating and compiling the domain tests inside the " + domainTestPath + ":");
		for (File file : domainTestPath.toFile().listFiles()) {
			if(file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
				CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(file.getAbsolutePath(), options,
						GeneratorCompilerUtil.dirTraduzione, GeneratorCompilerUtil.dirCompilazione);
				if (!genandcompile.getSuccess()) {
					if (failOnError) {
						fail();
					}
					failures.add(file.getName());
					System.err.println("failing for " + file.getName());
				}
			} else {
				System.err.println("skipping " + file.getName());
			}
		}
		assertTrue(failures.size() + ": " + failures.toString(), failures.isEmpty());
	}
}
