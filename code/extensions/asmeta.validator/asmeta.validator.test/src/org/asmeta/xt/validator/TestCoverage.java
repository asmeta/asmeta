package org.asmeta.xt.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.DEBUG);
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
		testWithCoverageAndWithout("scenariosforexamples/ferryman/emptyScenario.avalla");
	}
	
	@Test
	public void testWithCoverageAndWithoutAvallaWithNoStepAndNoCheck() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/ferryman/noStepNoCheckScenario.avalla");
	}

	@Test
	public void testWithCoverageAndWithoutAdvancedClock() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/advancedClock/advancedClock1.avalla",
				new CoverageOracle("r_Main()", 1, 0, 1, 1, 1));
	}

	@Test
	public void testWithCoverageAndWithoutSluiceGate() throws Exception {
		String scenario = "scenariosforexamples/sluiceGate";
		List<CoverageOracle> oracles = new ArrayList<>();
		// r_Main()
		int nBranch = 8;
		int coveredT = 8;
		int coveredF = 8;
		int nUpdate = 4;
		int coveredUpdate = 4;
		oracles.add(new CoverageOracle("r_Main()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_start_to_raise()
		nBranch = 0;
		coveredT = 0;
		coveredF = 0;
		nUpdate = 2;
		coveredUpdate = 2;
		oracles.add(new CoverageOracle("r_start_to_raise()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_start_to_lower()
		nBranch = 0;
		coveredT = 0;
		coveredF = 0;
		nUpdate = 2;
		coveredUpdate = 2;
		oracles.add(new CoverageOracle("r_start_to_lower()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_stop_motor()
		nBranch = 0;
		coveredT = 0;
		coveredF = 0;
		nUpdate = 1;
		coveredUpdate = 1;
		oracles.add(new CoverageOracle("r_stop_motor()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		testWithCoverageAndWithout(scenario, oracles.toArray(new CoverageOracle[0]));
	}

	@Test
	public void testWithCoverageAndWithoutATM() throws Exception {
		String scenario = "scenariosforexamples/atm/atm4.avalla";
		List<CoverageOracle> oracles = new ArrayList<>();
		// r_Main()
		int nBranch = 2;
		int coveredT = 1;
		int coveredF = 2;
		int nUpdate = 1;
		int coveredUpdate = 1;
		oracles.add(new CoverageOracle("r_Main()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_insertcard()
		nBranch = 2;
		coveredT = 2;
		coveredF = 1;
		nUpdate = 3;
		coveredUpdate = 3;
		oracles.add(new CoverageOracle("r_insertcard()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_enterPin()
		nBranch = 4;
		coveredT = 2;
		coveredF = 1;
		nUpdate = 6;
		coveredUpdate = 3;
		oracles.add(new CoverageOracle("r_enterPin()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_chooseService()
		nBranch = 5;
		coveredT = 2;
		coveredF = 3;
		nUpdate = 8;
		coveredUpdate = 2;
		oracles.add(new CoverageOracle("r_chooseService()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_chooseAmount()
		nBranch = 3;
		coveredT = 2;
		coveredF = 2;
		nUpdate = 4;
		coveredUpdate = 2;
		oracles.add(new CoverageOracle("r_chooseAmount()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_prelievo()
		nBranch = 6;
		coveredT = 3;
		coveredF = 2;
		nUpdate = 3;
		coveredUpdate = 0;
		oracles.add(new CoverageOracle("r_prelievo()", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		// r_grantMoney(Integer)
		nBranch = 0;
		coveredT = 0;
		coveredF = 0;
		nUpdate = 4;
		coveredUpdate = 4;
		oracles.add(new CoverageOracle("r_grantMoney(Integer)", nBranch, coveredT, coveredF, nUpdate, coveredUpdate));
		testWithCoverageAndWithout(scenario, oracles.toArray(new CoverageOracle[0]));
	}

	// two test with coverage and without coverage enabled
	private void testWithCoverageAndWithout(String scenario, CoverageOracle... coveredRules)
			throws IOException, Exception {
		testWithoutCoverage(scenario);
		// reset the
		stringWriter.getBuffer().setLength(0);
		test(scenario, true, true, true);
		// it does contain coverage info now
		List<String> outputs = Arrays.asList(stringWriter.toString().split("\n")).stream().map(x -> x.trim())
				.collect(Collectors.toList());
		assertTrue(outputs.contains("** Coverage Info: **"));
		int cov = outputs.indexOf("** covered rules: **");
		assertNotEquals(-1, cov);
		int nCov = outputs.indexOf("** NOT covered rules: **");
		assertNotEquals(-1, nCov);
		for (CoverageOracle oracle : coveredRules) {
			boolean found = false;
			for (int i = cov + 1; i < nCov; i++) {
				if (outputs.get(i).contains("::" + oracle.getSignature())) {
					if (oracle.getBranchNumber() == 0) {
						assertTrue("wrong branch coverage for " + oracle.getSignature(),
							outputs.get(i + 1).contains("-> branch coverage: - (no conditional rules to be covered)"));
					}else {
						assertTrue("wrong branch coverage for " + oracle.getSignature(),
							outputs.get(i + 1).contains("-> branch coverage: " + oracle.getBranchCoverage() + "%"));
					}
					if (oracle.getUpdateRuleNumber() == 0) {
						assertTrue("wrong update rule coverage for " + oracle.getSignature(), 
							outputs.get(i + 2).contains("-> update rule coverage: - (no update rules to be covered)"));
					}else {
						assertTrue("wrong update rule coverage for " + oracle.getSignature(), 
							outputs.get(i + 2).contains("-> update rule coverage: " + oracle.getUpdateRuleCoverage() + "%"));
					}
					found = true;
					break;
				}
			}
			assertTrue("missing " + oracle.getSignature(), found);
		}
	}

	private void testWithoutCoverage(String scenario) throws IOException, Exception {
		// test without coverage
		test(scenario, true, false, true);
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
		private final int nUpdate;
		private final float branchCoverage;
		private final float updateRuleCoverage;

		public CoverageOracle(String signature, int nBranch, int coveredT, int coveredF, int nUpdate,
				int coveredUpdate) {
			this.signature = signature;
			this.nBranch = nBranch;
			this.nUpdate = nUpdate;
			this.branchCoverage = nBranch == 0 ? 0 : (((float) coveredT + coveredF) / (nBranch * 2)) * 100;
			this.updateRuleCoverage = nUpdate == 0 ? 0 : ((float) coveredUpdate / nUpdate) * 100;
		}
		
		public int getBranchNumber() {
			return nBranch;
		}

		public int getUpdateRuleNumber() {
			return nUpdate;
		}

		public String getSignature() {
			return signature;
		}

		public float getBranchCoverage() {
			return branchCoverage;
		}

		public float getUpdateRuleCoverage() {
			return updateRuleCoverage;
		}
		
	}

}
