package org.asmeta.validator.main;

import java.io.File;
import java.util.List;

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
public class AsmetaV_CLI extends AsmetaCLI {

	@Option(name = "-cov", usage = "collect coverage info")
	private boolean coverage;

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) {
		new AsmetaV_CLI().run(args);
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
			if (failingScenerios.isEmpty())
				return RunCLIResult.SUCCESS;
			else
				return RunCLIResult.WARNING;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
