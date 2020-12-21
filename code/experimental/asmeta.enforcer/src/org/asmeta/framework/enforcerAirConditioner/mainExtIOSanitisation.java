package org.asmeta.framework.enforcerAirConditioner;

import java.io.*;
import java.util.Scanner;

import org.asmeta.framework.enforcer.Enforcer;
import org.asmeta.framework.managedSystem.ExtManagedSystem;
public class mainExtIOSanitisation{
	
public static void main(String[] args) throws IOException {
	
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
		System.out.println(managedSystem.read());
		System.out.println(managedSystem.read());
		/*while  (managedSystem.hasNext()) {
			sin = managedSystem.read();
			System.out.println(sin);
		}*/
	    while ( input.hasNextLine() ) { 
		  sin = input.nextLine();	
          System.out.println("Input: " + sin);
	      //Forward sin to the enforcer for input sanitisation
		 if (! e.sanitiseInput(sin)) {
			//Forward sin as it is to the managed system
			System.out.println("Input forwarded to the managed system: " + sin);
			sin = managedSystem.write(sin);
			System.out.println("Managed system reply: "+sin);
			//System.out.println(managedSystem.read());
			//System.out.println(managedSystem.read());
			//System.out.println(managedSystem.read());
			//System.out.println("pio");
		  }//else it is filtered out 
		  
	    }
	}
	finally {
		input.close();
	}
}
}