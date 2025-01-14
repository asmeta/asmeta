package asmeta.asm2java.config;

/**
 * This class {@code ModeConstantsConfig} contains several constants that represent the translator's modes.
 */
public class ModeConstantsConfig {
	
	/* Constants */
	
	/**	Translate the generated java class. */
	public static final String TRANSLATOR = "translator";
	
	/** Compile the generated java class.*/
	public static final String COMPILER = "compiler";
	
	/** Generate an executable Java class. */
	public static final String GENERATE_EXE = "generateExe";
	
	/** Generate an executable Java class with the GUI. */
	public static final String GENERATE_WIN = "generateWin";
	
	/** Generate a class for test generation.*/
	public static final String TEST_GEN = "testGen";
	
	/** Generate a specific translation for test generation. */
	public static final String TRANSLATOR_TEST = "translatorTest";
	
	/** Custom mode, allowing user-defined settings.*/
	public static final String CUSTOM = "custom";
	
	/**
	 * Empty private constructor to prevent class instantiation
	 */
	private ModeConstantsConfig() {
		// Empty constructor
	}
	

}
