package org.asmeta.asm2java.main;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptions {

	/** Indicates whether the generated code should be formatted. */
	private boolean formatter;

	/** Indicates whether a random shuffle should be applied. */
	private boolean shuffleRandom;

	/**
	 * Indicates whether to optimize the sequence macro rule.
	 * <p>
	 * If set to {@code true}, only the sequence macros that are actually used will be included,
	 * which can improve code coverage.
	 * </p>
	 */
	private boolean optimizeSeqMacroRule;

	/**
	 * Constructs a {@code TranslatorOptions} instance with all options set to {@code false}.
	 */
	TranslatorOptions(){
		this(false,false,false);
	}

	/**
	 * Constructs a {@code TranslatorOptions} instance with the specified option values.
	 *
	 * @param formatter           whether the generated code should be formatted.
	 * @param shuffleRandom       whether a random shuffle should be applied.
	 * @param optimizeSeqRule     whether to optimize the sequence macro rule.
	 */
	public TranslatorOptions(boolean formatter, boolean shuffleRandom, boolean optimizeSeqRule){
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optimizeSeqRule;
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
		}
	}

}
