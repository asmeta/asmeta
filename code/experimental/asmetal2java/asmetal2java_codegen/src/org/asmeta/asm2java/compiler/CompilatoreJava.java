package org.asmeta.asm2java.compiler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

public class CompilatoreJava {

	private CompilatoreJava() {}
	
	private static Logger logger = Logger.getLogger(CompilatoreJava.class);

	public static CompileResult compile(String name, File directory, boolean compileOnly) {
		if (!directory.isDirectory())
			throw new RuntimeException("The given path does not represent a proper directory");
		if (compileOnly && !name.endsWith(".java"))
			throw new RuntimeException(name + " does not end with .java");

		String messaggio = "non compilato";

		File sourceFile = new File("examples/compilazione/" + name);
		
		if (compileOnly) {
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			assert compiler != null;
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			File parent = sourceFile.getAbsoluteFile().getParentFile();
			logger.info("\nEsecuzione del file java presente nella destinazione - > " + parent);
			logger.info("\nGenerazione dei file .class ");
			try {
				fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parent));
			} catch (IOException e) {

				e.printStackTrace();
			}

			Boolean result = compiler.getTask(null, fileManager, null, null, null,
					fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();

			if (Boolean.TRUE.equals(result)) {
				messaggio = "\nCompilazione del file " + name + " riuscita\n";
				logger.debug(messaggio);
			} else {
				messaggio = "\nCompilazione del file " + name + " non riuscita\n";
				logger.debug(messaggio);
			}			
		}
		return new CompileResult(true, messaggio);
	}
}
