package org.asmeta.interpreter.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.interpreter.value.AgentValue;
import org.asmeta.interpreter.value.BooleanValue;
import org.asmeta.interpreter.value.EnumValue;
import org.asmeta.interpreter.value.IntegerValue;
import org.asmeta.interpreter.value.MapValue;
import org.asmeta.interpreter.value.RealValue;
import org.asmeta.interpreter.value.ReserveValue;
import org.asmeta.interpreter.value.RuleValue;
import org.asmeta.interpreter.value.SequenceValue;
import org.asmeta.interpreter.value.SetValue;
import org.asmeta.interpreter.value.StringValue;
import org.asmeta.interpreter.value.TupleValue;
import org.asmeta.interpreter.value.UndefValue;
import org.asmeta.interpreter.value.Value;

import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Implementations of the functions of the standard library.
 *
 */
public class StandardLibrary {

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

	public static Value or(UndefValue op1, UndefValue op2) {
			return UndefValue.UNDEF;
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
		if (!op1.getValue() && !op2.getValue()
				|| op1.getValue() && op2.getValue())
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

	public static IntegerValue div(IntegerValue v1, IntegerValue op2) {
		return new IntegerValue(v1.getValue() / op2.getValue());
	}

	public static BooleanValue eq(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() == op2.getValue());
	}

	public static BooleanValue neq(IntegerValue v1, IntegerValue op2) {
		return BooleanValue.parseBooleanValue(v1.getValue() != op2.getValue());
	}

	public static BooleanValue neq(RealValue op1, RealValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue() != op2.getValue());
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

	public static RealValue ntor(org.asmeta.interpreter.value.IntegerValue iv) {
		return new RealValue(iv.getValue());
	}

	public static RealValue itor(org.asmeta.interpreter.value.IntegerValue iv) {
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

	public static SetValue range(IntegerValue v1, IntegerValue iv) {
		Set<Value> result = new HashSet<Value>();
		for (int i = (int) v1.getValue(); i < iv.getValue(); i++)
			result.add(new IntegerValue(i));
		return new SetValue(result);
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
		return BooleanValue.parseBooleanValue(op1.getValue() == op2.getValue());
	}

	public static IntegerValue rton(RealValue op1) {
		return new IntegerValue((long) op1.getValue());
	}

	public static IntegerValue rtoi(RealValue op1) {
		return new IntegerValue((long) op1.getValue());
	}

	public static RealValue abs(RealValue rv) {
		double d = rv.getValue();
		if (d > 0 ) return rv;
		else return new RealValue(-d);
	}

	// Sequence

	public static BooleanValue eq(SequenceValue seq1, SequenceValue seq2) {
		return BooleanValue.parseBooleanValue(seq1.equals(seq2));
	}

	public static BooleanValue neq(SequenceValue seq1, SequenceValue seq2) {
		return BooleanValue.parseBooleanValue(!seq1.equals(seq2));
	}

	public static IntegerValue length(SequenceValue seq1) {
		return new IntegerValue(seq1.getValue().size());
	}

	public static Value at(SequenceValue seq1, IntegerValue iv) {
		return seq1.getValue().get((int) iv.getValue());
	}

	public static Value first(SequenceValue seq1) {
		return seq1.getValue().get(0);
	}

	public static SequenceValue tail(SequenceValue seq1) {
		List<Value> l = seq1.getValue();
		List<Value> nl =
			new ArrayList<Value>(l.subList(1, seq1.getValue().size()));
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

	public static SequenceValue subSequence(SequenceValue sv, IntegerValue iv1,
			IntegerValue iv2) {
		List<Value> l = sv.getValue();
		List<Value> nl = new ArrayList<Value>(
				l.subList((int) iv1.getValue(), (int) iv2.getValue()));
		return new SequenceValue(nl);
	}

	public static BooleanValue contains(SequenceValue sv, ReserveValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue contains(SequenceValue sv, Value av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue contains(UndefValue sv, Value av) {
		return BooleanValue.FALSE;
	}
	
	public static BooleanValue contains(SetValue sv, Value av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	// Set

	public static Value chooseone(SetValue setValue) {
		Set<Value> set = setValue.getValue();
		if (set.isEmpty()) {
			throw new RuntimeException("ERROR at chooseone: the set is empty ");
		}
		return set.iterator().next();
	}

	public static BooleanValue eq(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(set1.equals(set2));
	}

	public static BooleanValue neq(SetValue set1, SetValue set2) {
		return BooleanValue.parseBooleanValue(!set1.equals(set2));
	}

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

	public static BooleanValue contains(SetValue sv, TupleValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static BooleanValue contains(SetValue sv, IntegerValue av) {
		return BooleanValue.parseBooleanValue(sv.getValue().contains(av));
	}

	public static SetValue including(SetValue sv, Value iv) {
		Set<Value> set =  sv.getValue();
		Set<Value> n_set = new HashSet<Value>(set);
		n_set.add(iv);
		return  new SetValue(n_set);
	}

	public static SetValue excluding(SetValue sv, Value iv) {
		Set<Value> set =  sv.getValue();
		Set<Value> n_set = new HashSet<Value>(set);
		n_set.remove(iv);
		return  new SetValue(n_set);
	}

	public static SetValue excluding(SetValue sv, TupleValue iv) {
		return excluding(sv,(Value) iv);
	}

	public static SetValue excluding(SetValue sv, SetValue iv) {
		return excluding(sv,(Value) iv);
	}

	public static SetValue excluding(SetValue sv, IntegerValue iv) {
		return excluding(sv,(Value) iv);
	}

	// Map

	public static Value at(MapValue mapValue, Value key) {
		Map<Value, Value> map = mapValue.getValue();
		Value value = map.get(key);
		return value == null ? UndefValue.UNDEF : value;
	}

	// String

	public static BooleanValue eq(StringValue op1, StringValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue().equals(
				op2.getValue()));
	}

	public static BooleanValue neq(StringValue op1, StringValue op2) {
		return not(eq(op1, op2));
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
		return BooleanValue.parseBooleanValue(op1.getValue().equals(
				op2.getValue()));
	}

	public static BooleanValue neq(ReserveValue op1, ReserveValue op2) {
		return not(eq(op1, op2));
	}

	// Enumeration

	public static BooleanValue eq(EnumValue op1, EnumValue op2) {
		return BooleanValue.parseBooleanValue(op1.getValue().equals(
				op2.getValue()));
	}

	public static BooleanValue neq(EnumValue op1, EnumValue op2) {
		return BooleanValue.parseBooleanValue(!op1.getValue().equals(
				op2.getValue()));
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

	public static BooleanValue isDef(org.asmeta.interpreter.value.EnumValue e) {
		return BooleanValue.TRUE;
	}

	public static BooleanValue isDef(org.asmeta.interpreter.value.UndefValue e) {
		return BooleanValue.FALSE;
	}

	public static BooleanValue isDef(Value v) {
		return BooleanValue.parseBooleanValue(v != UndefValue.UNDEF);
	}

	public static BooleanValue isUndef(Value v) {
		return not(isDef(v));
	}

	public static BooleanValue eq(Value v, UndefValue undef) {
		return BooleanValue.parseBooleanValue(v == UndefValue.UNDEF);
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
		return new RuleValue(macroDcl);
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

	public static Value first(TupleValue t) {
		return t.getValue()[0];
	}

	public static Value second(TupleValue t) {
		return t.getValue()[1];
	}

	public static BooleanValue isDef(TupleValue tv){
		return BooleanValue.TRUE;
	}
	public static BooleanValue isUndef(TupleValue tv){
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

	public static Value append(SequenceValue s, IntegerValue iv) {
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

	public static IntegerValue print(
			Value v1, Value v2, Value v3, Value v4, Value v5, Value v6) {
		return _print(v1, v2, v3, v4, v5, v6);
	}

	public static IntegerValue print(
			Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7) {
		return _print(v1, v2, v3, v4, v5, v6, v7);
	}

	public static IntegerValue print(
			Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7,
			Value v8) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8);
	}

	public static IntegerValue print(
			Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7,
			Value v8, Value v9) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8, v9);
	}

	public static IntegerValue print(
			Value v1, Value v2, Value v3, Value v4, Value v5, Value v6, Value v7,
			Value v8, Value v9, Value v10) {
		return _print(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
	}

	public static IntegerValue _print(Value...values) {
		for (Value v : values) {
			System.out.print(v + " ");
		}
		System.out.println();
		return new IntegerValue(values.length);
	}

}
