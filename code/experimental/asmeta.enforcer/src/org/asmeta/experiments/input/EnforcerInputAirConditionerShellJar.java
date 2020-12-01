package org.asmeta.experiments.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class EnforcerInputAirConditionerShellJar {
	//ma effettivamente perchè sto usando 2 jar, non posso usare direttamente RUNTIMECONTAINER come imodelexecution???????
	public static void main(String[] args) {
		String stringJ = "",stringS = "";
		Scanner s = new Scanner(System.in); 
		try {
			//Processo JAVA
			ProcessBuilder pbJ = new ProcessBuilder("java", "-jar", ".\\jars\\ProvaAirC_v2.jar");
			Process pJ = pbJ.start();
			BufferedReader inJ = new BufferedReader(new InputStreamReader(pJ.getInputStream()));
			BufferedWriter outJ = new BufferedWriter(new OutputStreamWriter(pJ.getOutputStream()));
			//Processo SIMULATOR																				 
			//ProcessBuilder pbS = new ProcessBuilder("java", "-jar", "InterfacciaSimShell_v1.jar","init -n 1"); //forse è meglio se faccio una interfaccia jar ad hoc invece che usare shell
			ProcessBuilder pbS = new ProcessBuilder("java", "-jar", ".\\jars\\InterfacciaSimShell_v2.jar","init -n 1");	//ho isolato > su una riga con l'a capo
			Process pS = pbS.start();
			BufferedReader inS = new BufferedReader(new InputStreamReader(pS.getInputStream()));
			BufferedWriter outS = new BufferedWriter(new OutputStreamWriter(pS.getOutputStream()));
			
			int T=0;
			//INIT SIM
			while((stringS = inS.readLine()) != null && !stringS.equals(">")){ //stesso problema del carattere di attesa system.in (credo)
			    System.out.println(stringS);								   
			}
			outS.write("startexecution -modelpath \".\\examples\\airConditioner.asm\"\n");
		    outS.flush();
			while((stringS = inS.readLine()) != null && !stringS.equals(">")){
			    System.out.println(stringS);								   
			}
			//INIT JAVA
			while((stringJ = inJ.readLine()) != null && !stringJ.equals(">")){
			    System.out.println(stringJ);
			}
			T= s.nextInt();
			boolean errore=true;
			while (T>-275) {
				//EXEC SIM
				outS.write("runstep -id 1 -locationvalue {temperature="+T+"}\n");
			    outS.flush();
			    errore=true; // se trovo SAFE errore diventa false perchè non c'è errore
				while((stringS = inS.readLine()) != null && !stringS.equals(">")){
				    System.out.println(stringS);	
				    //meglio lavorare con oggetto runoutput o estendendo imodelexecution per avere le getOut ecc...
				    if (stringS.equals("The Esit of the running is: SAFE")) 
				    	errore=false;
				}
				if (!errore) {
					outJ.write(T+"\n");
			        outJ.flush();
			        while((stringJ = inJ.readLine()) != null && !stringJ.equals(">")){
					    System.out.println(stringJ);
					}
				}
				T= s.nextInt();
			}
			
			pJ.destroyForcibly();
			pS.destroyForcibly();
		}
		catch (IOException e) {
			System.out.println("-----ERRORE IOEXCEPTION-----");
		}finally {
			s.close();
		}
	}

}
