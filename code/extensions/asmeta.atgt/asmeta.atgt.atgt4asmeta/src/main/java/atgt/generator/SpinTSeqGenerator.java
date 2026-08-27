/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.generator;

import static atgt.preferences.ATGToolPreferences.SPINOPTION;
import static atgt.preferences.ATGToolPreferences.SPIN_COMPILE_OPTION;
import static atgt.preferences.ATGToolPreferences.minusILowerCase;
import static atgt.preferences.ATGToolPreferences.minusIUpperCase;
import static tgtlib.preferences.TGLibPreferences.TIMEOUT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Vector;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.parser.trail.ParseException;
import atgt.parser.trail.TrailParser;
import atgt.specification.ASMSpecification;
import atgt.translator.ToSpinTranslatorVisitor;
import atgt.translator.TranslatorVisitor;
import extgt.spin.generator.SpinExecutionResult;
import extgt.spin.generator.SpinTestGenerator;
import tgtlib.generator.MCAnalysisResult;
import tgtlib.generator.MCExecutionResultReader;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.StringAndTPMCInput;
import tgtlib.preferences.CheckedPreference;
import tgtlib.preferences.SimplePreference;
import tgtlib.preferences.TGLibPreferences;
import tgtlib.util.CmdExecutor;
import tgtlib.util.SimpleCmdExecutor;

/**
 * Visitor per la generazione dei modelli con i casi di test derivati dalla
 * specifica. Per ogni TestCondition della specifica viene creato un modello in
 * SPIN con uno statement
 * 
 * per la verifica della condizione.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini, Sergio Galati
 */
public class SpinTSeqGenerator extends
		TransTSeqGenerator<StringAndTPMCInput<AsmTestCondition>> {

	/**
	 * the spec to be translated
	 */
	private ASMSpecification spec;

	/**
	 * Lo
	 * 
	 * <PRE>
	 * SpecificationVisitor
	 * </PRE>
	 * 
	 * usato per la traduzione della specifica nel linguaggio target.
	 */
	protected TranslatorVisitor visitor;

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(SpinTSeqGenerator.class);

	/** The delete temp files. */
	private boolean deleteTempFiles = false;

	/** La directory dove salvare i casi di test generati. */
	// protected String directory;
	/**
	 * Il prefisso per il nome del file per il modello SPIN.
	 */
	private static String filenameprefix = "spin_spec";

	/** Il parser per i risultati del test. */
	// protected TrailParser trailP;
	static private Runtime rt;

	// PREFRENCES
	// (working dir)
	// private static String workDir;
	/** The spin program. */
	private static String spinProgram; // where spin is
	// private static String timeCmd; // time command to get the time for
	// running a command
	/** The cc opt. */
	private static String cc, ccOpt; // c compiler and options

	/** The run opt. */
	// private static String runOpt;
	/** The filenamesuffix. */
	private static String filenamesuffix = ".spin";

	// initializing static resources
	/*
	 * (non-Javadoc)
	 * 
	 * @see extgt.spin.generator.TestSequenceGenerator#initResources()
	 */
	@Override
	public void initResources() {
		tempDir = tgtlib.preferences.Utility.getTempDirPref();
		// workDir =
		// atgt.preferences.ATGToolPreferences.GENERIC_PREFS.getValue(atgt.preferences.ATGToolPreferences.WORK_DIR);
		// workDir = atgtPref.getPref(TGToolPreferences.WORK_DIR);
		spinProgram = atgt.preferences.ATGToolPreferences.SPIN_PROGRAM
				.getValue();
		assert spinProgram.contains("spin") : "the spin command must contain \"spin\"";
		// timeCmd =
		// atgt.preferences.ATGToolPreferences.GENERIC_PREFS.getValue(atgt.preferences.ATGToolPreferences.TIME_CMD);
		// timeCmd = atgtPref.getPref(TGToolPreferences.TIME_CMD);
		cc = TGLibPreferences.CC.getValue();
		// TIMEOUT IS REQUIRED?
		if (TIMEOUT.isChecked())
			CmdExecutor.setTimeOut(Integer.parseInt(TIMEOUT.getValue()
					.toString()));

	}

	static {
		rt = Runtime.getRuntime();
	}

	/** The ctree. */
	private AsmCoverageTree ctree;

	/**
	 * Instantiates a new spin test generator.
	 * 
	 * @param pro
	 *            .getCoverages() the coverage tree necessary to compute the
	 *            cross coverage (Spin still provides)
	 * @param visitor
	 */
	public SpinTSeqGenerator(ASMSpecification specification,
			AsmCoverageTree coverages, ToSpinTranslatorVisitor visitor) {
		initResources();
		ctree = coverages;
		spec = specification;
		this.visitor = visitor;
	}

	/**
	 * Run and wait.
	 * 
	 * @param command
	 *            the command
	 * @param dir
	 *            the dir
	 *  stream
	 * @return the process
	 */
	private Process runAndWait(String command, File dir) {
		return runAndWait(command, dir, null);
	}

	/**
	 * Run and wait.
	 * 
	 * @param command
	 *            the command
	 * @param dir
	 *            the dir
	 * @param ps
	 *            the print stream : to put the output
	 * 
	 * @return the process
	 */
	private Process runAndWait(String command, File dir, PrintStream ps) {

		// Runtime rt = Runtime.getRuntime();

		Process pr = null;

		// final boolean readMore;

		logger.debug("executing " + command + " in dir " + dir.getPath()); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			pr = rt.exec(command, null, dir);
			logger.debug(command + " running in " + dir.getPath()); //$NON-NLS-1$
			//
			// aggiunto per non intasare il Buffer !!
			// funziona nel caso in cui ci sono problemi
			// con l'istruzione waitFor
			//
			if (ps != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						pr.getInputStream()));
				String s;
				while ((s = br.readLine()) != null) {
					ps.println(s);
				}
			}

		} catch (IOException t) {
			logger.debug("Exception: " + t);
			logger.debug("It can't execute " + command);
			return null;
		}
		try {
			// se non c'e' il print stream di output aspetta altrimenti tira
			// dritto
			if (ps == null) {
				logger.debug("waiting for " + command + " to complete");
				pr.waitFor();
				logger.debug(command + " completed");
			}
		} catch (InterruptedException t) {
			logger.debug(command + " interrupted");
			return null;
		}
		// flush the print stream
		// ATTENZIONE: non chiudere il ps altrimenti il resto non viene
		// visualizzato !!
		if (ps != null) {
			logger.debug("flushing the print stream");
			ps.flush();
		}
		return pr;
	}

	/**
	 * run spin and put the result in a File.
	 * 
	 * @param specString
	 *            the spec string
	 * @param tc
	 *            the tc
	 * 
	 * @return the MC execution result
	 *  stream
	 * @throws ModelCheckerExecutionException
	 *             the model checker execution exception
	 */
	@Override
	public MCExecutionResultReader runModelChecker(
			StringAndTPMCInput<AsmTestCondition> promelaIn)
			throws ModelCheckerExecutionException {

		/***********************************************************************
		 * FASE 1 creazione file spin
		 */
		String execname; // name of the executable
		String filename = null; // the file containing the spec

		// creates a temp file
		try {
			File specFile = java.io.File.createTempFile(filenameprefix,
					filenamesuffix, tempDir);
			// Delete temp file when program exits.
			if (this.deleteTempFiles)
				specFile.deleteOnExit();
			FileWriter spec = new FileWriter(specFile);
			// visitor.setSearchCommonCoverage(tc.getSearchForCommonCoverage());
			spec.append(promelaIn.getSpec2().toString());
			spec.close();
			filename = specFile.getName();
			execname = SimpleCmdExecutor.getExecName(tempDir, filename);
			assert specFile.exists();
		} catch (IOException t) {
			String msg = "exception " + t.getMessage()
					+ " when creating promela file " + filename;
			logger.debug(msg);
			testgen.fireTestConditionError(promelaIn.tc, msg);
			throw new ModelCheckerExecutionException(msg);
		}
		/***********************************************************************
		 * FASE 2 creazione file pan
		 */
		// read the default information for spin
		String FLAGSSPIN_1 = "-a";

		// create file pan.*
		// String command = "spin -a " + fileName + ".spin";

		String command = spinProgram + " " + FLAGSSPIN_1 + " " + filename;

		/*
		 * if (System.getProperty("file.separator").equals("\\")) { command =
		 * command.replace('/', '\\'); // command.replace("\\", "\\\\"); }
		 */
		Process pr = runAndWait(command, tempDir);
		logger.debug("\"" + command + "\" executed");
		if (pr == null || !(new File(tempDir, "pan.c").exists())) {
			String msg = "Error executing : " + command + " in "
					+ tempDir.toString();
			testgen.fireTestConditionError(promelaIn.tc, msg);
			logger.debug("throwing " + msg);
			throw new ModelCheckerExecutionException(msg);
		}
		assert testgen != null;
		testgen.fireTestConditionStepCompleted(promelaIn.tc, command);
		// destroy pr
		pr.destroy();

		/* FASE 3: compilazione file pan */
		ccOpt = "";
		for (SimplePreference cp : SPIN_COMPILE_OPTION.getPreference()) {
			CheckedPreference cp2 = (CheckedPreference) cp;
			if (SPIN_COMPILE_OPTION.isChecked(cp2)) {
				String val = (cp.getValue()).toString();
				// if val = "true" do not write anything
				ccOpt += cp.getKey()
						+ (Boolean.parseBoolean(val) ? "" : "=" + val) + " ";
			}
		}
		if (System.getProperty("file.separator").equals("\\")) {
			filename = filename.replace('/', '\\');
			// fileName.replaceAll("\\", "\\\\");
		}
		String compileCmd = cc + " " + ccOpt + " pan.c -o " + execname;
		try {
			// attenzione sotto windows may not work
			// http://cboard.cprogramming.com/tech-board/125098-windows-7-access-denied-gcc.html
			// gcc is a symbolic link !
			// CAREFUL: it must wait until has finished
			// but not longer (
			logger.debug("before " + compileCmd);
			runAndWait(compileCmd, tempDir);
			logger.debug("\"" + compileCmd + "\" executed");
		} catch (Exception e1) {
			testgen.fireTestConditionError(promelaIn.tc,
					"Can't compile files pan.*");
			throw new ModelCheckerExecutionException("exception executing "
					+ compileCmd);
		}
		// if (! (new File(tempDir,execname).exists())) {
		if (!(new File(execname).exists())) {
			String msg = "The file " + execname + " does not exist in "
					+ tempDir;
			testgen.fireTestConditionError(promelaIn.tc, msg);
			throw new ModelCheckerExecutionException(msg);
		}
		testgen.fireTestConditionStepCompleted(promelaIn.tc,
				"Compiled files pan.*: " + compileCmd);
		// if (! (new File(tempDir,execname).canExecute())) {
		if (!(new File(execname).canExecute())) {
			String msg = "Can't exceute the compiled file " + execname;
			testgen.fireTestConditionError(promelaIn.tc, msg);
			throw new ModelCheckerExecutionException(msg);
		}

		// pr.destroy();

		// Eliminazione dei file pan.*
		// pr = runAndWait("rm -f ./pan.*");

		// Esegue l'analisi del modello
		// read default flag for running compiled spin

		/*
		 * TO DO RECUPERO runOpt con le nuove impostazioni
		 */
		Vector<String> runOpt = new Vector<String>();
		runOpt.add(execname);
		if (minusILowerCase.getValue())
			runOpt.add("-i");
		if (minusIUpperCase.getValue())
			runOpt.add("-I");
		if (SPINOPTION.isChecked(atgt.preferences.ATGToolPreferences.MAXDEPTH))
			runOpt.add("-m" 	+ atgt.preferences.ATGToolPreferences.MAXDEPTH.getValue());
		if (atgt.preferences.ATGToolPreferences.SPINOPTION.isChecked(atgt.preferences.ATGToolPreferences.DIMHASHTABLE))
			runOpt.add("-w"
					+ atgt.preferences.ATGToolPreferences.DIMHASHTABLE
							.getValue());
		/*
		 * if (System.getProperty("file.separator").equals("\\")){
		 * fileName.replaceAll("/", "\\"); fileName.replaceAll("\\", "\\\\"); }
		 */

		// FASE 3 (run dell'eseguibile) e replay of trail
		try {

			logger.debug("launching running model checker - " + filename);
			SpinExecutionResult runResult = SpinTestGenerator.runSpin(runOpt,spinProgram, filename, CmdExecutor.CMD);
			// check that the trail exists
			// pr.destroy();
			logger.debug("model checking finished - result " + runResult.toString());

			testgen.fireTestConditionStepCompleted(promelaIn.tc, execname + " "+ runOpt + " completed");
			testgen.fireTestConditionStepCompleted(promelaIn.tc, "result:"	+ runResult.toString());
			return runResult;
		} catch (Exception e) {
			logger.debug("forTestCondition(TestCondition) - " + e.toString()); //$NON-NLS-1$
			throw new ModelCheckerExecutionException(e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seetgtlib.generator.TestSequenceGenerator#analyses(extgt.spin.generator.
	 * MCExecutionResultReader, tgtlib.definitions.TestSequence)
	 */
	@Override
	public MCAnalysisResult analyses(MCExecutionResultReader in,AsmTestSequence ts) {
		if (in == null) {
			ts.setNotFound(" PROBLEMS !!!: no output generated by Spin");
			ts.close();
			return MCAnalysisResult
					.notFound("PROBLEMS !!!: no output generated by Spin");
		}
		SpinExecutionResult step3Result = (SpinExecutionResult) in;
		switch (step3Result.getResult()) {
		case UNFEASIBLE:
			ts.setUnfeasible();
			ts.close();
			in.close();
			return MCAnalysisResult.unfeasible();
		case ERROR:
			// error: print the error message
			ts.setNotFound(step3Result.getErrorMessage().toString());
			ts.close();
			in.close();
			return MCAnalysisResult.notFound(step3Result.getErrorMessage().toString());
		default:
			// ASSERTION VIOLATED
			try {
				Reader mcResult = in.getMcOutputreader();
				// prende il file di spin e fa il parsing
				TrailParser tp = new TrailParser(mcResult);
				//
				tp.setVariables(spec.getVariables());
				// try{
				MCAnalysisResult tr = tp.analysis(ctree, ts);
				mcResult.close();
				in.close();
				return tr;
			} catch (FileNotFoundException ex) {
				if (logger.isDebugEnabled()) {
					logger.debug("forTestCondition(TestCondition) - Impossibile creare il file!n" + ex); //$NON-NLS-1$
				}
			} catch (ParseException pe) {
				if (logger.isDebugEnabled()) {
					logger.debug("forTestCondition(TestCondition) - Errore nell'esecuzione del controesempio!!n" + pe); //$NON-NLS-1$
				}
			} catch (java.io.IOException ioe) {
				if (logger.isDebugEnabled()) {
					logger.debug("forTestCondition(TestCondition) - IO Errore nell'esecuzione del controesempio!!n" + ioe); //$NON-NLS-1$
				}
			}
			in.close();
			return MCAnalysisResult.notFound("some excpetion");
		}
	}

	public void setCoverages(AsmCoverageTree coverages) {
		// set the coverage for the visitor
		// L'insieme dei Coverages che sono stati applicati alla specifica must
		// be
		// equal to visitor.coverageTree
		((ToSpinTranslatorVisitor) visitor).setCoverages(coverages);
	}

	@Override
	public MCExecutionResultReader runModelChecker(AsmTestCondition tp)
			throws ModelCheckerExecutionException {
		logger.debug("translation of spec ...");
		// get the translation
		visitor.setTestCondition(tp);
		StringBuffer translation = visitor.analyze(spec);
		logger.debug("running model checker ...");
		return runModelChecker(new StringAndTPMCInput<AsmTestCondition>(
				translation, tp));
	}

	public boolean isSearchCommonCoverage() {
		return ((ToSpinTranslatorVisitor) visitor).isSearchCommonCoverage();
	}
}
