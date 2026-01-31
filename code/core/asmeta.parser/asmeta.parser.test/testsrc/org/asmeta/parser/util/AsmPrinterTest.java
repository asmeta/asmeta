package org.asmeta.parser.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.StringPrintWriter;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import asmeta.structure.Header;
import asmeta.structure.Signature;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

public class AsmPrinterTest {

	@Test
	public void testVisitAsmMain() {
		Asm asm = asmeta.structure.StructureFactory.eINSTANCE.createAsm();
		Header h = asmeta.structure.StructureFactory.eINSTANCE.createHeader();
		asm.setHeaderSection(h);		
		Signature s =  asmeta.structure.StructureFactory.eINSTANCE.createSignature();
		h.setSignature(s);		
		asm.setBodySection(asmeta.structure.StructureFactory.eINSTANCE.createBody());
		MacroDeclaration rd = asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory.eINSTANCE.createMacroDeclaration();
		rd.setRuleBody(asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory.eINSTANCE.createSkipRule());
		rd.setName("a");
		asm.setMainrule(rd);
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		spr.visit(asm);
	}

	@Test
	public void testVisitAsm2() throws Exception {
		StringPrintWriter out = new StringPrintWriter();
		AsmPrinter spr = new AsmPrinter(out);
		spr.visit(ASMParser.setUpReadAsm(new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm")).getMain());
		//System.out.println(out.getString());
		// check that the enum is translated properly - what if it is trsanledted with , ?
		assertTrue(out.getString().contains("{FERRYMAN | GOAT | CABBAGE | WOLF}"));
		spr.close();
		assertTrue(out.isClosed());
	}

	@Test
	public void testVisitAsmWithString() throws Exception {
		StringPrintWriter out = new StringPrintWriter();
		System.out.println(out.getString());
		AsmPrinter spr = new AsmPrinter(out);
		File f = new File("../../../../asm_examples/test/simulator/StringExpr01.asm");
		AsmCollection setUpReadAsm = ASMParser.setUpReadAsm(f);
		spr.visit(setUpReadAsm.getMain());	
		// check that the strings have \" \" aorund them
		assertTrue(out.getString().contains("\"pippo\""));
		spr.close();
		assertTrue(out.isClosed());
	}
	
}
