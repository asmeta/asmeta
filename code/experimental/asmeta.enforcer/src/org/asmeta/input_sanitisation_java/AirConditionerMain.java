package org.asmeta.input_sanitisation_java;

//Input: room temperature (integer value)
import java.util.InputMismatchException;
import java.util.Scanner;

public class AirConditionerMain {
	
	public static void main(String[] args) {	
        int T =0;//default temperature value: 0
        AirConditioner cond = new AirConditioner(0);	
		
        Scanner s = new Scanner(System.in); 
        try {
			while (T!=-1) { 
				System.out.println("Conditioner ON: speed "+cond.getAirIntensity());			
				System.out.println("Enter temperature value >");
				T = s.nextInt(); //read room temperature
				cond.setRoomTemperature(T);
				cond.setAirIntensity();
				}
        }catch(InputMismatchException ex) {
			System.out.println("Error, input illformed.");
        }finally {
	        s.close();
	        System.out.println("Conditioner OFF");
       }
    }
}

