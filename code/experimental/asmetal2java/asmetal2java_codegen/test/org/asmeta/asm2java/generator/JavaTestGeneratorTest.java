package org.asmeta.asm2java.generator;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.impl.TranslatorOptionsImpl;
import org.asmeta.asm2java.evosuite.impl.RulesImpl;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class JavaTestGeneratorTest {

	private static final String SRC_GEN = "../asmetal2java_examples/src/";
	
	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);
	
	/** Instance of the RulesImpl, a Map {name:Rule} collection containing the rules of the Asmeta specification */
	private RulesImpl rulesImpl = new RulesImpl();
	
	/** Generator of the java class used for test generation */
	private JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesImpl);
	
	/** Generator of the _ASM java class */
	private JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesImpl);
	
	
	@Test
	public void genTestandATGClasses() throws Exception {
		
		String asmspec = "examples/RegistroDiCassa.asm";
		
		options.setValue("coverRules", true);
		options.setValue("coverOutputs", true);
		
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);

		File javaFile = new File(SRC_GEN + File.separator + name + ".java");
		if (javaFile.exists())
			javaFile.delete();

		jGeneratorTest.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), options);
		
		assertTrue(javaFile.isFile());		
		assertTrue(javaFile.exists());
		
		File javaATGFile = new File(SRC_GEN + File.separator + name + "_ATG.java");
		if (javaATGFile.exists())
			javaATGFile.delete();
		
		jGeneratorAtg.compileAndWrite(model.getMain(), javaATGFile.getCanonicalPath(), options);
		
		assertTrue(javaATGFile.isFile());		
		assertTrue(javaATGFile.exists());
				
	}
	

}