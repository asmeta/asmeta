package atgt.generator.collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import atgt.combinatorial.CollectedNWiseTC;
import atgt.combinatorial.NWiseCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import tgtlib.definitions.expression.Expression;

/** some test of the TPCompatible collector
 * 
 * @author garganti
 *
 */
public class TPCompatibleCollectorTest2 {


	@Test
	public void testCollectedTestCondition() throws InstantiationException, IllegalAccessException{
		// creates a new TPCompatibleCollector2		
		TPCompatibleCollector tpc = TPCompatibleCollector.collectorRegistrator.getCollector(NWiseCoverage.class).build(Collections.EMPTY_LIST,Collections.EMPTY_LIST,null,null);
		CollectedTestCondition ctp = tpc.createEmptyCollectedTestCondition();
		assertNotNull(ctp);
		assertTrue(ctp instanceof CollectedNWiseTC);
	}
	
	
	@Test 
	public void testCollectedTestConditionSimple() throws InstantiationException, IllegalAccessException{
		TPCompatibleCollector.collectorRegistrator.register(MyCoverage.class, MyCollectedTP.getFactory());
		// creates a new TPCompatibleCollector2
		TPCompatibleCollector tpc = TPCompatibleCollector.collectorRegistrator.getCollector(MyCoverage.class).build(Collections.EMPTY_LIST,Collections.EMPTY_LIST,null,null);
		assertNotNull(tpc);
		assertTrue(tpc instanceof MyTPCompatibleCollector);
		CollectedTestCondition/*<MyTestCondition>*/ ctp = tpc.createEmptyCollectedTestCondition();
		assertNotNull(ctp);
		assertTrue(ctp instanceof MyCollectedTP);
	}

	class MyCoverage extends Coverage{

		public MyCoverage(String _name) {
			super(_name);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	class MyTestCondition extends AsmTestCondition {

		public MyTestCondition(String _name, Expression _condition) {
			super(_name, _condition);
		}}
	
}
