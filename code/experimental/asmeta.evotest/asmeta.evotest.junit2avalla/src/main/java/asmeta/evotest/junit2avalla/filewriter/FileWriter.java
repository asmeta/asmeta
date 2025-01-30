package asmeta.evotest.junit2avalla.filewriter;

import java.io.IOException;
import java.nio.file.Path;

import asmeta.evotest.junit2avalla.model.ScenarioFile;

/**
 * Interface for writing {@link ScenarioFile} objects to a file.
 */
public interface FileWriter {

	/**
	 * Writes the specified {@link ScenarioFile} to a default location.
	 *
	 * @param scenarioFile the {@link ScenarioFile} to write
	 * @return {@code true} if the write operation was successful, {@code false}
	 *         otherwise
	 * @throws IOException
	 */
	boolean writeToFile(ScenarioFile scenarioFile) throws IOException;

	/**
	 * Writes the specified {@link ScenarioFile} to the given output path.
	 *
	 * @param scenarioFile the {@link ScenarioFile} to write
	 * @param outputFolder the {@link Path} representing the desired output location
	 * @return {@code true} if the write operation was successful, {@code false}
	 *         otherwise
	 * @throws IOException
	 */
	boolean writeToFile(ScenarioFile scenarioFile, Path outputFolder) throws IOException;
}
