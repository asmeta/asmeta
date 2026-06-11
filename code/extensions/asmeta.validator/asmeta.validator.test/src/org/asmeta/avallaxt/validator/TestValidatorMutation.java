package org.asmeta.avallaxt.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.asmeta.xt.validator.AsmetaV;
import org.junit.jupiter.api.Test;

public class TestValidatorMutation extends TestValidator {

	@Test
	void testSimpleExample() throws Exception {
		testWMutation("scenariosforexamples/advancedClock/advancedClock1.avalla");
	}

	private void testWMutation(String scenarioPath) throws Exception {
		log.debug("executing " + scenarioPath);
		// it should be runnable
		List<String> result = AsmetaV.execValidation(scenarioPath, AsmetaV.computeMutationScore);
		assertTrue(result.isEmpty(), "failed " + result);
	}

}
