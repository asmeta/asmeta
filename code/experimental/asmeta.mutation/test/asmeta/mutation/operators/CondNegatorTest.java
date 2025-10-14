package asmeta.mutation.operators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.junit.Test;

import asmeta.AsmCollection;

public class CondNegatorTest {
	@Test
	public void testMutatate() throws Exception {
		StringWriter stringWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stringWriter, true);
		pw.println("TEST STRING");
		AsmPrinter amPrint = new AsmPrinter(pw);
		String string = "experiments_nfm25/CoffeeVendingMachineRefined.asm";
		File f = new File(string);
		assertTrue(f.exists());
		AsmCollection asmeta = ASMParser.setUpReadAsm(f);
		amPrint.visit(asmeta.getMain().getMainrule());
		System.out.println(stringWriter.toString()); stringWriter.getBuffer().setLength(0);

		List<AsmCollection> mutrul = new CondNegator().mutate(asmeta);

		amPrint.visit(mutrul.get(0).getMain().getMainrule());
		System.out.println(stringWriter.toString());
		amPrint.close();

	}

}
