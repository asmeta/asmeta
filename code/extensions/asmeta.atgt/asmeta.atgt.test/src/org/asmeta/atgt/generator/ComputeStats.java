package org.asmeta.atgt.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ComputeStats {

	public final String sep = " & ", endl=" \\\\\n";

	@Test
	public void computeStatistics() {
		System.out.println(listf(".").toString().replace(", [",endl));
	}

	/**
	 * Search for all files in that directory, and returns the statistics
	 * for all of them
	 */
	public List<String> listf(String directory) {
		List<String> statList = new ArrayList<>();

		// get all the files from a directory
		File[] fList = new File(directory).listFiles();

		for (File file : fList) {
//			System.out.println(file.getAbsolutePath());
			if (file.isFile() && file.getAbsolutePath().endsWith("protest")) {
//				System.out.println(file.getAbsolutePath());
				try {
//					statList.add(directory+sep+file.getName()+sep+computeStatistics(readFile(file.getAbsolutePath())));
					String methods = file.getName();
					methods = methods.substring(methods.indexOf("["), methods.indexOf("]"));
					methods = methods.replace("BASIC_RULE", "B");
					methods = methods.replace("RULE_UPDATE", "U");
					methods = methods.replace("MCDC", "M");
					methods = methods.replace("COMPLETE_RULE", "C");
					methods = methods.replace(", ", "");
					statList.add(methods+sep+computeStatistics(readFile(file.getAbsolutePath())));
				} catch (Exception e) {e.printStackTrace();}
			} else if (file.isDirectory()) {
				statList.addAll(listf(file.getAbsolutePath()));
			}
		}

		return statList;
	}

	static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

	/** computes the statistics from ProTest */
	public String computeStatistics(String fileContent) {
		String[] lines = fileContent.split("\n");
		int nseq = 0, total = 0, min = -1, max = -1;
		for (String line : lines) {
//			System.out.println(line);
			if (line.isEmpty()) {
				continue;
			}
			if (line.contains("Information")) {
				break;
			}
			int steps = line.split(" ").length;
			//System.out.println(steps);
			total += steps;
			if (min == -1 || steps < min) {
				min = steps;
			}
			if (max == -1 || steps > max) {
				max = steps;
			}
			nseq++;
		}
		String res = nseq + sep + min + sep + max + sep + total + sep + String.format("%,.2f", (double) total / (double) nseq).replace(",",".");
		//System.out.println(res);
		return res;
	}
}
