package org.asmeta.nusmv;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.asmeta.parser.ASMFileFilter;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * test all the examples in a directory parse and translate DO NOT PROVE
 * 
 */
@Category(org.asmeta.annotations.TestToMavenSkip.class)
public class AsmetaSMVtestTranslate {

	static final String FILE_BASE = "../../../../asm_examples/";
	
	@BeforeClass
	public static void checkFileBase() {
		File f = new File(FILE_BASE);
		assertTrue(f.getAbsolutePath() + " does not exist", f.exists());
		assertTrue(f.getAbsolutePath() + " does not exist", f.isDirectory());
	} 
	
	

	protected boolean testOneSpec(String spec) {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		AsmetaSMVOptions.keepNuSMVfile = true;
		//AsmetaSMVOptions.FLATTEN = false;
		return testOneSpec(spec, options);
	}

	/**
	 * @param args
	 * @param options
	 * @return
	 */
	protected boolean testOneSpec(String spec, AsmetaSMVOptions options) {
		String[] args = { spec };
		try {
			AsmetaSMV a = new AsmetaSMV(new File(args[0]),options);
			a.translation();
			a.createNuSMVfile();
			// the file exists
			if (!Files.exists(Paths.get(a.smvFileName)))
				return false;
			System.out.println(Paths.get(a.smvFileName));
			// TODO parse the file with nusmv
			if (options.isRunNuSMV()) a.executeNuSMV();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected boolean testOneSpec(File fspec) {
		String spec = fspec.getAbsolutePath();
		return testOneSpec(spec);
	}

	protected void testDir(String dir) {
		Collection<File> res = testSpecInSubFolderBASEDIR(dir);
		assertTrue(res.toString(), res.isEmpty());
	}

	// test all the files ina subdir of basedir
	private Collection<File> testSpecInSubFolderBASEDIR(String dirname) {
		return testSpecInSubFolder(FILE_BASE + dirname);
	}

	private Collection<File> testSpecInSubFolder(String dirname) {
		Collection<File> failedSpec = new ArrayList<File>();
		File dir = new File(dirname);
		assertTrue("example dir " + dir.getAbsolutePath() + " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), dir.isDirectory());
		// read all the specs
		for (File f : dir.listFiles(new ASMFileFilter())) {
			if (!testOneSpec(f)) {
				failedSpec.add(f);
			}
		}

		// test dirs
		for (File f : dir.listFiles()) {
			if (f.isDirectory())
				failedSpec.addAll(testSpecInSubFolder(dirname + "/" + f.getName()));
		}
		return failedSpec;

	}
	
		
	@Test
	public void testFSM_hooking() {
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking.asm");
	}

	@Test
	public void testFSM_hooking2() {
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking2.asm");
	}

	@Test
	public void testASM_even() {
		testOneSpec(FILE_BASE + "examples/fsmsemantics/Sle/ASM_even.asm");
	}


	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testfsmsemanticsmeta_hooking() {
		testDir("examples/fsmsemantics/meta_hooking/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testfsmsemantics() {
		testDir("examples/fsmsemantics/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testSimpleEx() {
		testDir("examples/examples/simple_example/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testsluicegate() {
		testDir("examples/sluicegate/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testtraffic_light() {
		testDir("examples/traffic_light/");
	}


	@Test
	public void testSomeSingle() {
		testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock2.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/ATM.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/Axioms.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/fattoriale.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/FLIP_FLOP_0.asm");
		testOneSpec(FILE_BASE + "examples/simple_ex/IncosistentUpdate.asm");
		testOneSpec(FILE_BASE + "examples/models/lift2.asm");
		testOneSpec(FILE_BASE + "examples/models/SIS.asm");
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking2.asm");
		testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_map0_2.asm");
		testOneSpec(FILE_BASE + "examples/sluicegate/sluiceGateGround.asm");
		testOneSpec(FILE_BASE + "examples/sluicegate/sluiceGateMotorCtl.asm");
		testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight.asm");
		testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight2.asm");
		testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight_refined.asm");
		testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight_refined_with_agents.asm");
	}

	@Test
	public void testAtm() {
		testOneSpec(FILE_BASE + "examples/simple_ex/ATM.asm");
	}

	@Test
	public void testasm1() {
		testOneSpec("examples/asm1.asm");
	}
	
	@Test
	public void testAxioms() {
		testOneSpec(FILE_BASE + "examples/simple_ex/Axioms.asm");
	}

	@Test
	public void testFattoriale() {
		testOneSpec(FILE_BASE + "examples/simple_ex/fattoriale.asm");
	}

	@Test
	public void testAdvancedClock() {
		testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock.asm");
	}

	@Test
	public void testAdvancedClock2() {
		testOneSpec(FILE_BASE + "examples/simple_ex/AdvancedClock2.asm");
	}

	@Test
	public void testFLIP_FLOP_0() {
		testOneSpec(FILE_BASE + "examples/simple_ex/FLIP_FLOP_0.asm");
	}

	@Test
	public void testIncosistentUpdate() {
		testOneSpec(FILE_BASE + "examples/simple_ex/IncosistentUpdate.asm");
	}

	@Test
	public void testOrdersystem() {
		testOneSpec(FILE_BASE + "examples/models/ordersystem.asm");
	}

	@Test
	public void testPhilo1() {
		testOneSpec(FILE_BASE + "examples/philosophers/philosophers1.asm");
	}

	@Test
	public void testPhilo2() {
		testOneSpec(FILE_BASE + "examples/philosophers/philosophers2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testFSM() {
		testDir("examples/fsmsemantics/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testTrafficLight() {
		testDir("examples/traffic_light/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testExamplesModels() {
		testDir("examples/models/");
	}

	@Test
	public void testFlipFlop() {
		testOneSpec(FILE_BASE + "examples/models/FlipFlop.asm");
	}

	@Test
	public void testLift2() {
		testOneSpec(FILE_BASE + "examples/models/lift2.asm");
	}

	@Test
	public void testSIS() {
		testOneSpec(FILE_BASE + "examples/models/SIS.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testExamplesPhilo() {
		testDir("examples/philosophers/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testProductionCell() {
		testDir("examples/production_cell/");
	}

	@Test
	public void testProduction_Cell_with_agents() {
		testOneSpec(FILE_BASE + "examples/production_cell/Production_Cell_with_agents.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testExamplesCluster() {
		testDir("examples/cluster/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testExamplesAgents() {
		testDir("examples/agents/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testExamplesSG() {
		testDir("examples/sluicegate/");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testLibrary() {
		testDir("examples/library");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testSystemC() {
		//some of these files are not translable
		testDir("systemc");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void FSMSLE() {
		testDir("examples/fsmSle/ASM_even.asm");
	}

	// examples in the current project
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testAllExamples() {
		Collection<File> testSpecInSubFolder = testSpecInSubFolder("examples");
		assertTrue(testSpecInSubFolder.toString(), testSpecInSubFolder.isEmpty());
	}

	@Test
	public void testAllExamplesTelecamere() {
		assertTrue(testOneSpec("examples/telecamere/telecamere1.asm"));
		assertTrue(testOneSpec("examples/telecamere/telecamere0.asm"));
	}

	@Test
	public void testTelecamere0noPropagateEq() {
		RemoveNestingFlattener.PROPAGATE_EQ = false;
		assertTrue(testOneSpec("examples/telecamere/telecamere0.asm"));
	}

	@Test
	public void testTelecamere0PropagateEq() {
		RemoveNestingFlattener.PROPAGATE_EQ = true;
		assertTrue(testOneSpec("examples/telecamere/telecamere0.asm"));
	}

	@Test
	public void testTelecamere2() {
		assertTrue(testOneSpec("examples/telecamere/telecamere2.asm"));
	}

	@Test
	public void testCaseTerms2() {
		assertTrue(testOneSpec("examples/caseTerm2.asm"));
		assertTrue(testOneSpec("examples/caseTerm3.asm"));
		assertTrue(testOneSpec("examples/caseTerm4.asm"));
		assertTrue(testOneSpec("examples/caseTerm5.asm"));
	}

	
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testTelecamereV2() {
		RemoveNestingFlattener.PROPAGATE_EQ = true;
		assertTrue(testOneSpec("examples/telecamere/telecamere_v2.asm"));
	}
		
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testPillBOX() {
		Logger.getLogger(ReflectiveVisitor.class).setLevel(Level.ALL);
		testOneSpec("F:\\Dati-Andrea\\GitHub\\quasmed\\PillboxASM\\pillbox_for_PropertyVerification.asm");
	}
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testPillBOX2() {
		Logger.getLogger(ReflectiveVisitor.class).setLevel(Level.ALL);
		testOneSpec("F:\\Dati-Andrea\\GitHub\\quasmed\\PillboxASM\\pillbox_for_PropertyVerification_1.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testpillBoxTestAG() {
		testOneSpec("C:\\Users\\garganti\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\pillbox_for_PropertyVerification.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testAmanUndef() throws IOException {
		// it is worng when tralsating undef .. why itis using undef and not UNDEF????
		testOneSpec("examples/aman.asm");
		Path fileName = Path.of("examples/aman.smv");
		String str = Files.readString(fileName);
		System.out.println(str);
		//it should not translate this as undef
		assertFalse(str.contains("m_airplane = undef"));
	}
	
	@Test
	public void testAmanNull() throws IOException {
		AsmetaSMVOptions option = new AsmetaSMVOptions(true, true, false, true, false);
		option.keepNuSMVfile = true;
		option.setUseNuXmv(true);
		option.FLATTEN = false;
		option.setRunNuSMV(true);
		testOneSpec("examples/aman0.asm", option);
		Path fileName = Path.of("examples/aman0.smv");
		String str = Files.readString(fileName);
		//it should not translate this as undef
		assertFalse(str.contains("null"));
		assertFalse(str.contains("is false"));
	}

	
	@Test
	public void testDivision() {
		// nuxmv
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		options.setUseNuXmv(true);
		options.FLATTEN = false;
		options.setRunNuSMV(false);
		//AsmetaSMVOptions.FLATTEN = false;
		testOneSpec("examples/division.asm", options);
	}
	
	

	
	/*
	 * @Test public void testUnibgProva() { testOneSpec(FILE_BASE_UNIBG +
	 * "Prova.asm"); }
	 * 
	 * @Test public void testUnibgRegistroDiCassa() { testOneSpec(FILE_BASE_UNIBG +
	 * "RegistroDiCassa.asm"); }
	 * 
	 * @Test public void testUnibgRubrica() { testOneSpec(FILE_BASE_UNIBG +
	 * "Rubrica.asm"); }
	 * 
	 * @Test public void teststufa() { testOneSpec(FILE_BASE_UNIBG + "stufa.asm"); }
	 * 
	 * 
	 * @Test public void testUnibgVideotecaASM() { testOneSpec(FILE_BASE_UNIBG +
	 * "VideotecaASM.asm"); }
	 * 
	 * @Test public void testUnibg() { testOneSpec(FILE_BASE_UNIBG +
	 * "Contatore_U_DA_H.asm"); testOneSpec(FILE_BASE_UNIBG + "Prova.asm");
	 * testOneSpec(FILE_BASE_UNIBG + "RegistroDiCassa.asm");
	 * testOneSpec(FILE_BASE_UNIBG + "Rubrica.asm"); testOneSpec(FILE_BASE_UNIBG +
	 * "stufa.asm"); testOneSpec(FILE_BASE_UNIBG + "VideotecaASM.asm"); }
	 */
}