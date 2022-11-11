package asmeta.mutation;

import static org.junit.Assert.*;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class MutationApplierTest {

	@Test
	public void test() throws Exception {
		String simple_example = "../../../asm_examples/examples/simple_example/AdvancedClock.asm";
		File f = new File(simple_example);
		assertTrue(f.exists());
		AsmCollection asm = ASMParser.setUpReadAsm(f);
		
		MutationApplier a = new MutationApplier(asm);
		
		a.mutate();
	}

}
