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

import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;

/**
 * The environment returns the value of the monitored functions.<br>
 * Non tiene conto del valore attuale: se viene richiesto due volte potrebbe 
 * dare due risultati diversi
 * 
 * always the same value.
 * 
 */
public final class Environment {

	// link the time variable to the time of the java machine
	public static boolean USE_TIME_JAVA = true;
	/**
	 * Parses an input string and returns a value.
	 */
	private MonFuncReader monFuncReader;
	
	public Environment(MonFuncReader monFuncReader) {
		this.monFuncReader = monFuncReader;
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
		if (USE_TIME_JAVA) {
			//TODO use switch expressions !
			switch(location.getName()){
			case "mCurrTimeNanosecs": value = new IntegerValue(System.nanoTime()); break;
			case "mCurrTimeMillisecs": value = new IntegerValue(System.currentTimeMillis()); break;
			case "mCurrTimeSecs": value = new IntegerValue(Instant.now().getEpochSecond()); break;
			}
		} 	
		if (value == null)
			value = monFuncReader.read(location, state);
		return value;
	}

}
