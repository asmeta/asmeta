package org.asmeta.simulationUI;

import java.util.Scanner;

import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_commander.CommanderSingleton;
import org.asmeta.runtime_container.SimulationContainerSingleton;

/**
 * @author Federico Rebucini
 */
public class SimShellSingleton {

	public static void main(String[] args) {
		String userInput = String.join(" ", args); // SimShell works as a command line tool as well
		SimulationContainerSingleton containerInstance = new SimulationContainerSingleton();
		CommanderSingleton.debugMode = false;
		CommanderSingleton.parseInput(containerInstance, userInput);
		CommanderOutput CO;
		Scanner keyboard = new Scanner(System.in);
		do {
			if(CommanderSingleton.prompt == null) {
				CommanderSingleton.prompt = "> ";
			}
			System.out.print(CommanderSingleton.prompt);
			userInput = keyboard.nextLine();
			if (!userInput.equals("qqq") && !userInput.equals("quit")) {
				CO = CommanderSingleton.parseInput(containerInstance, userInput);
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
					System.out.println(e.getMessage());
				}
			}
		} while(!userInput.equals("qqq") && !userInput.equals("quit"));
		
		System.out.println("Quitting...");
		keyboard.close();
		System.out.println("Done!");
		System.exit(0);
	}
}
