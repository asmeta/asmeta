package org.asmeta.avalla2junit.main;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;


import org.asmeta.asm2junit.compiler.CompilatoreJava;
import org.asmeta.asm2junit.compiler.CompileResult;
import org.asmeta.asm2junit.main.JUnitGenerator;
import org.asmeta.asm2junit.main.TranslatorOptions;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import asmeta.AsmCollection;

public class Asmetal2JavaTest {

	static private JUnitGenerator jGenerator = new JUnitGenerator();
	private TranslatorOptions options = new TranslatorOptions(true, true, true);
	
	
	@Test
	public void test1() throws IOException, Exception {
	    String asmspec = "examples/esempio1.avalla";
	    if (!test(asmspec, options).success) System.out.println("Fallito"); 
	}
	
	
	
	 static public CompileResult test(String asmspec, TranslatorOptions userOptions) throws Exception {
		//
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser
		
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		
		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		
		
		File dir = asmFile.getParentFile();
		assert dir.exists() && dir.isDirectory();
		
		String dirCompilazione = asmFile.getParentFile().getPath() + "/compilazione";
		String dirTraduzione = asmFile.getParentFile().getPath() + "/Traduzione";
		
		
		//
		File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");
		//File javaFileExeN = new File(dirEsecuzione + File.separator + name + ".java");
		//File javaFileWin = new File(dirWin + File.separator + name + "_Win.java");
		//File javaFileWinN = new File(dirWin + File.separator + name + ".java");
		
		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");

		// Se il file java esiste di gi�, lo cancella 
		
		if (javaFile.exists())
			javaFile.delete();
		assert !javaFile.exists();
		
		if (javaFileCompilazione.exists())
			javaFileCompilazione.delete();
		assert !javaFileCompilazione.exists();
		
		
		/*if (javaFileExeN.exists())
			javaFileExeN.delete();
		assert !javaFileExeN.exists();*/
		
		/*if (javaFileWin.exists())
			javaFileWin.delete();
		assert !javaFileWin.exists();*/
		
		/*if (javaFileWinN.exists())
			javaFileWinN.delete();
		assert !javaFileWinN.exists();*/

		if (javaFileT.exists())
			javaFileT.delete();
		assert !javaFileT.exists();
		
	

		
		System.out.println("\n\n===" + name + " ===================");

		try {
			 
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}
		
		// write java
		
		try {
			
			
			//jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			//jGenerator.compileAndWrite(model.getMain(), javaFileCompilazione.getCanonicalPath(), userOptions);
			//jGenerator.compileAndWrite(model.getMain(), javaFileExeN.getCanonicalPath(), userOptions);
			//jGenerator.compileAndWrite(model.getMain(), javaFileWinN.getCanonicalPath(), userOptions);
			//jGenerator.compileAndWrite(model.getMain(), javaFileT.getCanonicalPath(), userOptions);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}

		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
		
		CompileResult result = CompilatoreJava.compile(name + ".java", dir, true, false);
		
		
		return result;
	}

}