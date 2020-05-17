package org.asmeta.simulator.readers;

import java.util.Map;

import org.asmeta.simulator.Location;

public class MonitoredValues {
	private Map<Location, String> map;

	public MonitoredValues(Map<Location, String> map) {
		this.map = map;
	}

	public void setValues(Map<Location, String> map) {
		//System.out.println("MonitoredValues before set values " + this.map);
		this.map = map;
		//System.out.println("MonitoredValues set values " + map);
	}

	public Map<Location, String> getMap() {
		return map;
	}
}
