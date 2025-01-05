package org.asmeta.asm2java.compiler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;
import org.asmeta.asm2java.NotValidFileException;

// compiles a java (likely generated from asmeta) 
public class CompilatoreJava {

	private CompilatoreJava() {
	}

	private static Logger logger = Logger.getLogger(CompilatoreJava.class);

	/**
	 * Compile.
	 *
	 * @param name        the name of the .java to compile
	 * @param directory   the directory where to put the .class
	 * @param compileOnly the compile only
	 * @return the compile result
	 * @throws IOException 
	 */
	public static CompileResult compile(String name, File directory) throws IOException {
		if (!directory.isDirectory())
			throw new NotValidFileException("The given path does not represent a proper directory");
		if (!name.endsWith(".java"))
			throw new NotValidFileException(name + " does not end with .java");

		String messaggio;

		// File sourceFile = new File("examples/compilazione/" + name);
		//File sourceFile = new File(directory + File.separator + "compilazione" + File.separator + name);
		File sourceFile = new File(name);

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		assert compiler != null;
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		//File parent = sourceFile.getAbsoluteFile().getParentFile();
		//logger.info("\nEsecuzione del file java presente nella destinazione - > " + parent);
		logger.info("\nGenerazione dei file .class");
		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(directory));
		Iterable<? extends JavaFileObject> javaFileObjectsFromFiles = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
		CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,javaFileObjectsFromFiles);
		Boolean result = task.call();
		if (Boolean.TRUE.equals(result)) {
			messaggio = "\nCompilazione del file " + name + " riuscita\n";
			logger.debug(messaggio);
		} else {
			messaggio = "\nCompilazione del file " + name + " non riuscita\n";
			logger.debug(messaggio);
		}
		fileManager.close();
		return new CompileResult(result, messaggio);
	}
}
