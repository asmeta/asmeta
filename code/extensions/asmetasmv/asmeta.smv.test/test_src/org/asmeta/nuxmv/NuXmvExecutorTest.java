package org.asmeta.nuxmv;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.asmeta.nusmv.AsmetaSMVOptions;
import org.junit.Test;
import org.junit.runner.notification.RunListener.ThreadSafe;

class StreamGobbler extends Thread {
	InputStream is;
	StringBuilder sb;
	boolean processReady = false;

	StreamGobbler(InputStream is, StringBuilder sb) {
		this.is = is;
		this.sb = sb;
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			/*
			 * String line = null; while ((line = br.readLine()) != null) {
			 * System.out.println(line); sb.append(line + "\n"); }
			 */
			int ch;
			String line;
			System.out.println("Read started");
			do {
				ch = is.read();
				/*if ((char)ch == '>') {
					processReady = true;
					System.out.println("READY");
				}*/
				System.out.print((char) ch);
			} while (ch != -1);
			System.out.println("ENDED");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

class NuXmvExecutor {

	static void runNuSMV(String smvFileName, boolean usingTime) throws Exception {
		String solverName = "nuXmv";
		ProcessBuilder bp = usingTime? new ProcessBuilder(solverName, "-int", "-time") : new ProcessBuilder(solverName, "-int");
		bp.redirectErrorStream(true);
		Process proc = bp.start();
		//
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
		//
		StringBuilder sb = new StringBuilder();
		StreamGobbler sg = new StreamGobbler(proc.getInputStream(), sb);
		new Thread(sg).start();
		TimeUnit.SECONDS.sleep(1);
		// send some messages
		System.out.println("read_model -i "+ smvFileName + "\n");
		bw.write("read_model -i "+ smvFileName + "\n");
		bw.flush();
		if (usingTime) {
			bw.write("go_time\n");
			bw.write("timed_check_ltlspec\n");
		} else {
			bw.write("go_msat\n");
			bw.write("msat_check_ltlspec_inc_coi -k 100\n");
			//bw.write("check_ltlspec_ic3\n");
		}
		//
		//while(!sg.processReady);
		// now quit
		TimeUnit.SECONDS.sleep(1);
		System.out.println("quit");
		bw.write("quit\n");
		bw.flush();
		// any error???
		int exitVal = proc.waitFor();
		System.out.println("ExitValue: " + exitVal);

	}

}

public class NuXmvExecutorTest {

	@Test
	public void testExecuteInt() throws Exception {

		//NuXmvExecutor.runNuSMV("exampleXMV/timeexample2.smv");
		
		NuXmvExecutor.runNuSMV("D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\VentilatoreASM_NewTime\\Ventilatore4_1SimpleTime2.smv", true);
	}

	@Test
	public void testExecuteInteger() throws Exception {

		//NuXmvExecutor.runNuSMV("exampleXMV/timeexample2.smv");
		
		NuXmvExecutor.runNuSMV("D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtdYInf.smv", false);
	}

	
}
