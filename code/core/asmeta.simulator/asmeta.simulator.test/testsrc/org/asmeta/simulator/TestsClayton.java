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
package org.asmeta.simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.Test;


class TestsClayton {

	@Test void difference() {


		Set<Value> conj1 = new HashSet<>();
		Set<Value> conj2 = new HashSet<>();

		conj1.add(new StringValue("a1"));
		conj1.add(new StringValue("a2"));
		conj1.add(new StringValue("a3"));

		conj2.add(new StringValue("a3"));
		conj2.add(new StringValue("a4"));
		conj2.add(new StringValue("a5"));

		SetValue sv1 = new SetValue(conj1);
		SetValue sv2 = new SetValue(conj2);

		Set<Value> set1 = sv1.getValue();
		Set<Value> set2 = sv2.getValue();

		Set<Value> result = new HashSet<>();

		for (Value v : set1) {
			if (!set2.contains(v)) {
				result.add(v);
			}
		}

		for (Value v : set2) {
			if (!set1.contains(v)) {
				result.add(v);
			}
		}


		Set<Value> toTest = new HashSet<>();

		toTest.add(new StringValue("a1"));
		toTest.add(new StringValue("a2"));
		toTest.add(new StringValue("a4"));
		toTest.add(new StringValue("a5"));

		assertEquals(toTest,result);
	}

	@Test void intersection() {


		Set<Value> conj1 = new HashSet<>();
		Set<Value> conj2 = new HashSet<>();

		conj1.add(new StringValue("a1"));
		conj1.add(new StringValue("a2"));
		conj1.add(new StringValue("a3"));

		conj2.add(new StringValue("a3"));
		conj2.add(new StringValue("a4"));
		conj2.add(new StringValue("a5"));

		SetValue sv1 = new SetValue(conj1);
		SetValue sv2 = new SetValue(conj2);

		Set<Value> set1 = sv1.getValue();
		Set<Value> set2 = sv2.getValue();

		Set<Value> result = new HashSet<>();

		for (Value v : set1) {
			if (set2.contains(v)) {
				result.add(v);
			}
		}

		for (Value v : set2) {
			if (set1.contains(v)) {
				result.add(v);
			}
		}

		Set<Value> toTest = new HashSet<>();

		toTest.add(new StringValue("a3"));

		assertEquals(toTest,result);
	}

	@Test void testtoStringSetValue() {

		Set<Value> conj2 = new HashSet<>();

		conj2.add(new StringValue("a3"));
		conj2.add(new StringValue("a4"));
		conj2.add(new StringValue("a5"));

		for (Value v : conj2) {
			conj2.add(v);
		}

		assertEquals("[a3, a4, a5]", conj2.toString());

	}

	@Test void testindexOf() {

		Value v = new StringValue("a3");

		List<Value> l = new ArrayList<>();
		Integer result = -1;

		l.add(new StringValue("a1"));
		l.add(new StringValue("a2"));
		l.add(new StringValue("a3"));


		for (int i=0; i < l.size(); i++) {
			if (l.get(i).equals(v)) {
				result = i;
				break;
			}
		}
		assertEquals(2, result.intValue());
	}


}
