package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;
import asmeta.asmetal2java.codegen.evosuite.RulesMap;

/**
 * Translate an asmeta specification using the testGen mode (generate a test
 * class suited for test generation with Evosuite)
 */
public class JavaTestGeneratorTest {

	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);

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
		if (javaFile.exists())
			javaFile.delete();

		jGeneratorTest.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), options);

		assertTrue(javaFile.isFile());
		assertTrue(javaFile.exists());

		File javaATGFile = new File(GeneratorCompilerUtil.dirTraduzione + File.separator + name + "_ATG.java");
		if (javaATGFile.exists())
			javaATGFile.delete();

		jGeneratorAtg.compileAndWrite(model.getMain(), javaATGFile.getCanonicalPath(), options);

		assertTrue(javaATGFile.isFile());
		assertTrue(javaATGFile.exists());

	}

}
