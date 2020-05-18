package org.asmeta.nusmv.util;

/**
 * Eccezione sollevata quando in presenza di un modello ASM non supportato
 * dalla traduzione.
 * 
 * L'ho messa non controllata per poter extendere le interfacce che non prevedono exceptions
 *
 */
public class AsmNotSupportedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	
	public AsmNotSupportedException(String s){
		description = s;
	}
	
	@Override
	public String getMessage(){
		return description;
	}
	
}
