package org.asmeta.atgt.generator;

import java.util.HashMap;
import java.util.Random;

import org.asmeta.atgt.generator2.RandomMFReaderMemory;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.domains.BooleanDomain;

/** random reader o fmoniotred variables with some probabilities
 * 
 * @author garganti
 *
 */
public class RandomProbabilityMFReader extends RandomMFReaderMemory{
	
	static Random rnd = new Random();
	
	
	private Location  currentLocation;
	
	private HashMap<String, Double> trueprobablities  = new HashMap<>();
	
	@Override
	public Value readValue(Location location, State state) {
		currentLocation = location;
		return super.readValue(location, state);
	}

	
	@Override
	public BooleanValue visit(BooleanDomain domain) {
		Double cheat = trueprobablities.get(currentLocation.getSignature().getName());
		if (cheat == null) return super.visit(domain);
		// esempio false nel 90% dei casi
		if (rnd.nextDouble() <= cheat) return BooleanValue.TRUE;
		else return BooleanValue.FALSE;
	}

	/**
	 * 
	 * @param var
	 * @param d the probablity that var must take true value
	 */
	public void registerBoolTrue(String var, double d) {
		trueprobablities.put(var, d);
	}
	
}