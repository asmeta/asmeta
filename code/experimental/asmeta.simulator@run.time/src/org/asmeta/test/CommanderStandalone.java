package org.asmeta.test;

import java.util.Scanner;


import org.asmeta.runtime_container.Commander;
import org.asmeta.runtime_container.CommanderException;
import org.asmeta.runtime_container.CommanderOutput;
import org.asmeta.runtime_container.ContainerRT;

public class CommanderStandalone {

	public static void main(String[] args) {
		String in=String.join(" ", args);
		ContainerRT imp = ContainerRT.getInstance();
		Commander.parseInput(imp, in, true);
		CommanderOutput CO;
		Scanner keyboard = new Scanner(System.in);
		do
		{
			in = keyboard.nextLine();
			if (!in.equals("fermo")) {
				CO = Commander.parseInput(imp, in, true);
				try {
				switch (CO.getStatus()) {
				case VIEWINV:
					System.out.println(CO.getInvList().toString());
					break;
				case BOOLRES:
					if (CO.getSuccess())
						System.out.println("Operation successful");
					break;
				case FAILURE:
						System.out.println(CO.getErrorMessage());
					break;
				default:
					break;
				}
				}catch (CommanderException e) {
					System.out.println(""+e.getMessage());
				}
			}
		}
		while (!in.equals("fermo"));
		keyboard.close();
	}
}
