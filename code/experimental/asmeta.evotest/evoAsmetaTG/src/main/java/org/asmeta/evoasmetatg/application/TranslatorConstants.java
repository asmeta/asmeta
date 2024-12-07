package org.asmeta.evoasmetatg.application;

import org.asmeta.asm2java.application.FileManager;
import org.asmeta.asm2java.config.ModeConstantsConfig;
import org.asmeta.asm2java.main.Asmeta2JavaCLI;
import org.asmeta.junit2avalla.main.Junit2AvallaCLI;
import org.asmeta.parser.ASMParser;

/**
 * This class contains the constants used by the Translator in the application package.
 */
final class TranslatorConstants {

	static final String LINE_BRANCH = "LINE:BRANCH";

	static final String DASSERTION_STRATEGY_ALL = "-Dassertion_strategy=all";

	static final String DMINIMIZE_TRUE = "-Dminimize=true";
	
	static final String SEARCH_BUDGET = "-Dsearch_budget=";

	static final String CRITERION = "-criterion";

	static final String ASM_EXTENSION = ASMParser.ASM_EXTENSION;
	
	static final String ATG = FileManager.ATG;
	
	static final String JUNIT_TEST_EXTENSION = ATG + "_ESTest.java";
	
	static final String USER_DIR = "user.dir";

	static final String CLASS = "-class";

	static final String TARGET = "-target";

	static final String JAR = "-jar";

	static final String JAVA_9 = "9";

	static final String JAVA_8 = "8";
	
	static final String JAVA_HOME = "JAVA_HOME";

	static final String EVOSUITE_1_2_0_JAR = "evosuite-1.2.0.jar";

	static final String EVOSUITE_1_0_6_JAR = "evosuite-1.0.6.jar";
	
	static final String EVOSUITE_TARGET = "evosuite-target";
	
	static final String EVOSUITE_TESTS = "evosuite-tests";
	
	static final String EVOSUITE_REPORT = "evosuite-report";
	
	static final String EVOSUITE_JAR_DIR = "evosuite-jar";
	
	static final String JAVA_EXE = "java.exe";

	static final String BIN = "bin";

	static final String DASH = "-";

	static final String TEST_GEN = ModeConstantsConfig.TEST_GEN;

	static final String MODE = DASH + Asmeta2JavaCLI.MODE;

	static final String ASMETA2JAVA_OUTPUT = DASH + Asmeta2JavaCLI.OUTPUT;

	static final String ASMETA2JAVA_INPUT = DASH + Asmeta2JavaCLI.INPUT;

	static final String JUNIT2AVALLA_OUTPUT = DASH + Junit2AvallaCLI.OUTPUT;

	static final String JUNIT2AVALLA_INPUT = DASH + Junit2AvallaCLI.INPUT;

	static final String CLEAN = "-clean";

	static final String COMPILER_VERSION = DASH + Asmeta2JavaCLI.COMPILER_VERSION;
	
	static final String OUTPUT = "output";
	
	static final String EVOSUITE_ASCII_ART = """
			 _____                      _ _       
			| ____|_   _____  ___ _   _(_) |_ ___ 
			|  _| \\ \\ / / _ \\/ __| | | | | __/ _ \\
			| |___ \\ V / (_) \\__ \\ |_| | | ||  __/
			|_____| \\_/ \\___/|___/\\__,_|_|\\__\\___|
			""";

	/*
	 * Private constructor to prevent instantiation.
	 */
	private TranslatorConstants() {
		// empty constructor
	}
}