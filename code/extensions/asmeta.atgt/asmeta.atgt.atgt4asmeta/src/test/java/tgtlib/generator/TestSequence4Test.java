package tgtlib.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestSequence;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.Variable;

/**
 * simple minimal implementation useful for testing
 * 
 * @author garganti
 * 
 */
public class TestSequence4Test extends TestSequence<TestPredicate4Test> {
	
	static final Logger logger = Logger.getLogger(TestSequence4Test.class);
	
	static final IdExpressionCreator icc = new IdExpressionCreator();

	private Map<Variable, String> test = new HashMap<Variable, String>();
	
	public static  TestSequenceFactory<TestSequence4Test, TestPredicate4Test> factory =

	new TestSequenceFactory<TestSequence4Test, TestPredicate4Test>() {

		@Override
		public TestSequence4Test buildTestSequence(TestPredicate4Test tp) {
			return new TestSequence4Test(tp);
		}
	};
	
	public TestSequence4Test(TestPredicate4Test tp) {
		super(tp);
	}

	@Override
	public void addState() {
		// do nothing
	}

	@Override
	public void addAssignment(Variable varS, String val) {
		logger.debug(varS + "=" + val);
		test.put(varS, val);		
	}

	@Override
	protected Variable getVar(final String varS) {
		throw new RuntimeException(" TO BE IMPLMENTED OR NOT USED (use addassignemnt with variables)");
	}

	/** get the value for the variable */
	public String get(String var) {
		for(Variable v: test.keySet()){
			if (v.getName().equals(var)) return test.get(v);
		}
		return null;
	}

	@Override
	public int numberOfStates() {
		// it does not memoryze the sequence
		return 1;
	}

	@Override
	public Map<? extends Variable, String> getState(int stateNum) {
		if (stateNum == 0 ){
			return test;
		} else{
			throw new RuntimeException("only one state");
		}
	}
	
	@Override
	public String toString() {
		return test.toString();
	}
}