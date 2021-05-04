package org.asmeta.parser;
import java.io.File;

import org.junit.Test;

/** all the specs in test/parser and test/simulator should parse (and simulate)
 * otherwise should go to test/errors
 * 
 * @author garganti
 *
 */
public class AsmParserTest_Test extends AsmParserTest {
	
	@Test
	public void testParserTests(){
		testDir("test/parser/");
	}

	@Test
	public void testSimulatorTests(){
		testDir("test/simulator/");
	}

	@Test
	public void testParserNeqAndNot(){
		testOneSpec("test/parser/neqAndNot.asm");
	}
	
	@Test(expected = ParseException.class)
	public void testAE() throws Exception{
		ASMParser.setUpReadAsm(new File(FILE_BASE+"test/errors/ArithmeticExpr02.asm"));
	}

	
}
