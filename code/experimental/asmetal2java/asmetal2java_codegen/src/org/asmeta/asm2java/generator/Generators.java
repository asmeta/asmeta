package org.asmeta.asm2java.generator;

import org.asmeta.asm2java.evosuite.RulesMap;

/**
 * The {@code Generators} abstract class exposes the static instances of
 * generators that extend AsmToJavaGenerator.
 */
public final class Generators {

	/** Generator of the java class */
	private static AsmToJavaGenerator jGenerator;

	/** Generator of the _Exe java class */
	private static AsmToJavaGenerator jGeneratorExe;

	/** Generator of the _Win java class */
	private static AsmToJavaGenerator jGeneratorWin;

	/**
	 * Instance of the RulesImpl, a Map {name:Rule} collection containing the rules
	 * of the Asmeta specification
	 */
	private static RulesMap rulesMap;

	/** Generator of the java class used for test generation */
	private static AsmToJavaGenerator jGeneratorTest;

	/** Generator of the _ATG java class */
	private static AsmToJavaGenerator jGeneratorAtg;

	/**
	 * No args constructor
	 */
	private Generators() {
		// Empty constructor
	}

	/**
	 * Returns the singleton instance of the standard Java generator.
	 * 
	 * @return the singleton instance of {@link JavaGenerator}
	 */
	public static AsmToJavaGenerator getJavaGenerator() {
		if (jGenerator == null) {
			jGenerator = new JavaGenerator();
		}
		return jGenerator;
	}

	/**
	 * Returns the singleton instance of the _Exe class generator.
	 * 
	 * @return the singleton instance of {@link JavaExeGenerator}
	 */
	public static AsmToJavaGenerator getJavaExeGenerator() {
		if (jGeneratorExe == null) {
			jGeneratorExe = new JavaExeGenerator();
		}
		return jGeneratorExe;
	}

	/**
	 * Returns the singleton instance of the _Win class generator.
	 * 
	 * @return the singleton instance of {@link JavaWindowGenerator}
	 */
	public static AsmToJavaGenerator getJavaWindowGenerator() {
		if (jGeneratorWin == null) {
			jGeneratorWin = new JavaWindowGenerator();
		}
		return jGeneratorWin;
	}

	/**
	 * Returns the singleton instance of the Java generator for tests.
	 * 
	 * @return the singleton instance of {@link JavaTestGenerator}
	 */
	public static AsmToJavaGenerator getJavaTestGenerator() {
		createRuleMap();
		if (jGeneratorTest == null) {
			jGeneratorTest = new JavaTestGenerator(rulesMap);
		}
		return jGeneratorTest;
	}

	/**
	 * Returns the singleton instance of the _ATG class generator.<p>
	 * 
	 * > Note: use the JavaTestGenerator before the JavaAtgGenerator.
	 * 
	 * @return the singleton instance of {@link JavaAtgGenerator}
	 */
	public static AsmToJavaGenerator getJavaAtgGenerator() {
		if (jGeneratorAtg == null) {
			jGeneratorAtg = new JavaAtgGenerator(rulesMap);
		}
		return jGeneratorAtg;
	}

	/**
	 * Create the singleton instance of the RulesMap.
	 */
	private static void createRuleMap() {
		if (rulesMap == null) {
			rulesMap = new RulesMap();
		}
		rulesMap.resetMap();
	}

}
