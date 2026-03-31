package org.asmeta.validator.main;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class AsmetaV_CLITest {

	private Path avallaPath;

	public AsmetaV_CLITest() {
		String string = "scenariosforexamples/advancedClock/advancedClock1.avalla";
		avallaPath = Path.of(string);
		assertTrue(Files.exists(avallaPath));
	}

	@Test void mainRelDirExample() {
		// run main with an example given as relative path
		assertEquals(
				"scenariosforexamples" + File.separator + "advancedClock" + File.separator + "advancedClock1.avalla",
				avallaPath.toString());
		AsmetaV_CLI.main(new String[] { avallaPath.toString() });
	}

	@Test void mainAbsoluteDirExample() {
		// run main with an example given as absolute path
		assertNotEquals("scenariosforexamples\\advancedClock\\advancedClock1.avalla",
				avallaPath.toAbsolutePath().toString());
		// something like:
		// <C:\Users\angel\codicefromrepos\ricerca\asmeta\asmeta\code\extensions\asmeta.validator\asmeta.validator.test\scenariosforexamples\advancedClock\advancedClock1.avalla>
		AsmetaV_CLI.main(new String[] { avallaPath.toAbsolutePath().toString() });
	}

	@Test void currentDirExample() {
		// execute in the directory
		Path parent = avallaPath.getParent();
		assertEquals("scenariosforexamples" + File.separator + "advancedClock", parent.toString());
		// TODO vorrei chiamre con semplicemente il nome e cambiare la directory a
		// parent ma non so come fare
		// da fare con il jar?
	}

	@Test void failingTest() {
		AsmetaV_CLI.main(new String[] {"scenariosforexamples/advancedClock/advancedClock5_fail.avalla" });
	}


	@Test void coverage() {
		AsmetaV_CLI.main(new String[]{ "-cov", avallaPath.toString() });
	}

	
}
