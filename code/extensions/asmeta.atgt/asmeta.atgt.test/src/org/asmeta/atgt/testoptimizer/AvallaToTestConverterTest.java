package org.asmeta.atgt.testoptimizer;

import static org.junit.Assert.*;

import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;

import atgt.coverage.AsmTestSequence;

public class AvallaToTestConverterTest {

	@Test
	public void testFromAvallaToTestSequence() {
		// TODO: complete the test case
		ParseHelper<Scenario> parser = new ParseHelper();
		try {
			Scenario s = parser.parse("");
			AvallaToTestConverter converter = new AvallaToTestConverter();
			AsmTestSequence test = converter.convert(s);
			test.allInstructions();
		} catch (Exception e) {
			fail();
		}
	}

}
