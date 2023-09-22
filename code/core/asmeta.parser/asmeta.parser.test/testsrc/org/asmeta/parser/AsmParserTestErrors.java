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
	@Test(expected = ParseException.class)
	public void testCircularA() {
		ASMParser.getResultLogger().setLevel(Level.DEBUG);
		testOneSpec("test/errors/circular/SpecA.asm");
	}
	@Test(expected = ParseException.class)
	public void testCircularB() {
		testOneSpec("test/errors/circular/SpecB.asm");
	}
}
