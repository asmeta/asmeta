package org.asmeta.toyices;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Ignore;
import org.junit.Test;

public class SMTbasedASMsimulatorTest {
	
	private static boolean printOutput = true;

	static public void test(String asmFile) throws Exception {
		PrintStream standardOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asmFile);
		assertTrue(visitor.visitASM());
		assertTrue(visitor.assertRules());
		System.setOut(standardOut);
		String output = baos.toString();
		if(printOutput) {
			System.out.println(output);
		}
		baos.close();
		ps.close();
		if(output.contains("A fatal error has been detected by the Java Runtime Environment")) {
			fail();
		}
	}

	@Test
	public void testBoard() throws Exception {
		test("models/board.asm");
	}

	@Test
	public void testBoard2() throws Exception {
		test("models/board2.asm");
	}

	@Test
	public void testCaseRule() throws Exception {
		test("models/caseRule.asm");
	}

	@Test
	public void testCaseRule2() throws Exception {
		test("models/caseRule2.asm");
	}

	@Test
	public void testCaseRule3() throws Exception {
		test("models/caseRule3.asm");
	}

	@Test
	public void testCaseTerm() throws Exception {
		test("models/caseTerm.asm");
	}

	@Test
	public void testCaseTerm2() throws Exception {
		test("models/caseTerm2.asm");
	}

	@Test
	public void testCaseTerm3() throws Exception {
		test("models/caseTerm3.asm");
	}

	@Test
	public void testCaseTerm4() throws Exception {
		test("models/caseTerm4.asm");
	}

	@Test
	public void testChoose() throws Exception {
		test("models/choose.asm");
	}

	@Test
	public void testChoose2() throws Exception {
		test("models/choose2.asm");
	}

	@Test
	public void testChoose3() throws Exception {
		test("models/choose3.asm");
	}

	@Test
	public void testChoose4() throws Exception {
		test("models/choose4.asm");
	}

	@Test
	public void testChoose5() throws Exception {
		test("models/choose5.asm");
	}

	@Test
	public void testChoose6() throws Exception {
		test("models/choose6.asm");
	}

	@Test
	public void testChoose7() throws Exception {
		test("models/choose7.asm");
	}

	@Test
	public void testChoose8() throws Exception {
		test("models/choose8.asm");
	}
	
	@Test
	public void testChooseOtherwise() throws Exception {
		test("models/chooseOtherwise.asm");
	}

	@Test
	public void testCloudRoxana1() throws Exception {
		test("models/cloudRoxana/ClientDisplayOutput.asm");
	}

	@Test
	public void testCloudRoxana2() throws Exception {
		test("models/cloudRoxana/ClientDisplayOutputWithCookie.asm");
	}

	@Test
	public void testCloudRoxana3() throws Exception {
		test("models/cloudRoxana/ClientDisplayOutputWithCookieTags.asm");
	}

	@Test
	public void testDerived() throws Exception {
		test("models/derived.asm");
	}

	@Test
	public void testDerived2() throws Exception {
		test("models/derived2.asm");
	}

	@Test
	public void testForall() throws Exception {
		test("models/forall.asm");
	}

	@Test
	public void testForall2() throws Exception {
		test("models/forall2.asm");
	}

	@Test
	public void testForallForall() throws Exception {
		test("models/forallForall.asm");
	}

	@Test
	public void testForallForall2() throws Exception {
		test("models/forallForall2.asm");
	}

	@Test
	public void testFuncs() throws Exception {
		test("models/funcs.asm");
	}

	@Test
	public void testInitUndef() throws Exception {
		test("models/initUndef.asm");
	}

	@Test
	public void testLetRule() throws Exception {
		test("models/letRule.asm");
	}

	@Test
	public void testMacroCallRule() throws Exception {
		test("models/macroCallRule.asm");
	}

	@Test
	public void testMon() throws Exception {
		test("models/mon.asm");
	}

	@Test
	public void testNAryFuncUndef() throws Exception {
		test("models/nAryFuncUndef.asm");
	}

	@Test
	public void testRoulette() throws Exception {
		test("models/roulette.asm");
	}

	@Test
	public void testSimpleModel() throws Exception {
		test("models/simpleModel.asm");
	}

	@Test
	public void testTicTacToe_forMonitoring() throws Exception {
		test("models/ticTacToe_forMonitoring.asm");
	}

	@Test
	public void testTictactoe() throws Exception {
		test("models/Tictactoe.asm");
	}

	@Test
	public void testTictactoeSimple() throws Exception {
		test("models/TictactoeSimple.asm");
	}

	@Test
	public void testHemodialysis01() throws Exception {
		test("models/ABZ2016/Hemodialysis01.asm");
	}

	@Test
	public void testHemodialysis02() throws Exception {
		test("models/ABZ2016/Hemodialysis02.asm");
	}

	@Test
	public void testHemodialysis03() throws Exception {
		test("models/ABZ2016/Hemodialysis03.asm");
	}

	@Test
	public void testHemodialysis04() throws Exception {
		test("models/ABZ2016/Hemodialysis04.asm");
	}

	@Test
	public void testHemodialysis05() throws Exception {
		test("models/ABZ2016/Hemodialysis05.asm");
	}

	@Test
	public void testTank() throws Exception {
		test("models/Tank.asm");
	}
	@Test
	public void testPillBoxTutorial() throws Exception {
		SMTbasedASMsimulator.setLogger();
		test("../../../../asmeta_models/tutorials/tutorial_FM24/pillbox_ground.asm");
	}
	
	@Test
	@Ignore
	// for some reasons this fails
	public void testABZCaseStudy() throws Exception {
		String file = "D:\\AgHome\\progettidaSVNGIT\\ricerca\\abz2025_casestudy_autonomous_driving\\asmeta spec\\models\\SafetyEnforcer.asm";
		SMTbasedASMsimulator.setLogger();
		test(file);
	}

}