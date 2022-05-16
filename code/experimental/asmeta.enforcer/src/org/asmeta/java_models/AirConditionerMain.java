package org.asmeta.java_models;

import java.util.Scanner;

/**
 * @author Federico Rebucini
 */
public class AirConditionerMain {

	//V1 il bufferedreader sul processbuilder si blocca al system.in .nextInt
//	public static void main(String[] args) {
//		int T=0;
//		AirConditioner condizionatore = new AirConditioner(T);	//è giusto inizializzarlo a 0 di default? 
//		System.out.println("Condizionatore acceso: speed "+condizionatore.getAirIntensity1());														//nel modello "temperature" non è inizializzabile
//		Scanner s = new Scanner(System.in); 
//		while (T!=-999) {
//	        T = s.nextInt(); 
//	        condizionatore.setRoomTemperature(T);
//	        condizionatore.setAirIntensity();
//	        System.out.println("Ok: speed "+condizionatore.getAirIntensity1());
//		}
//        
//	}
	//V2 ho aggiunto > come riga per far saltare al bufferedreader il .nextInt
	public static void main(String[] args) {	
		int T=0;
		AirConditioner condizionatore = new AirConditioner(T);	//è giusto inizializzarlo a 0 di default? 
		System.out.println("Condizionatore acceso: speed "+condizionatore.getAirIntensity1());														//nel modello "temperature" non è inizializzabile
		Scanner s = new Scanner(System.in); 
		while (T!=-999) { //in base agli invarianti questa implementazione potrebbe essere sbagliata
			System.out.println(">");
	        T = s.nextInt(); 
	        condizionatore.setRoomTemperature(T);
	        condizionatore.setAirIntensity();
	        System.out.println("Ok: speed "+condizionatore.getAirIntensity1());
		}
        
	}

}
