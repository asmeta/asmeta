package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

class ParseAllExampleTest {

	@Test
	void parseOnExamples() throws Exception {
		Files.walk(Paths.get("examples"))
			.filter(Files::isRegularFile)
			.filter(x -> x.getFileName().toString().endsWith(AsmetaParserUtility.ASM_EXTENSION))
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
