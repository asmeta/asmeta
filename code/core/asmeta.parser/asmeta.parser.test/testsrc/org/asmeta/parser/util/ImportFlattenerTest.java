package org.asmeta.parser.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmParserTest;
import org.junit.Test;

import asmeta.structure.Asm;

public class ImportFlattenerTest {

	
	@Test
	public void testVisitAsm2() throws Exception {
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		String asmpath = AsmParserTest.FILE_BASE + "/examples/ABZ2020/CarSystemModule/CarSystem001/";
		File asmetafile = new File(asmpath + "CarSystem001main.asm");
		assertTrue(asmetafile.exists());
		Asm main = ASMParser.setUpReadAsm(asmetafile).getMain();
		ImportFlattener ifl = new ImportFlattener(main, asmpath);
		ifl.visit();
		spr.visit(main);
		spr.close();
	}

	
}
