package org.asmeta.asm2java.main;

import java.util.List;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptions {

	// don't make private formatter, shuffleRandom and optimizeSeqMacroRule
	// because the xtend classes access them directly
	
	/** Indicates whether the generated code should be formatted. */
	boolean formatter;

	/** Indicates whether a random shuffle should be applied. */
	boolean shuffleRandom;

	/**
	 * Indicates whether to optimize the sequence macro rule.
	 * <p>
	 * If set to {@code true}, only the sequence macros that are actually used will be included,
	 * which can improve code coverage.
	 * </p>
	 */
	boolean optimizeSeqMacroRule;
	
	// these can be private
	
	/** Indicates to translate the generated Java class */
	private boolean translator;
	
	/** Indicates to compile the generated java class */
	private boolean compiler;
	
	/** Indicates to generate an executable of the generated Java class */
	private boolean executable;
	
	/** Indicates to export the generated Java files into the output folder */
	private boolean export;
	

	/**
	 * Constructs a {@code TranslatorOptions} instance with the default settings.
	 */
	TranslatorOptions(){
		this(true,true,true,true,false,false,true);
	}

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values.
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 * @param compiler 		      whether to compile the generated java class.
	 * @param optimizeSeqRule     whether to execute the generated java class.
	 * @param optimizeSeqRule     whether to translate the generated java class.
	 */
	public TranslatorOptions(boolean formatter,
			boolean shuffleRandom, 
			boolean optimizeSeqRule, 
			boolean translator, 
			boolean executable, 
			boolean compiler,
			boolean export){
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optimizeSeqRule;
		this.translator = translator;
		this.executable = executable;
		this.compiler = compiler;
		this.export = export;
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
	 * @return {@code true} if the java class should be executed, {@code false} otherwise.
	 */
	public boolean getExecutable() {
		return executable;
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
	 * Sets the value of the specified property.
	 *
	 * @param propertyName  the name of the property to set.
	 *                      Valid values are "formatter", "shuffleRandom", and "optimizeSeqMacroRule".
	 * @param propertyValue the value to set the property to, interpreted as a boolean.
	 */
	public void setValue(String propertyName, String propertyValue) {
		switch(propertyName) {
		case "formatter":
			formatter = Boolean.parseBoolean(propertyValue);
			break;
		case "shuffleRandom":
			shuffleRandom  = Boolean.parseBoolean(propertyValue);
			break;
		case "optimizeSeqMacroRule":
			optimizeSeqMacroRule = Boolean.parseBoolean(propertyValue);
			break;
		case "translator":
			translator = Boolean.parseBoolean(propertyValue);
			break;
		case "compiler":
			compiler = Boolean.parseBoolean(propertyValue);
			break;
		case "executable":
			executable = Boolean.parseBoolean(propertyValue);
			break;
		case "export":
			export = Boolean.parseBoolean(propertyValue);
			break;
		}
	}
	
	/**
	 * Get all the name of the properties.
	 * 
	 * @return a List of String containing all the property names.
	 */
	public static List<String> getPriopertyNames() {
		return List.of(
				"formatter", 
				"shuffleRandom", 
				"optimizeSeqMacroRule", 
				"translator", 
				"compiler", 
				"executable", 
				"export"
				);
	}
	
	/**
	 * Get a description of the options.
	 * 
	 * @return a String containing the description of all the options.
	 */
	public static String getDescription() {
		return "use value for given translator property (optional):\n"
				+ " -Dformatter = true/false : to format the generated code.\n"
				+ " -DshuffleRandom = true/false : use random shuffle.\n"
				+ " -DoptimizeSeqMacroRule = true/false : remove unused seq rules.\n"
				+ " -Dtranslator = true/false : translate the asm file into a java class.\n"
				+ " -Dcompiler = true/false : translate and compile the asm file into a java class.\n"
				+ " -Dexecutable = true/false : generate a java class for execution.\n"
				+ " -Dexport = true/false : export the generated file into the output folder.\n";
	}

}
