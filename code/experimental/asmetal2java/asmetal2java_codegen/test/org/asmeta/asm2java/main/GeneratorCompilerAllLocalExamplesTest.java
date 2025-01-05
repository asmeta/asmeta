package org.asmeta.asm2java.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

// 
// it generates the java code for all the examples (local) except some that are excluded 
// and it compiles  them and it check that there are no errors
// the code is generated and compiled locally in a subdir of the examples

//
public class GeneratorCompilerAllLocalExamplesTest {

	private TranslatorOptions options = new TranslatorOptions(true, true, true);
	
	static boolean failOnError = false;
	
	static ArrayList<String> excludeFiles = 
			new ArrayList<String>(Arrays.asList("StandardLibrary.asm", "CTLLibrary.asm", "LTLLibrary.asm", 
					"fibonacci.asm", "testSignature.asm", "battleship.asm", "QuickSort.asm"));

	@Test
	public void testAllLocalExamples() throws Exception {
		
		List<String> failures = new ArrayList<>();
		Path path = Paths.get("examples/");
		assertTrue(path.toFile().exists() && path.toFile().isDirectory());
		// build where to put the .java and the .class
		String dirCompilazione = path.toString() + File.separator + "compilazione";
		File dirCompF = new File(dirCompilazione);
		// create if it does not exists
		if (!dirCompF.exists()) dirCompF.mkdirs();
		assert dirCompF.exists() && dirCompF.isDirectory();
		Stream<Path> walk = Files.walk(path);
		walk.forEach(x -> {
			try {
				String fileName = x.toFile().toString();
				if (fileName.endsWith(ASMParser.ASM_EXTENSION) && 
						excludeFiles.stream().filter(tX -> fileName.contains(tX)).count() == 0) {
					CompileResult genandcompile = GeneratorCompilerUtil.genandcompile(fileName, options, Path.of(dirCompilazione), Path.of(dirCompilazione));
					if (!genandcompile.getSuccess()) {
						if (failOnError) fail();
						failures.add(fileName);
						System.err.println("failing for " + fileName);
					}
				}
				else {
					System.err.println("skipping " + fileName);						
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		walk.close();
		assertTrue(failures.size() + ": " + failures.toString(), failures.isEmpty());
	}

}
