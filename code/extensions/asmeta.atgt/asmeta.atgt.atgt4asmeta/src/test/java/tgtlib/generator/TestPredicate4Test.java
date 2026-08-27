package tgtlib.generator;

import java.util.ArrayList;
import java.util.Collection;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.expression.Expression;

public class TestPredicate4Test extends TestPredicate<TestSequence4Test,TestPredicate4Test.Status> {

	public TestPredicate4Test(String name, Expression condition) {
		super(name, condition);
	}

	@Override
	public String getUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markInfeasible() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Collection<TestSequence4Test> buildCoveredBy() {
		return new ArrayList<TestSequence4Test>();
	}

	@Override
	public boolean isToVerify() {
		// TODO Auto-generated method stub
		return false;
	}

	public class Status{}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}