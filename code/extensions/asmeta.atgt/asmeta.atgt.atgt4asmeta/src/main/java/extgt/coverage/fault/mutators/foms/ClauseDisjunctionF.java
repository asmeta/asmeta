package extgt.coverage.fault.mutators.foms;

import java.util.List;

import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionSettableVisitMutator;
import extgt.coverage.fault.mutators.IsTerm.TermType;

/**
 * Clause Disjunction Fault (CDF). An occurrence of condition c is replaced by
c\/c′, in which c′ is a possible condition. For example, (x1\/not x2\/x3)/\(x3/\x4)
is a CDF of (x1 \/ not x2) /\ (x3 /\ x4).
 * @author garganti
 * 
 */
public class ClauseDisjunctionF extends ExpressionSettableVisitMutator<ClauseDisjunctionF.CDFVisitor>{

	private ClauseDisjunctionF(){}

	public static ClauseDisjunctionF CDF = new ClauseDisjunctionF();	

	@Override
	public String getName() {
		return "Clause Disjunction Fault";
	}

	@Override
	public String getAbbrvName() {
		return "CDF";
	}
	
	static class CDFVisitor extends ClauseInsertionFault{ 
		
		/**
		 * @param ids 
		 */
		private CDFVisitor(List<IdUNotIdExpression> ids) {
			super(ids,TermType.OR_TERM);
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}
	}

	@Override
	protected CDFVisitor getNewVisitorForIds(List<IdUNotIdExpression> ids) {
		return new CDFVisitor(ids);
	}

}
