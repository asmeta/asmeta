package org.asmeta.asm2code.main;

import java.io.File;
import java.util.List;

import org.asmeta.parser.ASMParser;

public class GeneratorCompilerTest2 {

	
	static protected TranslatorOptions testOptions = new TranslatorOptions(false, true, true, true);

	// return all the file in a directory
	void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(ASMParser.asmExtension)) {
				files.add(file);
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			}
		}
	}

}
