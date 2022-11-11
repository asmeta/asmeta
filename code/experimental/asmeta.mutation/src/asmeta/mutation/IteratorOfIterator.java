package asmeta.mutation;

import java.util.Iterator;

public class IteratorOfIterator<T> implements Iterator<T> {
//private final Queue<Iterator<T>> iterQueue;
	private Iterator<T> currentIter;
	private T nextValue;

	public IteratorOfIterator(Iterator<Iterator<T>> iters) {
		//this.iterQueue = new LinkedList<Iterator<T>>(iters);
		this.currentIter = null;
		this.nextValue = null;
	}

	@Override
	public boolean hasNext() {
		return this.nextValue != null || setNext();
	}

	@Override
	public T next() {
		if (this.nextValue != null) {
			T next = this.nextValue;
			this.nextValue = null;
			setNext();
			return next;
		}
		return null;
	}

private boolean setNext() {
    while (true) {
        if (currentIter == null && iterQueue.isEmpty()) {
            return false;
        }
        if (currentIter == null && !iterQueue.isEmpty()) {
            currentIter = iterQueue.poll();
        }
        if (currentIter != null && currentIter.hasNext()) {
            this.nextValue = currentIter.next();
            return true;
        }
        if (currentIter != null && !currentIter.hasNext()) {
            if (!iterQueue.isEmpty()) {
                currentIter = iterQueue.poll();
            } else {
                currentIter = null;
            }
        }
    }
}