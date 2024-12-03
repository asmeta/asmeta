package org.asmeta.xt.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
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
import org.junit.BeforeClass;
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
	public void cleanAppender() {
		// remove the appender
		Logger.getLogger(AsmetaV.class).removeAppender(writerAppender);
	}

	@Test
	public void testWithCoverageAndWithoutAdvancedClock() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/advancedClock/advancedClock1.avalla", "r_Main");
	}

	@Test
	public void testATM() throws Exception {
		testWithCoverageAndWithout("scenariosforexamples/atm/atm4.avalla", "r_Main", "r_insertcard"
//				r_chooseAmount
//				r_grantMoney
//				r_subtractFrom
//				r_processMoneyRequest
//				r_prelievo
//				r_chooseService
//				r_enterPin
		);
	}

	// two test with coverage and without coverage enabled
	private void testWithCoverageAndWithout(String scenario, String... coveredRules) throws IOException, Exception {
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
		System.err.println(outputs);
		List<String> coveredRulesOutput = outputs.subList(cov + 1, nCov);
		//
		Arrays.asList(coveredRules).stream()
				.forEach(x -> assertTrue("missing " + x, coveredRulesOutput.stream().anyMatch(y -> y.contains(x))));
	}

	private void testWithoutCoverage(String scenario) throws IOException, Exception {
		// test without coverage
		test(scenario, true, false, true);
		// it does not contain coverage info
		String string = stringWriter.toString();
		assertFalse(string.contains("** Coverage Info: **"));
	}

}
