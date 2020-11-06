// population_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class population_Exe {

	static void printControlled(population esecuzione) {

		System.out.print("Person" + " = {");
		for (int i = 0; i < esecuzione.Person_lista.size(); i++)
			if (i != esecuzione.Person_lista.size() - 1)
				System.out.print(esecuzione.Person_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Person_lista.get(i));
		System.out.println("}");

	}

	static void askMonitored(population esecuzione) {

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale population.asm");
		System.out.println("Inizio esecuzione del file population.java\n\n");

		population esecuzione = new population();

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


