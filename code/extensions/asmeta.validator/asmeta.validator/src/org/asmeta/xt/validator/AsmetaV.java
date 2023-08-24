package org.asmeta.xt.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.value.Value;

/**
 * main class of AsmetaV
 * 
 * @author garganti
 *
 */
public class AsmetaV {
	
	
	public static final String SCENARIO_EXTENSION = "avalla";
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
		return asmetaV.execValidation(new File(scenarioPath), coverage);
	}

	private AsmetaV(){}
	
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
		Set<String> all_rules = new HashSet<String>();
		// scenarios into directory
		if (scenarioPath.isDirectory()) {
			File[] listFile = scenarioPath.listFiles();
			for (File element : listFile)
				if (element.isFile() && element.getName().endsWith("." + SCENARIO_EXTENSION)) {
					String path = element.getPath();
					if (!validateSingleFile(coverage, all_rules, path)) {
						failedScenarios.add(path);
					}
				} else {
					logger.info(element.getName() + " is not a valid file!!");
				}
		} else {
			if (!scenarioPath.getName().endsWith("." + SCENARIO_EXTENSION)) {
				throw new RuntimeException("invalid file, the validator works with .avalla files");
			}
			// if the file is not a directory but a file
			if (!validateSingleFile(coverage, all_rules, scenarioPath.getAbsolutePath()))
				failedScenarios.add(scenarioPath.getCanonicalPath());
		}
		if (coverage) { // print all covered rules
			logger.info("\n** Coverage Info: **\n");
			for (String rule : all_rules)
				logger.info(rule);
		}
		return failedScenarios;
	}

	/**
	 * @param coverage
	 * @param coveredRules
	 * @param path
	 * @return true if the check have succeeded
	 * @throws Exception
	 */
	private boolean validateSingleFile(boolean coverage, Set<String> coveredRules, String path) throws Exception {
		assert path.endsWith(SCENARIO_EXTENSION) : " the validator works only with avalla files";		
		logger.info("\n** Simulation " + path + " **\n");
		AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(path);
		builder.save();
		File tempAsmPath = builder.getTempAsmPath();
		logger.info("** temp ASMETA saved in " + tempAsmPath.getAbsolutePath() + " **\n");
		Simulator sim = Simulator.createSimulator(tempAsmPath.getPath());
		sim.setShuffleFlag(true);
		if (coverage) {
			RuleEvaluator.COMPUTE_COVERAGE = true;
		}
		try {
			sim.runUntilEmpty();
		} catch (InvalidInvariantException iie) {
			AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
			logger.info("invariant violation found " + iie.getInvariant().getName() + " "
					+ tp.visit(iie.getInvariant().getBody()));
		}
		// check now the value of step
		//
		boolean check_succeded = false;
		for (Entry<Location, Value> cons : sim.getCurrentState().getContrLocs().entrySet()) {
			if (cons.getKey().toString().equals("step__")) {
				if (Integer.parseInt(cons.getValue().toString()) > 0)
					check_succeded = true;
				else
					logger.info("some checks failed");
				break;
			}
		}
		if (coverage) { // for each scenario insert rules covered
						// into list if they aren't covered
			for (String element : RuleEvaluator.getCoveredMacro())
				coveredRules.add(element);
		}
		return check_succeded;
	}

}
