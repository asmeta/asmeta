package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.log4j.Level;
import org.junit.jupiter.api.Test;

/**
 * parse the files in the errors directory which should parse (may be not
 * simulate)
 * 
 * @author garganti
 *
 */
class AsmParserTestErrors extends AsmParserTest {

	@Test void rpns() {
		testDir("test/errors/rpns");
	}

	@Test void rpws() {
		testDir("test/errors/rpws");
	}

	// all these specs must fail but with a right exception
	@Test void circularA() {
		testCircular("test/errors/circular/SpecA.asm");
	}

	@Test void circularB() {
		testCircular("test/errors/circular/SpecB.asm");
	}

	private void testCircular(String spec) {
		ASMParser.getResultLogger().setLevel(Level.DEBUG);
		try{
			// pass false false, to avoid the capture of the exception
			testOneSpec(new File(FILE_BASE+ spec),false,false);
		} catch (Exception e) {
			if (e.getMessage().contains("circular import found")) return;
			fail("wrong message");
		}
	}
}
