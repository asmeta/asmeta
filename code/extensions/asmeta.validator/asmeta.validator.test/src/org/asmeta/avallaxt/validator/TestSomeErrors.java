package org.asmeta.avallaxt.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmetaPrintInfo;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaPrinterForAvalla;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSomeErrors extends TestValidator {

	@Test
	public void testWithAsmeta(){
		// common error : try to run the validator with and asmeta file
		try {
			test("scenariosfortest/Lift_extramon.asm", true, false);
		} catch (RuntimeException e) {
			assertEquals("invalid file, the validator works with .avalla files", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();			
		}
	}

	@Test
	public void testWithWrongDirectory() throws Exception {		
		// common error : try to run the validator with and asmeta file
		try {
			test("inexistingdir/", true, false);
		} catch (RuntimeException e) {
			assertEquals("path inexistingdir/ does not exist", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();			
		}
	}	
	
}
