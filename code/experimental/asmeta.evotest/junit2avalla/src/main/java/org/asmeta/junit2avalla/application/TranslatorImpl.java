package org.asmeta.junit2avalla.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {
	
	/** File manager instance for handling file operations. */
	private static FileManager fileManager = new FileManager();

    /**
     * Empty constructor of {@code TranslatorImpl} .
     */
	public TranslatorImpl() {
		// Empty constructor
	}
	
	@Override
	public void setWorkingDir(String workingDir) throws IOException {
		fileManager.setInputDir(workingDir);
	}

	@Override
	public void setInput(String input) throws FileNotFoundException {
		fileManager.setInputFilePath(input);
	}

	@Override
	public void setOutput(String outputDir) throws IOException {
		fileManager.setOutputDir(outputDir);
	}

	@Override
	public void generate() throws IOException {
		
		File inputFile = fileManager.retrieveInput(fileManager.getInputFilePathToString());
		
		fileManager.runTheApplication(inputFile.toPath());
		
	}

	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

}
