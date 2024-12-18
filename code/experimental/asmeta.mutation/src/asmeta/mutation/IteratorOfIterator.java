package asmeta.mutation;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorOfIterator<E> implements Iterator<E> {

	private final Iterator<Iterator<E>> iteratorOfIterator;
	private Iterator<E> currentIterator = null;
	private E next = null;
	private boolean isvalid = false;

	public IteratorOfIterator(Iterator<Iterator<E>> iterator) {
		this.iteratorOfIterator = iterator;
		// call advance from the constructor!!
		advance();
	}

	private void advance() {
		if (currentIterator != null && currentIterator.hasNext()) {
			isvalid = true;
			next = currentIterator.next();
			return;
		}
		currentIterator = null;
		while (currentIterator == null && iteratorOfIterator.hasNext()) {
			currentIterator = iteratorOfIterator.next();
			if (currentIterator != null && currentIterator.hasNext()) {
				next = currentIterator.next();
				isvalid = true;
				return;
			}
		}
		next = null;
		isvalid = false;
	}

	@Override
	public boolean hasNext() {
		return isvalid;
	}

	@Override
	public E next() {
		if (! isvalid) {
			throw new NoSuchElementException("the stuff cannot be null.");
		}
		E val = next;
		advance();
		return val;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("The remove operation is not supported.");
	}
}