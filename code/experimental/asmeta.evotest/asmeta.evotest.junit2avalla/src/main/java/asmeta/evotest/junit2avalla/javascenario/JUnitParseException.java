package asmeta.evotest.junit2avalla.javascenario;

/**
 * Exception thrown when an error occurs while parsing an JUnit file.
 */
public class JUnitParseException extends RuntimeException {
    
    /**
	 *  Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code JUnitParseException} with the detail message.
     *
     * @param message the detail message.
     */
	public JUnitParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code JUnitParseException} with the detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public JUnitParseException(String message, Throwable cause) {
        super(message, cause);
    }
}