package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.compiler.CompileResultImpl;
import asmeta.asmetal2java.codegen.compiler.Compiler;
import asmeta.asmetal2java.codegen.compiler.CompilerImpl;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;
import asmeta.asmetal2java.codegen.evosuite.RulesMap;

/**
 * Process all the local examples inside the ./example/ folder and generates the
 * test generation translation for Evosuite.
 */
public class GeneratorCompilerEvosuiteTest {

	/**
	 * Path of the directory where the example files are stored
	 */
	private static final Path path = GeneratorCompilerUtil.dirExamples;

	/**
	 * Path of the directory where the domain test files are stored
	 */
	private static final Path domainTestPath = path.resolve("domainTests");

	/**
	 * Path of the directory where the example files are stored
	 */
	private static final Path dirTestGen = path.resolve("testGen");

	/**
	 * Instance of the RulesImpl, a Map {name:Rule} collection containing the rules
	 * of the Asmeta specification
	 */
	private static final RulesMap rulesMap = new RulesMap();

	/** Generator of the java class used for test generation */
	private static final JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesMap);

	/** Generator of the _ATG java class */
	private static final JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesMap);

	/**
	 * formatter = true, shuffleRandom = true, optimizeSeqRule = true
	 */
	private static final TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

	/**
	 * {@code True} to stop the generation at the first error, {@code False}
	 * otherwise
	 */
	private static boolean failOnError = false;

	/** Files to exclude from the translation */
	private static final List<String> excludeFiles = new ArrayList<>();

	/**
	 * List of asm files with known issues: these files have compilation errors
	 * related to the test generation translation (testGen mode).
	 */
	private static final List<String> testGenErrors = List.of();

	@BeforeClass
	public static void setup() {
		GeneratorCompilerUtil.setupFolders(path);
		File dirTestGenF = dirTestGen.toFile();
		GeneratorCompilerUtil.checkDir(dirTestGenF);
		// exclude libraries
		excludeFiles.addAll(GeneratorCompilerUtil.libraries);
		// exclude examples with translation errors (translation mode)
		excludeFiles.addAll(GeneratorCompilerUtil.parseException);
		excludeFiles.addAll(GeneratorCompilerUtil.runtimeException);
		excludeFiles.addAll(GeneratorCompilerUtil.errors);
		// exclude examples with known errors on test generation mode
		excludeFiles.addAll(testGenErrors);
		// set the options for the test generation mode
		options.setValue(TranslatorOptionsImpl.COVER_RULES_OPTION, true);
		options.setValue(TranslatorOptionsImpl.COVER_OUTPUTS_OPTION, true);
		// ignore the exception for unsupported domains
		options.setValue(TranslatorOptionsImpl.IGNORE_NOT_SUPPORTED_DOMAIN_EXCEPTION, true);
	}

	@Test
	public void translateAndCompileDomainTests() throws Exception {
		List<String> failures = new ArrayList<>();
		System.out.println("Translating and compiling all the local examples inside the " + path + ":");
		List<File> exampleList = Arrays.stream(path.toFile().listFiles()).collect(Collectors.toList());
		// add the domain tests
		exampleList.addAll(Arrays.stream(domainTestPath.toFile().listFiles()).toList());
		for (File file : exampleList) {
			if (file.getName().endsWith(ASMParser.ASM_EXTENSION) && !excludeFiles.contains(file.getName())) {
				CompileResult genandcompile = genandcompileEvosuite(file.getAbsolutePath(), options, dirTestGen,
						GeneratorCompilerUtil.dirCompilazione);
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
		if (failures.size() != 0) {
			System.err.println("Failing for: " + failures.toString());
		}
		assertTrue(failures.size() + ": " + failures.toString(), failures.isEmpty());
	}

	@Test
	public void testFailingAsm() throws Exception {
		// Test if the excluded files actually generates errors.
		System.out.println("Testing the compiler on all the asmeta specifications with translating (testGen) errors:");
		for (String asmspec : testGenErrors) {
			asmspec = path.resolve(asmspec).toString();
			assertFalse(genandcompileEvosuite(asmspec, options, dirTestGen, GeneratorCompilerUtil.dirCompilazione).getSuccess());
		}
	}

	/**
	 * generates the test generation translation for Evosuite and compile it.
	 *
	 * @param asmspec     the path of the spec
	 * @param userOptions the user options
	 * @param targetJava  the target directory where to put the java code generated
	 * @param targetClass the target directory where to put the class - it can be
	 *                    null if compilation is not required
	 * @return the results of the compilation of the java generated
	 * @throws Exception if an error occurs.
	 */
	private CompileResult genandcompileEvosuite(String asmspec, TranslatorOptions userOptions, Path targetJava,
			Path targetClass) throws Exception {

		File asmFile = new File(asmspec);
		assert asmFile.exists();
		assert Files.exists(targetJava) && Files.isDirectory(targetJava);
		String asmName = asmFile.getName().substring(0, asmFile.getName().lastIndexOf("."));

		// PARSE THE SPECIFICATION
		AsmCollection model = GeneratorCompilerUtil.parseSpec(asmFile);

		// 1. name of the generated java file
		// Java translation for test generation
		File javaTestTranslationFile = new File(targetJava.toString() + File.separator + asmName + ".java");
		GeneratorCompilerUtil.deleteExisting(javaTestTranslationFile);
		// Java wrapper class for test generation (ATG)
		File javaAtgFile = new File(targetJava.toString() + File.separator + asmName + "_ATG.java");
		GeneratorCompilerUtil.deleteExisting(javaAtgFile);

		// 2. name of the .class
		File javaTestTranslationCompilationFile = null;
		File javaAtgCompilationFile = null;
		if (targetClass != null) {
			assert Files.exists(targetClass) && Files.isDirectory(targetClass);
			javaTestTranslationCompilationFile = new File(targetClass.toString() + File.separator + asmName + ".class");
			javaAtgCompilationFile = new File(targetClass.toString() + File.separator + asmName + "_ATG.class");
			// delete it if exists
			GeneratorCompilerUtil.deleteExisting(javaTestTranslationCompilationFile);
			GeneratorCompilerUtil.deleteExisting(javaAtgCompilationFile);
		}

		System.out.println("=== " + asmName + " === translating in " + targetJava
				+ (targetJava != null ? " compiling in " + targetClass : ""));

		// 3. Translate to Java
		try {
			rulesMap.resetMap();
			jGeneratorTest.compileAndWrite(model.getMain(), javaTestTranslationFile.getCanonicalPath(), userOptions);
			jGeneratorAtg.compileAndWrite(model.getMain(), javaAtgFile.getCanonicalPath(), userOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResultImpl(false, e.getMessage());
		}
		assert javaTestTranslationFile.exists();
		assert javaAtgFile.exists();
		System.out.println(
				"Generated the Java translation for test generation: " + javaTestTranslationFile.getCanonicalPath());
		System.out.println(
				"Generated the Java wrapper class for test generation (ATG): " + javaAtgFile.getCanonicalPath());

		String separator = "===================================================================================================================================================";

		// 4. compile if required
		if (targetClass != null) {
			Compiler compiler = new CompilerImpl();
			CompileResult result = compiler.compileFiles(Arrays.asList(javaTestTranslationFile, javaAtgFile),
					targetClass, "8");
			System.out.println(separator);
			if (result.getSuccess()) {
				assert javaTestTranslationCompilationFile.exists();
				assert javaAtgCompilationFile.exists();
			}
			return result;
		} else {
			System.out.println(separator);
			return new CompileResultImpl(true, " test gen java files generated with success");
		}
	}

}
