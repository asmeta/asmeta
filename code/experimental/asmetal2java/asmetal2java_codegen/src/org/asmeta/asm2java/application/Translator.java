package org.asmeta.asm2java.application;

import java.io.IOException;
import java.util.List;

import org.asmeta.asm2java.exceptions.AsmParsingException;

/**
 * The {@code Translator} interface defines the contract for configuring and running 
 * a translation process.
 */
public interface Translator {
    
    /**
     * Sets the value of the specified property.
     *
     * @param propertyName  the name of the property to set.
     * @param propertyValue the value to set the property to, interpreted as a boolean.
     */
    void setOptions(String propertyName, String propertyValue);
    
    /**
     * Sets the input path to the file for the translation process.
     *
     * @param inputPath the path to the asm file to be translated.
     */
    void setInput(String inputPath);
    
    /**
     * Sets the output directory for the translation process.
     *
     * @param outputDir the output directory.
     */
    void setOutput(String outputDir);
    
    /**
     * Sets the mode of translation to be used.
     *
     * @param mode the mode of translation.
     */
    void setMode(String mode);
    
    /**
     * Executes the translation process.
     *
     * @return {@code true} if the translation was successful, otherwise {@code false}.
     * @throws AsmParsingException when an error occurs while parsing an ASM file. 
     * @throws IOException if an I/O error occurs.
     */
    boolean generate() throws AsmParsingException, IOException;
    
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
     * @return string containing the description of all the translator modes
     * .
     */
    String getModeDescription();
}
