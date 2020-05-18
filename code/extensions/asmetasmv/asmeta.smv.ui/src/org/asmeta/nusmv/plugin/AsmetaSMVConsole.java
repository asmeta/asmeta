package org.asmeta.nusmv.plugin;

import java.io.IOException;

import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

public class AsmetaSMVConsole extends IOConsole {
	public static final String CONSOLE_NAME = "AsmetaSMV console";

	public AsmetaSMVConsole() {
		super(CONSOLE_NAME, null);			
	}	
	
	/** write a simple string message
	 * 
	 * @param s
	 */
	public void writeMessage(String s){
		IOConsoleOutputStream output = newOutputStream();
		try {
			output.write(s + "\n");
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
