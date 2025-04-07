package asmeta.evotest.junit2avalla.javascenario;

import java.util.List;

import asmeta.evotest.junit2avalla.model.Scenario;

/**
 * This class represents the parser, it has an abstract method {@code parseJavaScenario}
 * that must be redefined in the child classes
 */
abstract class Parser {

	/**
	 * Parse the JUnit file.
	 * 
	 * @param javaFile string containing the JUnit file to parse.
	 * @param fileName the name of the file
	 * @return list of Scenario Objects (not null and not empty).
	 */
	abstract List<Scenario> parseJavaScenario(String fileName, String javaFile);

}
