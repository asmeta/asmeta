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

public class ChooseRuleMutatorTest {
	
	@Test
	public void testMutateSimpleChoose() throws Exception {
		testChooseMutator("examples/chooseToLet.asm", 1);
	}
		
	@Test
	public void testMutatefilosofiEciliegieJustLeft() throws Exception {
		testChooseMutator("examples/filosofiEciliegieJustLeft.asm", 1);
	}
	
	@Test
	public void testMutateCoffeVendingMachine() throws Exception {
		testChooseMutator("experiments_nfm25/CoffeeVendingMachineRefined.asm", 1);
	}
	
	private void testChooseMutator(String asmFile, int nChoose) throws Exception {
		StringWriter stringWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stringWriter, true);
		pw.println("TEST STRING");
		AsmPrinter amPrint = new AsmPrinter(pw);
		
		File f = new File(asmFile);
		assertTrue(f.exists());
		AsmCollection asmeta = ASMParser.setUpReadAsm(f);
		amPrint.visit(asmeta.getMain().getMainrule());
		System.out.println(stringWriter.toString()); 
		stringWriter.getBuffer().setLength(0);

		// Mutate the asm
		ChooseRuleMutator mutator = new ChooseRuleMutator(asmeta.getMain());
		List<AsmCollection> mutrul = mutator.mutate(asmeta);
		
		assertEquals(nChoose, mutrul.size());
		amPrint.visit(mutrul.get(0).getMain().getMainrule());
		System.out.println(stringWriter.toString());
		amPrint.close();
	}
	

}
