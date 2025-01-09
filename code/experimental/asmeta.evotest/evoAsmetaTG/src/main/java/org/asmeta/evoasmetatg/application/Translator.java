package org.asmeta.evoasmetatg.application;

import java.util.List;

/**
 * The {@code Translator} interface defines a contract for generating Avalla
 * scenarios from an Asmeta specification using the Evosuite tool.
 */
public interface Translator {

	/**
	 * Sets the current working directory where the application stores intermediate
	 * files
	 * 
	 * @param workingDir path to the custom working directory.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setWorkingDir(String workingDir) throws SetupException;

	/**
	 * Sets the input path to the file for the translation process.
	 *
	 * @param inputPath the path to the asm file to be translated.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setInput(String inputPath) throws SetupException;

	/**
	 * Sets the output directory for the translation process.
	 *
	 * @param outputDir the output directory.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setOutput(String outputDir) throws SetupException;

	/**
	 * Sets the path to the Java jdk folder used to run Evosuite.
	 * 
	 * @param javaPath path to the java jdk folder.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setJavaPath(String javaPath) throws SetupException;
	
	/**
	 * Sets the path to the Evosuite jar folder.
	 * 
	 * @param evosuitePath path to the Evosuite jar folder.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setEvosuitePath(String evosuitePath) throws SetupException;

	/**
	 * Sets the value of the specified property.
	 *
	 * @param propertyName  the name of the property to set.
	 * @param propertyValue the value to set the property to, interpreted as a
	 *                      boolean.
	 */
	void setOptions(String propertyName, String propertyValue);

	/**
	 * Sets the version of Evosuite.
	 * 
	 * @param evosuiteVersion the version of evosuite.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setEvosuiteVersion(String evosuiteVersion) throws SetupException;

	/**
	 * Set the time budget in seconds for the Evosuite process.
	 * 
	 * @param timeBudget time budget in seconds.
	 * @throws SetupException if there are errors during the setup.
	 */
	void setTimeBudget(String timeBudget) throws SetupException;

	/**
	 * Executes the translation process:
	 * <p>
	 * - Runs the asmetal2java service to get the translation of the asm
	 * specification java test class and the java test wrapper class, then compiles
	 * them with the selected java version;<br>
	 * - Runs Evosuite jar to generate junit scenarios on the compiled classes;<br>
	 * - Runs the junit2avalla service to translate the junit scenarios into Avalla
	 * scenarios;<br>
	 * Finally cleans the compiled class files.
	 *
	 * @throws TranslationException if there is an error during the translation
	 *                              process.
	 */
	void generate() throws TranslationException;

	/**
	 * Set the clean option status.
	 * 
	 * @param clean {@code true} activate the clean option, {@code false} deactivate the clean
	 *              option.
	 */
	void setClean(boolean clean);

	/**
	 * Cleans up resources.
	 */
	void clean();

	/**
	 * Get the names of all the options of the translator.
	 * 
	 * @return list of string containing the names of all the translator options.
	 */
	List<String> getOptionNames();

	/**
	 * Get the description of all the options of the translator.
	 * 
	 * @return string containing the description of all the translator options.
	 */
	String getOptionsDescription();

}
