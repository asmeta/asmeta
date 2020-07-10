package org.asmeta.asm2code.compiler;

public class CompileResult {
	public boolean success;
	String message;

	public CompileResult(boolean r, String m) {
		success = r;
		message = m;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return (success ? "OK" : "FAIL:") + message;
	}
}