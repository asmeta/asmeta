package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.avallaxt.validator.TestValidator;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ParToSeqMutator;

import asmeta.mutation.operators.NamedExpressionMutator;
import asmeta.mutation.operators.LogicalTermMutator;
import asmeta.mutation.operators.RelationalTermMutator;
import asmeta.mutation.operators.ArithmeticTermMutator;

class MutationScoreExecutorTest extends TestValidator {

	private static final String COFFEE_VENDING_MACHINE_SCENARIOS =
			"scenariosforexamples/coffeeVendingMachine";
	private static final String ADVANCED_CLOCK_SCENARIO =
			"scenariosforexamples/advancedClock/advancedClock1.avalla";

	@Test
	void testComputeMutationScoreFromSingleScenarioUsesAllStandardOperators() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		var result = executor.computeMutationScoreFromScenarios(ADVANCED_CLOCK_SCENARIO);

		assertStandardOperators(result.keySet());
		MutationTestUtils.assertValidScores(result.entrySet());
	}

	@Test
	void testComputeMutationScoreFromScenarioDirectory() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		var result = executor.computeMutationScoreFromScenarios(COFFEE_VENDING_MACHINE_SCENARIOS);

		assertStandardOperators(result.keySet());
		MutationTestUtils.assertValidScores(result.entrySet());
	}

	@Test
	void testGenerateParToSeqMutantsFromAsmPath() throws Exception {
		MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

		List<AsmCollection> mutants = executor.generateMutants(COFFEE_VENDING_MACHINE_SCENARIOS, new ParToSeqMutator());

		assertFalse(mutants.isEmpty());
	}

	//private void assertStandardOperators(Set<String> operatorNames) {
	//	assertEquals(Set.of("CaseMutator", "ChooseRuleMutator", "CondNegator", "CondRemover", "ForAllMutator",
	//			"ParToSeqMutator", "RuleRemover", "SeqToParMutator"), operatorNames);
	//}

	private void assertStandardOperators(Set<String> operatorNames) {
	    assertEquals(Set.of("CaseMutator", "ChooseRuleMutator", "CondNegator", "CondRemover", "ForAllMutator",
	            "ParToSeqMutator", "RuleRemover", "SeqToParMutator",
	            "Logical_not", "Logical_and", "Logical_xor", "Logical_or", "Logical_implies", "Logical_iff",
	            "Relational_lt", "Relational_le", "Relational_gt", "Relational_ge", "Relational_eq", "Relational_neq",
	            "Arithmetic_plus", "Arithmetic_minus", "Arithmetic_mult", "Arithmetic_div",
	            "Arithmetic_idiv", "Arithmetic_mod", "Arithmetic_pwr"), operatorNames);
	}


	@Test
	void testGenerateExpressionMutants() throws Exception {
	    MutationScoreExecutor executor = MutationScoreExecutor.createTempExecutor();

	    List<AsmCollection> logicalMutants = executor.generateMutants(COFFEE_VENDING_MACHINE_SCENARIOS, new NamedExpressionMutator("Logical_and", new LogicalTermMutator("and")));
	    List<AsmCollection> relationalMutants = executor.generateMutants(COFFEE_VENDING_MACHINE_SCENARIOS, new NamedExpressionMutator("Relational_eq", new RelationalTermMutator("eq", List.of("lt", "le", "gt", "ge", "neq"))));
	    List<AsmCollection> arithmeticMutants = executor.generateMutants(COFFEE_VENDING_MACHINE_SCENARIOS, new NamedExpressionMutator("Arithmetic_plus", new ArithmeticTermMutator("plus")));

	    assertFalse(logicalMutants.isEmpty());
	    assertFalse(relationalMutants.isEmpty());
	    assertFalse(arithmeticMutants.isEmpty());
	}

}
