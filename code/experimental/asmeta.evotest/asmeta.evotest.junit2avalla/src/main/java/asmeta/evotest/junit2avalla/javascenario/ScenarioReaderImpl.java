package asmeta.evotest.junit2avalla.javascenario;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioLexer;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

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

	@Override
	public List<Scenario> readJavaScenario(Path path) throws IOException, JUnitParseException {
		log.info("Reading the Scenario File at the path {}", path);
		// read the JUnit file
		String javaFile = null;
		byte[] fileBytes = Files.readAllBytes(path);
		javaFile = new String(fileBytes, StandardCharsets.UTF_8);
		log.debug("Content red: {} ", javaFile);
		// parse the JUnit file
		List<Scenario> javaScenarios = null;
		switch (parserType) {
		case CUSTOM_PARSER:
			javaScenarios = parseJavaScenarioUsingCustomParser(javaFile);
			break;
		case JAVA_PARSER:
			String className = path.getFileName().toString().split(JAVA_EXTENSION)[0];
			log.debug("Class name: {}.", className);
			javaScenarios = parseJavaScenarioUsingJavaParser(javaFile, className);
			break;
		default:
			log.error("NOT SUPPORTED.");
			throw new IllegalArgumentException(parserType.getType() + " not supported.");
		}
		if (javaScenarios == null || javaScenarios.isEmpty()) {
			log.error("Failed to parse the Junit file, no scenario found");
			throw new JUnitParseException("Unable to parse the JUnit file: no scenario found");
		}
		return javaScenarios;
	}

	/**
	 * Parse the JUnit file using the custom parser.
	 * 
	 * @param javaFile string containing the JUnit file to parse.
	 * @return list of Scenario Objects (not null and not empty).
	 */
	private List<Scenario> parseJavaScenarioUsingCustomParser(String javaFile) {
		log.info("Parsing the JUnit file using the custom parser.");
		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		CustomParserListener javaScenarioWalker = new CustomParserListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());
		return javaScenarioWalker.getScenarioList();
	}

	/**
	 * Parse the JUnit file using the custom parser.
	 * 
	 * @param javaFile string containing the JUnit file to parse.
	 * @return list of Scenario Objects.
	 * @throws JUnitParseException if an error occurs during the parsing process.
	 */
	private List<Scenario> parseJavaScenarioUsingJavaParser(String javaFile, String className)
			throws JUnitParseException {
		log.info("Parsing the JUnit file {}.java using the JavaParser.", className);
		CompilationUnit parsed = StaticJavaParser.parse(javaFile);
		Optional<ClassOrInterfaceDeclaration> cls = parsed.getClassByName(className);
		List<Scenario> scenarios = null;
		if (cls.isPresent()) {
			JavaParserListener javaParserListener = new JavaParserListener();
			scenarios = javaParserListener.parseJUnitClass(cls.get());
		}
		return scenarios;
	}

}
