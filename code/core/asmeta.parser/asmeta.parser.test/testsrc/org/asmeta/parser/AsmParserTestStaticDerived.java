package org.asmeta.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.Test;

public class AsmParserTestStaticDerived extends AsmParserTest {

	@Test
	public void test() {
		Logger.getLogger(ReflectiveVisitor.class).setLevel(Level.ALL);
		log.setLevel(Level.DEBUG);
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
		String spec = "test/errors/staticVSDerived.asm";
		testOneSpec(spec);
	}
}
