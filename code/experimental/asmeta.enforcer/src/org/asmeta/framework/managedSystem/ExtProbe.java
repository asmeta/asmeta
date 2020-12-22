package org.asmeta.framework.managedSystem;

public interface ExtProbe extends Probe{

	public String send(String cmd);
	/*{
		try {
			reply = client.write(cmd); 
		} 
		catch (IOException e) {
			e.printStackTrace();
			reply = "ERROR";
		}
	}*/
	
}
