package tgtlib.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.Vector;

import org.junit.jupiter.api.Test;

class IterableEnumerationTest {

	@Test void iterator() {
		Vector<String> v = new Vector<String>();
		v.add("a");
		v.add("b");
		IterableEnumeration<String> en = new IterableEnumeration<String>(v.elements());
		Iterator<String> it1 = en.iterator();
		assertEquals("a",it1.next());
		assertEquals("b",it1.next());
		assertFalse(it1.hasNext());
	}

	@Test void doubleIteratorSameEnum() {
		Vector<String> v = new Vector<String>();
		v.add("a");
		v.add("b");
		IterableEnumeration<String> en = new IterableEnumeration<String>(v.elements());
		Iterator<String> it1 = en.iterator();
		assertThrows(RuntimeException.class, () -> {
			// get a second iterator
			Iterator<String> it2 = en.iterator();
		});
	}

	@Test void doubleIterator2Enums() {
		Vector<String> v = new Vector<String>();
		v.add("a");
		v.add("b");
		// first enumeration
		IterableEnumeration<String> en1 = new IterableEnumeration<String>(v.elements());
		Iterator<String> it1 = en1.iterator();
		assertEquals("a",it1.next());
		assertEquals("b",it1.next());
		assertFalse(it1.hasNext());
		// get a second iterator
		IterableEnumeration<String> en2 = new IterableEnumeration<String>(v.elements());
		Iterator<String> it2 = en2.iterator();		
		assertEquals("a",it2.next());
		assertEquals("b",it2.next());
		assertFalse(it2.hasNext());
	}

}
