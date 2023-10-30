package org.asmeta.modeladvisor;

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import asmeta.cli.AsmetaCLI;

public class AsmetaMA_CLI extends AsmetaCLI {
	
	@Option(name = "-kf", usage = "keep the NuSMV file.")
	private boolean keepFile;

	@Option(name = "-mp1", usage = "No inconsistent update is ever performed")
	private boolean execMp1;

	@Option(name = "-mp2", usage = "Every conditional rule must be complete")
	private boolean execMp2;

	@Option(name = "-mp3", usage = "Every rule can eventually fire")
	private boolean execMp3;

	@Option(name = "-mp4", usage = "No assignment is always trivial")
	private boolean execMp4;

	@Option(name = "-mp5", usage = "For every domain element e there exists a location which has value e")
	private boolean execMp5;

	@Option(name = "-mp6", usage = "Every controlled function can take any value in its co-domain")
	private boolean execMp6;

	@Option(name = "-mp7", usage = "Every controlled location is updated and every location is read")
	private boolean execMp7;

	@Option(name = "-mpAll", usage = "Execute all the metaproperties")
	private boolean execMpAll;

	@Option(name = "-logCex", usage = "Log counterexamples")
	private boolean logCex;
	
	@Option(name = "-smvSimpl", usage = "Use the simplification facility of AsmetaSMV")
	private boolean useAsmetaSMVsimpl;

	@Option(name="-nusmvpath", usage =  "nusmv path", required = false)
	public String nusmvPath;
	

	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.OFF);
		new AsmetaMA_CLI().run(args);
	}

	// it is useless now
//	public void doMain(String[] args) throws Exception {
//		CmdLineParser parser = new CmdLineParser(this);
//
//		// if you have a wider console, you could increase the value;
//		// here 80 is also the default
//		parser.setUsageWidth(80);
//
//		try {
//			// parse the arguments.
//			parser.parseArgument(args);
//
//			// you can parse additional arguments if you want.
//			// parser.parseArgument("more","args");
//
//			// after parsing arguments, you should check
//			// if enough arguments are given.
//			if (arguments.isEmpty())
//				throw new CmdLineException("No argument is given");
//
//		} catch (CmdLineException e) {
//			// if there's a problem in the command line,
//			// you'll get this exception. this will report
//			// an error message.
//			System.err.println(e.getMessage());
//			System.err.println("java -jar " + ASMETA_MA_JAR_NAME
//					+ " [options...] "+ASMETAL_FILE_NAME_ASM);
//			// print the list of available options
//			parser.printUsage(System.err);
//			System.err.println();
//
//			// print option sample. This is useful sometime
//			System.err.println("  Example: java -jar " + ASMETA_MA_JAR_NAME
//					+ parser.printExample(ALL) + " " + ASMETAL_FILE_NAME_ASM );
//			return;
//		}
//
//		// this will redirect the output to the specified output
//		// System.out.println(out);
//		//asmetalFileName = arguments.get(0);
//	}

	@Override
	protected RunCLIResult runWith(File asmFile) throws Exception {
		AsmetaMA asmetaMA = AsmetaMA.buildAsmetaMA(asmFile.getAbsolutePath());
		if (execMpAll) {
			execMp1 = execMp2 = execMp3 = execMp4 = execMp5 = execMp6 = execMp7 = true;
		}
		AsmetaSMVOptions.keepNuSMVfile = keepFile;
		AsmetaMA.LOG_COUNTEREXAMPLES = logCex;
		AsmetaMA.USE_ASMETASMV_SIMPL = useAsmetaSMVsimpl;
		asmetaMA.setMetapropertiesExecution(execMp1, execMp2, execMp3, execMp4,
				execMp5, execMp6, execMp7);
		// set the path if needed
		if (nusmvPath!= null)
			AsmetaSMVOptions.setSolverPath(nusmvPath);		
		Map<String, Boolean> result = asmetaMA.runCheck();
		// check if any meta property is violated
		if (result.containsValue(Boolean.FALSE)) return RunCLIResult.WARNING;
		else return RunCLIResult.SUCCESS;
	}	
}
