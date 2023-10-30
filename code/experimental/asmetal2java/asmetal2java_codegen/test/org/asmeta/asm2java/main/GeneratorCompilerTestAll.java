package org.asmeta.asm2java.main;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

public class GeneratorCompilerTestAll {

	private TranslatorOptions options = new TranslatorOptions(true, true, true);

	@Test
	public void testAllLocalExamples() throws IOException, Exception {
		List<String> failures = new ArrayList<>();
		Path path = Paths.get("examples/");
		assertTrue(path.toFile().exists() && path.toFile().isDirectory());
		Stream<Path> walk = Files.walk(path);
		walk.forEach(x -> {
			try {
				String fileName = x.toFile().toString();
				if (fileName.endsWith(ASMParser.ASM_EXTENSION))
					if (!GeneratorCompilerTest.test(fileName, options).success){
							failures.add(fileName);
							System.err.println("failing for "+ fileName);
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
