package org.asmeta.asm2code.main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import asmeta.structure.Asm;

/**
 * descrizione: test di corretta generazione di codice (.h e .cpp) da
 * modello Asm input: cartella modelli asm, cartella destinazione codice c++
 */
@RunWith(Parameterized.class)
public class GeneratorCompilerTest {
	// path of the examples
	static private String examplesPath = "examples";
	// destination folder of compiled files
	static private String destinationFolder = "generatedExamples/";
	// options for the compiler
	private TranslatorOptions options = new TranslatorOptions(true, true, true,false);

	@Parameterized.Parameter(0)
	public String fileToTest;
	// list of file to test in the examplesPath
	private static ArrayList<File> listOfFileToTest;

	@Parameters(name = "{index}: model: {0}")
	public static Collection<Object[]> data() {
		if (listOfFileToTest == null)
			listf(examplesPath, listOfFileToTest = new ArrayList<File>(), 1);

		Collection<Object[]> c = new ArrayList<Object[]>();
		for (File f : listOfFileToTest)
			c.add(new String[] { f.getPath() });
		return c;
	}

	@Test
	public void testAll() throws IOException, Exception {
		if (test(fileToTest, options).success)
			System.out.println("Read, parsed and compiled correctly, yeah!");
	}

	// return files in directory, level is the depth
	static void listf(String directoryName, ArrayList<File> allAsmFiles, int level) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(AsmToCGenerator.Ext)) {
				allAsmFiles.add(file);
			} else if (file.isDirectory() && level > 0) {
				listf(file.getPath(), allAsmFiles, --level);
			}
		}
	}

	static public CompileResult test(String asmspec, TranslatorOptions userOptions) throws Exception {
		// parse using the asmeta parser
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		System.out.println("\n\n=== " + name + " ===================");

		final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
		assertNotNull("The parsing process gave some errors", model);
		HeaderGenerator hGenerator = new HeaderGenerator();
		CppGenerator cppGenerator = new CppGenerator();

		File cppFile = new File(destinationFolder +  name + CppGenerator.Ext);
		File hFile = new File(destinationFolder + name + HeaderGenerator.Ext);

		// write .cpp and .h
		try {
			cppGenerator.options = userOptions;
			hGenerator.options = userOptions;
			cppGenerator.generate(model, cppFile.getAbsolutePath());
			hGenerator.generate(model, hFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return new CompileResult(false, e.getMessage());
		}
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		CompileResult result = CppCompiler.compile(cppFile.getName(), destinationFolder, true, false);
		assertTrue(result.toString(), result.success);
		return result;
	}

}
