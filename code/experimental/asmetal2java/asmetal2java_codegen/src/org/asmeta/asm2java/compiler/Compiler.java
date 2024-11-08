package org.asmeta.asm2java.compiler;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

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
	 * @param javaVersion version of the java compiler to use.
	 * @return A {@link CompileResultImpl} object containing the result of the operation.
	 */
	CompileResult compileFile(String name, Path directory, boolean compileOnly, String javaVersion);
	
	/**
	 * Compile the generated java files with java8 in order to be used by Evosuite.
	 * 
	 * @param files list of java files to compile.
	 * @param directory the Path to the directory where the java files are stored.
	 * @param javaVersion version of the java compiler to use.
	 * @return A {@link CompileResultImpl} object containing the result of the operation.
	 */
	CompileResult compileFiles(List<File> files, Path directory, String javaVersion);

}
