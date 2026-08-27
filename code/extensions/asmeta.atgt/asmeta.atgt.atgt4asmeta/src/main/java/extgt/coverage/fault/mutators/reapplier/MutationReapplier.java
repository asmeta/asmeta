package extgt.coverage.fault.mutators.reapplier;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.GetOperator;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.util.Pair;

/** get an expression and its mutation and try to reapply the mutation
 *  
 * @author garganti
 *
 */
public class MutationReapplier{
	
	static Logger log = Logger.getLogger(MutationReapplier.class);
	
	MutationReapplier(){
		
	}
	
	/**
	 * reapply an expression
	 * @param e
	 * @param tomod
	 * @return
	 */
	public static Expression reapply(Pair<Integer,Expression> e, Expression tomod){
		log.debug(e + " <-> " + tomod);
		Expression result;
		int pos = e.getFirst();
		Expression modifiedExpr = e.getSecond();
		if (pos == 1) return modifiedExpr;
		// otherwise 
		int nextPos = pos/2;
		boolean isLeft = (pos %2 == 0);
		Expression next = null;
		Expression nextMod;
		if (modifiedExpr instanceof BinaryExpression && tomod instanceof BinaryExpression){
			// get the portion to be modified
			if (isLeft) next =  ((BinaryExpression)modifiedExpr).getFirstOperand();
			else next = ((BinaryExpression)modifiedExpr).getSecondOperand();
			if (isLeft) nextMod =  ((BinaryExpression)tomod).getFirstOperand();
			else nextMod = ((BinaryExpression)tomod).getSecondOperand();
			// call recursively
			Expression ne = reapply(new Pair<Integer, Expression>(nextPos,next),nextMod);
			// if the expression is null (since cannot longer be applied) return null;
			if (ne == null)
				return null;
			// rebuild expression
			Operator op = tomod.accept(GetOperator.INSTANCE);
			if (isLeft) {
					result = BinaryExpression.mkBinExpr(ne, op, ((BinaryExpression)tomod).getSecondOperand());				
			} else {
					result = BinaryExpression.mkBinExpr(((BinaryExpression)tomod).getFirstOperand(), op, ne);									
			}
			return result;			
		} else if (modifiedExpr instanceof UnaryExpression && tomod instanceof UnaryExpression){
			next = ((UnaryExpression)modifiedExpr).getOperand();
			nextMod = ((UnaryExpression)tomod).getOperand();
			Expression ne = reapply(new Pair<Integer, Expression>(nextPos,next),nextMod);
			// rebuild expression
			Operator op = tomod.accept(GetOperator.INSTANCE);
			return UnaryExpression.mkUnExpr(op, ne);
		}
		return null;
	}	
}
