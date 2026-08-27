package atgt.generator.testsuite.ordering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import atgt.coverage.TestCondition;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Specification;

public abstract class NWiseTcComparator<S extends Specification, T extends TestSequence<?>>
		implements Comparator<TestCondition> {

	List<Variable> vars = null;

	/**
	 * reset the specification for this comparator
	 * 
	 * @param spec
	 */
	void setSpecification(S spec) {
		vars = new ArrayList<Variable>();
		for (Variable v : spec.getVariables()) {
			// skip integers ?? TODO
			if (v.isControlled() || !(v.getType() instanceof ElementsType)) {
				PreferNovelty.log.debug("skipping " + v);
				continue;
			}
			vars.add(v);
		}
		this.init();
	}

	abstract void init();

	abstract int evaluate(EqTestCondition t);

	@Override
	public int compare(TestCondition t1, TestCondition t2) {
		EqTestCondition p1 = (EqTestCondition) t1, p2 = (EqTestCondition) t2;
		return evaluate(p1) - evaluate(p2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.generator.testsuite.ordering.NWiseTcComparator#equals(atgt.coverage
	 * .TestCondition, atgt.coverage.TestCondition)
	 */
	public boolean equals(TestCondition t1, TestCondition t2) {
		EqTestCondition p1 = (EqTestCondition) t1, p2 = (EqTestCondition) t2;
		return evaluate(p1) == evaluate(p2);
	}

	/**
	 * update HITS for this new test sequence.
	 * 
	 * @param tseq
	 *            the tseq
	 */
	public abstract void update(T tseq);

	/**
	 * Update. HITS for this new test condition.
	 * 
	 * @param var
	 *            the var
	 * @param value
	 *            the value
	 */
	public abstract void update(String var, String value);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

}