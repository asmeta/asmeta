package org.asmeta.junit2avalla.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This test class tests the entire application directly via the CLI with all
 * the java scenarios inside the src/test/resources folder and check the
 * return codes.
 */
public class CLIExampleFilesTest {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(CLIExampleFilesTest.class);

	/* Constants */
	static final Path TEST_RESOURCES_DIR_PATH = Paths.get("src", "test", "resources");
	static final String INPUT = "-input";
	static final String OUTPUT = "-output";
	static final String OUTPUT_DIR = "tempOutput";
	static final String CLEAN = "-clean";
	static final String JAVA = ".java";

	/** temp output folder */
	private File tempOutputDir;

	/** list of all the asm specification to test. */
	private List<File> asmFiles;

	/**
	 * Setup the test environment.
	 * <p>
	 * 
	 * Create the temp output folder and populate the asmFiles list.
	 * 
	 */
	@Before
	public void setup() {
		File testResourcesDir = TEST_RESOURCES_DIR_PATH.toFile();
		assertTrue(testResourcesDir.exists());
		assertTrue(testResourcesDir.isDirectory());
		this.asmFiles = new LinkedList<>();
		this.tempOutputDir = new File(OUTPUT_DIR);
		this.tempOutputDir.mkdir();
		assertTrue(tempOutputDir.exists());
		assertTrue(tempOutputDir.isDirectory());
		for (File file : testResourcesDir.listFiles()) {
			if (file.getName().endsWith(JAVA)) {
				logger.info("Adding the file: {} to the test list.", file.getName());
				this.asmFiles.add(file);
			}

		}
	}

	/**
	 * Test all the files by running the application and check the returned code.
	 */
	@Test
	public void test() {

		for (File file : asmFiles) {
			assertEquals(0, testFile(file));
		}

	}

	/**
	 * Start the CLI application to generate the Avalla scenario for the specified
	 * asmeta file
	 * 
	 * @param file the asmeta specification.
	 * @return -1: The application didn't started yet. <br>
	 *         0: The application terminated without errors.<br>
	 *         1: The application terminated with errors.
	 */
	private int testFile(File file) {

		logger.info("\n\n=== {} ===================", file.getName());

		List<String> args = List.of(INPUT, "\"" + file.getAbsolutePath() + "\"", OUTPUT,
				"\"" + tempOutputDir.getAbsolutePath() + "\"", CLEAN);

		logger.info("args: {} ", args);

		Junit2AvallaCLI.main(args.toArray(new String[0]));
		return Junit2AvallaCLI.getReturnedCode();
	}

	/**
	 * Clean the temp output folder.
	 */
	@After
	public void cleanTemp() {
		logger.info("Cleaning the temp folder {}. ", tempOutputDir);
		for (File file : tempOutputDir.listFiles()) {
			file.delete();
		}
		tempOutputDir.delete();
	}

}
