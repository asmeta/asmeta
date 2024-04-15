package org.asmeta.toyices;

import java.io.File;

public class AsmetaToSMT {

	public static void main(String[] args) throws Exception {
		String asm = args[0];
		if(new File(asm).exists()) {
			SMTbasedASMsimulator.setLogger();
			SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asm, false, false);
			visitor.visitASM();
			visitor.assertRules();
		}
		else {
			System.out.println("File " + asm + " does not exist!");
		}
	}
}