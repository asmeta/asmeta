package org.asmeta.flattener.undef;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.asmeta.parser.util.AsmPrinter;
import org.junit.jupiter.api.Test;

import asmeta.structure.Asm;

class UndefTest {

	@Test
	void explicitUndef() throws Exception {
		test("examples/undef/explicitUndef.asm");
	}

	@Test
	void undef() throws Exception {
		test("examples/undef/undef_use.asm");
	}

//	@Test
//	public void testUndefUse() throws Exception {
//		test("examples/undef/UseUndef.asm");
//	}


	@Test
	void gilbreathCardTrick() throws Exception {
		test("benchmarksFIDE2018/GilbreathCardTrick.asm");
	}

	private void test(String model) throws Exception {
		Asm asm = UndefRemover.buildNewAsm(model);
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visit(asm);
		System.out.println(sw.toString());
	}
}
