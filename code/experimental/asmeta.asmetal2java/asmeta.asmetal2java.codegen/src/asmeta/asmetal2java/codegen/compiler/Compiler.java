package asmeta.asmetal2java.codegen.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * The {@code CompilerJava} interface defines the contract for compiling the generated Java class.
 */
public interface Compiler {

	/**
	 * Compile the generated java file.
	 *
	 * @param javaFile the java file to compile.
	 * @param directory the Path to the directory where to put the .class
	 * @param javaVersion version of the java compiler to use.
	 * @return A {@link CompileResultImpl} object containing the result of the operation.
	 * @throws IOException if an I/O error occurs.
	 */
	CompileResult compileFile(File javaFile, Path directory, String javaVersion) throws IOException;

	/**
	 * Compile a list of generated java files.
	 *
	 * @param files list of java files to compile.
	 * @param directory the Path to the directory where to put the .class
	 * @param javaVersion version of the java compiler to use.
	 * @return A {@link CompileResultImpl} object containing the result of the operation.
	 * @throws IOException if an I/O error occurs.
	 */
	CompileResult compileFiles(List<File> files, Path directory, String javaVersion) throws IOException;

}
