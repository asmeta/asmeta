package org.asmeta.xt.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
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
		execValidationSetLogger(new File(scenarioPath), scenarioPath, coverage);
	}

	public static void execValidationSetLogger(File file, String scenarioPath, boolean coverage) throws Exception {
		setLogger();
		execValidation(file, scenarioPath, coverage);
	}

	public static void setLogger() {
		BasicConfigurator.configure();
		Logger log = Logger.getRootLogger();
		log.setLevel(Level.INFO);
		Enumeration<?> it = log.getAllAppenders();
		while(it.hasMoreElements()) {
			((Appender)it.nextElement()).setLayout(new PatternLayout());
		}
	}

	public static void execValidation(File file, String scenarioPath, boolean coverage) throws Exception {
		AsmetaFromAvallaBuilder builder;
		Simulator sim = null;
		File[] listFile;
		ArrayList<String> all_rules = new ArrayList<String>();
		if (file.isDirectory()) { // get all rules covered by a set of
									// scenarios into directory
			listFile = file.listFiles();
			for (int i = 0; i < listFile.length; i++)
				if (listFile[i].isFile()) {
					builder = new AsmetaFromAvallaBuilder(listFile[i].getPath());
					builder.save();
					sim = Simulator.createSimulator(builder.tempAsmPath);
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
			builder = new AsmetaFromAvallaBuilder(scenarioPath);
			builder.save();
			sim = Simulator.createSimulator(builder.tempAsmPath);
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
