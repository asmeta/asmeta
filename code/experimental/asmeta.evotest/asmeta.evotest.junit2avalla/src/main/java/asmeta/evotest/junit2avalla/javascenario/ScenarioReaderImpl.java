package asmeta.evotest.junit2avalla.javascenario;

import asmeta.evotest.junit2avalla.model.Scenario;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ScenarioReaderImpl} class provides functionality to read and parse
 * javaScenario from a file. It uses a lexer and parser generated from the
 * {@code JavaScenario} grammar to convert the contents of the file into a list
 * of {@link Scenario} objects.
 */
public class ScenarioReaderImpl implements ScenarioReader {

	/* Constants */
	private static final String JAVA_EXTENSION = ".java";

	/** Logger */
	private final Logger log = LogManager.getLogger(ScenarioReaderImpl.class);

	/** The type instance of the parser, initialized at javaParser */	
	private Parser parser = new JavaParser();

	/**
	 * Creates a ScenarioReader instance with the javaParser as default parserType
	 */
	public ScenarioReaderImpl() {
		// Empty constructor
	}

	@Override
	public void setParserType(String type) throws IllegalArgumentException {
		switch (ParserType.fromValue(type)) {
		case CUSTOM_PARSER:
			this.parser = new CustomParser();
			break;
		case JAVA_PARSER:
			this.parser = new JavaParser();
			break;
		default:
			log.error("NOT SUPPORTED.");
			throw new IllegalArgumentException(type + " not supported.");
		}
	}

	@Override
	public List<Scenario> readJavaScenario(Path path) throws IOException, JUnitParseException {
		log.info("Reading the Scenario File at the path {}", path);
		
		// read the JUnit file
		String javaFile = null;
		byte[] fileBytes = Files.readAllBytes(path);
		javaFile = new String(fileBytes, StandardCharsets.UTF_8);
		log.debug("Content red: {} ", javaFile);
		String fileName = path.getFileName().toString().split(JAVA_EXTENSION)[0];
		
		// parse the JUnit file
		List<Scenario> javaScenarios = parser.parseJavaScenario(fileName, javaFile);

		// check the returned list
		if (javaScenarios == null || javaScenarios.isEmpty()) {
			log.error("Failed to parse the Junit file, no scenario found");
			throw new JUnitParseException("Unable to parse the JUnit file: no scenario found");
		}
		
		return javaScenarios;
	}

}
