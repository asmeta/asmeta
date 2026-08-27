package atgt.coverage;

import tgtlib.definitions.expression.Expression;


/** test condition for ASMtestSequences
 * 
 * @author garganti
 *
 */
public class AsmTestCondition extends TestCondition<AsmTestSequence>{

	public AsmTestCondition(String _name, Expression _condition) {
		super(_name, _condition);
	}
}
