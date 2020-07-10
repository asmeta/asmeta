import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;

import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.codegenerator.HWIntegrationGenerator;
import org.asmeta.codegenerator.InoGenerator;
import org.asmeta.codegenerator.JsonGenerator;
import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import asm2code_inoproject.asm2code;
import asmeta.structure.Asm;

/*
 * questo test intende valutare la correttezza delle seguenti operazioni
 * 1) unzip del file arduino_project.zip
 * 2) parsing del modello asm
 * 3) esecuzione make del progetto arduino unzippato
 * 4) verifica errori di compilazione
 * 5) copia del progetto arduino .ino creato nella cartella locale dei progetti generati
 */

/*
 * per ogni modello asm 
 * 1) parsing del modello
 * 2) generazione e scrittura file generati in destinationFolder
 * 3) copia dei file nella cartella del progetto arduino
 * 4) rinomino file .ino con nome del progetto arduino
 * 5) eseguo makefile
 * 6) stampa dei risultati
 * */

@RunWith(Parameterized.class)
public class GeneratorAndParserTest {
	// path of the examples
	static private String examplesPath = "../asm_examples/asmetal2cpp/asmetal2cpp_codegen/";
	// destination folder of arduino projects
	static private String destinationFolder = "ArduinoGeneratedProject/";
	// destination folder of arduino projects
	static private String ArduinoPrjPath = "../Erdurino/";
	// destination folder of arduino projects
	static private String ArduinoPrjName = "Erdurino";
	// make exe path of arduino plugin
	static private String arduinoMakeFolder = "C:/Users/Jey/eclipse/arduinoPlugin/tools/make/";

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
		test(fileToTest);
	}

	static void test(String specName) throws IOException, Exception {
		File asmFile = new File(specName);
		assert asmFile.exists();
		System.out.println("Parsing " + asmFile.getName() + " in " + specName);
		// 1)
		final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
		asm2code a2c;
		try {
			// 2)
			Gson gson = new Gson();
			File u2cFile = new File(
					destinationFolder + asmFile.getName().replace(AsmToCGenerator.Ext, JsonGenerator.Ext));
			if (!u2cFile.exists()) {
				JsonGenerator jsonGen = new JsonGenerator(ArduinoVersion.MEGA2560);
				jsonGen.generate(model, destinationFolder + u2cFile.getName());
			}
			JsonReader reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			assertTrue("Configurazione non valida", config.isValid());
			String modelName = asmFile.getName().replace(AsmToCGenerator.Ext, "");
			a2c = new asm2code(config);
			a2c.generateAll(model, destinationFolder, modelName);

			// copia dei file nella cartella del progetto arduino
			// &&
			// rinomino file ino con nome del progetto arduino

			Files.move(new File(destinationFolder + modelName + CppGenerator.Ext).toPath(),
					new File(ArduinoPrjPath + modelName + CppGenerator.Ext).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			Files.move(new File(destinationFolder + modelName + HeaderGenerator.Ext).toPath(),
					new File(ArduinoPrjPath + modelName + HeaderGenerator.Ext).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			Files.move(new File(destinationFolder + modelName + HWIntegrationGenerator.Ext).toPath(),
					new File(ArduinoPrjPath + modelName + HWIntegrationGenerator.Ext).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			Files.move(new File(destinationFolder + modelName + InoGenerator.Ext).toPath(),
					new File(ArduinoPrjPath + ArduinoPrjName + InoGenerator.Ext).toPath(),
					StandardCopyOption.REPLACE_EXISTING);

			// eseguo makefile

			// (only with arduino UNO, not tested with other versions)

			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
					"cd " + ArduinoPrjPath + "Release && " + arduinoMakeFolder + "make all");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			boolean notCompiled = false;
			String line = r.readLine();
			while (line != null) {
				if (line.contains("Error") || line.contains("error"))
					notCompiled = true;
				System.out.println(line);
				line = r.readLine();
			}
			//if (notCompiled) fail("Compiler found some errors in source code");

			// stampo dei risultati
			
			// elimino i file generati
			Files.deleteIfExists(new File(ArduinoPrjPath + modelName + CppGenerator.Ext).toPath());
			Files.deleteIfExists(new File(ArduinoPrjPath + modelName + HeaderGenerator.Ext).toPath());
			Files.deleteIfExists(new File(ArduinoPrjPath + modelName + HWIntegrationGenerator.Ext).toPath());
			Files.deleteIfExists(new File(ArduinoPrjPath + ArduinoPrjName + InoGenerator.Ext).toPath());
			

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
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
}
