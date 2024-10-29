package org.asmeta.xt.validator;

import java.io.File;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.Value;
import org.asmeta.xt.validator.SimulatorWCov.BranchCovData;
import org.asmeta.xt.validator.SimulatorWCov.UpdateCovData;

import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * main class of AsmetaV
 *
 * @author garganti
 *
 */
public class AsmetaV {

	public static final String SCENARIO_EXTENSION = ".avalla";

	private static Logger logger = Logger.getLogger(AsmetaV.class);

	/**
	 * Exec validation.
	 *
	 * @param scenarioPath path of the file containing the scenario or directory
	 *                     containing all the scenarios
	 * @param coverage     compute also the coverage ?
	 * @return the list of scenarios that fail
	 * @throws Exception the exception
	 */
	public static List<String> execValidation(String scenarioPath, boolean coverage) throws Exception {
		AsmetaV asmetaV = new AsmetaV();
		File scenarioPathFile = new File(scenarioPath);
		if (!scenarioPathFile.exists())
			throw new RuntimeException("path " + scenarioPath + " does not exist");
		// disable lazy evaluation (it is not interactive)
		TermEvaluator.setAllowLazyEval(false);
		List<String> result = asmetaV.execValidation(scenarioPathFile, coverage);
		// ripristina il valore messo in precedenza
		TermEvaluator.recoverAllowLazyEval();
		return result;
	}

	private AsmetaV() {
	}

	/**
	 * Exec validation.
	 *
	 * @param scenarioPath file containing the scenario or directory containing all
	 *                     the scenarios
	 * @param coverage     the coverage
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private List<String> execValidation(File scenarioPath, boolean coverage) throws Exception {
		List<String> failedScenarios = new ArrayList<>();
		// get all rules covered by a set of string
		// contains also the name of the spec
		// TRUE -> it is covered , FALSE -> it is not
		Map<String, Boolean> allCoveredRules = new HashMap<>();
		ValidationResult result = null; // result of the last file validation performed
		// scenarios into directory
		if (scenarioPath.isDirectory()) {
			File[] listFile = scenarioPath.listFiles();
			for (int i = 0; i < listFile.length; i++) {
				File element = listFile[i];
				if (element.isFile() && element.getName().endsWith(SCENARIO_EXTENSION)) {
					String path = element.getPath();
					result = validateSingleFile(coverage, allCoveredRules, path);
					if (!result.isCheckSucceded()) {
						failedScenarios.add(path);
					}
				} else {
					logger.info(element.getName() + " is not a valid file!!");
				}
			}
		} else {
			if (!scenarioPath.getName().endsWith(SCENARIO_EXTENSION)) {
				throw new RuntimeException("invalid file, the validator works with " + SCENARIO_EXTENSION + " files");
			}
			// if the file is not a directory but a file
			result = validateSingleFile(coverage, allCoveredRules, scenarioPath.getCanonicalPath());
			if (!result.isCheckSucceded())
				failedScenarios.add(scenarioPath.getCanonicalPath());
		}
		if (coverage) { // print all covered rules and branch and update rule coverage info
			logger.info("\n** Coverage Info: **");
			logger.info("** covered rules: **");
			for (Entry<String, Boolean> rule : allCoveredRules.entrySet()) {
				if (Boolean.TRUE.equals(rule.getValue())) {
					logger.info(rule.getKey());
					if (result == null) {
						logger.info("-> no information about branch coverage and update rule coverage can be displayed");
					}else {
						BranchCovData branchData = result.getBranchData().get(rule.getKey().substring(rule.getKey().lastIndexOf(':') + 1));
						if (branchData.tot != 0) {
							float branchCoverage = ((float)branchData.coveredT.size()+branchData.coveredF.size())/(branchData.tot*2)*100;
							logger.info("-> branch coverage: " + branchCoverage + "%");
						}else {
							logger.info("-> branch coverage: 0% (no conditional rules to be covered)");
						}
						UpdateCovData updateData = result.getUpdateData().get(rule.getKey().substring(rule.getKey().lastIndexOf(':') + 1));
						if (updateData.tot != 0) {
							float updateCoverage = ((float)updateData.covered.size()/updateData.tot)*100;;
							logger.info("-> update rule coverage: " + updateCoverage + "%");
						}else {
							logger.info("-> update rule coverage: 0% (no update rules to be covered)");
						}
					}

				}
			}
			logger.info("** NOT covered rules: **");
			for (Entry<String, Boolean> rule : allCoveredRules.entrySet())
				if (Boolean.FALSE.equals(rule.getValue()))
					logger.info(rule.getKey());
			logger.info("");

		}
		// print a recap of the result
		if (failedScenarios.isEmpty())
			logger.info("validation terminated without errors");
		else
			logger.info("WARNING: some check failed");
		return failedScenarios;
	}

	/**
	 * Validate single file.
	 *
	 * @param coverage     if required
	 * @param coveredRules the covered rules (till now, once it is covered it is
	 *                     covered forever)
	 * @param path         scenario path
	 * @return the result of the validation, i.e. if the check have succeeded and
	 *         information about coverage (till now)
	 * @throws Exception the exception
	 */
	private ValidationResult validateSingleFile(boolean coverage, Map<String, Boolean> coveredRules, String path)
			throws Exception {
		assert path.endsWith(SCENARIO_EXTENSION) : " the validator works only with " + SCENARIO_EXTENSION + " files";
		logger.info("\n** Simulation " + path + " **\n");
		AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(path);
		builder.save();
		File tempAsmPath = builder.getTempAsmPath();
		logger.info("** temp ASMETA saved in " + tempAsmPath.getAbsolutePath() + " **\n");
		// create the simulator with the coverage
		Simulator sim;
		if (coverage) {
			sim = SimulatorWCov.createSimulatorWC(tempAsmPath.getPath());
			assert sim instanceof SimulatorWCov;
			assert RuleEvalWCov.coveredMacros != null;
		} else {
			sim = Simulator.createSimulator(tempAsmPath.getPath());
		}
		sim.setShuffleFlag(true);
		try {
			sim.runUntilEmpty();
			// sim.runUntilStepNeg();
		} catch (InvalidInvariantException iie) {
			AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
			logger.info("invariant violation found " + iie.getInvariant().getName() + " "
					+ tp.visit(iie.getInvariant().getBody()));
		}
		// check now the value of step
		//
		boolean check_succeded = false;
		for (Entry<Location, Value> cons : sim.getCurrentState().getContrLocs().entrySet()) {
			if (cons.getKey().toString().equals(StatementToStringBuffer.STEP_VAR)) {
				if (Integer.parseInt(cons.getValue().toString()) > 0)
					check_succeded = true;
				else
					logger.info("some checks failed");
				break;
			}
		}
		ValidationResult result = new ValidationResult();
		result.setCheckSucceded(check_succeded);
		if (coverage) { // for each scenario insert rules covered
						// into list if they aren't covered
			List<RuleDeclaration> ruleDeclaration = sim.getAsmModel().getBodySection().getRuleDeclaration();
			// for each rule which is declared in the current ASM
			ruleDeclaration.forEach(rd -> {
				String ruleName = rd.getName();
				String completeRuleName = getCompleteRuleName(rd);
//				// skip the artificial name of the
				if (!ruleName.equals(AsmetaPrinterForAvalla.R_MAIN)) {
					boolean rdIsCovered = false;
					// check if it is covered as it is present in the covered rules
					for(MacroDeclaration md : RuleEvalWCov.coveredMacros) {
						String asmName = md.getAsmBody().getAsm().getName();
						String completeMacroName = getCompleteRuleName(md);
						if (completeMacroName.equals(completeRuleName)
								&& asmName.contains(AsmetaFromAvallaBuilder.TEMP_ASMETA_V)) {
							coveredRules.put(completeRuleName, Boolean.TRUE);
							rdIsCovered = true;
							break;
						}
					}
					// if it is not covered put it in the false part
					if (!rdIsCovered) {
						if (!coveredRules.containsKey(completeRuleName))
							coveredRules.put(completeRuleName, Boolean.FALSE);
					}
				}
			});
			// now put also all the extra rules (not belonging to this ASM)
			for (MacroDeclaration md : RuleEvalWCov.coveredMacros) {
				String asmName = md.getAsmBody().getAsm().getName();
				if (!asmName.contains(AsmetaFromAvallaBuilder.TEMP_ASMETA_V))
					coveredRules.put(getCompleteRuleName(md), Boolean.TRUE);
			}
			// update the result with the data about branch coverage
			Map<String, BranchCovData> branchData = ((SimulatorWCov)sim).getCoveredBranches();
			logger.debug("Covered conditional rules (branch coverage):");
			for (Entry<String, BranchCovData> entry : branchData.entrySet()) {
				logger.debug(entry.getKey() + " " + entry.getValue());
			}
			result.setBranchData(branchData);
			// update the result with the data about update rule coverage
			Map<String, UpdateCovData> updateData = ((SimulatorWCov)sim).getCoveredUpdateRules();
			logger.debug("Covered update rules:");
			for (Entry<String, UpdateCovData> entry : updateData.entrySet()) {
				logger.debug(entry.getKey() + " " + entry.getValue());
			}
			result.setUpdateData(updateData);
		}
		return result;
	}
	
	/**
	 * Compute the complete name of a rule declaration.
	 *
	 * @param rd the rule declaration
	 * @return the complete name as asm_name::rule_signature
	 */
	private String getCompleteRuleName(RuleDeclaration rd) {
		String asmName = rd.getAsmBody().getAsm().getName();
		if (asmName.contains(AsmetaFromAvallaBuilder.TEMP_ASMETA_V)) { // For asm built from avalla
			asmName = asmName.substring(0, asmName.indexOf(AsmetaFromAvallaBuilder.TEMP_ASMETA_V));
		}else if (asmName.startsWith("_")){ // For imported asm
			asmName = asmName.substring(1, asmName.lastIndexOf('_'));
		}
		String signature = rd.getName() + "(";
		for(VariableTerm variable : rd.getVariable()) {
			signature += variable.getDomain().getName() + ",";
		}
		if(signature.endsWith(",")) 
			signature = signature.substring(0, signature.length() - 1);
		signature += ")";
		return asmName + "::" + signature;
	}
	
}