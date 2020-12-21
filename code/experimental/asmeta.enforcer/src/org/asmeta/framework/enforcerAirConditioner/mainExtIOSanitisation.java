package org.asmeta.framework.enforcerAirConditioner;

import java.io.*;
import java.util.Scanner;

import org.asmeta.framework.enforcer.Enforcer;
import org.asmeta.framework.managedSystem.ExtManagedSystem;
public class mainExtIOSanitisation{
	
public static void main(String[] args) throws IOException {
//if (args.length != 1) {
//System.err.println("Usage: java OSProcess <command>");
//System.exit(0);
//}
// args[0] is the command that is run in a separate process
//ProcessBuilder pb = new ProcessBuilder(args[0]);
//Process process = pb.start();
// obtain the input stream
//InputStream is = process.getInputStream();
//InputStreamReader isr = new InputStreamReader(is);
//BufferedReader br = new BufferedReader(isr);
// read the output of the process
//String line;
//while ( (line = br.readLine()) != null)
//System.out.println(line);
//br.close();
	
	/** Initialization*/
	
	//Create a new specialized enforcer for the AirConditioner system without feedback control loop (to use only for I/O sanitisation)
	Enforcer.setConfigFile("./resources/AirConditioner/config.properties");
	Enforcer e = new EnforcerAirConditioner();
	//Create system handle 
	ExtManagedSystem managedSystem =  new ExtManagedSystem(); 
	
	/** Running*/
	Scanner input = new Scanner(System.in);
	String sin;
	try {
		System.out.println("Managed system prompt messages(s):\n ");
		while ( (sin = managedSystem.read()) != null) 
			System.out.println(sin);
	    while ( (sin = input.nextLine()) != null) { 
		  System.out.println("Input: " + sin);
	      //Forward sin to the enforcer for input sanitisation
		  if (! e.sanitiseInput(sin)) {
			//Forward sin as it is to the managed system
			System.out.println("Input forwarded to the managed system: " + sin);
			String r = managedSystem.write(sin);
			System.out.println("Managed system reply: "+r);
		  }//else it is filtered out
	    }
	}
	finally {
		input.close();
	}
}
}