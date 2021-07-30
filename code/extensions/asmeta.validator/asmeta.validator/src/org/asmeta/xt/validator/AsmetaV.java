package org.asmeta.xt.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
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
		setLogger();
		AsmetaV asmetaV = new AsmetaV();
		return asmetaV.execValidation(new File(scenarioPath), coverage);
	}

	private AsmetaV() {

	}

	private static void setLogger() {
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.ALL);
		// BasicConfigurator.configure();
		Logger log = Logger.getRootLogger();
		// log.setLevel(Level.INFO);
		Enumeration<?> it = log.getAllAppenders();

		// 03/03/2021 - Andrea
		// Delete all the appenders of the root logger except a single ConsoleAppender
		if (Collections.list(log.getAllAppenders()).stream().filter(x -> (x instanceof ConsoleAppender)).count() > 1) {

			java.util.Optional app = Collections.list(log.getAllAppenders()).stream()
					.filter(x -> (x instanceof ConsoleAppender)).findFirst();

			if (app != null) {
				ConsoleAppender consoleApp = (ConsoleAppender) app.get();
				ConsoleAppender newConsoleApp = new ConsoleAppender(consoleApp.getLayout(), consoleApp.getTarget());
				newConsoleApp.setLayout(new org.apache.log4j.PatternLayout());
				log.removeAllAppenders();
				log.addAppender(newConsoleApp);
			}
		}

		/*
		 * while(it.hasMoreElements()) { ((Appender)it.nextElement()).setLayout(new
		 * PatternLayout()); }
		 */
	}

	/**
	 * Exec validation.
	 *
	 * @param scenarioPath file containing the scenario or directory containign all
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
				if (element.isFile()) {
					String path = element.getPath();
					if (!validateSingleFile(coverage, all_rules, path)) {
						failedScenarios.add(path);
					}
				} else {
					System.out.println(element.getName() + " is not a file!!");
				}
		} else { // if the file is not a directory but a file
			if (!validateSingleFile(coverage, all_rules, scenarioPath.getAbsolutePath()))
				failedScenarios.add(scenarioPath.getCanonicalPath());
		}
		if (coverage) { // print all covered rules
			System.out.println("\n** Coverage Info: **\n");
			for (String rule : all_rules)
				System.out.println(rule);
		}
		return failedScenarios;
	}

	/**
	 * @param coverage
	 * @param coveredRules
	 * @param path
	 * @return true if the check have succeded
	 * @throws Exception
	 */
	private boolean validateSingleFile(boolean coverage, Set<String> coveredRules, String path) throws Exception {
		System.out.println("\n** Simulation " + path + " **\n");
		AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(path);
		builder.save();
		Simulator sim = Simulator.createSimulator(builder.getTempAsmPath().getPath());
		sim.setShuffleFlag(true);
		if (coverage) {
			RuleEvaluator.COMPUTE_COVERAGE = true;
		}
		try {
			sim.runUntilEmpty();
		} catch (InvalidInvariantException iie) {
			AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
			System.out.println("invariant violation found " + iie.getInvariant().getName() + " "
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
					System.out.println("some checks failed");
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
