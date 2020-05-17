package org.asmeta.xt.tests.parsing.positive;

import java.io.File;
import java.io.IOException;

/**
 * main class to be used in the jar file Non si può usare il parserhelper e cose
 * del genere perchè l'uri non funziona più e non si riesce a trovare la
 * location del file
 * 
 */
public class MainRunner {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("add as argument the file to parse");
			return;
		}
		File asmFile = new File(args[0]);
		if (!asmFile.exists()) {
			System.out.println("file " + args[0] + " does not exist");
			return;
		}
		AllAsmExamplesTesterWOHelper.testAsmetaXtFile(args[0], true);
	}

}
