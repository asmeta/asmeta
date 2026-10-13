package org.asmeta.visualdesigner.validation;

public class AsmValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int line;
    private final int column;

    public AsmValidationException(String message) {
        this(message, -1, -1, null);
    }

    public AsmValidationException(String message, Throwable cause) {
        this(message, -1, -1, cause);
    }

    public AsmValidationException(String message, int line, int column, Throwable cause) {
        super(message, cause);
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean hasLocation() {
        return line > 0;
    }
}