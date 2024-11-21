package org.asmeta.evoasmetatg.application;

/**
 * The {@code TranslationException} class represents an exception that is
 * thrown when an error occurs during the translation process.
 */
public class TranslationException extends Exception {

    /** Serialization identifier for this exception class. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code TranslationException} with a default error message.
     */
    public TranslationException() {
        super("Error while generating the translation...");
    }

    /**
     * Constructs a {@code TranslationException} with a custom error message.
     *
     * @param message the detail message describing the error.
     */
    public TranslationException(String message) {
        super("Translation error: " + message);
    }
}
