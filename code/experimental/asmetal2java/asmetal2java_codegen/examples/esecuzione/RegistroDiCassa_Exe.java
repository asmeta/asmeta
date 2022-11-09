// RegistroDiCassa_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class RegistroDiCassa_Exe {
	
	static void printControlled(RegistroDiCassa esecuzione) {
		
		System.out.print("Pizza"+ " = {");
		for(int i=0 ; i< esecuzione.Pizza_lista.size(); i++)
		if(i!= esecuzione.Pizza_lista.size()-1)
		System.out.print(esecuzione.Pizza_lista.get(i) +", ");
		else
		System.out.print(esecuzione.Pizza_lista.get(i));	
		System.out.println("}");
		System.out.println("statoCassa = " + esecuzione.statoCassa.oldValue.name());
		System.out.println("totale = " + esecuzione.totale.get());
		
		
		}
		
	static void askMonitored(RegistroDiCassa esecuzione) {
		
		System.out.print("Inserire un numero per indicare l'enumerativo per servizioSelezionato "+ 
		esecuzione.Servizio_lista.toString() +":  ");
		Scanner servizioSelezionatoinput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String servizioSelezionatoControllo = servizioSelezionatoinput.nextLine();
			            if (servizioSelezionatoControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(servizioSelezionatoControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.servizioSelezionato.set(esecuzione.Servizio_lista.get(x-1));
			            break;
		         }				    		
						    		
		System.out.print("Inserire un numero per indicare l'enumerativo per sceltaDiAggiuntaPizza "+ 
		esecuzione.AggiungiPizza_lista.toString() +":  ");
		Scanner sceltaDiAggiuntaPizzainput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String sceltaDiAggiuntaPizzaControllo = sceltaDiAggiuntaPizzainput.nextLine();
			            if (sceltaDiAggiuntaPizzaControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(sceltaDiAggiuntaPizzaControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.sceltaDiAggiuntaPizza.set(esecuzione.AggiungiPizza_lista.get(x-1));
			            break;
		         }				    		
		System.out.print("Inserire un numero per indicare l'enumerativo per sceltaDelTipoPizza "+ 
		esecuzione.SelezioneTipoDiPizza_lista.toString() +":  ");
		Scanner sceltaDelTipoPizzainput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String sceltaDelTipoPizzaControllo = sceltaDelTipoPizzainput.nextLine();
			            if (sceltaDelTipoPizzaControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(sceltaDelTipoPizzaControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.sceltaDelTipoPizza.set(esecuzione.SelezioneTipoDiPizza_lista.get(x-1));
			            break;
		         }				    		
		System.out.print("Inserire un valore Intero per insertQuantita (Integer):  ");
		Scanner insertQuantitainput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String insertQuantitaControllo = insertQuantitainput.nextLine();
			            if (insertQuantitaControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(insertQuantitaControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.insertQuantita.set(x);
			            break;
		         }
		
		
		
		System.out.print("Inserire un valore Intero per insertPrezzo (Integer):  ");
		Scanner insertPrezzoinput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String insertPrezzoControllo = insertPrezzoinput.nextLine();
			            if (insertPrezzoControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(insertPrezzoControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.insertPrezzo.set(x);
			            break;
		         }
		
		
		
		
		}
	
	
	public static void main(String[] args) {
		
		
		  System.out.println("INFO - file java creto e tradotto dal file originale RegistroDiCassa.asm");
		  System.out.println("Inizio esecuzione del file RegistroDiCassa.java\n\n");
		  
		  RegistroDiCassa esecuzione = new RegistroDiCassa();
		  
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


