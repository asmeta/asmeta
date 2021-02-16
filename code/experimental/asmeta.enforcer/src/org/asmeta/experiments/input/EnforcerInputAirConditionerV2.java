package org.asmeta.experiments.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainerSingleton;

public class EnforcerInputAirConditionerV2 {

	
	
	public static void main(String[] args) {
		String stringJ="";
		int T=0;
		RunOutput SafeOut = new RunOutput(Esit.SAFE, "ok");
		RunOutput simResult;
		Scanner s = new Scanner(System.in); 
		//model simulation start
		SimulationContainerSingleton sc = SimulationContainerSingleton.getInstance();
		sc.init(1);
		sc.startExecution("./examples./input_sanitisation/air_conditioner/airConditioner.asm");
		//java system start
		//ProcessBuilder pbJ = new ProcessBuilder("java", "-jar", "./jars/AirConditionerInputSystem.jar");
		ProcessBuilder pbJ = new ProcessBuilder("java", "-cp", "bin", "org.asmeta.input_sanitisation_java.AirConditionerIMain");
		try {
			Process pJ = pbJ.start();
			BufferedReader inJ = new BufferedReader(new InputStreamReader(pJ.getInputStream()));
			BufferedWriter outJ = new BufferedWriter(new OutputStreamWriter(pJ.getOutputStream()));
			
			while(stringJ != null && !stringJ.endsWith(">") && pJ.isAlive()){
				stringJ=inJ.readLine();
			    System.out.println(stringJ);
			}
			T = s.nextInt(); //first input
			while (T!=-1) {
				Map<String,String> locVal = new HashMap<>();
				locVal.put("temperature", ""+T);	//simulation input
				simResult = sc.runStep(1,locVal);	//simulation run
				if (simResult.equals(SafeOut)) {
					outJ.write(T+"\n");				//system input
			        outJ.flush();					//system run
					stringJ=inJ.readLine();
					//System.out.println(stringJ);
				}else
					System.out.println("Input filtered, system still at previous state.");
				while(stringJ != null && !stringJ.endsWith(">") && pJ.isAlive()){	//waiting for next input prompt
					stringJ=inJ.readLine();
				    //System.out.println(stringJ);
				}
				locVal.clear();
		        T= s.nextInt();
			}
		}catch (IOException e) {
			System.out.println("-----IOEXCEPTION-----");
		}finally {
			s.close();
		}
	}
	
}
