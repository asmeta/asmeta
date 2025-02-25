package org.asmeta.modeladvisor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class ParseAllExampleTest {

	@Test
	public void testParseOnExamples() throws IOException {
		Files.walk(new File("examples").toPath()).forEach(x -> {
			String filepath = x.toString();
			if (filepath.endsWith(ASMParser.ASM_EXTENSION) && 
					!filepath.contains("errors")&& 
					!filepath.contains("old")&& 
					// TO BE FIXED
					!filepath.contains("FLIP_FLOP")&& 
					!filepath.contains("MERGE")	&& 
					!filepath.contains("philosophers_with_res")	&& 
					!filepath.contains("QUICKSORT")&& 
					!filepath.contains("systemc")&&
					!filepath.contains("SystemCUMLProfile") &&
					!filepath.contains("test"+File.separatorChar+"simulator")										
					) {
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
