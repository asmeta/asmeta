package org.asmeta.asm2java.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;

import asmeta.AsmCollection;

public class GeneratorCompilerUtil {
	
	
	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();
	// static private JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	// static private JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();
	
	/**
	 * generates the java code and compiles it.
	 *
	 * @param asmspec the path of the spec
	 * @param userOptions the user options
	 * @param targetJava the target directory where to put the java code generated
	 * @param targetClass the target directory where to put the class - it can be null if compilation is not required
	 * @return the results of the compilation of the java generated
	 * @throws Exception the exception
	 */
	public static CompileResult genandcompile(String asmspec, TranslatorOptions userOptions, Path targetJava, Path targetClass) throws Exception {
		//
		// get the name of the asmeta spec
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser - if error ndo not continue
		AsmCollection model = null;
		try{
			model = ASMParser.setUpReadAsm(asmFile);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			// assuming that the specs are correct
			fail("error in parsing asmeta example: " + asmspec);
		}
		assert model != null;

		// get the names of the files
//		String dirEsecuzione = asmFile.getParentFile().getPath() + "/esecuzione";
//		String dirWin = asmFile.getParentFile().getPath() + "/window";
//		File javaFileExe = new File(dirEsecuzione + File.separator + name + "_Exe.java");
//		File javaFileExeN = new File(dirEsecuzione + File.separator + name + ".java");
//		File javaFileWin = new File(dirWin + File.separator + name + "_Win.java");
//		File javaFileWinN = new File(dirWin + File.separator + name + ".java");
//		
//		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");
//		File javaFileExeT = new File(dirTraduzione + File.separator + name + "_Exe.java");
//		File javaFileWinT = new File(dirTraduzione + File.separator + name + "_Win.java");

		
		// 1. name of the generated java file
		assert Files.exists(targetJava) && Files.isDirectory(targetJava);
		File javaFile = new File(targetJava.toString() + File.separator + name + ".java");		
		// Se il file java esiste di già, lo cancella
		if (javaFile.exists())
			javaFile.delete();
		assert !javaFile.exists();

		// 2. name of the .class
		File javaFileCompilazione = null;
		if (targetClass!= null) {
			assert Files.exists(targetClass) && Files.isDirectory(targetClass);
			javaFileCompilazione = new File(targetClass.toString() + File.separator + name + ".class");
			// delete it if exists
			if (javaFileCompilazione.exists())
				javaFileCompilazione.delete();
			assert !javaFileCompilazione.exists();
		}
//		
//		if (javaFileExe.exists())
//			javaFileExe.delete();
//		assert !javaFileExe.exists();
//		
//		if (javaFileExeN.exists())
//			javaFileExeN.delete();
//		assert !javaFileExeN.exists();
//		
//		if (javaFileWin.exists())
//			javaFileWin.delete();
//		assert !javaFileWin.exists();
//		
//		if (javaFileWinN.exists())
//			javaFileWinN.delete();
//		assert !javaFileWinN.exists();
//
//		if (javaFileT.exists())
//			javaFileT.delete();
//		assert !javaFileT.exists();
//		
//		if (javaFileExeT.exists())
//			javaFileExeT.delete();
//		assert !javaFileExeT.exists();
//		
//		if (javaFileWinT.exists())
//			javaFileWinT.delete();
//		assert !javaFileWinT.exists();

		
		System.out.println("\n\n ===" + name + " === translating in " + targetJava + (targetJava!= null? " compiling in " + targetClass :""));
		//
		// TRANSALTE to Java
		//
		try {
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExe.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileExeN.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWin.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileWinN.getCanonicalPath(), userOptions);

//			jGenerator.compileAndWrite(model.getMain(), javaFileT.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExeT.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWinT.getCanonicalPath(), userOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}
		assert javaFile.exists();
		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileCompilazione.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileExeN.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileWinN.getCanonicalPath());
//		System.out.println("Generated java file for the execution: " + javaFileExe.getCanonicalPath());
//		System.out.println("Generated java file for window: " + javaFileWin.getCanonicalPath());

//		System.out.println("All java files Generated in: " + javaFileT.getCanonicalPath());

		//
		// COMPILE java if required
		// 
		if (targetClass != null) {
			CompileResult result = CompilatoreJava.compile(javaFile.toString(), targetClass.toFile());
			if (result.getSuccess())
				assert javaFileCompilazione.exists();
			return result;
		} else {
			return new CompileResult(true, " java generated with success");
		}

	}


}
