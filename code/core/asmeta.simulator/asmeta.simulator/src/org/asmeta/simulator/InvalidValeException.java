package org.asmeta.simulator;

import org.asmeta.simulator.value.Value;

public class InvalidValeException extends RuntimeException {

	public InvalidValeException(Value content, Location location) {
		super("value out of domain: cannot assign " + content + " to " + location);		
	}
}
