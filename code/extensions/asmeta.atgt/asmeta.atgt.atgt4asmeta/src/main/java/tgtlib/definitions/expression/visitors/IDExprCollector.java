package tgtlib.definitions.expression.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoolType.BoolConst;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.Variable;

/**
 * collect all the IDexpression in a expression
 * 
 * @author garganti
 * 
 * @version $Revision: 1.0 $
 */
public final class IDExprCollector implements ExpressionVisitor<Set<IdExpression>> {

	public static IDExprCollector instance = new IDExprCollector();

	/** return the ids as list (instead of a set) but no repetition is allowed*/
	public static List<IdExpression> getIdsAsList(Expression e){
		return new ArrayList<IdExpression>(e.accept(instance));
		
	}
	
	/**
	 * return all the ids in ee.
	 *
	 * @param ee the ee
	 * @return the collection
	 */
	public static Collection<IdExpression> collectIds(Collection<Expression> ee){
		Set<IdExpression> res = new TreeSet<IdExpression>();
		for(Expression e: ee){
			res.addAll(e.accept(instance));
		}
		return res;	
	}
	/**
	 * 
	 * @param vars variables 
	 * @return
	 */
	public static Collection<IdExpression> collectIdsFromVars(Collection<? extends Variable> vars) {
		Set<IdExpression> res = new TreeSet<IdExpression>();
		for(Variable v: vars){
			IdExpression idExpression = v.getIdExpression();
			assert !res.contains(idExpression);
			res.add(idExpression);
		}
		return res;	
	}

	
	/**
	 * return the list of variables assuming that all the IDs are boolean variables in the expression
	 * (exclude the EnumConst).
	 *
	 * @param e the expression in which search the IDs
	 * @return the bool vars from id
	 */
	public static List<BooleanVar> getBoolVarsFromId(Expression e) {
		return getBoolVarsFromId(e,Collections.EMPTY_SET); 
	}

	/**
	 * return the list of variables assuming that all the IDs are boolean variables in the expression
	 * (exclude the EnumConst).
	 *
	 * @param e the expression in which search the IDs
	 * @param vars the variables to be excluded (for instance those already considered) - consider the name
	 * @return the bool vars from id
	 */
	public static List<BooleanVar> getBoolVarsFromId(Expression e, Collection<? extends Variable> vars) {
		assert vars!= null;
		Collection<IdExpression> ids = e.accept(IDExprCollector.instance);
		List<BooleanVar>  result = new ArrayList<>();
		buildNewVar: for(final IdExpression id : ids){
			if (id instanceof BoolConst)
				continue;
			// TODO why an expression could possibly contain enumerations
			assert !(id instanceof EnumConst);
			for (Variable v: vars){
				// another variable found 
				if (v.getName().equals(id.getIdString())) continue buildNewVar;
			}
			BooleanVar v = new BooleanVar(id);
			result.add(v);			
		}
		return result;
	}

	
	
	private IDExprCollector() {

	}
	
	/**
	 * Method forAndExpression.
	 * @param e AndExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forAndExpression(AndExpression)
	 */
	@Override
	public Set<IdExpression> forAndExpression(AndExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forDivExpression.
	 * @param e DivExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forDivExpression(DivExpression)
	 */
	@Override
	public Set<IdExpression> forDivExpression(DivExpression e) {
		return forBinaryExpression(e);
	}
	/**
	 * Method forModuloExpression.
	 * @param e ModuloExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forModuloExpression(ModuloExpression)
	 */
	@Override
	public Set<IdExpression> forModuloExpression(
			ModuloExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forEqualsExpression.
	 * @param e EqualsExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forEqualsExpression(EqualsExpression)
	 */
	@Override
	public Set<IdExpression> forEqualsExpression(EqualsExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forGreaterEqualExpression.
	 * @param e GreaterEqualExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterEqualExpression(GreaterEqualExpression)
	 */
	@Override
	public Set<IdExpression> forGreaterEqualExpression(GreaterEqualExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forGreaterThanExpression.
	 * @param e GreaterThanExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forGreaterThanExpression(GreaterThanExpression)
	 */
	@Override
	public Set<IdExpression> forGreaterThanExpression(GreaterThanExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forIdExpression.
	 * @param e IdExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forIdExpression(IdExpression)
	 */
	@Override
	public Set<IdExpression> forIdExpression(IdExpression e) {
		if (e == BoolType.FALSE_CONST)
			return Collections.EMPTY_SET;
		if (e == BoolType.TRUE_CONST)
			return Collections.EMPTY_SET;
		return Collections.singleton(e);
	}
	/**
	 * Method forPrimedIdExpression.
	 * @param primedIdExpression PrimedIdExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPrimedIdExpression(PrimedIdExpression)
	 */
	@Override
	public Set<IdExpression> forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		return forIdExpression(primedIdExpression.getID());
	}

	/**
	 * Method forImpliesExpression.
	 * @param e ImpliesExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forImpliesExpression(ImpliesExpression)
	 */
	@Override
	public Set<IdExpression> forImpliesExpression(ImpliesExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forLessEqualExpression.
	 * @param e LessEqualExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessEqualExpression(LessEqualExpression)
	 */
	@Override
	public Set<IdExpression> forLessEqualExpression(LessEqualExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forLessThanExpression.
	 * @param e LessThanExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forLessThanExpression(LessThanExpression)
	 */
	@Override
	public Set<IdExpression> forLessThanExpression(LessThanExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forMinusExpression.
	 * @param e MinusExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMinusExpression(MinusExpression)
	 */
	@Override
	public Set<IdExpression> forMinusExpression(MinusExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forMultExpression.
	 * @param e MultExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forMultExpression(MultExpression)
	 */
	@Override
	public Set<IdExpression> forMultExpression(MultExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forNotEqualsExpression.
	 * @param e NotEqualsExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotEqualsExpression(NotEqualsExpression)
	 */
	@Override
	public Set<IdExpression> forNotEqualsExpression(NotEqualsExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forNegExpression.
	 * @param e NegExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNegExpression(NegExpression)
	 */
	@Override
	public Set<IdExpression> forNegExpression(NegExpression e) {
		return forUnaryExpression(e);
	}

	/**
	 * Method forNextExpression.
	 * @param e NextExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNextExpression(NextExpression)
	 */
	@Override
	public Set<IdExpression> forNextExpression(NextExpression e) {
		return forUnaryExpression(e);
	}

	/**
	 * Method forNotExpression.
	 * @param e NotExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forNotExpression(NotExpression)
	 */
	@Override
	public Set<IdExpression> forNotExpression(NotExpression e) {
		return forUnaryExpression(e);
	}

	/**
	 * Method forOrExpression.
	 * @param e OrExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forOrExpression(OrExpression)
	 */
	@Override
	public Set<IdExpression> forOrExpression(OrExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forPlusExpression.
	 * @param e PlusExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forPlusExpression(PlusExpression)
	 */
	@Override
	public Set<IdExpression> forPlusExpression(PlusExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forXOrExpression.
	 * @param e XOrExpression
	 * @return Set<IdExpression>
	 * @see tgtlib.definitions.expression.ExpressionVisitor#forXOrExpression(XOrExpression)
	 */
	@Override
	public Set<IdExpression> forXOrExpression(XOrExpression e) {
		return forBinaryExpression(e);
	}

	/**
	 * Method forBinaryExpression.
	 * @param e BinaryExpression
	 * @return Set<IdExpression>
	 */
	private Set<IdExpression> forBinaryExpression(BinaryExpression e) {
		Set<IdExpression> res = new TreeSet<IdExpression>();
		res.addAll(e.getFirstOperand().accept(this));
		res.addAll(e.getSecondOperand().accept(this));
		return res;
	}

	/**
	 * Method forUnaryExpression.
	 * @param e UnaryExpression
	 * @return Set<IdExpression>
	 */
	private Set<IdExpression> forUnaryExpression(UnaryExpression e) {
		return e.getOperand().accept(this);
	}

	@Override
	public Set<IdExpression> forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IdExpression> forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public Set<IdExpression> forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}