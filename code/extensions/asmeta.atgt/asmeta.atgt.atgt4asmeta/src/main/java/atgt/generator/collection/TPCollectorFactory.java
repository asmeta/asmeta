package atgt.generator.collection;

import java.util.Collection;

import tgtlib.definitions.TestPredicate;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.TPProcessor;
import tgtlib.specification.Axiom;

/**
 * return the factory for the Compatible collector
 * 
 * @author garganti
 * 
 */
public abstract class TPCollectorFactory<TC extends TestPredicate<?,?>>{

	/**
	 * build a new TPCompatibleColector
	 * 
	 * @param vars
	 * @param _axioms
	 * @param generator
	 * @param tp
	 * @return
	 */
	abstract public TPCompatibleCollector/* < , MCInput<TestCondition>> */build(
			Iterable<? extends tgtlib.definitions.expression.type.Variable> vars,
			Collection<Axiom> _axioms,
//			TestSequenceGenerator<TC, ?, ?> generator,
			TestSequenceGenerator generator,
			TPProcessor<TC> tp);
}
