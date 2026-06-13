package org.asmeta.xt.validator.mutationscore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.parser.AsmetaParserUtility;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.mutation.operators.ChooseRuleMutator;

class MutateScenarioWChooseTest {

	private static final String NFM_DIR = "scenariosforexamples/mutationScore/nfm25";

	@Test
	void testMutateScenarioAndSaveTemporaryAsm() throws Exception {
		Path temp = Files.createTempDirectory("asmeta-mutated-scenario-test");
		try {
			String scenarioPath = Path.of(NFM_DIR, "scenario_ref_flaky.avalla").toString();
			MutationScoreExecutor.AsmetaMutatedFromAvalla asmetaBuilder =
					new MutationScoreExecutor.AsmetaMutatedFromAvalla(scenarioPath, temp.toFile());

			ChooseRuleMutator mutator = new ChooseRuleMutator(asmetaBuilder.getAsm().getMain());
			List<AsmCollection> mutants = mutator.mutate(asmetaBuilder.getAsm());
			assertEquals(1, mutants.size());

			asmetaBuilder.setAsmeta(mutants.get(0));
			asmetaBuilder.save();
			File tempAsmPath = asmetaBuilder.getTempAsmPath();

			assertTrue(tempAsmPath.exists());
			assertTrue(tempAsmPath.isFile());
			assertTrue(tempAsmPath.getName().endsWith(AsmetaParserUtility.ASM_EXTENSION));
		} finally {
			deleteRecursively(temp);
		}
	}

	private static void deleteRecursively(Path temp) throws IOException {
		try (Stream<Path> stream = Files.walk(temp)) {
			stream.sorted(Comparator.reverseOrder())
					.forEach(MutateScenarioWChooseTest::deleteIfExists);
		}
	}

	private static void deleteIfExists(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			path.toFile().deleteOnExit();
		}
	}
}