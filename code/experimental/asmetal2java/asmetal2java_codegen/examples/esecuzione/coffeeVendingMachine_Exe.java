// coffeeVendingMachine_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class coffeeVendingMachine_Exe {
	
	static void printControlled(coffeeVendingMachine esecuzione) {
		
		System.out.println("coins = " + esecuzione.coins.get().value);
		for(int i=0; i < esecuzione.Product_lista.size(); i++)
				{
					System.out.println(" available =>  (" + esecuzione.Product_lista.get(i) +") 
					= " + esecuzione.available.oldValues.get(esecuzione.Product_lista.get(i)).value );
				}
		
		}
		
	static void askMonitored(coffeeVendingMachine esecuzione) {
		
		System.out.print("Inserire un numero per indicare l'enumerativo per insertedCoin "+ 
		esecuzione.CoinType_lista.toString() +":  ");
		Scanner insertedCoininput = new Scanner(System.in);
		
		for(;;) {
			         int x;
			            String insertedCoinControllo = insertedCoininput.nextLine();
			            if (insertedCoinControllo.isEmpty()) break;
			            try{
			                x = Integer.parseInt(insertedCoinControllo);
			            }catch (Exception e) {
			                System.out.println("hai inserito un valore sbagliato, riprova");
			                continue;
			            }
			            
			            esecuzione.insertedCoin.set(esecuzione.CoinType_lista.get(x-1));
			            break;
		         }				    		
		
		}
	
	
	public static void main(String[] args) {
		
		
		  System.out.println("INFO - file java creto e tradotto dal file originale coffeeVendingMachine.asm");
		  System.out.println("Inizio esecuzione del file coffeeVendingMachine.java\n\n");
		  
		  coffeeVendingMachine esecuzione = new coffeeVendingMachine();
		  
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


