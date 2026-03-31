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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import org.asmeta.simulator.StdlEvaluator.WrappedMethod;
import org.asmeta.simulator.util.UnresolvedReferenceException;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.Test;


/**
 * Test for StdlEvaluator class.
 *
 */
public class StdlEvaluatorTest {
	
	private StdlEvaluator eval = new StdlEvaluator();
	
	public void print() {
		for (WrappedMethod m : StdlEvaluator.funDcls) {
			System.out.println(m);
		}
	}

	@Test void abs() {
		String expectedName = "abs";
		Class<?>[] expectedTypes = new Class<?>[]{RealValue.class};
		String actualName = "abs";
		Class<?>[] actualTypes = new Class<?>[]{RealValue.class}; 
		testResolve(expectedName, expectedTypes, actualName, actualTypes,RealValue.class);
	}

	@Test void absInteger() {
		String expectedName = "abs";
		Class<?>[] expectedTypes = new Class<?>[]{IntegerValue.class};
		String actualName = "abs";
		Class<?>[] actualTypes = new Class<?>[]{IntegerValue.class}; 
		testResolve(expectedName, expectedTypes, actualName, actualTypes,IntegerValue.class);
	}

	@Test void eq() {
		String expectedName = "eq";
		Class<?>[] expectedTypes = new Class<?>[]{Value.class, UndefValue.class};
		String actualName = "eq";
		Class<?>[] actualTypes = new Class<?>[]{IntegerValue.class, UndefValue.class}; 
		testResolve(expectedName, expectedTypes, actualName, actualTypes,BooleanValue.class);
	}

	@Test void foo() {
		String expectedName = "foo";
		Class<?>[] expectedTypes = new Class<?>[]{IntegerValue.class, Value.class};
		String actualName = "foo";
		Class<?>[] actualTypes = new Class<?>[]{IntegerValue.class, IntegerValue.class};
		Class<String> x = String.class;
		assertThrows(UnresolvedReferenceException.class, () ->
			testResolve(expectedName, expectedTypes, actualName, actualTypes, x));
	}


	@Test void bar() {
		String expectedName = "bar";
		Class<?>[] expectedTypes = new Class<?>[]{};
		String actualName = "bar";
		Class<?>[] actualTypes = new Class<?>[]{};
		Class<String> x = String.class;
		assertThrows(UnresolvedReferenceException.class, () ->
			testResolve(expectedName, expectedTypes, actualName, actualTypes, x));
	}

	@Test void time() {
		String expectedName = "currTimeMillisecs";
		Class<?>[] expectedTypes = new Class<?>[]{};
		String actualName = "currTimeMillisecs";
		Class<?>[] actualTypes = new Class<?>[]{}; 
		testResolve(expectedName, expectedTypes, actualName, actualTypes,IntegerValue.class);
	}

	@Test void time2() {
		String expectedName = "currTimeMillisecs";
		Class<?>[] expectedTypes = new Class<?>[]{};
		String actualName = "currTimeMillisecs";
		Class<?>[] actualTypes = new Class<?>[]{}; 
		testResolve(expectedName, expectedTypes, actualName, actualTypes,IntegerValue.class);
		// now visit it
	}
	
	/**
	 * Searches a function with a given name and parameter types and checks that
	 * the method returned by the name resolution procedure has the expected 
	 * name and parameter types.
	 * 
	 * @param expectedName the expected function name
	 * @param expectedTypes the expected parameter types
	 * @param actualName the name of the function to search
	 * @param actualTypes the parameter types of the function to search
	 */
	private void testResolve(String expectedName, Class<?>[] expectedTypes,
			String actualName, Class<?>[] actualTypes, Class<?> returnType) {
		Method m = eval.resolve(actualName, actualTypes);
		assertEquals(expectedName, m.getName());
		assertEquals(returnType,m.getReturnType());
		assertArrayEquals(expectedTypes, m.getParameterTypes());
	}

}
