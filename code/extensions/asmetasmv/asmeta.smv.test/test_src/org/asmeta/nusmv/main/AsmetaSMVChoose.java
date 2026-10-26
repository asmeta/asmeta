package org.asmeta.nusmv.main;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class AsmetaSMVChoose extends AsmetaSMVtestTranslateBase {

	@Test
	public void testGuardTRUE() {
		assertTrue(testOneSpec("examples/chooseProblem2.asm"));
		assertFalse(contains("examples/chooseProblem2.smv", "INVAR"));
	}

	private boolean contains(String fileName, CharSequence stringToSearch) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			// search all
			long count = stream.filter(l -> l.contains(stringToSearch)).count();
			return count > 0;
			// do whatever
		} catch (IOException e) {
			// log exception
			throw new RuntimeException();
		}
	}

}
