package org.asmeta.asm2java.main;

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
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.config.TranslatorOptions;
import org.asmeta.asm2java.evosuite.RulesImpl;

import asmeta.AsmCollection;

/**
 * The {@code FileManagerImpl} class provides an implementation of the {@link FileManager} interface.
 */
public class FileManagerImpl implements FileManager {

	/* constants */
	private static final String ATG_EXTENSION = "_ATG.java";
	private static final String WIN_EXTENSION = "_Win.java";
	private static final String EXE_EXTENSION = "_Exe.java";
	private static final String JAVA_EXTENSION = ".java";
	private static final String USER_DIR = "user.dir";
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	private static final String TEST_GEN = "testGen";
	private static final String EXECUTION = "execution";
	private static final String COMPILER = "compiler";
	private static final String TRANSLATION = "translation";
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(FileManagerImpl.class);
	
	/** Generator of the java class */
	private static final JavaGenerator jGenerator = new JavaGenerator();

	/** Generator of the _Exe java class */
	private static final JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	
	/** Generator of the _Win java class */
	private static final JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();
	
	/** Instance of the RulesImpl, a Map {name:Rule} collection containing the rules of the Asmeta specification */
	private static RulesImpl rulesImpl = new RulesImpl();
	
	/** Generator of the java class used for test generation */
	private static JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesImpl);
	
	/** Generator of the _ASM java class */
	private static JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesImpl);
	
	/** Path to the input directory. */
	private static final Path INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);
	
	/** Path to the default output directory. */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);

    /** Path to the translation folder. */
    private static final Path TRANSLATION_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), TRANSLATION);

    /** Path to the compiler folder. */
    private static final Path COMPILER_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), COMPILER);

    /** Path to the execution folder. */
    private static final Path EXECUTION_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), EXECUTION);

    /** Path to the test generation folder. */
    private static final Path TESTGEN_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), TEST_GEN);
	
    /** Path to the output folder. */
    private Path outputFolder;

    /**
     * Constructs a {@code FileManagerImpl} instance with the default output directory.
     */
	public FileManagerImpl() {
		this.outputFolder = DEFAULT_OUTPUT_DIR_PATH;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public File retrieveInput(String asmspec) throws IOException {
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
	 * {@inheritDoc}
	 */
	@Override
	public File generateFile(String name, AsmCollection model, TranslatorOptions userOptions, Mode mode) throws IOException {
		
		File javaFile = null;
		
		switch(mode) {
		case TRANSLATOR:
			checkPath(TRANSLATION_DIR_PATH);
			javaFile = new File(TRANSLATION_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaGenerator: generating the .java class...");
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case COMPILER:
			checkPath(COMPILER_DIR_PATH);
			javaFile = new File(COMPILER_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaGenerator: generating the .java class...");
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case GENERATE_EXE:
			checkPath(EXECUTION_DIR_PATH);
			javaFile = new File(EXECUTION_DIR_PATH + File.separator + name + EXE_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaExeGenerator: generating the _Exe.java class...");
			jGeneratorExe.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case GENERATE_WIN:
			checkPath(EXECUTION_DIR_PATH);
			javaFile = new File(EXECUTION_DIR_PATH + File.separator + name + WIN_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaWinGenerator: generating the _Win.java class...");
			jGeneratorWin.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case TRANSLATOR_TEST:
			checkPath(TESTGEN_DIR_PATH);
			javaFile = new File(TESTGEN_DIR_PATH + File.separator + name + JAVA_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaAtgGenerator: generating the .java test class...");
			jGeneratorTest.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		case TEST_GEN:
			checkPath(TESTGEN_DIR_PATH);
			javaFile = new File(TESTGEN_DIR_PATH + File.separator + name + ATG_EXTENSION);
			deleteExisting(javaFile);
			logger.info("JavaAtgGenerator: generating the _ATG.java class...");
			jGeneratorAtg.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			break;
		default:
			break;
		
		}
		
		return javaFile;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompileResult compileFile(String asmName) {
		logger.info("JavaCompiler: compiling the .java class...");
		checkPath(COMPILER_DIR_PATH);
		return CompilatoreJava.compile(asmName + JAVA_EXTENSION, COMPILER_DIR_PATH, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exportFile(File javaInputFile) {
		logger.info("Exporting " + javaInputFile + " to: " + outputFolder.toString());
		checkPath(outputFolder);
		File javaOutFile = new File(outputFolder + File.separator + javaInputFile.getName());
		try (InputStream in = new BufferedInputStream(Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(javaOutFile.toPath()))) {
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
	 * {@inheritDoc}
	 */
	@Override
	public void cleanInputDir() {
		if (INPUT_DIR_PATH.toFile().exists() && INPUT_DIR_PATH.toFile().isDirectory()) {
			for (File file : INPUT_DIR_PATH.toFile().listFiles()) {
				if (!file.getName().equals("STDL") && !file.getName().equals(".gitignore")) {
					this.cleanRecursively(file);
				}
			}
		}
	}
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void setOutputDir(String outputDir) {
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

		if (file.delete()) {
			logger.info("Deleted: " + file.getAbsolutePath());
		} else {
			logger.error("Failed to delete: " + file.getAbsolutePath());
		}

	}
	

	/**
	 * Checks whether the specified path exists, and if not, creates it.
	 *
	 * @param path the path to check.
	 */
	private void checkPath(Path path) {
		if( !Files.exists(path) ) {
			this.createPath(path);
		}
	}
	
	/**
	 * Creates a directory at the specified path.
	 *
	 * @param path The path where the directory should be created.
	 */
	private void createPath(Path path) {
		try {
			logger.info("Path " + path + " doesn't exist.");
			logger.info("Creating path " + path + " ...");
			Files.createDirectories(path);
			logger.info("Path" + path + " created with success.");
		} catch (IOException e) {
			logger.error("Failed to create path: path");
			throw new RuntimeException("Failed to create path: " + path, e);
		}
	}
	
	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 * @throws IOException if an IO error occurs (and execution should be stopped).
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
