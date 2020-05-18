package org.asmeta.atgt.generator;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.visitors.MathExpressionTranslator;

/**
 * Translate an expression to SMV
 */
public class ExpressionToSMV extends MathExpressionTranslator {

	public static ExpressionToSMV EXPR_TO_SMV = new ExpressionToSMV();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forIdExpression(atgt.specification.expression.IdExpression)
	 */
	@Override
	public StringBuffer forIdExpression(IdExpression e) {
		return new StringBuffer(EnumConst.toStrCheckBool(e, "FALSE","TRUE"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forAndExpression(atgt.specification.expression.AndExpression)
	 */
	@Override
	public StringBuffer forAndExpression(AndExpression e) {
		return forBinaryExpression(e, "&");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forOrExpression(atgt.specification.expression.OrExpression)
	 */
	@Override
	public StringBuffer forOrExpression(OrExpression e) {
		return forBinaryExpression(e, "|");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forXOrExpression(atgt.specification.expression.XOrExpression)
	 */
	@Override
	public StringBuffer forXOrExpression(XOrExpression e) {
		return forBinaryExpression(e, "xor");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotExpression(atgt.specification.expression.NotExpression)
	 */
	@Override
	public StringBuffer forNotExpression(NotExpression e) {
		return forUnaryExpression(e, "!", true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forEqualsExpression(atgt.specification.expression.EqualsExpression)
	 */
	@Override
	public StringBuffer forEqualsExpression(EqualsExpression e) {
		return forBinaryExpression(e, "=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forNotEqualsExpression(atgt.specification.expression.NotEqualsExpression)
	 */
	@Override
	public StringBuffer forNotEqualsExpression(NotEqualsExpression e) {
		return forBinaryExpression(e, "!=");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.AsmExpressionVisitor#forImpliesExpression(atgt.specification.expression.ImpliesExpression)
	 */
	@Override
	public StringBuffer forImpliesExpression(ImpliesExpression ie) {
		OrExpression oe = ie.getEquivalent();
		return oe.accept(this);
	}

	/**
	 * assuming that the translation is to LTL, otherwise I should use '.
	 * 
	 * @param nextExpression
	 *            the next expression
	 * 
	 * @return the string buffer
	 */
	@Override
	public StringBuffer forNextExpression(NextExpression nextExpression) {
		throw new RuntimeException("operator next not supported in NUSMV");
	}

	@Override
	public StringBuffer forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("operator next not supported in NUSMV");
	}

	@Override
	public StringBuffer forModuloExpression(ModuloExpression e) {
		return forBinaryExpression(e, "mod");
	}

	@Override
	public StringBuffer forFunctionTerm(FunctionTerm ft) {
		throw new RuntimeException("operator next not supported in NUSMV - to flatten??");
	}
	
	@Override
	public StringBuffer forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("operator next not supported in NUSMV");
	}

	@Override
	public StringBuffer forConditionalExpression(CondExpression cond) {
		throw new RuntimeException("operator next not supported in NUSMV");
	}
}
