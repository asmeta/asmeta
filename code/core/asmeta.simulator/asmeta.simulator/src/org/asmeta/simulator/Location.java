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

import java.util.Arrays;

import org.asmeta.parser.util.Defs;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * A location is a pair made of a function signature and a tuple of values
 * (the actual parameters).
 * 
 */
public class Location {

//	private static Logger logger = Logger.getLogger(Location.class);

	/**
	 * The signature.
	 */
	private Function signature;

	/**
	 * The actual parameters.
	 */
	private Value[] elements;

	/**
	 * Creates a new location.
	 *  
	 * @param signature a signature
	 * @param elements the parameter values
	 */
	public Location(Function signature, Value[] elements) {
		// assert (signature.getArity() == elements.length)
		this.signature = signature;
		this.elements = elements;
	}

	/**
	 * Returns the location name.
	 * 
	 * @return the location name
	 */
	String getName() {
		return signature.getName();
	}

	/**
	 * Returns the signature.
	 *  
	 * @return the signature
	 */
	public Function getSignature() {
		return signature;
	}

	/**
	 * Returns the parameter values.
	 *  
	 * @return the values
	 */
	public Value[] getElements() {
		return elements;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Location) {
			Location location = (Location) object;
			return equals(location);
		}
		throw new IllegalArgumentException("Expected Location object");
	}

	/**
	 * Compares two location.<br>
	 * Two locations are equal if they have the same signature and the
	 * same values.
	 * 
	 * @param location a location
	 * @return true if the locations are equal
	 */
	private boolean equals(Location location) {
		return 
			Defs.equals(getSignature(), location.getSignature()) && 
			Arrays.equals(getElements(), location.getElements());
	}

	// cached version may have some problems
	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 7 * result + Arrays.deepHashCode(elements);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getName());
		if (elements.length > 0) {
			Object value = elements[0];
			s.append("(" + value);
			for (int i = 1; i < elements.length; i++) {
				value = elements[i];
				s.append("," + value);
			}
			s.append(")");
		}
		return s.toString();
	}

}
