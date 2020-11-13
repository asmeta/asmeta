/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 * 
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 * 
 *   http://www.gnu.org/licenses/gpl.txt
 * 
 *   
 *******************************************************************************/
package org.asmeta.simulator;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.asmeta.simulator.util.StandardLibrary;
import org.asmeta.simulator.util.UnresolvedReferenceException;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * Evaluator of the function of the standard library.
 * 
 */
public class StdlEvaluator {
	
	private static Logger logger = Logger.getLogger(StdlEvaluator.class);
	
	/** 
	 * Function declarations of the standard library (the declaration are 
	 * sorted with respect to the name and the number of arguments).
	 * 
	 */
	static WrappedMethod[] funDcls;
	
	static {
		// wraps Method objects before sorting
		Method[] mm = StandardLibrary.class.getMethods();
		funDcls = new WrappedMethod[mm.length];
		for (int i = 0; i < mm.length; i++) {
			funDcls[i] = new WrappedMethod(mm[i]);
		}
		// sorts them
		Arrays.sort(funDcls);
	}
	
	/**
	 * Evaluates the given standard library function.
	 *  
	 * @param function a function declaration
	 * @param arguments actual parameters
	 * @return the function value
	 */
	public Value visit(Function function, Object[] arguments) {		
		// assert(function.getArity() == arguments.length);	
		logger.debug("<StaticFunction>" + function.getName()+ "</StaticFunction>");
		String name = function.getName();
		Class<?>[] argTypes = getClasses(arguments);
		Method m = resolve(name, argTypes);
		Object result;
		try {
			result = m.invoke(null, arguments);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			// this is the most frequent exception, so to avoid long chain of 
			// encapsulated runtime exception, I do a type check
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			} else {
				throw new RuntimeException(e.getCause());
			}
		}			
		return (Value) result;
	}
		
	/**
	 * Returns the method of the standard library that best matches with the
	 * given function declaration.
	 *  
	 * @param name function name
	 * @param argTypes list of parameter types
	 * @return a method declaration
	 * @throws UnresolvedReferenceException if no method is found or there
	 * are too methods compatible with the given declaration
	 */
	protected Method resolve(String name, Class<?>[] argTypes)
	throws UnresolvedReferenceException {
		WrappedMethod m1 = new WrappedMethod(name, argTypes);
		int[] jj = findCandidates(m1);
		int j1 = jj[0], j2 = jj[1];
		// NOTE 1
		// bestRating = 999999, no method can have a rating so high to exceed this
		// value, so the first one found compatible sets the variables bestRating
		// and bestIndex with his own values
		// NOTE 2
		// bestIndex = -1 means that I have not found a method (until now)		 
		int bestRating = 999999, bestIndex = -1;
		int bestIndex2 = -1;
		for (; j1 < j2; j1++) {
			int r = rating(m1, funDcls[j1]);
			if (r < 0) {
				// declaration are not compatible
				continue;
			} else if (r < bestRating) {
				bestRating = r;
				bestIndex = j1;
				bestIndex2 = -1;
			} else if (r == bestRating) {
				bestIndex2 = j1;
			}
		}
		if (bestIndex == -1) {
			throw new UnresolvedReferenceException(
					"ERROR: Unresolved reference " + m1);
		} else if (bestIndex2 != -1) {
			throw new UnresolvedReferenceException(
					"ERROR: Unresolved reference " + m1 +
					" Too many compatible definitions: " +
					funDcls[bestIndex] + ", " + funDcls[bestIndex2]);			
		}
		return funDcls[bestIndex].getMethod();
	}
	
	/**
	 * Returns the functions with the same name and the same number of arguments
	 * of the given function.
	 * 
	 * @param m1 the function name to resolve
	 * @return a pair of index (j1, j2) such that
	 * funDcls[j1-1] < funDcls[j1] = ... = funDcls[j2-1] < funDcls[j2]. 
	 * If no declaration matches m1 then returns j1 > j2.
	 */
	private int[] findCandidates(WrappedMethod m1) {		
		int i = Arrays.binarySearch(funDcls, m1);
		if (i >= 0) {
			// element found
			int j1, j2;
			for (j1 = i; j1 > 0; j1--) {
				if (m1.compareTo(funDcls[j1 - 1]) != 0) {
					break;
				}
			}
			for (j2 = i + 1; j2 < funDcls.length; j2++) {
				if (m1.compareTo(funDcls[j2]) != 0) {
					break;
				}
			}
			// ASSERT: funDcls[j1-1] < funDcls[j1] = ... = funDcls[j2-1] < funDcls[j2]
			return new int[]{j1, j2};
		}
		return new int[]{0, -1};
	}

	/**
	 * Computes a measure of compatibility between two function declarations.
	 * The following procedure is adopted:<br>
	 * - F1(A1,..,Z1) is the declaration of the first function with name F1 and
	 * parameters types A1,..,Z1<br>
	 * - F2(A2,..,Z2) is the declaration of the second function with name F2 and
	 * parameters types A2,..,Z2<br>
	 * - the rating is equals to g(A1,A2) + ... + g(Z1, Z2) with g that returns
	 * 0 if A1 = A2 or returns 1 if A1 != A2 but A2 = Value.class
	 * 
	 * @param m1 a function declaration
	 * @param m2 a function declaration
	 * @return a negative number if the functions are not compatible, 0 if a
	 * strict matching is found, a positive number if the functions are 
	 * more or less compatible
	 */
	private int rating(WrappedMethod m1, WrappedMethod m2) {
		int rating = 0;
		Class<?>[] params1 = m1.getParameterTypes();
		Class<?>[] params2 = m2.getParameterTypes();
		for (int i = 0; i < params1.length; i++) {
			Class<?> p1 = params1[i];
			Class<?> p2 = params2[i];
			if (!p1.equals(p2)) {
				if (p2.equals(Value.class)) {
					rating++;
				} else {
					return -1;
				}
			}
		}
		return rating;
	}
	
	/**
	 * Returns an array whose elements are the Class objects of the first array
	 * elements.
	 *  
	 * @param arguments an array of object
	 * @return an array of Class objects
	 */
	public static Class<?>[] getClasses(Object[] arguments) {
		Class<?>[] classes = new Class[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			classes[i] = arguments[i].getClass();
		}
		return classes;
	}
	
	/**
	 * Wraps the Method class, so I can redefine methods compareTo() and
	 * equals().
	 *
	 */
	static class WrappedMethod implements Comparable<WrappedMethod> {

		private Method m;
		private String name;
		private Class<?>[] params;

		public WrappedMethod(Method m) {
			this.m = m;
			this.name = m.getName();
			this.params = m.getParameterTypes();
		}

		/**
		 * Creates a new method declaration.
		 * 
		 * @param name function name
		 * @param params list of the classes that act as formal parameter types
		 */
		public WrappedMethod(String name, Class<?>[] params) {
			this.m = null;
			this.name = name;
			this.params = params;
		}

		public String getName() {
			return name;
		}

		public Class<?>[] getParameterTypes() {
			return params;
		}

		/**
		 * If the wrapped method belongs to the standard library, returns the
		 * related Method object.
		 * 
		 * @return the related Method object
		 */
		public Method getMethod() {
			return m;
		}
		
		@Override
		public boolean equals(Object arg0) {
			if (arg0 instanceof WrappedMethod) {
				WrappedMethod m2 = (WrappedMethod) arg0;
				return compareTo(m2) == 0;
			}
			throw new IllegalArgumentException();
		}

		@Override
		public int compareTo(WrappedMethod m2) {
			WrappedMethod m1 = this;
			int order = m1.getName().compareTo(m2.getName());
			if (order == 0) {
				Class<?>[] params1 = m1.getParameterTypes();
				Class<?>[] params2 = m2.getParameterTypes();
				return params1.length - params2.length;
			}
			return order;
		}

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(name);
			s.append("(");
			for (Class<?> c : params) {
				s.append(c.getSimpleName());
				s.append(",");
			}
			if (params.length > 0) {
				// delete last comma
				s.deleteCharAt(s.length() - 1);
			}
			s.append(")");
			return s.toString();
		}		
	}
}