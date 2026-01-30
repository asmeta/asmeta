package org.asmeta.nusmv.main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class AsmetaSMVtestTranslate extends AsmetaSMVtestTranslateBase{

	static final String FILE_BASE = "../../../../asm_examples/";
	
	@BeforeClass
	public static void checkFileBase() {
		File f = new File(FILE_BASE);
		assertTrue(f.getAbsolutePath() + " does not exist", f.exists());
		assertTrue(f.getAbsolutePath() + " does not exist", f.isDirectory());
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testFSM_hooking() {
		assertTrue(testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking.asm"));
	}

	@Test
	public void testFSM_hooking2() {
		assertTrue(testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking2.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testASM_even() {
		assertTrue(testOneSpec(FILE_BASE + "examples/fsmsemantics/Sle/ASM_even.asm"));
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testSomeSingle() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/AdvancedClock.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/AdvancedClock2.asm"));
		// domain Any not supported
		// assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/oldATM/ATM.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/Axioms.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/fattoriale.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/FLIP_FLOP_0.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/IncosistentUpdate.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/models/lift2.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/models/SIS.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_hooking2.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/fsmsemantics/FSM_map0_2.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/sluicegate/sluiceGateGround.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/sluicegate/sluiceGateMotorCtl.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight2.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight_refined.asm"));
		assertTrue(testOneSpec(FILE_BASE + "examples/traffic_light/oneWayTrafficLight_refined_with_agents.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testAtm() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/ATM.asm"));
	}

	@Test
	public void testasm1() {
		testOneSpec("examples/asm1.asm");
	}
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testAxioms() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/Axioms.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testFattoriale() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/fattoriale.asm"));
	}

	@Test
	public void testAdvancedClock() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/AdvancedClock.asm"));
	}

	@Test
	public void testAdvancedClock2() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/AdvancedClock2.asm"));
	}

	@Test
	public void testFLIP_FLOP_0() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/FLIP_FLOP_0.asm"));
	}

	@Test
	public void testIncosistentUpdate() {
		assertTrue(testOneSpec(FILE_BASE + "examples/simple_example/IncosistentUpdate.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testOrdersystem() {
		assertTrue(testOneSpec(FILE_BASE + "examples/models/ordersystem.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testPhilo1() {
		assertTrue(testOneSpec(FILE_BASE + "examples/philosophers/philosophers1.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testPhilo2() {
		assertTrue(testOneSpec(FILE_BASE + "examples/philosophers/philosophers2.asm"));
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testFlipFlop() {
		assertTrue(testOneSpec(FILE_BASE + "examples/models/FlipFlop.asm"));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testLift2() {
		assertTrue(testOneSpec(FILE_BASE + "examples/models/lift2.asm"));
	}

	@Test
	public void testSIS() {
		assertTrue(testOneSpec(FILE_BASE + "examples/models/SIS.asm"));
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testProduction_Cell_with_agents() {
		assertTrue(testOneSpec(FILE_BASE + "examples/production_cell/Production_Cell_with_agents.asm"));
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
	public void testTictactoe_forSMV() {
		RemoveNestingFlattener.PROPAGATE_EQ = true;
		assertTrue(testOneSpec("examples/Tictactoe_forSMV.asm"));
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
	public void testAmanUndef() throws IOException {
		Path smv_fileName = Path.of("examples/aman.smv");		
		Files.deleteIfExists(smv_fileName);
		// it is wrong when translating undef .. why it is using "undef" and not UNDEF????
		testOneSpec("examples/aman.asm");
		String str = Files.readString(smv_fileName);
		//System.out.println(str);
		//it should not translate this as undef
		assertFalse(str.contains("m_airplane = undef"));
		// ingeneral, there is no "undef"
		assertFalse(str.contains("undef"));
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
	
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testStudente24() {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		//AsmetaSMVOptions.FLATTEN = false;
		testOneSpec("examples/student2/fraioli.asm", options);
	}
	
	@Test
	public void testStudente_sett24() {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		//AsmetaSMVOptions.FLATTEN = false;
		testOneSpec("examples/student2/LightArray2.asm", options);
	}


	@Test
	public void testStudente_Aug25() {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		//AsmetaSMVOptions.FLATTEN = false;
		testOneSpec("examples/UseAnyDomain.asm", options);
	}

	@Test
	public void testINVAR() throws IOException {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		testOneSpec("examples/UseInvar.asm", options);
		Path fileName = Path.of("examples/UseInvar.smv");
		String str = Files.readString(fileName);
		System.err.println(str);
		//it should not translate this as undef
		assertTrue(str.contains("INVAR !(mon);"));
		assertTrue(str.contains("INVARSPEC NAME inv0 := !(foo);"));
	}

	@Test
	public void testForall() throws IOException {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.keepNuSMVfile = true;
		testOneSpec("examples/forallTerm.asm", options);
		Path fileName = Path.of("examples/forallTerm.smv");
		String str = Files.readString(fileName);
		System.err.println(str);
		//it should not translate this as undef
		assertTrue(str.contains("INVARSPEC NAME"));
		assertTrue(str.contains("CTLSPEC NAME"));
	}

	
	/*
	 * @Test public void testUnibgProva() { assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "Prova.asm"); }
	 * 
	 * @Test public void testUnibgRegistroDiCassa() { assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "RegistroDiCassa.asm"); }
	 * 
	 * @Test public void testUnibgRubrica() { assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "Rubrica.asm"); }
	 * 
	 * @Test public void teststufa() { assertTrue(testOneSpec(FILE_BASE_UNIBG + "stufa.asm"); }
	 * 
	 * 
	 * @Test public void testUnibgVideotecaASM() { assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "VideotecaASM.asm"); }
	 * 
	 * @Test public void testUnibg() { assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "Contatore_U_DA_H.asm"); assertTrue(testOneSpec(FILE_BASE_UNIBG + "Prova.asm");
	 * assertTrue(testOneSpec(FILE_BASE_UNIBG + "RegistroDiCassa.asm");
	 * assertTrue(testOneSpec(FILE_BASE_UNIBG + "Rubrica.asm"); assertTrue(testOneSpec(FILE_BASE_UNIBG +
	 * "stufa.asm"); assertTrue(testOneSpec(FILE_BASE_UNIBG + "VideotecaASM.asm"); }
	 */
	
	
}