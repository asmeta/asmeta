package org.asmeta.eclipse;

/**
 * Asmeta Console to be used for all the ASMETA Plugins. For now it iused as
 * default console (for simulation and parsing for example
 */

public class AsmeeConsole extends AsmetaConsole {

	private static final String CONSOLE_NAME = "Asmeta console";
	
	public AsmeeConsole() {
		super(CONSOLE_NAME);
	}
}
