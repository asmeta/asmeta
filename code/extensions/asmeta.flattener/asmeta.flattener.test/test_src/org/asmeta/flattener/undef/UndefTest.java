package org.asmeta.flattener.undef;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.asmeta.parser.util.AsmPrinter;
import org.junit.Test;

import asmeta.structure.Asm;

public class UndefTest {

	@Test
	public void testExplicitUndef() throws Exception {
		test("examples/undef/explicitUndef.asm");
	}

	@Test
	public void testUndef() throws Exception {
		test("examples/undef/undef.asm");
	}

//	@Test
//	public void testUndefUse() throws Exception {
//		test("examples/undef/UseUndef.asm");
//	}

	
	@Test
	public void testGilbreathCardTrick() throws Exception {
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
