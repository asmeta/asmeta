/**
 * CollectionValue.java
 *
 * Created on 10/ago/06, 14:28:16
 *
 */
package org.asmeta.simulator.value;

import java.util.Collection;

/**
 * A collection of values.
 * 
 */
public abstract class CollectionValue<T> extends Value<Collection<Value<T>>> implements Iterable<Value<T>> {
	
	@Override
	public abstract Collection<Value<T>> getValue();
	
}
