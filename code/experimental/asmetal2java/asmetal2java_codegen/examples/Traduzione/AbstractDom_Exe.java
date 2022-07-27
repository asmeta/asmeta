// AbstractDom_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class AbstractDom_Exe {
	
	static void printControlled(AbstractDom esecuzione) {
		
		System.out.print("NumCard"+ " = {");
		for(int i=0 ; i< esecuzione.NumCard_lista.size(); i++)
		if(i!= esecuzione.NumCard_lista.size()-1)
		System.out.print(esecuzione.NumCard_lista.get(i) +", ");
		else
		System.out.print(esecuzione.NumCard_lista.get(i));	
		System.out.println("}");
		
		}
		
	static void askMonitored(AbstractDom esecuzione) {
		
		
		}
	
	
	public static void main(String[] args) {
		
		
		  System.out.println("INFO - file java creto e tradotto dal file originale AbstractDom.asm");
		  System.out.println("Inizio esecuzione del file AbstractDom.java\n\n");
		  
		  AbstractDom esecuzione = new AbstractDom();
		  
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


