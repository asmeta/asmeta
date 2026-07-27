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
package tgtlib.util;

/**
 */
public class Pair<K,H>{ 
	final K first;
	final H second;
	/**
	 * Constructor for Pair.
	 * @param k K
	 * @param h H
	 */
	public Pair(K k, H h){first = k; second = h;}
	
	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString(){ return ("<"+ first + ", "+ second+">");}
	
	/**
	 * Method getFirst.
	 * @return K
	 */
	public final K getFirst(){ return first;}

	/**
	 * Method getSecond.
	 * @return H
	 */
	public final H getSecond(){ return second;}
	
	/**
	 * Method equals.
	 * @param o Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o instanceof Pair<?,?>) {
			Pair<?,?> new_name = (Pair<?,?>) o;
			if (first == new_name.first && second == new_name.second) return true;
			return (first.equals(new_name.first) && second.equals(new_name.second));
		}
		return false;
	}
	
	/**
	 * Method hashCode.
	 * @return int
	 */
	@Override
	public int hashCode(){
		// TODO use another algorithm to avoid hash clashes
		return first.hashCode() + second.hashCode();
	}
}
