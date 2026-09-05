package tgtlib.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.visitors.ExpressionEvaluator;
import tgtlib.definitions.expression.visitors.IDExprCollector;
import tgtlib.definitions.expression.visitors.RandomLogicExpressionBuilder;
import tgtlib.generator.IncrementalModelGenerator.AssertPlusResult;

/**
 * some generic tests for an incremental solver (like yices and Z3)
 * 
 * @author garganti
 * 
 * @param <T>
 */
public abstract class IncrementalModelGeneratorTest<T extends MCExecutionResult> {

	static EnumConstCreator ecc = new EnumConstCreator();

	// init the variables to be considered
	protected static List<BooleanVar> vars;

	@BeforeAll
	static public void setupVars() throws ParseException {
		vars = IDExprCollector.getBoolVarsFromId(ExpressionParser.parseAsBooleanExpression("a and b and c", ecc));
	}

	protected IncrementalModelGenerator<TestPredicate4Test, TestSequence4Test, T> solver;

	@Test
	public void andAlso1() throws Exception {
		// a && (b || c)
		TestPredicate4Test tp0 = fromString("a && (b || c)");
		T res0 = solver.runModelChecker(tp0.getCondition());
		MCAnalysisResult ans = solver.analyses(res0);
		assertTrue(ans.isTestFound());
		TestSequence4Test ts = solver.buildTestFor(tp0,
				res0);
		assertTrue(isAModel(ts, tp0));
		//
		// add now not c
		//
		TestPredicate4Test tp1 = fromString("not c");
		tgtlib.generator.IncrementalModelGenerator.AssertPlusResult res2 = solver
				.andAlso(res0, tp1.getCondition(), tp1.getName());
		assertEquals(
				tgtlib.generator.IncrementalModelGenerator.AssertPlusResult.ADDED,
				res2);
		ts = solver.buildTestFor(tp0, res0);
		assertTrue(isAModel(ts, tp0, tp1));
		// add now a negation
		// like not B:
		TestPredicate4Test tp3 = fromString("not b");
		tgtlib.generator.IncrementalModelGenerator.AssertPlusResult res3 = solver
				.andAlso(res0, tp3.getCondition(), tp3.getName());
		assertEquals(
				tgtlib.generator.IncrementalModelGenerator.AssertPlusResult.REFUSED,
				res3);
		// I still get the same model
		// checkmodel1(res0);
		// now add a satisfiable
		TestPredicate4Test tp4 = fromString("a || b");
		AssertPlusResult res4 = solver.andAlso(res0, tp4.getCondition(),
				tp4.getName());
		assertEquals(AssertPlusResult.ADDED, res4);
		// I still get the same model
		checkmodel1(res0);
		assertTrue(isAModel(ts, tp0, tp1, tp4));
	}

	// this is far more complex
	// add a test condition with a different model
	@Test
	public void andAlso2() throws Exception {
		// a && (b || c)
		TestPredicate4Test tp0 = fromString("a && (b || c)");
		T res0 = solver.runModelChecker(tp0.getCondition());
		MCAnalysisResult ans = solver.analyses(res0);
		assertTrue(ans.isTestFound());
		TestSequence4Test ts = solver.buildTestFor(tp0,
				res0);
		assertTrue(isAModel(ts, tp0));
		//
		// add now a test predicate which is feasible but does not share the
		// same model
		//
		List<IdExpression> IDS = IDExprCollector.getIdsAsList(tp0
				.getCondition());
		assertNotNull(IDS.get(0));
		assertNotNull(IDS.get(1));
		assertNotNull(IDS.get(2));
		assertEquals(3, IDS.size());
		RandomLogicExpressionBuilder rndb = new RandomLogicExpressionBuilder(
				IDS, 3, false);
		ExpressionEvaluator ev = new ExpressionEvaluator(ts.getState(0));
		Expression e = null;
		for (;;) {
			// build new expression randomly
			e = rndb.next();
			// check if equals
			if (e.equals(tp0.getCondition()))
				continue;
			// share the same model? no good
			if (e.accept(ev))
				continue;
			// is feasible
			AssertPlusResult res4 = solver.andAlso(res0, e, "test");
			if (res4 == AssertPlusResult.REFUSED)
				continue;
			// feasible found
			break;
		}
		// found feasible, different model
		System.out.println(e.toString());
		assertFalse(e.accept(ev));
		// get the model
		TestPredicate4Test tc = new TestPredicate4Test("test1", e);
		TestSequence4Test test2 = solver.buildTestFor(tc,
				res0);
		//
		// this must be a model for both:
		//
		assertTrue(isAModel(test2, tc, tp0));

	}

	private boolean isAModel(TestSequence4Test ts, TestPredicate... tps) {
		ExpressionEvaluator ev = new ExpressionEvaluator(ts.getState(0));
		for (TestPredicate tp : tps) {
			if (!tp.getCondition().accept(ev))
				return false;
		}
		return true;
	}

	// a and b and not c
	private void checkmodel1(T res0) {
		MCAnalysisResult ans;
		// if I get the model
		ans = solver.analyses(res0);
		assertTrue(ans.isTestFound());
		//
		TestSequence4Test ts = new TestSequence4Test(null);
		solver.buildTest(res0, ts);
		assertEquals("true", ts.get("a"));
		assertEquals("true", ts.get("b"));
		assertEquals("false", ts.get("c"));
	}

	TestPredicate4Test fromString(String expr) throws ParseException {
		Expression e = ExpressionParser.parseAsBooleanExpression(expr, ecc);
		TestPredicate4Test tc = new TestPredicate4Test("test1", e);
		return tc;
	}
}