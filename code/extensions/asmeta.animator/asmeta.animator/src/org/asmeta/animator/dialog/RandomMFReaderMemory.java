package org.asmeta.animator.dialog;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;

public class RandomMFReaderMemory extends RandomMFReader{

	public Map<Location,Value> values = new HashMap<Location, Value>();
	
	@Override
	public Value readValue(Location location, State state) {
		Value v = super.readValue(location, state);
		//System.out.println("HERE TEXT: ");
		values.put(location, v);
		return v;
	}
	
	
}
