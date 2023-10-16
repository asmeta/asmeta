package asmeta.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.asmeta.parser.ASMParser;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;

public abstract class AsmetaCLI {

	@Option(name = "-log", usage = "log4j configuration file")
	protected String logConfFile;

	@Option(name = "-debug", usage = "set log4j level to debug")
	protected boolean debug = false;

	@Option(name = "-exit", usage = "use system.exit at the end")
	protected boolean exit = false;

	@Argument
	private List<String> arguments = new ArrayList<String>();

	/**
	 * executes the application with the arguments args MUST be the last action of
	 * the CLI
	 * 
	 * @param args
	 */
	protected final void run(String... args) {
		CmdLineParser parser = null;
		RunCLIResult runresult;
		try {
			parser = new CmdLineParser(this);
			parser.parseArgument(args);
			if (arguments.isEmpty())
				throw new CmdLineException("No file is given" + Arrays.toString(args));
			String asmPath = arguments.get(0);
			if (asmPath.startsWith("/") || asmPath.startsWith("\\")) {
				// TODO
			}
			if (logConfFile != null) {
				LogManager.resetConfiguration();
				PropertyConfigurator.configure(logConfFile);
			} else if (System.getProperty("log4j.configuration") == null) {
				BasicConfigurator.configure();
				Logger log = Logger.getRootLogger();
				if (debug) {
					log.setLevel(Level.DEBUG);
				} else {
					log.setLevel(Level.INFO);
				}
				// get the default console appender
				Appender app = (Appender) log.getAllAppenders().nextElement();
				// set the layout if necessary
				Layout log4jlayout = app.getLayout();
				if (log4jlayout instanceof PatternLayout) {
					((PatternLayout) log4jlayout).setConversionPattern("%m%n");
				}
			}
			File f = new File(asmPath);
			if (f.exists() && (f.isFile() || (f.isDirectory()))) {
				runresult = runWith(f);
			} else {
				throw new CmdLineException("Error:  file " + f.toString() + " does not exist!");
			}
		} catch (CmdLineException e) {
			// handling of wrong arguments
			System.err.println(e.getMessage());
			String jarName = getJar();
			System.err.println("java -jar " + jarName + " [options...] " + getExampleArgument());
			// print the list of available options
			parser.printUsage(System.err);
			System.err.println();
			// print option sample. This is useful some time
			System.err.println("  Example: java -jar " + jarName + parser.printExample(ExampleMode.ALL) + " "
					+ getExampleArgument());
			runresult = RunCLIResult.FATAL;
		} catch (Exception e) {
			e.printStackTrace();
			runresult = RunCLIResult.FATAL;
		}
		// perform the system exit if required
		if (exit) {
			switch (runresult) {
			case SUCCESS:
				System.exit(0);
			case FATAL:
				System.exit(1);
			case WARNING:
				System.exit(2);
			}

		}
	}

	protected String getExampleArgument() {
		return "file" + getExtension();
	}

	protected String getExtension() {
		return ASMParser.ASM_EXTENSION;
	}

	// return the name of the JAR. it assumes that the name of the jar is equal to the name of the class
	// or it has a _CLI appended
	private String getJar() {
		String jarName = this.getClass().getSimpleName();
		jarName = jarName.replace("_CLI", "");
		return jarName + ".jar";
	}

	/**
	 * specific method for subclasses: run the application with the file
	 * 
	 * @param asmFile
	 * @throws Exception
	 */
	abstract protected RunCLIResult runWith(File asmFile) throws Exception;

	public enum RunCLIResult {
		SUCCESS, // everything went well
		WARNING, // some possible errors has been found
		FATAL // the analisys could not be completed
	}

}
