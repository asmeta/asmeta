///*******************************************************************************
// * Copyright (c) 2008 Angelo Gargantini.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Contributors:
// *     Angelo Gargantini - initial API and implementation
// ******************************************************************************/
//package atgt.generator;
//
//import static atgt.preferences.ATGToolPreferences.SALOPTION;
//import static atgt.preferences.ATGToolPreferences.SAL_BMCdepth;
//import static atgt.preferences.ATGToolPreferences.SAL_PROGRAM;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import atgt.coverage.AsmTestCondition;
//import atgt.coverage.AsmTestSequence;
//import atgt.coverage.TestCondition;
//import atgt.specification.ASMSpecification;
//import extgt.srisal.generator.SRISALCexParser;
//import tgtlib.definitions.TestSequenceFactory;
//import tgtlib.generator.MCAnalysisResult;
//import tgtlib.generator.MCExecutionResultReader;
//import tgtlib.generator.ModelCheckerExecutionException;
//import tgtlib.util.CexParseException;
//import tgtlib.util.CmdExecutor;
//
///**
// * generates a single test sequences with SAL.
// */
//public class SalTSeqGenerator extends TransTSeqGenerator<SriSalInput> {
//
//	/** The Constant filename prefix. */
//	private static final String filenameprefix = "sal_spec";
//
//	/** The Constant filename suffix. */
//	private static final String filenamesuffix = ".sal";
//
//	private static String lastMemInfo = "";
//
//	
//	/**
//	 * Instantiates a new SAL test generator. Consider to use singleton
//	 */
//	public SalTSeqGenerator(ASMSpecification spec) {
//		// initialize the tarnlsator to the ASM (mon data) -> String 
//		visitor = MonitoredDataToSAL.SINGLETON;
//		this.spec = spec;
//		// consider only the first ones???
//		if (!atgt.preferences.ATGToolPreferences.ConsiderInitNext.getValue()) {
//			log.debug("considering only the first values");
//			tsFactory =  new TestSequenceFactory<AsmTestSequence, AsmTestCondition>() {
//
//				@Override
//				public AsmTestSequence buildTestSequence(AsmTestCondition tp) {
//					return new AsmTestSequenceFirstValues(tp);
//				}
//			};
//		} 
//	}
//
//	/** The log. */
//	protected static Logger log = Logger.getLogger(SalTSeqGenerator.class);
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see extgt.spin.generator.TestSequenceGenerator#initResources()
//	 */
//	@Override
//	public void initResources() {
//		super.initTempFilePrefs();
//	}
//	
//	
//
//
//	/**
//	 * run the model checker SAL with this file (it needs a file and not a spec,
//	 * since file name and spec name must be consistent).
//	 * 
//	 * @param spec
//	 *            file
//	 * @param tp
//	 *            test condition
//	 * 
//	 * @return the MC execution result
//	 * 
//	 * @throws ModelCheckerExecutionException
//	 *             the model checker execution exception
//	 */
//	@Override
//	public MCExecutionResultReader runModelChecker(SriSalInput salIn)
//			throws ModelCheckerExecutionException {
//
//		// Commands
//		List<String> commands = new ArrayList<String>();
//		
//		// get the location for sal: it may contain some options
//		String salProgram = SAL_PROGRAM.getValue();
//		commands.add(salProgram);
//		// get the option of depth if bmc
//		if (salProgram.endsWith("bmc") && SALOPTION.isChecked(SAL_BMCdepth)){
//			//add depth command
//			commands.add("--depth="+SAL_BMCdepth.getValue());
//		}			
//		//
//		String filename = salIn.spec.getPath();
//		// 
//		commands.add(filename);
//		commands.add(salIn.tc.getUniqueID());
//		log.debug("running sal with commands " +commands);
//		// run sal (use + to allow options in the sal program
//		CmdExecutor.CMD.runCommand(salIn.spec.getParentFile(),true,false,commands);
//		//SimpleCmdExecutor.CMD.runCommand(spec.getParentFile(), true, false,commands.toArray(new String[commands.size()]));
//		// get the output
//		//File output = SimpleCmdExecutor.CMD.getOutput();
//		File output = CmdExecutor.CMD.getOutput();
//		// TODO: ad dthis info the the MCExecutionresult
//		// FIXME now prints only
//		String memoryInfo = "MEMORY: stack peak: " + CmdExecutor.getStackPeak() + " heap :" + CmdExecutor.getHeapTotal();
//		if (!memoryInfo.equals(lastMemInfo)){
//			lastMemInfo = memoryInfo;
//			log.info(memoryInfo);
//		}
//		try {
//			return new MCExecutionResultReader(output);
//		} catch (FileNotFoundException e) {
//			throw new ModelCheckerExecutionException("file " + output	+ " not found");
//		}
//	}
//
//
//	@Override
//	public MCAnalysisResult analyses(MCExecutionResultReader in, AsmTestSequence ts) {
//		/** The reader. must be closed !! */
//		java.io.Reader reader = in.getMcOutputreader();
//		
//		MCAnalysisResult result = null;
//		try {
//			result = SRISALCexParser.parseCex(reader, ts, true);
//			reader.close();
//			return result;
//		} catch (CexParseException e) {
//			return MCAnalysisResult.notFound(e.getMessage());
//		} catch (IOException e) {
//			// cosa accade???
//			e.printStackTrace();
//			return result;
//		}
//	}
//	/** check for this specification (including the axioms) if tc has a model
//	 * 
//	 */
//	@Override
//	protected MCExecutionResultReader runModelChecker(AsmTestCondition tc) throws ModelCheckerExecutionException {
//		// build the file for a given test condition
//		File specFile;
//		try {
//			specFile = buildFile(tc);
//		} catch (IOException e) {
//			throw new ModelCheckerExecutionException(e);
//		}
//		// call the generator with such file
//		MCExecutionResultReader execResult = runModelChecker(new SriSalInput(specFile, tc));
//		return execResult;
//	}
//
//	/** build the necessary file
//	 * 
//	 * @param tc
//	 * @return
//	 * @throws IOException
//	 */
//	private File buildFile(TestCondition tc) throws IOException {
//		// create temp file
//		File specFile = java.io.File.createTempFile(filenameprefix, filenamesuffix, tempDir);
//		// Delete temp file when program exits.
//		if (deleteTempFiles)	specFile.deleteOnExit();
//	
//		FileWriter spec = new FileWriter(specFile);
//		log.debug("sal spec file is " + specFile.getAbsolutePath());
//		// compute the context (consistent with the file name)
//		String context = specFile.getName();
//		context = context.substring(0, context.length() - filenamesuffix.length());
//		visitor.setTestCondition(tc);
//		// set the context
//		((MonitoredDataToSAL) visitor).setContextName(context);
//		// get the translation and save to file
//		spec.append(visitor.analyze(this.spec));
//		spec.close();
//		return specFile;
//	}	
//}
