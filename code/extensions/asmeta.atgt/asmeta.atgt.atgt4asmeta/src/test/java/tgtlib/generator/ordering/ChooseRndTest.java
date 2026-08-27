package tgtlib.generator.ordering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tgtlib.definitions.TestPredicate;
import tgtlib.generator.TestPredicate4Test;

/**
 * The class <code>ChooseRndTest</code> contains tests for the class <code>{@link ChooseRnd}</code>.
 *
 * @author garganti
 */
public class ChooseRndTest {
	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test
	public void testChooseRndEmpty(){
		List<TestPredicate> candidates = new ArrayList<TestPredicate>();

		ChooseRnd result = new ChooseRnd(candidates);

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.next());
	}

	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test
	public void testChooseSomeElements(){
		List<TestPredicate4Test> candidates = new ArrayList<TestPredicate4Test>();
		candidates.add(new TestPredicate4Test("a1", null));
		candidates.add(new TestPredicate4Test("a2", null));
		
		ChooseRnd<TestPredicate4Test> result = new ChooseRnd<TestPredicate4Test>(candidates);
		assertNotNull(result);
		//
		assertNotNull(result.next());
		assertNotNull(result.next());
		assertNull(result.next());
	}
	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test
	public void testChooseRemove(){
		List<TestPredicate4Test> candidates = new ArrayList<TestPredicate4Test>();
		candidates.add(new TestPredicate4Test("a1", null));
		candidates.add(new TestPredicate4Test("a2", null));
		
		ChooseRnd<TestPredicate4Test> result = new ChooseRnd<TestPredicate4Test>(candidates);
		assertNotNull(result);
		//
		assertNotNull(result.next());
		result.remove();
		//
		assertNotNull(result.next());
		assertNull(result.next());
	}
	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test(expected=java.lang.IllegalStateException.class)
	public void testChooseRemoveTwice(){
		List<TestPredicate4Test> candidates = new ArrayList<TestPredicate4Test>();
		candidates.add(new TestPredicate4Test("a1", null));
		candidates.add(new TestPredicate4Test("a2", null));
		
		ChooseRnd<TestPredicate4Test> result = new ChooseRnd<TestPredicate4Test>(candidates);
		assertNotNull(result);
		//
		assertNotNull(result.next());
		result.remove();
		result.remove();
	}
}