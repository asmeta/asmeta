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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestForCoverageShortICTSS {

	public static String base_dir = "../asmeta.evotest/asmeta.evotest.experiments/data/ase-exp";

	//@Test
	//public void test1file() throws Exception {
	//	String avallaTotest = base_dir + "/results/run1/atgttests/Lift/testRG_r_Main_TTRG2.avalla";
	//	MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
	//	var res = executor.computeMutationScore(avallaTotest);
//		System.out.println(res);
//	}

	// run mutation for all the avalla files for ASE
	// CESAR
	@Test
	public void testAseExperiments() throws IOException {
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.simulator").setLevel(Level.OFF);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
		String timeStamp = LocalDateTime.now().format(formatter);
		BufferedWriter out = new BufferedWriter(new FileWriter("muationresults"+timeStamp+".txt"));
		String csvName = "mutationResults" + timeStamp + ".csv";
		FileWriter csvWriter = new FileWriter(csvName);
		csvWriter.append("File,Mutator,Killed,Mutants\n");
		csvWriter.close();
		Path base = Path.of(base_dir);
		assertTrue(Files.exists(base));
		// now walk from here
		Files.walk(base).forEach(avalla -> {
			if (avalla.toFile().toString().endsWith(".avalla")) {
				//Next line uncomment if uses macOS, TODO make paths OS independent. 
				//correctLoadSpec(avalla);
				System.out.println(avalla.toFile().toString());
				HashMap<String, Entry<Integer, Integer>> value = new HashMap<>();
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
						// return 0 0 
						//Map.Entry<Integer, Integer> v = new AbstractMap.SimpleEntry<Integer, Integer>(0,0);
						//value = new HashMap<String, Entry<Integer, Integer>>();
						//value.put(avalla.toFile().toString(), v);
						//TODO Shall we add the case to csv? or cases where it's no possible to compute mutation score are dropped?
					}
					out.write("\t" + value + "\n");
					exportToCsv(csvName,avalla.toFile().toString(), value);
					
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

	//For MacOS, solves the path to load the models. Shoudn't be necessary TODO make path OS independent
	private void correctLoadSpec(Path avalla) {
		try {
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(avalla), charset);
			content = content.replaceAll("load ./data/ase-exp/", "load ../../../../");
			content = content.replace("\\", "/");
			System.err.println(content);
			Files.write(avalla, content.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void exportToCsv(String file, String avallaFile, HashMap<String, Entry<Integer, Integer>> values) {
		//String resultCSV = "mutationResults.csv";
		
		try (FileWriter writer = new FileWriter(file,true)){
			for (Map.Entry<String, Map.Entry<Integer, Integer>> entry : values.entrySet()) {
				String mutantName = entry.getKey();
				Integer nKilled = entry.getValue().getKey();
				Integer nMutants = entry.getValue().getValue();
				
				writer.append(avallaFile)
					  .append(",")		
					  .append(mutantName)
					  .append(",")
					  .append(nKilled.toString())
					  .append(",")
					  .append(nMutants.toString()).append("\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error generating csv file: " + e.getMessage());
		}
	}

}
