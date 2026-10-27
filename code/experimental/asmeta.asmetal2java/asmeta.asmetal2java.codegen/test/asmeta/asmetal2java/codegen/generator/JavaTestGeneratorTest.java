package asmeta.asmetal2java.codegen.generator;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.compiler.CompilerImpl;
import asmeta.asmetal2java.codegen.config.ChooseMode;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;
import asmeta.asmetal2java.codegen.evosuite.RulesMap;

/**
 * Translate an asmeta specification using the testGen mode (generate a test
 * class suited for test generation with Evosuite)
 */
public class JavaTestGeneratorTest {

	private TranslatorOptions options = new TranslatorOptionsImpl(true, ChooseMode.PICK, true);

	/**
	 * Instance of the RulesImpl, a Map {name:Rule} collection containing the rules
	 * of the Asmeta specification
	 */
	private RulesMap rulesImpl = new RulesMap();

	/** Generator of the java class used for test generation */
	private JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesImpl);

	/** Generator of the _ASM java class */
	private JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesImpl);

	@Before
	public void setup() {
		GeneratorCompilerUtil.setupFolders(GeneratorCompilerUtil.dirExamples);
		// ignore the exception for unsupported domains
		options.setValue(TranslatorOptionsImpl.IGNORE_NOT_SUPPORTED_DOMAIN_EXCEPTION, true);
	}

	@Test
	public void genTestandATGClasses() throws Exception {

		String asmspec = GeneratorCompilerUtil.dirExamples.resolve("RegistroDiCassa.asm").toString();

		options.setValue(TranslatorOptionsImpl.COVER_RULES_OPTION, true);
		options.setValue(TranslatorOptionsImpl.COVER_OUTPUTS_OPTION, true);

		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);

		File javaFile = new File(GeneratorCompilerUtil.dirTraduzione + File.separator + name + ".java");
		if (javaFile.exists()) {
			javaFile.delete();
		}

		jGeneratorTest.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), options);

		assertTrue(javaFile.isFile());
		assertTrue(javaFile.exists());

		File javaATGFile = new File(GeneratorCompilerUtil.dirTraduzione + File.separator + name + "_ATG.java");
		if (javaATGFile.exists()) {
			javaATGFile.delete();
		}

		jGeneratorAtg.compileAndWrite(model.getMain(), javaATGFile.getCanonicalPath(), options);

		assertTrue(javaATGFile.isFile());
		assertTrue(javaATGFile.exists());

	}

	@Test
	public void choiceRecorderIsGeneratedOnlyForTestGeneration() throws Exception {
		Path asmPath = GeneratorCompilerUtil.dirExamples.resolve("chooseTest").resolve("ChooseFromDT.asm");
		final AsmCollection model = ASMParser.setUpReadAsm(asmPath.toFile());

		RulesMap choiceRules = new RulesMap();
		JavaTestGenerator testGenerator = new JavaTestGenerator(choiceRules);
		JavaAtgGenerator atgGenerator = new JavaAtgGenerator(choiceRules);
		Path testJava = GeneratorCompilerUtil.dirTraduzione.resolve("ChooseFromDT.java");
		Path atgJava = GeneratorCompilerUtil.dirTraduzione.resolve("ChooseFromDT_ATG.java");

		testGenerator.compileAndWrite(model.getMain(), testJava.toString(), options);
		atgGenerator.compileAndWrite(model.getMain(), atgJava.toString(), options);

		String testSource = Files.readString(testJava);
		String atgSource = Files.readString(atgJava);
		int nonEmptyGuard = testSource.indexOf("point0.isEmpty()");
		int randomSelection = testSource.indexOf("nextInt(0, point0.size())");
		int selectedValue = testSource.indexOf("point0.get(rndm)");

		assertTrue(nonEmptyGuard >= 0);
		assertTrue(nonEmptyGuard < randomSelection);
		assertTrue(randomSelection < selectedValue);
		assertTrue(testSource.contains("private static void __asmetaRecordChoice"));
		assertFalse(testSource.contains("_ATG.__asmetaRecordChoice"));
		assertTrue(testSource.contains("Character.toString((char) 36) + \"b\""));
		assertTrue(atgSource.contains("private static void __asmetaStartChoiceRecording"));
		assertTrue(atgSource.contains("private static String[][] __asmetaStopChoiceRecording"));
		assertFalse(atgSource.contains("void __asmetaRecordChoice"));
		assertTrue(atgSource.indexOf("ChooseFromDT.__asmetaBeginStep();") < atgSource.indexOf("this.execution.updateASM();"));

		CompileResult compilation = new CompilerImpl().compileFiles(
				List.of(testJava.toFile(), atgJava.toFile()), GeneratorCompilerUtil.dirCompilazione, "8");
		assertTrue(compilation.getSuccess(), compilation.toString());

		Path regularJava = GeneratorCompilerUtil.dirTraduzione.resolve("ChooseFromDT_regular.java");
		new JavaGenerator().compileAndWrite(model.getMain(), regularJava.toString(), options);
		assertFalse(Files.readString(regularJava).contains("__asmetaRecordChoice"));
	}

}
