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

/*
 * ReflectiveVisitor.java
 *
 * Created on 22 maggio 2006, 11.16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.parser.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * Implements a reflective visitor,
 * see <i>Object invokeMethod(Object argument, String methodName)</i>.
 *
 * @param <T> the generic type of the type returned by the visit
 */
public abstract class ReflectiveVisitor<T> {

	private static Logger logger = Logger.getLogger(ReflectiveVisitor.class);

	/**
	 * Equivalent to invokeMethod(object, "visit").
	 * 
	 */
	public T visit(Object object) {
		logger.debug("<visit>" + object + "</visit>");
		return invokeMethod(object);
	}

	/**
	 * Equivalent to invokeMethod(object, "visit").
	 */
	protected T invokeMethod(Object object) {
		return invokeMethod(object, "visit");
	}

	/**
	 * Invokes the method (belonging to this class or to a subclass) with the 
	 * given name and with one formal parameter whose type is the first interface
	 * (i.e. the first returned by getInterfaces()) implemented by the given argument.
	 * 
	 * @param argument the actual parameter
	 * @param methodName the method name
	 * @return the object returned by the invoked method
	 */
	protected T invokeMethod(Object argument, String methodName) {
		Method m = null;
		T result = null;
		if (argument != null) {
			assert argument!=null;
			Class<?>[] interfaces = argument.getClass().getInterfaces();
			Class<?> anInterface = null;
			try {
				assert interfaces != null;	
				// if there is no interface for the argument (normally it is an implementation) 
				if (interfaces.length ==0)
					anInterface = argument.getClass();
				else 
					// take the first interface
					anInterface = interfaces[0];
				//System.out.println(methodName + " - " + anInterface + " - " + argument.toString());
				//logger.debug("<visiting>Interface:" + anInterface.getName() + "</visiting>");
				assert methodName != null;
				assert anInterface != null;
				m = this.getClass().getMethod(methodName, anInterface);
				assert m != null;
				assert argument != null;
				result = (T) m.invoke(this, argument);
			} catch (SecurityException e) {
				throw e;
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(
						"Unknown function " + this + " " + methodName + "(" + anInterface + ")");
						//"Unknown function " + this + " visit(" + anInterface + ")");
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e + " method: " + methodName + "(" + anInterface + ")");
			} catch (InvocationTargetException e) {
				Throwable cause = e.getCause();
				if (cause instanceof RuntimeException) {
					throw (RuntimeException) e.getCause();
				} else if (cause instanceof java.lang.AssertionError){
					throw (java.lang.AssertionError) e.getCause();
				} else {
					throw new RuntimeException(e.getCause());
				}
			}
		}
		return result;
	}

}
