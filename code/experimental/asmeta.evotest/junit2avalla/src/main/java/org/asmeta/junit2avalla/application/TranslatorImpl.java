package org.asmeta.junit2avalla.application;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {
	
	/** File manager instance for handling file operations. */
	private final FileManager fileManager = new FileManager();
	
	/** Logger */
	private final Logger logger = LogManager.getLogger(TranslatorImpl.class);

    /**
     * Empty constructor of {@code TranslatorImpl} .
     */
	public TranslatorImpl() {
		// Empty constructor
	}
	
	@Override
	public void setWorkingDir(String workingDir) throws SetupException {
		try {
			fileManager.setInputDir(workingDir);
		} catch (Exception e) {
			logger.error("Failed to set the working directory");
			throw new SetupException("Unable to set the working directory", e);
		}
	}

	@Override
	public void setInput(String input) throws SetupException {
		try {
			fileManager.setInputFilePath(input);
		} catch (Exception e) {
			logger.error("Failed to set the input file");
			throw new SetupException("Unable to set the input file", e);
		}
	}

	@Override
	public void setOutput(String outputDir) throws SetupException {
		try {
			fileManager.setOutputDir(outputDir);
		} catch (Exception e) {
			logger.error("Failed to set the output directory");
			throw new SetupException("Unable to set the output directory", e);
		}
	}

	@Override
	public void generate() throws SetupException, TranslationException {
		// retrieve the input file
		File inputFile = null;
		try {
			inputFile = fileManager.retrieveInput(fileManager.getInputFilePathToString());
		} catch (Exception e) {
			logger.error("Failed to retrieve the input file");
			throw new SetupException("Unable to retrieve the input file", e);
		}
		// generate the translation
		try {
			fileManager.runTheApplication(inputFile.toPath());
		} catch (Exception e) {
			logger.error("Failed to translate the input file {}", inputFile);
			throw new TranslationException("Unable to translate the input file " + inputFile, e);
		}
		
	}

	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

}
