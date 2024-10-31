package org.asmeta.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Level;
import org.junit.Test;

public class AsmParserTestStaticDerived extends AsmParserTest {

	@Test
	public void test() {
		ASMParser.getResultLogger().setLevel(Level.DEBUG);
		String spec = "test/errors/staticVSDerived.asm";
		testOneSpec(spec);
	}
}
