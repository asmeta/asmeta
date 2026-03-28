package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

// it is too difficult to test a checker in isolation
class AsmParserTestStaticDerived extends AsmParserTest {

	@BeforeAll
	static void setUp() {
		Logger.getLogger(ReflectiveVisitor.class).setLevel(Level.ALL);
		log.setLevel(Level.DEBUG);
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
	}

	// some examples with errors - they must be rejected by the parser
	@Test void specificTestWErrors() {
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

	@Test void specificTestNoErrors() {
		testOneSpec("test/parser/staticVSDerived.asm");
	}

	@Test void lifts() {
		testOneSpec("/examples/models/LIFT.asm");
		testOneSpec("/examples/models/lift2.asm");
		testOneSpec("/examples/models/lift3.asm");
		testOneSpec("/examples/models/lift3.asm");
	}

	@Test void orderSystem() {
		testOneSpec("/examples/models/ordersystem.asm");
	}

	@Test void testproduction_cell() {
		testOneSpec("examples/production_cell/robot.asm");
		testOneSpec("examples/production_cell/press.asm");
		testOneSpec("examples/production_cell/Production_Cell_with_agents.asm");
	}

	// test all the specifications - over all the projects in case fix derived/static
	@Disabled
	@Test @Tag("TestToMavenSkip") void allspecificationsandfix() throws Exception {
		ASMParser.getResultLogger().setLevel(Level.OFF);
		Files.walk(Path.of("../../../../code/"))
		.filter(x -> (x.toFile().isDirectory() || x.toString().endsWith(AsmetaParserUtility.ASM_EXTENSION))).forEach(f -> {
			String string = f.toFile().toString();
			// skip many problematic files
			if (string.contains("drafts")); else 
			if (string.contains("DAS")); else
			if (string.contains("oldVersion")); else
			if (string.contains("flashProtocol\\old")); else
			if (string.contains("test\\errors")); else				
			if (string.contains("repository\\errors")); else				
			if (string.contains("workspaceMSL")); else				
			if (string.contains("template")); else	
			if (string.contains("safePillboxForPaper")); else	
			if (string.contains("target\\classes")); else	
			if (string.contains("asmeta.simulator@run.time")); else	
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\rpns\\r")); else	
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\systemc\\simple_bus\\")); else
			if (string.contains("asmeta.modeladvisor.test\\examples\\repository\\test\\")); else
				// queste non so se funzionano convertendo derivate in static
//			if (string.contains("asmeta.modeladvisor.test\\examples\\statDerIsUsed.asm")); else
//			if (string.contains("asmeta.modeladvisor.test\\examples\\usedDomain2.asm")); else
			if (string.contains("ABZ2016\\old")); else
			if (string.endsWith(AsmetaParserUtility.ASM_EXTENSION)) {
				try {
					System.err.println("checking " + string);
					AsmCollection asmcollection = ASMParser.setUpReadAsm(f.toFile());
				} catch (Exception e) {
					if (e.getMessage().contains("does not contain dynamic functions in its definition"))
						fixDerived(f, e.getMessage());
					if (e.getMessage().contains("contains (dynamic)"))
						fixStatic(f, e.getMessage());					
					e.printStackTrace();
					//fail();
				}
			}
		});

//		for (String s : failures) {
//			testOneSpec(s);
//		}
	}

	private void fixStatic(Path x, String msg) {
		String[] data = msg.split(" ");
		String location = data[3];
		System.err.println("fixing " + x + " static: " + location + " must be derived");
		try {
			replace(x, "static " + location, "/*static*/ derived " + location);
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fixDerived(Path x, String msg) {
		String[] data = msg.split(" ");
		String location = data[3];
		System.err.println("fixing " + x + " derived: " + location + " must be static");
		try {
			replace(x, "derived " + location, "/*derived*/ static " + location);
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void replace(Path x, String old, String newS) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(x), charset);
		content = content.replaceAll(old, newS);
		Files.write(x, content.getBytes(charset));
	}

}
