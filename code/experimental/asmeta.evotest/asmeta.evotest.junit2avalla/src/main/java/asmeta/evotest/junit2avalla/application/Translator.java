package asmeta.evotest.junit2avalla.application;

import asmeta.evotest.junit2avalla.javascenario.JUnitParseException;

/**
 * The {@code Translator} interface defines the contract for configuring and running 
 * a translation process.
 */
public interface Translator {
	
    /**
     * Sets the workingDir path for the intermediate files.
     *
     * @param workingDirPath the path to the working directory.
     * @throws SetupException if an error occurs during the setup process.
     */
    void setWorkingDir(String workingDirPath) throws SetupException;
	
    /**
     * Sets the input path to the file for the translation process.
     *
     * @param inputPath the path to the asm file to be translated.
     * @throws SetupException if an error occurs during the setup process.
     */
    void setInput(String inputPath) throws SetupException;
    
    /**
     * Sets the output directory for the translation process.
     *
     * @param outputDir the output directory.
     * @throws SetupException if an error occurs during the setup process.
     */
    void setOutput(String outputDir) throws SetupException;
    
    /**
     * Sets the desired parser (javaParser or custom).
     * 
     * @param parser parser to use.
     * @throws SetupException if an error occurs during the setup process.
     */
    void setParser(String parser) throws SetupException;
    
    /**
     * Executes the translation process.
     *
     * @throws SetupException if an error occurs during the setup process.
     * @throws TranslationException if an error occurs during the translation process.
	 * @throws JUnitParseException if an error occurs during the parsing process.
     */
    void generate() throws SetupException, TranslationException, JUnitParseException;
    
    /**
     * Cleans up resources.
     */
    void clean();

}
