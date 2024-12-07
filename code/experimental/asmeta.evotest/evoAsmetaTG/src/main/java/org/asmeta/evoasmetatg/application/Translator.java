package org.asmeta.evoasmetatg.application;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * @param workingDirPath path to the custom working directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setWorkingDir(String workingDir) throws IOException;

	/**
	 * Sets the input path to the file for the translation process.
	 *
	 * @param inputPath the path to the asm file to be translated.
	 * @throws FileNotFoundException if the file is not found.
	 */
	void setInput(String inputPath) throws FileNotFoundException;

	/**
	 * Sets the output directory for the translation process.
	 *
	 * @param outputDir the output directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setOutput(String outputDir) throws IOException;

	/**
	 * Sets the path to the Java jdk folder used to run Evosuite.
	 * 
	 * @param javaPath path to the java jdk folder.
	 * @throws FileNotFoundException if the file is not found.
	 */
	void setJavaPath(String javaPath) throws FileNotFoundException;

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
	 * @param evosuite the version of evosuite.
	 */
	void setEvosuiteVersion(String evosuiteVersion);

	/**
	 * Set the time budget in seconds for the Evosuite process.
	 * 
	 * @param timeBudget time budget in seconds.
	 */
	void setTimeBudget(String timeBudget);

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
	 * @throws IOException          if an I/O error occurs.
	 */
	void generate() throws TranslationException, IOException;

	/**
	 * Set the clean option status.
	 * 
	 * @param clean {@true} activate the clean option, {@false} deactivate the clean
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
