package atgt.generator.collection;

import atgt.coverage.TestCondition;
import tgtlib.definitions.TestSequence;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.TPProcessor;

class MyTPCompatibleCollector extends TPCompatibleCollector {

	protected MyTPCompatibleCollector(TPProcessor tpp,
			TestSequenceGenerator generator) {
		super(tpp, generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CollectedTestCondition createEmptyCollectedTestCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CHECK_RESULT checkConsistency(CollectedTestCondition collect,
			TestCondition ptc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isFeasibleWithAxioms(TestCondition ptc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void computeWitness(CollectedTestCondition collect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void closeCollection(CollectedTestCondition collect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean checkModel(TestSequence witness, TestCondition ptc) {
		// TODO Auto-generated method stub
		return false;
	}
}