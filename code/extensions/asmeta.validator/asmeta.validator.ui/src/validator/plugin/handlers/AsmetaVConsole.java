package validator.plugin.handlers;

import java.io.IOException;

import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

public class AsmetaVConsole extends IOConsole {
	private static final String CONSOLE_NAME = "ASMETAV_CONSOLE";

	public AsmetaVConsole() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
