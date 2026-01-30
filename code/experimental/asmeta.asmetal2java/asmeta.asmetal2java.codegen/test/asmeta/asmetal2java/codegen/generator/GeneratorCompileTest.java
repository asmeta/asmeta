package asmeta.asmetal2java.codegen.generator;

import java.nio.file.Path;

import org.junit.BeforeClass;

import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;

/**
 *
 */
abstract public class GeneratorCompileTest {

	/**
	 * Path of the directory where the example files are stored
	 */
	protected static final Path path = GeneratorCompilerUtil.dirExamples;

	/**
	 * formatter = true, shuffleRandom = true, optimizeSeqRule = true
	 */
	protected TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

	/**
	 * {@code True} to stop the generation at the first error, {@code False}
	 * otherwise
	 */
	static boolean failOnError = false;

	@BeforeClass
	public static void setup() {
		GeneratorCompilerUtil.setupFolders(path);
	}
}
