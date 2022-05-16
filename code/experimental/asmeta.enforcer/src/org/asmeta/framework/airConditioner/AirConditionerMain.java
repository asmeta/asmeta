package org.asmeta.framework.airConditioner;

//Input: room temperature (integer value)
import java.util.InputMismatchException;
import java.util.Scanner;

public class AirConditionerMain {
	    
	public static void main(String[] args) {	
        int T =0;//default temperature value: 0
        AirConditioner cond = new AirConditioner(0);	
        
        Scanner s = new Scanner(System.in); 
        System.out.println("Conditioner ON, speed: "+cond.getAirIntensity());	
        System.out.println("Enter temperature value >");
        try {
         String str = s.nextLine();
         while (! str.equals("###")) { 	
				T = Integer.parseInt(str); //read room temperature
				cond.setRoomTemperature(T);
				cond.setAirIntensity();
				System.out.println("Conditioner speed: "+cond.getAirIntensity());	
				System.out.println("Enter temperature value >");
				str = s.nextLine();
			}
         }
			catch(InputMismatchException ex) {
				System.err.println("Error, input illformed.");
	        } 
		finally {
		        s.close();
		        System.out.println("Conditioner OFF");
	       }
        
   }
}
