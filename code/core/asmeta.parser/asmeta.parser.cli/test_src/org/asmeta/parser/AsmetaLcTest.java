package org.asmeta.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * cosa fa esattamente ?? AG
 * 
 */
public class AsmetaLcTest {

	@BeforeAll
	static void setUpLogger() {
		//
//		Logger log = Logger.getLogger("org.asmeta.parser");
//		if (!log.getAllAppenders().hasMoreElements())
//			log.addAppender(new ConsoleAppender(new SimpleLayout()));
//		log.setLevel(Level.INFO);
	}

	public static final String FILE_BASE = "../../../../asm_examples/";

	protected boolean testOneSpec(String spec) {
		String[] args = { "-xmi", spec };
		AsmetaLc.main(args);
		int i = spec.lastIndexOf(AsmetaParserUtility.ASM_EXTENSION);
		// String s = new String(spec.substring(0, i));
		String s = spec.substring(0, i);
		File f = new File(s + ".xmi");
		assertTrue(f.exists(),
				"file " + f.getAbsolutePath() + " does not exist, current dir: " + new File(".").getAbsolutePath());
		return f.exists();
	}

	protected boolean testOneSpec(File fspec) {
		return testOneSpec(fspec.getAbsolutePath());
	}

	protected void testDir(String dir) {
		Collection<File> res = testSpecInSubFolder(dir);
		assertTrue(res.isEmpty(), res.toString());
	}

	/**
	 * recursive test test all the specs in the subdirectory of asm_examples called
	 * dirname if it contains a directory, all the specs in it will be tested
	 */
	private Collection<File> testSpecInSubFolder(String dirname) {
		Collection<File> failedSpec = new ArrayList<File>();
		File dir = new File(FILE_BASE + dirname);
		assertTrue(dir.isDirectory(), "example dir " + dir.getAbsolutePath() + " does not exist, current dir: "
				+ new File(".").getAbsolutePath());
		// read all the specs
		for (File f : dir.listFiles(new ASMFileFilter())) {
			if (!testOneSpec(f))
				failedSpec.add(f);
		}
		// test dirs
		for (File f : dir.listFiles(new ASMDirFilter())) {
			if (f.isDirectory())
				failedSpec.addAll(testSpecInSubFolder(dirname + "/" + f.getName()));
		}
		return failedSpec;

	}

	@Test void spec1() {
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking.asm");
		// testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock.asm");
	}

	@Test void simpleEx() {
		testDir("examples/simple_example/");
	}

	@Test void fsm() {
		testDir("examples/fsmsemantics/");
	}

	@Test void trafficLight() {
		testDir("examples/traffic_light/");
	}

	@Test void examplesModels() {
		testDir("examples/models/");
	}

	@Test void examplesPhilo() {
		testDir("examples/philosophers/");
	}

	@Test void productionCell() {
		testDir("examples/production_cell/");
	}

	@Test void examplesAgents() {
		testDir("examples/agents/");
	}

	@Test void examplesSG() {
		testDir("examples/sluicegate/");
	}

	@Test void library() {
		testDir("examples/library");
	}

	@Test void systemC() {
		testDir("systemc");
	}

	@Test void FSMSLE() {
		testDir("examples/fsmsemantics/Sle");
	}
		
}
