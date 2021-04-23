package org.asmeta.flattener;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;

public class ParseAllExampleTest {

	@Test
	public void testParseOnExamples() {
		File[] examples = new File("examples").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {				
				return !pathname.isDirectory() && !pathname.getName().contains("_flat.asm") && !pathname.getName().contains("_flattened.asm");
			}
		});
		for (File asm: examples ) {
			try {
				AsmCollection asmcollection = ASMParser.setUpReadAsm(asm);
				assertNotNull(asmcollection);
				assertNotNull(asmcollection.getMain());
			} catch (Exception e) {
				System.out.println(asm);
				fail();
			}
			
		}
	}

}
