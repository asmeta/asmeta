package org.asmeta.asm2code;

import java.io.File;
import java.io.IOException;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import com.google.inject.Inject;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class GeneratorAndParserTest {

	String path = "../asm_examples/examples/";
	// String arduinoMakeFolder =
	// "D:/modeling-mars-codegen/eclipse/arduinoPlugin/tools/make/";
	String pathToArduinoProject = "ArduinoGeneratedProject/";

	@Inject
	private HeaderGenerator underTest;

	@Test
	public void testcards() throws Exception {
		testAsm(path + "cards.asm");
	}

	@Test
	public void testcarController() throws Exception {
		testAsm(path + "carController.asm");
	}

	@Test
	public void testdarts() throws Exception {
		testAsm(path + "darts.asm");
	}

	@Test
	public void testparking() throws Exception {
		testAsm(path + "parking.asm");
	}

	/**
	 * 
	 * @param specName: filename.asm
	 * @throws IOException
	 * @throws Exception
	 */
	void testAsm(String specName) throws IOException, Exception {
		//
		// UNZIP PROJECT
		//
		String zippedProject = "test/arduino_project.zip";
		String destination = pathToArduinoProject;
		// unzipProject(zippedProject, destination);

		final Asm model = readAsmAndTranslate(specName);

		System.out.println("Processo di compilazione del progetto Arduino da " + model.getName() + " saltato");

		//
		// COMPILE .INO FILE (ARDUINO TOOLCHAIN)
		//
		// TODO this works only with arduino UNO, not tested with other versions
		/*
		 * ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " +
		 * pathToArduinoProject + "arduino_project/Release && " + arduinoMakeFolder +
		 * "make all"); builder.redirectErrorStream(true); Process p = builder.start();
		 * BufferedReader r = new BufferedReader(new
		 * InputStreamReader(p.getInputStream())); String line;
		 * 
		 * boolean notCompiled = false; while (true) { line = r.readLine(); if (line ==
		 * null) { break; } if (line.contains("Error") || line.contains("error")) {
		 * notCompiled = true; } System.out.println(line); }
		 * 
		 * // make a copy of the .ino file inside "generatedexamples" folder for
		 * inspection String pathFile = "generatedexamples/" + model.getName() + ".ino";
		 * File compiledFile = new File(pathToArduinoProject +
		 * "arduino_project/arduino_project.ino"); File generatedExamplesFile = new
		 * File(pathFile); if (generatedExamplesFile.exists() &&
		 * !generatedExamplesFile.isDirectory()) generatedExamplesFile.delete();
		 * Files.copy(compiledFile.toPath(), new File(pathFile).toPath());
		 * 
		 * if (notCompiled) { throw new
		 * RuntimeException("Compiler found some errors in source code"); }
		 */
	}

	protected Asm readAsmAndTranslate(String asm) throws IOException, Exception {
		//parse the asm file
		File asmFile = new File(asm);
		assert asmFile.exists();
		System.out.println("Parsing " + asmFile.getName() + " in " + asm);

		final AsmCollection modelCollection = ASMParser.setUpReadAsm(asmFile);
		Asm model = modelCollection.getMain();
		
		//TODO: generate the .ino file

		// this.underTest.setWriterPath(pathToArduinoProject +
		// "arduino_project/arduino_project.ino");
		// this.underTest.doGenerate(_eResource, fsa);
		return model;
	}

	protected void unzipProject(String zipFilePath, String destFolder) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.extractAll(destFolder);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
}
