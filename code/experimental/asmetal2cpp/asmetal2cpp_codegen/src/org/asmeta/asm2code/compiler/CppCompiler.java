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
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * class where to put the C++ compiler functionalities
 * 
 * @author garganti
 *
 */
public class CppCompiler {

	private static String G_EXE;

	private static boolean USE_CPP_11 = false;

	public static String extraOptionsWhenCompiling = "";

	private static String OS = System.getProperty("os.name").toLowerCase();

	// set the compiler. If it cannot find the compiler, it will return false
	// compiler can be like:
	// g++ (only the name) -> search in the path the g++.exe or similar (according
	// to the OS) if not found -> return false
	// "C:\\MinGW\\bin\\g++.exe" -> absolute path, then it will checkif it exists
	// and will throw an exception
	public static boolean setCompiler(String compiler) {
		logger.debug("setting the compiler to " + compiler);
		//if it is 
		if (Files.isExecutable(Paths.get(compiler))){
			G_EXE = compiler;
	       return true;
	    }
		// serach in the path
		final String compilerName = compiler += (isWindows() && ! compiler.endsWith(".exe")) ? ".exe": "";
		Map<String, String> env = System.getenv();
		String path = env.get("Path");
		logger.debug("searching the compiler in the path " + path);
		for(String dirInPath: path.split(File.pathSeparator)){
			File f = new File(dirInPath);
			if (f.isDirectory()) {
				if (Arrays.asList(f.listFiles()).stream().anyMatch( x-> (x.getName().equals(compilerName)&& x.canExecute()))){
					logger.debug("cpp compiler " + compilerName + " found in " + dirInPath);
					G_EXE = compiler;
					return true;
				}
			}
		}		
		return false;
	}

	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	static private Logger logger = Logger.getLogger(CppCompiler.class);

	/**
	 * compile a cpp file in a directory
	 * 
	 * @param name         with cpp extension (it can be * for example)
	 * @param compileOnly  compile only, do not link - otherwise link
	 * @param evalCoverage TODO
	 * @param useBoost     TODO
	 * @param directory    in which cpp (it can relative in case)
	 * @return
	 */
	public static CompileResult compile(String name, String dir, boolean compileOnly, boolean evalCoverage,
			boolean useBoost) {
		if (G_EXE == null) throw new RuntimeException("compiler not set");
		try {
			File directory = new File(dir);
			assert directory.isDirectory();
			// if compile only, it must exist and end with .cpp
			assert !compileOnly || name.endsWith(".cpp") : name + " does not end with cpp";
			// otherwise can be end with ".o"
			// TODO allow also g++ *.cpp???? non compile?
			assert compileOnly || name.endsWith(".o") : name + " does not end with .o";
			assert !compileOnly || Files.isRegularFile(Paths.get(directory.getAbsolutePath(), name))
					: "<" + name + ">" + " is not a file";
			List<String> command = new ArrayList<>();
			String oFile; // outputfile
			if (compileOnly) {
				// remove .cpp
				int pos = name.lastIndexOf(".");
				String nameNoExt = name.substring(0, pos);
				// delete the .o file (to check if it has been produced after)
				oFile = directory.getPath() + '/' + nameNoExt + ".o";
				// delete so I can check the success if .o file is present
				command.addAll(Arrays.asList(G_EXE, "-c"));
				if (USE_CPP_11)
					command.add("-std=c++11");
				if (evalCoverage)
					command.addAll(Arrays.asList("-fprofile-arcs", "-ftest-coverage"));
				// add extra options
				command.addAll(Arrays.asList(extraOptionsWhenCompiling.split(" ")));
				//
				command.add("-o" + nameNoExt + ".o");
				command.add(nameNoExt + ".cpp");
			} else {
				// in this case, link !! (assume that boost is needed
				// -lgcov --coverage is needed otherwise I get an error.
				// TODO
				command.addAll(Arrays.asList(G_EXE));
				if (USE_CPP_11)
					command.add("-std=c++11");
				if (evalCoverage)
					command.addAll(Arrays.asList("-fprofile-arcs", "-ftest-coverage", "-lgcov", "--coverage"));
				command.add(name);
				if (useBoost)
					command.add("-lboost_unit_test_framework");
				oFile = directory.getPath() + '/' + "a.exe"; // assuming windows
			}
			// delete the file if already exists
			Files.deleteIfExists(Paths.get(oFile));
			// executing
			logger.debug("executing " + command.toString());
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
			logger.debug("checking existance " + oFile);
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
