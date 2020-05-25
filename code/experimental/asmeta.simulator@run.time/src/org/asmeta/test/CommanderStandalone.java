package org.asmeta.test;

import java.util.Scanner;


import org.asmeta.runtime_container.Commander;
import org.asmeta.runtime_container.ContainerRT;

public class CommanderStandalone {

	public static void main(String[] args) {
		String in=String.join(" ", args);
		ContainerRT imp = ContainerRT.getInstance();
		Commander.parseInput(imp, in, true);
		Scanner keyboard = new Scanner(System.in);
		do
		{
			in = keyboard.nextLine();
			if (!in.equals("fermo"))
				Commander.parseInput(imp, in, true);
		}
		while (!in.equals("fermo"));
		keyboard.close();
	}
}
