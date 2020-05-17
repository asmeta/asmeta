/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.simulator.value.AgentValue;
import org.asmeta.simulator.value.BagValue;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CharValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.MapValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.RuleValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Implementations of the functions of the standard library.
 * 
 */
public class StandardLibrary {
	// -------------------

	// //////////////////////////////////////////////////////////////////////
	// Boolean

	public static BooleanValue not(BooleanValue op1) {
		if (!op1.getValue())
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}

	public static BooleanValue and(BooleanValue op1, BooleanValue op2) {
		if (op1.getValue() && op2.getValue())
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}

	public static BooleanValue and(UndefValue op1, BooleanValue op2) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue or(BooleanValue op1, BooleanValue op2) {
		if (op1.getValue() || op2.getValue())
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}

	public static Value or(BooleanValue op1, UndefValue op2) {
		if (op1.getValue())
			return BooleanValue.TRUE;
		else
			return UndefValue.UNDEF;
	}

	public static UndefValue or(UndefValue op1, UndefValue op2) {
		return UndefValue.UNDEF;
	}

	public static BooleanValue xor(BooleanValue op1, BooleanValue op2) {
		boolean v1 = op1.getValue();
		boolean v2 = op2.getValue();
		if ((v1 && !v2) || (!v1 && v2))
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}

	public static BooleanValue implies(BooleanValue op1, BooleanValue op2) {
		if (!op1.getValue())
			return BooleanValue.TRUE;
		else
			return op2.getValue() ? BooleanValue.TRUE : BooleanValue.FALSE;
	}

	public static Value implies(BooleanValue op1, UndefValue op2) {
		if (!op1.getValue())
			return BooleanValue.TRUE;
		else
			return UndefValue.UNDEF;
	}

	public static BooleanValue iff(BooleanValue op1, BooleanValue op2) {
		if (!op1.getValue() && !op2.getValue() || op1.getValue() && op2.getValue())
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}

	public static BooleanValue eq(BooleanValue op1, BooleanValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() == op2.getValue());
	}

	public static BooleanValue neq(BooleanValue op1, BooleanValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() != op2.getValue());
	}

	// Integer

	public static IntegerValue min(IntegerValue v1, IntegerValue v2) {
		return v1.getValue() < v2.getValue() ? v1 : v2;
	}

	public static IntegerValue max(IntegerValue v1, IntegerValue v2) {
		return v1.getValue() > v2.getValue() ? v1 : v2;
	}

	public static IntegerValue plus(IntegerValue v1, IntegerValue v2) {
		return new IntegerValue(v1.getValue() + v2.getValue());
	}

	public static IntegerValue mod(IntegerValue v1, IntegerValue op2) {
		return new IntegerValue(v1.getValue() % op2.getValue());
	}

	public static IntegerValue mult(IntegerValue v1, IntegerValue op2) {
		return new IntegerValue(v1.getValue() * op2.getValue());
	}

	public static IntegerValue plus(IntegerValue v1) {
		return v1;
	}

	public static IntegerValue minus(IntegerValue v1, IntegerValue op2) {
		return new IntegerValue(v1.getValue() - op2.getValue());
	}

	// PA: 2018/2/1 This is wrong!
	/*
	 * public static IntegerValue div(IntegerValue v1, IntegerValue op2) { return
	 * new IntegerValue(v1.getValue() / op2.getValue()); }
	 */
	public static RealValue div(IntegerValue v1, IntegerValue op2) {
		return new RealValue(v1.getValue() / (double) op2.getValue());
	}

	public static IntegerValue idiv(IntegerValue v1, IntegerValue op2) {
		return new IntegerValue(v1.getValue() / op2.getValue());
	}

	public static BooleanValue eq(IntegerValue v1, IntegerValue op2) {
		// DO NOT USE == because with Integer and Long does not work with values > 127
		return BooleanValue.parseBooleanValue(v1.equals(op2));
	}

	public static BooleanValue neq(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(! v1.equals(op2));
	}

	public static BooleanValue neq(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(!op1.equals(op2));
	}

	public static BooleanValue lt(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() < op2.getValue());
	}

	public static UndefValue lt(UndefValue v1, IntegerValue op2) {
		return UndefValue.UNDEF;
	}

	public static UndefValue lt(UndefValue v1, UndefValue op2) {
		return UndefValue.UNDEF;
	}

	public static UndefValue ge(UndefValue v1, IntegerValue op2) {
		return UndefValue.UNDEF;
	}

	public static BooleanValue lt(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() < op2.getValue());
	}

	public static BooleanValue le(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() <= op2.getValue());
	}

	public static IntegerValue ntoi(IntegerValue iv) {
		return iv;
	}

	public static RealValue ntor(org.asmeta.simulator.value.IntegerValue iv) {
		return new RealValue(iv.getValue());
	}

	public static RealValue itor(org.asmeta.simulator.value.IntegerValue iv) {
		return new RealValue(iv.getValue());
	}

	// Real

	public static BooleanValue le(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() <= op2.getValue());
	}

	public static BooleanValue gt(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() > op2.getValue());
	}

	public static BooleanValue gt(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() > op2.getValue());
	}

	public static BooleanValue ge(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() >= op2.getValue());
	}

	public static BooleanValue ge(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() >= op2.getValue());
	}

	public static IntegerValue minus(IntegerValue v1) {
		return new IntegerValue(-v1.getValue());
	}

	public static IntegerValue abs(IntegerValue v1) {
		return v1.getValue() >= 0 ? v1 : new IntegerValue(-v1.getValue());
	}

	public static SetValue<Long> range(IntegerValue v1, IntegerValue iv) {
		Set<Value<Long>> result = new HashSet<>();
		for (Long i = v1.getValue(); i < iv.getValue(); i++)
			result.add(new IntegerValue(i));
		return new SetValue<>(result);
	}

	public static RealValue div(RealValue op1, RealValue op2) {
		return new RealValue(op1.getValue() / op2.getValue());
	}

	public static RealValue plus(RealValue op1, RealValue op2) {
		return new RealValue(op1.getValue() + op2.getValue());
	}

	public static RealValue minus(RealValue op1, RealValue op2) {
		return new RealValue(op1.getValue() - op2.getValue());
	}

	public static RealValue minus(RealValue op1) {
		return new RealValue(-op1.getValue());
	}

	public static RealValue mult(RealValue op1, RealValue op2) {
		return new RealValue(op1.getValue() * op2.getValue());
	}

	public static BooleanValue eq(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.equals(op2));
	}

	public static IntegerValue rton(RealValue op1) {
		return new IntegerValue(op1.getValue().longValue());
	}

	public static IntegerValue rtoi(RealValue op1) {
		return new IntegerValue(op1.getValue().longValue());
	}

	public static RealValue abs(RealValue rv) {
		double d = rv.getValue();
		if (d > 0)
			return rv;
		else
			return new RealValue(-d);
	}

	public static IntegerValue floor(RealValue rv) {
		double d = rv.getValue();
		return new IntegerValue((int) Math.floor(d));
	}

	public static IntegerValue floor(IntegerValue rv) {
		return rv;
	}
	
	
	public static RealValue pwr(RealValue rv1, RealValue rv2) {
		return new RealValue(Math.pow(rv1.getValue().doubleValue(), rv2.getValue().doubleValue()));
	}
	public static RealValue max(RealValue rv1, RealValue rv2) {
		if (rv1.getValue().compareTo(rv2.getValue())>0) return rv1;
		else return rv2;
	}
	public static RealValue min(RealValue rv1, RealValue rv2) {
		if (rv1.getValue().compareTo(rv2.getValue())<0) return rv1;
		else return rv2;		
	}

	

	// Sequence

	// static count : Prod(Seq(D),D) -> Natural
	public static IntegerValue count(SequenceValue seq, Value value) {
		ArrayList<Value> list = new ArrayList<Value>(seq.getValue());
		int count = 0;
		while (list.remove(value)) {
			count++;
		}
		return new IntegerValue(count);
	}

	public static BooleanValue eq(SequenceValue seq1, SequenceValue seq2) {
		return BooleanValue.parseBooleanValue(seq1.equals(seq2));
	}

	public static BooleanValue neq(SequenceValue seq1, SequenceValue seq2) {
		return BooleanValue.parseBooleanValue(!seq1.equals(seq2));
	}

	public static IntegerValue length(SequenceValue<?> seq1) {
		return new IntegerValue(seq1.getValue().size());
	}

	public static <T> Value<T> at(SequenceValue<T> seq1, IntegerValue iv) {
		return seq1.getValue().get(iv.getValue().intValue());
	}

	public static <T> Value<T> first(SequenceValue<T> seq1) {
		return seq1.getValue().get(0);
	}

	public static SequenceValue tail(SequenceValue seq1) {
		List<Value> l = seq1.getValue();
		List<Value> nl = new ArrayList<Value>(l.subList(1, seq1.getValue().size()));
		return new SequenceValue(nl);
	}

	public static SequenceValue union(SequenceValue seq1, SequenceValue seq2) {
		List<Value> newSeq = new LinkedList<Value>(seq1.getValue());
		newSeq.addAll(seq2.getValue());
		return new SequenceValue(newSeq);
	}

	public static SetValue asSet(SequenceValue seq1) {
		return new SetValue(new LinkedHashSet<Value>(seq1.getValue()));
	}

	public static <T> SequenceValue<T> subSequence(SequenceValue<T> sv, IntegerValue iv1, IntegerValue iv2) {
		List<Value<T>> l = sv.getValue();
		List<Value<T>> nl = new ArrayList<>(l.subList(iv1.getValue().intValue(), iv2.getValue().intValue()));
		return new SequenceValue<>(nl);
	}

	public static SequenceValue excluding(SequenceValue sv, Value av) {
		List<Value> l = new ArrayList<Value>(sv.getValue());
		l.remove(av);
		return new SequenceValue(l);
	}

	public static SequenceValue insertAt(SequenceValue sv, IntegerValue index, Value av) {
		List<Value> l = new ArrayList<Value>(sv.getValue());
		l.add(index.getValue().intValue(), av);
		return new SequenceValue(l);
	}

	public static SequenceValue replaceAt(SequenceValue sv, IntegerValue index, Value av) {
		List<Value> l = new ArrayList<Value>(sv.getValue());
		l.add(index.getValue().intValue(), av);
		l.remove(index.getValue().intValue() + 1);
		return new SequenceValue(l);
	}

	public static BooleanValue contains(SequenceValue sv, ReserveValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue contains(SequenceValue sv, Value av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static Value last(SequenceValue sv) {
		List<Value> elements = sv.getValue();
		return elements.get(elements.size() - 1);
	}

	public static BooleanValue contains(UndefValue sv, Value av) {
		return BooleanValue.FALSE;
	}

	// Set

	public static Value chooseone(SetValue setValue) {
		Set<Value> set = setValue.getValue();
		if (set.isEmpty()) {
			// throw new RuntimeException("ERROR at chooseone: the set is empty ");
			return UndefValue.UNDEF;
		}
		Value value = set.iterator().next();
		return value;
	}

	public static BooleanValue eq(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(set1.equals(set2));
	}

	public static BooleanValue neq(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(!set1.equals(set2));
	}

	public static BooleanValue contains(SetValue sv, Value av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	// clayton

	public static BooleanValue notEmpty(SetValue set1) {
		return BooleanValue.parseBooleanValue(!set1.getValue().isEmpty());
	}

	public static BooleanValue notallin(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(!set1.getValue().containsAll(set2.getValue()));
	}

	public static BooleanValue allin(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(set1.getValue().containsAll(set2.getValue()));
	}

	public static <T> IntegerValue indexOf(SequenceValue<T> sq, Value<T> v) {
		int result = -1;
		int index = 0;
		for (Value<T> value : sq) {
			if (value.equals(v)) {
				result = index;
				break;
			}
			index++;
		}
		return new IntegerValue(result);
	}

	public static SetValue difference(SetValue sv1, SetValue sv2) {
		Set<Value> set1 = sv1.getValue();
		Set<Value> set2 = sv2.getValue();
		Set<Value> result = new HashSet<Value>();

		Iterator<?> e1 = set1.iterator();

		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			if (!set2.contains(v)) {
				result.add(v);
			}
		}

		Iterator<?> e2 = set2.iterator();

		while (e2.hasNext()) {
			Value v = (Value) e2.next();
			if (!set1.contains(v)) {
				result.add(v);
			}
		}

		return new SetValue(result);
	}

	public static SetValue intersection(SetValue sv1, SetValue sv2) {
		Set<Value> set1 = sv1.getValue();
		Set<Value> set2 = sv2.getValue();
		Set<Value> result = new HashSet<Value>();

		Iterator<?> e1 = set1.iterator();
		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			if (set2.contains(v)) {
				result.add(v);
			}
		}

		Iterator<?> e2 = set2.iterator();

		while (e2.hasNext()) {
			Value v = (Value) e2.next();
			if (set1.contains(v)) {
				result.add(v);
			}
		}

		return new SetValue(result);
	}

	public static SetValue union(SetValue sv1, SetValue sv2) {
		Set<Value> set1 = sv1.getValue();
		Set<Value> set2 = sv2.getValue();
		Set<Value> result = new HashSet<Value>();
		Iterator<?> e1 = set1.iterator();
		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			result.add(v);
		}
		Iterator<?> e2 = set2.iterator();
		while (e2.hasNext()) {
			Value v = (Value) e2.next();
			result.add(v);
		}
		return new SetValue(result);
	}

	public static StringValue toString(SetValue set) {
		Set<Value> set1 = set.getValue();
		Iterator<?> e1 = set1.iterator();
		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			set1.add(v);
		}
		return new StringValue(set1.toString());
	}

	public static StringValue concat(StringValue str1, StringValue str2) {
		String str1Value = str1.getValue();
		String str2Value = str2.getValue();
		/*if (str1Value.startsWith("\"") && str1Value.endsWith("\"")) {
			str1Value = str1Value.substring(0, str1Value.length() - 1);
		}
		if (str2Value.startsWith("\"") && str2Value.endsWith("\"")) {
			str2Value = str2Value.substring(1, str2Value.length());
		}*/
		return new StringValue(str1Value + str2Value);
	}

	public static StringValue plus(StringValue str1, StringValue str2) {
		return concat(str1, str2);
	}

	public static IntegerValue toInteger(StringValue str) {
		return new IntegerValue(str.getValue());
	}

	public static RealValue toReal(StringValue str) {
		return new RealValue(str.getValue());
	}

	// fim clayton

	public static IntegerValue size(SetValue set1) {
		return new IntegerValue(set1.getValue().size());
	}

	public static BooleanValue isEmpty(SetValue set1) {
		return BooleanValue.parseBooleanValue(set1.getValue().isEmpty());
	}

	public static BooleanValue isEmpty(SequenceValue seq) {
		return BooleanValue.parseBooleanValue(seq.getValue().isEmpty());
	}

	public static SequenceValue asSequence(SetValue set1) {
		return new SequenceValue(new LinkedList<Value>(set1.getValue()));
	}

	public static BagValue asBag(SetValue set1) {
		return new BagValue(new ArrayList<Value>(set1.getValue()));
	}

	public static BooleanValue contains(SetValue sv, TupleValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue contains(SetValue sv, IntegerValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue notin(SetValue sv, TupleValue av) {
		return BooleanValue.parseBooleanValue(!sv.getValue().contains(av));
	}

	public static BooleanValue notin(SetValue sv, IntegerValue av) {
		return BooleanValue.parseBooleanValue(!sv.getValue().contains(av));
	}

	public static SetValue including(SetValue sv, Value iv) {
		Set<Value> set = sv.getValue();
		Set<Value> n_set = new HashSet<Value>(set);
		n_set.add(iv);
		return new SetValue(n_set);
	}

	public static SetValue excluding(SetValue sv, Value iv) {
		Set<Value> set = sv.getValue();
		Set<Value> n_set = new HashSet<Value>(set);
		n_set.remove(iv);
		return new SetValue(n_set);
	}

	public static SetValue excluding(SetValue sv, TupleValue iv) {
		return excluding(sv, (Value) iv);
	}

	public static SetValue excluding(SetValue sv, SetValue iv) {
		return excluding(sv, (Value) iv);
	}

	public static SetValue excluding(SetValue sv, IntegerValue iv) {
		return excluding(sv, (Value) iv);
	}

	public static IntegerValue sum(SetValue<Integer> sv) {
		long somma = 0;
		for (Value v : sv.getValue()) {
			somma = somma + ((IntegerValue) v).getValue();
		}
		return new IntegerValue(somma);
	}

	public static IntegerValue sum(BagValue<Integer> sv) {
		long somma = 0;
		for (Value v : sv.getValue()) {
			somma = somma + ((IntegerValue) v).getValue();
		}
		return new IntegerValue(somma);
	}

	// Map

	public static Value at(MapValue mapValue, Value key) {
		Map<Value, Value> map = mapValue.getValue();
		Value value = map.get(key);
		return value == null ? UndefValue.UNDEF : value;
	}

	public static BooleanValue eq(TupleValue tuple1, TupleValue tuple2) {
		return BooleanValue.parseBooleanValue(tuple1.equals(tuple2));
	}

	// String

	public static BooleanValue eq(StringValue op1, StringValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue().equals(op2.getValue()));
	}

	public static BooleanValue neq(StringValue op1, StringValue op2) {
		return not(eq(op1, op2));
	}

	public static IntegerValue size(StringValue str) {
		String strValue = str.getValue();
		if (strValue.startsWith("\"") && strValue.endsWith("\"")) {
			strValue = strValue.substring(1, strValue.length() - 1);
		}
		return new IntegerValue(strValue.length());
	}

	public static BooleanValue contains(StringValue str, StringValue subStr) {
		String strValue = str.getValue();
		if (strValue.startsWith("\"") && strValue.endsWith("\"")) {
			strValue = strValue.substring(1, strValue.length() - 1);
		}
		String subStrValue = subStr.getValue();
		if (subStrValue.startsWith("\"") && subStrValue.endsWith("\"")) {
			subStrValue = subStrValue.substring(1, subStrValue.length() - 1);
		}
		return BooleanValue.parseBooleanValue(strValue.contains(subStrValue));
	}

	public static StringValue toLower(StringValue str) {
		return new StringValue(str.getValue().toLowerCase());
	}

	public static StringValue toUpper(StringValue str) {
		return new StringValue(str.getValue().toUpperCase());
	}

	public static Value toChar(StringValue str) {
		String strValue = str.getValue();
		if (strValue.length() == 1) {
			return new CharValue(strValue.charAt(0));
		} else {
			return UndefValue.UNDEF;
		}
	}

	public static SequenceValue split(StringValue str, StringValue regex) {
		String strValue = str.getValue();
		if (strValue.startsWith("\"") && strValue.endsWith("\"")) {
			strValue = strValue.substring(1, strValue.length() - 1);
		}
		String regexValue = regex.getValue();
		if (regexValue.startsWith("\"") && regexValue.endsWith("\"")) {
			regexValue = regexValue.substring(1, regexValue.length() - 1);
		}
		// System.err.println(Arrays.toString(strValue.split("\\|")));
		// System.err.println(regexValue);
		// System.err.println(regexValue.equals("\\|"));
		String[] split = strValue.split(regexValue);
		// System.err.println(Arrays.toString(split));
		List<Value> values = new ArrayList<Value>();
		for (String s : split) {
			values.add(new StringValue(s));
		}
		return new SequenceValue(values);
	}

	// Char

	public static StringValue toString(CharValue c) {
		return new StringValue(c.toString());
	}

	public static BooleanValue lt(CharValue c1, CharValue c2) {
		return (c1.getValue() < c2.getValue()) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}

	public static BooleanValue gt(CharValue c1, CharValue c2) {
		return (c1.getValue() > c2.getValue()) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}

	public static BooleanValue le(CharValue c1, CharValue c2) {
		return (c1.getValue() <= c2.getValue()) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}

	public static BooleanValue ge(CharValue c1, CharValue c2) {
		return (c1.getValue() >= c2.getValue()) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}

	// Undef
	// Da questo punto, inizia il codice che implementa le funzioni
	// della libreria standard

	public static BooleanValue eq(UndefValue undef, Value value) {
		return BooleanValue.parseBooleanValue(undef == value);
	}

	public static BooleanValue neq(UndefValue undef, Value value) {
		return not(eq(undef, value));
	}

	// Abstract Value

	public static BooleanValue eq(ReserveValue op1, ReserveValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue().equals(op2.getValue()));
	}

	public static BooleanValue neq(ReserveValue op1, ReserveValue op2) {
		return not(eq(op1, op2));
	}

	// Enumeration

	public static BooleanValue eq(EnumValue op1, EnumValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue().equals(op2.getValue()));
	}

	public static BooleanValue neq(EnumValue op1, EnumValue op2) {
		return BooleanValue.parseBooleanValue(!op1.getValue().equals(op2.getValue()));
	}

	public static BooleanValue eq(UndefValue op1, UndefValue op2) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue neq(UndefValue op1, UndefValue op2) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue eq(UndefValue op1, EnumValue op2) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue neq(UndefValue op1, EnumValue op2) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue isDef(org.asmeta.simulator.value.EnumValue e) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue isDef(org.asmeta.simulator.value.UndefValue e) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue isDef(Value v) {
		return BooleanValue.parseBooleanValue(!v.equals(UndefValue.UNDEF));
	}

	public static BooleanValue isUndef(Value v) {
		return not(isDef(v));
	}

	public static BooleanValue eq(Value v, UndefValue undef) {
		return BooleanValue.parseBooleanValue(v.equals(UndefValue.UNDEF));
	}

	public static BooleanValue neq(Value v, UndefValue undef) {
		return not(eq(v, undef));
	}

	public static BooleanValue isUndef(UndefValue v) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue isUndef(AgentValue v) {
		return BooleanValue.FALSE;
	}

	// Agent

	public static RuleValue program(AgentValue agent) {
		Rule program = agent.getProgram();
		if (program == null) {
			throw new RuntimeException("Agent " + agent.getId() + " has not program");
		}
		MacroCallRule macro = (MacroCallRule) program;
		MacroDeclaration macroDcl = macro.getCalledMacro();
		return new RuleValue(macroDcl, agent);
	}

	public static BooleanValue eq(AgentValue a1, AgentValue a2) {
		if (a1 == a2)
			return BooleanValue.TRUE;
		return BooleanValue.parseBooleanValue(a1.getId().equals(a2.getId()));

	}

	public static BooleanValue neq(AgentValue a1, AgentValue a2) {
		if (a1 == a2)
			return BooleanValue.FALSE;
		return BooleanValue.parseBooleanValue(!a1.getId().equals(a2.getId()));

	}

	public static BooleanValue eq(UndefValue u, AgentValue a) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue eq(UndefValue u, BooleanValue b) {
		return BooleanValue.FALSE;
	}

	public static <T> Value<T> first(TupleValue<T> t) {
		return t.getValue().get(0);
	}

	public static <T> Value<T> second(TupleValue<T> t) {
		return t.getValue().get(1);
	}

	public static <T> Value<T> third(TupleValue<T> t) {
		return t.getValue().get(2);
	}

	public static <T> Value<T> fourth(TupleValue<T> t) {
		return t.getValue().get(3);
	}

	public static <T> Value<T> fifth(TupleValue<T> t) {
		return t.getValue().get(4);
	}

	public static <T> Value<T> sixth(TupleValue<T> t) {
		return t.getValue().get(5);
	}

	public static <T> Value<T> seventh(TupleValue<T> t) {
		return t.getValue().get(6);
	}

	public static <T> Value<T> eighth(TupleValue<T> t) {
		return t.getValue().get(7);
	}

	public static <T> Value<T> nineth(TupleValue<T> t) {
		return t.getValue().get(8);
	}

	public static BooleanValue isDef(TupleValue tv) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue isUndef(TupleValue tv) {
		return BooleanValue.FALSE;
	}

	public static Value first(RealValue t1, RealValue t2) {
		return t1;
	}

	public static Value second(RealValue t1, RealValue t2) {
		return t2;
	}

	public static Value first(ReserveValue t1, IntegerValue t2) {
		return t1;
	}

	public static Value second(ReserveValue t1, IntegerValue t2) {
		return t2;
	}

	public static Value append(SequenceValue s, Value v) {
		SequenceValue result = new SequenceValue(s);
		result.getValue().add(v);
		return result;
	}

	public static Value prepend(Value v, SequenceValue s) {
		SequenceValue result = new SequenceValue(s);
		result.getValue().add(0, v);
		return result;
	}

	public static Value asBag(SequenceValue s) {
		return new BagValue(s.getValue());
	}

	public static SequenceValue append(SequenceValue s, IntegerValue iv) {
		SequenceValue result = new SequenceValue(s);
		result.getValue().add(iv);
		return result;
	}

	public static IntegerValue iton(IntegerValue iv) {
		return iv;
	}

	// These last functions are not part of the standard library, their
	// purpose is to test the name resolution procedure

	public static void foo(IntegerValue i, Value v) {
		throw new UnsupportedOperationException();
	}

	public static void foo(Value v, IntegerValue i) {
		throw new UnsupportedOperationException();
	}

	// Debug functions

	public static IntegerValue print(Value v1) {
		return _print(v1);
	}

	public static IntegerValue print(Value v1, Value v2) {
		return _print(v1, v2);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3) {
		return _print(v1, v2, v3);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4) {
		return _print(v1, v2, v3, v4);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5) {
		return _print(v1, v2, v3, v4, v5);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5, Value v6) {
		return _print(v1, v2, v3, v4, v5, v6);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7) {
		return _print(v1, v2, v3, v4, v5, v6, v7);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7, Value v8) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7, Value v8,
			Value v9) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8, v9);
	}

	public static IntegerValue print(Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7, Value v8,
			Value v9, Value v10) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
	}

	public static IntegerValue _print(Value... values) {
		for (Value v : values) {
			System.out.print(v + " ");
		}
		System.out.println();
		return new IntegerValue(values.length);
	}

	public static StringValue toString(Value value) {
		return new StringValue(value.toString());
	}

	public static IntegerValue currTimeNanosecs() {
		return new IntegerValue(System.nanoTime());
	}

	public static IntegerValue currTimeMillisecs() {
		return new IntegerValue(System.currentTimeMillis());
	}

	public static IntegerValue currTimeSecs() {
		return new IntegerValue(System.currentTimeMillis() / 1000);
	}
}
