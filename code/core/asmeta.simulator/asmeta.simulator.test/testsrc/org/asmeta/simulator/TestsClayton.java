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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;


public class TestsClayton {

	@Test
	public void testDifference() {


		Set<Value> conj1 = new HashSet<Value>();
		Set<Value> conj2 = new HashSet<Value>();

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

		Set<Value> result = new HashSet<Value>();

		Iterator<?> e1 = set1.iterator();
		Iterator<?> e2 = set2.iterator();

		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			if (!set2.contains(v)) {
				result.add(v);
			}
		}

		while (e2.hasNext()) {
			Value v = (Value) e2.next();
			if (!set1.contains(v)) {
				result.add(v);
			}
		}


		Set<Value> toTest = new HashSet<Value>();

		toTest.add(new StringValue("a1"));
		toTest.add(new StringValue("a2"));
		toTest.add(new StringValue("a4"));
		toTest.add(new StringValue("a5"));

		assertEquals(toTest,result);
	}

	@Test
	public void testIntersection() {


		Set<Value> conj1 = new HashSet<Value>();
		Set<Value> conj2 = new HashSet<Value>();

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

		Set<Value> result = new HashSet<Value>();

		Iterator<?> e1 = set1.iterator();
		Iterator<?> e2 = set2.iterator();

		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			if (set2.contains(v)) {
				result.add(v);
			}
		}

		while (e2.hasNext()) {
			Value v = (Value) e2.next();
			if (set1.contains(v)) {
				result.add(v);
			}
		}

		Set<Value> toTest = new HashSet<Value>();

		toTest.add(new StringValue("a3"));

		assertEquals(toTest,result);
	}

	@Test
	public void testtoStringSetValue() {

		Set<Value> conj2 = new HashSet<Value>();

		conj2.add(new StringValue("a3"));
		conj2.add(new StringValue("a4"));
		conj2.add(new StringValue("a5"));

		Iterator<?> e1 = conj2.iterator();

		while (e1.hasNext()) {
			Value v = (Value) e1.next();
			conj2.add(v);
		}

		assertEquals("[a3, a4, a5]", conj2.toString());

	}

	@Test
	public void testindexOf() {

		Value v = new StringValue("a3");

		List<Value> l = new ArrayList<Value>();
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
