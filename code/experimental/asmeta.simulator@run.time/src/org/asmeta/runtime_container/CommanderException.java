package org.asmeta.runtime_container;

public class CommanderException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5535301548463359822L;
	private CommanderStatus CS;
	public CommanderException(String message, CommanderStatus CS) {
		super(message);
		this.CS=CS;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage()+", actual type: "+CS;
		
	}
}
