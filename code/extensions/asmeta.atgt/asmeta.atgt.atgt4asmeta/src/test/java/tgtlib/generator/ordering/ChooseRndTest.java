package tgtlib.generator.ordering;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.TestPredicate;

import org.junit.jupiter.api.Test;
import tgtlib.generator.TestPredicate4Test;

/**
 * The class <code>ChooseRndTest</code> contains tests for the class <code>{@link ChooseRnd}</code>.
 *
 * @author garganti
 */
class ChooseRndTest {
	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test void chooseRndEmpty(){
		List<TestPredicate> candidates = new ArrayList<TestPredicate>();

		ChooseRnd result = new ChooseRnd(candidates);

		// add additional test code here
		assertNotNull(result);
		assertNull(result.next());
	}

	/**
	 * Run the ChooseRnd(List<T>) constructor test.
	 */
	@Test void chooseSomeElements(){
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
	@Test void chooseRemove(){
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
	@Test void chooseRemoveTwice() {
		List<TestPredicate4Test> candidates = new ArrayList<TestPredicate4Test>();
		candidates.add(new TestPredicate4Test("a1", null));
		candidates.add(new TestPredicate4Test("a2", null));
		ChooseRnd<TestPredicate4Test> result = new ChooseRnd<TestPredicate4Test>(candidates);
		assertNotNull(result);
		assertNotNull(result.next());
		result.remove();
		assertThrows(java.lang.IllegalStateException.class, () ->
			result.remove());
	}
}