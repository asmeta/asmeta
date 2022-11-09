// Rubrica_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class Rubrica_Exe {
	
	static void printControlled(Rubrica esecuzione) {
		
		System.out.print("Contact"+ " = {");
		for(int i=0 ; i< esecuzione.Contact_lista.size(); i++)
		if(i!= esecuzione.Contact_lista.size()-1)
		System.out.print(esecuzione.Contact_lista.get(i) +", ");
		else
		System.out.print(esecuzione.Contact_lista.get(i));	
		System.out.println("}");
		System.out.println("rubricaState = " + esecuzione.rubricaState.oldValue.name());
		System.out.println("contacts = " + esecuzione.contacts.get());
		
		}
		
	static void askMonitored(Rubrica esecuzione) {
		
		System.out.print("Inserire un numero per indicare l'enumerativo per selectedService "+ 
		esecuzione.Service_lista.toString() +":  ");
		Scanner selectedServiceinput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String selectedServiceControllo = selectedServiceinput.nextLine();
			            if (selectedServiceControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(selectedServiceControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.selectedService.set(esecuzione.Service_lista.get(x-1));
			            break;
		         }				    		
						    		
		
		}
	
	
	public static void main(String[] args) {
		
		
		  System.out.println("INFO - file java creto e tradotto dal file originale Rubrica.asm");
		  System.out.println("Inizio esecuzione del file Rubrica.java\n\n");
		  
		  Rubrica esecuzione = new Rubrica();
		  
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


