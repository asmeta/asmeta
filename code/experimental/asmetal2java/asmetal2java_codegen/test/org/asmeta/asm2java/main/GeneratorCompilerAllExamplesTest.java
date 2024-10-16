package org.asmeta.asm2java.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

public class GeneratorCompilerAllExamplesTest {

	private TranslatorOptions options = new TranslatorOptions(true, true, true, false, false, false, false);
	
	static boolean failOnError = false;
	
	static ArrayList<String> excludeFiles = new ArrayList<String>(Arrays.asList("StandardLibrary.asm", "CTLLibrary.asm", "LTLLibrary.asm", "fibonacci.asm", "testSignature.asm", "battleship.asm", "QuickSort.asm"));

	@Test
	public void testAllLocalExamples() throws IOException, Exception {
		List<String> failures = new ArrayList<>();
		Path path = Paths.get("examples/");
		assertTrue(path.toFile().exists() && path.toFile().isDirectory());
		Stream<Path> walk = Files.walk(path);
		walk.forEach(x -> {
			try {
				String fileName = x.toFile().toString();				
				if (fileName.endsWith(ASMParser.ASM_EXTENSION) && excludeFiles.stream().filter(tX -> fileName.contains(tX)).count() == 0)
					if (!GeneratorCompilerTest.test(fileName, options).getSuccess()) {
						if (failOnError) fail();
						failures.add(fileName);
						System.err.println("failing for " + fileName);
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		walk.close();
		assertTrue(failures.toString(), failures.isEmpty());
	}

}
