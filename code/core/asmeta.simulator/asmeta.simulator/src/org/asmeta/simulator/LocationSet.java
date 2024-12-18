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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.parser.Defs;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;

/** 
 * Keeps the association between the locations/dynamic sets and their contents.
 *
 */
public class LocationSet implements Iterable<Map.Entry<Location, Value>> {

	/**
	 * Contents of locations.
	 * 
	 */
	private Map<Location, Value> locationMap;
		
	/** 
	 * Contents of dynamic sets.
	 * 
	 */
	protected Map<WrappedDomain, Set<ReserveValue>> abstractSets;  

	/**
	 * Constructor.
	 * 
	 */
	protected LocationSet() {
		locationMap = new HashMap<Location, Value>();
		abstractSets = new HashMap<WrappedDomain, Set<ReserveValue>>();
	}
	
	/** 
	 * Copy constructor. In depth constructor !
	 *  
	 */
	protected LocationSet(LocationSet state) {
		locationMap = new HashMap<Location, Value>(state.locationMap);
		abstractSets = new HashMap<WrappedDomain, Set<ReserveValue>>();
		for(Entry<WrappedDomain,Set<ReserveValue>> s: state.abstractSets.entrySet()){
			abstractSets.put(s.getKey(), new  HashSet<ReserveValue>(s.getValue()));
		}
	}
	
	/**
	 * Adds a reserve element to a dynamic domain.
	 * 
	 * @param domain a dynamic domain
	 * @param content a new element
	 */
	public void add(Domain domain, ReserveValue content) {
		WrappedDomain wdomain = new WrappedDomain(domain);
		Set<ReserveValue> set = abstractSets.get(wdomain);
		if (set == null){
			set = new HashSet<ReserveValue>();
			abstractSets.put(wdomain, set);
		} 
		set.add(content);		
	}

	/**
	 * Adds all the elements of the given set to the given domain.
	 *  
	 * @param domain a domain
	 * @param content a set
	 */
	protected void add(WrappedDomain domain, Set<ReserveValue> content) {
		Set<ReserveValue> current = abstractSets.get(domain);
		if (current == null) {
			abstractSets.put(domain, content);
		} else {
			current.addAll(content);
		}
	}
	
	/**
	 * Adds all the elements of the given sets to the given domains. 
	 * 
	 * @param map a map from domain to set
	 */
	protected void add(Map<WrappedDomain, Set<ReserveValue>> map) {
		for(Entry<WrappedDomain, Set<ReserveValue>> entry: map.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * A WrappedDomain redefines the methods equals(Object o) and hashCode()
	 * of the class Domain.
	 *
	 */
	protected static class WrappedDomain {
		
		protected Domain domain;
		
		WrappedDomain(Domain domain) {
			this.domain = domain;
		}
				
		@Override
		public boolean equals(Object o) {
			if (o instanceof WrappedDomain) {
				WrappedDomain d = (WrappedDomain) o;
				return Defs.equals(domain, d.domain);
			}
			throw new IllegalArgumentException();
		}
		
		@Override
		public int hashCode() {
			return domain.getName().hashCode();
		}
		
	}

	@Override
	public String toString() {
		return formatString('\n');
	}
	
	/**
	 * Returns a string representation of a collection of objects.
	 * 
	 * @param c a collection of objects
	 * @param sep a character separator
	 * @return a string
	 */
	String formatString(Collection<?> c, char sep) {
		StringBuilder s = new StringBuilder();
		Iterator<?> i = c.iterator();
		if (i.hasNext()) {
			s.append(i.next().toString());
			while (i.hasNext()) {
				s.append(sep);
				s.append(i.next().toString());
			}
		}
		return s.toString();
	}

	//Inizio modifiche PA: 10 giugno 2010
	/**
	 * Returns a string representation of the content of a location set.
	 * 
	 * @param sep a character separator
	 * @return a string
	 */
	String formatString(char sep) {
		return formatString(locationMap, sep, true);
	}
	/*String formatString(char sep) {
		StringBuilder s = new StringBuilder();
		// sort dynamic domain by name
		WrappedDomain[] keys = new WrappedDomain[0];
		keys = abstractSets.keySet().toArray(keys);
		Arrays.sort(keys, new Comparator<WrappedDomain>() {
			
			@Override
			public int compare(WrappedDomain o1, WrappedDomain o2) {
				String name1 = o1.domain.getName();
				String name2 = o2.domain.getName();
				return name1.compareTo(name2);
			}
			
		});
		
		if (keys.length > 0) {
			int i = 0;
			ReserveValue[] set;
			Comparator<ReserveValue> comp = new Comparator<ReserveValue>() {
				
				@Override
				public int compare(ReserveValue o1, ReserveValue o2) {
					String v1 = o1.getValue();
					String v2 = o2.getValue();
					return v1.compareTo(v2);
				}
				
			};
			// get first domain content
			set = new ReserveValue[0];
			set = abstractSets.get(keys[i]).toArray(set);
			Arrays.sort(set, comp);
			s.append(keys[i].domain.getName());
			s.append("={");
			s.append(formatString(Arrays.asList(set), ','));
			s.append("}");
			// get others domain contents
			for (i++; i < keys.length; i++) {
				s.append(sep);
				
				set = new ReserveValue[0];
				set = abstractSets.get(keys[i]).toArray(set);
				Arrays.sort(set, comp);
				s.append(keys[i].domain.getName());
				s.append("={");
				s.append(formatString(Arrays.asList(set), ','));
				s.append("}");
			}						
		}
		// now formats the locations
		Location[] keys2 = new Location[0];
		keys2 = locationMap.keySet().toArray(keys2);
		Arrays.sort(keys2, new Comparator<Location>() {
			
			@Override
			public int compare(Location l1, Location l2) {
				Function sig1 = l1.getSignature();
				Function sig2 = l2.getSignature();
				String name1 = sig1.getName();
				String name2 = sig2.getName();
				if (name1.compareTo(name2) == 0) {
					if (sig1.getArity() == sig2.getArity()) {
						String str1 = l1.toString();
						String str2 = l2.toString();
						return str1.compareTo(str2);
					}
					return sig1.getArity() - sig2.getArity();
				}
				return name1.compareTo(name2);
			}
			
		});		
		if (keys2.length > 0) {
			int i = 0;
			
			if (keys.length > 0) {
				s.append(sep);
			}			
			s.append(keys2[i].toString());
			s.append("=");
			s.append(locationMap.get(keys2[i]).toString());			
			for (i++; i< keys2.length; i++) {
				s.append(sep);
				
				s.append(keys2[i].toString());
				s.append("=");
				Value tmp = locationMap.get(keys2[i]);
				s.append((tmp != null) ? tmp.toString() : "null");
			}
		}
		return s.toString();
	}*/

	String formatString(Map<Location, Value> locMap, char sep, boolean printDomains) {
		StringBuilder s = new StringBuilder();
		// sort dynamic domain by name
		WrappedDomain[] keys = new WrappedDomain[0];
		if(printDomains) {
			keys = abstractSets.keySet().toArray(keys);
			Arrays.sort(keys, new Comparator<WrappedDomain>() {
				
				@Override
				public int compare(WrappedDomain o1, WrappedDomain o2) {
					String name1 = o1.domain.getName();
					String name2 = o2.domain.getName();
					return name1.compareTo(name2);
				}
				
			});
			
			if (keys.length > 0) {
				int i = 0;
				ReserveValue[] set;
				Comparator<ReserveValue> comp = new Comparator<ReserveValue>() {
					
					@Override
					public int compare(ReserveValue o1, ReserveValue o2) {
						String v1 = o1.getValue();
						String v2 = o2.getValue();
						return v1.compareTo(v2);
					}
					
				};
				// get first domain content
				set = new ReserveValue[0];
				set = abstractSets.get(keys[i]).toArray(set);
				Arrays.sort(set, comp);
				s.append(keys[i].domain.getName());
				s.append("={");
				s.append(formatString(Arrays.asList(set), ','));
				s.append("}");
				// get others domain contents
				for (i++; i < keys.length; i++) {
					s.append(sep);
					
					set = new ReserveValue[0];
					set = abstractSets.get(keys[i]).toArray(set);
					Arrays.sort(set, comp);
					s.append(keys[i].domain.getName());
					s.append("={");
					s.append(formatString(Arrays.asList(set), ','));
					s.append("}");
				}						
			}
		}
		
		// now formats the locations
		Location[] keys2 = new Location[0];
		keys2 = locMap.keySet().toArray(keys2);
		Arrays.sort(keys2, new Comparator<Location>() {
			
			@Override
			public int compare(Location l1, Location l2) {
				Function sig1 = l1.getSignature();
				Function sig2 = l2.getSignature();
				String name1 = sig1.getName();
				String name2 = sig2.getName();
				if (name1.compareTo(name2) == 0) {
					//FINDBUGS
					//This method compares two reference values using the == or
					//!= operator, where the correct way to compare instances of
					//this type is generally with the equals() method. It is
					//possible to create distinct instances that are equal but
					//do not compare as == since they are different objects.
					//Examples of classes which should generally not be compared
					//by reference are java.lang.Integer, java.lang.Float, etc.
					//if (sig1.getArity() == sig2.getArity()) {
					
					//possible solution
					if(sig1.getArity().intValue() == sig2.getArity().intValue()) {
						String str1 = l1.toString();
						String str2 = l2.toString();
						return str1.compareTo(str2);
					}
					return sig1.getArity() - sig2.getArity();
				}
				return name1.compareTo(name2);
			}
			
		});		
		if (keys2.length > 0) {
			int i = 0;
			
			if (printDomains && keys.length > 0) {
				s.append(sep);
			}			
			s.append(keys2[i].toString());
			s.append("=");
			s.append(locMap.get(keys2[i]).toString());			
			for (i++; i< keys2.length; i++) {
				s.append(sep);
				
				s.append(keys2[i].toString());
				s.append("=");
				Value tmp = locMap.get(keys2[i]);
				s.append((tmp != null) ? tmp.toString() : "null");
			}
		}
		return s.toString();
	}
	//Fine modifiche PA: 10 giugno 2010
	
	@Override
	public Iterator<Entry<Location, Value>> iterator() {
		return locationMap.entrySet().iterator();
	}	
	
	// return the current location map
	public Map<Location,Value> getLocationMap() {
		return Collections.unmodifiableMap(locationMap);
	}
	
	// return the current value of the location set
	public Value getCurrentValue(Location l) {
		return locationMap.get(l);
	}

	// apply the updates to the current location map of the state
	public void applyLocationUpdates(Map<? extends Location, ? extends Value> updates) {
		assert ! updates.containsValue(null);
		locationMap.putAll(updates);
	}
	// apply a single location update
	public void applyLocationUpdate(Location loc, Value val) {
		locationMap.put(loc,val);		
	}

	/**
	 * Is the update set empty?
	 * 
	 * @return true if it is empty, otherwise false
	 */
	public boolean isEmpty() {
		return locationMap.isEmpty();
	}

	@Override
	public int hashCode() {
	    //internal consistency: the value of hashCode() may only change if a property that is in equals() changes
	    //equals consistency: objects that are equal to each other must return the same hashCode
	    //collisions: unequal objects may have the same hashCode
		return locationMap.hashCode() + abstractSets.hashCode();
	}

	
	
}
