package asmeta.asmetal2java.codegen.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the {@link Compiler} interface, compiles a java file
 * (likely generated from asmeta).
 */
public class CompilerImpl implements Compiler {

	/** Constants */
	private static final String RELEASE_OPTION = "--release";

	/** Logger */
	private final Logger logger = LogManager.getLogger(CompilerImpl.class);

	/**
	 * Default No args constructor.
	 */
	public CompilerImpl() {
		// Empty constructor
	}

	@Override
	public CompileResult compileFile(File javaFile, Path directory, String javaVersion) throws IOException {
		if (!directory.toFile().isDirectory())
			throw new NotValidFileException("The given path does not represent a proper directory");
		if (!javaFile.isFile() || !javaFile.getName().endsWith(".java"))
			throw new NotValidFileException(javaFile + " does not end with .java");

		return compile(Arrays.asList(javaFile), directory.toFile(), Arrays.asList(RELEASE_OPTION, javaVersion));
	}

	@Override
	public CompileResult compileFiles(List<File> files, Path directory, String javaVersion) throws IOException {
		return compile(files, directory.toFile(), Arrays.asList(RELEASE_OPTION, javaVersion));
	}

	/**
	 * Compile a list of java files.
	 * 
	 * @param files     list of java files to compile.
	 * @param directory the directory where to put the .class
	 * @param options   list of options.
	 * @return A {@link CompileResultImpl} object containing the result of the
	 *         operation.
	 * @throws IOException if an I/O error occurs.
	 */
	private CompileResult compile(List<File> files, File directory, List<String> options) throws IOException {

		String message;
		String filesToCompile = files.stream().map(File::getName).collect(Collectors.joining(", "));
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		assert compiler != null;
		StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(diagnostics, null, null);
		logger.info("Compiling the java file {} into the target directory -> {}", filesToCompile, directory);
		logger.info("Generating .class files");
		standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(directory));
		Iterable<? extends JavaFileObject> javaFileObjectsFromFiles = standardJavaFileManager
				.getJavaFileObjectsFromFiles(files);
		CompilationTask task = compiler.getTask(null, standardJavaFileManager, diagnostics, options, null,
				javaFileObjectsFromFiles);
		Boolean result = task.call();

		if (Boolean.TRUE.equals(result)) {
			message = "Successfully compiled file " + filesToCompile;
			logger.info(message);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("Failed to compile:");
			sb.append(System.lineSeparator());
	    	for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
	    		// read error details from the diagnostic object
	    		if(diagnostic.getSource() != null) {
	    			sb.append("File: ").append(diagnostic.getSource().getName()).append(System.lineSeparator());
	    			sb.append("\tLine number: ").append(diagnostic.getLineNumber()).append(", ");
	    			sb.append("Column number: ").append(diagnostic.getColumnNumber()).append(System.lineSeparator());
	    		}
	    		sb.append("\tCause: ").append(diagnostic.getMessage(null)).append(System.lineSeparator());
	    	}
			message = sb.toString();
			logger.error(message);
		}

		standardJavaFileManager.close();
		return new CompileResultImpl(result, message);
	}

}
