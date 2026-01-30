package asmeta.asmetal2java.codegen.application;

/**
 * Exception thrown when an error occurs while parsing an ASM file.
 */
public class AsmParsingException extends RuntimeException {

    /**
	 *  Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code AsmParsingException} with the detail message.
     *
     * @param message the detail message.
     */
	public AsmParsingException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code AsmParsingException} with the detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public AsmParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}