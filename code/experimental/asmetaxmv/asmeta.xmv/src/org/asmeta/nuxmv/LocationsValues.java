package org.asmeta.nuxmv;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.nusmv.LocationValue;
import org.asmeta.nusmv.LocationsValues;

class LocationsValues {
	private List<LocationValue> locations;
	
	public LocationsValues(){
		locations = new ArrayList<LocationValue>();
	}
	
	public LocationsValues(LocationsValues lv){
		locations = new ArrayList<LocationValue>(lv.asList());
	}

	public void add(String location, List<String> conds, String value){
		locations.add(new LocationValue(location, conds, value));
	}

	public void removeLast(){
		locations.remove(locations.size() - 1);
	}

	public List<LocationValue> asList(){
		return locations;
	}

	@Override
	public String toString(){
		return locations.toString();
	}
}