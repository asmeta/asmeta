package org.asmeta.asm2java.main;

/**
 * The {@code Mode} enum represents different modes of operation for the translation process.
 */
public enum Mode {
    
    /** Mode for translating ASM specifications to Java code. */
    TRANSLATOR("translator"),
    
    /** Mode for compiling the translated Java code. */
    COMPILER("compiler"),
    
    /** Mode for generating an executable Java file from the translation. */
    GENERATE_EXE("generateExe"),
    
    /** Mode for generating a Java application with a GUI from the translation. */
    GENERATE_WIN("generateWin"),
    
    /** Mode for generating a wrapper class to test the generated translation. */
    TEST_GEN("testGen"),
    
    /** Mode for generate a specific translation for test generation. */
    TRANSLATOR_TEST("TranslatorTest"),
    
    /** Custom mode, allowing user-defined settings. */
    CUSTOM("custom");

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
}
