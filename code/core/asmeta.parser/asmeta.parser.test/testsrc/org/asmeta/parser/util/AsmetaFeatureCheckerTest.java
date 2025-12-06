package org.asmeta.parser.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;

public class AsmetaFeatureCheckerTest {

	@Test
	public void testVisitAsm2() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof MonitoredFunction));
		File f = new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		spr = new AsmetaFeatureChecker(x -> (x instanceof OutFunction));
		// it has no monitored functions
		assertFalse(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// remove all appenders to avoid bloating the console
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
		

	}

}
