package org.asmeta.experiments.in_out;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.io_sanitisation_java.AirConditionerIO;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

class Enf{
	private SimulationContainer model;
	private AirConditionerIO system;	//TODO ho deciso di farlo così per far vedere che l'out sanitisation avviene senza il coinvolgimento dell'utente
	//private Map<String, String> locationValue;
	public Enf(String modelPath, AirConditionerIO sys){
		model = SimulationContainer.getInstance();	//TODO container è singleton, non è meglio se faccio singleton anche l'enforcer?
		model.init(1);
		model.startExecution(modelPath);
		system = sys;
	}
	
	public void apply(int temperature) {
		int oldAirI=system.getAirIntensity();
		Map<String, Integer> data = new HashMap<>();
		data.put("temperature", temperature);
		data.put("airIntensity", oldAirI);	//sono obbligatorie tutte le monitored sempre
		if (sanitize(convert(data))) {
			system.setRoomTemperature(temperature);
			system.setAirIntensity();
			data.put("airIntensity", system.getAirIntensity());
			if (sanitize(convert(data))) {
				System.out.println("ALL OK: TEMP: "+temperature+" AIR INT: "+system.getAirIntensity());
			}else {
				system.setAirIntensity(oldAirI);
				System.out.println("OUTPUT FILTERED: REVERTING TO OLD AIR INTENSITY: "+system.getAirIntensity());
			}
		}else {
			System.out.println("INPUT FILTERED");
		}
		
	}
	
	private boolean sanitize(Map<String, String> locationValue) {
		return model.runStep(1, locationValue).equals(new RunOutput(Esit.SAFE,"safe"));
	}
	
	
	private static <T> Map<String, String> convert(Map<String, T> input) {	//ho cercato di fare una conversione di input generica non so se è giusto
		Map<String,String> locValue=new HashMap<>();
		for(Map.Entry<String, T> entry : input.entrySet()) {
		    String key = entry.getKey();
		    T value = entry.getValue();
		    if (value instanceof String)	//effettivamente se è un enum di asm non va bene
		    	locValue.put(key, "\""+value.toString()+"\"");
		    else
		    	locValue.put(key, value.toString());
		}
		return locValue;
	}
	
}

public class EnforcerIOAirConditionerInteg {

	public static void main(String[] args) {
		AirConditionerIO conditioner = new AirConditionerIO(0);
		Enf enforcer = new Enf("examples/input_output_sanitisation/air_conditioner/airConditioner.asm", conditioner);
		enforcer.apply(20);		//all ok: intensity set to 1
		enforcer.apply(-15);	//input error: system untouched
		enforcer.apply(30);		//output error: intensity reverted to 1
	}

}
