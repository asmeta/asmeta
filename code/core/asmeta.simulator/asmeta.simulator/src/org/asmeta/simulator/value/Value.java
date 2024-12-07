/**
 * Value.java
 *
 * Created on 22/ago/06, 10:44:37
 *
 */
package org.asmeta.simulator.value;

/**
 * The super class of all values. The subclasses act as wrappers for the
 * corresponding Java implementations.
 *
 * @param <T> the generic type representing the value type in Java
 */
public abstract class Value<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract Value<T> clone();

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public abstract T getValue();
}
