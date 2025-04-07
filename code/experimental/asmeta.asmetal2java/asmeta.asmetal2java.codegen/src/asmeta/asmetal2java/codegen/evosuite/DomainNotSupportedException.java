package asmeta.asmetal2java.codegen.evosuite;

/**
 * Exception thrown when an error occurs while parsing an ASM file.
 */
public class DomainNotSupportedException extends RuntimeException {
    
    /**
	 *  Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code DomainNotSupportedException} with the detail message.
     *
     * @param message the detail message.
     */
	public DomainNotSupportedException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DomainNotSupportedException} with the detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public DomainNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}