package asmeta.asmetal2java.codegen.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.compiler.CompileResultImpl;
import asmeta.asmetal2java.codegen.compiler.Compiler;
import asmeta.asmetal2java.codegen.compiler.CompilerImpl;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;

/**
 * Utility class for testing.
 * <p>
 * contains the path to the folders, the list of files to exclude from testing
 * and methods to generate and compile the translation.
 */
public class GeneratorCompilerUtil {

	/** Path of the directory where the example files are stored */
	public static final Path dirExamples = Path.of("examples");

	/**
	 * Path to the directory where the translation-related .class files are stored
	 */
	public static final Path dirCompilazione = Path.of(dirExamples.toString(), "compilazione");

	/** Path to the directory where the translsted files are stored */
	public static final Path dirTraduzione = Path.of(dirExamples.toString(), "traduzione");

	/**
	 * Path to the directory where the translation-related execution files are
	 * stored
	 */
	public static final Path dirEsecuzione = Path.of(dirExamples.toString(), "esecuzione");

	/**
	 * List of libraries that should be excluded from testing
	 */
	static List<String> libraries = List.of("StandardLibrary.asm", "LTLLibrary.asm", "CTLLibrary.asm");

	/**
	 * List of asm files with known issues: these files have compilation errors
	 * related to the translation.
	 */
	static List<String> errors = List.of("battleship.asm", "fibonacci.asm", "QuickSort.asm", "testSignature.asm",
			"SIS.asm");

	/**
	 * The following files have compilation errors already in the .asm file and
	 * asmetal2java correctly throws exceptions
	 */
	static List<String> parseException = List.of(/* Empty */);

	/**
	 * The following classes have runtime errors
	 */
	static List<String> runtimeException = List.of("gameOfLife.asm", "groundModel_v2.asm", "Bare.asm", "ProdDomain.asm",
			"population.asm", "LIFT.asm");

	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();
	// static private JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	// static private JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();

	/**
	 * generates the java code and compiles it.
	 *
	 * @param asmspec     the path of the spec
	 * @param userOptions the user options
	 * @param targetJava  the target directory where to put the java code generated
	 * @param targetClass the target directory where to put the class - it can be
	 *                    null if compilation is not required
	 * @return the results of the compilation of the java generated
	 * @throws Exception the exception
	 */
	public static CompileResult genandcompile(String asmspec, TranslatorOptions userOptions, Path targetJava,
			Path targetClass) throws Exception {
		// get the name of the asmeta spec
		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		System.out.println(asmname);
		String name = asmname.substring(0, asmname.lastIndexOf("."));
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser - if error do not continue
		AsmCollection model = parseSpec(asmFile);

		// get the names of the files
//		String dirEsecuzione = asmFile.getParentFile().getPath() + "/esecuzione";
//		String dirWin = asmFile.getParentFile().getPath() + "/window";
//		File javaFileExe = new File(dirEsecuzione + File.separator + name + "_Exe.java");
//		File javaFileExeN = new File(dirEsecuzione + File.separator + name + ".java");
//		File javaFileWin = new File(dirWin + File.separator + name + "_Win.java");
//		File javaFileWinN = new File(dirWin + File.separator + name + ".java");
//		
//		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");
//		File javaFileExeT = new File(dirTraduzione + File.separator + name + "_Exe.java");
//		File javaFileWinT = new File(dirTraduzione + File.separator + name + "_Win.java");

		// 1. name of the generated java file
		assert Files.exists(targetJava) && Files.isDirectory(targetJava);
		File javaFile = new File(targetJava.toString() + File.separator + name + ".java");
		// Check if the file exists and delete it
		deleteExisting(javaFile);

		// 2. name of the .class
		File javaFileCompilazione = null;
		if (targetClass != null) {
			assert Files.exists(targetClass) && Files.isDirectory(targetClass);
			javaFileCompilazione = new File(targetClass.toString() + File.separator + name + ".class");
			// delete it if exists
			deleteExisting(javaFileCompilazione);
		}

		/*
		 * deleteExisting(javaFileExe); deleteExisting(javaFileExeN);
		 * deleteExisting(javaFileWin); deleteExisting(javaFileWinN);
		 * deleteExisting(javaFileT); deleteExisting(javaFileExeT);
		 * deleteExisting(javaFileWinT);
		 */

		System.out.println("=== " + name + " === translating in " + targetJava
				+ (targetJava != null ? " compiling in " + targetClass : ""));
		//
		// TRANSALTE to Java
		//
		try {
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExe.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileExeN.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWin.getCanonicalPath(), userOptions);
//			jGenerator.compileAndWrite(model.getMain(), javaFileWinN.getCanonicalPath(), userOptions);

//			jGenerator.compileAndWrite(model.getMain(), javaFileT.getCanonicalPath(), userOptions);
//			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExeT.getCanonicalPath(), userOptions);
//			jGeneratorWin.compileAndWrite(model.getMain(), javaFileWinT.getCanonicalPath(), userOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResultImpl(false, e.getMessage());
		}
		assert javaFile.exists();
		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileCompilazione.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileExeN.getCanonicalPath());
//		System.out.println("Generated java file: " + javaFileWinN.getCanonicalPath());
//		System.out.println("Generated java file for the execution: " + javaFileExe.getCanonicalPath());
//		System.out.println("Generated java file for window: " + javaFileWin.getCanonicalPath());

//		System.out.println("All java files Generated in: " + javaFileT.getCanonicalPath());
		String separator = "===================================================================================================================================================";
		//
		// COMPILE java if required
		//
		if (targetClass != null) {
			Compiler compiler = new CompilerImpl();
			CompileResult result = compiler.compileFile(javaFile, targetClass, "17");
			System.out.println(separator);
			if (result.getSuccess())
				assert javaFileCompilazione.exists();
			return result;
		} else {
			System.out.println(separator);
			return new CompileResultImpl(true, " java generated with success");
		}

	}

	/**
	 * Parse the asmeta specification using the asmeta parser and returns the model.
	 * Asserts that the model is not equal to null.
	 * Stop the test execution if there is an error during the parsing process.
	 * 
	 * @param asmFile the asmeta spec to parse.
	 * @return the parsed model (!= null).
	 * @throws Exception if an error occurs during the parsing process.
	 */
	public static AsmCollection parseSpec(File asmFile) throws Exception {
		AsmCollection model = null;
		try {
			model = ASMParser.setUpReadAsm(asmFile);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			// assuming that the specs are correct
			fail("error in parsing asmeta example: " + asmFile.getAbsolutePath());
		}
		assert model != null;
		return model;
	}

	/**
	 * Configuration steps to perform before the testing process. Verify that the
	 * destination folder where the files to be translated are stored exists and
	 * check/create the translation and compilation folders
	 * 
	 * @param filesFolderPath path to the folder of the file to be translated
	 */
	public static void setupFolders(Path filesFolderPath) {
		assertTrue(filesFolderPath.toFile().exists());
		assertTrue(filesFolderPath.toFile().isDirectory());
		File dirCompF = GeneratorCompilerUtil.dirCompilazione.toFile();
		File dirTradF = GeneratorCompilerUtil.dirTraduzione.toFile();
		// create if it does not exists
		GeneratorCompilerUtil.checkDir(dirCompF);
		GeneratorCompilerUtil.checkDir(dirTradF);
	}

	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 * @throws IOException if an IO error occurs.
	 */
	public static void deleteExisting(File file) throws IOException {
		if (file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
				fail("File not found : " + file.getPath());
			}
		}
		assert !file.exists();
	}

	/**
	 * Check if the directory exists, and in case not, creates a new one.
	 * 
	 * @param dir the File to check.
	 */
	public static void checkDir(File dir) {
		if (!dir.exists())
			dir.mkdirs();
		assert dir.exists() && dir.isDirectory();
	}

}
