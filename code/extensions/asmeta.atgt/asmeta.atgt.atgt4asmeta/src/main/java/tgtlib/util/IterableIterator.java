/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.util;

import java.util.Iterator;

/** transform an Iterator in Iterable 
 * */

public class IterableIterator<T> implements Iterable<T> {
	
	private final Iterator<T> en;

	public IterableIterator(Iterator<T> en) {
		this.en = en;
	}

	// return an adaptor for the Enumeration
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return en.hasNext();
			}

			@Override
			public T next() {
				return en.next();
			}

			@Override
			public void remove() {
				en.remove();
			}
		};
	}
}
