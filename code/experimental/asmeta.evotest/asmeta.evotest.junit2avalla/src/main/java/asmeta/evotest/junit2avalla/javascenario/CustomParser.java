package asmeta.evotest.junit2avalla.javascenario;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.antlr.JavaScenarioLexer;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser;
import asmeta.evotest.junit2avalla.model.Scenario;

/**
 * Parser implementation that allows parsing with customParser
 */
public class CustomParser extends Parser {
	
	/** Logger */
	private final Logger log = LogManager.getLogger(CustomParser.class);

	@Override
	List<Scenario> parseJavaScenario(String fileName, String javaFile) {
		log.info("Parsing the JUnit file {}.java using the custom parser.", fileName);
		JavaScenarioLexer javaScenarioLexer = new JavaScenarioLexer(CharStreams.fromString(javaFile));
		CommonTokenStream tokens = new CommonTokenStream(javaScenarioLexer);
		JavaScenarioParser javaScenarioParser = new JavaScenarioParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		CustomParserListener javaScenarioWalker = new CustomParserListener();
		walker.walk(javaScenarioWalker, javaScenarioParser.start());
		return javaScenarioWalker.getScenarioList();
	}

}
