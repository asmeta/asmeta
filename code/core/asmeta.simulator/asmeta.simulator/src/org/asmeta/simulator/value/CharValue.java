/*
 * CharValue 
 *
 * Created on 26/7/2018
 */

package org.asmeta.simulator.value;

/**
 * A char value.
 * 
 */
public class CharValue extends Value<Character> {
	private char c;

	public CharValue(char c) {
		this.c = c;
	}

	@Override
	public Character getValue() {
		return c;
	}

	@Override
	public int hashCode() {
		return getValue();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CharValue) {
			return c == ((CharValue) object).c;
		} else if (object instanceof UndefValue) {
			return false;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return Character.toString(c);
	}

	@Override
	public Value clone() {
		return new CharValue(c);
	}
}