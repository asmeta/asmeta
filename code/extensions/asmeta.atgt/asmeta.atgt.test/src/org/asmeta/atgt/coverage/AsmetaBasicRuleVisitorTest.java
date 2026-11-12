package org.asmeta.atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.flattener.rule.RuleFlattener;
import org.asmeta.parser.ASMParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.BasicRuleVisitor;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public class AsmetaBasicRuleVisitorTest {

	private static final String FILE_BASE = AsmetaBasicRuleVisitorTestExp.FILE_BASE;

	@BeforeAll
	public static void setup() {
//		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.DEBUG);
//		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.DEBUG);
//		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.DEBUG);
//		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
//		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
	}

	@Test
	@Tag("TestToMavenSkip")
	public void testGetTPTree() throws Exception {
		// String ex =
		// "D:\\AgDocuments\\progettiDaSVN\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		// String ex =
		// "D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM\\Ventilatore2.asm";
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatoreASM_NewTime/Ventilatore4SimpleTimeLtdY.asm";
		generateCoverageFor(ex, false, true);
	}

	@Test
	@Tag("TestToMavenSkip")
	public void testGetTPTreeMVM() throws Exception {
		generateCoverageFor("examples\\mvm0.asm", false, true);
	}

	@Test
	public void testGetTPWithErrorsCorrected() throws Exception {
		// Logger.getLogger(AsmetaToExprTrans.class).setLevel(Level.DEBUG);
		checkSpec(FILE_BASE + "examples\\sluicegate\\sluiceGateMotorCtl.asm");
		checkSpec(FILE_BASE + "examples\\traffic_light\\forAsmetaSMV\\oneWayTrafficLight_4SMV.asm");
		checkSpec(FILE_BASE + "PillBox\\Level2\\pillbox_2.asm");
		checkSpec(FILE_BASE + "examples\\models\\lift2.asm");
	}

	private void checkSpec(String ex) throws Exception {
		int tps = generateCoverageFor(ex, false, true);
		assertTrue(tps > 0, "tps should be present");
	}

	@Test
	public void testdoubleGeneration() throws Exception {
		// generating twice should wkr well
		int tp1 = generateCoverageFor(FILE_BASE + "stereoacuity\\certifierRaff5.asm", false, true);
		int tp2 =generateCoverageFor(FILE_BASE + "stereoacuity\\certifierRaff5.asm", false, true);
		assertTrue(tp1>0);
		assertEquals(tp1, tp2);
	}

	
	@Test
	public void testDoubleCall() throws Exception {
		// Logger.getLogger(AsmetaToExprTrans.class).setLevel(Level.DEBUG);
		int tps = generateCoverageFor(FILE_BASE + "stereoacuity\\certifierRaff5.asm", false, true);
		System.out.println(tps);
		tps = generateCoverageFor(FILE_BASE + "stereoacuity\\certifierRaff5.asm", false, true);
	}

	@Test
	public void testNuSMVWorking() throws Exception {
		// Logger.getLogger(AsmetaToExprTrans.class).setLevel(Level.DEBUG);
		Logger.getLogger(AsmetaBasicRuleVisitor.class).setLevel(Level.DEBUG);
		// Sequential rules - how to treat them????
		// int tps = generateCoverageFor(FILE_BASE + "examples\\simple_example\\FLIP_FLOP_0.asm");
		int tps = generateCoverageFor(FILE_BASE + "examples\\ABZ2020\\CarSystemModule\\CarSystem006\\CarSystem006main.asm", false, true);
		System.out.println(tps);
	}
	
	
	@Test
	public void testGetTPTreeChoose() throws Exception {
		int tps;
		tps = generateCoverageFor("examples\\SpecWithChoose.asm", true, true);
		assertEquals(1, tps);
		// not clear what to do with simple choose without conditions... any step would suffice
		tps = generateCoverageFor("examples\\simplechoose.asm", true, true);
		assertEquals(0, tps);
		//tps = generateCoverageFor("examples\\simplechoose.asm", true, false);
		//assertEquals(1, tps);
		tps = generateCoverageFor("examples\\simplechooseBiGDomain.asm", true, true);
		assertEquals(2, tps);
		// with the function
		tps = generateCoverageFor("examples/simplechoose_f.asm", true, true);
		assertEquals(2, tps);
		generateCoverageFor(FILE_BASE + "examples\\petriNets\\forAsmetaSMV\\petriNet_forNuSMV.asm", false, true);
		// one tp: $i = 0
		assertEquals(1, tps);
	}


	/**
	 * @param ex
	 * @param printTps TODO
	 * @return
	 * @throws Exception
	 * @deprecated Use {@link #generateCoverageFor(String,boolean,boolean)} instead
	 */
	static int generateCoverageFor(String ex, boolean printTps) throws Exception {
		return generateCoverageFor(ex, printTps, true);
	}

	/**
	 * @param ex
	 * @param printTps TODO
	 * @param newCoveBuilder TODO
	 * @return
	 * @throws Exception
	 */
	static int generateCoverageFor(String ex, boolean printTps, boolean newCoveBuilder) throws Exception {
		File f = new File(ex);
		if (!f.exists()) {
			throw new RuntimeException(f + " does not exists");
		}
		try {
			AsmCoverage tp;
			if (newCoveBuilder){
				AsmetaBasicRuleVisitor tpbuilder = new AsmetaBasicRuleVisitor();
				asmeta.AsmCollection asms = ASMParser.setUpReadAsm(f);
				AsmetaAsSpec spec = new AsmetaAsSpec(asms);
				tp = tpbuilder.getTPTree(spec);				
			} else {
				AsmCoverageBuilder tpbuilder = new BasicRuleVisitor();
				ASMSpecification spec = new AsmetaLLoader().read(f, true);
				tp = tpbuilder.getTPTree(spec);
			}
			if (printTps) tp.allTPs().forEach(x -> System.out.println(x.getCondition()));
			return tp.getNumberofTPs();
		} catch (Throwable t) {
			if (t.getMessage() == null) {
				System.err.println("*** no resason provided");
				t.printStackTrace();
				System.err.println("***");
			}
			t.printStackTrace();
			throw new RuntimeException("spec not analyzable " + t.getMessage());
		}
	}

}
