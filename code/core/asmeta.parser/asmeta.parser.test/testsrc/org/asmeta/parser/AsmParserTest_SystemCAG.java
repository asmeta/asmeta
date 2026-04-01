package org.asmeta.parser;

import org.junit.jupiter.api.Test;


class AsmParserTest_SystemCAG extends AsmParserTest{


	@Test void systemCAGSpecs(){
		testOneSpec("systemc/counterAG/count_stim_user.asm");
		testOneSpec("systemc/counterAG/top_user.asm");
	}
}
