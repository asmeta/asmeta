package org.asmeta.junit2avalla.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {
	
	/** Path to the input file. */
	private Path inputPath;
	
	/** File manager instance for handling file operations. */
	private static FileManager fileManager = new FileManager();

    /**
     * Empty constructor of {@code TranslatorImpl} .
     */
	public TranslatorImpl() {
		// Empty constructor
	}

	@Override
	public void setInput(String input) throws FileNotFoundException {
		this.inputPath = Paths.get(input);
	}

	@Override
	public void setOutput(String outputDir) throws IOException {
		fileManager.setOutputDir(outputDir);
	}

	@Override
	public void generate() throws IOException {
		
		File inputFile = fileManager.retrieveInput(inputPath);
		
		fileManager.runTheApplication(inputFile.toPath());
		
	}

	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

}
