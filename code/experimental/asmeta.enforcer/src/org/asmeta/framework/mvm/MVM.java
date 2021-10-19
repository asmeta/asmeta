/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.mvm;

import java.util.Map;

import org.asmeta.framework.managedSystem.Effector;
import org.asmeta.framework.managedSystem.ManagedSystem;
import org.asmeta.framework.managedSystem.Probe;


//Java class for the causal connection with the Arduino prototype of the MVM
public class MVM extends ManagedSystem implements Probe, Effector{

	public Map<String, String> getOutputForProbing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Probe getProbe() {
		return this;
	}

	@Override
	public Effector getEffector() {
		return this;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}

}
