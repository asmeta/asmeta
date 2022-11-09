// cassaforte_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class cassaforte_Exe {
	
	static void printControlled(cassaforte esecuzione) {
		
		
		}
		
	static void askMonitored(cassaforte esecuzione) {
		
		System.out.print("Inserire un valore Intero per switchSensore (Integer):  ");
		Scanner switchSensoreinput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String switchSensoreControllo = switchSensoreinput.nextLine();
			            if (switchSensoreControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(switchSensoreControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.switchSensore_supporto.value = x;
			            esecuzione.switchSensore.set(esecuzione.switchSensore_supporto);
			            break;
		         }				    		
		
		
		
		
		}
	
	
	public static void main(String[] args) {
		
		
		  System.out.println("INFO - file java creto e tradotto dal file originale cassaforte.asm");
		  System.out.println("Inizio esecuzione del file cassaforte.java\n\n");
		  
		  cassaforte esecuzione = new cassaforte();
		  
		  String continuare = "no";
		  int stato =0;
		  stato++;
		  
		  System.out.println("INITIAL STATE: ");
		     
		  do {
		  
		  	   System.out.println("<State "+ stato +" (controlled)>");
		  	   
		  	   //Aggiornamento valori dell'ASM e inserimento dati monitorati
		  	   
		  	   printControlled(esecuzione);
		  	   askMonitored(esecuzione);
		  	   esecuzione.UpdateASM();
		  	   
		  	   System.out.println("</State "+ stato +" (controlled)>");
		  	   
		  	   System.out.println("\n<Stato attuale>");
		  	   printControlled(esecuzione);
		  	   
		  	   
		  	    System.out.print("Vuoi continuare? (yes/no)  "); 
		  	    Scanner input = new Scanner(System.in);
		  	   continuare = input.next();
		  	              
		  	   stato++;
		  	 }
		  	 
		  while(continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES") );
		  	     
		  	     
		  System.out.println("FINAL STATE:");
		  
		  //Valori finale delle variabili
		  printControlled(esecuzione);
		  
		  System.out.println("esecuzione terminata");
		  	     
		}
	
}


