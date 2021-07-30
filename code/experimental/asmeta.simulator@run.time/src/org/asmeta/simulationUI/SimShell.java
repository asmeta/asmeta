package org.asmeta.simulationUI;

import java.util.Scanner;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_container.SimulationContainer;

/**
 * @author Federico Rebucini
 */
public class SimShell {

	public static void main(String[] args) {
		String userInput = String.join(" ", args); // SimShell works as a command line tool as well
		SimulationContainer containerInstance = new SimulationContainer();
		Commander.debugMode = false;
		Commander.parseInput(containerInstance, userInput);
		CommanderOutput CO;
		Scanner keyboard = new Scanner(System.in);
		do {
			if(Commander.prompt == null) {
				Commander.prompt = "> ";
			}
			System.out.print(Commander.prompt);
			userInput = keyboard.nextLine();
			if (!userInput.equals("qqq") && !userInput.equals("quit")) {
				CO = Commander.parseInput(containerInstance, userInput);
				try {
					switch (CO.getStatus()) {
					case SIM_ID:
						System.out.println("Simulation ID: " + CO.getID());
					break;
					case INSTANCES:
						System.out.println("Instantiated " + CO.getInstances() + " simulations");
					break;
					case STOP:
						if (CO.getStop() > 0)
							System.out.println("Stop successful!");
						else 
							System.out.println("Couldn't stop given simulation!");
					break;
					case RUNOUTPUT:
						System.out.println(CO.getRunOutput());
					break;
					case VIEWINV:
						System.out.println(CO.getInvList());
					break;
					case BOOLRES:
						if (CO.getSuccess())
							System.out.println("Operation successful!");
					break;
					case FAILURE:
						System.out.println();
						System.err.println(CO.getErrorMessage());
					break;
					case SUCCESS:
						System.out.println();
					break;
					case LOADED_IDS:
						System.out.println(CO.getLoadedIDs());
					break;
					default: break; 
					}
				} catch(CommanderException e) {
					System.err.println(e.getMessage());
				}
			}
		} while(!userInput.equals("qqq") && !userInput.equals("quit"));
		
		System.out.println("Quitting...");
		keyboard.close();
		System.out.println("Done!");
		System.exit(0);
	}
}
