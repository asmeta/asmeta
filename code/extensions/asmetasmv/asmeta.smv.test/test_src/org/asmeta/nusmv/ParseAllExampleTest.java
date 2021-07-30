package org.asmeta.nusmv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class ParseAllExampleTest {

	@Test
	public void testParseOnExamples() throws IOException {

		Files.walk(new File("examples").toPath()).forEach(x -> {
			if (x.toString().endsWith(".asm")) {
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
