package asmeta.evotest.junit2avalla.javascenario;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import asmeta.evotest.junit2avalla.model.Scenario;

public class JavaParserListener {

	/** Logger */
	private final Logger logger = LogManager.getLogger(JavaParserListener.class);

	/** List of scenarios parsed and processed. */
	private final List<Scenario> scenarioList;
	
	/** Context */
	private final Context context;

	JavaParserListener() {
		// NO ARGS CONSTRUCTOR
		this.scenarioList = new LinkedList<>();
		this.context = new Context();
		context.setScenarioManager(new ScenarioManager());
	}

	List<Scenario> parseJUnitClass(ClassOrInterfaceDeclaration cls) {
		logger.debug("class to parse: \n{}. ", cls);
		int i = 0; // scenario count
		for (MethodDeclaration method : cls.getMethods()) {
			Scenario currScenario = new Scenario();
			context.setScenarioIndex(i++);
			context.setCurrenteScenario(currScenario);
			logger.info("Parsing the method:\n{}", method);
			TermsVisitor termsVisitor = new TermsVisitor();
			method.accept(termsVisitor, context);
			this.scenarioList.add(context.getCurrenteScenario());
		}
		return this.scenarioList;
	}

}
