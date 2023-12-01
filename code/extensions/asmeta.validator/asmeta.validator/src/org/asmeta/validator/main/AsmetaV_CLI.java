package org.asmeta.validator.main;

import java.io.File;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaV;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.cli.AsmetaCLI;

/**
 * main class of AsmetaV It can take also a directory, in that case
 *
 * @author garganti
 *
 */
public class AsmetaV_CLI extends AsmetaCLI {

	@Option(name = "-cov", usage = "collect coverage info")
	private boolean coverage;

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) {
		AsmetaV_CLI asmetaV_CLI = new AsmetaV_CLI();
		asmetaV_CLI.run(args);
	}

	@Override
	protected void setUpLogger() {
		//super.setUpLogger();
//		// reset
		Logger.getRootLogger().getLoggerRepository().resetConfiguration();
		// create appender
		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		//String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		String PATTERN = "%m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.ALL);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(console);
//		// now set the levels
		// TODO - this ignored the debug option - see the super mathod
		Logger.getRootLogger().setLevel(Level.OFF);
		Logger.getLogger(Simulator.class).setLevel(Level.INFO);
	}

	@Override
	protected RunCLIResult runWith(File file) throws CmdLineException {
		// after parsing arguments, you should check
		// if enough arguments are given.
		// String scriptPath = file.getAbsolutePath();
		// use relative path instead to allow the use under windows
		String scriptPath = file.getPath();
		try {
			List<String> failingScenerios = AsmetaV.execValidation(scriptPath, coverage);
			if (failingScenerios.isEmpty()) {
				System.out.println("validation successfully completed");
				return RunCLIResult.SUCCESS;
			} else {
				System.out.println("some check failed");
				return RunCLIResult.WARNING;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("cirtical error");
		return RunCLIResult.FATAL;
	}

	@Override
	protected String getExtension() {
		return AsmetaV.SCENARIO_EXTENSION;
	}

	@Override
	protected String getExampleArgument() {
		return super.getExampleArgument() + " OR dir (containing all the avalla)";
	}

}
