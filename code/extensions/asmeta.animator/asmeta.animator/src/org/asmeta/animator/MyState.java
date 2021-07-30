package org.asmeta.animator;

import java.util.Map;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.Value;

// controlled --> in current state
// monitored --> in previous state
public class MyState {

	@Deprecated // use get methods instead
	public Map<Location, Value> controlledValues;
	@Deprecated // use get methods instead
	public Map<Location, Value> monitoredValues;

	public MyState(Map<Location, Value> controlledValues, Map<Location, Value> monitoredValues) {
		this.controlledValues = controlledValues;
		this.monitoredValues = monitoredValues;
	}


	void setControlledValues(Map<Location, Value> controlledValues) {
		this.controlledValues = controlledValues;
	}

	void setMonitoredValues(Map<Location, Value> monitoredValues) {
		this.monitoredValues = monitoredValues;
	}

	public Map<Location, Value> getControlledValues() {
		return controlledValues;
	}

	public Map<Location, Value> getMonitoredValues() {
		return monitoredValues;
	}
	

}
 