package org.asmeta.asm2java.main;

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
	
	// if true -> only those used (to improve code coverage)
	public boolean optimizeSeqMacroRule;
	
	/** default constructor */
	TranslatorOptions(){
		this(false,false,false);
	}
	
	public TranslatorOptions(boolean formatter, boolean shuffleRandom, boolean optmizeSeqRule){
		compilerType = CompilerType.DesktopCompiler;
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optmizeSeqRule;
	}



}
