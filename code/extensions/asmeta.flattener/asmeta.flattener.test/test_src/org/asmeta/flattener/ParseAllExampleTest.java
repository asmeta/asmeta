package org.asmeta.flattener;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class ParseAllExampleTest {

	@Test
	public void testParseOnExamples() throws IOException {
		Files.walk(Paths.get("examples"))
			.filter(Files::isRegularFile)
			.filter(x -> x.getFileName().toString().endsWith(".asm"))
			.filter(x -> !(x.getFileName().toString().contains("_flat.asm")))
			.filter(x -> !(x.getFileName().toString().contains("_flattened.asm")))
			.forEach(x -> {
				System.out.println(x);
			AsmCollection asmcollection;
			try {
				asmcollection = ASMParser.setUpReadAsm(x.toFile());
				assertNotNull(asmcollection);
				assertNotNull(asmcollection.getMain());
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		});
	}

}
