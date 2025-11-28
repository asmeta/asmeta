package org.asmeta.cbd_composer;

import org.asmeta.parser.util.AsmetaPrintInfo;

public class PrintInfo {

	public static void main(String[] args) throws Exception {		

		String[] string = {"pillbox.asm","configurationMgr.asm","compartment.asm","knowledge.asm","rescheduler.asm"};
		for (String s: string) {
		AsmetaPrintInfo asmpi = new AsmetaPrintInfo(CdBComposerTest.path + "Pillbox_CBD/"+ s);
		System.out.println(s + " -- " + asmpi.getInfo().infoMapOrderd());
		}

	}

}
