package org.asmeta.runtime_simulator;

/**
 * 
 * @author Simone Giusso
 *	Exception for invalid id
 */

public class IdNotFoundException extends RuntimeException{
	
	public IdNotFoundException(String message) {
		super(message);
	}
	
}
