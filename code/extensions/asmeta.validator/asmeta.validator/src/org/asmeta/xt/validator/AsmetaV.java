package org.asmeta.xt.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;

/**
 * main class of AsmetaV
 * 
 * @author garganti
 *
 */
public class AsmetaV {


	public static void execValidation(String scenarioPath, boolean coverage) throws Exception {
		setLogger();
		AsmetaV asmetaV = new AsmetaV();
		asmetaV.execValidation(new File(scenarioPath), coverage);
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
	 * 
	 * @param scenarioPath file containing the scenario or directory containign all
	 *                     the scenarios
	 * @param coverage
	 * @throws Exception
	 */
	private void execValidation(File scenarioPath, boolean coverage) throws Exception {
		// get all rules covered by a set of string
		Set<String> all_rules = new HashSet<String>();
		// scenarios into directory
		if (scenarioPath.isDirectory()) {
			File[] listFile = scenarioPath.listFiles();
			for (File element : listFile)
				if (element.isFile()) {
					String path = element.getPath();
					validateSingleFile(coverage, all_rules, path);
				} else {
					System.out.println(element.getName() + " is not a file!!");
				}
		} else { // if the file is not a directory but a file
			validateSingleFile(coverage, all_rules, scenarioPath.getAbsolutePath());
		}
		if (coverage) { // print all covered rules
			System.out.println("\n** Coverage Info: **\n");
			for (String rule : all_rules)
				System.out.println(rule);
		}
	}

	/**
	 * @param coverage
	 * @param coveredRules
	 * @param path
	 * @throws Exception
	 */
	private void validateSingleFile(boolean coverage, Set<String> coveredRules, String path) throws Exception {
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
		if (coverage) { // for each scenario insert rules covered
						// into list if they aren't covered
			for (String element : RuleEvaluator.getCoveredMacro())
				coveredRules.add(element);
		}
	}

}
