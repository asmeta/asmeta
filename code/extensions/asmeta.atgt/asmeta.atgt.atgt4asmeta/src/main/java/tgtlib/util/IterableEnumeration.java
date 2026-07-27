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

import java.util.Enumeration;
import java.util.Iterator;

/** transform an Enumeration in Iterable: only one iterator for every enumeration
 * is available */

public class IterableEnumeration<T> implements Iterable<T> {
	
	
	private boolean iteratorValid = true;
	
	private final Enumeration<T> en;

	public IterableEnumeration(Enumeration<T> en) {
		this.en = en;
	}

	// return an adaptor for the Enumeration: use only once, otherwise it does not work !
	@Override
	public Iterator<T> iterator() {
		if (! iteratorValid) throw new RuntimeException("no double iteration is possible");
		iteratorValid = false;
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return en.hasMoreElements();
			}

			@Override
			public T next() {
				return en.nextElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <T> Iterable<T> make(Enumeration<T> en) {
		return new IterableEnumeration<T>(en);
	}
}
