package org.asmeta.asmeta.evotest.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.annotations.TestToMavenSkip;
import org.asmeta.evoasmetatg.main.EvoAsmetaTgCLI;
import org.asmeta.parser.ASMParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * This test class tests the entire application directly via the CLI with all
 * the asm specifications inside the src/test/resources folder and check the
 * return codes. It must be ignored by maven because we need to specify the jdk
 * folder on our pc and otherwise it would fail the automatic test in the
 * pipelines. ( ignored using the maven-surefire-plugin and excluding the group
 * TestToMavenSkip from the Asmeta Parser).
 */
public class CLIExampleFilesTest {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(CLIExampleFilesTest.class);

	/* Constants */
	private static final String TEST_RESOURCES_DIR = "src" + File.separator + "test" + File.separator + "resources";
	private static final String JAVA_PATH = "-javaPath";
	private static final String JAVA_PATH_VALUE = "\"C:\\Program Files\\Java\\jdk-1.8\"";
	private static final String INPUT = "-input";
	private static final String OUTPUT = "-output";
	private static final String OUTPUT_DIR = "tempOutput";
	private static final String EVOSUITE_VERSION = "-evosuiteVersion";
	private static final String EVOSUITE_VERSION_VALUE = "1.0.6";
	private static final String TIME_BUDGET = "-timeBudget";
	private static final String TIME_BUDGET_VALUE = "5";
	private static final String CLEAN = "-clean";
	private static final String ASM_EXTENSION = ASMParser.ASM_EXTENSION;

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
		File testResourcesDir = new File(TEST_RESOURCES_DIR);
		assertTrue(testResourcesDir.exists());
		assertTrue(testResourcesDir.isDirectory());
		this.asmFiles = new LinkedList<>();
		this.tempOutputDir = new File(OUTPUT_DIR);
		this.tempOutputDir.mkdir();
		assertTrue(tempOutputDir.exists());
		assertTrue(tempOutputDir.isDirectory());
		for (File file : testResourcesDir.listFiles()) {
			if (file.getName().endsWith(ASM_EXTENSION)) {
				logger.info("Adding the file: {} to the test list.", file.getName());
				this.asmFiles.add(file);
			}

		}
	}

	/**
	 * Test all the files by running the application and check the returned code.
	 */
	@Test
	@Category(TestToMavenSkip.class)
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
				"\"" + tempOutputDir.getAbsolutePath() + "\"", JAVA_PATH, JAVA_PATH_VALUE, EVOSUITE_VERSION,
				EVOSUITE_VERSION_VALUE, TIME_BUDGET, TIME_BUDGET_VALUE, CLEAN);

		logger.info("args: {} ", args);

		EvoAsmetaTgCLI.main(args.toArray(new String[0]));
		return EvoAsmetaTgCLI.getReturnedCode();
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
