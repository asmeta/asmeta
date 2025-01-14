package asmeta.evoasmetatg.config;

import java.util.List;

/**
 * Includes all the available operations regarding the options of the Translator.
 */
public interface Options {

	/**
	 * Sets the value of the specified option.
	 * 
	 * @param optionName name of the considered option.
	 * @param optionValue value of the considered option.
	 */
	void setValue(String optionName, Boolean optionValue);
	
	/**
	 * Gets all the name of the options.
	 * 
	 * @return a List of String containing all the option names.
	 */
	public List<String> getOptionNames();
	
	/**
	 * Get a description of the options.
	 * 
	 * @return a String containing the description of all the options.
	 */
	public String getDescription();
	
	/**
	 * Get a List of String containing the CLI command options. 
	 * 
	 * @return list of CLI commands.
	 */
	public List<String> getCLIStringOptions();

	/**
	 * Returns the coverRules property.
	 *
	 * @return {@code true} if we want to cover the rules in the testGen class, {@code false} otherwise.
	 */
	public boolean isCoverRules();

	/**
	 * Returns the coverOutputs property.
	 *
	 * @return {@code true} if we want to cover the outputs in the testGen class, {@code false} otherwise.
	 */
	public boolean isCoverOutput();

	/**
	 * Returns the compiler option.
	 *
	 * @return {@code true} if the java class should be compiled, {@code false} otherwise.
	 */
	public boolean isCompiler();
	
	/**
	 * Returns the copyAsm option.
	 *
	 * @return {@code true} if the asmeta specification should be copied in another
	 *         folder to be processed, {@code false} otherwise.
	 */
	public boolean isCopyAsm();

}
