/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions;

import java.util.Collection;

import tgtlib.definitions.expression.Expression;

/** an expression + its name
 * 
 * @author garganti
 *

 * @version $Revision: 1.0 $
 */
public class NamedTerm implements Comparable<NamedTerm> {

		/** condition or terms which this named term refers to*/
		protected Expression term;

		/** name of the the test condition. It can not be unique*/
		private String name;

		/**
		 * Instantiates a new named expression.
		 * 
		 * @param _name
		 *            the _name
		 * @param condition
		 *            the _condition it may be temporary null 
		 */
		public NamedTerm(String _name, Expression condition) {
			this.name = _name;
			this.term = condition;
		}

		/**
		 * Gets the condition.
		 * 
		
		 * @return the condition */
		public Expression getCondition() {
			return this.term;
		}

		/**
		 * Gets the name.
		 * 
		
		 * @return the name */
		public String getName() {
			return this.name;
		}

		/**
		 * Sets the name.
		 * 
		 * @param s
		 *            the new name
		 */
		public void setName(String s) {
			this.name = s;
		}

		
		/**
		 * return the name + ":" the expression as string
		 */
		@Override
		public String toString() {
			return this.name+ ": " + ( term == null ? "NULL" : term.toString());
		}

		/** return the list of names of Named Terms
		 * 
		 * @param nts
		 * @return
		 */
		public static String getNames(Collection<? extends NamedTerm> nts){
			if (nts.size() == 0) return "[]";
			StringBuffer result = null;
			for(NamedTerm nt: nts){
				if (result == null){ 
					result = new StringBuffer("[");
				} else{
					result.append(", ");
				}
				result.append(nt.getName());
			}
			result.append("]");
			return result.toString();
		}
		
		/**
		 * Method compareTo.to order Test conditions (by name) 
		 * @param o NamedTerm
		 * @return int
		 */
		@Override
		public int compareTo(NamedTerm o) {
			return this.name.compareTo(o.name);
		}
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o != null && o instanceof NamedTerm){
				NamedTerm ot = (NamedTerm) o;
				return name.equals(ot.name) && term.equals(ot.term);
			}
			return false;
		}
		/*@Override
		public int hashCode() {
			return term.hashCode() + name.hashCode();
		}*/
		
	}

