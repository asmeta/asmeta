package tgtlib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.State;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import tgtlib.preferences.TGLibPreferences;

/**
 * executes external programs and returns ???? two versions one returns the
 * stream another does run the program and stops additional time capabilities !!
 * 
 * DEVO ANCORA AGGIUNGERE IL CODICE FATTO PER ASM TG TOOL DEVO UNIRE I DUE
 * PROCESS E FILEOUTPUT
 * 
 * TODO - USA ProcessBuilder che unisce STerr e Stdout - usa il globber per
 * evitare buffer overflow
 * 
 * @author Angelo Gargantini
 * @version $Revision: 1.0 $
 */
public class CmdExecutor extends SimpleCmdExecutor {

	/* Logger for this class */
	private Logger log = Logger.getLogger(CmdExecutor.class);

	private static long timeout;

	/**
	 * timeout of an external application in seconds before task is to be
	 * executed 0-> no timeout >0 timeout in seconds (note that in Java timeout
	 * is in milliseconds)
	 * * @param time long
	 */
	public static void setTimeOut(long time) {
		timeout = time;
	}

	/**
	 * use the time command? be sure to set the time command to the right time
	 * command usually /usr/bin/time otherwise the output may differ
	 */
	private static boolean wantTimeCommand = false;

	/**
	 * append the information regarding time on the output of the command
	 */
	private static boolean appendTimeToOutput = false;

	/*
	 * reads and parse the output of command for computing the time not now just
	 * print the time
	 */
	private static boolean parseAndSetTime = true;

	private static boolean useMemusage = false;

	private static final String MEMUSAGE_CMD = "memusage";

	private static final boolean writeOnFile = true;

	/*
	 * write the std err on the communication note that time is on stderr
	 * TODO : adesso st out ed error sono fusi insieme ...
	 */
	private static boolean writeError = false;

	/**
	 * the time is added for every invocation is in millisec ??? or sec ??? see
	 * time command !!! , except for elapsed
	 * 
	
	 */
	private static float realTime;

	/**
	
	 */
	private static float userTime;

	/**
	
	 */
	private static float systemTime;

	/**
	
	 */
	private static double heapTotal;

	/**
	
	 */
	private static double stackPeak;

	/**
	
	
	 * @return float
	 */
	public static float getRealTime() {
		return realTime;
	}

	/**
	
	
	 * @return float
	 */
	public static float getUserTime() {
		return userTime;
	}

	/**
	
	
	 * @return float
	 */
	public static float getSystemTime() {
		return systemTime;
	}

	/**
	
	
	 * @return double
	 */
	public static double getHeapTotal() {
		return heapTotal;
	}

	/**
	
	
	 * @return double
	 */
	public static double getStackPeak() {
		return stackPeak;
	}

	@Override
	public void resetTime() {
		super.resetTime();
		realTime = 0;
		userTime = 0;
		systemTime = 0;
	}
	
	private CmdExecutor() {
		deleteTempFiles = TGLibPreferences.DELETE_TMP.getValue();
	}

	/**
	
	
	 */
	static public CmdExecutor CMD = new CmdExecutor();

	/* note that not all time are good !! */
	private static String TIME_CMD;

	// interface where put the communication of the computation
	// note that if the user does not change is the System.out
	/**
	
	
	 */
	private static MyPrintWriter communicationWriter = new MyPrintWriter(System.out);

	/**
	 * @param cw
	
	 */
	public static void setCommunicationWriter(MyPrintWriter cw) {
		communicationWriter = cw;
	}

	/**
	 * runs a program and returns a InputStream (a temporary file now) where the
	 * user can read the output of the external program we take the temp file to
	 * avoid the buffer overflow problem
	 * 
	 * @param cmd
	 * @param currentDir
	 *            (must be a dir where execute the command
	
	
	 * @return the steam where the user can read the output of the program * @throws Exception */
	public InputStream getInputExecuteCommand(String cmd[], File currentDir) throws Exception {
		return new FileInputStream(getFileExecuteCommand(cmd, null, currentDir));
	}

	/**
	 * runs a program and returns a InputStream (a temporay file now) where
	 * throws euse can read the output of throws external program we use
	 * getFileExecuteCommand
	 * 
	 * @param cmd
	 * @param toWrite
	 *            the content must be written to the standard input
	
	
	 * @return the steam where the user can read the output of the program * @throws Exception */
	public InputStream getInputExecuteCommand(String cmd[], String toWrite) throws Exception {

		return new FileInputStream(getFileExecuteCommand(cmd, toWrite, null));
	}

	/**
	 * in caso di spin questo viene chiamato e passa la chiamata a file execute
	 * command
	 * @param currentDir File
	 * @param writeOutputOnfile boolean
	 * @param writeErrorOnFile boolean
	 * @param cmd String[]
	 */
	@Override
	public void runCommand(File currentDir, boolean writeOutputOnfile, boolean writeErrorOnFile, String... cmd) {
		try {
			output = getFileExecuteCommand(cmd, null, currentDir);
		} catch (Exception e) {
			output = null;
			e.printStackTrace();
		}
	}

	/**
	 * run a program and return a File containing the output of the program
	 * where the user can read the output of throws external program the file is
	 * deleted after the end of the program we take the temp file to avoid the
	 * buffer overflow problem so far; used only by cadence that needs the file
	 * instead of the InputStream
	 * 
	 * @param currentDir
	 *            : the currentDire where executing the command (it can be null)
	 * @param cmdOriginal
	 *            : array of commands to be executed
	 * @param toWrite
	 *            : write the content of the file
	
	
	 * @return the file where the user can read the output of the program * @throws Exception */

	private File getFileExecuteCommand(String cmdOriginal[], String toWrite, File currentDir) throws Exception {

		// starts the process
		ProcessBuilder pbuilder = new ProcessBuilder();

		// set the current time
		long startTime = System.currentTimeMillis();

		//
		// try executing smd
		//
		// add extra commands like memusage ....
		String[] cmdtime = getCompleteCommands(cmdOriginal);
		// build the string representing the complete command
		StringBuffer cmdTotal = new StringBuffer();
		for (String element : cmdtime)
			cmdTotal.append(element).append(' ');

		// error: it will contains error text
		String error = "";
		// where to write the output
		Writer out = null;
		try {
			// launch the command
			//communicationWriter.println("executing " + cmdTotal.toString() + " in " + currentDir);
			log.debug("DEBUG: executing " + cmdTotal + " in " + currentDir);
			pbuilder.directory(currentDir);
			// TODO some options (time, memusage) prints the output to the error stream
			// pe rora tienimoli seprati 
			// pbuilder.redirectErrorStream(true);
			pbuilder.command(cmdtime);
			Process proc = pbuilder.start();			

			// set the timeout if requested
			if (timeout > 0) {
				// Set a timer to interrupt the process if it does not return
				// within
				// the timeout period
				Timer timer = new Timer();
				// convert seconds in milliseconds
				timer.schedule(new InterruptScheduler(proc, Thread.currentThread(),cmdOriginal[0]), timeout * 1000);
			}
			// temp file, it will be returned 
			File temp;
			// setup where to write the output
			if (writeOnFile) {
				// get the output on a temp file
				// 	create the temporary file
				temp = File.createTempFile("commmand_output", ".txt", currentDir);

				// Delete temp file when program exits.
				if (deleteTempFiles) temp.deleteOnExit();

				// 	Write output to temp file
			
				out = new BufferedWriter(new FileWriter(temp));
				//communicationWriter.println("writing the output to a temporary file: " + temp.getPath());
			
			} else {
				// write on a String and print it after
				out = new StringWriter(); 
				communicationWriter.println("storing the output on a string");
			}

			// TODO; use a thread to capture the output also for the stderr
			// any error message?
			// StreamGobbler errorGobbler = new
			// StreamGobbler(proc.getErrorStream(), "ERROR");
			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), out);
			// kick them off
			// errorGobbler.start();
			outputGobbler.start();
			
			// write to the process output
			// Which mc is using toWrite != null ?? old SMV
			if (toWrite != null) {
				OutputStream outproc = proc.getOutputStream();
				OutputStreamWriter w = new OutputStreamWriter(outproc);
				w.write(toWrite);
				w.close();
			}

			// wait the process to finish
			// if interrupted by the time will exit
			proc.waitFor();
			// wait also the grapper to finish (otherwise the output could be not 
			// copied all, if proc finished before 
			while (outputGobbler.getState() !=State.TERMINATED);

			// write other information
			// TODO use process Builder that prints also the
			// ADD THE ERRORS (NOTE THAT TIME PRINTS ON STDERR)
			if (writeError || parseAndSetTime || appendTimeToOutput || useMemusage) {
				// read err from the process
				if (writeError) communicationWriter.println("writing the std error to the temporary file");

				BufferedReader brerr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

				String newLine = "";
				while ((newLine = brerr.readLine()) != null) {
					if (writeError)
						communicationWriter.println(newLine);
					readMemusage(newLine);
					log.error(newLine);
					error += newLine + " ";
				}
				brerr.close();
				/*
				 * Moved here because it parses the final errorStream output to
				 * get time
				 */
				readTimeLine(error);

				if (appendTimeToOutput) {
					// it must write something
					out.write("<TIMERESULT>\n");
					out.write(error);
					out.write("\n</TIMERESULT>\n");
				}
			}

			// set the time
			incElapsedTime(((double) (System.currentTimeMillis() - startTime)) / 1000);

			//communicationWriter.println("computed elapsed time " + getElapsedTime());
			log.debug("computed elapsed time " + getElapsedTime());
			// return program output
			return temp;
		} catch (IOException e) {
			e.printStackTrace(System.err);
			throw e;
		} catch (java.lang.InterruptedException t) {			
			throw new Exception("process interrupted; timeout = " + timeout); 
		} catch (Exception t) {
			t.printStackTrace(System.err);
			// if the command does not run returns and execption
			throw new Exception(t.toString() + "\nIt can't execute " + cmdTotal + ": check your PATH");
		} finally{
			// close the output file
			out.close();			
		}		
	}

	/**
	 * compute the commands, adding in case the memusage
	 * 
	 * @param cmdOriginal
	
	 * @return the modified array of commands with time and memusage */
	private String[] getCompleteCommands(String[] cmdOriginal) {
		// add memusage
		if (useMemusage || wantTimeCommand) {
			LinkedList<String> orginalCms = new LinkedList<String>(Arrays.asList(cmdOriginal));
			completeCommands(orginalCms);
			return orginalCms.toArray(cmdOriginal);
		} else {
			return cmdOriginal;
		}
	}

	/** add the time and memusage info if necessary * * @param cmdOriginal List<String>
	 */

	public void completeCommands(List<String> cmdOriginal) {
		// add memusage
		if (useMemusage) {
			cmdOriginal.add(0, MEMUSAGE_CMD);
		}
		// add time
		if (wantTimeCommand) {
			// tokenizer qui per aggiungere l'opzione -p
			// time cmd specificato per l'uso di -p
			// TIME_CMD = "/usr/bin/time -p";

			// tokenizer qui per aggiungere l'opzione -p
			StringTokenizer stok = new java.util.StringTokenizer(TIME_CMD);
			int index = 0;
			while (stok.hasMoreTokens()) {
				cmdOriginal.add(index, stok.nextToken());
				index++;
			}
		}
	}

	/**
	 * compute the commands, adding in case the memusage
	 * 
	 * @param cmdOriginal
	
	 * @return the modified array of commands with time and memusage */
	@Override
	protected String[] getCommands(String... cmdOriginal) {
		return getCompleteCommands(super.getCommands(cmdOriginal));
	}

	/*
	 * take the line and analyzes it: if it is in the form of result for time,
	 * then read the time and return true, otherwise return false. UPDATE FOR
	 * USE WITH /usr/bin/time -p
	 */
	/**
	 * Method readTimeLine.
	 * @param line String
	 * @return boolean
	 */
	private static boolean readTimeLine(String line) {
		// some typical errors !!
		String CommandExited = "LineCommand exited with non-zero status";
		if (line.startsWith(CommandExited))
			return false;
		// it may also start with a "warning:....
		if (line.startsWith("warning"))
			return false;
		// it may begin with hint (spin)
		if (line.startsWith("hint:"))
			return false;
		// the output has always the following format
		/*
		 * the output has always the following format real ##.## user ##.## sys
		 * ##.##
		 */
		double thisRealTime, thisUserTime, thisSystemTime;

		StringTokenizer stok = new java.util.StringTokenizer(line);
		String rt = "", ut = "", st = "";
		String realTimeStr = "", userTimeStr = "", systemTimeStr = "";
		// parse the line
		if (stok.hasMoreTokens())
			realTimeStr = stok.nextToken();
		else
			return false;
		if (stok.hasMoreTokens())
			rt = stok.nextToken();
		else
			return false;
		if (stok.hasMoreTokens())
			userTimeStr = stok.nextToken();
		else
			return false;
		if (stok.hasMoreTokens())
			ut = stok.nextToken();
		else
			return false;
		if (stok.hasMoreTokens())
			systemTimeStr = stok.nextToken();
		else
			return false;
		if (stok.hasMoreTokens())
			st = stok.nextToken();
		else
			return false;

		if (!(realTimeStr.equals("real") && userTimeStr.equals("user") && systemTimeStr.equals("sys")))
			return false;

		try {
			thisRealTime = Double.parseDouble(rt);
			thisUserTime = Double.parseDouble(ut);
			thisSystemTime = Double.parseDouble(st);
		} catch (NumberFormatException nfe) {
			return false;
		}

		if (parseAndSetTime) {
			realTime += thisRealTime;
			userTime += thisUserTime;
			systemTime += thisSystemTime;
		}
		return true;
	}

	/* run a command and get output */
	/**
	 * Method checkCommand.
	 * @param cmd String
	 * @return boolean
	 */
	public boolean checkCommand(String cmd) {
		// starts the process
		Process proc = null;
		Runtime rt = Runtime.getRuntime();
		try {
			proc = rt.exec(cmd);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/*
	 * read memory usage
	 */
	/**
	 * Method readMemusage.
	 * @param line String
	 * @return boolean
	 */
	private boolean readMemusage(String line) {
		if (line.contains("Memory usage summary")) {
			log.debug("reading memory info from output line "+  line);
			int posHt = line.indexOf("heap total:");
			int posHtEnd = line.indexOf(",", posHt);
			int possp = line.indexOf("stack peak:");
			try {
				heapTotal = Float.parseFloat(line.substring(posHt + "heap total:".length(), posHtEnd));
				stackPeak = Float.parseFloat(line.substring(possp + "stack peak:".length()));
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Method setUseTimeCommand.
	 * @param value boolean
	 */
	public static void setUseTimeCommand(boolean value) {
		// check if the user wants time !!!
		if (value) {
			wantTimeCommand = true;
			parseAndSetTime = true;
		} else {
			wantTimeCommand = false;
			parseAndSetTime = false;
		}
	}

	/**
	 * @param value
	
	 */
	public static void setUseMemusage(boolean value) {
		// check if the user wants memusage
		if (value) {
			useMemusage = true;
		} else {
			useMemusage = false;
		}
	}

	/**
	 * Method setTimeCommand.
	 * @param value String
	 */
	public static void setTimeCommand(String value) {
		TIME_CMD = value;
	}

	/**
	 * used to kill a process after a while
	 * Note that 
	 * 
	 * @author garganti
	 * 
	 * @version $Revision: 1.0 $
	 */
	private class InterruptScheduler extends TimerTask {
		Thread target = null;
		Process p;
		private String cmd;

		/**
		 * Constructor for InterruptScheduler.
		 * @param proc Process
		 * @param target Thread
		 * @param cmd String
		 */
		public InterruptScheduler(Process proc, Thread target,String cmd) {
			this.target = target;
			p = proc;
			this.cmd = cmd;
		}

		/**
		 * Method run.
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			log.info("timeout reached - process " + target.getName() + " running "+ cmd + " interrupted");
			// ATTENZIONE: questo non interrompe il processo sottostante, semplicmenet non lo aspetta più
			// semplicemnet cambia lo stato del thread, se l'applicazione
			// non controlla, continua indefinitamente
			target.interrupt();
			// chiamo il kill del SO
			// TODO: adesso chiama il killall
			try {
				Runtime.getRuntime().exec("killall "+ cmd);
			} catch (IOException e) {
				log.error("kill all not supported? disable timeout");
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * useful to grap the output of a process , to avoid buffer overflow
	 * 
	 * Si potrebbe anche non fare come Thread e fare come prima ilr ead senza qitfor
	 * tanto di ferma sul readLine ...
	 * * @author garganti
	 * @version $Revision: 1.0 $
	 */
	private class StreamGobbler extends Thread {
		InputStream is;
		BufferedWriter out;
		/**
		 * 
		 * @param is input stream
		 * @param out where to save what is written (it could be even STringWriter)
		 */
		StreamGobbler(InputStream is, Writer out) {
			this.is = is;
			this.out = new BufferedWriter(out);
		}

		/**
		 * Method run.
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				// stops until the program has not finished yet.
				// wait in any case the end of the line
				while ((line = br.readLine()) != null) {
					log.debug(line);					
					out.append(line);
					out.append('\n');
				}
				// has finished:
				br.close();
				isr.close();
				out.flush();
				// DO NOT CLOSE, the error may be appended
				// out.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
