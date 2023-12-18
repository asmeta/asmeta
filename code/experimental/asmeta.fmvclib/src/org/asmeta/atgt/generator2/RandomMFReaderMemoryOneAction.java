package org.asmeta.atgt.generator2;

import java.util.List;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.Value;

/** this class generates a radom value for each step but only for ONE function at the tile in a predefined set,
 * while the others keep thery value
 */

public class RandomMFReaderMemoryOneAction extends RandomMFReaderMemory{

	//static Logger log = new Logger
	
	
	private boolean hasActionBeenSet = false;
	private List<String> actions;
	
	RandomMFReaderMemoryOneAction(List<String> actions){
		this.actions = actions;
	}
	
	@Override
	public Value readValue(Location location, State state) {
		String locName = location.getSignature().getName();
		// if it is an action, 
		// if it is has been take the previous values
		if (actions.contains(locName)) {
			// IT IS AN ACTION
			// return that of the last state
			if (hasActionBeenSet) {
				System.err.println("action has been already set. returning " + values.get(location));
				// read from values
				return values.get(location);
			} else {
				Value rndValue = super.readValue(location, state);
				System.err.println("action has not been set. returning " + rndValue);
				// action has not been set
				return rndValue;
			}
		} else {
			// take a random value (and store in the values)
			Value rndValue = super.readValue(location, state);
			System.err.println("not an action " + rndValue);
			return rndValue;
		}
	}
	
	@Override
	public void clear() {
		hasActionBeenSet = false;
		super.clear();
	}
	
	
	
}
