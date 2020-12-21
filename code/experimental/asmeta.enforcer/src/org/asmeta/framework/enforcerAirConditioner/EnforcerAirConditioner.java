package org.asmeta.framework.enforcerAirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;	
public class EnforcerAirConditioner extends Enforcer{

	public EnforcerAirConditioner() {
		super();
	}
	
	public EnforcerAirConditioner(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
	}

	//Unimplemented methods yet
	@Override
	public boolean sanitiseInput(String inputValues) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sanitiseOutput(String outputValues) {
		// TODO Auto-generated method stub
		return false;
	}

}
