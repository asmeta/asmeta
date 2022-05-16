package org.asmeta.framework.airConditioner;

//Input: room temperature (integer value)
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Federico Rebucini
 */
public class AirConditionerMainLoop {
	    
	public static void main(String[] args) {	
        int T=0; //default temperature value: 0
        //int Speed=0; //default airIntensity value: 0
        
        AirConditioner cond = new AirConditioner(T);	
        
        Scanner s = new Scanner(System.in);
        try {
         String str = s.nextLine();
         while (! str.equals("###")) { 	
        	 if (str.trim().toLowerCase().startsWith("temp:")) {
        		 T = Integer.parseInt(str.substring(str.indexOf(":")+1));
        		 cond.setRoomTemperature(T);
        		 cond.setAirIntensity();
        		 System.out.println("OK");
        	 }else if (str.trim().toLowerCase().startsWith("speed:")) {
        		 int speed=0;
        		 speed = Integer.parseInt(str.substring(str.indexOf(":")+1));
        		 cond.setAirIntensity(speed);
        		 System.out.println("OK");
        	 }else if (str.trim().toLowerCase().equals("get")) {
        		 System.out.println("Speed:"+cond.getAirIntensity()+",Temp:"+cond.getRoomTemperature());
        	 }
				//T = Integer.parseInt(str); //read room temperature
				//cond.setRoomTemperature(T);
				//cond.setAirIntensity();
				//System.out.println("Conditioner speed: "+cond.getAirIntensity());	
				//System.out.println("Enter temperature value >");
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
