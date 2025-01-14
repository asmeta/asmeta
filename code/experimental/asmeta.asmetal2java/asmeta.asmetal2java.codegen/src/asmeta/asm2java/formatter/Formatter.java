package asmeta.asm2java.formatter;

/**
 * The {@code Formatter} interface define the contract for using the formatter.
 */
public interface Formatter {
	
	/**
	 * Format the code.
	 * 
	 * @param code String containing the code to format
	 * @return string containing the code formatted.
	 */
	String formatCode(String code);


}
