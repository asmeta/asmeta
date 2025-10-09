package org.asmeta.xt.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.avallaxt.validator.TestValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCoverage extends TestValidator {

	private StringWriter stringWriter;
	private WriterAppender writerAppender;

	@Before
	public void setupLogger() {
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.INFO);
		// get the logger output
		stringWriter = new StringWriter();
		Layout layout = new PatternLayout();
		writerAppender = new WriterAppender(layout, stringWriter);
		Logger.getLogger(AsmetaV.class).addAppender(writerAppender);
	}

	@After
	public void cleanAppender() throws IOException {
		// remove the appender
		Logger.getLogger(AsmetaV.class).removeAppender(writerAppender);
	}

	@Test
	public void testWithCoverageAndWithoutEmptyAvalla() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/ferryman/emptyScenario.avalla", true);
	}

	@Test
	public void testWithCoverageAndWithoutAvallaWithNoStepAndNoCheck() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/ferryman/noStepNoCheckScenario.avalla", true);
	}

	@Test
	public void testWithCoverageAndWithoutAdvancedClock() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/advancedClock/advancedClock1.avalla", true,
				cov("r_Main()", branch(1, 0, 1), rule(4, 3), update(1, 1), forall(0, 0, 0, 0)));
	}

	@Test
	public void testWithCoverageAndWithoutNestedForall() throws Exception {
		List<CoverageOracle> oracles = new ArrayList<>();
		oracles.add(cov("r_Main()", branch(1, 1, 0), rule(2, 2), update(0, 0), forall(1, 0, 0, 1)));
		oracles.add(cov("r_inc(Rows)", branch(3, 2, 2), rule(7, 6), update(1, 1), forall(3, 2, 1, 1)));
		testWithCoverageAndWithout("scenariosforexamples/nestedForall/nested_forall_scenario.avalla", true,
				oracles.toArray(new CoverageOracle[0]));
	}

	@Test
	public void testWithCoverageAndWithoutNestedChooseAndLet() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/nestedChooseAndLet/no_pick.avalla", true,
				cov("r_Main()", branch(2, 2, 0), rule(4, 4), update(1, 1), forall(0, 0, 0, 0)));
		RuleEvalWCov.reset();
		stringWriter.getBuffer().setLength(0);
		testWithCoverageAndWithout("scenariosforexamples/nestedChooseAndLet/all_picked.avalla", true,
				cov("r_Main()", branch(2, 2, 0), rule(4, 4), update(1, 1), forall(0, 0, 0, 0)));
		RuleEvalWCov.reset();
		stringWriter.getBuffer().setLength(0);
		testWithCoverageAndWithout("scenariosforexamples/nestedChooseAndLet/pick_with_false_guard.avalla", false,
				cov("r_Main()", branch(2, 0, 0), rule(4, 0), update(1, 0), forall(0, 0, 0, 0))); // should fail
	}
	
	@Test
	public void testWithCoverageAndWithoutTinyScheduler() throws Exception {
		testWithCoverageAndWithout("scenariosfortest/flaky/tiny_scheduler/correct_scenarios/check_ifnone_no_pick.avalla", true,
				cov("r_Main()", branch(4, 3, 3), rule(8, 7), update(3, 2), forall(1, 1, 1, 1)));
		RuleEvalWCov.reset();
		stringWriter.getBuffer().setLength(0);
		testWithCoverageAndWithout("scenariosfortest/flaky/tiny_scheduler/correct_scenarios/check_ifnone_w_pick.avalla", true,
				cov("r_Main()", branch(4, 3, 3), rule(8, 7), update(3, 2), forall(1, 1, 1, 1)));
		RuleEvalWCov.reset();
		stringWriter.getBuffer().setLength(0);
		testWithCoverageAndWithout("scenariosfortest/flaky/tiny_scheduler/check_ifnone_w_pick_false_guard.avalla", false,
				cov("r_Main()", branch(4, 3, 2), rule(8, 6), update(3, 1), forall(1, 1, 1, 1)));
		RuleEvalWCov.reset();
		stringWriter.getBuffer().setLength(0);
		try {
			testWithCoverageAndWithout("scenariosfortest/flaky/tiny_scheduler/check_ifnone_w_pick_undef.avalla", false);
			fail("Expected RuntimeException to be thrown");
		} catch (RuntimeException e) {
			// expected
		}
		
	}
	
	@Test
	public void testWithCoverageAndWithoutPickAndNoPick() throws Exception {
		// Test two scenarios together, one picks the first choose rule and the other does not
		testWithCoverageAndWithout("scenariosfortest/flaky/tiny_scheduler/correct_scenarios", true,
				cov("r_Main()", branch(4, 3, 3), rule(8, 7), update(3, 2), forall(1, 1, 1, 1)));
	}

	@Test
	public void testWithCoverageAndWithoutPopulation() throws Exception {
		List<CoverageOracle> oracles = new ArrayList<>();
		// scenario 1: forall executed two times, the first with more than one
		// iteration, the second with zero iterations
		String scenario = "scenariosforexamples/population/zero_executions.avalla";
		oracles.add(cov("r_Main()", branch(1, 1, 1), rule(5, 5), update(3, 3), forall(1, 1, 0, 1)));
		oracles.add(cov("r_dead(Person)", branch(2, 2, 0), rule(3, 3), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_reproduce(Person)", branch(5, 2, 2), rule(12, 3), update(5, 0), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario, true, oracles.toArray(new CoverageOracle[0]));
		// scenario 2: forall executed two times, the first with more than one
		// iteration, the second with a single iteration
		scenario = "scenariosforexamples/population/exactly_one_execution.avalla";
		RuleEvalWCov.reset();
		oracles.clear();
		stringWriter.getBuffer().setLength(0);
		oracles.add(cov("r_Main()", branch(1, 1, 0), rule(5, 5), update(3, 3), forall(1, 0, 1, 1)));
		oracles.add(cov("r_dead(Person)", branch(2, 2, 1), rule(3, 3), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_reproduce(Person)", branch(5, 5, 1), rule(12, 12), update(5, 5), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario, true, oracles.toArray(new CoverageOracle[0]));
		// scenario 3: forall executed two times, both with more than one iteration
		scenario = "scenariosforexamples/population/multiple_executions.avalla";
		RuleEvalWCov.reset();
		oracles.clear();
		stringWriter.getBuffer().setLength(0);
		oracles.add(cov("r_Main()", branch(1, 1, 0), rule(5, 5), update(3, 3), forall(1, 0, 0, 1)));
		oracles.add(cov("r_dead(Person)", branch(2, 1, 1), rule(3, 2), update(1, 0), forall(0, 0, 0, 0)));
		oracles.add(cov("r_reproduce(Person)", branch(5, 5, 2), rule(12, 12), update(5, 5), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario, true, oracles.toArray(new CoverageOracle[0]));
	}

	@Test
	public void testWithCoverageAndWithoutSluiceGate() throws Exception {
		String scenario = "scenariosforexamples/sluiceGate";
		List<CoverageOracle> oracles = new ArrayList<>();
		oracles.add(cov("r_Main()", branch(8, 8, 8), rule(21, 21), update(4, 4), forall(0, 0, 0, 0)));
		oracles.add(cov("r_start_to_raise()", branch(0, 0, 0), rule(3, 3), update(2, 2), forall(0, 0, 0, 0)));
		oracles.add(cov("r_start_to_lower()", branch(0, 0, 0), rule(3, 3), update(2, 2), forall(0, 0, 0, 0)));
		oracles.add(cov("r_stop_motor()", branch(0, 0, 0), rule(5, 5), update(1, 1), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario, true, oracles.toArray(new CoverageOracle[0]));
	}

	@Test
	public void testWithCoverageAndWithoutATM() throws Exception {
		String scenario = "scenariosforexamples/atm/atm4.avalla";
		List<CoverageOracle> oracles = new ArrayList<>();
		// NOTE: the correctness of the coverage is checked only for a subset of the
		// macro rules in the asm
		oracles.add(cov("r_Main()", branch(2, 1, 2), rule(11, 10), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_insertcard()", branch(2, 2, 1), rule(6, 6), update(3, 3), forall(0, 0, 0, 0)));
		oracles.add(cov("r_enterPin()", branch(4, 2, 1), rule(12, 6), update(6, 3), forall(0, 0, 0, 0)));
		oracles.add(cov("r_chooseService()", branch(5, 2, 3), rule(18, 8), update(8, 2), forall(0, 0, 0, 0)));
		oracles.add(cov("r_chooseAmount()", branch(3, 2, 2), rule(10, 7), update(4, 2), forall(0, 0, 0, 0)));
		oracles.add(cov("r_prelievo()", branch(6, 3, 2), rule(12, 6), update(3, 0), forall(0, 0, 0, 0)));
		oracles.add(cov("r_grantMoney(Integer)", branch(0, 0, 0), rule(6, 6), update(4, 4), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario, true, oracles.toArray(new CoverageOracle[0]));
	}

	@Test
	public void testWithCoverageAndWithoutCoffeVendingMachine() throws Exception {
		String baseFolder = "scenariosforexamples/coffeeVendingMachine";
		String scenario1 = baseFolder + "/scenario1.avalla";
		String scenario2 = baseFolder + "/scenario2.avalla";
		List<CoverageOracle> oracles = new ArrayList<>();
		// scenario1.avalla (without pick)
		oracles.add(cov("r_Main()", branch(4, 4, 1), rule(8, 8), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_serveProduct(Product)", branch(0, 0, 0), rule(3, 3), update(2, 2), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario1, true, oracles.toArray(new CoverageOracle[0]));
		// scenario2.avalla (with pick)
		RuleEvalWCov.reset();
		oracles.clear();
		stringWriter.getBuffer().setLength(0);
		oracles.add(cov("r_Main()", branch(4, 2, 1), rule(8, 6), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_serveProduct(Product)", branch(0, 0, 0), rule(3, 3), update(2, 2), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(scenario2, true, oracles.toArray(new CoverageOracle[0]));
		// Both scenarios
		RuleEvalWCov.reset();
		oracles.clear();
		stringWriter.getBuffer().setLength(0);
		oracles.add(cov("r_Main()", branch(4, 4, 1), rule(8, 8), update(1, 1), forall(0, 0, 0, 0)));
		oracles.add(cov("r_serveProduct(Product)", branch(0, 0, 0), rule(3, 3), update(2, 2), forall(0, 0, 0, 0)));
		testWithCoverageAndWithout(baseFolder, true, oracles.toArray(new CoverageOracle[0]));
	}

	// two test with coverage and without coverage enabled
	private void testWithCoverageAndWithout(String scenario, boolean expectedSuccess, CoverageOracle... coveredRules)
			throws IOException, Exception {
		testWithoutCoverage(scenario, expectedSuccess);
		// reset the buffer of the string writer
		stringWriter.getBuffer().setLength(0);
		test(scenario, true, true, expectedSuccess);
		// it does contain coverage info now
		List<String> outputs = Arrays.asList(stringWriter.toString().split("\n")).stream().map(x -> x.trim())
				.collect(Collectors.toList());
		assertTrue(outputs.contains("** Coverage Info: **"));
		int cov = outputs.indexOf("** covered rules: **");
		assertNotEquals(-1, cov);
		int nCov = outputs.indexOf("** NOT covered rules: **");
		assertNotEquals(-1, nCov);
		for (CoverageOracle oracle : coveredRules) {
			String signature = oracle.getSignature();
			boolean found = false;
			for (int i = cov + 1; i < nCov; i++) {
				String actualBranchString = outputs.get(i + 1);
				String actualRuleString = outputs.get(i + 2);
				String actualUpdateRuleString = outputs.get(i + 3);
				String actualForallRuleString = outputs.get(i + 4);
				if (outputs.get(i).contains("::" + signature)) {
					if (oracle.getBranchNumber() == 0) {
						assertTrue("wrong branch coverage for " + signature,
								actualBranchString.contains("- (no branches to be covered)"));
					} else {
						assertTrue("wrong branch coverage for " + signature,
								actualBranchString.contains(oracle.getBranchCoverage()));
					}
					if (oracle.getRuleNumber() == 0) {
						assertTrue("wrong rule coverage for " + signature,
								actualRuleString.contains("- (no rules to be covered)"));
					} else {
						assertTrue("wrong rule coverage for " + signature,
								actualRuleString.contains(oracle.getRuleCoverage()));
					}
					if (oracle.getUpdateRuleNumber() == 0) {
						assertTrue("wrong update rule coverage for " + signature,
								actualUpdateRuleString.contains("- (no update rules to be covered)"));
					} else {
						assertTrue("wrong update rule coverage for " + signature,
								actualUpdateRuleString.contains(oracle.getUpdateRuleCoverage()));
					}
					if (oracle.getForallRuleNumber() == 0) {
						assertTrue("wrong forall rule coverage for " + signature,
								actualForallRuleString.contains("- (no forall rules to be covered)"));
					} else {
						assertTrue("wrong forall rule coverage for " + signature,
								actualForallRuleString.contains(oracle.getForallCoverage()));
					}
					found = true;
					break;
				}
			}
			assertTrue("missing " + signature, found);
		}
	}

	private void testWithoutCoverage(String scenario, boolean expectedSuccess) throws IOException, Exception {
		// test without coverage
		test(scenario, true, false, expectedSuccess);
		// it does not contain coverage info
		String string = stringWriter.toString();
		assertFalse(string.contains("** Coverage Info: **"));
	}

	/**
	 * This class works as oracle for the result of the validation
	 */
	private static class CoverageOracle {
		private final String signature;
		private final int nBranch;
		private final int nRule;
		private final int nUpdate;
		private final int nForall;
		private final float branchCoverage;
		private final float ruleCoverage;
		private final float updateRuleCoverage;
		private final float forallRuleCoverage;

		public CoverageOracle(String signature, int nBranch, int coveredT, int coveredF, int nRule, int coveredRule,
				int nUpdate, int coveredUpdate, int nForall, int zeroIter, int oneIter, int multipleIter) {
			this.signature = signature;
			this.nBranch = nBranch;
			this.nRule = nRule;
			this.nUpdate = nUpdate;
			this.nForall = nForall;
			this.branchCoverage = nBranch == 0 ? 0 : (((float) coveredT + coveredF) / (nBranch * 2)) * 100;
			this.ruleCoverage = nRule == 0 ? 0 : ((float) coveredRule / nRule) * 100;
			this.updateRuleCoverage = nUpdate == 0 ? 0 : ((float) coveredUpdate / nUpdate) * 100;
			this.forallRuleCoverage = nForall == 0 ? 0 : (((float) zeroIter + oneIter + multipleIter) / (nForall * 3)) * 100;
		}

		public int getBranchNumber() {
			return nBranch;
		}

		public int getRuleNumber() {
			return nRule;
		}

		public int getUpdateRuleNumber() {
			return nUpdate;
		}

		public int getForallRuleNumber() {
			return nForall;
		}

		public String getSignature() {
			return signature;
		}

		private String formatCoverage(float cov) {
			return String.format(Locale.US, "%.2f%%", cov);
		}

		public String getBranchCoverage() {
			return formatCoverage(branchCoverage);
		}

		public String getRuleCoverage() {
			return formatCoverage(ruleCoverage);
		}

		public String getUpdateRuleCoverage() {
			return formatCoverage(updateRuleCoverage);
		}

		public String getForallCoverage() {
			return formatCoverage(forallRuleCoverage);
		}
	}

	/**
	 * Factory method to create a {@link CoverageOracle} with grouped arguments.
	 * <p>
	 * Example usage:
	 * <code>cov("r_Main()", branch(0, 0, 0), update(3, 3), forall(1, 1, 0, 1));</code>
	 * <br>
	 * this returns an oracle for asserting that the r_Main() macro has 0 branches,
	 * 3 update rules (3 of which covered) and 1 forall rule (executed with 0
	 * iterations and more than one iteration).
	 */
	private static CoverageOracle cov(String signature, Branch br, Rule rl, Update up, Forall fr) {
		return new CoverageOracle(signature, br.total, br.coveredTrue, br.coveredFalse, rl.total, rl.covered, up.total,
				up.covered, fr.total, fr.zeroIters, fr.oneIter, fr.multiIters);
	}

	/** Describes expected branch coverage. */
	static final class Branch {
		final int total;
		final int coveredTrue;
		final int coveredFalse;

		Branch(int total, int coveredTrue, int coveredFalse) {
			this.total = total;
			this.coveredTrue = coveredTrue;
			this.coveredFalse = coveredFalse;
		}
	}

	/** Describes expected rule coverage. */
	static final class Rule {
		final int total;
		final int covered;

		Rule(int total, int covered) {
			this.total = total;
			this.covered = covered;
		}
	}

	/** Describes expected update rule coverage. */
	static final class Update {
		final int total;
		final int covered;

		Update(int total, int covered) {
			this.total = total;
			this.covered = covered;
		}
	}

	/** Describes expected forall rule coverage. */
	static final class Forall {
		final int total;
		final int zeroIters;
		final int oneIter;
		final int multiIters;

		Forall(int total, int zeroIters, int oneIter, int multiIters) {
			this.total = total;
			this.zeroIters = zeroIters;
			this.oneIter = oneIter;
			this.multiIters = multiIters;
		}
	}

	// --- Convenience factory methods to avoid 'new' in tests ---
	private static Branch branch(int total, int coveredTrue, int coveredFalse) {
		return new Branch(total, coveredTrue, coveredFalse);
	}

	private static Rule rule(int total, int covered) {
		return new Rule(total, covered);
	}

	private static Update update(int total, int covered) {
		return new Update(total, covered);
	}

	private static Forall forall(int total, int zeroIters, int oneIter, int multiIters) {
		return new Forall(total, zeroIters, oneIter, multiIters);
	}

}
