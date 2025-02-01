package asmeta.evotest.junit2avalla.javascenario;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioLexer;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ScenarioReaderImpl} class provides functionality to read and parse
 * javaScenario from a file. It uses a lexer and parser generated from the
 * {@code JavaScenario} grammar to convert the contents of the file into a list
 * of {@link Scenario} objects.
 */
public class ScenarioReaderImpl implements ScenarioReader {

	/** Logger */
	private final Logger log = LogManager.getLogger(ScenarioReaderImpl.class);

	/** The type of the parser, initialized at customParser */
	private ParserType parserType = ParserType.CUSTOM_PARSER;

	/**
	 * Creates a ScenarioReader instance with the customParser as default parserType
	 */
	public ScenarioReaderImpl() {
		// Empty constructor
	}

	@Override
	public void setType(String type) throws IllegalArgumentException {
		this.parserType = ParserType.fromValue(type);
	}

	/**
	 * Reads a java scenario from the file at the specified {@code path} and parses
	 * its content to retrieve a list of {@link Scenario} objects.
	 *
	 * @param path the {@link Path} to the file containing the scenario
	 * @return a list of {@link Scenario} objects parsed from the file, or an empty
	 *         list if an error occurs
	 */
	@Override
	public List<Scenario> readJavaScenario(Path path) {
		log.info("Reading the Scenario File at the path {}", path);
		String javaFile = null;
		try {
			byte[] fileBytes = Files.readAllBytes(path);
			javaFile = new String(fileBytes, StandardCharsets.UTF_8);
			log.debug("Content red: {} ", javaFile);
		} catch (IOException e) {
			log.error("An exception occurred while reading the file: {}", e.getMessage(), e);
		}
		List<Scenario> javaScenarios = null;
		switch (parserType) {
		case CUSTOM_PARSER:
			javaScenarios = readJavaScenarioUsingCustomParser(javaFile);
			break;
		case JAVA_PARSER:
			log.error("NOT SUPPORTED YET.");
			throw new IllegalArgumentException(parserType.getType() + " not yet supported."); 
		}
		return javaScenarios;
	}

	/**
	 * 
	 * @param javaFile
	 * @return
	 */
	private List<Scenario> readJavaScenarioUsingCustomParser(String javaFile) {
		log.info("Parsing the JUnit file using the custom parser.");
		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaScenarioListener javaScenarioWalker = new JavaScenarioListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());
		return javaScenarioWalker.getScenarioList();
	}

}
