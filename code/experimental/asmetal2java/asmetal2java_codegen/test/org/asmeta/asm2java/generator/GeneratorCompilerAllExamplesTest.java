package org.asmeta.asm2java.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.TranslatorOptionsImpl;
import org.asmeta.parser.ASMParser;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorCompilerAllExamplesTest {

	private TranslatorOptions options = new TranslatorOptionsImpl(true, true, true);
	
	static boolean failOnError = false;
		
	static List<String> excludeFiles = new ArrayList<>();
	
	@BeforeClass
	public static void setup() {
		excludeFiles.addAll(GeneratorCompilerTest.libraries);
		excludeFiles.addAll(GeneratorCompilerTest.parseException);
		excludeFiles.addAll(GeneratorCompilerTest.runtimeException);
		excludeFiles.addAll(GeneratorCompilerTest.errors);
	}

	@Test
	public void testAllLocalExamples() throws IOException {
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
				e.printStackTrace();
			}
		});
		walk.close();
		assertTrue(failures.toString(), failures.isEmpty());
	}

}
