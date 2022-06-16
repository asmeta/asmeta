/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerMVM;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.mvm.MVM;

//import org.asmeta.framework.pillBox1.PillBox;

public class Main {
	
	public static void main(String[] args) {
	
	System.out.println("#################################################\n"+
			   "##   MVM Enforcer System   ##\n"+
			   "#################################################\n");
	/** Initialization*/
	//Create managed system handle, probe, and effector
	MVM managedSystem =  new MVM("examples/MVM/MVMcontrollerAdapt.asm"); //TODO
	//Create system knowledge and feedback loop
	KnowledgeMVM k = new KnowledgeMVM();
	FeedbackLoop loop = new FeedbackLoopMVM(managedSystem.getProbe(),managedSystem.getEffector(),k);
	//Create a new specialized enforcer for the MVM system
	Enforcer.setConfigFile("./resources/MVM/config.properties");
	EnforcerMVM e = new EnforcerMVM(managedSystem,k,loop);
	
	/* Input trace removed for MVM
	//Init step for the enforcement model SafePillbox for the plug-in of the pill box, with consistency checks of the invariants
   	String initial_input_trace = 
			"adc_reply RESPONSE"
			+ " all_cont NONE"
			+ " breath_sync EXP"	
			+ " compliance 3.0"
			+ " current_volume 6.0"
			+ " dropPAW_ITS_sup false"
			+ " enter_self false"
			+ " exit_self false"
			+ " fan_working true"
			+ " iValve CLOSED"	
			+ " limit_pa 19.0"
			+ " mCurrSecs 1"
			+ " oValve OPEN"
			+ " peep 25"
			+ " pi_6 25"
			+ " pi_6_reply RESPONSE"
			+ " rcexp 0.5"
			+ " respirationMode_sup PCV"
			+ " run_command false"
			+ " snooze false" 
			+ " stop_command false"
			+ " temperature 25"
			+ " vol_min_perc 10.0"
			+ " watchdog false"
			+ " weight_sup 70.0";
			
*/	 
	
	//Map<String, String> initState = e.initStep(initial_input_trace); //TODO Aggiungere gestione eccezione per invarianti, ecc..

   /** Running -- example of safety enforcement via MAPE-K*/
   //Causality relation implementation between managed system and the ASM enforcement model: user input reading (by console), system/loop execution
   //Once an event triggers the MAPE loop, the loop executes safety checks and eventually adapts the managed system by executing it once again with the provided effectors values
 
  //TODO
 Scanner s = new Scanner(System.in); 
 System.out.println("MVM ON, enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
 String str = s.nextLine();   
 
 try {     
       while (! str.equals("###") && ! str.isBlank()) { 	
 	  
    	e.sanitiseInput(str); //no input sanitisation applied, only input storing into the knowledge
 	    //System.out.println("User input:~$\n "+str);
 	    System.out.println("(Sanitised) User input:~$ "+e.getSanitisedInput().toString());
 	    System.out.println("Controller running...");
 	    managedSystem.run(e.getSanitisedInput()); //the managed system runs 
 	    //System.out.println("Output to patient:~$\n"+managedSystem.getOutputToPatient().toString());
 	    System.out.println("Output for probing:~$ "+managedSystem.getOutputForProbing().toString());
   	    System.out.print("Enforcement feedback loop starting...");
   	    long execTime = e.runLoop(); //system output sanitisation by monitoring and adaptation
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
	        managedSystem.shutDown();
	        System.out.println("MVM OFF");
    }
	
	managedSystem.shutDown();
	
}//End main	

}
