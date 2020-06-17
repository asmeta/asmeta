package org.asmeta.simulationUI;

import java.util.Scanner;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class SimShell {

	public static void main(String[] args) {
		String in=String.join(" ", args);
		SimulationContainer imp = SimulationContainer.getInstance();
		Commander.parseInput(imp, in, true);
		CommanderOutput CO;
		Scanner keyboard = new Scanner(System.in);
		do
		{
			System.out.print('>');
			in = keyboard.nextLine();
			if (!in.equals("qqq")) {
				CO = Commander.parseInput(imp, in, false);
				try {
				switch (CO.getStatus()) {
				case SIM_ID:
					System.out.println("Simulation ID: "+CO.getID());
					break;
				case INSTANCES:
					System.out.println("Instantiated "+CO.getInstances()+" simulations");
					break;
				case STOP:
					if (CO.getStop()>0)
						System.out.println("Stop successful");
					else 
						System.out.println("Couldn't stop given simulation");
					break;
				case RUNOUTPUT:
					System.out.println(CO.getRunOutput());
					break;
				case VIEWINV:
					System.out.println(CO.getInvList());
					break;
				case BOOLRES:
					if (CO.getSuccess())
						System.out.println("Operation successful");
					break;
				case FAILURE:
						System.out.println(CO.getErrorMessage());
					break;
				case LOADED_IDS:
					System.out.println(CO.getLoadedIDs());
				break;
				default:
					break;
				}
				}catch (CommanderException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		while (!in.equals("qqq"));
		keyboard.close();
	}
}
