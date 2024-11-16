package org.asmeta.evoasmetatg.application;

public class TranslationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TranslationException() {
		super("Error while generating the translation...");
	}

	public TranslationException(String message) {
		super("Translation error: " + message);
	}


}
