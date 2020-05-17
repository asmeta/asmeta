package org.asmeta.parser;

import org.junit.Test;

/** test class for SystemC specs
 * 
 * @author garganti
 *
 */
public class AsmParserTest_SystemC extends AsmParserTest {

	@Test
	public void testSystemC_Clock() {
		testDir("systemc/clock");
	}

	@Test
	public void testSystemC_Counter() {
		testDir("systemc/counter");
	}

	@Test
	public void testSystemC_orgate() {
		testDir("systemc/orgate");
	}

	@Test
	public void testSystemC_sched() {
		testDir("systemc/sched");
	}

	@Test
	public void testSystemC_simplebus() {
		testDir("systemc/simple_bus");
	}

}
