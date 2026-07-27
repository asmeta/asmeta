/**
 * 
 */
package atgt.coverage;

import tgtlib.definitions.TestPredicateFactory;
import tgtlib.definitions.expression.Expression;

/** builds a new ASMTestCondition
 * 
 * @author garganti
 *
 */
final public class TestConditionFactory implements TestPredicateFactory<AsmTestCondition> {

	static final public TestConditionFactory factory = new TestConditionFactory();

	/**
	 * @param asmFaultCoverage
	 */
	private TestConditionFactory() {
	}

	@Override
	public AsmTestCondition buildTestPredicate(String n, Expression expression) {
		return new AsmTestCondition(n, expression);
	}
}