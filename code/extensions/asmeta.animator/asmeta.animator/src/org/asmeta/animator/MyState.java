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
	
	public String getControlledValuesToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int i = 0;
		if(this.getControlledValues() != null && !this.getControlledValues().isEmpty()) {
			for(Location loc: this.getControlledValues().keySet()) {
				sb.append(loc.toString() + "=");
				Value val = this.getControlledValues().get(loc);
				if(val instanceof org.asmeta.simulator.value.StringValue) {
					sb.append("\"" + val.toString() + "\"");
				} else {
					sb.append(val.toString());
				}
				i++;
				if(this.getControlledValues().size() == i) {
					sb.append("}");
				} else {
					sb.append(";");
				}
			}
		} else {
			sb.append("}");
		}
		
		return sb.toString();
	}
}
 