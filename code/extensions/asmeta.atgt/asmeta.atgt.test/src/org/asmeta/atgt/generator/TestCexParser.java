package org.asmeta.atgt.generator;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestCexParser {

	@Test
	public void test() throws FileNotFoundException, IOException {
		String path = "cexes\\cex_mvm1.txt";
		TestGenerationWithNuSMV.parseCounterExample(new BufferedReader(new FileReader(path)));
	}

	
	
	
}
