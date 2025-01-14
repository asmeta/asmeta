package asmeta.asm2java.config;

/**
 * The {@code Mode} enum represents different modes of operation for the translation process.
 */
public enum Mode {
    
    /** Mode for translating ASM specifications to Java code. */
    TRANSLATOR_MODE(ModeConstantsConfig.TRANSLATOR),
    
    /** Mode for compiling the translated Java code. */
    COMPILER_MODE(ModeConstantsConfig.COMPILER),
    
    /** Mode for generating an executable Java file from the translation. */
    GENERATE_EXE_MODE(ModeConstantsConfig.GENERATE_EXE),
    
    /** Mode for generating a Java application with a GUI from the translation. */
    GENERATE_WIN_MODE(ModeConstantsConfig.GENERATE_WIN),
    
    /** Mode for generating a wrapper class to test the generated translation. */
    TEST_GEN_MODE(ModeConstantsConfig.TEST_GEN),
    
    /** Mode for generate a specific translation for test generation. */
    TRANSLATOR_TEST_MODE(ModeConstantsConfig.TRANSLATOR_TEST),
    
    /** Custom mode, allowing user-defined settings. */
    CUSTOM_MODE(ModeConstantsConfig.CUSTOM);
	
    /** The string representation of the mode. */
    private final String value;

    /**
     * Constructs a {@code Mode} with the specified string value.
     * 
     * @param value the string representation of the mode.
     */
    Mode(String value) {
        this.value = value;
    }

    /**
     * Returns the string value associated with this mode.
     * 
     * @return the string value of this mode.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a {@code Mode} constant based on the provided string value.
     * 
     * @param value the string representation of the mode.
     * @return the {@code Mode} constant matching the value.
     * @throws IllegalArgumentException if the provided value does not match any mode.
     */
    public static Mode fromValue(String value) {
        for (Mode mode : Mode.values()) {
            if (mode.getValue().equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Not a valid value for mode: " + value);
    }
    
    /**
     * Get the description of the various modes of the application
     * 
     * @return a String containing the description of the modes.
     */
    public static String getDescription() {
    	return "-mode " + TRANSLATOR_MODE.value + " : translate the asm file to a java file (default).\n"
    			+ "-mode " + GENERATE_EXE_MODE.value + " : translate the asm file to a java file and generate an executable java class\n"
    			+ "-mode " + GENERATE_WIN_MODE.value + " : translate the asm file to a java file and generate an executable java class with a Grapical User Interace (GUI)\n"
    			+ "-mode " + TEST_GEN_MODE.value + " : generate a test class suited for test generation with Evosuite\n"
    			+ "-mode " + CUSTOM_MODE.value + " : set a custom behavior by adding properties with -D (see help)";
    }
}
