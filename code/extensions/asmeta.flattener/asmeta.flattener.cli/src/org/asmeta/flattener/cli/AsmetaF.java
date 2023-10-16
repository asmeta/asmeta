package org.asmeta.flattener.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.flattener.FlattenerSetting;
import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.flattener.statistics.Statistics;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.cli.ASMFileFilter;
import asmeta.cli.AsmetaCLI;

public class AsmetaF extends AsmetaCLI {
	
	@Option(name = "-MCR", usage = "remove macro call rules")
	public boolean mcr;

	@Option(name = "-FR", usage = "remove forall rules")
	public boolean fr;

	@Option(name = "-ChR", usage = "remove choose rules")
	public boolean chr;

	@Option(name = "-AR", usage = "remove function arguments")
	public boolean ar;

	@Option(name = "-LR", usage = "remove let rules")
	public boolean lr;

	@Option(name = "-CaR", usage = "remove case rules")
	public boolean car;

	@Option(name = "-NR", usage = "remove nesting")
	public boolean nr;

	@Option(name = "-ALL", usage = "apply all the flatteners")
	public boolean all = false;

	
	private AsmetaF() {}
	
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 */
	public static void main(String[] args) {
		Statistics.getInstance().setEnabled(false);
		FlattenerSetting.simplify = true;
		Logger.getGlobal().setLevel(Level.OFF);
		AsmetaF asmetaF = new AsmetaF();
		asmetaF.run(args);
	}

	@Override
	protected RunCLIResult runWith(File asmFile) throws CmdLineException {
		ASMFileFilter filter = new ASMFileFilter();
		if (!filter.accept(asmFile)) {
			throw new CmdLineException("Error:  " + asmFile.toString() + " is not an ASM file.");
		} else {
			List<Class<? extends AsmetaFlattener>> flatteners = new ArrayList<>();
			if (mcr || all) {
				flatteners.add(MacroCallRuleFlattener.class);
			}
			if (fr || all) {
				flatteners.add(ForallRuleFlattener.class);
			}
			if (chr || all) {
				flatteners.add(ChooseRuleFlattener.class);
			}
			if (ar || all) {
				flatteners.add(RemoveArgumentsFlattener.class);
			}
			if (lr || all) {
				flatteners.add(LetRuleFlattener.class);
			}
			if (car || all) {
				flatteners.add(CaseRuleFlattener.class);
			}
			if (nr || all) {
				flatteners.add(RemoveNestingFlattener.class);
			}
			try {
				String path = asmFile.getAbsolutePath();
				String flattenedAsm = AsmetaMultipleFlattener.flattenAsStr(path, flatteners);
				System.out.println(flattenedAsm);
				writeFlattenedAsm(flattenedAsm, path);
				return RunCLIResult.SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return RunCLIResult.FATAL;
			}
		}
	}

	private void writeFlattenedAsm(String flattenedAsm, String path) throws IOException {
		Path p = Paths.get(path).getFileName();
		String asmName = p.toString();
		path = path.substring(0, path.indexOf(asmName));
		String flattenedAsmPath = path + asmName.substring(0, asmName.length() - 4) + "_flat.asm";
		File f = null;
		BufferedWriter bw = null;
		try {
			f = new File(flattenedAsmPath);
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(flattenedAsm);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			assert false;
		} finally {
			// f.delete();
			bw.close();
		}
	}

	@Override
	protected String getJar() {
		return "AsmetaF.jar";
	}
}
