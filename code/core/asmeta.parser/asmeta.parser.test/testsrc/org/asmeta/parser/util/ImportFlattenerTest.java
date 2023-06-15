package org.asmeta.parser.util;

import java.io.File;
import java.io.PrintWriter;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.structure.Asm;

public class ImportFlattenerTest {

	
	@Test
	public void testVisitAsm2() throws Exception {
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		String asmpath = "../../../../asm_examples/examples\\ABZ2020\\CarSystemModule\\CarSystem001\\";
		Asm main = ASMParser.setUpReadAsm(new File(asmpath + "CarSystem001main.asm")).getMain();
		ImportFlattener ifl = new ImportFlattener(main, asmpath);
		ifl.visit();
		spr.visit(main);
		spr.close();
	}

	
}
