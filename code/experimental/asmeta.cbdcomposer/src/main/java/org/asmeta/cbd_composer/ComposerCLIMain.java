package org.asmeta.cbd_composer;

import org.asmeta.cbd_composer.ComposerCLI;

public class ComposerCLIMain {
	
	static ComposerCLI comp;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: java ConfigParserApp <config_file.asmsh>");
			return;
		}
		try {
			comp = new ComposerCLI(args[0], true);
			comp.runcomposition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
