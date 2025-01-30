package org.asmeta.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;

// it is too difficult to test a checker in isolation
public class AsmParserTestStaticDerived extends AsmParserTest {

	@BeforeClass
	static public void setUp() {
		Logger.getLogger(ReflectiveVisitor.class).setLevel(Level.ALL);
		log.setLevel(Level.DEBUG);
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
	}

	// some examples with errors - they must be rejected by the parser
	@Test
	public void testSpecificTestWErrors() {
		// first type of error static contains dynamic
		String err = testOneSpecWithError("test/errors/staticVSDerived.asm");
		assertTrue(err.contains("static function fs contains (dynamic)"));
		// derived does not contain dynamic
		err = testOneSpecWithError("test/errors/staticVSDerived2.asm");
		assertTrue(err.contains("derived function fs does not contain dynamic functions"));
		// also the domains
		// it is not clear if the dynamic domains make the definition derived
		// it should be for me AG FEB 25
//		err = testOneSpecWithError("test/errors/staticVSDerived3.asm");
//		System.err.println(err);
//		assertTrue(err.contains("static function pendingOrders contains (dynamic)"));
		err = testOneSpecWithError("test/errors/staticVSDerived4.asm");
		assertTrue(err.contains("derived function fd does not contain dynamic functions"));
		err = testOneSpecWithError("test/errors/staticVSDerived5.asm");
		assertTrue(err.contains("static function fs contains (dynamic)"));
	}

	@Test
	public void testSpecificTestNoErrors() {
		testOneSpec("test/parser/staticVSDerived.asm");
	}

	@Test
	public void testLifts() {
		testOneSpec("/examples/models/LIFT.asm");
		testOneSpec("/examples/models/lift2.asm");
		testOneSpec("/examples/models/lift3.asm");
		testOneSpec("/examples/models/lift3.asm");
	}

	@Test
	public void testOrderSystem() {
		testOneSpec("/examples/models/ordersystem.asm");
	}

	@Test
	public void testproduction_cell() {
		testOneSpec("examples/production_cell/robot.asm");
		testOneSpec("examples/production_cell/press.asm");
		testOneSpec("examples/production_cell/Production_Cell_with_agents.asm");
	}

	@Test
	public void testtemp() throws IOException {
		Files.walk(Paths.get("../../../../code/extensions/asmeta.refprover\\asmeta.refinementprover\\refinement"))
		.filter(x -> (x.toFile().isDirectory() || x.toString().endsWith(".asm"))).forEach(f -> {
			String string = f.toFile().toString();
			if (string.contains("drafts")); else 
			if (string.contains("DAS")); else
			if (string.contains("oldVersion")); else
			if (string.contains("flashProtocol\\old")); else
			if (string.contains("test\\errors")); else				
			if (string.contains("repository\\errors")); else				
			if (string.contains("workspacePatrizia")); else				
			if (string.contains("template")); else	
			if (string.contains("safePillboxForPaper")); else	
			if (string.contains("target\\classes")); else	
			if (string.contains("asmeta.simulator@run.time")); else	
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\rpns\\r")); else	
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\systemc\\simple_bus\\")); else
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\test\\")); else
				// queste non so se funzionano convertendo derivate in static
			if (string.contains("asmeta.modeladvisor.test\\examples\\statDerIsUsed.asm")); else
			if (string.contains("asmeta.modeladvisor.test\\examples\\usedDomain2.asm")); else
			if (string.endsWith(".asm")) {
				AsmCollection res = testOneSpec(f.toFile(),false,false);
				if (res == null) {
					System.err.println(f.toString());
					fail();
				}
			}
		});

//		for (String s : failures) {
//			testOneSpec(s);
//		}
	}

}
