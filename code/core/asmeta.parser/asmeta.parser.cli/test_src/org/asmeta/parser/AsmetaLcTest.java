package org.asmeta.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * cosa fa esattamente ?? AG
 * 
 */
public class AsmetaLcTest {

	@BeforeClass
	public static void setUpLogger() {
		//
		Logger log = Logger.getLogger("org.asmeta.parser");
		if (!log.getAllAppenders().hasMoreElements())
			log.addAppender(new ConsoleAppender(new SimpleLayout()));
		log.setLevel(Level.INFO);
	}

	public static final String FILE_BASE = "../../../../asm_examples/";

	protected void testOneSpec(String spec) {
		String[] args = { "-xmi", spec };
		AsmetaLc.main(args);
		int i = spec.lastIndexOf(ASMParser.ASM_EXTENSION);
		// String s = new String(spec.substring(0, i));
		String s = spec.substring(0, i);
		File f = new File(s + ".xmi");
		assertTrue("file " + f.getAbsolutePath() + " does not exist, current dir: " + new File(".").getAbsolutePath(),
				f.exists());

	}

	protected boolean testOneSpec(File fspec) {
		String spec = fspec.getAbsolutePath();
		String[] args = { "-xmi", spec };
		AsmetaLc.main(args);
		int i = spec.lastIndexOf(ASMParser.ASM_EXTENSION);
		// String s = new String(spec.substring(0, i));
		String s = spec.substring(0, i);
		File f = new File(s + ".xmi");
		assertTrue("file " + f.getAbsolutePath() + " does not exist, current dir: " + new File(".").getAbsolutePath(),
				f.exists());
		return f.exists();

	}

	protected void testDir(String dir) {
		Collection<File> res = testSpecInSubFolder(dir);
		assertTrue(res.toString(), res.isEmpty());
	}

	/**
	 * recursive test test all the specs in the subdirectory of asm_examples called
	 * dirname if it contains a directory, all the specs in it will be tested
	 */
	private Collection<File> testSpecInSubFolder(String dirname) {
		Collection<File> failedSpec = new ArrayList<File>();
		File dir = new File(FILE_BASE + dirname);
		assertTrue("example dir " + dir.getAbsolutePath() + " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), dir.isDirectory());
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

	@Test
	public void testSpec1() {
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking.asm");
		// testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock.asm");
	}

	@Test
	public void testSimpleEx() {
		testDir("examples/simple_example/");
	}

	@Test
	public void testFSM() {
		testDir("examples/fsmsemantics/");
	}

	@Test
	public void testTrafficLight() {
		testDir("examples/traffic_light/");
	}

	@Test
	public void testExamplesModels() {
		testDir("examples/models/");
	}

	@Test
	public void testExamplesPhilo() {
		testDir("examples/philosophers/");
	}

	@Test
	public void testProductionCell() {
		testDir("examples/production_cell/");
	}

	@Test
	public void testExamplesAgents() {
		testDir("examples/agents/");
	}

	@Test
	public void testExamplesSG() {
		testDir("examples/sluicegate/");
	}

	@Test
	public void testLibrary() {
		testDir("examples/library");
	}

	@Test
	public void testSystemC() {
		testDir("systemc");
	}

	@Test
	public void FSMSLE() {
		testDir("examples/fsmsemantics/Sle");
	}
}
