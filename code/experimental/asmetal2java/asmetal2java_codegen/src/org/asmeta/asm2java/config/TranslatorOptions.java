package org.asmeta.asm2java.config;

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
	 * Constructs a {@code TranslatorOptions} instance with the default settings: <p>
	 * formatter, shuffleRandom, optimizeSeqRule, translator, coverRules, export = {@code true}.
	 * All the others = {@code false}.
	 */
	public TranslatorOptions(){
		this(true,true,true,true,false,false,false,false,false,true,true);
	}

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values.
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 * @param translator    	  whether to translate the generated java class.
	 * @param generateExe    	  whether to generate an executable Java class.
	 * @param generateWin    	 	  whether to generate an executable Java class with the GUI.
	 * @param compiler 		      whether to compile the generated java class.
	 * @param testGen    		  whether to generate a class for test generation.
	 * @param coverOutputs   	  whether to cover the outputs in the testGen class.
	 * @param coverRules   		  whether to cover the rules in the testGen class.
	 * @param export   	  		  whether to export the classes.
	 */
	public TranslatorOptions(boolean formatter,
			boolean shuffleRandom, 
			boolean optimizeSeqRule, 
			boolean translator, 
			boolean generateExe, 
			boolean generateWin,
			boolean compiler,
			boolean testGen,
			boolean coverOutputs,
			boolean coverRules,
			boolean export){
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optimizeSeqRule;
		this.translator = translator;
		this.generateExe = generateExe;
		this.generateWin = generateWin;
		this.compiler = compiler;
		this.testGen = testGen;
		this.coverOutputs = coverOutputs;
		this.coverRules = coverRules;
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
	 * Sets the value of the specified property.
	 *
	 * @param propertyName  the name of the property to set.
	 * @param propertyValue the boolean value to set the property to.
	 */
	public void setValue(String propertyName, boolean propertyValue) {
		switch(propertyName) {
		case "formatter":
			formatter = propertyValue;
			break;
		case "shuffleRandom":
			shuffleRandom  = propertyValue;
			break;
		case "optimizeSeqMacroRule":
			optimizeSeqMacroRule = propertyValue;
			break;
		case "translator":
			translator = propertyValue;
			break;
		case "compiler":
			compiler = propertyValue;
			break;
		case "generateExe":
			generateExe = propertyValue;
			break;
		case "generateWin":
			generateWin =propertyValue;
			break;
		case "testGen":
			testGen = propertyValue;
			break;
		case "coverOutputs":
			coverOutputs = propertyValue;
			break;
		case "coverRules":
			coverRules = propertyValue;
			break;
		case "export":
			export = propertyValue;
			break;
		}
	}
	
	/**
	 * Get all the name of the properties.
	 * 
	 * @return a List of String containing all the property names.
	 */
	public static List<String> getPropertyNames() {
		return List.of(
				"formatter", 
				"shuffleRandom", 
				"optimizeSeqMacroRule", 
				"translator", 
				"compiler", 
				"generateExe",
				"generateWin",
				"testGen",
				"coverOutputs",
				"coverRules",
				"export"
				);
	}
	
	/**
	 * Get a description of the options.
	 * 
	 * @return a String containing the description of all the options.
	 */
	public static String getDescription() {
		return "use value for given translator property (the default value is in brackets):\n"
				+ " -Dformatter = (true)/false : to format the generated code.\n"
				+ " -DshuffleRandom = (true)/false : use random shuffle.\n"
				+ " -DoptimizeSeqMacroRule = (true)/false : remove unused seq rules.\n"
				+ " -Dtranslator = (true)/false : translate the asm file to a java class.\n"
				+ " -Dcompiler = true/(false) : translate and compile the generated java class.\n"
				+ " -DgenerateExe = true/(false) : generate a java class for execution.\n"
				+ " -DgenerateWin = true/(false) : generate an executable java class with a GUI.\n"
				+ " -DtestGen = true/(false) : generate a specific java class designed for test generation with Evosuite.\n"
				+ " -DcoverOutputs = true/(false) : cover the outputs in the testGen class.\n"
				+ " -DcoverRules = (true)/false : cover the rules in the testGen class.\n"
				+ " -Dexport = (true)/false : export the generated file into the output folder.\n"
				+ " Note: Please use translator, compiler, executable, window and testGen options only if you have selected the -mode custom option.";
	}

}
