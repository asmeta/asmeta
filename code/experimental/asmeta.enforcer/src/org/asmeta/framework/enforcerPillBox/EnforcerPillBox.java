package org.asmeta.framework.enforcerPillBox;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;	
public class EnforcerPillBox extends Enforcer{

	public EnforcerPillBox() {
		super();
	}
	
	public EnforcerPillBox(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
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
