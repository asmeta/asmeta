package org.asmeta.framework.enforcerPillBox;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.pillBox.*;
//import java.awt.*; 
//import javax.swing.*; 

//First test class: the managed system is internal to its enforcer
public class Main { //extends JFrame {
	
	    // frame 
	    //static JFrame f; 
	  
	    // default constructor 
	    Main() 
	    { 
	    }   
	
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
		
		System.out.println("#################################################\n"+
				   "##   Medicine Reminder and Monitoring System   ##\n"+
				   "#################################################\n"+
				   "Pillbox initialization (simulated version) ...");
		/** Initialization*/
		//Create managed system handle, probe, and effector
		PillBox managedSystem =  new PillBox("examples/pillbox/pillbox.asm"); 		
		//Create system knowledge and feedback loop
		Knowledge k = new KnowledgePB();
		FeedbackLoop loop = new FeedbackLoopPillBox(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//create a new specialized enforcer for the Pillbox system
		Enforcer.setConfigFile("./resources/PillBox/config.properties");
		EnforcerPillBox e = new EnforcerPillBox(managedSystem,k,loop);
		
		 /** Running -- example of safety enforcement via MAPE-K*/
		 //Causality relation implementation between managed system and the ASM enforcement model: user input reading (by console), system/loop execution
		 //Once an event triggers the MAPE loop, the loop executes safety checks and eventually adapts the managed system 
		
		Scanner s = new Scanner(System.in); 
        System.out.println("PillBox ON, enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
        //Cursor c = Cursor.getSystemCustomCursor("Invalid.32x32\");
        try {
         String str = s.nextLine();
         while (! str.equals("###") && ! str.isBlank()) { 	
        	    e.sanitiseInput(str); //no input sanitisation applied, only input storing into the knowledge 
        	    //System.out.println("User input:"+e.getSanitisedInput().toString());
        	    //System.out.println("User input: "+str);
        	    //managedSystem.run(e.getSanitisedInput()); //the managed system runs 
        	    managedSystem.run(str);
        	    System.out.println("Output to patient: "+managedSystem.getOutputToPatient().toString());
          	    System.out.println("Output for probing: "+managedSystem.getOutputForProbing().toString());
          	    //e.runLoop(); //system output sanitisation by monitoring and adaptation
				//System.out.println("(Enforced) Output to patient: "+managedSystem.getOutputToPatient().toString());
				System.out.println("Enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
				str = s.nextLine();
			}
         }
		catch(InputMismatchException ex) {
				System.err.println("Error, input illformed.");
	        } 

        
		finally {
		        s.close();
		        managedSystem.shutDown();
		        System.out.println("PillBox OFF");
	       }
        
   }
		

}
