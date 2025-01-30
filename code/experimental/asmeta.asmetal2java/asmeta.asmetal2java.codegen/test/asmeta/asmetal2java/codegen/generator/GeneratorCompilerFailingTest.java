package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertFalse;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;

public class GeneratorCompilerFailingTest {

	// disable the formatter
	private TranslatorOptions options = new TranslatorOptionsImpl(false, true, true);

	private static final Path dirExamples = GeneratorCompilerUtil.dirExamples;
	private static final Path dirCompilazione = GeneratorCompilerUtil.dirCompilazione;
	private static final Path dirTraduzione = GeneratorCompilerUtil.dirTraduzione;
	private static final String FIBONACCI_ASM = "fibonacci.asm";
	
	@Before
	public void setup() {
		GeneratorCompilerUtil.setupFolders(dirExamples);
	}

	@Test
	public void testFibonacci() throws Exception {
		String asmspec = dirExamples.resolve(FIBONACCI_ASM).toString();
		assertFalse(GeneratorCompilerUtil.genandcompile(asmspec, options, dirTraduzione, dirCompilazione).getSuccess());
	}

	@Test
	public void testAllErrors() throws Exception {
		System.out.println("Testing the compiler on all the asmeta specifications with translating errors:");
		// List of asmeta specifications with known issues:
		// these files have compilation errors related to the translation.
		for (String asmspec : GeneratorCompilerUtil.errors) {
			// skip the fibonacci.asm to not repeat the testFibonacci()
			if (asmspec.equals(FIBONACCI_ASM))
				continue;
			asmspec = dirExamples.resolve(asmspec).toString();
			assertFalse(
					GeneratorCompilerUtil.genandcompile(asmspec, options, dirTraduzione, dirCompilazione).getSuccess());
		}
	}

}
