package mcdc.scrtgtool;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import tgtlib.definitions.TestPredicate;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.visitors.IsAtomicBool;
import tgtlib.util.Pair;

class PairMaskingMCDCExrTest {
	
	static IdExpressionCreator idc = new IdExpressionCreator();
	static IdExpression A = idc.createIdExpression("A", null);
	static NotExpression notA = NotExpression.createNotExpression(A);

	static TestPredicateFactory<TestPredicate<?,?>> tpf = new TestPredicateFactory<TestPredicate<?,?>>() {
		
		@Override
		public TestPredicate buildTestPredicate(String n, Expression expression) {
			return new TestPredicate(n, expression){

				@Override
				public String getUniqueID() {
					// TODO Auto-generated method stub
							throw new RuntimeException("not implemented");
				}

				@Override
				public void markInfeasible() {
					// TODO Auto-generated method stub
					
				}

				@Override
				protected Collection buildCoveredBy() {
					// TODO Auto-generated method stub
							throw new RuntimeException("not implemented");
				}
				@Override
				public boolean isToVerify() {
					throw new RuntimeException("not implemented ");
				}

				@Override
				public Object getStatus() {
					// TODO Auto-generated method stub
							throw new RuntimeException("not implemented");
				}

				
			};
		}
	}; 
	// build very test a new generator, to have right numbers for literla	
	PairMaskingMCDCExr<TestPredicate<?,?>> pe = new PairMaskingMCDCExr<TestPredicate<?,?>>(tpf,IsAtomicBool.isAtomicBool);

	@Test void forIdExpression() {
		MCDCTPList<TestPredicate<?,?>> result = A.accept(pe);
		Pair<TestPredicate<?,?>, TestPredicate<?,?>> pair = result.iterator().next();
		assertEquals("literal 1 T", pair.getFirst().getName().toString());
		assertEquals("A", pair.getFirst().getCondition().toString());
		assertEquals("literal 1 F", pair.getSecond().getName().toString());
		assertEquals("not A", pair.getSecond().getCondition().toString());
	}

	@Test void forNotExpression() {
		// the order must change
		MCDCTPList<TestPredicate<?,?>> result = notA.accept(pe);
		Pair<TestPredicate<?,?>, TestPredicate<?,?>> pair = result.iterator().next();
		assertEquals("literal 1 F", pair.getFirst().getName().toString());
		assertEquals("not A", pair.getFirst().getCondition().toString());
		assertEquals("literal 1 T", pair.getSecond().getName().toString());
		assertEquals("A", pair.getSecond().getCondition().toString());
	}

}
