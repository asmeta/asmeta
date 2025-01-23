package org.asmeta.asm2java.compiler;

/**
 * Implementation of the {@link CompileResult} interface.
 */
public class CompileResultImpl implements CompileResult {

	/**
	 * {@code true} if the operation is completed with success, {@code false}
	 * otherwise.
	 */
	private boolean success;

	/** the message related to the compiling operation. */
	String message;

	/**
	 * All args constructor.
	 * 
	 * @param r the result of the compilation.
	 * @param m the message related to the compiling operation.
	 */
	public CompileResultImpl(boolean r, String m) {
		success = r;
		message = m + "\n";
	}

	@Override
	public String toString() {
		return (success ? "OK" : "FAIL:") + message;
	}

	@Override
	public boolean getSuccess() {
		return success;
	}

}