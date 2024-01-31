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
		// TODO - this ignores the debug option - see the super method
//		// now set the levels
		Logger.getRootLogger().setLevel(Level.OFF);
		Logger.getLogger(Simulator.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.INFO);
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
				return RunCLIResult.SUCCESS;
			} else {
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
