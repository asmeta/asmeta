package org.asmeta.atgt.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.asmeta.atgt.ConvertToAsmeta;
import org.asmeta.atgt.generator.AsmTestGenerator.MBTCoverage;
import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.AsmetaSMVOptions;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;

/**
 * Generates the test from different sources: - Only ASM, by using the Avalla
 * language - FSM and ASM
 * 
 * @author Andrea Bombarda - 2020
 */
public class GenerateTestsFromFSM {

	private NuSMVtestGenerator generator;
	private String inputASMName;
	private String asmPath;
	private AsmCoverage ct;

//	public GenerateTestsFromFSM(String inputASMName, List<CriteriaEnum> criteria) throws Exception {
//		String asmName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_"));
//		String fileOutputName = asmName + ".txt";
//		PrintWriter fout = new PrintWriter(new FileWriter(fileOutputName));
//		String file = new File(inputASMName + ".txt").getAbsolutePath();
//		System.out.println(file);
//		fout.println(getFSMWithTestsFromFSM(file, asmName, criteria));
//		fout.close();
//		//return fileOutputName;
//	}
//
//	public String GenerateTestsFromFSM(String inputASMName, String fsmPath, String asmPath, List<CriteriaEnum> criteria)
//			throws Exception {
//		String fileOutputName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".txt";
//		PrintWriter fout = new PrintWriter(new FileWriter(fileOutputName));
//		fout.println(getFSMWithTestsFromFSMAndASM(new File(fsmPath).getAbsolutePath(),
//				new File(asmPath).getAbsolutePath(), criteria));
//		fout.close();
//		return fileOutputName;
//	}
//
//	/**
//	 * generate a set of FSM from an ASM using different criteria and producing
//	 * several avalla scenario files
//	 * 
//	 * @param inputASMName the name of the model
//	 * @param asmPath      the path of the asm
//	 * @param criteria     the list of criteria
//	 * 
//	 * @return the set of paths of the avalla files
//	 */
//	public String[] saveFSMWithAvallaTests(String inputASMName, String asmPath, boolean useMonitoring, List<CriteriaEnum> criteria)
//			throws Exception {
//		String fileOutputName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".avalla";
//		return getFSMWithTestsFromFSMAndASMfromAvalla(new File(fileOutputName).getAbsolutePath(),
//				new File(asmPath).getAbsolutePath(), useMonitoring, criteria);
//	}
//	
//	/**
//	 * generate a set of FSM from an ASM using different criteria and producing
//	 * several avalla scenario files
//	 * 
//	 * @param inputASMName 		the name of the model
//	 * @param asmPath      		the path of the asm
//	 * @param criteria     		the list of criteria
//	 * @param destinationPath	the destination path for the abstract tests 
//	 * 
//	 * @return the set of paths of the avalla files
//	 */
//	public String[] saveFSMWithAvallaTests(String inputASMName, String asmPath, boolean useMonitoring, 
//			List<CriteriaEnum> criteria, String destinationPath) throws Exception {
//		String fileOutputName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".avalla";
//		return getFSMWithTestsFromFSMAndASMfromAvalla(new File(fileOutputName).getAbsolutePath(),
//				new File(asmPath).getAbsolutePath(), useMonitoring, criteria, destinationPath);
//	}
//	
//	/**
//	 * generate a set of FSM from an ASM using different criteria and producing
//	 * several avalla scenario files
//	 * 
//	 * @param inputASMName    the name of the model
//	 * @param asmPath         the path of the asm
//	 * @param criteria        the list of criteria
//	 * @param destinationPath the destination path for the abstract tests
//	 * 
//	 */
//	public GenerateTestsFromFSM(String inputASMName, String asmPath, boolean useMonitoring, List<CriteriaEnum> criteria,
//			String destinationPath) throws Exception {
//		String fileOutputName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".avalla";
//		TestGenerationWithNuSMV.useLTLandBMC = true;
//		NuSMVtestGenerator.removeUnaskedChanges = false;
//		NuSMVtestGenerator.removeUnChangedControlles = false;
//		ConverterCounterExample.IncludeUnchangedVariables = true;
//		AsmTestSuite result = new NuSMVtestGenerator(asmPath, useMonitoring)
//				.generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria), Integer.MAX_VALUE, ".*");
//		SaveResults.saveResults(result, new File(asmPath).getAbsolutePath(),
//				Collections.singletonList(FormatsEnum.AVALLA), fileOutputName, destinationPath);
//		// String[] tests = SaveResults.getAvallaResults(result, fsmPath, asmPath,
//		// destinationPath);
//	}

	/**
	 * generate a set of FSM from an ASM using different criteria and producing
	 * several avalla scenario files
	 * 
	 * @param inputASMName    the name of the model
	 * @param asmPath         the path of the asm
	 * @param criteria        the list of criteria
	 * @param destinationPath the destination path for the abstract tests
	 * 
	 */
	public GenerateTestsFromFSM(String inputASMName, String asmPath, boolean useMonitoring,
			boolean removeUnaskedChanges, boolean removeUnChangedControlles, List<CriteriaEnum> criteria) throws Exception {
		TestGenerationWithNuSMV.useLTLandBMC = true;
		NuSMVtestGenerator.removeUnaskedChanges = removeUnaskedChanges;
		NuSMVtestGenerator.removeUnChangedControlles = removeUnChangedControlles;
		ConverterCounterExample.IncludeUnchangedVariables = true;
		AsmetaSMV.BMCLength = 100;
		AsmetaSMVOptions.useCoi = true;
		//
		this.inputASMName = inputASMName;
		this.asmPath = new File(asmPath).getAbsolutePath();
		// build the generator
		generator = new NuSMVtestGenerator(asmPath, useMonitoring);
		// build the tp tree and do not set to verify
		List<AsmCoverageBuilder> covBuilders = CriteriaEnum.getCoverageCriteria(criteria);
		generator.buildTPTree(new MBTCoverage(covBuilders),0,"");
	}
	
	public void setAllTpToBeCovered() {
		this.generator.setAllTpToBeCovered();
	}

	public void generate(String destinationPath, String regexTp, int maxNTP) throws Exception {
//		String fileOutputName = inputASMName + "_"
//				+ criteria.stream().map(n -> n.getAbbrvName()).collect(Collectors.joining("_")) + ".avalla";
		generator.quequeTPs(maxNTP, regexTp);
		AsmTestSuite result = generator.generateTests();
		SaveResults.saveResults(result, asmPath, Collections.singletonList(FormatsEnum.AVALLA), "", destinationPath);
	}

	public void generate(List<CriteriaEnum> criteria,  String destinationPath) throws Exception{

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
				.generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria), Integer.MAX_VALUE, ".*");
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
	 * Creates a set of Avalla files representing the several tested scenarios. The
	 * scenarios are generated using LTL and BMC
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
				.generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria), Integer.MAX_VALUE, ".*");
		String[] tests = SaveResults.getAvallaResults(result, fsmPath, asmPath);
		// Returns the Avalla file paths
		return tests;
	}

	/**
	 * Creates a set of Avalla files representing the several tested scenarios. The
	 * scenarios are generated using LTL and BMC
	 * 
	 * @param fsmPath         the path of the FSM
	 * @param asmPath         the path of the ASM
	 * @param criteria        the list of criteria
	 * @param destinationPath the destination path for the avalla files
	 * 
	 * @return the avalla file paths
	 */
	protected String[] getFSMWithTestsFromFSMAndASMfromAvalla(String fsmPath, String asmPath, boolean useMonitoring,
			List<CriteriaEnum> criteria, String destinationPath) throws Exception {
		TestGenerationWithNuSMV.useLTLandBMC = true;
		AsmTestSuite result = new NuSMVtestGenerator(asmPath, useMonitoring)
				.generateAbstractTests(CriteriaEnum.getCoverageCriteria(criteria), Integer.MAX_VALUE, ".*");
		String[] tests = SaveResults.getAvallaResults(result, fsmPath, asmPath, destinationPath);
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
