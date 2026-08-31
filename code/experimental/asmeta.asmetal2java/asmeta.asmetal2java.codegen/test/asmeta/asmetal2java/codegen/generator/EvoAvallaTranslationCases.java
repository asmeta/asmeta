package asmeta.asmetal2java.codegen.generator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.compiler.CompilerImpl;
import asmeta.asmetal2java.codegen.config.ModeConstantsConfig;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;
import asmeta.asmetal2java.codegen.evosuite.RulesMap;

/**
 * Focused cases for the Java generators used by EvoAvalla.
 *
 *
 * <p>Generated Java sources and classes are retained under
 * {@code examples/evoavallaTranslationCases/generated} for inspection.</p>
 */
public class EvoAvallaTranslationCases {

	private static final Path CASES = Path.of("examples", "evoavallaTranslationCases");
	private static final Path GENERATED = CASES.resolve("generated");
	private static final Path CLASSES = GENERATED.resolve("classes");
	private static final String COMPILER_VERSION = "9";

	@BeforeAll
	static void createOutputDirectories() throws Exception {
		Files.createDirectories(GENERATED);
		Files.createDirectories(CLASSES);
	}

	@Test
	@Tag("TestToMavenSkip")
	void productAndTupleTermMustGenerateCompilableTestAndAtgClasses() throws Exception {
		translateAndCompile("ProductTupleInitialization.asm");
	}

	@Test
	@Tag("TestToMavenSkip")
	void nestedTermsInInitializationMustNotRepeatAssignmentOperator() throws Exception {
		translateAndCompile("ConditionalTermInitialization.asm");
		translateAndCompile("ConditionalTermInitialization_static.asm");
	}

	@Test
	@Tag("TestToMavenSkip")
	void enumCaseTermInitializationMustGenerateCompilableTestAndAtgClasses() throws Exception {
		translateAndCompile("CaseTermEnumInitialization.asm");
		translateAndCompile("CaseTermEnumInitialization_static.asm");
	}

//	@Test
//	@Tag("TestToMavenSkip")
//	void sequenceInitializationMustGenerateCompilableTestAndAtgClasses() throws Exception {
//		translateAndCompile("SequenceInitialization.asm");
//	}
//
//	@Test
//	@Tag("TestToMavenSkip")
//	void powersetInitializationMustGenerateCompilableTestAndAtgClasses() throws Exception {
//		translateAndCompile("PowersetInitialization.asm");
//	}

	@Test
	void naturalMustGenerateCompilableTestAndAtgClasses() throws Exception {
		translateAndCompile("Natural.asm");
	}

//	@Test
//	@Tag("TestToMavenSkip")
//	void extendRuleMustGenerateCompilableTestAndAtgClasses() throws Exception {
//		translateAndCompile("ExtendRule.asm");
//	}

	private void translateAndCompile(String specPath) throws Exception {
		Path specification = CASES.resolve(specPath);
		String asmName = specPath.substring(0, specPath.lastIndexOf('.'));
		Path testJava = GENERATED.resolve(asmName + ".java");
		Path atgJava = GENERATED.resolve(asmName + "_ATG.java");

		AsmCollection model = GeneratorCompilerUtil.parseSpec(specification.toFile());
		RulesMap rules = new RulesMap();
		JavaTestGenerator testGenerator = new JavaTestGenerator(rules);
		JavaAtgGenerator atgGenerator = new JavaAtgGenerator(rules);
		TranslatorOptions options = translatorOptions();

		Files.deleteIfExists(testJava);
		Files.deleteIfExists(atgJava);
		testGenerator.compileAndWrite(model.getMain(), testJava.toString(), options);
		atgGenerator.compileAndWrite(model.getMain(), atgJava.toString(), options);

		CompileResult compilation = new CompilerImpl().compileFiles(
				List.of(testJava.toFile(), atgJava.toFile()), CLASSES, COMPILER_VERSION);
		assertTrue(compilation.getSuccess(),
				() -> specPath + " generated invalid EvoAvalla Java:\n" + compilation);
	}

	private TranslatorOptions translatorOptions() {
		TranslatorOptions options = new TranslatorOptionsImpl();
		options.setValue(ModeConstantsConfig.TRANSLATOR, false);
		options.setValue(ModeConstantsConfig.GENERATE_EXE, false);
		options.setValue(ModeConstantsConfig.GENERATE_WIN, false);
		options.setValue(ModeConstantsConfig.TEST_GEN, true);
		options.setValue(ModeConstantsConfig.COMPILER, true);
		options.setValue(TranslatorOptionsImpl.COVER_OUTPUTS_OPTION, true);
		return options;
	}

}
