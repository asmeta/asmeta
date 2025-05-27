package asmeta.evotest.junit2avalla.javascenario;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import asmeta.evotest.junit2avalla.model.Scenario;

/**
 * Parser implementation that allows parsing with javaParser.
 */
public class JavaParser extends Parser {
	
	/** Logger */
	private final Logger log = LogManager.getLogger(JavaParser.class);

	@Override
	List<Scenario> parseJavaScenario(String fileName, String javaFile) throws JUnitParseException {
		log.info("Parsing the JUnit file {}.java using the JavaParser.", fileName);
		CompilationUnit parsed = StaticJavaParser.parse(javaFile);
		Optional<ClassOrInterfaceDeclaration> cls = parsed.getClassByName(fileName);
		List<Scenario> scenarios = null;
		if (cls.isPresent()) {
			JavaParserListener javaParserListener = new JavaParserListener();
			scenarios = javaParserListener.parseJUnitClass(cls.get());
		}
		return scenarios;
	}

}
