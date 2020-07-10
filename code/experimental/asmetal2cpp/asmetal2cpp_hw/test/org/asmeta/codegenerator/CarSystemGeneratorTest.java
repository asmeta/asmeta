package org.asmeta.codegenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

public class CarSystemGeneratorTest {

	@Test
	public void CarSystem004_hw_test() throws IOException,Exception {
		String destinationFolder = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem004/";
		String jsonFilePath = destinationFolder + "CarSystem004main.u2c";
		String asmFilePath = destinationFolder + "CarSystem004main.asm";
		test_hw(asmFilePath,jsonFilePath,destinationFolder);
	}
	
	@Test
	public void CarSystem004_ino_test() throws IOException,Exception {
		String destinationFolder = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem004/";
		String asmFilePath = destinationFolder + "CarSystem004main.asm";
		String jsonFilePath = destinationFolder + "CarSystem004main.u2c";
		test_ino(asmFilePath,jsonFilePath, destinationFolder);
	}
	
	static void test_hw(String asmFilePath, String jsonFilePath, String destinationFolder) throws IOException, Exception {
		File asmFile = new File(asmFilePath);
		File u2cFile = new File(jsonFilePath);
		File hwFile = new File(destinationFolder + asmFile.getName().replace(AsmToCGenerator.Ext, HWIntegrationGenerator.Ext));
		assertTrue(asmFile.exists() && u2cFile.exists());
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			assertTrue("Configuration file not correct", config.isValid());
			HWIntegrationGenerator hwGen = new HWIntegrationGenerator(config);
			//final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
			AsmCollection model = ASMParser.setUpReadAsm(asmFile);
			hwGen.generate(model, hwFile.getPath());
			assertTrue(hwFile.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	};
	
	static void test_ino(String asmFilePath, String jsonFilePath, String destinationFolder) throws IOException, Exception{
		File asmFile = new File(asmFilePath);
		File u2cFile = new File(jsonFilePath);
		File inoFile = new File(destinationFolder + asmFile.getName().replace(AsmToCGenerator.Ext, InoGenerator.Ext));
		assertTrue(asmFile.exists() && u2cFile.exists());
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
			InoGenerator inoGen = new InoGenerator(config);
			inoGen.generate(model, inoFile.getPath());
			assertTrue(inoFile.exists());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
