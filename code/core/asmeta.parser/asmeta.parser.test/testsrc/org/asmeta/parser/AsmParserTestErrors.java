package org.asmeta.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Level;
import org.junit.Test;

/**
 * parse the files in the errors directory which should parse (may be not
 * simulate)
 * 
 * @author garganti
 *
 */
public class AsmParserTestErrors extends AsmParserTest {

	@Test
	public void testRPNS() {
		testDir("test/errors/rpns");
	}

	@Test
	public void testRPWS() {
		testDir("test/errors/rpws");
	}

	// all these specs must fail but with a right exception
	@Test
	public void testCircularA() {
		testCircular("test/errors/circular/SpecA.asm");
	}
	@Test
	public void testCircularB() {
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
