package org.asmeta.nusmv.main;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.asmeta.nusmv.main.AsmetaSMV.ModelCheckerMode;
import org.asmeta.nusmv.util.AsmetaSMVOptions;
import org.junit.Test;

//
// example of call of asmetasmv with bounded model checking in a batch mode or interactively
//
public class AsmetaSMV_BMC {

	protected AsmetaSMV execNuSMVBMC(String file) {
		AsmetaSMV as = null;
		AsmetaSMVOptions asmetaOptions = new AsmetaSMVOptions();
		AsmetaSMVOptions.keepNuSMVfile = true;
		AsmetaSMV.BMCLength = 10;
		AsmetaSMV.modelCheckerMode = ModelCheckerMode.LTLandBMC;
		try {
			as = new AsmetaSMV(new File(file), asmetaOptions);
			as.translation();
			as.createNuSMVfile();
			AsmetaSMVOptions.setPrintNuSMVoutput(true);
			as.executeNuSMV();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error while executing the NuSMV model.");
		}
		return as;
	}

	@Test
	public void testBMCAmanBatch() {
		execNuSMVBMC("examples/aman0_noDerived_LimitedTime_noUndef_singleMove.asm");
	}

	// example of call nusmv interactively with customised commands
	// still todo the translation from asmeta to nusmv
	@Test
	public void testBMCAmanInt() throws IOException, InterruptedException {
		String solverName = "nusmv";
		ProcessBuilder bp = new ProcessBuilder(solverName, "-int");
		bp.redirectErrorStream(true);
		Process proc = bp.start();
		//
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
		//
		StringBuilder sb = new StringBuilder();
		StreamGobblerNuXmv sg = new StreamGobblerNuXmv(proc.getInputStream(), sb);
		new Thread(sg).start();
		// send some messages
		String smvFileName = "FILE OBTAINED TRASLATING ASM  - TODO";
		System.out.println("read_model -i " + smvFileName + "\n");
		bw.write("read_model -i " + smvFileName + "\n");
		bw.flush();
		bw.write("flatten_hierarchy\n");
		bw.write("build_flat_model\n");
		bw.write("time_setup\n");
		bw.write("timed_check_ltlspec\n");
		//
		// while(!sg.processReady);
		// now quit
		System.out.println("quit");
		bw.write("quit\n");
		bw.flush();
		// any error???
		int exitVal = proc.waitFor();
		System.out.println("ExitValue: " + exitVal);
	}
}
