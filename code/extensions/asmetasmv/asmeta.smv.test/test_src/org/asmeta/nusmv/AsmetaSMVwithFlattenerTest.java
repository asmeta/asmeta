package org.asmeta.nusmv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsmetaSMVwithFlattenerTest extends AsmetaSMVtest {

	private static boolean old;

	@BeforeClass
	public static void setFlattener() {
		old = AsmetaSMVOptions.FLATTEN;
		AsmetaSMVOptions.FLATTEN = true;
	}
		
	@AfterClass
	public static void setFlattenerOld() {
		AsmetaSMVOptions.FLATTEN = old;
	}

	
	@Test
	public void dirFilesTest() {
		testAllCtlPropsAreTrue("examples/files/agentsMain.asm", 1);
	}

	@Test
	public void dirFilesConcrDomTest() {
		testAllCtlPropsAreTrue("examples/filesConcrDom/agentsMain.asm", 1);
	}

	@Test
	public void dirOneWayTrafficLightAgentsTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLightAgents/owtl_main.asm", 1);
	}

	@Test
	public void dirSultanSuccessorsSimulatorTest() {
		List<Boolean> results = getResults("examples/sultanSuccessors/sultan.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		while (i.hasNext()) {
			assertFalse(i.next());
		}
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoardNoSolution() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardNoSolution.asm", 1);
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoradSolutionGreaterK() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardSolutionGreaterK.asm", 1);
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoradSolutionLessK() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardSolutionLessK.asm", 1);
	}

	@Test
	public void dirTaxiTest() {
		testAllCtlPropsAreTrue("examples/taxi/main.asm", 1);
	}

	@Test
	public void dirTaxiSingTest() {
		testAllCtlPropsAreTrue("examples/taxiSing/main.asm", 1);
	}

	@Test
	public void dirTicTacToeTest() {
		testAllCtlPropsAreTrue("examples/ticTacToe/ticTacToe_simulator.asm", 1);
	}

	@Test
	public void absTest() {
		testAllCtlPropsAreTrue("examples/abs.asm", 1);
	}

	@Test
	public void abstractTest() {
		testAllCtlPropsAreTrue("examples/abstract.asm", 1);
	}

	@Test
	public void agTest() {
		testAllCtlPropsAreTrue("examples/ag.asm", 1);
	}

	@Test
	public void agentsTest() {
		testAllCtlPropsAreTrue("examples/agents.asm", 1);
	}

	@Test
	public void agents1Test() {
		testAllCtlPropsAreTrue("examples/agents1.asm", 1);
	}

	@Test
	public void agents3Test() {
		testAllCtlPropsAreTrue("examples/agents3.asm", 1);
	}

	@Test
	public void agentsAsyncrTest() {
		testAllCtlPropsAreTrue("examples/agentsAsyncr.asm", 1);
	}

	@Test
	public void agentsAsyncr2Test() {
		testAllCtlPropsAreTrue("examples/agentsAsyncr2.asm", 1);
	}

	@Test
	public void agentsChooseTest() {
		List<Boolean> results = getResults("examples/agentsChoose.asm");
		Iterator<Boolean> i = results.iterator();
		assertFalse(i.next());
		assertFalse(i.next());
		while (i.hasNext()) {
			assertTrue(i.next());
		}
	}

	@Test
	public void agentsChoose2Test() {
		List<Boolean> results = getResults("examples/agentsChoose2.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		assertFalse(i.next());
	}

	@Test
	public void agentsChoose3Test() {
		testAllCtlPropsAreTrue("examples/agentsChoose3.asm", 1);
	}

	@Test
	public void agentsChoose4Test() {
		List<Boolean> results = getResults("examples/agentsChoose4.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		assertFalse(i.next());
		assertTrue(i.next());
	}

	@Test
	public void agentsForallTest() {
		testAllCtlPropsAreTrue("examples/agentsForall.asm", 1);
	}

	@Test
	public void agentsForall2Test() {
		testAllCtlPropsAreTrue("examples/agentsForall2.asm", 1);
	}

	@Test
	public void agentsForall3Test() {
		testAllCtlPropsAreTrue("examples/agentsForall3.asm", 1);
	}

	@Test
	public void bitCounterTest() {
		testAllCtlPropsAreTrue("examples/bitCounter.asm", 1);
	}

	@Test
	public void bitCounterAgentsTest() {
		testAllCtlPropsAreTrue("examples/bitCounterAgents.asm", 1);
	}

	@Test
	public void chooseTest() {
		testAllCtlPropsAreTrue("examples/choose.asm", 1);
	}

	@Test
	public void caseTermTest() {
		testAllCtlPropsAreTrue("examples/caseTerm.asm", 1);
	}

	@Test
	public void conditionalTermTest() {
		testAllCtlPropsAreTrue("examples/conditionalTerm.asm", 1);
	}

	@Test
	public void choose2Test() {
		testAllCtlPropsAreTrue("examples/choose2.asm", 1);
	}

	@Test
	public void choose3Test() {
		testAllCtlPropsAreTrue("examples/choose3.asm", 1);
	}

	@Test
	public void choose4Test() {
		testAllCtlPropsAreTrue("examples/choose4.asm", 1);
	}

	@Test
	public void chooseChooseTest() {
		// per controllare che funzioni sia con la semplificazione, sia senza.
		AsmetaSMV as = null;
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		options.simplify = true;
		try {
			as = new AsmetaSMV(new File("examples/chooseChoose.asm"), options);
			as.translation();
			as.createNuSMVfile();
			AsmetaSMVOptions.setPrintNuSMVoutput(false);
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (boolean result : as.mv.nuSmvPropsResults) {
			assertTrue(result);
		}

		for (boolean result : getResults("examples/chooseChoose.asm")) {
			assertTrue(result);
		}
	}

	// @Test
	// public void chooseForChooseTest() {
	// testAllCtlPropsAreTrue("examples/chooseForChoose.asm");
	// }

	@Test
	public void chooseMonTest() {
		testAllCtlPropsAreTrue("examples/chooseMon.asm", 1);
	}

	@Test
	public void choosePhdThesisTest() {
		testAllCtlPropsAreTrue("examples/choosePhdThesis.asm", 1);
	}

	@Test
	public void chooseRuleTest() {
		testAllCtlPropsAreTrue("examples/chooseRule.asm", 1);
	}

	@Test
	public void chooseRule2Test() {
		testAllCtlPropsAreTrue("examples/chooseRule2.asm", 1);
	}

	@Test
	public void chooseRule3Test() {
		testAllCtlPropsAreTrue("examples/chooseRule3.asm", 1);
	}

	@Test
	public void condUpdateTest() {
		// non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		// e l'esecuzione non diano problemi
		execNuSMV("examples/condUpdate.asm");
	}

	@Test
	public void derivedTest() {
		testAllCtlPropsAreTrue("examples/derived.asm", 1);
	}

	@Test
	public void derivedBehaviourTest() {
		testAllCtlPropsAreTrue("examples/derivedBehaviour.asm", 1);
	}

	@Test
	public void derivedCtlCheckTest() {
		testAllCtlPropsAreTrue("examples/derivedCtlCheck.asm", 1);
	}

	@Test
	public void derivedLocArgsTest() {
		testAllCtlPropsAreTrue("examples/derivedLocArgs.asm", 1);
	}

	@Test
	public void derivedLetTest() {
		testAllCtlPropsAreTrue("examples/derivedLet.asm", 1);
	}

	@Test
	public void derivedExhaustiveTest() {
		testAllCtlPropsAreTrue("examples/derivedExhaustive.asm", 1);
	}

	@Test
	public void derivedNotExhaustiveTest() {
		// non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		// e l'esecuzione non diano problemi
		execNuSMV("examples/derivedNotExhaustive.asm");
	}

	@Test
	public void derivedRecursivelyDefTest() {
		testAllCtlPropsAreTrue("examples/derivedRecursivelyDef.asm", 1);
	}

	/*
	 * @Test public void esSemplTest() { //non ci sono proprieta' CTL. Verifica
	 * semplicemente che la traduzione //e l'esecuzione non diano problemi
	 * execNuSMV("examples/esSempl.asm"); }
	 * 
	 * @Test public void esSemplNoTest() { //non ci sono proprieta' CTL.
	 * Verifica semplicemente che la traduzione //e l'esecuzione non diano
	 * problemi execNuSMV("examples/esSempl.asm", "examples/esSemplNo", false);
	 * }
	 */

	@Test
	public void existTermTest() {
		testAllCtlPropsAreTrue("examples/existTerm.asm", 1);
	}

	@Test
	public void existTerm2Test() {
		testAllCtlPropsAreTrue("examples/existTerm2.asm", 1);
	}

	@Test
	public void existUniqueTermTest() {
		testAllCtlPropsAreTrue("examples/existUniqueTerm.asm", 1);
	}

	@Test
	public void ferrymanTest() {
		testAllCtlPropsAreTrue("examples/ferryman.asm", 1);
	}

	@Test
	public void ferryman_v2Test() {
		testAllCtlPropsAreTrue("examples/ferryman_v2.asm", 1);
	}

	@Test
	public void ferrymanWrongTest() {
		testAllCtlPropsAreFalse("examples/ferrymanWrong.asm", 1);
	}

	@Test
	public void forallTest() {
		testAllCtlPropsAreTrue("examples/forall.asm", 1);
	}

	@Test
	public void forall2Test() {
		testAllCtlPropsAreTrue("examples/forall2.asm", 1);
	}

	@Test
	public void forall3Test() {
		testAllCtlPropsAreTrue("examples/forall3.asm", 1);
	}

	@Test
	public void forallRuleTest() {
		testAllCtlPropsAreTrue("examples/forallRule.asm", 1);
	}

	@Test
	public void forallTermTest() {
		testAllCtlPropsAreTrue("examples/forallTerm.asm", 1);
	}

	@Test
	public void forallTerm2Test() {
		testAllCtlPropsAreTrue("examples/forallTerm2.asm", 1);
	}

	@Test
	public void functionsTest() {
		testAllCtlPropsAreTrue("examples/functions.asm", 1);
	}

	@Test
	public void functionAsArgSupportedTest() {
		testAllCtlPropsAreTrue("examples/functionAsArgSupported.asm", 1);
	}

	@Test
	public void ifTest() {
		testAllCtlPropsAreTrue("examples/if.asm", 1);
	}

	@Test
	public void if2Test() {
		testAllCtlPropsAreTrue("examples/if2.asm", 1);
	}

	@Test
	public void iffTest() {
		testAllCtlPropsAreTrue("examples/iff.asm", 1);
	}

	@Test
	public void impliesTest() {
		testAllCtlPropsAreTrue("examples/implies.asm", 1);
	}

	@Test
	public void integerConcrDomTest() {
		testAllCtlPropsAreTrue("examples/integerConcrDom.asm", 1);
	}

	@Test
	public void justiceConstraint() {
		testAllCtlPropsAreTrue("examples/justiceConstraint.asm", 1);
		testAllCtlPropsAreFalse("examples/noJusticeConstraint.asm", 1);
	}

	@Test
	public void letRuleTest() {
		testAllCtlPropsAreTrue("examples/letRule.asm", 1);
	}

	@Test
	public void mapTest() {
		testAllCtlPropsAreTrue("examples/map.asm", 1);
	}

	@Test
	public void maxTest() {
		testAllCtlPropsAreTrue("examples/max.asm", 1);
	}

	@Test
	public void minTest() {
		testAllCtlPropsAreTrue("examples/min.asm", 1);
	}

	@Test
	public void minusUnaryTest() {
		testAllCtlPropsAreTrue("examples/minusUnary.asm", 1);
	}

	@Test
	public void modTest() {
		testAllCtlPropsAreTrue("examples/mod.asm", 1);
	}

	@Test
	public void monitoredTest() {
		testAllCtlPropsAreTrue("examples/monitored.asm", 1);
	}

	@Test
	public void monitoredExampleTest() {
		testAllCtlPropsAreTrue("examples/monitoredExample.asm", 1);
	}

	@Test
	public void myCtlTest() {
		testAllCtlPropsAreTrue("examples/myCTL.asm", 1);
	}

	@Test
	public void notConsistentTest() {
		// non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		// e l'esecuzione non diano problemi
		execNuSMV("examples/notConsistent.asm");
	}

	@Test
	public void oneWayTrafficLight_refined_with_agentsTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight_refined_with_agents.asm", 1);
	}

	@Test
	public void oneWayTrafficLight_refinedTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight_refined.asm", 1);
	}

	@Test
	public void oneWayTrafficLightTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight.asm", 1);
	}

	@Test
	public void owtlTest() {
		testAllCtlPropsAreTrue("examples/owtl.asm", 1);
	}

	@Test
	public void philosophersPhdThesisTest() {
		List<Boolean> results = getResults("examples/philosophersPhdThesis.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		assertFalse(i.next());
		assertTrue(i.next());
		assertTrue(i.next());
	}

	@Test
	public void rangeFunctionTest() {
		testAllCtlPropsAreTrue("examples/rangeFunction.asm", 1);
	}

	@Test
	public void seqTest() {
		testAllCtlPropsAreTrue("examples/seq.asm", 1);
	}

	@Test
	public void seq1Test() {
		testAllCtlPropsAreTrue("examples/seq1.asm", 1);
	}

	@Test
	public void seq2Test() {
		testAllCtlPropsAreTrue("examples/seq2.asm", 1);
	}

	@Test
	public void seq3Test() {
		testAllCtlPropsAreTrue("examples/seq3.asm", 1);
	}

	@Test
	public void seq4Test() {
		testAllCtlPropsAreTrue("examples/seq4.asm", 1);
	}

	// L'eccezione di partenza e' una AsmNotSupportedException (perche' non si
	// puo' mettere una choose rule in una seq rule). Questa eccezione viene
	// catturata nel ReflectiveVisitor e rilanciata come RuntimeException.

	@Test(expected = RuntimeException.class)
	public void seq5Test() throws Exception {
		try {
			AsmetaSMV as = new AsmetaSMV("examples/seq5.asm");
			as.translation();
		} catch (Exception e) {
			assertEquals(e.getCause().getMessage(), "Chooserule in seqrule non supportato.");
			throw e;
		}
	}

	@Test
	public void seqRuleTest() {
		testAllCtlPropsAreTrue("examples/seqRule.asm", 1);
	}

	@Test
	public void seqRule1Test() {
		testAllCtlPropsAreTrue("examples/seqRule1.asm", 1);
	}

	@Test
	public void seqRule2Test() {
		testAllCtlPropsAreTrue("examples/seqRule2.asm", 1);
	}

	@Test
	public void seqRule3Test() {
		testAllCtlPropsAreTrue("examples/seqRule3.asm", 1);
	}

	@Test
	public void seqRule4Test() {
		testAllCtlPropsAreTrue("examples/seqRule4.asm", 1);
	}

	// il test verifica che il tool si accorge dell'update inconsistente

	@Test
	public void seqRule5Test() {
		AsmetaSMV as = null;
		try {
			as = new AsmetaSMV("examples/seqRule5.asm");
			as.translation();
		} catch (RuntimeException e) {
			assertEquals(e.getCause().getClass().getSimpleName(), "AsmNotSupportedException");
			assertEquals(e.getCause().getMessage(),
					"Update inconsistente: la locazione fooB viene aggiornata sia al valore 1 sia al valore 2 sotto la condizione TRUE");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		fail();// se e' arrivato qui vuol dire che l'eccezione non e' stata
				// catturata
	}

	@Test
	public void sluiceGateGroundTest() {
		testAllCtlPropsAreTrue("examples/sluiceGateGround.asm", 1);
	}

	@Test
	public void sluiceGateMotorCtlTest() {
		testAllCtlPropsAreTrue("examples/sluiceGateMotorCtl.asm", 1);
	}

	@Test
	public void staticDerivedTest() {
		// non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		// e l'esecuzione non diano problemi
		execNuSMV("examples/staticDerived.asm");
	}

	@Test
	public void switchTest() {
		testAllCtlPropsAreTrue("examples/switch.asm", 1);
	}

	@Test
	public void taxi_singTest() {
		testAllCtlPropsAreTrue("examples/taxi_sing.asm", 1);
	}

	@Test
	public void taxiTest() {
		List<Boolean> results = getResults("examples/taxi.asm");
		int numProp = results.size();
		for (int i = 0; i < numProp - 2; i++) {
			assertTrue(results.get(i));
		}
		assertFalse(results.get(numProp - 2));
		assertTrue(results.get(numProp - 1));
	}

	@Test
	public void taxiSingAgentsTest() {
		testAllCtlPropsAreTrue("examples/taxiSingAgents.asm", 1);
	}

	@Test
	public void Tictactoe_forSMVTest() {
		testAllCtlPropsAreTrue("examples/Tictactoe_forSMV.asm", 1);
	}

	@Test
	public void Tictactoe_v2Test() {
		testAllCtlPropsAreTrue("examples/Tictactoe_v2.asm", 1);
	}

	@Test
	public void undefValueTest() {
		testAllCtlPropsAreTrue("examples/undefValue.asm", 1);
	}

	@Test
	public void untilEwAwTest() {
		testAllCtlPropsAreTrue("examples/untilEwAw.asm", 1);
	}

	@Test
	public void updateTest() {
		testAllCtlPropsAreTrue("examples/update.asm", 1);
	}

	@Test
	public void armadioTest() {
		// questa dovrebbe funzionare col fattener
		testAllCtlPropsAreFalse("examples/tvsw_angelo/ArmadioCaramelle2.asm", 1);
	}

	@Test
	public void armadioTest_withSolution() {
		// soluzione temporanea. il flattener non funziona nelle proprieta'
		testAllCtlPropsAreFalse("examples/tvsw_angelo/ArmadioCaramelle2_withSolution.asm", 1);
	}

	
	/*
	 * @Test public void updateRuleTest() { //non ci sono proprieta' CTL.
	 * Verifica semplicemente che la traduzione //e l'esecuzione non diano
	 * problemi execNuSMV("examples/updateRule.asm"); }
	 */
}