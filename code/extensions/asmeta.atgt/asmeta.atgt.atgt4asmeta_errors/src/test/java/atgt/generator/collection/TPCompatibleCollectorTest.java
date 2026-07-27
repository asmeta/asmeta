package atgt.generator.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import atgt.combinatorial.CollectedNWiseTC;
import atgt.combinatorial.CollectedNWiseTC.CHECK_RESULT;
import atgt.combinatorial.PairEqTestCondition;
import atgt.combinatorial.TPNWiseCompatibleCollector;
import atgt.coverage.AsmTestSequence;
import atgt.generator.testsuite.ordering.PreferNoveltyTest;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmgofer.ParseException;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.preferences.TGLibPreferences;
import tgtlib.util.Pair;

public abstract class TPCompatibleCollectorTest {

	/**
	 * Test consistent with axioms.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 * @throws ModelCheckerExecutionException 
	 */
	protected void testConsistentWithAxioms() throws ParseException, ModelCheckerExecutionException, IOException {
		// leave the temp files
		TGLibPreferences.DELETE_TMP.setChecked(false);
		//
		atgt.preferences.ATGToolPreferences.SAL_PROGRAM.setValue("sal-smc");
		// take the cruise control, quello con gli assiomi ma non il next
		ASMSpecification ccAx = AsmMLoaderTest.loadSpec(atgt.parser.ParseSpecsAsmm.CC_ASM);
		assertNotNull(ccAx);
		assertNotNull(ccAx.getTypeFor("CCMode"));		
		AsmProject pro = new AsmProject(ccAx, null);
		Variable fast = PreferNoveltyTest.findVar(ccAx, "fast");
		Variable brake = null, igOn = null;
		Variable engRun = null;
		brake = PreferNoveltyTest.findVar(ccAx,"brake");
		igOn = PreferNoveltyTest.findVar(ccAx,"igOn");
		engRun = PreferNoveltyTest.findVar(ccAx,"engRun");
		assertNotNull(fast);
		assertNotNull(brake);
		assertNotNull(igOn);
		assert(fast.isMonitored());
		assert (fast.getType() instanceof BoolType) :  fast.getType();
		assertNotNull(brake);
		assertNotNull(igOn);
		// due test prediate che sarebbe compatibili e fesible se non ci fossero
		// glia ssiomi
		// fast = true and brake = false
		PairEqTestCondition tc1 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("tc1", fast, BoolType.TRUE_CONST, brake, BoolType.FALSE_CONST);
		TestSequenceGenerator gen = getTSeqgenerator(pro);
		Pair<MCAnalysisResult, AsmTestSequence> result = gen.executeAndAnalyze(tc1);
		assertTrue(result.getFirst().isTestFound());
		assertNotNull(result.getSecond());
		// secondo predicato
		// "igOn == false && brake == false";
		PairEqTestCondition tc2 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("tc2", igOn, BoolType.FALSE_CONST, brake, BoolType.FALSE_CONST);
		result = gen.executeAndAnalyze(tc2);
		assertTrue(result.getFirst().isTestFound());
		assertNotNull(result.getSecond());
		// sono compatibili tra loro formalmente
		assertEquals(CHECK_RESULT.NOT_IMPLIED, consistentByValues(tc1, tc2));
		// ma insieme sono incompatibili con gli assiomi
		// call the saltest generator
		CollectedNWiseTC tc1Set = new CollectedNWiseTC();
		tc1Set.addTestCondition(tc1);
		TPNWiseCompatibleCollector collector = new TPNWiseCompatibleCollector(Collections.list(ccAx.allVariables()),ccAx.getAxiom(), gen, null);
		boolean checkCons = collector.consistentWithAxioms(tc2, tc1Set);
		System.out.println(checkCons);
		assertFalse(checkCons);
		// ////////
		// ///////: (fast implies engRun)
		// ///////(engRun implies igOn)
		PairEqTestCondition inf = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("tc_in", fast, BoolType.TRUE_CONST, igOn, BoolType.FALSE_CONST);
		assertFalse(collector.consistent(inf, ccAx.getAxiom()));
	}



	abstract protected TestSequenceGenerator getTSeqgenerator(AsmProject pro);


	
	/**
	 * Test method for
	 * {@link atgt.combinatorial.PairEqTestCondition#consistentByValues(atgt.combinatorial.PairEqTestCondition, PairEqTestCondition, Object)}
	 * .
	 */
	@Test
	public void testConsistentNOaxioms() {
		EnumType type = new EnumType("THREE");
		type.addElement("a");
		type.addElement("b");
		type.addElement("c");
		EnumConst a = type.value(0);
		EnumConst b = type.value(1);
		EnumConst c = type.value(2);
		assertNotNull(a);
		assertNotNull(b);
		assertNotNull(c);
		Variable v1 = new Variable(IdExpressionCreator.createNewIdExpression("A"), type, null);
		Variable v2 = new Variable(IdExpressionCreator.createNewIdExpression("B"), type, null);
		Variable v3 = new Variable(IdExpressionCreator.createNewIdExpression("C"), type, null);
		Variable v4 = new Variable(IdExpressionCreator.createNewIdExpression("D"), type, null);
		PairEqTestCondition ptc1 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt1", v1, a, v2, b);
		assertEquals(CHECK_RESULT.IMPLIED, consistentByValues(ptc1, ptc1));
		PairEqTestCondition ptc2 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt2", v1, a, v2, c);
		assertEquals(CHECK_RESULT.INCONSISTENT, consistentByValues(ptc2, ptc1));
		PairEqTestCondition ptc3 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt3", v2, b, v1, a);
		assertEquals(CHECK_RESULT.IMPLIED, consistentByValues(ptc3, ptc1));
		//
		PairEqTestCondition ptc4 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt4", v3, b, v4, a);
		assertEquals(CHECK_RESULT.NOT_IMPLIED, consistentByValues(ptc4, ptc1));
		PairEqTestCondition ptc5 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt4", v1, a, v4, a);
		assertEquals(CHECK_RESULT.NOT_IMPLIED, consistentByValues(ptc5, ptc1));
		PairEqTestCondition ptc6 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt6", v4, a, v1, a);
		assertEquals(CHECK_RESULT.NOT_IMPLIED, consistentByValues(ptc6, ptc1));
		PairEqTestCondition ptc7 = (PairEqTestCondition) PairEqTestCondition.factory.buildTestPredicate("pt6", v4, a, v1, b);
		assertEquals(CHECK_RESULT.INCONSISTENT, consistentByValues(ptc7, ptc1));

	}

	/**
	 * returns true if this and other may have a test which satisfy both. ignore
	 * the axioms
	 * 
	 * @param other
	 *            the other
	 * @param newParam
	 *            TODO
	 * @param newParam2
	 *            TODO
	 * @return 
	 * @return true, if compatible (ignoring the axioms)
	 */

	static CHECK_RESULT consistentByValues(PairEqTestCondition other, PairEqTestCondition newParam) {
		CollectedNWiseTC ctc = new CollectedNWiseTC();
		ctc.addTestCondition(other);
		return ctc.checkConsistencyByValue(newParam);
	}

	
	
}
