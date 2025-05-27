package asmeta.asmetal2java.codegen.application;

import java.io.IOException;
import java.util.List;

/**
 * The {@code Translator} interface defines the contract for configuring and
 * running a translation process.
 */
public interface Translator {

	/**
	 * Sets the value of the specified property.
	 *
	 * @param propertyName  the name of the property to set.
	 * @param propertyValue the value to set the property to, interpreted as a
	 *                      boolean.
	 */
	void setOptions(String propertyName, String propertyValue);
	
	/**
	 * Set a custom path for the working directory.
	 * 
	 * @param workingDirPath the path to the working directory.
	 */
	void setWorkingDir(String workingDirPath);

	/**
	 * Sets the input path to the file for the translation process.
	 *
	 * @param inputPath the path to the asm file to be translated.
	 * @throws AsmParsingException if the file doesn't have the Asmeta extension
	 */
	void setInput(String inputPath) throws AsmParsingException;

	/**
	 * Sets the output directory for the translation process. Create a new one if it
	 * doesn't exists.
	 *
	 * @param outputDir the output directory.
	 * @throws IOException if an I/O error occurs.
	 * @throws SetupException 
	 */
	void setOutput(String outputDir) throws SetupException;

	/**
	 * Sets the mode of translation to be used.
	 *
	 * @param mode the mode of translation.
	 */
	void setMode(String mode);

	/**
	 * Executes the translation process.
	 *
	 * @throws AsmParsingException when an error occurs while parsing an ASM file.
	 * @throws SetupException      if an error occurs during the setup process.
	 * @throws TranslationException if an error occurs during the translation process.
	 */
	void generate() throws AsmParsingException, SetupException, TranslationException;

	/**
	 * Sets the version of the java compiler.
	 * 
	 * @param javaVersion the java version.
	 * @throws SetupException if an error occurs during the setup process.
	 */
	void setCompilerVersion(String javaVersion) throws SetupException;

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

	/**
	 * Get the description of all the modes of the translator.
	 * 
	 * @return string containing the description of all the translator modes .
	 */
	String getModeDescription();
}
