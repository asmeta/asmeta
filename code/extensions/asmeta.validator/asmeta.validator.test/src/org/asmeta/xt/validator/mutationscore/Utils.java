package org.asmeta.xt.validator.mutationscore;

import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Utils {
	
	static void assertValidScores(Iterable<Entry<String, Entry<Integer, Integer>>> scores) {
		boolean foundScore = false;
		for (Entry<String, Entry<Integer, Integer>> score : scores) {
			foundScore = true;
			int killedMutants = score.getValue().getKey();
			int generatedMutants = score.getValue().getValue();
			assertTrue(generatedMutants >= 0, score.getKey());
			assertTrue(killedMutants >= 0, score.getKey());
			assertTrue(killedMutants <= generatedMutants, score.getKey());
		}
		assertTrue(foundScore);
	}

}
