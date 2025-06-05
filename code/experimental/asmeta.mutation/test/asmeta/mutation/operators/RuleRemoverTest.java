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
import asmeta.mutation.mutationscore.TestForCoverageShortICTSS;

public class RuleRemoverTest {

	@Test
	public void testMutatate() throws Exception {
		String string = "examples/ruletoskip.asm";
		mutate(string, 3);
	}

	@Test
	public void testMutatate2() throws Exception {
		String string = "examples/ruletoskip2.asm";
		mutate(string, 4);
	}
	@Test
	public void testMutatate3() throws Exception {
		String string = "examples/ruletoskip3.asm";
		mutate(string, 5);
	}

	@Test
	public void testMutatate4() throws Exception {
		// only if
		String string = "examples/ruletoskip4.asm";
		mutate(string, 3);
	}

	@Test
	public void testASESPEC() throws Exception {
		// only if
		String string = TestForCoverageShortICTSS.base_dir +  "\\models\\ATM.asm";
		mutate(string, 56);
	}
	@Test
	public void testASESPEC2() throws Exception {
		// only if
		String string = TestForCoverageShortICTSS.base_dir +  "\\models\\CoffeeVendingMachine.asm";
		mutate(string, 12);
	}
	

	
	private void mutate(String string, int numMutations) throws Exception {
		File f = new File(string);
		StringWriter stringWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stringWriter, true);
		pw.println("TEST STRING");
		AsmPrinter amPrint = new AsmPrinter(pw);
		assertTrue(f.exists());
		AsmCollection asmeta = ASMParser.setUpReadAsm(f);
		amPrint.visit(asmeta.getMain().getMainrule());
//		amPrint.close();
//		System.out.println(stringWriter.toString()); stringWriter.getBuffer().setLength(0);

		List<AsmCollection> mutrul = new RuleRemover().mutate(asmeta);
		for (AsmCollection asm : mutrul) {
			amPrint.visit(asm.getMain().getMainrule());
			System.out.println(stringWriter.toString());
		}
		amPrint.close();
		assertEquals(numMutations, mutrul.size());
	}

}
