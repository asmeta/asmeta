package model;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.Value;

@SuppressWarnings("rawtypes")
public class ViewReader extends MonFuncReader {

	Map<Location, Value> locationMemory;

	public ViewReader() {
		locationMemory = new HashMap<Location, Value>();
	}

	@Override
	public Value readValue(Location location, State state) {
		return (locationMemory.get(location));
	}

	/**
	 * Adds a location assignments to the current state
	 * 
	 * @param location the ASM location
	 * @param value    the location value
	 */
	public void addValue(Location location, Value value) {
		System.out.println("Adding " + location.getSignature().getName() + " with value " + value.toString());
		this.locationMemory.put(location, value);
	}
}
