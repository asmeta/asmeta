package org.asmeta.modeladvisor.plugin;

import java.io.IOException;

import org.asmeta.eclipse.AsmetaConsole;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

public class AsmetaMAConsole extends AsmetaConsole {
	
	
	public static final String CONSOLE_NAME = "AsmetaMA console";

	public AsmetaMAConsole() {
		super(CONSOLE_NAME);			
	}	
	
}
