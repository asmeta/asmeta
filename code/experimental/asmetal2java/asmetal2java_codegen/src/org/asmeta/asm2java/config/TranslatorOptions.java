package org.asmeta.asm2java.config;

import java.util.List;

public interface TranslatorOptions {

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values
	 * (Considering only the specific properties related to the translation process).
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 */
	void setValue(String propertyName, boolean propertyValue);
	
	/**
	 * Get all the name of the properties.
	 * 
	 * @return a List of String containing all the property names.
	 */
	List<String> getPropertyNames();
	
	/**
	 * Get a description of the options.
	 * 
	 * @return a String containing the description of all the options.
	 */
	String getDescription();	
	
	/**
	 * Returns the random shuffle property.
	 *
	 * @return {@code true} if a random shuffle should be applied, {@code false} otherwise.
	 */
	boolean getShuffleRandom();

	/**
	 * Returns the formatted property.
	 *
	 * @return {@code true} if the generated code should be formatted, {@code false} otherwise.
	 */
	boolean getFormatter();

	/**
	 * Returns the optimizeSeqMacroRule property.
	 *
	 * @return {@code true} if the sequence macro rule should be optimized, {@code false} otherwise.
	 */
	boolean getOptimizeSeqMacroRule();
	
	/**
	 * Returns the compiler property.
	 *
	 * @return {@code true} if the java class should be compiled, {@code false} otherwise.
	 */
	boolean getTranslator();
	
	/**
	 * Returns the compiler property.
	 *
	 * @return {@code true} if the java class should be compiled, {@code false} otherwise.
	 */
	boolean getCompiler();
	
	/**
	 * Returns the executor property.
	 *
	 * @return {@code true} to generate an executable Java class, {@code false} otherwise.
	 */
	boolean getExecutable();
	
	/**
	 * Returns the window property.
	 *
	 * @return {@code true} to generate an executable Java class with the Graphical User Interface (GUI), {@code false} otherwise.
	 */
	boolean getWindow();
	
	/**
	 * Returns the testGen property.
	 *
	 * @return {@code true} if we want to generate a class designed for generating tests with Evosuite, {@code false} otherwise.
	 */
	boolean getTestGen();
	
	/**
	 * Returns the coverOutputs property.
	 *
	 * @return {@code true} if we want to cover the outputs in the testGen class, {@code false} otherwise.
	 */
	boolean getCoverOutputs();
	
	/**
	 * Returns the coverRules property.
	 *
	 * @return {@code true} if we want to cover the rules in the testGen class, {@code false} otherwise.
	 */
	boolean getCoverRules();
	
	/**
	 * Returns the export property.
	 *
	 * @return {@code true} if the java class should be exported, {@code false} otherwise.
	 */
	boolean getExport();
	
}
