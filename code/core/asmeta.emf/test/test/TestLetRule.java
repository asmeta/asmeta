package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
import asmeta.transitionrules.basictransitionrules.LetRule;

public class TestLetRule {
	/**
	 * 
	 * Test case to test the unique attribute:
	 * 
	 * Unique (default is true)
	 * 
	 * Unique only applies to multiplicity-many attributes, indicating that such
	 * an attribute may not contain multiple equal objects. References are
	 * always treated as unique.
	 * 
	 * @param args
	 */
	@Test
	public void testLetRule(){
		LetRule atd = BasictransitionrulesFactory.eINSTANCE.createLetRule();
		VariableTerm vt1 = BasictermsFactory.eINSTANCE.createVariableTerm();
		vt1.setName("a");
		VariableTerm vt2 = BasictermsFactory.eINSTANCE.createVariableTerm();
		vt2.setName("b");
		atd.getVariable().add(vt1);
		atd.getVariable().add(vt2);
		assertEquals(2, atd.getVariable().size());

	}

}
