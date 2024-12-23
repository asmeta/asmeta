package org.asmeta.asm2java.generator;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

// test that all the asmeta files in example are correct
public class TestAsmetaExamplesParser {

	@Test
	public void testExamples() throws IOException {
		Stream<Path> files = Files.walk(Paths.get("examples"));
		files.forEach( asmeta -> {
			if (asmeta.toString().endsWith(".asm")) {
				try {
					AsmCollection x = ASMParser.setUpReadAsm(asmeta.toFile());
					System.out.println(asmeta);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.err.println(asmeta);
				}
			}
		});
		files.close();
	}
	
}
