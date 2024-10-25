package org.asmeta.asm2java.compiler;

import java.nio.file.Path;

import org.asmeta.asm2java.compiler.impl.CompileResultImpl;

/**
 * The {@code CompilerJava} interface defines the contract for compiling the generated Java class.
 */
public interface Compiler {
	
	/**
	 * Compile the generated java file.
	 * 
	 * @param name the name of the java file to compile.
	 * @param directory the Path to the directory where the java file is stored.
	 * @param compileOnly {@code true} to only compile, {@code false} otherwise.
	 * @return A {@link CompileResultImpl} object containing the result of the operation.
	 */
	CompileResult compile(String name, Path directory, boolean compileOnly);

}
