package extgt.coverage.fault.mutators.foms;

import java.util.List;

import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionSettableVisitMutator;
import extgt.coverage.fault.mutators.IsTerm.TermType;

/**
 * chen: Clause Conjunction Fault (CCF). An occurrence of condition c is
 * replaced by c/\c′, in which c′ is a possible condition. For example,
 * (x1\/not x2)/\(x1/\x3/\x4) is a CCF of (x1 \/ not x2) /\ (x3 /\ x4).
 * 
 * @author garganti
 * 
 */
public class ClauseConjunctionF extends ExpressionSettableVisitMutator<ClauseConjunctionF.CCFVisitor> {

	public ClauseConjunctionF(){}

	@Override
	public String getName() {
		return "Clause Conjunction Fault";
	}

	@Override
	public String getAbbrvName() {
		return "CCF";
	}

	/**
	 * The CCF.
	 * 
	 * it does not contain the ids. must be set 
	 */
	static final public ClauseConjunctionF CCF = new ClauseConjunctionF();

	static class CCFVisitor extends ClauseInsertionFault {

		private CCFVisitor(List<IdUNotIdExpression> ids) {
			super(ids,TermType.AND_TERM);
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}

	}

	@Override
	protected CCFVisitor getNewVisitorForIds(List<IdUNotIdExpression> ids) {
		return new CCFVisitor(ids);
	}
}
