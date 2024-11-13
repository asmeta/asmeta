// Generated from asmeta\junit2avalla\antlr\JavaScenario.g4 by ANTLR 4.7.1
package asmeta.junit2avalla.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavaScenarioParser}.
 */
public interface JavaScenarioListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(JavaScenarioParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(JavaScenarioParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(JavaScenarioParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(JavaScenarioParser.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#scenario}.
	 * @param ctx the parse tree
	 */
	void enterScenario(JavaScenarioParser.ScenarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#scenario}.
	 * @param ctx the parse tree
	 */
	void exitScenario(JavaScenarioParser.ScenarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#asmDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAsmDeclaration(JavaScenarioParser.AsmDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#asmDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAsmDeclaration(JavaScenarioParser.AsmDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(JavaScenarioParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(JavaScenarioParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#variableType}.
	 * @param ctx the parse tree
	 */
	void enterVariableType(JavaScenarioParser.VariableTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#variableType}.
	 * @param ctx the parse tree
	 */
	void exitVariableType(JavaScenarioParser.VariableTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#variableName}.
	 * @param ctx the parse tree
	 */
	void enterVariableName(JavaScenarioParser.VariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#variableName}.
	 * @param ctx the parse tree
	 */
	void exitVariableName(JavaScenarioParser.VariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#variableValue}.
	 * @param ctx the parse tree
	 */
	void enterVariableValue(JavaScenarioParser.VariableValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#variableValue}.
	 * @param ctx the parse tree
	 */
	void exitVariableValue(JavaScenarioParser.VariableValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#assertEquals}.
	 * @param ctx the parse tree
	 */
	void enterAssertEquals(JavaScenarioParser.AssertEqualsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#assertEquals}.
	 * @param ctx the parse tree
	 */
	void exitAssertEquals(JavaScenarioParser.AssertEqualsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#actual}.
	 * @param ctx the parse tree
	 */
	void enterActual(JavaScenarioParser.ActualContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#actual}.
	 * @param ctx the parse tree
	 */
	void exitActual(JavaScenarioParser.ActualContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#expected}.
	 * @param ctx the parse tree
	 */
	void enterExpected(JavaScenarioParser.ExpectedContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#expected}.
	 * @param ctx the parse tree
	 */
	void exitExpected(JavaScenarioParser.ExpectedContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#setFunction}.
	 * @param ctx the parse tree
	 */
	void enterSetFunction(JavaScenarioParser.SetFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#setFunction}.
	 * @param ctx the parse tree
	 */
	void exitSetFunction(JavaScenarioParser.SetFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#stepFunction}.
	 * @param ctx the parse tree
	 */
	void enterStepFunction(JavaScenarioParser.StepFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#stepFunction}.
	 * @param ctx the parse tree
	 */
	void exitStepFunction(JavaScenarioParser.StepFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#setVariableValue}.
	 * @param ctx the parse tree
	 */
	void enterSetVariableValue(JavaScenarioParser.SetVariableValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#setVariableValue}.
	 * @param ctx the parse tree
	 */
	void exitSetVariableValue(JavaScenarioParser.SetVariableValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaScenarioParser#trycatchblock}.
	 * @param ctx the parse tree
	 */
	void enterTrycatchblock(JavaScenarioParser.TrycatchblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaScenarioParser#trycatchblock}.
	 * @param ctx the parse tree
	 */
	void exitTrycatchblock(JavaScenarioParser.TrycatchblockContext ctx);
}