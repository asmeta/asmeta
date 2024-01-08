package org.asmeta.atgt.generator2;

import java.util.List;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

/** this class generates a raNdom value for each step but only for ONE function ACTION 
 * at the tile in a predefined set,
 * while the others keep their values, the other actions become UNDEF
 */

public class RandomMFReaderMemoryOneAction extends RandomMFReaderMemory{

	//static Logger log = new Logger	
	
	private String chosenAction;
	private List<String> actions;
	
	
	RandomMFReaderMemoryOneAction(List<String> actions){
		this.actions = actions;
		chosenAction = actions.get(random.nextInt(actions.size()));
	}
	
	@Override
	public Value readValue(Location location, State state) {
		String locName = location.getSignature().getName();
		// if it is an action, 
		// if it is has been take the previous values
		if (actions.contains(locName)) {
			// IT IS AN ACTION
			// return that of the last state
			if (chosenAction.equals(locName)) {
				if (values.get(location) == null) {
					Value val = super.readValue(location, state);
					values.put(location, val);
				}
				System.err.println("this action "+ locName + " is chosen been returning " + values.get(location));
				// read from values
				return values.get(location);
			} else {
				System.err.println("action "+ locName + " is not the chosen one ("+chosenAction+"). returning undef");
				// action has not been set
				return UndefValue.UNDEF;
			}
		} else {
			// take a random value (and store in the values)
			Value rndValue = super.readValue(location, state);
			System.err.println("not an action " + locName);
			return rndValue;
		}
	}
	
	@Override
	public void clear() {
		chosenAction = actions.get(random.nextInt(actions.size()));
		super.clear();
	}
	
	
	
}
