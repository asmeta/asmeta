package org.asmeta.evoasmetatg.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Translator {
	
    /**
     * Sets the input path to the file for the translation process.
     *
     * @param inputPath the path to the asm file to be translated.
     * @throws FileNotFoundException 
     */
    void setInput(String inputPath) throws FileNotFoundException;
    
    /**
     * Sets the output directory for the translation process.
     *
     * @param outputDir the output directory.
     * @throws IOException 
     */
    void setOutput(String outputDir) throws IOException;
    
    /**
     * Sets the path to the Java executable used to run Evosuite.
     * 
     * @param javaPath path to the java.exe
     * @throws FileNotFoundException 
     */
    void setJavaPath(String javaPath) throws FileNotFoundException;
    
    /**
     * Sets the value of the specified property.
     *
     * @param propertyName  the name of the property to set.
     * @param propertyValue the value to set the property to, interpreted as a boolean.
     */
    void setOptions(String propertyName, String propertyValue);
	
    /**
     * Sets the version of Evosuite.
     * 
     * @param evosuite the version of evosuite.
     */
    void setEvosuiteVersion(String evosuiteVersion);
	
    /**
     * Executes the translation process.
     *
     * @return {@code true} if the translation was successful, otherwise {@code false}.
     * @throws TranslationException 
     * @throws IOException 
     */
    void generate() throws TranslationException, IOException;
    
    /**
     * Set the clean option status.
     * 
     * @param clean {@true} activate the clean option, {@false} deactivate the clean option.
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
