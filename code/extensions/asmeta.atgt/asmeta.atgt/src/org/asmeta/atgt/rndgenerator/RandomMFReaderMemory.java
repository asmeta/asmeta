package org.asmeta.atgt.rndgenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;


/**
 * keep the memory of the monitored variables
 */
public class RandomMFReaderMemory extends RandomMFReader{

	protected Map<Location,Value> values = new HashMap<>();

	@Override
	public Value readValue(Location location, State state) {
		Value v = super.readValue(location, state);
		assert v != null;
		values.put(location, v);
		return v;
	}

	// returns the values stored so far
	public Map<? extends Location, ? extends Value> getValues() {
		return Collections.unmodifiableMap(values);
	}
	// clear the values
	public void clear() {
		values.clear();
	}
}
