package org.asmeta.codegenerator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import asmeta.structure.Asm;

/*
 * Descrizione: con questo test, dato in input il modello asm trovato in examplesAsmPath, 
 * genero automaticamente il file .u2c (json) e lo salvo nella stessa cartella del modello asm
 */

@RunWith(Parameterized.class)
public class JsonGeneratorTest {
	// path of the asm examples
	static private String examplesAsmPath = "../asm_examples/asmetal2cpp/asmetal2cpp_hw/";
	
	@Parameterized.Parameter(0)
	public String fileToTest;
	// list of file to test in the examplesPath
	private static ArrayList<File> listOfFileToTest;

	@Parameters(name = "{index}: model: {0}")
	public static Collection<Object[]> data() {
		if (listOfFileToTest == null)
			listf(examplesAsmPath, listOfFileToTest = new ArrayList<File>(), 100);
		Collection<Object[]> c = new ArrayList<Object[]>();
		for (File f : listOfFileToTest)
			c.add(new String[] { f.getPath() });
		return c;
	}

	@Test
	public void testAll() throws IOException, Exception {
		test(fileToTest);
	}

	static void test(String asmFilePath) throws IOException, Exception {
		File asmFile = new File(asmFilePath);
		File jsonFile = new File(asmFile.getPath().replace(ASMParser.ASM_EXTENSION, JsonGenerator.Ext));
		assert asmFile.exists();
		try {
			Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
			JsonGenerator jsonGen = new JsonGenerator(ArduinoVersion.UNO);
			jsonGen.generate(model, jsonFile.getPath());
			assertTrue(jsonFile.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}

	// return files in directory, level is the depth
	static void listf(String directoryName, ArrayList<File> allAsmFiles, int level) {
		File directory = new File(directoryName);
		assert directory.exists();
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile() && file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
				allAsmFiles.add(file);
			} else if (file.isDirectory() && level > 0) {
				listf(file.getPath(), allAsmFiles, --level);
			}
		}
	}

}
