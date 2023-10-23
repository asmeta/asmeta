package asmeta.mutation;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.junit.Test;

import asmeta.AsmCollection;

public class RuleRemoverTest {

	@Test
	public void testMutatate() throws Exception {
		AsmPrinter amPrint = new AsmPrinter(new PrintWriter(System.out));
		String string = "examples/ruletoskip.asm";
		File f = new File(string);
		assertTrue(f.exists());
		AsmCollection asmeta = ASMParser.setUpReadAsm(f);
		amPrint.visit(asmeta.getMain().getMainrule());
//		amPrint.close();
		
		List<AsmCollection> mutrul = new RuleRemover().mutatate(asmeta);
		assertEquals(mutrul.size(), 2);
		
		amPrint.visit(mutrul.get(0).getMain().getMainrule());
		amPrint.visit(mutrul.get(1).getMain().getMainrule());
		amPrint.close();
		
	}

}
