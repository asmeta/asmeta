package asmeta.asmetal2java.codegen.compiler;

/**
 * The {@code CompileResult} interface defines the contract to get the result of the compiler.
 */
public interface CompileResult {
	
	/**
	 * Get the result of the compiler.
	 * 
	 * @return {@code true} if the operation completed successfully, {@code false} otherwise.
	 */
	boolean getSuccess();

}
