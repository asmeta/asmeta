package org.asmeta.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void testtemp() {
		String failures[] = {
				"/examples/sultanSuccessors/sultanGreedy.asm", 
				"/examples/Yahalom/FlawedYahalom.asm",
				"/examples/Yahalom/Yahalom.asm", 
				"/examples/Yahalom/oldVersions/YahalomVerbose.asm",
				"/examples_for_nuXmv/FunctionInt.asm", 
				"/examples_for_nuXmv/FunctionInt2.asm", 
				"/STDL/MAPEpatterns.asm",
				"/systemc/simple_bus/arbiter.asm", 
				"/SystemCUMLProfile_metahooking/main.asm",
				"/SystemCUMLProfile_metahooking/ruleSM.asm",
				"/SystemCUMLProfile_metahooking/SystemCUMLProfile_INIT.asm", 
				"/SystemCUMLProfile_metahooking/top.asm" };
		for (String s : failures) {
			testOneSpec(s);
		}
	}

}
