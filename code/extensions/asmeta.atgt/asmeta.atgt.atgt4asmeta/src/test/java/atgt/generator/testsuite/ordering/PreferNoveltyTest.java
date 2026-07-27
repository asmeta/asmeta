package atgt.generator.testsuite.ordering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import atgt.combinatorial.CombinatorialTestCondition;
import atgt.combinatorial.PairEqTestCondition;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestCondition;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.util.IterableEnumeration;

public class PreferNoveltyTest {

	@Test
	public void testMaxUsage() {
		test(new MAXComparator(), 1, 1, "tc1");
	}

	@Test
	public void testToch() {
		test(new TOUCHComparator(), 2, 1, "tc2");
	}

	private void test(NWiseTcComparator coparator, int i, int j, String tc_next) {
		// get the CC propject
		ASMSpecification ccAx = AsmMLoaderTest
				.loadSpec(atgt.parser.ParseSpecsAsmm.CC_ASM);
		AsmTestSuite ts = new AsmTestSuite();
		Variable fast = findVar(ccAx, "fast");
		Variable brake = null, igOn = null;
		Variable engRun = null;
		brake = findVar(ccAx,"brake");
		igOn = findVar(ccAx,"igOn");
		engRun = findVar(ccAx,"engRun");
		assertNotNull(fast);
		assertNotNull(brake);
		assertNotNull(igOn);
		// add two test predicates
		// fast = true and brake = false
		PairEqTestCondition tc1 = (PairEqTestCondition) PairEqTestCondition.factory
				.buildTestPredicate("tc1", fast, BoolType.TRUE_CONST, brake,
						BoolType.FALSE_CONST);
		System.out.println(tc1.toString());
		// secondo predicato
		// "igOn == false && brake == false";
		PairEqTestCondition tc2 = (PairEqTestCondition) PairEqTestCondition.factory
				.buildTestPredicate("tc2", engRun, BoolType.FALSE_CONST, brake,
						BoolType.FALSE_CONST);
		// add to the comparator
		List<CombinatorialTestCondition> l = new ArrayList<CombinatorialTestCondition>();
		l.add(tc1);
		l.add(tc2);
		// Novelty Comp
		PreferNovelty nc = new PreferNovelty(ts, ccAx,l);
		// set the right comparatore
		coparator.setSpecification(ccAx);
		nc.nc = coparator;
		// add a test case
		// fast = true and brake = false
		AsmTestCondition testc1 = new AsmTestCondition("test1", null);
		AsmTestSequence test1 = new AsmTestSequence(testc1);
		test1.addState();
		test1.addAssignment(fast, BoolType.TRUE_CONST.getIdString());
		test1.addAssignment(brake, BoolType.FALSE_CONST.getIdString());
		ts.addTest(test1);
		// to be continued
		// check novetly of two test
		// check max usage
		// entrambi hanno max usage usguale
		assertEquals(i, nc.nc.evaluate(tc1));
		assertEquals(j, nc.nc.evaluate(tc2));
		// sceglierebbe tc
		TestCondition min = (TestCondition) nc.next();
		System.out.println(min);
		// assertSame(tc2,min);
		assertEquals(tc_next, min.getName());

	}

	public static Variable findVar(ASMSpecification ccAx, String varname) {
		for (Variable v : new IterableEnumeration<Variable>(ccAx.allVariables())) {
			if (v.getName().equals(varname))
				return v;
		}
		return null;

	}

}