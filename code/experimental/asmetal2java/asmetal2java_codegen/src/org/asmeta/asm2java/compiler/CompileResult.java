package org.asmeta.asm2java.compiler;

public class CompileResult {
	public boolean success;
	String message;

	public CompileResult(boolean r, String m) {
		success = r;
		message = m+"\n";
	}

	@Override
	public String toString() {
		return (success ? "OK" : "FAIL:") + message;
	}
	
}