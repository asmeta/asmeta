package org.asmeta.framework.enforcerAirConditioner;

import java.io.*;
import java.util.Scanner;

import org.asmeta.framework.enforcer.Enforcer;
import org.asmeta.framework.managedSystem.ExtManagedSystem;
public class mainExtIOSanitisation{
	
public static void main(String[] args)  {
	
	/** Initialization*/
	
	//Create a new specialized enforcer for the AirConditioner system without feedback control loop (to use only for I/O sanitisation)
	Enforcer.setConfigFile("./resources/AirConditioner/config.properties");
	Enforcer e = new EnforcerAirConditioner();
	//Create system handle 
	ExtManagedSystem managedSystem =  new ExtManagedSystem(); 
	
	/** Running*/
	Scanner input = new Scanner(System.in);
	String s;
	try {
		System.out.println("Managed system prompt messages(s):\n ");
		System.out.println(managedSystem.readAll());
	    while ( input.hasNextLine() ) { 
		  s = input.nextLine();	
          //System.out.println("Input: " + s);
          //Checks whether to shutdown the system or not
          if (s.equals("###")) {
        	  managedSystem.shutDown();
        	  break;
          }
          //Forward s to the enforcer for input sanitisation
		  if (! e.sanitiseInput(s)) {
			//Forward s as it is to the managed system
			System.out.println("Input forwarded to the managed system: " + s);
			managedSystem.write(s);
			//Take the output speed value emitted from the system
			s = managedSystem.read();
			//Forward s to the enforcer for output sanitisation to avoid over speed
			System.out.println("Managed system reply message(s): ");
			if (! e.sanitiseOutput(s)) 
				System.out.println("Conditioner speed: "+s);
			else System.out.println(s + " is filtered out!");
			System.out.println(managedSystem.read()); 
		  }
		  else System.out.println("Input "+ s + " is filtered out!\n"+"Enter temperature value >");
		  
	    }
	}
	catch (IOException ex) {
		ex.printStackTrace();
	}
	finally {
		input.close();
	}
	System.out.println("Shutting down the system ...Done.");
}
}