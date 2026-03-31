package org.asmeta.parser;

import org.junit.jupiter.api.Test;

/** test class for SystemC specs
 * 
 * @author garganti
 *
 */
class AsmParserTest_SystemC extends AsmParserTest {

	@Test void systemCClock() {
		testDir("systemc/clock");
	}

	@Test void systemCCounter() {
		testDir("systemc/counter");
	}

	@Test void systemCOrgate() {
		testDir("systemc/orgate");
	}

	@Test void systemCSched() {
		testDir("systemc/sched");
	}

	@Test void systemCSimplebus() {
		testDir("systemc/simple_bus");
	}

}
