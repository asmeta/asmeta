package org.asmeta.asm2code.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;

abstract public class GeneratorCompilerTest {

	
	private static final String GCC_COMPILER = "g++";
	static protected TranslatorOptions testOptions = new TranslatorOptions(false, true, true, true);
	
	@BeforeClass
	public static void checkandsetCompiler() {
		if (!CppCompiler.isCompilerSet()) {
			// set a standard compiler
			boolean setCompiler = CppCompiler.setCompiler(GCC_COMPILER);
			assertTrue("compiler "+GCC_COMPILER+" not found in " + System.getenv().get("PATH"), setCompiler);
		}
	}

	// return all the file in a directory
	static void listf(String directoryName, List<File> files) {
		listf(directoryName, files, Integer.MAX_VALUE);
	}

	
	// return files in directory, level is the depth
	static void listf(String directoryName, List<File> allAsmFiles, int level) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
				allAsmFiles.add(file);
			} else if (file.isDirectory() && level > 0) {
				listf(file.getPath(), allAsmFiles, --level);
			}
		}
	}


}
