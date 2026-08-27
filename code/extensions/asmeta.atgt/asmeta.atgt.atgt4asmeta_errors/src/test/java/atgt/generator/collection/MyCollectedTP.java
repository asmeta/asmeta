/**
 * 
 */
package atgt.generator.collection;

import java.util.Collection;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.generator.collection.TPCompatibleCollectorTest2.MyTestCondition;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.generator.TestSequenceGenerator;
import tgtlib.generator.ordering.TPProcessor;
import tgtlib.specification.Axiom;

public class MyCollectedTP extends CollectedTestCondition<MyTestCondition,AsmTestSequence> {

	public MyCollectedTP() {
	}

	@Override
	public void addTestCondition(MyTestCondition ptc) {
		// TODO Auto-generated method stub

	}
	
	static TPCollectorFactory getFactory(){
		return new  TPCollectorFactory<AsmTestCondition>() {
			
			@Override
			public MyTPCompatibleCollector build(
					Iterable<? extends Variable> vars,
					Collection<Axiom> _axioms,
					TestSequenceGenerator<AsmTestCondition, ?, ?> generator,
					TPProcessor<AsmTestCondition> tp) {
				return new MyTPCompatibleCollector(tp,generator);
			}
		};
	}

}