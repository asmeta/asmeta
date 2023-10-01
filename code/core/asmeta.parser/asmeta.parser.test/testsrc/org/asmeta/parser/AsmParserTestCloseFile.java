package org.asmeta.parser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import asmeta.AsmCollection;


/**
 * test that the parser closes a file even if there is an error
 * 
 * @author garganti
 *
 */
public class AsmParserTestCloseFile extends AsmParserTest{

	@Test
	public void testWithError(){
		// un file a caso con errore
		String spec = "test/errors/np/genericError.asm";
		File errfile = new File(FILE_BASE + spec);
		AsmCollection asms = testOneSpec(errfile, true, false);
		assertNull(asms);
		//now check that it can be renamed !!!
		File errfiletemp = new File(FILE_BASE + spec + "__temp");
		assertTrue(errfile.renameTo(errfiletemp));
		assertFalse(errfile.exists());
		assertTrue(errfiletemp.exists());
		// now rename back
		assertTrue(errfiletemp.renameTo(errfile));
		assertTrue(errfile.exists());
		assertFalse(errfiletemp.exists());
	}

}