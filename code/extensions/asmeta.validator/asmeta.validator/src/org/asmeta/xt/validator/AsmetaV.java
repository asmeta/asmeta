package org.asmeta.xt.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
		execValidationSetLogger(new File(scenarioPath), coverage);
	}

	public static void execValidationSetLogger(File file, boolean coverage) throws Exception {
		setLogger();
		execValidation(file, coverage);
	}
	
	public static void setLogger() {
		BasicConfigurator.configure();
		Logger log = Logger.getRootLogger();
		log.setLevel(Level.INFO);
		Enumeration<?> it = log.getAllAppenders();

		// 03/03/2021 - Andrea
		// Delete all the appenders of the root logger except a single ConsoleAppender
		if (Collections.list(log.getAllAppenders()).stream().filter(x -> (x instanceof ConsoleAppender)).count() > 1) {

			java.util.Optional app = Collections.list(log.getAllAppenders()).stream()
					.filter(x -> (x instanceof ConsoleAppender)).findFirst();

			if (app != null) {
				ConsoleAppender consoleApp = (ConsoleAppender)app.get();
				ConsoleAppender newConsoleApp = new ConsoleAppender(consoleApp.getLayout(),
						consoleApp.getTarget());
				newConsoleApp.setLayout(new org.apache.log4j.PatternLayout());
				log.removeAllAppenders();
				log.addAppender(newConsoleApp);
			}
		}
		
		/*while(it.hasMoreElements()) {
			((Appender)it.nextElement()).setLayout(new PatternLayout());
		}*/
		System.out.println();
	}
	/**
	 * 
	 * @param scenarioPath file containing the scenario or directory containign all the scenarios
	 * @param coverage
	 * @throws Exception
	 */
	public static void execValidation(File scenarioPath, boolean coverage) throws Exception {
		AsmetaFromAvallaBuilder builder;
		Simulator sim = null;
		File[] listFile;
		ArrayList<String> all_rules = new ArrayList<String>();
		// get all rules covered by a set of
		// scenarios into directory
		if (scenarioPath.isDirectory()) { 
			listFile = scenarioPath.listFiles();
			for (int i = 0; i < listFile.length; i++)
				if (listFile[i].isFile()) {
					builder = new AsmetaFromAvallaBuilder(listFile[i].getPath());
					builder.save();
					sim = Simulator.createSimulator(builder.getTempAsmPath());
					sim.setShuffleFlag(true);
					if (coverage) {
						RuleEvaluator.COMPUTE_COVERAGE = true;
					}
					System.out.println("\n** Simulation **\n");
					sim.runUntilEmpty();
					if (coverage) // for each scenario insert rules covered
									// into list if they aren't covered
						for (int j = 0; j < RuleEvaluator.getCoveredMacro()
								.size(); j++)
							if (!all_rules.contains(RuleEvaluator
									.getCoveredMacro().get(j)))
								all_rules.add(RuleEvaluator
										.getCoveredMacro().get(j));
				} else
					System.out.println(listFile[i].getName()
							+ " is not a file!!");
			if (coverage) { // print all covered rules
				System.out.println("\n** Coverage Info: **\n");
				for (int j = 0; j < all_rules.size(); j++)
					System.out.println(all_rules.get(j));
			}
		} else { // if the file is not a directory but a file
			builder = new AsmetaFromAvallaBuilder(scenarioPath.getAbsolutePath());
			builder.save();
			sim = Simulator.createSimulator(builder.getTempAsmPath());
			sim.setShuffleFlag(true);
			if (coverage) {
				RuleEvaluator.COMPUTE_COVERAGE = true;
			}
			System.out.println("\n** Simulation **\n");
			sim.runUntilEmpty();
			if (coverage) {
				System.out.println("\n** Coverage Info: **\n");
				RuleEvaluator.printCoveredMacro(System.out);
			}
		}
	}
}
