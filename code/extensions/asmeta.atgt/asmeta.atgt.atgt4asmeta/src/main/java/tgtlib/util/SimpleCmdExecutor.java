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
package tgtlib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * to run external programs it runs a program and set result and errors as files
 * It uses files to avoid the buffer overflow.
 * Note that the process should be run in parallel: See
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */

public class SimpleCmdExecutor {

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(SimpleCmdExecutor.class);

	/** The delete temp files. */
	static public boolean deleteTempFiles = true;

	/** the output of the last command. it may be null */
	protected File output;

	/** The Constant outFileName. */
	static private final String OUT_FILE_NAME = "cmd_ouput";

	/** The Constant OUT_FILE_SUFFIX. */
	static private final String OUT_FILE_SUFFIX = ".txt";

	/** The errors. */
	public File errors;

	/** The Constant errFileName. */
	static private final String ERR_FILE_NAME = "cmd_errors";

	/** The Constant errFileSuffix. */
	static private final String ERR_FILE_SUFFIX = ".txt";

	/**
	 * Gets the output of the last command. it may be null
	 * 
	
	 * @return the output */
	public File getOutput() {
		return output;
	}

	/** The elapsed time. 
	 * messo static, cos�not  le sottoclassi uasono solo questo !!!
	 * altrimenti se uso una sottoclasse avraè un suo elapsed diverso (ad esempi CADSMV test generator usa Simple, altri usano CmdExecutor
	 * 
	 * computed using the runtime Java environment <BR>
	 * in seconds
	 * */
	private static double elapsedTime;
	
	/** reset the time */
	public void resetTime(){
		elapsedTime = 0;
	}
	
	/**
	 * Method incElapsedTime.
	 * @param time double
	 */
	protected void incElapsedTime(double time){
		elapsedTime += time;
	}
	
	/**
	 * Method getElapsedTime.
	 * @return double
	 */
	public double getElapsedTime(){
		return elapsedTime;
	} 

	protected SimpleCmdExecutor() {
	}

	static final public SimpleCmdExecutor CMD = new SimpleCmdExecutor();

	/**
	 * runs a command without current Dir the subprocess should inherit the
	 * working directory of the current process.
	 * @param cmd String[]
	 * @throws Exception
	 */
	public void runCommand(String... cmd) throws Exception {
		runCommand(getCommands(cmd), null, false, false);
	}

	/**
	 * runs a command do not write on files.
	 * 
	 * @param currentDir the current dir
	 * @param cmd the cmd
	 * 
	
	 * @throws Exception the exception */
	public void runCommand(File currentDir, String... cmd) throws Exception {
		runCommand(getCommands(cmd), currentDir, false, false);
	}

	/**
	 * Run command.
	 * 
	 * @param currentDir
	 *            the current dir
	 * @param writeOutputOnfile
	 *            the write output on file
	 * @param writeErrorOnFile
	 *            the write error on file
	 * @param cmd
	 *            the commands as an array of strings or as unique string which
	 *            will be split
	 */
	public void runCommand(File currentDir, boolean writeOutputOnfile,
			boolean writeErrorOnFile, String... cmd) {
		runCommand(getCommands(cmd), currentDir, writeOutputOnfile,
				writeErrorOnFile);
	}

	/** as the previous, with a list instead of an array
	 * 
	 * @param currentDir
	 * @param writeOutputOnfile
	 * @param writeErrorOnFile
	 * @param cmd
	 */
	public void runCommand(File currentDir, boolean writeOutputOnfile,
			boolean writeErrorOnFile, List<String> cmd) {
		runCommand(currentDir,writeOutputOnfile, writeErrorOnFile,
				cmd.toArray(new String[cmd.size()]));
	}

	/**
	 * execute a single command and sets the standard output and error, and time
	 * elapsed. CAN BE BLOCKED BY BUFFEROVWERFLOW it may be used if there is not
	 * too much output SO far used only by spin for intermediate commands
	 * (compile ....)
	 * 
	 * @param currentDir
	 *            the directory where it must be executed
	
	 * @param writeOutputOnfile
	 *            the write output onfile
	 * @param writeErrorOnFile
	 *            the write error on file
	 * 
	
	 * @param cmd String[]
	 * @throws Exception
	 *             the exception */
	private void runCommand(String[] cmd, File currentDir,
			boolean writeOutputOnfile, boolean writeErrorOnFile) {

		// starts the process
		ProcessBuilder pbuilder = new ProcessBuilder();

		// set the current time
		long startTime = System.currentTimeMillis();

		// starts the process
		Process pr = null;

		// display the command to be executed
		StringBuffer cmdTotal = new StringBuffer();
		for (String element : cmd){
			assert(!element.contains(" "));
			cmdTotal.append(element).append(' ');
		}
		LOG.info("executing " + cmdTotal + "in "
				+ (currentDir != null ? currentDir : "current dir"));

		//
		output = errors = null;

		// try executing cmd
		try {
			pbuilder.directory(currentDir);
			pbuilder.command(cmd);
			pr = pbuilder.start();
			LOG.info(".... running");
		} catch (Exception t) {
			LOG.info("errors executing this command");
			t.printStackTrace(System.err);
			// if the command does not run returns and execption
			throw new CmdException(t,cmdTotal,cmd);
		}
		try {
			BufferedReader std_output = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			BufferedWriter out = null;
			if (writeOutputOnfile) {
				// create the temporary file
				output = File.createTempFile(OUT_FILE_NAME, OUT_FILE_SUFFIX, currentDir);
				LOG.info("results in temp file " + output.getCanonicalPath());
				// Delete temp file when program exits.
				if (deleteTempFiles)
					output.deleteOnExit();
				// Write to temp file
				out = new BufferedWriter(new FileWriter(output));
			}
			// read from the process (otherwise get stuck)
			while (true) {
				String newLine = std_output.readLine();
				if (newLine == null)
					break;
				if (writeOutputOnfile) {
					out.write(newLine);
					out.write('\n');
				}
			}
			std_output.close();
			if (writeOutputOnfile) {
				out.close();
			}
			// ADD THE ERRORS (AN NOTY ETHAT TIME PRINTS ON STDERR)
			if (writeErrorOnFile) {
				// create the temporary file
				errors = File.createTempFile(ERR_FILE_NAME, ERR_FILE_SUFFIX);
				LOG.info("errors in temp file: " + errors.getCanonicalPath());

				// Delete temp file when program exits.
				if (deleteTempFiles)
					errors.deleteOnExit();
				// read err from the process
				BufferedReader std_err = new BufferedReader(
						new InputStreamReader(pr.getErrorStream()));
				// Write to temp file
				BufferedWriter err = new BufferedWriter(new FileWriter(errors));
				while (true) {
					String newLine = std_err.readLine();
					// temp: writes also the time
					if (newLine == null)
						break;
					err.write(newLine);
					err.write('\n');
				}
				std_err.close();
				err.close();
			}
			// set the time
			elapsedTime += ((double) (System.currentTimeMillis() - startTime)) / 1000;
			// return program output
			return;
		} catch (IOException e) {
			LOG.info("errors reading and writing files");
			e.printStackTrace(System.err);
		}		
		// wait for the process // WORKS ONLY in Unix - Linux 
		// problems in windows ????
		try{
			pr.waitFor(); 
		} catch (Exception t){ 
			LOG.info(t.getMessage());
		}
		if (pr.exitValue() != 0) LOG.info("command terminated not normally");
		return;
	}

	/**
	 * given a command returns the command and its argument a subclass can add
	 * new functionlities
	 * 
	 * @param cmdOriginal
	
	 * @return String[]
	 */
	protected String[] getCommands(String... cmdOriginal) {
		if (cmdOriginal.length == 1)
			return cmdOriginal[0].split(" ");
		else
			return cmdOriginal;
	}

	/**
	 * give the a file name computes the name of the exec file File f: the spec
	 * file. With the path: so even if "." it is not in the path, the program can be executed
	 * 
	 * @param dir the directory in which the file will be placed
	 * @param desiredNameprefix the desired nameprefix (without the directory)
	 * 
	
	 * @return the exec name */
	public static String getExecName(File dir, String desiredNameprefix) {
		assert(! desiredNameprefix.startsWith("/tmp"));
		assert(! desiredNameprefix.startsWith("/"));
		String execname;
		try {
			execname = dir.getCanonicalPath();
		} catch (IOException e) {
			LOG.debug("errors trying the name of the executor - taking the absolute path");
			execname = dir.getAbsolutePath();
		}
		// appendSeparator
		execname += File.separatorChar;
		// appeand the name
		execname += desiredNameprefix + ".exe";
		// if under windows, substutute \ with \\
		// ideed gcc wants \\ and not \
		/*
		 * if (File.separatorChar == '\\') { //
		 * execname.replaceAll("[^\\\\]\\\\[^\\\\]", "\\\\\\\\"); execname =
		 * execname.replaceAll("\\\\", "\\\\\\\\"); }
		 */
		return execname;
	}

}
