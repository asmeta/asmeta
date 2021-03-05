package org.asmeta.avallaxt.validator.test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ParserResultLogger;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.SimulatorWCov;
import org.junit.Test;

public class TestMultipleImports extends TestValidator {
	
	@Test
	public void testMultipleImports1() throws Exception {
		AsmetaV.fileNames = new HashMap<String, String>();
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		test("F:\\Dati-Andrea\\GitHub\\asmeta\\asm_examples\\examples\\ABZ2020\\CarSystemModule\\CarSystem003\\scenari\\HighBeamFlasherONOFF.avalla", true);		
	}
	
	@Test
	public void testMultipleImports2() throws Exception {
		AsmetaV.fileNames = new HashMap<String, String>();
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		test("F:\\Dati-Andrea\\GitHub\\asmeta\\asm_examples\\examples\\ABZ2020\\CarSystemModule\\CarSystem003\\scenari\\HighBeamFixedONOFF.avalla", true);		
	}

	@Test
	public void testMultipleImports3() throws Exception {
		AsmetaV.fileNames = new HashMap<String, String>();
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		test("F:\\Dati-Andrea\\GitHub\\asmeta\\asm_examples\\examples\\ABZ2020\\CarSystemModule\\CarSystem003\\scenari\\HighBeamFlasherONOFF_Absolute.avalla", true);		
	}

	
	
	
}
