package org.asmeta.asm2java.translator.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.Logger;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.compiler.Compiler;
import org.asmeta.asm2java.compiler.impl.CompilerImpl;
import org.asmeta.asm2java.config.Mode;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.config.impl.ModeConstantsConfig;
import org.asmeta.asm2java.generator.Generator;
import org.asmeta.asm2java.generator.impl.GeneratorImpl;

import asmeta.AsmCollection;

/**
 * The {@code FileManagerImpl} class provides an implementation of the {@link FileManager} interface.
 */
public class FileManagerImpl {

	/* constants */
	private static final String ATG_EXTENSION = "_ATG.java";
	private static final String WIN_EXTENSION = "_Win.java";
	private static final String EXE_EXTENSION = "_Exe.java";
	private static final String JAVA_EXTENSION = ".java";
	private static final String USER_DIR = "user.dir";
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(FileManagerImpl.class);
	
	/** Path to the input directory. */
	private static final Path INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);
	
	/** Path to the default output directory. */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);

    /** Path to the translation folder. */
    private static final Path TRANSLATION_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), ModeConstantsConfig.TRANSLATOR);

    /** Path to the compiler folder. */
    private static final Path COMPILER_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), ModeConstantsConfig.COMPILER);

    /** Path to the exe folder. */
    private static final Path EXE_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), ModeConstantsConfig.GENERATE_EXE);
    
    /** Path to the win folder. */
    private static final Path WIN_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), ModeConstantsConfig.GENERATE_WIN);

    /** Path to the test generation folder. */
    private static final Path TESTGEN_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), ModeConstantsConfig.TEST_GEN);
	
    /** Generator instance for generate the java translation */
    private static final Generator generator = new GeneratorImpl();
    
    /** Compiler instance for compiling the java translation */
    private static final Compiler compilerJava = new CompilerImpl();
    
    /** Path to the output folder. */
    private Path outputFolder;

    /**
     * Constructs a {@code FileManagerImpl} instance with the default output directory.
     */
	FileManagerImpl() {
		this.outputFolder = DEFAULT_OUTPUT_DIR_PATH;
	}
	
	/**
     * Given a string containing the path to the input file, copies the file into the
     * input directory and returns the newly generated file.
     * 
     * @param file the path to the input file (relative or absolute).
     * @return the copied file.
     * @throws IOException if an I/O error occurs during the file copying process.
     */
	File retrieveInput(String asmspec) throws IOException {
		File asmFile = new File(asmspec);
		if (!asmFile.exists()) {
			logger.error("Failed to locate the input file:" + asmFile.toString());
			throw new IOException("File doesn't exist: " + asmFile.toString());
		}
		// Copy the asm file to the input folder
		Path inputAsmPath = Paths.get(INPUT_DIR_PATH.toString(), asmFile.getName());
		Files.copy(Paths.get(asmFile.getAbsolutePath()), inputAsmPath, StandardCopyOption.REPLACE_EXISTING);
		return asmFile;
	}

    /**
     * Generates a Java file in the specified directory, using the given name, extension, 
     * model, and translator options.
     * 
     * @param name the name of the file to be generated.
     * @param model the parsed ASM specification.
     * @param userOptions the translator options to be applied.
     * @param mode the mode for the translation process.
     * @return the generated file.
     * @throws IOException if an I/O error occurs during file generation.
     */
	File generateFile(String name, AsmCollection model, TranslatorOptions userOptions, Mode mode) throws IOException {
		
		File javaFile = null;
		
		switch(mode) {
		case TRANSLATOR_MODE:
			checkPath(TRANSLATION_DIR_PATH);
			javaFile = new File(TRANSLATION_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			generator.generateJava(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case COMPILER_MODE:
			checkPath(COMPILER_DIR_PATH);
			javaFile = new File(COMPILER_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			generator.generateJava(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case GENERATE_EXE_MODE:
			checkPath(EXE_DIR_PATH);
			javaFile = new File(EXE_DIR_PATH + File.separator + name + EXE_EXTENSION);
			deleteExisting(javaFile);
			generator.generateExe(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case GENERATE_WIN_MODE:
			checkPath(WIN_DIR_PATH);
			javaFile = new File(WIN_DIR_PATH + File.separator + name + WIN_EXTENSION);
			deleteExisting(javaFile);
			generator.generateWin(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case TRANSLATOR_TEST_MODE:
			checkPath(TESTGEN_DIR_PATH);
			javaFile = new File(TESTGEN_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			generator.generateTest(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case TEST_GEN_MODE:
			checkPath(TESTGEN_DIR_PATH);
			javaFile = new File(TESTGEN_DIR_PATH + File.separator + name + ATG_EXTENSION);
			deleteExisting(javaFile);
			generator.generateAtg(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		default:
			break;
		
		}
		
		return javaFile;
	}
	
    /**
     * Compiles the file with the given name and returns the result of the compilation process.
     * 
     * @param asmName the name of the ASM file to compile.
     * @return {@code true} if the the result of the compilation is successful, {@code false} otherwise.
     * @throws IOException if an I/O error occurs.
     */
	boolean compileFile(String asmName) throws IOException {
		logger.info("JavaCompiler: compiling the .java class...");
		checkPath(COMPILER_DIR_PATH);
		CompileResult result = compilerJava.compile(asmName + JAVA_EXTENSION, COMPILER_DIR_PATH, true);
		if(result.getSuccess()) {
			return true;
		}
		logger.error("Compilation errors: " + result.toString());
		return false;
	}

    /**
     * Exports (copies) the specified Java file to a desired output folder.
     *
     * @param javaFile the Java file to be exported.
     * @throws IOException if an I/O error occurs during the export.
     */
	void exportFile(File javaInputFile) {
		File javaOutFile = new File(outputFolder + File.separator + javaInputFile.getName());
		logger.info("Exporting " + javaInputFile + " to: " + outputFolder.toString());
		try (InputStream in = new BufferedInputStream(Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(javaOutFile.toPath()))) {
			checkPath(outputFolder);
			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		} catch (IOException e) {
			logger.error("Export Failed.");
            e.printStackTrace(); 
        }
		logger.info("Export completed.");
	}
	
	
    /**
     * Cleans the input directory by removing execution-related files.
     */
	void cleanInputDir() {
		if (INPUT_DIR_PATH.toFile().exists() && INPUT_DIR_PATH.toFile().isDirectory()) {
			for (File file : INPUT_DIR_PATH.toFile().listFiles()) {
				if (!file.getName().equals("STDL") && !file.getName().equals(".gitignore")) {
					this.cleanRecursively(file);
				}
			}
		}
	}
	
    /**
     * Sets the output directory where the generated files will be stored.
     * 
     * @param outputDir the path of the output directory.
     */
	void setOutputDir(String outputDir) {
		this.outputFolder = Paths.get(outputDir);
	}

    /**
     * Recursively deletes files and directories.
     * 
     * @param file the file or directory to delete.
     */
	private void cleanRecursively(File file) {

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					cleanRecursively(f);
				}
			}
		}

		try {
			Files.delete(file.toPath());
			logger.info("Deleted: " + file.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Failed to delete: " + file.getAbsolutePath() + "\nError message: " + e.getMessage());
		}
		

	}
	

	/**
	 * Checks whether the specified path exists, and if not, creates it.
	 *
	 * @param path the path to check.
	 * @throws IOException if an IO error occurs.
	 */
	private void checkPath(Path path) throws IOException {
		if( !Files.exists(path) ) {
			this.createPath(path);
		}
	}
	
	/**
	 * Creates a directory at the specified path.
	 *
	 * @param path The path where the directory should be created.
	 * @throws IOException if an IO error occurs.
	 */
	private void createPath(Path path) throws IOException {
		logger.info("Path " + path + " doesn't exist.");
		logger.info("Creating path " + path + " ...");
		Files.createDirectories(path);
		logger.info("Path" + path + " created with success.");
	}
	
	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 * @throws IOException if an IO error occurs.
	 */
	private void deleteExisting(File file) throws IOException {
		if (file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
                logger.error("File not found, skipping: " + file.getPath());
			}
		}
	}



}