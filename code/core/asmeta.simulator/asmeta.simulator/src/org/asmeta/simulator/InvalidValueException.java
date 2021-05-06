package org.asmeta.simulator;

import org.asmeta.simulator.value.Value;

public class InvalidValueException extends RuntimeException {

	public InvalidValueException(Value content, Location location) {
		super("value out of domain: cannot assign " + content + " to " + location);		
	}
}
