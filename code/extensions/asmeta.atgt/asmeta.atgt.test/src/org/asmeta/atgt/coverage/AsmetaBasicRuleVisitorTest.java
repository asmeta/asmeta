package org.asmeta.atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		generateCoverageFor(ex);
	}

	@Test
	@Tag("TestToMavenSkip")
	public void testGetTPTreeMVM() throws Exception {
		generateCoverageFor("examples\\mvm0.asm");
	}

	@Test
	public void testGetTPWithErrorsCorrected() throws Exception {
		// Logger.getLogger(AsmetaToExprTrans.class).setLevel(Level.DEBUG);
		checkSpec(FILE_BASE + "examples\\sluicegate\\sluiceGateMotorCtl.asm");
		checkSpec(FILE_BASE + "examples\\traffic_light\\forAsmetaSMV\\oneWayTrafficLight.asm");
		checkSpec(FILE_BASE + "PillBox\\Level2\\pillbox_2.asm");
		checkSpec(FILE_BASE + "examples\\models\\lift2.asm");
	}

	private void checkSpec(String ex) throws Exception {
		int tps = generateCoverageFor(ex);
		assertTrue(tps > 0, "tps should be present");
	}

	@Test
	public void testGetTPWithErrors() throws Exception {
		// Logger.getLogger(AsmetaToExprTrans.class).setLevel(Level.DEBUG);
		checkSpec(FILE_BASE + "examples\\petriNets\\forAsmetaSMV\\petriNet_forNuSMV.asm");
	}

	@Test
	public void testGetTPTreeChoose() throws Exception {
		int tps = generateCoverageFor("examples\\SpecWithChoose.asm");
		// one tp: $i = 0
		assertEquals(1, tps);
	}

	/**
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	static int generateCoverageFor(String ex) throws Exception {
		File f = new File(ex);
		if (!f.exists()) {
			throw new RuntimeException(f + " does not exists");
		}
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(f);
		AsmetaBasicRuleVisitor tpbuilder = new AsmetaBasicRuleVisitor();
		try {
			AsmetaAsSpec spec = new AsmetaAsSpec(asms);
			AsmCoverage tp = tpbuilder.getTPTree(spec);
			// tp.allTPs().forEach(x -> System.out.println(x.getCondition()));
			return tp.getNumberofTPs();
		} catch (Throwable t) {
			if (t.getMessage() == null) {
				System.err.println("*** no resason provided");
				t.printStackTrace();
				System.err.println("***");
			}
			throw new RuntimeException("spec not analyzable " + t.getMessage());
		}
	}

}
