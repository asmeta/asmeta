package org.asmeta.visualdesigner.validation;

public class AsmValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AsmValidationException(String message) {
        super(message);
    }

    public AsmValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}