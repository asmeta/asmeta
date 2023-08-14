package org.asmeta.asm2code.main;

import java.io.File;

import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.asm2code.main.TranslatorOptions.CompilerType;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

/** the main generator that calls the hpp and cpp generator and takes care of updating them in case*/
public class AsmetaL2CppGeneratorMain {

	// the generator for the header
	static private HeaderGenerator hGenerator = new HeaderGenerator();

	// the generator for the code 
	static private CppGenerator cppGenerator = new CppGenerator();
	
	
	/**
	 * 
	 * @param asmspec
	 *            the path of the spec
	 * @return true if success
	 * 
	 * @throws Exception
	 */
	static public CompileResult test(String asmspec, TranslatorOptions userOptions) throws Exception {
		//
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser
		TranslatorOptions opt = userOptions;
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));
	
		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		File dir = asmFile.getParentFile();
		assert dir.exists() && dir.isDirectory();
		//
		File cppFile = new File(dir.getPath() + File.separator + name + ".cpp");
		File hFile = new File(dir.getPath() + File.separator + name + ".h");
	
		// delete cpp if exists
		if (cppFile.exists())
			cppFile.delete();
		assert !cppFile.exists();
	
		// delete h if exists
		if (hFile.exists())
			hFile.delete();
		assert !hFile.exists();
	
		System.out.println("\n\n===" + name + " ===================");
		// write H
		try {
			hGenerator.options = userOptions;
			hGenerator.generate(model.getMain(), hFile.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}
		// write CPP
		try {
			cppGenerator.options = userOptions;
			//cppGenerator.generate(model.getMain(), cppFile.getCanonicalPath());
			cppGenerator.generate(model, cppFile.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}
	
		// now compile it
		System.out.println("Generated h file: " + hFile.getCanonicalPath());
		System.out.println("Generated cpp file: " + cppFile.getCanonicalPath());
		CompileResult result =  new CompileResult(true,"");
		if(opt.compilerType != CompilerType.ArduinoCompiler)//se il codice � per arduino, non compila. 
			result = CppCompiler.compile(name + ".cpp", dir.getAbsolutePath(), true, false, true);
	
		
	
		// clean the produced files
		// delete h file
		// hFile.delete();
		// delete cpp file
		// cppFile.delete();
		return result;
	}
	
	

}
