/**
 * The compiler package contains the {@link Compiler} and {@link CompileResult}
 * interfaces that expose outside the package methods to compile the file, or a
 * list of files and to obtain the result of the operation. The implementations
 * are performed by the {@link CompilerImpl} and {@link CompileResultImpl}
 * classes. The compiler provides the choice of the java version with which
 * compile the generated java files, this is because if you want to use Evosuite
 * to generate test scenarios it is necessary to compile the files with the java
 * version used by Evosuite, instead if you are interested in the classical
 * translation you can use the default version which is the 17. Finally the
 * package contains the {@link NotValidFileException} thrown if the compiler has
 * errors while searching for files.
 */
package asmeta.asmetal2java.codegen.compiler;
