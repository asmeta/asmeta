package org.asmeta.asm2java.compiler;

public class CompileResultImpl implements CompileResult{
	private boolean success;
	String message;

	public CompileResultImpl(boolean r, String m) {
		success = r;
		message = m+"\n";
	}

	@Override
	public String toString() {
		return (success ? "OK" : "FAIL:") + message;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getSuccess() {
		return success;
	}
	
}