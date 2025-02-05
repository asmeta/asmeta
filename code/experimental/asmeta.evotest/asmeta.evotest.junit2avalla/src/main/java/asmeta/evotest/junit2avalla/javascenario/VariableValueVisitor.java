 package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableValueVisitor extends VoidVisitorAdapter<Context> {

	/** Logger */
	private final Logger logger = LogManager.getLogger(VariableValueVisitor.class);

	@Override
	public void visit(ObjectCreationExpr node, Context context) {
		logger.info("CONSTRUCTOR: {}.", node);
		context.getCurrentJavaVariable().setValue(node.getArgument(0).toString());
	}

	@Override
	public void visit(FieldAccessExpr node, Context context) {
		logger.info("IDENTIFIER: {}.", node);
		ScenarioParserUtil.setNotPrimitiveVariable(node.getNameAsString(), context.getCurrentJavaVariable());
	}

	@Override
	public void visit(NameExpr node, Context context) {
		logger.info("VARIABLE: {}.", node);
		if (context.getVariablesMap().containsKey(node.getNameAsString())) {
			context.getCurrentJavaVariable().setValue(context.getVariablesMap().get(node.getNameAsString()).getValue());
			if (context.getGetterMap().containsKey(context.getCurrentJavaVariable().getValue())) {
				// search in the getters map
				String value = context.getGetterMap().get(context.getCurrentJavaVariable().getValue());
				logger.info("Found a getter value in the getterMap: {} .", value);
				context.getCurrentJavaVariable().setValue(value);
				context.getCurrentJavaVariable().setPrimitive(true);
			} else {
				context.getCurrentJavaVariable()
						.setPrimitive(context.getVariablesMap().get(node.getNameAsString()).isPrimitive());
				ScenarioParserUtil.setNaturalVariableValue(context.getCurrentJavaVariable().getValue(),
						context.getCurrentJavaVariable());
			}
		} else
			throw new JUnitParseException("Unable to find the variable in the variable map: " + node);
	}

	@Override
	public void visit(MethodCallExpr node, Context context) {
		logger.info("VALUE METHOD CALL: {}.", node);
		if (node.getNameAsString().equals(ScenarioParserUtil.VALUE_OF) && node.getArguments().size() == 1) {
			// valueOf method
			ScenarioParserUtil.setPrimitiveVariable(node.getArgument(0).toString(), context.getCurrentJavaVariable());
		} else if (node.getNameAsString().contains(ScenarioParserUtil.GET_FLAG)) {
			// asm getter
			String value = ScenarioParserUtil.buildDomainCodomain(node.getNameAsString());
			ScenarioParserUtil.setNotPrimitiveVariable(value, context.getCurrentJavaVariable());
		} else {
			logger.error("Method call not supported: {}", node.getNameAsString());
			throw new JUnitParseException("Method call not supported: " + node.getNameAsString());
		}
	}

	@Override
	public void visit(BooleanLiteralExpr node, Context context) {
		logger.info("VALUE BOOLEAN: {}.", node);
		ScenarioParserUtil.setPrimitiveVariable(Boolean.toString(node.getValue()), context.getCurrentJavaVariable());
	}

	@Override
	public void visit(CharLiteralExpr node, Context context) {
		logger.info("VALUE CHAR: {}.", node);
		ScenarioParserUtil.setPrimitiveVariable(node.getValue(), context.getCurrentJavaVariable());
	}

	@Override
	public void visit(DoubleLiteralExpr node, Context context) {
		String fullValue = getFullLiteralValue(node);
		logger.info("VALUE DOUBLE: {}.", fullValue);
		ScenarioParserUtil.setPrimitiveVariable(node.getValue(), context.getCurrentJavaVariable());
	}

	@Override
	public void visit(IntegerLiteralExpr node, Context context) {
		String fullValue = getFullLiteralValue(node);
		logger.info("VALUE INTEGER: {}.", fullValue);
		ScenarioParserUtil.setIntegerVariableValue(fullValue, context.getCurrentJavaVariable());
	}

	@Override
	public void visit(LongLiteralExpr node, Context context) {
		String fullValue = getFullLiteralValue(node);
		logger.info("VALUE LONG: {}.", fullValue);
		ScenarioParserUtil.setIntegerVariableValue(node.getValue(), context.getCurrentJavaVariable());
	}

	@Override
	public void visit(StringLiteralExpr node, Context context) {
		logger.info("VALUE STRING: {}.", node);
		ScenarioParserUtil.setStringVariableValue(node.getValue(), context.getCurrentJavaVariable());
	}

	/**
	 * Returns the full literal value, including a minus sign if the literal is
	 * preceded by a unary minus.
	 *
	 * @param literalNode the literal node
	 * @return the full string value of the literal, including a '-' if necessary
	 */
	private String getFullLiteralValue(Expression literalNode) {
		String value = literalNode.toString();
		// Check if the parent is a UnaryExpr with a MINUS operator
		if (literalNode.getParentNode().isPresent()) {
			if (literalNode.getParentNode().get() instanceof UnaryExpr) {
				UnaryExpr parent = (UnaryExpr) literalNode.getParentNode().get();
				if (parent.getOperator() == UnaryExpr.Operator.MINUS) {
					value = "-" + value;
				}
			}
		}
		return value;
	}

}
