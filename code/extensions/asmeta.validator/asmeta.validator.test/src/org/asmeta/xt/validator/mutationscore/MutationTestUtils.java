package org.asmeta.xt.validator.mutationscore;

import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MutationTestUtils {
	
	static void assertValidScores(Iterable<Entry<String, Entry<Integer, Integer>>> scores) {
		boolean foundScore = false;
		int totalKilledMutants = 0;
		int totalGeneratedMutants = 0;
		for (Entry<String, Entry<Integer, Integer>> score : scores) {
			foundScore = true;
			int killedMutants = score.getValue().getKey();
			int generatedMutants = score.getValue().getValue();
			totalKilledMutants += killedMutants;
			totalGeneratedMutants += generatedMutants;
			assertTrue(generatedMutants >= 0, score.getKey());
			assertTrue(killedMutants >= 0, score.getKey());
			assertTrue(killedMutants <= generatedMutants, score.getKey());
			System.out.println(score.getKey() + ": " + killedMutants + "/" + generatedMutants);
		}
		System.out.println("Total: " + totalKilledMutants + "/" + totalGeneratedMutants);
		assertTrue(foundScore);
	}

}
