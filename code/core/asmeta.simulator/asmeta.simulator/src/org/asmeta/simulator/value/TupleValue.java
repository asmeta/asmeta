/**
 * 
 */
package org.asmeta.simulator.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A tuple value.
 * 
 */
public class TupleValue<T> extends CollectionValue<T> {

	private ArrayList<Value<T>> values;

	/**
	 * Creates a tuple.
	 * 
	 * @param list a list of values
	 */
	public TupleValue(List<Value<T>> list) {
		values = new ArrayList<>(list);
	}

	/**
	 * Creates a tuple.
	 * 
	 * @param values values
	 */
	public TupleValue(Value<T>... values) {
		this(Arrays.asList(values));
	}

	/**
	 * Gets the value. 
	 * 
	 * @return the value
	 */
	@Override
	public List<Value<T>> getValue() {
		return values;
	}

	public Value<T>[] getValueAsArray() {
		return values.toArray(new Value[values.size()]);
	}

	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("(");
		int i = 0;
		s.append(values.get(i++));
		while (i < values.size()) {
			s.append("," + values.get(i++));
		}
		s.append(")");
		return s.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TupleValue))
			return false;
		TupleValue another = (TupleValue) o;
		if (this.values.size() != another.values.size())
			return false;
		for (int j = 0; j < values.size(); j++) {
			if (!values.get(j).equals(another.values.get(j)))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return values.hashCode();
	}

	@Override
	public Iterator<Value<T>> iterator() {
		return values.iterator();
	}

	//PA: 14 giugno 10
	@Override
	public Value clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
