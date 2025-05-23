package asmeta.evotest.junit2avalla.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import asmeta.evotest.junit2avalla.javascenario.ParserType;

/**
 * Automatically translates all the tests in the junit folder and compares them
 * with the translations in the avalla folder
 */
public class ComparisonProjectTest {

	/* Constants */
	private static final String AVALLA_FOLDER = "avalla";
	private static final String JUNIT_FOLDER = "junit";
	private static final String TRANSLATION_FOLDER = "translation";
	private static final String SCENARIO_NAME_DEFAULT = "_scenario0.avalla";
	private static final String ATG_SPLIT = "_ATG";
	private static final String SCENARIO_SPLIT = "_scenario";

	/** Test resource folder */
	private static final Path resourcePath = CLIExampleFilesTest.TEST_RESOURCES_DIR_PATH;

	/** Contains the junit files to translate */
	private static final Path junitPath = resourcePath.resolve(JUNIT_FOLDER);

	/** Contains the avalla file already translated (correctly) */
	private static final Path avallaPath = resourcePath.resolve(AVALLA_FOLDER);

	/** Contains the translations of the junit files */
	private static final Path translationPath = resourcePath.resolve(TRANSLATION_FOLDER);

	/** List of junit files to translate */
	List<File> junitFiles;

	/** Map of the avalla files to be compared with {Name:File} */
	Map<String, File> avallaFilesMap;

	@Before
	public void setup() throws IOException {
		assert resourcePath.toFile().exists();
		File junitFile = junitPath.toFile();
		assert junitFile.exists() && junitFile.isDirectory();
		junitFiles = List.of(junitFile.listFiles());
		File avallaFile = avallaPath.toFile();
		assert avallaFile.exists() && avallaFile.isDirectory();
		avallaFilesMap = List.of(avallaFile.listFiles()).stream()
				.collect(Collectors.toMap(file -> file.getName().split(SCENARIO_SPLIT)[0], file -> file));
		File translationFile = translationPath.toFile();
		if (!translationFile.exists()) {
			Files.createDirectory(translationPath);
		}
		assert translationFile.exists() && translationFile.isDirectory();
	}

	/**
	 * Generates the translation of a list of JUnit files and checks if the
	 * generated Avalla file is identical to the expected one, first parses with the
	 * custom parser and then with JavaParser
	 */
	@Test
	public void comparisonTest() {
		System.out.println("Comparison test using CUSTOM PARSER: ");
		testAll(ParserType.CUSTOM_PARSER);
		System.out.println("\n--------------------------------------------------------");
		System.out.println("Comparison test using JAVA PARSER: ");
		testAll(ParserType.JAVA_PARSER);
	}

	/**
	 * Generate the translation for all the files in the junitFiles list and execute
	 * the comparison test
	 * 
	 * @param parserType type of the parser to use
	 */
	private void testAll(ParserType parserType) {
		for (File junit : junitFiles) {
			File avalla = avallaFilesMap.get(junit.getName().split(ATG_SPLIT)[0]);
			if (avalla != null) {
				System.out.println("\n===" + junit.getName() + "===================");
				File translation = genTranslation(junit, parserType.getType());
				try {
					assertTrue(comparisonTest(avalla, translation));
				} catch (IOException e) {
					fail();
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Generate the avalla translation.
	 * 
	 * @param junitFile junit file to translate
	 * @return the translated avalla file
	 */
	private File genTranslation(File junitFile, String parserType) {
		List<String> args = List.of(CLIExampleFilesTest.INPUT, "\"" + junitFile.getAbsolutePath() + "\"",
				CLIExampleFilesTest.OUTPUT, "\"" + translationPath + "\"", CLIExampleFilesTest.CLEAN,
				CLIExampleFilesTest.PARSER, parserType);
		Junit2AvallaCLI.main(args.toArray(new String[0]));
		assertEquals(0, Junit2AvallaCLI.getReturnedCode());
		File avallaFileTranslated = translationPath
				.resolve(junitFile.getName().split(ATG_SPLIT)[0].concat(SCENARIO_NAME_DEFAULT)).toFile();
		assert avallaFileTranslated.exists() && avallaFileTranslated.isFile();
		return avallaFileTranslated;
	}

	/**
	 * Compare the original avalla file with the translated one line by line.
	 * 
	 * @param avallaTest       file to be compared with
	 * @param avallaTranslated file to compare
	 * @return {@code True} if the two file are identical, {@code False} otherwise
	 * @throws IOException if an I/O error occurs
	 */
	private boolean comparisonTest(File avallaTest, File avallaTranslated) throws IOException {

		if (avallaTest == null || avallaTranslated == null || !avallaTest.exists() || !avallaTranslated.exists()) {
			return false;
		}

		try (BufferedReader br1 = new BufferedReader(new FileReader(avallaTest));
				BufferedReader br2 = new BufferedReader(new FileReader(avallaTranslated))) {

			String line1, line2;

			while ((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
				if (!line1.equals(line2)) {
					System.err.println("Difference detected:");
					System.err.println("avalla: \t" + line1);
					System.err.println("translation: \t" + line2);
					return false;
				}
			}

			return br1.readLine() == null && br2.readLine() == null;
		}
	}

}
