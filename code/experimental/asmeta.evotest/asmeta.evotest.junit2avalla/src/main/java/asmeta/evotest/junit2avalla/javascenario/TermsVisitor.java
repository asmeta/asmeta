package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

public class TermsVisitor extends VoidVisitorAdapter<Context> {

	/** Logger */
	private final Logger logger = LogManager.getLogger(TermsVisitor.class);

	@Override
	public void visit(VariableDeclarator node, Context context) {
		// To ensure child nodes of the current node are also visited
		super.visit(node, context);

		// ASM Declaration ( ATM_ATG ATM_ATG0 = new ATM_ATG() )
		if (node.getTypeAsString().endsWith(ScenarioParserUtil.ATG_CLASS_FLAG)) {
			String asmName = context.getCurrentJavaVariable().getType();
			logger.debug("ASM DECLARATION: {}.", asmName);
			context.getScenarioManager().setHeaderTerm(context.getCurrenteScenario(), asmName,
					context.getScenarioIndex());
			context.getScenarioManager().setLoadTerm(context.getCurrenteScenario(), asmName);
			return;
		}
		
		logger.info("VARIABLE TERM: {}.", node);
		JavaVariableTerm javaVariableTerm = new JavaVariableTerm();
		
		String type = node.getTypeAsString();
		logger.info("TYPE: {}.", type);
		javaVariableTerm.setType(type);
		

		String name = node.getNameAsString();
		logger.info("VARIABLE NAME: {}.", name);
		javaVariableTerm.setName(name);

		context.setCurrentJavaVariable(javaVariableTerm);

		InitializerVisitor initializerVisitor = new InitializerVisitor();
		node.accept(initializerVisitor, context);

	}

}
