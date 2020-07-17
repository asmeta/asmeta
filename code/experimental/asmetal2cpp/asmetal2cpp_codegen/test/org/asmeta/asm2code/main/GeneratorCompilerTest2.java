package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.asm2code.main.TranslatorOptions.CompilerType;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class GeneratorCompilerTest2 {

	// the generator for the header
	static private HeaderGenerator hGenerator = new HeaderGenerator();

	// the generator for the code 
	static private CppGenerator cppGenerator = new CppGenerator();
	
	static protected TranslatorOptions options= new TranslatorOptions(false, true, true, true);

	// resturn all the file in a directory
	void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(".asm")) {
				files.add(file);
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			}
		}
	}

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
			cppGenerator.generate(model.getMain(), cppFile.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}
		
		CompileResult result = new CompileResult(true, "");
		if (options.compilerType != CompilerType.ArduinoCompiler)
		{
			// now compile it
			System.out.println("Generated h file: " + hFile.getCanonicalPath());
			System.out.println("Generated cpp file: " + cppFile.getCanonicalPath());
			result = CppCompiler.compile(name + ".cpp", dir.getAbsolutePath(), true, false);
		}
		// clean the produced files
		// delete h file
		// hFile.delete();
		// delete cpp file
		// cppFile.delete();
		return result;
	}

}
