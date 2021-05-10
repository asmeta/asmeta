package org.asmeta.nusmv;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.nusmv.util.Util;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;

import asmeta.structure.Asm;

/**
 * Main class of AsmetaSMV
 * 
 */
public class AsmetaSMV {
	
	/** use bounded model checking and LTL */
	public boolean useBMC;
	
	public MapVisitor mv;
	public String outputRunNuSMV;
	public String outputRunNuSMVreplace;
	public static Process proc;
	private File asmFile;
	private Asm asm;
	String smvFileName;
	
	AsmetaSMVOptions asmetaOptions;


	
	public AsmetaSMV(String file) throws Exception {
		this(new File(file));
	}

	public AsmetaSMV(String file, boolean simplify) throws Exception {
		this(new File(file), simplify);
	}

	public AsmetaSMV(File file) throws Exception {
		this(file, new AsmetaSMVOptions()); //true, false, true, false);
	}

	public AsmetaSMV(File file, boolean simplify) throws Exception {
		this(file, simplify, false, true, false);
	}

	public AsmetaSMV(File file, boolean simplify, boolean execute, boolean checkInteger, boolean useNuXmv) throws Exception {
		this(file,new AsmetaSMVOptions(simplify, execute, checkInteger, useNuXmv));
	}

	
	/**
	 * 
	 * @param file
	 * @param simplify
	 * @param execute
	 * @param checkInteger
	 * @param useNuXmv
	 * @throws Exception
	 */
	public AsmetaSMV(File file, AsmetaSMVOptions options)
			throws Exception {
		AsmetaSMVOptions.useNuXmv = true;
		asmFile = file;
		if (asmFile.exists()) {
			try {
				asm = ASMParser.setUpReadAsm(asmFile).getMain();
				options.setRunNuSMV(options.execute);
				// relative path including the file name
				Util.setPath(asmFile.getPath());
				// relative path without file name
				Util.setDir(asmFile.getParent());
			} catch (ParseException pe) {
				throw new RuntimeException("error parsing " + file.toString() + "\nmessage: " + pe.getMessage()
						+ (pe.currentToken != null
								? ("\ntoken: " + pe.currentToken + " line: " + pe.currentToken.beginLine + " column: "
										+ pe.currentToken.beginColumn)
								: ""));
			}
		} else {
			throw new FileNotFoundException(
					"File " + file.getPath() + " has not been found. Current path " + new File(".").getAbsolutePath());
		}
	}

	/**
	 * It creates and populates the data structures that describe the mapping
	 * between the ASM model and the NuSMV model.
	 * 
	 * @throws Exception
	 */
	public void translation() throws Exception {
		Util.setMainAsmName(asm.getName());
		Util.setIsAsynchr(asm.getIsAsynchr());
			mv = new MapVisitor();
		mv.visit(asm);
	}

	public void createNuSMVfile() throws Exception {
		smvFileName = Util.replaceExtension(asmFile.getPath(), "smv");
		mv.printSmv(smvFileName);
	}

	public void createNuSMVfile(String smvFileName) throws Exception {
		assert mv != null : "An instance of MapVisitor must have been created previously";
		this.smvFileName = smvFileName + ".smv";
		mv.printSmv(this.smvFileName);
	}

	public void createTempNuSMVfile() throws Exception {
		assert mv != null : "An instance of MapVisitor must have been created previously";
		this.smvFileName = File
				.createTempFile(asmFile.getName().substring(0, asmFile.getName().lastIndexOf(".")), "temp.smv")
				.getAbsolutePath();
		// System.out.println(smvFileName);
		mv.printSmv(this.smvFileName);
	}

	public void executeNuSMV() throws Exception {
		/*
		 * Counter c = null; Thread t = null; if (Util.isPrintNuSMVoutput()) { c = new
		 * Counter(); t = new Thread(c); t.start(); }
		 */
		if (AsmetaSMVOptions.isPrintNuSMVoutput()) {
			out.print("Execution of NuSMV code ...");
		}
		// System.out.println(smvFileName);
		runNuSMV(smvFileName);
		/*
		 * if (Util.isPrintNuSMVoutput()) { c.run = false;// it stops the counter try {
		 * t.join(); } catch (InterruptedException e) { e.printStackTrace(); } }
		 */
		outputRunNuSMV = getOutput();
		// System.out.println(outputRunNuSMV);
		outputRunNuSMVreplace = replaceVarsWithLocations(outputRunNuSMV);
		mv.getPropertiesResults(outputRunNuSMV);
		if (AsmetaSMVOptions.isPrintNuSMVoutput()) {
			out.println(
					"\n-------------------------------" + "-----------------------------\n" + outputRunNuSMVreplace);
		}
		proc.destroy();
	}

	/**
	 * It substitutes in the input string "input" the NuSMV variables names with the
	 * corresponding location names. It also substitutes the values representing the
	 * undef value for the different domain with the string "undef", and the TRUE
	 * and FALSE constants.
	 * 
	 * @param input a string containing NuSMV traces, usually the output of the
	 *              NuSMV execution
	 * @return
	 */
	public String replaceVarsWithLocations(String input) {
		String output = input;
		// NuSMV variables names are substituted with the corresponding
		// locations names.
		for (String name : sortLength(mv.nusmvNameToLocation.keySet())) {
			output = output.replaceAll(name, mv.nusmvNameToLocation.get(name));
		}
		// Values representing the undef value for the different domains are
		// substituted
		// with the string "undef".
		for (String domain : mv.getUndefValue().keySet()) {
			output = output.replaceAll(mv.getUndefValue().get(domain), "undef");
		}
		output = output.replaceAll(" FALSE", " false");
		output = output.replaceAll(" TRUE", " true");
		return output;
	}

	public static String replaceVarsWithLocations(String input, Map<String, String> nusmvNameToLocation) {
		String output = input;
		// NuSMV variable names are replaced with ASM location names
		for (String name : AsmetaSMV.sortLength(nusmvNameToLocation.keySet())) {
			output = output.replaceAll(name, nusmvNameToLocation.get(name));
		}
		return output;
	}

	/**
	 * It executes the NuSMV model "smvFileName".
	 * 
	 * @param smvFileName the file name of the NuSMV model
	 * 
	 * @throws Exception
	 */
	private void runNuSMV(String smvFileName) throws Exception {
		String solverName;
		// nuXmv also accepts standard NuSMV files
		if (AsmetaSMVOptions.getSolverPath() == null) {
			if (AsmetaSMVOptions.isUseNuXmv()) {
				solverName = "nuXmv";
			} else {
				solverName = "NuSMV";
			}
		} else {
			solverName = AsmetaSMVOptions.getSolverPath();
		}
		List<String> commands = new ArrayList<>();
		// to run NuSMV also on MacOS X
		boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
		if (isMac) {
			// commands.add("/bin/sh");
			// commands.add("-c");
			solverName = "/Applications/NuSMV/bin/NuSMV";
		}
		commands.add(solverName);
		if (!AsmetaSMVOptions.isPrintCounterExample()) {
			commands.add("-dcx");
		}
		commands.add("-dynamic");
		if (AsmetaSMVOptions.useCoi) {
			commands.add("-coi");
		}
		commands.add("-quiet");
		// bounded model checking? LTL and
		if (useBMC) {
			commands.add("-bmc");			
		}
		commands.add(smvFileName);
		if (false) {
			String nusmvCommand = commands.subList(2, commands.size()).toString();
			nusmvCommand = nusmvCommand.replaceAll(",", "");
			nusmvCommand = nusmvCommand.substring(1, nusmvCommand.length() - 1);
			commands.clear();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add("\"" + nusmvCommand + "\"");
		}
		runNuSMV(commands.toArray(new String[commands.size()]));
		// runNuSMV(new String[] {"/Applications/NuSMV/bin/NuSMV", smvFileName});
	}

	/**
	 * It executes the NuSMV model with the provided set of options.
	 *
	 * @param cmdarray an array of options for NuSMV. The first one is the solver
	 *                 name and the last one the file name.
	 * 
	 * @throws Exception
	 */
	void runNuSMV(String[] cmdarray) throws Exception {
		// System.out.println(Arrays.toString(cmdarray));
		try {
			Runtime rt = Runtime.getRuntime();
			proc = rt.exec(cmdarray);

			// outputRunNuSMV = getOutput(smvFileName);
		} catch (Exception e) {
			out.println("Execution error\n" + e);
			throw e;
		}
	}

	public Map<String, Boolean> getResults(Set<String> properties) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		Map<String, Boolean> mapPropResult = mv.getMapPropResult();
		// System.out.println(properties);
		// System.out.println(mv.getMapPropResult());
		for (String property : properties) {
			Boolean propRes = mapPropResult.get(property);
			assert propRes != null : "property: " + property + "\nnot contained in\nmv.mapPropResult: " + mapPropResult;
			result.put(property, propRes);
		}
		return result;
	}

	/**
	 * It reads from the input and streams of NuSMV the output of the computation.
	 * 
	 * @param smvFileName the name of the executed NuSMV file
	 * @return the output produced by NuSMV
	 * @throws Exception
	 */
	public String getOutput() throws Exception {
		StringBuilder sb = new StringBuilder("> ");
		if (AsmetaSMVOptions.isUseNuXmv()) {
			sb.append("nuXmv");
		} else {
			sb.append("NuSMV");
		}
		if (!AsmetaSMVOptions.isPrintCounterExample()) {
			sb.append(" -dcx");
		}
		sb.append(" -dynamic");
		if (AsmetaSMVOptions.useCoi) {
			sb.append(" -coi");
		}
		sb.append(" -quiet ").append(smvFileName).append("\n");
		InputStream inputStream = proc.getInputStream();
		InputStream errorStream = proc.getErrorStream();
		InputStreamReader inputStreamR = new InputStreamReader(inputStream);
		InputStreamReader errorStreamR = new InputStreamReader(errorStream);
		BufferedReader brInput = new BufferedReader(inputStreamR);
		BufferedReader brError = new BufferedReader(errorStreamR);

		// per provare a catturare l'output stream e l'error stream in modo
		// corretto
		/*
		 * StringBuilder sb2 = new StringBuilder(); StreamGobbler errorGobbler = new
		 * StreamGobbler(proc.getErrorStream(),sb2); StreamGobbler outputGobbler = new
		 * StreamGobbler(proc.getInputStream(),sb2); errorGobbler.start();
		 * outputGobbler.start();
		 */

		String str = brInput.readLine();
		while (str != null) {
			sb.append(str + "\n");
			str = brInput.readLine();
		}
		str = brError.readLine();
		boolean error = false;
		if (str != null) {
			// error = true;
			sb.append("\nNuSMV has outputted the following output:\n");
		} else {
			// error = false;
		}
		while (str != null) {
			// here we only throw the Java Exception if it is indeed a NuSMV error and not a
			// warning
			// TODO: add other error messages if needed
			if (str.contains("Aborting batch mode")) {
				error = true;
			}
			sb.append(str + "\n");
			str = brError.readLine();
		}
		if (error)
			throw new RuntimeException(sb.toString());
		brInput.close();
		brError.close();
		return sb.toString();
	}

	/**
	 * Sort length.
	 * 
	 * @param set the set
	 * 
	 * @return the list< string>
	 */
	public static List<String> sortLength(Set<String> set) {
		final Comparator<String> LENGTH_ORDER = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				int s1Length = s1.length();
				int s2Length = s2.length();
				if (s1Length > s2Length) {
					return -1;
				} else if (s1Length == s2Length) {
					return 0;
				} else {
					return 1;
				}
			}
		};
		List<String> list = new ArrayList<String>(set);
		Collections.sort(list, LENGTH_ORDER);
		return list;
	}

	// Used by AsmetaMA
	public void addCtlProperties(Set<String> properties) throws Exception {
		assert properties.size() > 0 : "The list is not expected to be empty.";
		if (properties.size() > 0) {
			if (!mv.ctlList.addAll(properties)) {
				throw new Exception("An error occurred while adding properties.");
			}
			for (int i = 0; i < properties.size(); i++) {
				mv.ctlListNames.add("");
			}
		}
	}
	
	public void addLtlProperties(Set<String> properties) throws Exception {
		assert properties.size() > 0 : "The list is not expected to be empty.";
		if (properties.size() > 0) {
			if (!mv.ltlList.addAll(properties)) {
				throw new Exception("An error occurred while adding properties.");
			}
			for (int i = 0; i < properties.size(); i++) {
				mv.ltlListNames.add("");
			}
		}
	}

	public HashMap<Integer, String> getPropertiesCounterExample() {
		return mv.getPropertiesCounterExample();
	}

	public HashMap<Integer, String> getPropertiesForPrinting() {
		return mv.getPropertiesForPrinting();
	}

	public String getSmvFileName() {
		return smvFileName;
	}
}

class Name implements Comparable<Name> {
	private String name;

	Name(String s) {
		name = s;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Name n) {
		int currentNameLength = name.length();
		int otherNameLength = n.toString().length();
		if (currentNameLength > otherNameLength) {
			return -1;
		} else if (currentNameLength == otherNameLength) {
			return 0;
		} else {
			return 1;
		}
	}

}

class Counter implements Runnable {
	boolean run;

	@Override
	public void run() {
		int i = 25;// all'inizio il cursore del punto e' all'indice 25, dopo la
					// stringa "Execution of NuSMV code:"
		run = true;
		out.print("Execution of NuSMV code: ");
		while (run) {
			out.print(".");
			i++;
			if (i == 80) {
				i = 0;
				out.println();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class StreamGobbler extends Thread {
	InputStream is;
	StringBuilder sb;

	StreamGobbler(InputStream is, StringBuilder sb) {
		this.is = is;
		this.sb = sb;
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
