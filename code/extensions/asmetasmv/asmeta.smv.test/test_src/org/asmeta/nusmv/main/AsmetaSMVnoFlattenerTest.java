package org.asmeta.nusmv.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.asmeta.simulator.util.TermChecker;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import asmeta.definitions.Invariant;
import asmeta.definitions.Property;
import asmeta.definitions.TemporalProperty;

public class AsmetaSMVnoFlattenerTest extends AsmetaSMVtest {

	private static boolean old;

	@BeforeClass
	public static void setFlattener() {
		old = AsmetaSMVOptions.FLATTEN;
		AsmetaSMVOptions.FLATTEN = false;
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.CTL;
	}
	
	@AfterClass
	public static void setFlattenerOld() {
		AsmetaSMVOptions.FLATTEN = old;
	}


	@Test
	public void dirFilesTest() {
	}

	@Test
	public void dirFilesConcrDomTest() {
		testAllCtlPropsAreTrue("examples/filesConcrDom/agentsMain.asm");
	}

	@Test
	public void dirOneWayTrafficLightAgentsTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLightAgents/owtl_main.asm");
	}

	@Test
	public void dirSultanSuccessorsSimulatorTest() {
		List<Boolean> results = getResults("examples/sultanSuccessors/sultan.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		while(i.hasNext()) {
			assertFalse(i.next());
		}
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoardNoSolution() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardNoSolution.asm");
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoradSolutionGreaterK() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardSolutionGreaterK.asm");
	}

	@Test
	public void dirSultanSuccessorsGreedyTestBoradSolutionLessK() {
		testAllCtlPropsAreTrue("examples/sultanSuccessors/sultanGreedyBoardSolutionLessK.asm");
	}

	@Test
	public void dirTaxiTest() {
		testAllCtlPropsAreTrue("examples/taxi/main.asm");
	}

	@Test
	public void dirTaxiSingTest() {
		testAllPropsAre(true,"examples/taxiSing/main.asm", (x->true));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void dirTicTacToeTest() {
		testAllCtlPropsAreTrue("examples/ticTacToe/ticTacToe_simulator.asm");
	}

	@Test
	public void absTest() {
		testAllCtlPropsAreTrue("examples/abs.asm");
	}

	@Test
	public void abstractTest() {
		testAllCtlPropsAreTrue("examples/abstract.asm");
	}

	@Test
	public void agTest() {
		testAllCtlPropsAreTrue("examples/ag.asm");
	}

	@Test
	public void agentsTest() {
		testAllCtlPropsAreTrue("examples/agents.asm");
	}

	@Test
	public void agents1Test() {
		testAllCtlPropsAreTrue("examples/agents1.asm");
	}

	@Test
	public void agents3Test() {
		testAllCtlPropsAreTrue("examples/agents3.asm");
	}

	@Test
	public void agentsAsyncrTest() {
		testAllCtlPropsAreTrue("examples/agentsAsyncr.asm");
	}

	@Test
	public void agentsAsyncr2Test() {
		testAllCtlPropsAreTrue("examples/agentsAsyncr2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void agentsChooseTest() {
		List<Boolean> results = getResults("examples/agentsChoose.asm");
		Iterator<Boolean> i = results.iterator();
		assertFalse(i.next());
		assertFalse(i.next());
		while(i.hasNext()) {
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
		testAllCtlPropsAreTrue("examples/agentsChoose3.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void agentsChoose4Test() {
		List<Boolean> results = getResults("examples/agentsChoose4.asm");
		Iterator<Boolean> i = results.iterator();
		assertTrue(i.next());
		assertFalse(i.next());
		assertTrue(i.next());
	}

	@Test
	public void agentsForallTest() {
		testAllCtlPropsAreTrue("examples/agentsForall.asm");
	}

	@Test
	public void agentsForall2Test() {
		testAllCtlPropsAreTrue("examples/agentsForall2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void agentsForall3Test() {
		testAllCtlPropsAreTrue("examples/agentsForall3.asm");
	}

	@Test
	public void bitCounterTest() {
		testAllCtlPropsAreTrue("examples/bitCounter.asm");
	}

	@Test
	public void bitCounterAgentsTest() {
		testAllCtlPropsAreTrue("examples/bitCounterAgents.asm");
	}

	@Test
	public void chooseTest() {
		testAllCtlPropsAreTrue("examples/choose.asm");
	}

	@Test
	public void caseTermTest() {
		testAllCtlPropsAreTrue("examples/caseTerm.asm");
	}

	@Test
	public void conditionalTermTest() {
		testAllCtlPropsAreTrue("examples/conditionalTerm.asm");
	}
	
	@Test
	public void choose2Test() {
		testAllCtlPropsAreTrue("examples/choose2.asm");
	}

	@Test
	public void choose3Test() {
		testAllCtlPropsAreTrue("examples/choose3.asm");
	}

	@Test
	public void choose4Test() {
		testAllCtlPropsAreTrue("examples/choose4.asm");
	}

	@Test
	public void chooseChooseTest(){
		//per controllare che funzioni sia con la semplificazione, sia senza.
		AsmetaSMV as = null;
		try {
			AsmetaSMVOptions opt = new AsmetaSMVOptions();
			opt.setPrintNuSMVoutput(false);
			opt.simplify = true;
			as = new AsmetaSMV(new File("examples/chooseChoose.asm"), opt);
			as.translation();
			as.createNuSMVfile();
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(boolean result: as.mv.getNuSmvPropsResults()) {
			assertTrue(result);
		}

		for(boolean result: getResults("examples/chooseChoose.asm")) {
			assertTrue(result);
		}
	}

	//@Test
	//public void chooseForChooseTest() {
	//	testAllCtlPropsAreTrue("examples/chooseForChoose.asm");
	//}

	@Test
	public void chooseMonTest() {
		testAllCtlPropsAreTrue("examples/chooseMon.asm");
	}

	@Test
	public void choosePhdThesisTest() {
		testAllCtlPropsAreTrue("examples/choosePhdThesis.asm");
	}

	@Test
	public void chooseRuleTest() {
		testAllCtlPropsAreTrue("examples/chooseRule.asm");
	}

	@Test
	public void chooseRule2Test() {
		testAllCtlPropsAreTrue("examples/chooseRule2.asm");
	}

	@Test
	public void chooseRule3Test() {
		testAllCtlPropsAreTrue("examples/chooseRule3.asm");
	}

	@Test
	public void condUpdateTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/condUpdate.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void derivedTest() {
		testAllCtlPropsAreTrue("examples/derived.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void derivedBehaviourTest() {
		testAllCtlPropsAreTrue("examples/derivedBehaviour.asm");
	}

	@Test
	public void derivedCtlCheckTest() {
		testAllCtlPropsAreTrue("examples/derivedCtlCheck.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void derivedLetTest() {
		testAllCtlPropsAreTrue("examples/derivedLet.asm");
	}

	@Test
	public void derivedExhaustiveTest() {
		testAllCtlPropsAreTrue("examples/derivedExhaustive.asm");
	}

	@Test
	public void derivedNotExhaustiveTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/derivedNotExhaustive.asm");
	}

	@Test
	public void derivedRecursivelyDefTest() {
		testAllCtlPropsAreTrue("examples/derivedRecursivelyDef.asm");
	}

	/*@Test
	public void esSemplTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/esSempl.asm");
	}

	@Test
	public void esSemplNoTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/esSempl.asm", "examples/esSemplNo", false);
	}*/

	@Test
	public void existTermTest() {
		testAllCtlPropsAreTrue("examples/existTerm.asm");
	}

	@Test
	public void existUniqueTermTest() {
		testAllCtlPropsAreTrue("examples/existUniqueTerm.asm");
	}

	@Test
	public void ferrymanTest() {
		testAllCtlPropsAreTrue("examples/ferryman.asm");
	}

	@Test
	public void ferryman_v2Test() {
		testAllCtlPropsAreTrue("examples/ferryman_v2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void ferrymanWrongTest() {
		testAllCtlPropsAreFalse("examples/ferrymanWrong.asm");
	}

	@Test
	public void forallTest() {
		testAllCtlPropsAreTrue("examples/forall.asm");
	}

	@Test
	public void forall2Test() {
		testAllCtlPropsAreTrue("examples/forall2.asm");
	}

	@Test
	public void forall3Test() {
		testAllCtlPropsAreTrue("examples/forall3.asm");
	}

	@Test
	public void forallRuleTest() {
		testAllCtlPropsAreTrue("examples/forallRule.asm");
	}

	@Test
	public void forallTermTest() {
		//PRINT_NU_SM_VOUTPUT = true;
		testAllPropsAre(true,"examples/forallTerm.asm",(x->true));
		//PRINT_NU_SM_VOUTPUT = false;

	}

	@Test
	public void functionsTest() {
		testAllCtlPropsAreTrue("examples/functions.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void functionAsArgSupportedTest() {
		testAllCtlPropsAreTrue("examples/functionAsArgSupported.asm");
	}

	@Test
	public void ifTest() {
		testAllCtlPropsAreTrue("examples/if.asm");
	}

	@Test
	public void if2Test() {
		testAllCtlPropsAreTrue("examples/if2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void iffTest() {
		testAllCtlPropsAreTrue("examples/iff.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void impliesTest() {
		testAllCtlPropsAreTrue("examples/implies.asm");
	}

	@Test
	public void integerConcrDomTest() {
		testAllCtlPropsAreTrue("examples/integerConcrDom.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void justiceConstraint() {
		testAllCtlPropsAreTrue("examples/justiceConstraint.asm");
		testAllCtlPropsAreFalse("examples/noJusticeConstraint.asm");
	}

	@Test
	public void letRuleTest() {
		testAllCtlPropsAreTrue("examples/letRule.asm");
	}

	@Test
	public void mapTest() {
		testAllCtlPropsAreTrue("examples/map.asm");
	}

	@Test
	public void maxTest() {
		testAllCtlPropsAreTrue("examples/max.asm");
	}

	@Test
	public void minTest() {
		testAllCtlPropsAreTrue("examples/min.asm");
	}

	@Test
	public void minusUnaryTest() {
		testAllCtlPropsAreTrue("examples/minusUnary.asm");
	}

	@Test
	public void modTest() {
		testAllCtlPropsAreTrue("examples/mod.asm");
	}

	@Test
	public void monitoredTest() {
		testAllCtlPropsAreTrue("examples/monitored.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void monitoredExampleTest() {
		testAllCtlPropsAreTrue("examples/monitoredExample.asm");
	}

	@Test
	public void myCtlTest() {
		testAllCtlPropsAreTrue("examples/myCTL.asm");
	}

	@Test
	public void notConsistentTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/notConsistent.asm");
	}

	@Test
	public void oneWayTrafficLight_refined_with_agentsTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight_refined_with_agents.asm");
	}

	@Test
	public void oneWayTrafficLight_refinedTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight_refined.asm");
	}

	@Test
	public void oneWayTrafficLightTest() {
		testAllCtlPropsAreTrue("examples/oneWayTrafficLight.asm");
	}

	@Test
	public void owtlTest() {
		testAllCtlPropsAreTrue("examples/owtl.asm");
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void rangeFunctionTest() {
		testAllCtlPropsAreTrue("examples/rangeFunction.asm");
	}

	@Test
	public void seqTest() {
		testAllCtlPropsAreTrue("examples/seq.asm");
	}

	@Test
	public void seq1Test() {
		testAllCtlPropsAreTrue("examples/seq1.asm");
	}

	@Test
	public void seq2Test() {
		testAllCtlPropsAreTrue("examples/seq2.asm");
	}

	@Test
	public void seq3Test() {
		testAllCtlPropsAreTrue("examples/seq3.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void seq4Test() {
		testAllCtlPropsAreTrue("examples/seq4.asm");
	}

	//L'eccezione di partenza e' una AsmNotSupportedException (perche' non si
	//puo' mettere una choose rule in una seq rule). Questa eccezione viene 
	//catturata nel ReflectiveVisitor e rilanciata come RuntimeException.
	@Test(expected=RuntimeException.class)
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
		testAllCtlPropsAreTrue("examples/seqRule.asm");
	}

	@Test
	public void seqRule1Test() {
		testAllCtlPropsAreTrue("examples/seqRule1.asm");
	}

	@Test
	public void seqRule2Test() {
		testAllCtlPropsAreTrue("examples/seqRule2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void seqRule3Test() {
		testAllCtlPropsAreTrue("examples/seqRule3.asm");
	}

	@Test
	public void seqRule4Test() {
		testAllCtlPropsAreTrue("examples/seqRule4.asm");
	}

	//il test verifica che il tool si accorge dell'update inconsistente
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void seqRule5Test() {
		AsmetaSMV as = null;
		try {
			as = new AsmetaSMV("examples/seqRule5.asm");
			as.translation();
		}
		catch (RuntimeException e) {
			assertEquals(e.getCause().getClass().getSimpleName(), "AsmNotSupportedException");
			assertEquals(e.getCause().getMessage(), "Update inconsistente: la locazione fooB viene aggiornata sia al valore 1 sia al valore 2 sotto la condizione TRUE");
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		fail();//se e' arrivato qui vuol dire che l'eccezione non e' stata catturata
	}

	@Test
	public void sluiceGateGroundTest() {
		testAllCtlPropsAreTrue("examples/sluiceGateGround.asm");
	}

	@Test
	public void sluiceGateMotorCtlTest() {
		testAllCtlPropsAreTrue("examples/sluiceGateMotorCtl.asm");
	}

	@Test
	public void staticDerivedTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/staticDerived.asm");
	}

	@Test
	public void switchTest() {
		testAllCtlPropsAreTrue("examples/switch.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void taxi_singTest() {
		testAllCtlPropsAreTrue("examples/taxi_sing.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void taxiTest() {
		List<Boolean> results = getResults("examples/taxi.asm");
		int numProp = results.size();
		for(int i=0; i < numProp-2; i++) {
			assertTrue(results.get(i));
		}
		assertFalse(results.get(numProp-2));
		assertTrue(results.get(numProp-1));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void taxiSingAgentsTest() {
		testAllCtlPropsAreTrue("examples/taxiSingAgents.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void Tictactoe_forSMVTest() {
		testAllCtlPropsAreTrue("examples/Tictactoe_forSMV.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void Tictactoe_v2Test() {
		testAllCtlPropsAreTrue("examples/Tictactoe_v2.asm");
	}

	@Test
	public void undefValueTest() {
		testAllCtlPropsAreTrue("examples/undefValue.asm");
	}

	@Test
	public void undefValueTest2() {
		testAllCtlPropsAreTrue("examples/UseUndef.asm");
	}

	@Test
	public void untilEwAwTest() {
		testAllCtlPropsAreTrue("examples/untilEwAw.asm");
	}

	@Test
	public void updateTest() {
		testAllCtlPropsAreTrue("examples/update.asm");
	}

	@Test
	public void useINVAR() {
		PRINT_NU_SM_VOUTPUT = true;
		Predicate<Property> notINVAR  = new Predicate<Property>() {
			// exclude those that contains only input
			@Override
			public boolean test(Property t) {
				if (t instanceof Invariant) {
					TermChecker mf = TermChecker.monitoredFinder;
					return ! mf.visit(((Invariant)t).getBody());
				}
				return true;
			}			
		};
		testAllPropsAre(true, "examples/UseInvar.asm", notINVAR);
		PRINT_NU_SM_VOUTPUT = false;
	}

	@Test
	public void useINVAR2() {
		PRINT_NU_SM_VOUTPUT = true;
		Predicate<Property> notINVAR  = new Predicate<Property>() {
			// exclude those that have no name
			@Override
			public boolean test(Property t) {
				if (t instanceof Invariant) {
					return ((Invariant)t).getName().isBlank() ? false: true;
				}
				return false;
			}			
		};
		testAllPropsAre(true, "examples/UseInvar2.asm", notINVAR);
		PRINT_NU_SM_VOUTPUT = false;
	}

	
	@Test
	public void updateVC() {
		testAllPropsAre(true,"examples/coffeeVendingMachineNC.asm",(x->true));
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void trainTest() {
		testAllCtlPropsAreTrue("telecamere/trainTrack.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void pillBoxTest() {
		testAllCtlPropsAreTrue("F:\\Dati-Andrea\\GitHub\\quasmed\\PillboxASM\\pillbox_for_PropertyVerification.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void pillBoxTestAG() {
		testAllCtlPropsAreTrue("C:\\Users\\garganti\\Dropbox\\Documenti\\progetti\\quasmed_git\\PillboxASM\\pillbox_for_PropertyVerification.asm");
	}
	
	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void armadioTest() {
		testAllCtlPropsAreFalse("examples\\tvsw_angelo\\ArmadioCaramelle2.asm");
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void armadioTest2() {
		testAllCtlPropsAreFalse("examples\\tvsw_angelo\\ArmadioCaramelle3.asm");
	}
	
	
	
	/*@Test
	public void updateRuleTest() {
		//non ci sono proprieta' CTL. Verifica semplicemente che la traduzione
		//e l'esecuzione non diano problemi
		execNuSMV("examples/updateRule.asm");
	}*/
}
