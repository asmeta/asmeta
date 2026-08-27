package extgt.coverage.mcdc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

import tgtlib.util.Pair;

public class IterableFromPairListTest {

	@Test
	public void testIteratorEmpty() {
		List<Pair<String, String>> ll = new ArrayList<Pair<String,String>>();
		IterableFromPairList<String> it = new IterableFromPairList<String>(ll);
		Iterator<String> ite = it.iterator();
		assertFalse(ite.hasNext());
	}
	@Test
	public void testIterator1Pair() {
		List<Pair<String, String>> ll = new ArrayList<Pair<String,String>>();
		ll.add(new Pair<String, String>("a", "b"));
		IterableFromPairList<String> it = new IterableFromPairList<String>(ll);
		Iterator<String> ite = it.iterator();
		assertTrue(ite.hasNext());
		assertEquals("a", ite.next());
		assertTrue(ite.hasNext());
		assertEquals("b", ite.next());
		assertFalse(ite.hasNext());
	}
	@Test
	public void testIterator2Pair() {
		List<Pair<String, String>> ll = new ArrayList<Pair<String,String>>();
		ll.add(new Pair<String, String>("a", "b"));
		ll.add(new Pair<String, String>("c", "d"));
		IterableFromPairList<String> it = new IterableFromPairList<String>(ll);
		Iterator<String> ite = it.iterator();
		assertTrue(ite.hasNext());
		assertEquals("a", ite.next());
		assertTrue(ite.hasNext());
		assertEquals("b", ite.next());
		assertTrue(ite.hasNext());
		assertEquals("c", ite.next());
		assertTrue(ite.hasNext());
		assertEquals("d", ite.next());
		assertFalse(ite.hasNext());
	}
}
