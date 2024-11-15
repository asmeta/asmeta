package org.asmeta.junit2avalla.application;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The {@code Translator} interface defines the contract for configuring and running 
 * a translation process.
 */
public interface Translator {
	
    /**
     * Sets the input path to the file for the translation process.
     *
     * @param inputPath the path to the asm file to be translated.
     * @throws FileNotFoundException if a file not found error occurs.
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
     * Executes the translation process.
     *
     * @throws IOException if an I/O error occurs.
     */
    void generate() throws IOException;
    
    /**
     * Cleans up resources.
     */
    void clean();

}
