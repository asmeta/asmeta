package org.asmeta.experiments.output;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.output_sanitisation_java.AirConditionerO;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

class adaptationResult{
	boolean resu;
	int valu;
	adaptationResult (){
		resu=true;
	}
	adaptationResult (int b){
		resu=false;
		b=valu;
	}
	/**get operation result: true->output was correct; false->output was filtered */
	boolean getResult() {
		return resu;
	}
	/**get operation correct value from model */
	int getValue() {
		//if (!resu)
			return valu;
		//return 0;
	}
}

class Enf{
	private SimulationContainer model;
	//private Map<String, String> locationValue;
	public Enf(String modelPath){
		model = SimulationContainer.getInstance();	//TODO container è singleton, non è meglio se faccio singleton anche l'enforcer?
		model.init(1);
		model.startExecution(modelPath);
	}
	
	public boolean sanitize(Map<String, String> locationValue) {
		return model.runStep(1, locationValue).equals(new RunOutput(Esit.SAFE,"safe"));
	}
	
	//TODO COME USARE L'IMPLEMENTAZIONE DELL'ALTRO MODELLO ADAPTATION
	//Il modello output normale va in unsafe e quindi non restituisce lo stato con la variabile OUT corretta,
	//il modello output adaptation non va mai in unsafe quindi non mi accorgo quando devo fare adaptation (a meno che faccio
	//il controllo sulla variabile invece che sull'unsafe ma non avrebbe senso), devo metterli entrambi running in parallelo?
	public adaptationResult adapt(Map<String, String> locationValue) {
		RunOutput modelOut = model.runStep(1, locationValue);
		if (modelOut.equals(new RunOutput(Esit.SAFE,"safe")))
			return new adaptationResult();
		System.out.println("ADAPT RETURN");
		Map<String, String> out = modelOut.getControlledvalues();
		System.out.println(out);
		return null;
	}
	
	public static <T> Map<String, String> convert(Map<String, T> input) {	//ho cercato di fare una conversione di input generica non so se è giusto
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
public class EnforcerOAirConditionerInteg {

	public static void main(String[] args) {
		Map<String, Integer> data = new HashMap<>();
		data.put("temperature", 0);
		data.put("airIntensity", 0);
		//int tem = 0, airI=0;
		int airI_old=0;
		AirConditionerO daikin = new AirConditionerO(data.get("temperature"));
		Enf enforcer = new Enf("examples/output_sanitisation/air_conditioner/airConditioner.asm");
		//airI=daikin.getAirIntensity();
		data.put("airIntensity", daikin.getAirIntensity());
		airI_old=daikin.getAirIntensity();
		System.out.println("Conditioner ON");
		System.out.println("Step 0:air intensity "+daikin.getAirIntensity()+": temperature "+data.get("temperature"));
		//tem = 40;
		//Cycle	
		data.put("temperature", 20); //correct temp
		daikin.setRoomTemperature(data.get("temperature"));
		daikin.setAirIntensity();
		data.put("airIntensity", daikin.getAirIntensity());	//output
		if (!enforcer.sanitize(Enf.convert(data))) {	//control
			System.out.println("INCORRECT VALUE: REVERTING TO OLD AIR INTENSITY");
			daikin.setAirIntensity(airI_old);
		}
		else {
			System.out.println("CONTROL SAFE");	
			airI_old = daikin.getAirIntensity();
		}
		System.out.println("Step 1:air intensity "+daikin.getAirIntensity()+": temperature "+data.get("temperature"));
		//End of cycle
		//Cycle
		//questo ciclo mostra un'errore di output filtrato
		data.put("temperature", 40); 	//airIntensity 2
		daikin.setRoomTemperature(data.get("temperature"));
		daikin.setAirIntensity();
		data.put("airIntensity", daikin.getAirIntensity());	//output incorrect
		if (!enforcer.sanitize(Enf.convert(data))) {	//control
			System.out.println("INCORRECT VALUE: REVERTING TO OLD AIR INTENSITY");
			daikin.setAirIntensity(airI_old);
		}
		else {
			System.out.println("CONTROL SAFE");	
			airI_old = daikin.getAirIntensity();
		}
		System.out.println("Step 2:air intensity "+daikin.getAirIntensity()+": temperature "+data.get("temperature"));
		//End of cycle
		//Cycle
		data.put("temperature", 10);
		daikin.setRoomTemperature(data.get("temperature"));
		daikin.setAirIntensity();
		data.put("airIntensity", daikin.getAirIntensity());	//output
		if (!enforcer.sanitize(Enf.convert(data))) {	//control
			System.out.println("INCORRECT VALUE: REVERTING TO OLD AIR INTENSITY");
			daikin.setAirIntensity(airI_old);
		}
		else {
			System.out.println("CONTROL SAFE");	
			airI_old = daikin.getAirIntensity();
		}
		System.out.println("Step 3:air intensity "+daikin.getAirIntensity()+": temperature "+data.get("temperature"));
		//End of cycle
		System.out.println("Conditioner OFF");
	}

}
