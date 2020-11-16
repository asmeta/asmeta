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
	
	static private Logger logger = Logger.getLogger(CompilatoreJava.class);
	
	
	public static CompileResult compile(String name, File directory, boolean compileOnly, boolean evalCoverage) {
		
		     System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_191");
		    
			
		     assert directory.isDirectory();	
		     assert !compileOnly || name.endsWith(".java") : name + " does not end with .java";
		     
		     String messaggio = "non compilato";
		
		     File sourceFile = new File("examples/compilazione/"+ name);
		     
		     if(compileOnly)
		     {
		    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		 		assert compiler != null;
		 		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		 		File parent = sourceFile.getAbsoluteFile().getParentFile();
		 		System.out.println("\nEsecuzione del file java presente nella destinazione - > " + parent);
		 		System.out.println("\nGenerazione dei file .class ");
		 		try {
					fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parent));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		 		
		 		
		 		compiler.getTask(null, fileManager, null, null, null,
						fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();
				
				if(compiler.getTask(null, fileManager, null, null, null,
						fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call() == true)
					  {
					   System.out.println("\nCompilazione del file "+ name + " riuscita\n");
					   messaggio = "\nCompilazione del file "+ name + " riuscita\n";
					  }
				else
				{
					
			
					System.out.println("\nCompilazione del file "+ name + " non riuscita\n");
					messaggio = "\nCompilazione del file "+ name + " non riuscita\n";
				}	
		     }
		
			
			return new CompileResult(true, messaggio);
			
	}
	
	

}
