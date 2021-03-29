/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcer.pillBoxApp;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import org.asmeta.framework.enforcer.*;


//import java.awt.*; 
//import javax.swing.*; 

//First test class: the managed system is internal to its enforcer
public class Main { //extends JFrame {
	
	    // frame 
	    //static JFrame f; 
	  
	    // default constructor 
	    //Main() 
	    //{ 
	    //}   
	
	public static void main(String[] args) {
		
		/*
		try { 
            // create a frame 
            f = new JFrame("Medicine Reminder and Monitoring System");           
            JTextArea text = new JTextArea(50, 20);
            JScrollPane pane = new JScrollPane(text);
            pane.setPreferredSize(new Dimension(250,200));
            f.add("Center", pane);
            //JButton printButton = new JButton("Print This Window");
            //printButton.addActionListener(f);
            //f.add("South", printButton);
            //f.pack();
            // show the frame 
            //f.setVisible(true);
            f.show(); 
            f.setSize(800, 400); 
            text.append("###############################################\n"+
 				   "##   Medicine Reminder and Monitoring System   ##\n"+
 				   "###############################################\n"+
 				   "Pillbox initialization (simulated version) ...");
 				   
            text.append("\nPillBox ON, enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
            //Cursor c = Cursor.getSystemCustomCursor("Invalid.32x32\");		     
		
		}
            catch(Exception e) { 
                System.err.println(e.getMessage()); 
            } 
		
		*/
		
		/*System.out.println("#################################################\n"+
				   "##   Medicine Reminder and Monitoring System   ##\n"+
				   "#################################################\n"+
				   "Pillbox initialization (simulated version) ...");
		/** Initialization*/
		//Create managed system handle, probe, and effector
		/*String path=new MyPath().getPath();
		PillBoxNotSing managedSystem;
		managedSystem = new PillBoxNotSing(path.substring(0,path.length()-2)+"\\src\\org\\asmeta\\framework\\enforcer\\pillBoxApp\\specs\\pillbox.asm");
		//Create system knowledge and feedback loop
		KnowledgePB k = new KnowledgePB();
		FeedbackLoop loop = new FeedbackLoopPillBox(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//Create a new specialized enforcer for the Pillbox system
		Enforcer.setConfigFile(path.substring(0,path.length()-2)+"\\src\\org\\asmeta\\framework\\enforcer\\pillBoxApp\\specs\\config.properties");
		EnforcerPillBox e = new EnforcerPillBox(managedSystem,k,loop,true);
		
		//Init step for the enforcement model SafePillbox for the plug-in of the pill box, with consistency checks of the invariants
        String initial_input_trace =  "redLed(compartment1) OFF redLed(compartment2) OFF name(compartment1) \"fosamax\" name(compartment2) \"moment\" time_consumption(compartment1) [360] time_consumption(compartment2) [730,1140] drugIndex(compartment1) 0 drugIndex(compartment2) 0";
		//String initial_input_trace =  "drugIndex(compartment1) 0 drugIndex(compartment2) 0 name(compartment2) \"moment\" name(compartment1) \"fosamax\" redLed(compartment1) OFF redLed(compartment2) OFF time_consumption(compartment2) [730,1140] time_consumption(compartment1) [360]";
		Map<String, String> initState = e.initStep(initial_input_trace); //TODO Aggiungere gestione eccezione per invarianti, ecc..
	
        /** Running -- example of safety enforcement via MAPE-K*/
	    //Causality relation implementation between managed system and the ASM enforcement model: user input reading (by console), system/loop execution
		//Once an event triggers the MAPE loop, the loop executes safety checks and eventually adapts the managed system by executing it once again with the provided effectors values
       
		EnforcerApp myEnf= new EnforcerApp();
		
		Scanner s = new Scanner(System.in); 
        System.out.println("PillBox ON, enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
        String str = s.nextLine();    
        try {     
              while (! str.equals("###") && ! str.isEmpty()) { 	
            	  myEnf.getEnforcerPillBox().sanitiseInput(str); //no input sanitisation applied, only input storing into the knowledge
        	    //System.out.println("User input:~$\n "+str);
        	    System.out.println("(Sanitised) User input:~$ "+myEnf.getEnforcerPillBox().getSanitisedInput().toString());
        	    System.out.println("Pillbox running...");
        	    myEnf.getManagedSystem().run(myEnf.getEnforcerPillBox().getSanitisedInput()); //the managed system runs 
        	    //System.out.println("Output to patient:~$\n"+managedSystem.getOutputToPatient().toString());
          	    System.out.println("Output for probing:~$ "+ myEnf.getManagedSystem().getOutputForProbing().toString());
          	    System.out.print("Enforcement feedback loop starting...");
          	    long execTime = myEnf.getEnforcerPillBox().runLoop(); //system output sanitisation by monitoring and adaptation
          	    System.out.println("Enforcement feedback loop execution time (in milliseconds): "+execTime/ 1000000 +" ms");
          	    //System.out.println("Enforcer output for effectors:~$ "+ e.getOutputForEffectors().toString());
          	    //System.out.println("Enforced PillBox state: "+managedSystem.getOutput());
          	    //System.out.println("(Enforced) Output to patient: "+managedSystem.getOutputToPatient().toString());
				System.out.println("Enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
				str = s.nextLine();
			  }
            }
		    catch(InputMismatchException ex) {
				System.err.println("Error, input illformed.");
	        } //TODO Aggiungere altri di tipi di eccezioni come Invalid Invariant
		    finally {
		        s.close();
		        myEnf.getManagedSystem().shutDown();
		        System.out.println("PillBox OFF");
	       }
		
        myEnf.getManagedSystem().shutDown();
	}//End main	

}
