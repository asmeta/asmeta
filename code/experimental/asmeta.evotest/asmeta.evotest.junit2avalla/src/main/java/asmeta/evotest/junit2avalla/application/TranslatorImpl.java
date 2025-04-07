package asmeta.evotest.junit2avalla.application;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.javascenario.JUnitParseException;
import asmeta.evotest.junit2avalla.javascenario.ScenarioReader;
import asmeta.evotest.junit2avalla.javascenario.ScenarioReaderImpl;

/**
 * The {@code TranslatorImpl} class implements the {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {

	/** File manager instance for handling file operations. */
	private final FileManager fileManager = new FileManager();

	/** ScenarioReader instance to parse the JUnit file */
	private final ScenarioReader scenarioReader = new ScenarioReaderImpl();

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
	public void setParser(String parser) throws SetupException {
		try {
			scenarioReader.setParserType(parser);
		} catch (IllegalArgumentException e) {
			logger.error("Failed to set the parser type");
			throw new SetupException("Unable to set the parser type", e);
		}
	}

	@Override
	public void generate() throws SetupException, TranslationException, JUnitParseException {
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
			fileManager.runTheApplication(inputFile.toPath(), scenarioReader);
		} catch (JUnitParseException e) {
			throw e;
		} catch (Exception e) {
			// if its not a parse error, the exception is related to the translation process
			logger.error("Failed to translate the input file {}", inputFile);
			throw new TranslationException("Unable to translate the input file " + inputFile, e);
		}

	}

	@Override
	public void clean() {
		fileManager.cleanInputDir();
	}

}
