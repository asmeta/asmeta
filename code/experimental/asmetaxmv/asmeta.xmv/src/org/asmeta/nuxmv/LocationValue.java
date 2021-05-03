package org.asmeta.nuxmv;

import java.util.List;

class LocationValue {
	String location;
	List<String> conds;
	String value;
	
	public LocationValue(String location, List<String> conds, String value) {
		this.location = location;
		this.conds = conds;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Location = " + location + ", conds = " + conds + ", value = " +
				value;
	}
}
