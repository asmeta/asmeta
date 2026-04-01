package org.asmeta.codegenerator;

import java.io.IOException;

import org.junit.Test;

public class MVMGeneratorTest {

	@Test
	public void MVM_ino_test() throws IOException,Exception {
		String destinationFolder = "D:\\AgHome\\progettidaSVNGIT\\mvm-asmeta\\VentilatoreASM\\";
		String asmFilePath = destinationFolder + "Ventilatore000.asm";
		String jsonFilePath = destinationFolder + "Ventilatore000.u2c";
		CarSystemGeneratorTest.test_ino(asmFilePath,jsonFilePath, destinationFolder);
	}

}
