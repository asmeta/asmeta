package org.asmeta.nusmv.main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

//
// check that all the examples parse correctly
//
public class ParseAllExampleTest {

	@Ignore
	@Test
	public void testParseOnExamples() throws IOException {

		Files.walk(new File("examples").toPath()).forEach(x -> {
			if (x.toString().endsWith(AsmetaParserUtility.ASM_EXTENSION)) {
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
