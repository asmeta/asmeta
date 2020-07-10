package org.asmeta.codegenerator.arduino;

import java.util.ArrayList;
import java.util.List;

public class ArduinoPin {
	
	private ArduinoPinID id;
	List<ArduinoPinFeature> features = new ArrayList<ArduinoPinFeature>();
	
	public ArduinoPin(ArduinoPinID id, ArduinoPinFeature... features){
		this.id=id;
		for(ArduinoPinFeature feature : features)
			this.features.add(feature);
	}
	
	public boolean supportFeature(ArduinoPinFeature feature){
		return features.contains(feature);
	}
	public ArduinoPinID getId() {
		return id;
	}

	@Override
	public String toString(){
		String tostring = id.name() + ": ";
		for(ArduinoPinFeature feature : features)
			tostring += feature.name()+" ";
		
		return tostring; 
	}
	
	
}
