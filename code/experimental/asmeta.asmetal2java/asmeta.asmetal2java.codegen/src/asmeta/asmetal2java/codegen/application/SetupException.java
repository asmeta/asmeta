package asmeta.asmetal2java.codegen.application;

/**
 * Exception thrown when an error occurs while setting the parameters of the application.
 */
public class SetupException extends Exception {

    /**
	 *  Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code SetupException} with the detail message.
     *
     * @param message the detail message.
     */
	public SetupException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code SetupException} with the detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public SetupException(String message, Throwable cause) {
        super(message, cause);
    }

}
