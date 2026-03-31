package org.asmeta.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.asmeta.simulationUI.SimShell;

/**
 * @author Federico Rebucini
 */
public class TestRuntimeParser {

	public static void main(String[] args) {
		String model = "examples/ferrymanSimulator_raff1.asm";
		try {
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			
		}
		SimShell.main(args);
	}

}
