package org.asmeta.atgt.generator2;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;


/** keep the memory of the monitored variables
 * 
 */
public class RandomMFReaderMemory extends RandomMFReader{

	Map<Location,Value> values = new HashMap<Location, Value>();
		
	@Override
	public Value readValue(Location location, State state) {
		Value v = super.readValue(location, state);
		values.put(location, v);
		return v;
	}
	
	
	
}
