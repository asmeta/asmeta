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
 * UpdateSet.java
 *
 * Created on 26 maggio 2006, 10.03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * An update set. 
 * 
 */
public class UpdateSet extends LocationSet {

	private static Logger logger = Logger.getLogger(UpdateSet.class);

	/**
	 * Adds a new update.
	 * 
	 * @param location a location
	 * @param content a new content
	 * @throws UpdateClashException if the update set becames inconsistent
	 */
	public void putUpdate(Location location, Value content) {
		if (locationMap.containsKey(location)){
			Value oldContent = locationMap.get(location);
			if (!oldContent.equals(content)) {
				logger.debug("<UpdateClash>");
				logger.debug("<UpdateSet>" + this + "</UpdateSet>");
				logger.debug("<Update>" + location + "=" + content + "</Update>");
				logger.debug("</UpdateClash>");
				//System.out.println("location = " + location + "\noldContent="+oldContent+"\ncontent="+content);
				throw new UpdateClashException(location, oldContent, content);
			}
		}
		locationMap.put(location, content);
	}

	/**
	 * Merges two update sets. If a location belongs to both the update sets,
	 * the final content is that in the second update set.
	 *  
	 * @param updateSet an update set
	 */
	public void merge(UpdateSet updateSet) {
		// manages the locations
		locationMap.putAll(updateSet.locationMap);
		// manages the abstract sets
		add(updateSet.abstractSets);
	}

	/**
	 * Merges two update sets. If a location belongs to both the update sets, but
	 * the contents are different, an exception is thrown.
	 * 
	 * @param anotherSet an update set
	 * @throws UpdateClashException if two updates clash
	 */
	public void union(UpdateSet anotherSet) {
		// manages the locations
		for (Map.Entry<Location, Value> entry : anotherSet.locationMap.entrySet()) {
			Location location = entry.getKey();
			Value value = entry.getValue();
			putUpdate(location, value);
		}
		// manages the abstract sets
		add(anotherSet.abstractSets);
	}

	/**
	 * Is the update set empty?
	 * 
	 * @return true if it is empty, otherwise false
	 */
	public boolean isEmpty() {
		return locationMap.isEmpty();
	}

	/*public UpdateSet clone() {
		UpdateSet newUpdateSet = new UpdateSet();
		newUpdateSet.abstractSets = new HashMap<WrappedDomain, Set<ReserveValue>>();
		for(Entry<WrappedDomain, Set<ReserveValue>> entry: abstractSets.entrySet()) {
			newUpdateSet.abstractSets.put(entry.getKey(), new HashSet<ReserveValue>());
			for(ReserveValue rv: entry.getValue()) {
				newUpdateSet.abstractSets.get(entry.getKey()).add(rv);
			}
		}
		newUpdateSet.locationMap = new HashMap<Location, Value>();
		for(Entry<Location, Value> entry: locationMap.entrySet()) {
			newUpdateSet.locationMap.put(entry.getKey(), entry.getValue());
		}
		return newUpdateSet;
	}*/

	public Value get(Location location) {
		return locationMap.get(location);
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof UpdateSet) {
			UpdateSet updateSet = (UpdateSet)o;
			if(locationMap.size() != updateSet.locationMap.size()) {
				//if the two update sets do not have the  same number of elements,
				//it means that they are not equal. Are we sure?
				return false;
			}
			for(Location location: locationMap.keySet()) {
				Value value = updateSet.get(location);
				if(value == null || !value.equals(locationMap.get(location))) {
					return false; 
				}	
			}
			return true;
		}
		else {
			return false;
		}
		// AG 2023 why abstracts sets are ignored????
	}

	@Override
	public int hashCode() {
	    //internal consistency: the value of hashCode() may only change if a property that is in equals() changes
	    //equals consistency: objects that are equal to each other must return the same hashCode
	    //collisions: unequal objects may have the same hashCode
		return locationMap.hashCode() + abstractSets.hashCode();
	}
	
	
	public Set<Location> getLocationsUpdated() {
		return locationMap.keySet();
	}

	public Set<Function> getFunctionsUpdated() {
		Set<Location> locations = getLocationsUpdated();
		Set<Function> functions = new HashSet<Function>();
		for(Location location: locations) {
			functions.add(location.getSignature());
		}
		return functions;
	}

	public boolean isTrivial(State previousState) {
		if(!isEmpty()) {
			for(Entry<Location, Value> locValue: locationMap.entrySet()) {
				Location loc = locValue.getKey();
				Value value = locValue.getValue();
				Value previousStateValue = previousState.read(loc);
				if(!(value instanceof UndefValue)) {
					if(previousStateValue instanceof UndefValue ||
						!previousStateValue.equals(value)) {
						return false;
					}
				}
				else {
					if(!(previousStateValue instanceof UndefValue)) {
						return false;
					}
				}				
			}
			return true;
		}
		else {
			return false;//an empty update set is NOT considered trivial
		}
	}
}
