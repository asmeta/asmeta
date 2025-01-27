package org.asmeta.junit2avalla.application;

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
     * Executes the translation process.
     *
     * @throws SetupException if an error occurs during the setup process.
     * @throws TranslationException if an error occurs during the translation process.
     */
    void generate() throws SetupException, TranslationException;
    
    /**
     * Cleans up resources.
     */
    void clean();

}
