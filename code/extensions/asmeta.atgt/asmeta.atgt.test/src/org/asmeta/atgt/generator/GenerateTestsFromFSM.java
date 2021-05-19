package org.asmeta.atgt.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.asmeta.atgt.ConvertToAsmeta;

import atgt.coverage.AsmTestSuite;

/**
 * Generates the test from different sources: - Only ASM, by using the Avalla
 * language - FSM and ASM
 * 
 * @author Andrea Bombarda - 2020
 */
public class GenerateTestsFromFSM {

	public String saveFSMWithTests(String inputASMName, List<CriteriaEnum> criteria) throws Exception {
		String asmName = inputASMName + "_"
				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_"));
		String fileOutputName = asmName + ".txt";
		PrintWriter fout = new PrintWriter(new FileWriter(fileOutputName));
		String file = new File(inputASMName + ".txt").getAbsolutePath();
		System.out.println(file);
		fout.println(getFSMWithTestsFromFSM(file, asmName, criteria));
		fout.close();
		return fileOutputName;
	}

	public String saveFSMWithTests(String inputASMName, String fsmPath, String asmPath, List<CriteriaEnum> criteria)
			throws Exception {
		String fileOutputName = inputASMName + "_"
				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".txt";
		PrintWriter fout = new PrintWriter(new FileWriter(fileOutputName));
		fout.println(getFSMWithTestsFromFSMAndASM(new File(fsmPath).getAbsolutePath(),
				new File(asmPath).getAbsolutePath(), criteria));
		fout.close();
		return fileOutputName;
	}

	/**
	 * generate a set of FSM from an ASM using different criteria and producing
	 * several avalla scenario files
	 * 
	 * @param inputASMName the name of the model
	 * @param asmPath      the path of the asm
	 * @param criteria     the list of criteria
	 * 
	 * @return the set of paths of the avalla files
	 */
	public String[] saveFSMWithAvallaTests(String inputASMName, String asmPath, boolean useMonitoring, List<CriteriaEnum> criteria)
			throws Exception {
		String fileOutputName = inputASMName + "_"
				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".avalla";
		return getFSMWithTestsFromFSMAndASMfromAvalla(new File(fileOutputName).getAbsolutePath(),
				new File(asmPath).getAbsolutePath(), useMonitoring, criteria);
	}

	/**
	 * converts automatically the fsm into an asm, and returns the path to that ASM
	 * file
	 * 
	 * @param fsmPath the path of the fsm
	 * @param asmName the name of the asm
	 * 
	 * @return the name of the asm
	 */
	protected String convertFsmToAsm(String fsmPath, String asmName) throws Exception {
		String newAsmName = asmName;
		if (asmName.contains("//")) {
			// remove the first part of the path from the name of the ASM
			newAsmName = newAsmName.split("//")[newAsmName.split("//").length - 1];
		}
		String asm = new ConvertToAsmeta().getASMETAFlatFromFSM(fsmPath, newAsmName);
		String asmFile = fsmPath.replace("\\", "/").substring(0, fsmPath.lastIndexOf("\\")) + "\\" + newAsmName
				+ ".asm";
		PrintWriter fout = new PrintWriter(new FileWriter(asmFile));
		fout.println(asm);
		fout.close();
		System.out.println(new File(asmFile).getAbsolutePath());
		return asmFile;
	}

	/**
	 * it does not create the ASM automatically, just creates the FSM with tests
	 * (given the FSM transition table, not the tests)
	 */
	protected String getFSMWithTestsFromFSMAndASM(String fsmPath, String asmPath, List<CriteriaEnum> criteria)
			throws Exception {
		AsmTestSuite result = new NuSMVtestGenerator(asmPath, false)
				.generateAbstractTests( CriteriaEnum.getCoverageCriteria(criteria),Integer.MAX_VALUE, ".*");
		String tests = SaveResults.getResults(result, asmPath, FormatsEnum.AVALLA);
		BufferedReader fin = new BufferedReader(new FileReader(fsmPath));
		String line = "", res = "";
		while ((line = fin.readLine()) != null) {
			if (line.startsWith("Set of Sequences :"))
				break;
			res += line + "\n";
		}
		fin.close();
		res += tests;
		return res;
	}

	/**
	 * Creates a set of Avalla files representing the several tested scenarios. The scenarios are generated using LTL and BMC
	 * 
	 * @param fsmPath  the path of the FSM
	 * @param asmPath  the path of the ASM
	 * @param criteria the list of criteria
	 * 
	 * @return the avalla file paths
	 */
	protected String[] getFSMWithTestsFromFSMAndASMfromAvalla(String fsmPath, String asmPath, boolean useMonitoring, 
			List<CriteriaEnum> criteria) throws Exception {
		TestGenerationWithNuSMV.useLTLandBMC = true;
		AsmTestSuite result = new NuSMVtestGenerator(asmPath, useMonitoring)
				.generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria),Integer.MAX_VALUE, ".*");
		String[] tests = SaveResults.getAvallaResults(result, fsmPath, asmPath);
		// Returns the Avalla file paths
		return tests;
	}

	/**
	 * Automatically creates the FSM with tests, passing by the creation of the ASM
	 * as well Warning: do not use if you have a custom ASM!!
	 * 
	 * This function gets a FSM, creates the corresponding ASM and generate a new
	 * version of the FSM containing the tests generated exploiting NuSMV on the ASM
	 * 
	 * @param fsmPath  the path of the FSM
	 * @param asmName  the name of the ASM
	 * @param criteria the list of criteria
	 * 
	 * @return the content of the new FSM file
	 */
	protected String getFSMWithTestsFromFSM(String fsmPath, String asmName, List<CriteriaEnum> criteria)
			throws Exception {
		String asmFile = convertFsmToAsm(fsmPath, asmName);
		return getFSMWithTestsFromFSMAndASM(fsmPath, asmFile, criteria);
	}
}
