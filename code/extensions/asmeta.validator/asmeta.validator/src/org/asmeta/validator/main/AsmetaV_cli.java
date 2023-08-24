package org.asmeta.validator.main;

import java.io.File;

import org.asmeta.xt.validator.AsmetaV;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.cli.AsmetaCLI;

/**
 * main class of AsmetaV
 * It can take also a directory, in that case 
 * 
 * @author garganti
 *
 */
public class AsmetaV_cli extends AsmetaCLI {

	@Option(name = "-cov", usage = "collect coverage info")
	private boolean coverage;

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) {
		runAsmetaV(args);
	}

	public static void runAsmetaV(String[] args) {
		new AsmetaV_cli().run(args);
	}

	@Override
	protected void runWith(File file) throws CmdLineException {
		// after parsing arguments, you should check
		// if enough arguments are given.
		// String scriptPath = file.getAbsolutePath();
		// use relative path instead to allow the use under windows
		String scriptPath = file.getPath();
		try {
			AsmetaV.execValidation(scriptPath, coverage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getExtension() {
		return AsmetaV.SCENARIO_EXTENSION;
	}
	
	protected String getExampleArgument() {
		return super.getExampleArgument() + " dir (containing all the avalla)";
	}

}
