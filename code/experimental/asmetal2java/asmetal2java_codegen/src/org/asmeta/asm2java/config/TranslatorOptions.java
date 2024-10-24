package org.asmeta.asm2java.config;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptions {

	/* Constants */
	private static final String FORMATTER = "formatter";
	private static final String SHUFFLE_RANDOM = "shuffleRandom";
	private static final String OPTIMIZE_SEQ_MACRO_RULE = "optimizeSeqMacroRule";
	private static final String COVER_OUTPUTS = "coverOutputs";
	private static final String COVER_RULES = "coverRules";
	private static final String EXPORT = "export";

	/** Logger */
	private static final Logger logger = Logger.getLogger(TranslatorOptions.class);

	// don't make private formatter, shuffleRandom and optimizeSeqMacroRule
	// because the xtend classes access them directly
	
	/** Indicates whether the generated code should be formatted. */
	boolean formatter;

	/** Indicates whether a random shuffle should be applied. */
	boolean shuffleRandom;

	/** Indicates whether to optimize the sequence macro rule (remove unused seq rules). */
	boolean optimizeSeqMacroRule;
	
	// these can be private
	
	/** Indicates to translate the generated Java class. */
	private boolean translator;
	
	/** Indicates to compile the generated java class. */
	private boolean compiler;
	
	/** Indicates to generate an executable of the generated Java class. */
	private boolean generateExe;
	
	/** Indicates to generate an executable of the generated Java class with a Graphical User Interface (GUI). */
	private boolean generateWin;
	
	/** Indicates to generate a class designed for generating tests with Evosuite for the generated Java class. */
	private boolean testGen;
	
	/** Indicates to add methods to cover the outputs in the testgen class. */
	private boolean coverOutputs;
	
	/** Indicates to add methods to cover the rules in the testgen class. */
	private boolean coverRules;
	
	/** Indicates to export the generated Java files into the output folder */
	private boolean export;
	
	/**
	 * A map that associates property names with actions that modify the corresponding boolean fields.
	 *
	 * This map is used to replace a switch-case block, simplifying the logic for setting 
	 * property values by dynamically invoking the corresponding setter action.
	 */
	private HashMap<String,Consumer<Boolean>> propertyMapper;

	
	/**
	 * Constructs a {@code TranslatorOptions} instance with the default settings: <p>
	 * formatter, shuffleRandom, optimizeSeqRule, translator, coverRules, export = {@code true}.
	 * All the others = {@code false}.
	 */
	public TranslatorOptions(){
		this.formatter = true;
		this.shuffleRandom = true;
		this.optimizeSeqMacroRule = true;
		this.translator = true;
		this.generateExe = false;
		this.generateWin = false;
		this.compiler = false;
		this.testGen = false;
		this.coverOutputs = false;
		this.coverRules = true;
		this.export = true;
		mapperSetup();
	}

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values
	 * (Considering only the specific properties related to the translation process).
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 */
	public TranslatorOptions(boolean formatter,
			boolean shuffleRandom, 
			boolean optimizeSeqRule) {
		this();
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optimizeSeqRule;
	}
	
    /**
     * Initializes the mapping between property names and their corresponding actions.
     */
	private void mapperSetup() {
		this.propertyMapper = new HashMap<>();
		this.propertyMapper.put(FORMATTER, value -> formatter = value);
		this.propertyMapper.put(SHUFFLE_RANDOM, value -> shuffleRandom = value);
		this.propertyMapper.put(OPTIMIZE_SEQ_MACRO_RULE, value -> optimizeSeqMacroRule = value);
		this.propertyMapper.put(ModeConstantsConfig.TRANSLATOR, value -> translator = value);
		this.propertyMapper.put(ModeConstantsConfig.COMPILER, value -> compiler = value);
		this.propertyMapper.put(ModeConstantsConfig.GENERATE_EXE, value -> generateExe = value);
		this.propertyMapper.put(ModeConstantsConfig.GENERATE_WIN, value -> generateWin = value);
		this.propertyMapper.put(ModeConstantsConfig.TEST_GEN, value -> testGen = value);
		this.propertyMapper.put(COVER_OUTPUTS, value -> coverOutputs = value);
		this.propertyMapper.put(COVER_RULES, value -> coverRules = value);
		this.propertyMapper.put(EXPORT, value -> export = value);
	}
	
	/**
	 * Sets the value of the specified property.
	 *
	 * @param propertyName  the name of the property to set.
	 * @param propertyValue the boolean value to set the property to.
	 * @throws IllegalArgumentException if the specified property name is not recognized
	 */
	public void setValue(String propertyName, boolean propertyValue) {
		Consumer<Boolean> action = propertyMapper.get(propertyName);

        if (action != null) {
            action.accept(propertyValue);
            logger.info("Setting the translator option " + propertyName + " to " + propertyValue + ".");
        } else {
            logger.error("Failed to set the value: " + propertyName);
            throw new IllegalArgumentException("Unexpected value: " + propertyName);
        }
	}

	/**
	 * Returns the random shuffle property.
	 *
	 * @return {@code true} if a random shuffle should be applied, {@code false} otherwise.
	 */
	public boolean getShuffleRandom() {
		return shuffleRandom;
	}

	/**
	 * Returns the formatted property.
	 *
	 * @return {@code true} if the generated code should be formatted, {@code false} otherwise.
	 */
	public boolean getFormatter() {
		return formatter;
	}

	/**
	 * Returns the optimizeSeqMacroRule property.
	 *
	 * @return {@code true} if the sequence macro rule should be optimized, {@code false} otherwise.
	 */
	public boolean getOptimizeSeqMacroRule() {
		return optimizeSeqMacroRule;
	}
	
	/**
	 * Returns the compiler property.
	 *
	 * @return {@code true} if the java class should be compiled, {@code false} otherwise.
	 */
	public boolean getTranslator() {
		return translator;
	}
	
	/**
	 * Returns the compiler property.
	 *
	 * @return {@code true} if the java class should be compiled, {@code false} otherwise.
	 */
	public boolean getCompiler() {
		return compiler;
	}
	
	/**
	 * Returns the executor property.
	 *
	 * @return {@code true} to generate an executable Java class, {@code false} otherwise.
	 */
	public boolean getExecutable() {
		return generateExe;
	}
	
	/**
	 * Returns the window property.
	 *
	 * @return {@code true} to generate an executable Java class with the Graphical User Interface (GUI), {@code false} otherwise.
	 */
	public boolean getWindow() {
		return generateWin;
	}
	
	/**
	 * Returns the testGen property.
	 *
	 * @return {@code true} if we want to generate a class designed for generating tests with Evosuite, {@code false} otherwise.
	 */
	public boolean getTestGen() {
		return testGen;
	}
	
	/**
	 * Returns the coverOutputs property.
	 *
	 * @return {@code true} if we want to cover the outputs in the testGen class, {@code false} otherwise.
	 */
	public boolean getCoverOutputs() {
		return coverOutputs;
	}
	
	/**
	 * Returns the coverRules property.
	 *
	 * @return {@code true} if we want to cover the rules in the testGen class, {@code false} otherwise.
	 */
	public boolean getCoverRules() {
		return coverRules;
	}
	
	/**
	 * Returns the export property.
	 *
	 * @return {@code true} if the java class should be exported, {@code false} otherwise.
	 */
	public boolean getExport() {
		return export;
	}
	
	/**
	 * Get all the name of the properties.
	 * 
	 * @return a List of String containing all the property names.
	 */
	public static List<String> getPropertyNames() {
		return List.of(
				FORMATTER, 
				SHUFFLE_RANDOM, 
				OPTIMIZE_SEQ_MACRO_RULE, 
				ModeConstantsConfig.TRANSLATOR, 
				ModeConstantsConfig.COMPILER, 
				ModeConstantsConfig.GENERATE_EXE,
				ModeConstantsConfig.GENERATE_WIN,
				ModeConstantsConfig.TEST_GEN,
				COVER_OUTPUTS,
				COVER_RULES,
				EXPORT
				);
	}
	
	/**
	 * Get a description of the options.
	 * 
	 * @return a String containing the description of all the options.
	 */
	public static String getDescription() {
		return "use value for given translator property (the default value is in brackets):\n"
				+ " -D" + FORMATTER + " (true)/false : to format the generated code.\n"
				+ " -D" + SHUFFLE_RANDOM + " = (true)/false : use random shuffle.\n"
				+ " -D" + OPTIMIZE_SEQ_MACRO_RULE + " = (true)/false : remove unused seq rules.\n"
				+ " -D" + ModeConstantsConfig.TRANSLATOR + " = (true)/false : translate the asm file to a java class.\n"
				+ " -D" + ModeConstantsConfig.COMPILER + " = true/(false) : translate and compile the generated java class.\n"
				+ " -D" + ModeConstantsConfig.GENERATE_EXE + " = true/(false) : generate a java class for execution.\n"
				+ " -D" + ModeConstantsConfig.GENERATE_WIN + " = true/(false) : generate an executable java class with a GUI.\n"
				+ " -D" + ModeConstantsConfig.TEST_GEN + " = true/(false) : generate a specific java class designed for test generation with Evosuite.\n"
				+ " -D" + COVER_OUTPUTS + " = true/(false) : cover the outputs in the testGen class.\n"
				+ " -D" + COVER_RULES + " = (true)/false : cover the rules in the testGen class.\n"
				+ " -D" + EXPORT + " = (true)/false : export the generated file into the output folder.\n"
				+ " Note: Please use " + ModeConstantsConfig.TRANSLATOR + ", " + ModeConstantsConfig.COMPILER + ", " + ModeConstantsConfig.GENERATE_EXE + ", " + ModeConstantsConfig.GENERATE_WIN + " and " + ModeConstantsConfig.TEST_GEN 
				+ " options only if you have selected the -mode custom option.";
	}

}
