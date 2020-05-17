package org.asmeta.parser;

import org.junit.Test;

/** 
 * parse the files in the errors directory which should parse (may be not simulate) 
 * 
 * @author garganti
 *
 */
public class AsmParserTestErrors extends AsmParserTest{
	
	
	@Test
	public void testRPNS(){
		testDir("errors/rpns");
	}
	@Test
	public void testRPWS(){
		testDir("errors/rpws");
	}

}
