package org.asmeta.nusmv.main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.junit.Ignore;
import org.junit.Test;

import asmeta.AsmCollection;

//
// check that all the examples parse correctly
//
public class ParseAllExampleTest {

	@Ignore
	@Test
	public void testParseOnExamples() throws IOException {

		Files.walk(new File("examples").toPath()).forEach(x -> {
			if (x.toString().endsWith(ASMParser.ASM_EXTENSION)) {
				System.out.println(x);
				try {
					AsmCollection asmcollection = ASMParser.setUpReadAsm(x.toFile());
					assertNotNull(asmcollection);
					assertNotNull(asmcollection.getMain());
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
			}
		});

	}
}
