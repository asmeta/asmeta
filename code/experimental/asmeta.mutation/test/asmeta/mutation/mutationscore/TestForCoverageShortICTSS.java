package asmeta.mutation.mutationscore;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestForCoverageShortICTSS {

	public static String base_dir = "../asmeta.evotest/asmeta.evotest.experiments/data/ase-exp";

	@Test
	public void test1file() throws Exception {
		String avallaTotest = base_dir + "\\results\\run1\\atgttests\\Lift\\testRG_r_Main_TTRG2.avalla";
		MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
		var res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
	}

	// run mutation for all the avalla files for ASE
	// CESAR
	@Test
	public void testAseExperiments() throws IOException {
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.simulator").setLevel(Level.OFF);
		BufferedWriter out = new BufferedWriter(new FileWriter("muationresults.txt"));
		Path base = Path.of(base_dir);
		assertTrue(Files.exists(base));
		// now walk from here
		Files.walk(base).forEach(avalla -> {
			if (avalla.toFile().toString().endsWith(".avalla")) {
				Entry<Integer, Integer> value;
				try {
					out.write(avalla.toFile().toString());
//        		try {
//					Files.walk(d).forEach(avalla ->{
//						// look for avalla tests
//						if (avalla.toFile().toString().endsWith(".avalla")) {
//							System.out.println(avalla.toFile().toString());
//							// the path of the asmeta is wrong
//							// for instance:
//							// load ./src\main\resources\models\Ascensore.asm
//							// must become
//							// load ../../../../src ... etc
//							//correctLoadSpec(avalla);
//						
					MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
					try {
						value = executor.computeMutationScore(avalla.toFile().toString());
					} catch (Exception e) {
						value = new  AbstractMap.SimpleEntry<Integer, Integer>(0,0);
					}
					out.write("\t" + value + "\n");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (Error e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		});
		out.close();
	}

	private void correctLoadSpec(Path avalla) {
		try {
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(avalla), charset);
			content = content.replaceAll("load ./data\\ase-exp\\", "load ../../../../");
			System.err.println(content);
			Files.write(avalla, content.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
