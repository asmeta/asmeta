package org.asmeta.parser;
import org.junit.Test;


public class AsmParserTest_SystemCAG extends AsmParserTest{



	@Test
	public void testSystemC_AG_specs(){
		testOneSpec("systemc/counterAG/count_stim_user.asm");
		testOneSpec("systemc/counterAG/top_user.asm");
	}
}
