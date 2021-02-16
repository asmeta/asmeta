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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;

/**
 * The environment returns the value of the monitored functions.
 */
public final class Environment {

	public enum TimeMngt{
		// link the time variable to the time of the java machine
		use_java_time,
		// ask the user
		ask_user;
	}
	// it can be null: automatic
	public static TimeUnit currentTimeUnit = null;
	
	public static TimeMngt timeMngt;
	
	private static boolean START_TIME_FROM_0 = true;
	/**
	 * Parses an input string and returns a value.
	 */
	private MonFuncReader monFuncReader;

	private Instant startFrom;
	
	public Environment(MonFuncReader monFuncReader) {
		this.monFuncReader = monFuncReader;
		if (START_TIME_FROM_0) 
			startFrom = Instant.now();
		else
			startFrom = Instant.MIN;
	}

	
	/**
	 * Returns the value of a monitored function.
	 * 
	 * @param location
	 *            a location
	 * @param state
	 *            the current state
	 * @return the location value
	 */
	public Value<?> read(Location location, State state) {
		// state does not contain location value otherwise must not be asked 
		assert state.locationMap.get(location) == null;
		// AG 13/11/2020 
		// if it is time, read in a special way
		if (isTime(location)) {
			// extract 
			TimeUnit locationTimeUnit = getTimeUnit(location);
			// set the time unit if not already set
			if (currentTimeUnit==null) currentTimeUnit = locationTimeUnit;
			// check if a time is already set in the state
			return find_mCurrTime(state,location, locationTimeUnit);
			// convert if possible 
			// if it is not possible throw Exception
		}			
		// not a time location
		return monFuncReader.read(location, state);
	}

	// find the current time of the location in tu timeunit
	// considering that the teh current time could be set in other time units 
	private Value find_mCurrTime(State state, Location location, TimeUnit tu) {
		assert tu == getTimeUnit(location);
		// scan the state and look for time of the time unit for the simulation
		// case base, it is asking a time in the time of the simulation
		if (currentTimeUnit == tu) return readTime(state, location, tu); 
		// check if its convertible 
		if (currentTimeUnit.convert(1, tu) == 0) throw new RuntimeException("cannot convert " +currentTimeUnit + " to " + tu);
		// check if is already memorized in the currentTimeUnit of the simulation		
		Value<Long> currentValue = null;
		for (Entry<Location, Value> v : state.getMonLocs().entrySet()) {
			if (v.getKey().getName().equals("mCurrTimeMillisecs") && currentTimeUnit == TimeUnit.MILLISECONDS) {
				currentValue = v.getValue();
				break;
			}			
			if (v.getKey().getName().equals("mCurrTimeSecs") && currentTimeUnit == TimeUnit.SECONDS) {
				currentValue = v.getValue();
				break;
			}			
		}
		// if not found I get the time in the currentTimeUnit
		if (currentValue == null) currentValue = readTime(state, location, currentTimeUnit);
		assert currentValue != null;
		// convert from currentTimeUnit to tu 
		long converteval = tu.convert(currentValue.getValue(),currentTimeUnit); 
		return new IntegerValue(converteval);		
	}

	// read time - it must be 
	private Value readTime(State state, Location location, TimeUnit tu) {
		Value value = null;
		// if use java time
		if (timeMngt == TimeMngt.use_java_time) {
			value = new IntegerValue(startFrom.until(Instant.now(), currentTimeUnit.toChronoUnit()));
		} else { 	
			value = monFuncReader.read(location, state);
		}
		return value;
	}


	// return the temporal unit
	private TimeUnit getTimeUnit(Location location) {
		switch(location.getName()){
		case "mCurrTimeNanosecs": return TimeUnit.NANOSECONDS;
		case "mCurrTimeMillisecs": return TimeUnit.MILLISECONDS;
		case "mCurrTimeSecs": return TimeUnit.SECONDS;
		}
		assert (false);
		return null;
	}
	// check is the location refers to time quantities (special monitored functions)
	private boolean isTime(Location location) {
		return location.getName().startsWith("mCurrTime");
	}
	
	
}
