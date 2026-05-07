package org.asmeta.xt.validator;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.fail;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMutationScore extends TestValidator {


	@BeforeAll void setupLogger() {
		// set to error to reduc eoutput for gitlab
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.ERROR);
		// this must be in debug since the output is checked with this logger
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);
	}

	@Test 
	void test1() throws Exception {
		String scenario = "scenariosforexamples/advancedClock/advancedClock1.avalla";
		List<String> result = AsmetaV.execValidation(scenario, AsmetaV.computeMutationScore);
	}
}
