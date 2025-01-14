package asmeta.evoasmetatg.application;

/**
 * The {@code TranslationException} class represents an exception that is
 * thrown when an error occurs during the translation process.
 */
public class TranslationException extends Exception {

	/** Constant "Translation error: " */
    private static final String TRANSLATION_ERROR = "Translation error: ";
	/** Serialization identifier for this exception class. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code TranslationException} with a default error message.
     */
    public TranslationException() {
        super(TRANSLATION_ERROR + "error while generating the translation.");
    }

    /**
     * Constructs a {@code TranslationException} with a custom error message.
     *
     * @param message the detail message describing the error.
     */
    public TranslationException(String message) {
        super(TRANSLATION_ERROR + message);
    }
    
    /**
     * Constructs a {@code TranslationException} with the cause.
     *
     * @param cause the original cause of the error.
     */
    public TranslationException(Throwable cause) {
        super(TRANSLATION_ERROR + cause);
    }

    /** 
     * Constructs a {@code TranslationException} with a custom error message and the cause.
     * 
     * @param message the detail message describing the error.
     * @param cause the original cause of the error.
     */
	public TranslationException(String message, Throwable cause) {
		super(TRANSLATION_ERROR + message, cause);
	}
}
