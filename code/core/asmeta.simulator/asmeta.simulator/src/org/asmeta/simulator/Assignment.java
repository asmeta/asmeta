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
 * VariableAssignment.java
 *
 * Created on 31 maggio 2006, 9.16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import asmeta.terms.basicterms.VariableTerm;

/**
 * Keeps the association between variable names and their own contents.
 * 
 */
public class Assignment<E> {

	// private static Logger logger = Logger.getLogger(Assignment.class);

	/**
	 * Map between names and contents.
	 * 
	 */
	protected Map<String, E> assignmentMap;

	/**
	 * Constructor.
	 * 
	 */
	public Assignment() {
		assignmentMap = new HashMap<String, E>();
	}

	/**
	 * Copy constructor.
	 * 
	 */
	public Assignment(Assignment<E> assignment) {
		assignmentMap = new HashMap<String, E>(assignment.assignmentMap);
	}

	/**
	 * Gets the content of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return the content
	 */
	public E get(VariableTerm variable) {
		return assignmentMap.get(variable.getName());
	}

	/**
	 * Sets the content of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @param value
	 *            a value
	 */
	public void put(VariableTerm variable, E value) {
		assignmentMap.put(variable.getName(), value);
	}

	/**
	 * Assigns a list of values to a list of variables.
	 * 
	 * @param variables
	 *            variable list
	 * @param arguments
	 *            value list
	 */
	public void put(Collection<?>/* <VariableTerm> */ variables, E[] arguments) {
		int i = 0;
		for (Object o : variables) {
			VariableTerm var = (VariableTerm) o;
			E val = arguments[i++];
			put(var, val);
		}
	}
}
