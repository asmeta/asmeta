package org.asmeta.codegenerator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import asmeta.structure.Asm;

/*
 * Descrizione: con questo test si verifica la corretta generazione del file hw 
 * dato in input il modello asm ed il file di configurazione .u2c
 * Mi aspetto in output il file _HW.cpp che salvo in destinationFolder
 * NOTA: per questo test il file .u2c e` nella stessa directory del modello asm 
 * che si trova in examplesAsmPath
 */

@RunWith(Parameterized.class)
public class GeneratorTest {
	
	// path of the asm examples
	//static private String examplesAsmPath = "../asm_examples/asmetal2cpp/asmetal2cpp_hw/";
	static private String examplesAsmPath = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/";
	// destination folder of compiled files
	static private String destinationFolder = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/";

	@Parameterized.Parameter(0)
	public String fileToTest = examplesAsmPath;
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
		File u2cFile = new File(asmFilePath.replace(ASMParser.ASM_EXTENSION, JsonGenerator.Ext));
		File hwFile = new File(destinationFolder + asmFile.getName().replace(ASMParser.ASM_EXTENSION, HWIntegrationGenerator.Ext));
		assertTrue(asmFile.exists() && u2cFile.exists());
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			assertTrue("Configuration file not correct", config.isValid());
			HWIntegrationGenerator hwGen = new HWIntegrationGenerator(config);
			final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
			hwGen.generate(model, hwFile.getPath());
			assertTrue(hwFile.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}

	// return files in directory, level is the depth
	static void listf(String directoryName, ArrayList<File> allAsmFiles, int level) {
		File directory = new File(directoryName);
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
