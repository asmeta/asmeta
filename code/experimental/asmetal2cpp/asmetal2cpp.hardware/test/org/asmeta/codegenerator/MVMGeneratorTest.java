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

public class MVMGeneratorTest {

	@Test
	public void MVM_ino_test() throws IOException,Exception {
		String destinationFolder = "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\";
		String asmFilePath = destinationFolder + "Ventilatore000.asm";
		String jsonFilePath = destinationFolder + "Ventilatore000.u2c";
		CarSystemGeneratorTest.test_ino(asmFilePath,jsonFilePath, destinationFolder);
	}

}
