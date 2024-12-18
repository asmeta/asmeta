package asmeta.mutation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class IteratorOfIteratorTest {

	@Test
	public void testIterator() {
		List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
		List<Integer> list2 = Arrays.asList(5, 6, 7, 8);
		List<Iterator<Integer>> combined = Arrays.asList(list1.iterator(), null, list2.iterator());

		IteratorOfIterator<Integer> ioi = new IteratorOfIterator<>(combined.iterator());
		//
		assertTrue(ioi.hasNext());
//		assertEquals(1,ioi.next().intValue());
		
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		extracted(ioi, expected);
	}
	@Test
	public void testIteratorWEmpty() {
		List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
		List<Integer> list2 = Arrays.asList();
		List<Iterator<Integer>> combined = Arrays.asList(list1.iterator(), null, list2.iterator());
		IteratorOfIterator<Integer> ioi = new IteratorOfIterator<>(combined.iterator());
		List<Integer> expected = Arrays.asList(1, 2, 3, 4);
		extracted(ioi, expected);
	}
	@Test
	public void testIteratorEmpty() {
		List<Integer> list1 = Arrays.asList();
		List<Integer> list2 = Arrays.asList();
		List<Iterator<Integer>> combined = Arrays.asList(list1.iterator(), null, list2.iterator());
		IteratorOfIterator<Integer> ioi = new IteratorOfIterator<>(combined.iterator());
		List<Integer> expected = Arrays.asList();
		extracted(ioi, expected);
	}
	private void extracted(IteratorOfIterator<Integer> ioi, List<Integer> expected) {
		for(Integer i: expected) {
			assertTrue(ioi.hasNext());
			assertEquals(i, ioi.next());
		}
		assertFalse(ioi.hasNext());
	}

}
