package asmetal2java_junit;

import org.asmeta.avallaxt.avalla.Scenario;

// converts an avalla to String
public class AvallaToString extends org.asmeta.avallaxt.avalla.util.AvallaSwitch<String> {
	
	
	@Override
	public String caseScenario(Scenario object) {
		return "caso di test junit";
	}
	
	
}