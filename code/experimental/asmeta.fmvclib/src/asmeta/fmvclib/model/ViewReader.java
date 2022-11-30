package asmeta.fmvclib.model;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

@SuppressWarnings("rawtypes")
public class ViewReader extends MonFuncReader {

	Map<String, Value> locationMemory;

	public ViewReader() {
		locationMemory = new HashMap<String, Value>();
	}

	@Override
	public Value readValue(Location location, State state) {
		if (locationMemory.get(location.getSignature().getName()) != null)
			return (locationMemory.get(location.getSignature().getName()));
		else
			return UndefValue.UNDEF;
	}

	/**
	 * Adds a location assignments to the current state
	 * 
	 * @param location the ASM location
	 * @param value    the location value
	 */
	public void addValue(String locationName, Value value) {
		this.locationMemory.put(locationName, value);
	}
}
