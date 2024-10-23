package org.asmeta.asm2java.main;

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
     * @throws Exception if an error occurs during the generation process.
     */
    boolean generate() throws Exception;
    
    /**
     * Cleans up resources.
     */
    void clean();
}
