package org.asmeta.asm2code.main;

import org.eclipse.emf.ecore.util.Switch;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptions {
	
	// the dialect 
	enum CompilerType {
		ArduinoCompiler,
		DesktopCompiler
	}
	public CompilerType compilerType;
	
	// code to be formatted?
	public boolean formatter;
	
	public boolean shuffleRandom;
	
	// if false -> create all seq into cpp
	// if true -> only those used (to improve code coverage)
	public boolean optimizeSeqMacroRule;
	
	// Std namespace prefix
	public String stdNamespacePrefix;
	
	// use insert method for initialization of map variables
	public boolean initMapsWithInsert;

	// implement switch using cases
	public boolean useCasesForSwitch;
	
	// use maps or multi-dimensional arrays
	public boolean useMaps;
	
	/** default constructor */
	TranslatorOptions(){
		this(true,false,false,false);
	}
	
	/**
	 * Instantiates a new translator options.
	 *
	 * @param formatter apply the formatter for C code
	 * @param shuffleRandom the shuffle random
	 * @param optmizeSeqRule the optmize seq rule
	 * @param arduinoCompiler the arduino compiler
	 */
	public TranslatorOptions(boolean formatter, boolean shuffleRandom, boolean optmizeSeqRule, boolean arduinoCompiler){
		this(formatter, shuffleRandom, optmizeSeqRule, arduinoCompiler, true, true, true);
	}
	
	/**
	 * Instantiates a new translator options.
	 *
	 * @param formatter apply the formatter for C code
	 * @param shuffleRandom the shuffle random
	 * @param optmizeSeqRule the optmize seq rule
	 * @param arduinoCompiler the arduino compiler
	 * @param initWithInsert initialization type
	 */
	public TranslatorOptions(boolean formatter, boolean shuffleRandom, boolean optmizeSeqRule, boolean arduinoCompiler, boolean initWithInsert
			, boolean useCasesForSwitch, boolean useMaps){
		if (arduinoCompiler) 
			compilerType = CompilerType.ArduinoCompiler;
		else
			compilerType=CompilerType.DesktopCompiler;
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optmizeSeqRule;
		if (compilerType == CompilerType.ArduinoCompiler) 
			stdNamespacePrefix = "std::";
		else
			stdNamespacePrefix = "";
		this.initMapsWithInsert = initWithInsert;
		this.useCasesForSwitch = useCasesForSwitch;
		this.useMaps = useMaps;
	}



}
