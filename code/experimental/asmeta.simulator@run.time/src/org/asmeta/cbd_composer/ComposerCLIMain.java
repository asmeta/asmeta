package org.asmeta.cbd_composer;

import org.asmeta.cbd_composer.ComposerCLI;

public class ComposerCLIMain {
	
	static ComposerCLI2 comp;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: java ConfigParserApp <config_file.asmsh>");
			return;
		}
		try {
			comp = new ComposerCLI2(args[0], true);
			comp.runcomposition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
