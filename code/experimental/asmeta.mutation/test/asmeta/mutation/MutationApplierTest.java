package asmeta.mutation;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.junit.Test;

import asmeta.AsmCollection;

public class MutationApplierTest {

	@Test
	public void test() throws Exception {
		Logger.getLogger(MutationApplier.class).setLevel(Level.ALL);
		Logger.getLogger(MutationApplier.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		String simple_example = "../../../asm_examples/examples/simple_example/AdvancedClock.asm";
		File f = new File(simple_example);
		assertTrue(f.exists());
		AsmCollection asm = ASMParser.setUpReadAsm(f);
		
		MutationApplier a = new MutationApplier(asm);
		
		IteratorOfIterator<AsmCollection> mutations = a.mutate();
		while(mutations.hasNext()) {
			AsmCollection newasm = mutations.next();
			PrintWriter writer = new PrintWriter(System.out);
			AsmPrinter printer = new AsmPrinter( writer);
			printer.visit(newasm.getMain());
			writer.flush();
		}
	}

}
