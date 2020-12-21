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

	@Override
	public boolean sanitiseInput(String inputValues) {
		int temp = Integer.parseInt(inputValues.trim());
		return temp <= 0; //to filter out negative values (without the use of the runtime model)
	}
	/*{
	return eval(inputValues).equals(new RunOutput(Esit.SAFE,"safe"));
    }*/

	@Override
	public boolean sanitiseOutput(String outputValues) {
		int speed = Integer.parseInt(((outputValues.split(":"))[1]).trim());
		return speed > 1; //to avoid over speed (without the use of the runtime model)
	}

}
