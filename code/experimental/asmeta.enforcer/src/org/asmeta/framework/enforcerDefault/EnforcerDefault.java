package org.asmeta.framework.enforcerDefault;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;	

public class EnforcerDefault extends Enforcer{

	public EnforcerDefault(ManagedSystem s, Knowledge k, FeedbackLoop loop) {
		super(s, k, loop);
	}

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