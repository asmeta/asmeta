package asmeta.cli;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.junit.Test;

public class AsmetaCLITest {

	@Test
	public void testSetUpLogger() {
		AsmetaCLI acli = new AsmetaCLI() {
			@Override
			protected RunCLIResult runWith(File asmFile) throws Exception {
				return null;
			}
		};
		Logger log = Logger.getRootLogger();
		int nappenders = Collections.list(log.getAllAppenders()).size();
		assertTrue(nappenders <= 1);
		acli.setUpLogger();
		nappenders = Collections.list(log.getAllAppenders()).size();
		assertTrue(nappenders <= 1);
		acli.setUpLogger();
		nappenders = Collections.list(log.getAllAppenders()).size();
		assertTrue(nappenders <= 1);
		acli.setUpLogger();
		nappenders = Collections.list(log.getAllAppenders()).size();
		assertTrue(nappenders <= 1);
	}

}
