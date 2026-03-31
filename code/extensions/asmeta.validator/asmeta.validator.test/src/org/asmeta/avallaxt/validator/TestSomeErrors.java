package org.asmeta.avallaxt.validator;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class TestSomeErrors extends TestValidator {

	@Test void withAsmeta(){
		// common error : try to run the validator with and asmeta file
		try {
			test("scenariosfortest/Lift_extramon.asm", true, false, true);
		} catch (RuntimeException e) {
			assertEquals("invalid file, the validator works with .avalla files", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();			
		}
	}

	@Test void withWrongDirectory() throws Exception {		
		// common error : try to run the validator with and asmeta file
		try {
			test("inexistingdir/", true, false, true);
		} catch (RuntimeException e) {
			assertEquals("path inexistingdir/ does not exist", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();			
		}
	}	
	
}
