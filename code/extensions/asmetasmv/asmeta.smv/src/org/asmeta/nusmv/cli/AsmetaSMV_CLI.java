package org.asmeta.nusmv.cli;

import java.io.File;

import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.asmeta.nusmv.util.Util;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.cli.ASMFileFilter;
import asmeta.cli.AsmetaCLI;

/**
 * The Class AsmetaSMV_CLI
 * The class must be public. Otherwise the jar does not work.
 * 
 */
public class AsmetaSMV_CLI extends AsmetaCLI {
	@Option(name = "-en", usage = "execute the NuSMV model after the translation")
	public boolean executeNuSMVmodel;

	@Option(name = "-dcx", usage = "disable computation of counterexamples")
	public boolean disableCounterExampleComputation;

	@Option(name = "-ns", usage = "do not simplify the boolean conditions in NuSMV code")
	public boolean doNotSimplify;
	
	@Option(name = "-nc", usage = "do not add the check on integer enum")
	public boolean doNotCheckConcrete;

	@Option(name = "-kf", usage = "keep the NuSMV file. To be used with the -en option enabled. If the -en option is not enabled, the option -kf is enabled by default.")
	public boolean keepFile;
	
	@Option(name="-nusmvpath", usage =  "nusmv path", required = false)
	public String nusmvPath;
	
	@Option(name = "-xm", usage = "use NuXMV instead of NuSMV")
	public boolean useNuXmv;
	
	@Option(name = "-xt", usage = "use NuXMV with time functions")
	public boolean useNuXmvTime;
	

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AsmetaSMV_CLI as = new AsmetaSMV_CLI();
		as.run(args);
	}

	@Override
	protected RunCLIResult runWith(File asmFile) throws Exception {
		ASMFileFilter filter = new ASMFileFilter();
		if (! filter.accept(asmFile)) {
			throw new CmdLineException("Error:  " + asmFile.toString() + " is not an asm file.");
		}
		AsmetaSMVOptions.simplify = !doNotSimplify;
		AsmetaSMVOptions.setCheckConcrete(!doNotCheckConcrete);
		AsmetaSMVOptions.setRunNuSMV(executeNuSMVmodel); //execute the NuSMV model after the mapping
		AsmetaSMVOptions.setPrintCounterExample(!disableCounterExampleComputation);
		AsmetaSMVOptions.keepNuSMVfile = !executeNuSMVmodel || keepFile;
		AsmetaSMVOptions.setPrintNuSMVoutput(AsmetaSMVOptions.isRunNuSMV()); //stampa l'ouput solo se il modello viene eseguito (ovviamente)
		AsmetaSMVOptions.setUseNuXmv(useNuXmv);
		AsmetaSMVOptions.setUseNuXmvTime(useNuXmvTime);		
		Util.setPath(asmFile.getPath());//percorso relativo del file asm comprensivo del nome del file
		Util.setDir(asmFile.getParent());//percorso relativo del file senza il nome del file
		// set the path if needed
		if (nusmvPath!= null)
			AsmetaSMVOptions.setSolverPath(nusmvPath);		
		if(asmFile != null) {
			AsmetaSMV asmetaSMV = new AsmetaSMV(asmFile, !doNotSimplify, executeNuSMVmodel, !doNotCheckConcrete, useNuXmv,useNuXmvTime);
			asmetaSMV.translation();
			asmetaSMV.createNuSMVfile();
			if(executeNuSMVmodel) {
				asmetaSMV.executeNuSMV();
			}
			return RunCLIResult.SUCCESS;
		}
		return RunCLIResult.FATAL;
	}
}
