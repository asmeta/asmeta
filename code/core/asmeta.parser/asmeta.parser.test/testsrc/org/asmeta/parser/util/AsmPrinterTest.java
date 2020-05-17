package org.asmeta.parser.util;

import java.io.File;
import java.io.PrintWriter;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

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
		MacroDeclaration rd = asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory.eINSTANCE.createMacroDeclaration();
		rd.setName("a");
		asm.setMainrule(rd);
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		spr.visit(asm);
	}

	@Test
	public void testVisitAsm2() throws Exception {
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		spr.visit(ASMParser.setUpReadAsm(new File("../../../asm_examples/examples/ferryman/ferrymanSimulator.asm")).getMain());
		spr.close();
	}

}
