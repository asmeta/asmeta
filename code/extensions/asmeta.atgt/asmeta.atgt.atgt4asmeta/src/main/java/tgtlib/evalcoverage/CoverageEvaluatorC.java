/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.evalcoverage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import tgtlib.definitions.NavigableInputSequence;
import tgtlib.definitions.TestPredicate;
import tgtlib.preferences.TGLibPreferences;
import tgtlib.specification.Specification;
import tgtlib.util.MyStreamTokenizer;
import tgtlib.util.SimpleCmdExecutor;

/**
 * Coverage evaluator be translation to C.
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
public abstract class CoverageEvaluatorC<S extends TestPredicate<?,?>, T extends Specification>
		implements CoverageEvaluator<S> {

	private static final String CFILE_NAME_SUFFIX = ".c";

	private static String CFILE_NAME_PREFIX = "c_code_for_coverage";

	private static final Logger log = Logger.getLogger(CoverageEvaluatorC.class);

	TranslatorInputsToC<T> toCTranslator;

	private Vector<S> TGcovered;

	/**
	 * Method addToCovered.
	 * 
	 * @param tg
	 *            S
	 */
	private void addToCovered(S tg) {
		TGcovered.add(tg);

	}

	/**
	 * Method getTPwithID.
	 * 
	 * @param tpID
	 *            String
	 * @return S
	 */
	protected abstract S getTPwithID(String tpID);

	/**
	 * Creates a new instance of CoverageEvaluator for a specification and a
	 * tree PUT these in the constructor because I could call the method cover
	 * for the same Spec and tree without
	 * 
	 * 
	 * 
	 * @param translator
	 *            TranslatorInputsToC<T>
	 */
	public CoverageEvaluatorC(TranslatorInputsToC<T> translator) {
		toCTranslator = translator;
	}

	/**
	 * given test sequence containing only inputs run spin to have the coverage
	 * return the test goals covered by the input Sequence
	 * 
	 * @param inputs
	 *            NavigableInputSequence
	 * @return Vector<S>
	 * @see tgtlib.evalcoverage.CoverageEvaluator#computeCoverage(NavigableInputSequence)
	 */
	@Override
	public Vector<S> computeCoverage(NavigableInputSequence inputs) {

		TGcovered = new Vector<S>();
		// take the translation with the predicates
		StringBuffer SPEC = toCTranslator.translate(inputs);
		try {
			// run Spin with the executor
			FileReader in = executeCoverage(SPEC);
			// analysis with a testsequenceEvaluator
			if (in != null) {
				analyse(in);
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return TGcovered;
	}

	/* analysis of the result */
	/**
	 * Method analyse.
	 * 
	 * @param r
	 *            FileReader
	 */
	private void analyse(FileReader r) {

		// skip till you get a variable that starts with the same name

		StreamTokenizer st = new MyStreamTokenizer(r, false);

		st.wordChars('_', '_');

		try {
			// read the information
			do {

				st.nextToken();
				// 1. new state
				if ((st.ttype == StreamTokenizer.TT_WORD)
						&& st.sval.startsWith(TranslatorInputsToC.getCoveredprefix())) {

					String tpID = st.sval.substring(TranslatorInputsToC.getCoveredprefix().length());

					log.debug("case " + tpID + " covered");
					// take the tree and search for the to covered!!
					S tgCovered = getTPwithID(tpID);
					if (tgCovered != null)
						addToCovered(tgCovered);
					else
						log.error("tg with ID " + tpID + " not found");

				}
			} while (st.ttype != StreamTokenizer.TT_EOF);
		} catch (Exception t) {
			t.printStackTrace(System.out);
		}
		return;
	}

	/**
	 * run spin to evaluate the coverage it is different from the test
	 * generation: here just run the c code to get the covergae
	 * 
	 * @param Spec
	 *            the spec
	 * 
	 * 
	 * @return the input stream
	 */
	private FileReader executeCoverage(StringBuffer Spec) {
		File tempDir = tgtlib.preferences.Utility.getTempDirPref();
		boolean deleteTempFiles = TGLibPreferences.DELETE_TMP.getValue();

		String filename; // the file containing the spec
		String fileNameCPrefix; // the file containing the spec without path

		try {
			File specFile = java.io.File.createTempFile(CFILE_NAME_PREFIX, CFILE_NAME_SUFFIX, tempDir);
			// Delete temp file when program exits.
			if (deleteTempFiles)
				specFile.deleteOnExit();
			filename = specFile.getPath();
			fileNameCPrefix = specFile.getName();
			FileWriter spec = new FileWriter(filename);
			writeSBufferToFile(Spec, spec);
			spec.close();
		} catch (IOException t) {
			log.fatal(t);
			return null;
		}
		assert (filename != null);
		// get the preferences
		String covMethod = TGLibPreferences.COV_EVAL.getValue();
		if (covMethod.equals(TGLibPreferences.COMPILED))
			return runCoverageCompExec(tempDir, deleteTempFiles, fileNameCPrefix);
		else if (covMethod.equals(TGLibPreferences.CINT_INTERPRETER))
			return runCoverageInterpreter(tempDir, filename);
		else
			throw new RuntimeException("method not found");
	}

	/**
	 * compile and execute the coverage evaluator
	 * 
	 * @param tempDir
	 * 
	 * @param deleteTempFiles
	 * @param filename
	 *            without path
	 * 
	 * @return FileReader
	 */
	public FileReader runCoverageCompExec(File tempDir, boolean deleteTempFiles, String filename) {

		log.debug("run coverage evaluation for " + filename + " in " + tempDir.toString());

		String cCcompiler = TGLibPreferences.CC.getValue();

		String fileExec = SimpleCmdExecutor.getExecName(tempDir, filename);
		try {
			// compile
			SimpleCmdExecutor.CMD.runCommand(tempDir, cCcompiler, "-o", fileExec, filename);
			// check the the executable file exists and can be executed
			File fExe = new File(fileExec);
			assert fExe.exists();
			assert fExe.canExecute();
			log.debug("the file  name is " + filename);
			// run - it is not necessary to have "." in the path
			SimpleCmdExecutor.CMD.runCommand(tempDir, true, false, fileExec);
			// delete exe
			File fileExecF = new File(tempDir, fileExec);
			if (deleteTempFiles)
				fileExecF.deleteOnExit();
			return new FileReader(SimpleCmdExecutor.CMD.getOutput());
		} catch (Exception t) {
			log.error("error executing coverage commands. Exception " + t.toString());
			return null;
		}
	}

	static private final String cint = "cint";

	/**
	 * e' public solo per per permettere il testing nelle sottoclassi |!! *
	 * 
	 * @param tempDir
	 *            File
	 * @param filename
	 *            String
	 * @return FileReader
	 */
	public FileReader runCoverageInterpreter(File tempDir, String filename) {
		// launch the interpreter
		try {
			SimpleCmdExecutor.CMD.runCommand(tempDir, true, true, cint, filename);
			return new FileReader(SimpleCmdExecutor.CMD.getOutput());
		} catch (Exception e) {
			log.error("error interpreting cov evaluator. Exception " + e.toString());
			e.printStackTrace();
			return null;
		}
	}

	// TODO sostituisci c passando al tranlsatore il writer
	/**
	 * Method writeSBufferToFile.
	 * 
	 * @param spec
	 *            StringBuffer
	 * @param file
	 *            FileWriter
	 * @throws IOException
	 */
	private void writeSBufferToFile(StringBuffer spec, FileWriter file) throws IOException {
		for (int i = 0; i < spec.length(); i++)
			file.write(spec.charAt(i));

	}
}
