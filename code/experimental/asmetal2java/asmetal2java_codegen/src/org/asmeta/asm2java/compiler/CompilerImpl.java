package org.asmeta.asm2java.compiler;

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

import org.apache.log4j.Logger;
import org.asmeta.asm2java.exceptions.NotValidFileException;

/**
 * Implementation of the {@link Compiler} interface.
 */
public class CompilerImpl implements Compiler {
	
	/** Logger */
	private static Logger logger = Logger.getLogger(CompilerImpl.class);
	
	/**
	 * Default constructor.
	 */
	public CompilerImpl() {
		// Empty constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompileResult compileFile(String name, Path directory, boolean compileOnly) {
		if (!directory.toFile().isDirectory())
			throw new NotValidFileException("The given path does not represent a proper directory");
		if (compileOnly && !name.endsWith(".java"))
			throw new NotValidFileException(name + " does not end with .java");

		File sourceFile = new File(directory + File.separator + name);
		
		if(compileOnly) {
			return compile(Arrays.asList(sourceFile), directory, null);
		}
		
		return new CompileResultImpl(true, "not compiled.");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompileResult compileFiles(List<File> files, Path directory) {
		// cross-compile with java 8
		return compile(files, directory, Arrays.asList("--release", "8"));
	}

	private CompileResult compile(List<File> files, Path directory, List<String> options) {
		
		String message;
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		assert compiler != null;
		StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
		File parent = directory.toFile();
		logger.info("Compiling the java file present in the target -> " + parent);
		logger.info("Generating .class files ");
		try {
			standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parent));
		} catch (IOException e) {
			logger.error("An exception occurred while setting the location for standardJavaFileManager: "
					+ e.getMessage());
			e.printStackTrace();
		}

		Boolean result = compiler.getTask(null, standardJavaFileManager, null, options, null,
				standardJavaFileManager.getJavaFileObjectsFromFiles(files)).call();

		if (Boolean.TRUE.equals(result)) {
			message = "Successfully compiled file " + files.stream().map(File::getName).collect(Collectors.joining(", "));
			logger.info(message);
		} else {
			message = "Failed to compile file " + files.stream().map(File::getName).collect(Collectors.joining(", "));
			logger.error(message);
		}	
		
		return new CompileResultImpl(true, message);
	}
	
	
	
	
	
}

