package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.asmeta.avallaxt.validator.TestValidator;
import org.asmeta.xt.validator.AsmetaV;
import org.junit.jupiter.api.Test;

class TestMutationScore extends TestValidator {

	@Test
	void testValidationWithMutationScore() throws Exception {
		String scenario = "scenariosforexamples/advancedClock/advancedClock1.avalla";
		List<String> result = AsmetaV.execValidation(scenario, AsmetaV.computeMutationScore);
		assertTrue(result.isEmpty(), "failed " + result);
	}
}
