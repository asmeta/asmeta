package asmeta.ai.propgen.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.ai.propgen.PropertyGenerationListener;
import asmeta.ai.propgen.PropertyType;

class TestLibraryPreparer {

	@TempDir
	Path tempDir;

	@BeforeAll
	static void setupLogging() {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.OFF);
	}

	@Test
	void prepareLtlLibraryAdvancedClock() throws Exception {
		Path asm = copyAdvancedClock();

		LibraryPreparer.prepare(asm.toString(), PropertyType.LTLPROP, PropertyGenerationListener.NO_OP);

		assertEquals(1, countImport(asm, "LTLLibrary"));
		assertTrue(Files.exists(asm.getParent().resolve("LTLLibrary.asm")));
	}

	private Path copyAdvancedClock() throws IOException {
		Path resources = tempDir.resolve("asmeta.ai.propgen.test").resolve("resources");
		Path standardLibrary = tempDir.resolve("STDL").resolve("StandardLibrary.asm");
		Files.createDirectories(resources);
		Files.createDirectories(standardLibrary.getParent());

		Path asm = resources.resolve("AdvancedClock.asm");
		Files.copy(Path.of("resources", "AdvancedClock.asm"), asm, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(Path.of("resources", "StandardLibrary.asm"), standardLibrary, StandardCopyOption.REPLACE_EXISTING);
		return asm;
	}

	private long countImport(Path asm, String libraryName) throws IOException {
		return Files.lines(asm, StandardCharsets.UTF_8)
				.filter(line -> line.trim().equals("import " + libraryName))
				.count();
	}

}
