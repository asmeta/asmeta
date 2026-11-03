package org.asmeta.atgt.generator;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmTestSuite;

// compare the 
public class NuSMVAsmTestGeneratorComparison {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.ALL);
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.ALL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String spec = "examples/ImpiantoSemaforico.asm";
		NuSMVASMSpecTestGenerator gen1 = new NuSMVASMSpecTestGenerator(spec);
		AsmTestSuite test = gen1.generateAbstractTests(1, ".*");
		System.out.println(test.getTests());
		// stesso con altra classe
		NuSMVAsmetaTestGenerator gen2 = new NuSMVAsmetaTestGenerator(spec, false);
		test = gen2.generateAbstractTests(1, ".*");
		System.out.println(test.getTests());		
				
	}

}
