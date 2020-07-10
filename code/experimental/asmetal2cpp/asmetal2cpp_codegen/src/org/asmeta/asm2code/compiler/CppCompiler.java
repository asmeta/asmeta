package org.asmeta.asm2code.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * class where to put the C++ compiler functionalities
 * 
 * @author garganti
 *
 */
public class CppCompiler {

	private static String C_MIN_GW_BIN_G_EXE;	
	
	static {
		// try to set the c++ compiler
		searchCppCompiler("C:\\MinGW\\bin\\g++.exe");
		//searchCppCompiler("C:\\MinGW\\bin\\g++.exe");
	}
	
	static void searchCppCompiler(String cpppath) {
		if (Files.isExecutable(Paths.get(cpppath)))  C_MIN_GW_BIN_G_EXE = cpppath;
		
	}
	
	
	static private Logger logger = Logger.getLogger(CppCompiler.class);

	/**
	 * compile a cpp file in a directory
	 * 
	 * @param name         with cpp extension (it can be * for example)
	 * @param directory    in which cpp (it can relative in case)
	 * @param compileOnly  compile only, do not link - otherwise link
	 * @param evalCoverage TODO
	 * @return
	 */
	public static CompileResult compile(String name, String dir, boolean compileOnly, boolean evalCoverage) {
		try {
			File directory = new File(dir);
			assert directory.isDirectory();
			// if compile only, it must exist and end with .cpp
			assert !compileOnly || name.endsWith(".cpp") : name + " does not end with cpp";
			// otherwise can be end with ".o"
			// TODO allow also g++ *.cpp???? non compile?
			assert compileOnly || name.endsWith(".o") : name + " does not end with .o";
			assert !compileOnly || Files.isRegularFile(Paths.get(directory.getAbsolutePath(), name)) : "<" + name + ">"
					+ " is not a file";
			List<String> command = new ArrayList<>();
			String oFile; // outputfile
			if (compileOnly) {
				// remove .cpp
				int pos = name.lastIndexOf(".");
				String nameNoExt = name.substring(0, pos);
				// delete the .o file (to check if it has been produced after)
				oFile = directory.getPath() + '/' + nameNoExt + ".o";
				// delete so I can check the success if .o file is present
				command.addAll(Arrays.asList(C_MIN_GW_BIN_G_EXE, "-c", "-std=c++11"));
				if (evalCoverage)
					command.addAll(Arrays.asList("-fprofile-arcs", "-ftest-coverage"));
				command.add(nameNoExt + ".cpp");
			} else {
				// in this case, link !! (assume that boost is needed
				// -lgcov --coverage is needed otherwise I get an error.
				// TODO
				command.addAll(Arrays.asList(C_MIN_GW_BIN_G_EXE, "-std=c++11", "-lgcov", "--coverage"));
				if (evalCoverage)
					command.addAll(Arrays.asList("-fprofile-arcs", "-ftest-coverage"));
				command.add(name);
				command.add("-lboost_unit_test_framework");
				oFile = directory.getPath() + '/' + "a.exe"; // assuming windows
			}
			// delete the file if already exists
			Files.deleteIfExists(Paths.get(oFile));
			// executing
			logger.debug("executing");
			for (String c : command) {
				logger.debug(c);
			}
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			builder.directory(directory);
			Process process;
			process = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = r.readLine();
			while (line != null) {
				logger.debug(line);
				line = r.readLine();
			}
			process.waitFor();
			if (Files.exists(Paths.get(oFile))) {
				logger.info("File " + name + " compiled successfully!");
				return new CompileResult(true, "");
			}
			return new CompileResult(false, " not compiled");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new CompileResult(false, " not compiled" + e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new CompileResult(false, " not compiled" + e.getMessage());
		}
	}

}
