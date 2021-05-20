package org.asmeta.atgt.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.testseqexport.toAvalla;
import atgt.testseqexport.toXML;

public class SaveResults {

	/**
	 * 
	 * @param result the already generated test suite
	 * @param asmetaSpecPath the asmeta file path, the folder containing the .asm file, under which an abstractestsXXX folder is generated, containing the test cases: one file per sequence
	 * @param formats XML, Avalla
	 */
	public static void saveResults(AsmTestSuite result, String asmetaSpecPath, Collection<FormatsEnum> formats, String config) {
		saveResults(result, asmetaSpecPath, formats, "", config);
	}
	
	/**
	 * 
	 * @param result the already generated test suite
	 * @param asmetaSpecPath the asmeta file path, the folder containing the .asm file, under which an abstractestsXXX folder is generated, containing the test cases: one file per sequence
	 * @param formats XML, Avalla
	 */
	public static void saveResults(AsmTestSuite result, String asmetaSpecPath, Collection<FormatsEnum> formats, String foldersuffix, String config) {
		if (formats==null || formats.size()==0) {
			System.err.println("No formats specified");
			return;
		}
		String parent = new File(asmetaSpecPath).getParent(); //.uptoSegment(config.asmetaSpecPath.segmentCount()-1);
		if (parent==null) parent = ".";
		//System.out.println("Parent: "+parent);		
		// find new dir where to put files
		String dirPath = Paths.get(parent,"abstractests"+foldersuffix).toString();
		// find new dir where to put files
		File testFile = new File(dirPath);
		int i = 1;
		while (testFile.exists()) {
			testFile = new File(dirPath + i++);
		}
		testFile.mkdir();
		//System.out.println("saving tests to " + testFile.getAbsolutePath());
		// save to output files
		String allSequences = ""; // used for ProTest, to create a single file with all the sequences
		for (AsmTestSequence tc : result.getTests()) {
			try {
				if (formats.contains(FormatsEnum.XML)) {
					File ftc = new File(testFile, tc.getName().replace("@","") + ".xml");
					PrintStream dst;
					dst = new PrintStream(new FileOutputStream(ftc));
					dst.println((new toXML().export(tc)));
					dst.close();
				} 
				if (formats.contains(FormatsEnum.AVALLA)) {
					File ftc = new File(testFile, tc.getName().replace("@","") + ".avalla");	
					// get the relative path if possible
					Path asm_to_import = null;
					try {
						asm_to_import = ftc.toPath().getParent().relativize(new File(asmetaSpecPath).toPath());
					} catch(IllegalArgumentException  ie) {
						asm_to_import = new File(asmetaSpecPath).toPath().normalize();
					}	
					new toAvalla(ftc,tc,asm_to_import.toString()).save();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!"".equals(allSequences)) try {
			File ftc = new File(testFile, config+".protest");
			PrintWriter fout = new PrintWriter(new FileWriter(ftc));
			fout.println(allSequences);
			fout.println("Information of Sequences :\n" + 
					"2-way");
			for (i=0; i<result.getTests().size(); i++) fout.print("0 "); // for statistics purposes
			fout.println();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Warning: Avalla is not supported */
	public static String getResults(AsmTestSuite result, String asmetaSpecPath, FormatsEnum format) {
		if (format==null) {
			throw new RuntimeException("No formats specified");
		}
		String parent = new File(asmetaSpecPath).getParent();
		if (parent==null) parent = ".";
		//System.out.println("Parent: "+parent);		
		String res = "Set of Sequences :\n";
		
		for (AsmTestSequence tc : result.getTests()) {
			switch (format) {
			case XML: res+= new toXML().export(tc)+"\n"; break;
			case AVALLA: throw new RuntimeException("Avalla not yet supported as string (only saveToFile");
			default: throw new RuntimeException("Format not yet supported as string)");
			}
		}
		res+= "Information of Sequences :\n2-way\n";
		for (int i=0; i<result.getTests().size(); i++) res+="0 "; // for statistics purposes
		return res;
	}
	
	/** Results in Avalla format */
	public static String[] getAvallaResults(AsmTestSuite result, String baseAvallaName, String asmName) {
		String[] avallaPath = new String[result.getTests().size()]; 
		int counter = 0;
		
		for (AsmTestSequence tc : result.getTests()) {
			// Create the new avalla file
			new toAvalla(new File(baseAvallaName.split("\\.avalla")[0] + counter + ".avalla"), tc, asmName).save();
			avallaPath[counter] = baseAvallaName.split("\\.avalla")[0] + counter + ".avalla";
			
			counter++;
		}
		
		return avallaPath;
	}
	
	static String getRelativePath(String base, String path) {
		
		System.out.println("Base dir: "+base);
		String relative = new File(base.toString()).toURI().relativize(new File(path.toString()).toURI()).getPath();
		return relative;
	}
	
	
	// ***** UTILS *******
	public static String toAttachedString(List<?> v) {
		return v.toString().replace(" ","").replace("_","").replace(",","_").replace("[","").replace("]","").replace("{","").replace("}","").replace("(","").replace(")","");
	}
	public static String toStringForFile(boolean computeCoverage, List<CriteriaEnum> criteria, List<FormatsEnum> formats) {
		return computeCoverage+"_"+toAttachedString(criteria)+"_"+toAttachedString(formats);
	}
}
