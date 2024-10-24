package org.asmeta.asm2java.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;
import org.asmeta.asm2java.NotValidFileException;

/**
 * Implementation of the {@link CompilerAsm2Java} interface.
 */
public class CompilerAsm2JavaImpl implements CompilerAsm2Java {
	
	/** Logger */
	private static Logger logger = Logger.getLogger(CompilerAsm2JavaImpl.class);
	
	/**
	 * Default constructor.
	 */
	public CompilerAsm2JavaImpl() {
		// Empty constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompileResult compile(String name, Path directory, boolean compileOnly) {
		if (!directory.toFile().isDirectory())
			throw new NotValidFileException("The given path does not represent a proper directory");
		if (compileOnly && !name.endsWith(".java"))
			throw new NotValidFileException(name + " does not end with .java");

		String message = "not compiled";

		File sourceFile = new File(directory + File.separator + name);
		
		if (compileOnly) {
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			assert compiler != null;
			StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
			File parent = sourceFile.getAbsoluteFile().getParentFile();
			logger.info("\nExecuting the java file present in the target -> " + parent);
			logger.info("\nGenerating .class files ");
			try {
				standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parent));
			} catch (IOException e) {

				e.printStackTrace();
			}

			Boolean result = compiler.getTask(null, standardJavaFileManager, null, null, null,
					standardJavaFileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();

			if (Boolean.TRUE.equals(result)) {
				message = "\nSuccessfully compiled file " + name + "\n";
				logger.debug(message);
			} else {
				message = "\nFailed to compile file " + name + "\n";
				logger.debug(message);
			}			
		}
		return new CompileResult(true, message);
	}
}

