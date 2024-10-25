package org.asmeta.asm2java.generator.impl;

import org.apache.log4j.Logger;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.evosuite.RulesImpl;
import org.asmeta.asm2java.generator.Generator;
import org.asmeta.asm2java.generator.JavaAtgGenerator;
import org.asmeta.asm2java.generator.JavaExeGenerator;
import org.asmeta.asm2java.generator.JavaGenerator;
import org.asmeta.asm2java.generator.JavaTestGenerator;
import org.asmeta.asm2java.generator.JavaWindowGenerator;

import asmeta.structure.Asm;

/**
 *  The {@code GeneratorImpl} class implements the {@link Generator} interface.
 */
public class GeneratorImpl implements Generator {
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(GeneratorImpl.class);
	
	/** Generator of the java class */
	private static final JavaGenerator jGenerator = new JavaGenerator();

	/** Generator of the _Exe java class */
	private static final JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	
	/** Generator of the _Win java class */
	private static final JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();
	
	/** Instance of the RulesImpl, a Map {name:Rule} collection containing the rules of the Asmeta specification */
	private static RulesImpl rulesImpl = new RulesImpl();
	
	/** Generator of the java class used for test generation */
	private static JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesImpl);
	
	/** Generator of the _ASM java class */
	private static JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesImpl);

	/**
	 * Default constructor
	 */
	public GeneratorImpl() {
		// Empty constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateJava(Asm asm, String writerPath, TranslatorOptions userOptions) {
		logger.info("JavaGenerator: generating the .java class...");
		jGenerator.compileAndWrite(asm, writerPath, userOptions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateExe(Asm asm, String writerPath, TranslatorOptions userOptions) {
		logger.info("JavaExeGenerator: generating the _Exe.java class...");
		jGeneratorExe.compileAndWrite(asm, writerPath, userOptions);	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateWin(Asm asm, String writerPath, TranslatorOptions userOptions) {
		logger.info("JavaWinGenerator: generating the _Win.java class...");
		jGeneratorWin.compileAndWrite(asm, writerPath, userOptions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateTest(Asm asm, String writerPath, TranslatorOptions userOptions) {
		logger.info("JavaAtgGenerator: generating the .java test class...");
		jGeneratorTest.compileAndWrite(asm, writerPath, userOptions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateAtg(Asm asm, String writerPath, TranslatorOptions userOptions) {
		logger.info("JavaAtgGenerator: generating the _ATG.java class...");
		jGeneratorAtg.compileAndWrite(asm, writerPath, userOptions);
	}

}
