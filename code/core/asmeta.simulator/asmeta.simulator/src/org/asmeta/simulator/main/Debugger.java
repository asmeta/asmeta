/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import org.asmeta.simulator.UpdateSet;


/**
 * A very simple interactive debugger to ASM.
 *
 */
public class Debugger {

	/**
	 * @param args the file path of an ASM
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Syntax: debug <file path>");
			System.exit(-1);
		}
		Simulator sim = Simulator.createSimulator(args[0]);
		System.out.println();
		System.out.println("Help: Run[times], Print, Quit");
		System.out.print(">");
		for (;;) {
			int c = System.in.read();
			if (Character.isWhitespace(c)) {
				continue;
			} else if (c == 'r') {
				//String ns = "";
				StringBuilder ns = new StringBuilder();
				for (;;) {
					c = System.in.read();
					if (Character.isWhitespace(c)) {
						break;
					} else if (Character.isDigit(c)) {
						//ns = ns + (char) c;
						ns.append((char)c);
					} else {
						// others chars
						ns = null;
						break;
					}
				}
				if (ns != null) {
					int n;
					String str = ns.toString();
					if (str.equals("")) {
						n = 1;
					} else {
						n = Integer.parseInt(str);
					}
					// skips cr
					System.in.read();
					UpdateSet uset = sim.run(n);
					System.out.println("CHANGES...");
					System.out.println(uset);
					System.out.println();
					System.out.println("Help: Run[times], Print, Quit");
					System.out.print(">");
				}
			} else if (c == 'p') {
				System.out.println(sim.getCurrentState());
				System.out.println();
				System.out.println("Help: Run[times], Print, Quit");
				System.out.print(">");
			} else if (c == -1 || c == 'q') {
				System.exit(0);
			}
		}
	}

}
