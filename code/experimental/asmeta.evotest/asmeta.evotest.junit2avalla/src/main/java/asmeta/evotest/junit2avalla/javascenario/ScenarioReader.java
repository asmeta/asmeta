package asmeta.evotest.junit2avalla.javascenario;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import asmeta.evotest.junit2avalla.model.Scenario;

/**
 * The {@code ScenarioReader} interface defines the contract for reading and
 * parsing Java scenario files.
 */
public interface ScenarioReader {

	/**
	 * Sets the type of the parser.
	 * 
	 * @param type string containing the type of the parser.
	 * @throws IllegalArgumentException if no matching type is found
	 */
	public void setType(String type) throws IllegalArgumentException;

	/**
	 * Reads a java scenario from the file at the specified {@code path} and parses
	 * its content to retrieve a list of {@link Scenario} objects.
	 *
	 * @param path the {@link Path} to the file containing the scenario
	 * @return a list of {@link Scenario} objects parsed from the file, or an empty
	 *         list if an error occurs
	 * @throws IOException if an I/O error occurs.
	 */
	public List<Scenario> readJavaScenario(Path path) throws IOException;

}
