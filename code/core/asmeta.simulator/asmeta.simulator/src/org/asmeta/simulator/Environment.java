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

import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;

/**
 * The environment returns the value of the monitored functions.
 */
public final class Environment {

	enum TimeMngt{
		// link the time variable to the time of the java machine
		use_java_time,
		// ask the user
		ask_user;
	}
	public static TemporalUnit timeunit = null;
	
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
		// state does not contain location value
		assert state.locationMap.get(location) == null;
		// AG 13/11/2020 
		// if it is time, read from the machine
		Value value = null;
		// se è un tempo
		if (isTime(location)) {
			// check if a time is already set in the state
			Object timeSet = find_mCurrTime(state);
			// convert if possible 
			// if it is not possible throw Exception
		
		
		// se la simualzione è java time prendi il time dallo stato (da bvraibile extra)
		// se domando all'utente?
		/*se la varibile è un time
		se c'è il un time settato nello stato, converti 
		altrimenti a seconda dei modi
		
		if (timeMngt == TimeMngt.use_java_time) {
			//TODO use switch expressions !
			TemporalUnit timeunit = null;
			switch(location.getName()){
			// problema se li leggo in istanti diversi ma nello stesso stato
			// ho tre valori distinti on multipli esatti
			case "mCurrTimeNanosecs": timeunit = ChronoUnit.NANOS; break;
			case "mCurrTimeMillisecs": timeunit = ChronoUnit.MILLIS; break;
			case "mCurrTimeSecs": timeunit = ChronoUnit.SECONDS; break;
			}
			if (timeunit!= null) {
				value = new IntegerValue(startFrom.until(Instant.now(), timeunit));
			}
		} 	
		if (value == null)
			value = monFuncReader.read(location, state);*/
			return null;
		}
		// not a time location
		return monFuncReader.read(location, state);
	}


	private Object find_mCurrTime(State state) {
		// scan the state and look for time
		// TODO Auto-generated method stub
		return null;
	}


	private boolean isTime(Location location) {
		return (location.getName().startsWith("mCurrTime"));
	}

}
