package org.asmeta.refinement;

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.asmeta.parser.ASMParser;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Command line version of the Asm Refinement Prover 
 *
 */
public class AsmRefProver {
	private static final String ASMETA_MA_JAR_NAME = "AsmRefProver.jar";

	//@Option(name = "-ep", usage = "execute the proof")
	private boolean execProof = true;

	@Option(name = "-sp", usage = "save the proof")
	private boolean saveProof = false;

	@Option(name = "-log", usage = "show the SMT context")
	private boolean log = false;

	@Argument
	private List<String> arguments = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		new AsmRefProver().doMain(args);
	}

	public void doMain(String[] args) throws Exception {
		CmdLineParser parser = new CmdLineParser(this);
		try {
			// parse the arguments.
			parser.parseArgument(args);

			// you can parse additional arguments if you want.
			// parser.parseArgument("more","args");

			// after parsing arguments, you should check
			// if enough arguments are given.
			if(arguments.isEmpty()) {
				throw new CmdLineException("No argument is given");
			}
			else if(arguments.size() != 2) {
				throw new CmdLineException("Two arguments must be given (the abstract and the refined ASM models)");
			}

		} catch (CmdLineException e) {
			// if there's a problem in the command line,
			// you'll get this exception. this will report
			// an error message.
			System.err.println(e.getMessage());
			System.err.println("java -jar " + ASMETA_MA_JAR_NAME + " [options...] abstractModel.asm refinedModel.asm");
			// print the list of available options
			parser.printUsage(System.err);
			System.err.println();

			// print option sample. This is useful sometime
			System.err.println("Example: java -jar " + ASMETA_MA_JAR_NAME + parser.printExample(ALL) + " abstractModel.asm refinedModel.asm");

			return;
		}
		String abstractModelName = arguments.get(0);
		String refinedModelName = arguments.get(1);
		RefinementProof rp = new RefinementProof(abstractModelName, refinedModelName);
		String proofFileName = "proof_" + new File(abstractModelName).getName().replaceAll(ASMParser.asmExtension, "") + "_" + new File(refinedModelName).getName().replaceAll(ASMParser.asmExtension, "") + ".txt" ;
		if(saveProof) {
			File proofFile = new File(proofFileName);
			if(proofFile.exists()) {
				proofFile.delete();
			}
			FileAppender fileAppender = new FileAppender(new PatternLayout("%m\n"), proofFileName);
			if(!log) {
				Logger.getRootLogger().removeAllAppenders();
			}
			Logger.getRootLogger().addAppender(fileAppender);			
		}
		else {
			if(!log) {
				rp.shutdownConsole();
			}
		}
		rp.buildProof();
	}
}