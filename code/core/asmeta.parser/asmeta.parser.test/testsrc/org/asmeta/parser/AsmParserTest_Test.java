package org.asmeta.parser;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

/** all the specs in test/parser and test/simulator should parse (and simulate)
 * otherwise should go to test/errors
 * 
 * @author garganti
 *
 */
class AsmParserTest_Test extends AsmParserTest {

	@Test void parserTests(){
		testDir("test/parser/");
	}

	@Test void simulatorTests(){
		testDir("test/simulator/");
	}

	@Test void parserNeqAndNot(){
		testOneSpec("test/parser/neqAndNot.asm");
	}	
}
