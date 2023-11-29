package org.asmeta.validator.main;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class AsmetaV_CLITest {
	
	private Path avallaPath;

	public AsmetaV_CLITest(){
		String string = "scenariosforexamples/advancedClock/advancedClock1.avalla";
		avallaPath = Path.of(string);
		assertTrue(Files.exists(avallaPath));
	}

	@Test
	public void testMainRelDirExample() {
		// run main with an example given as relative path
		assertEquals("scenariosforexamples"+ File.separator+"advancedClock"+ File.separator+"advancedClock1.avalla", avallaPath.toString());
		AsmetaV_CLI.main(new String[] {avallaPath.toString()});
	}

	@Test
	public void testMainAbsoluteDirExample() {
		// run main with an example given as absolute path
		assertNotEquals("scenariosforexamples\\advancedClock\\advancedClock1.avalla", avallaPath.toAbsolutePath().toString());
		// something like:
		//<C:\Users\angel\codicefromrepos\ricerca\asmeta\asmeta\code\extensions\asmeta.validator\asmeta.validator.test\scenariosforexamples\advancedClock\advancedClock1.avalla>
		AsmetaV_CLI.main(new String[] {avallaPath.toAbsolutePath().toString()});
	}

	@Test
	public void testCurrentDirExample() {
		// execute in the directory
		Path parent = avallaPath.getParent();
		assertEquals("scenariosforexamples"+ File.separator+"advancedClock",parent.toString());
		// TODO vorrei chiamre con semplicemente il nome e cambiare la directory a parent ma non so come fare
		// da fare con il jar?
	}

	
}
