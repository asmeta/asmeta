// CashPoint_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class CashPoint_Exe {

	static void printControlled(CashPoint esecuzione) {

		System.out.print("Account" + " = {");
		for (int i = 0; i < esecuzione.Account_lista.size(); i++)
			if (i != esecuzione.Account_lista.size() - 1)
				System.out.print(esecuzione.Account_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Account_lista.get(i));
		System.out.println("}");
		System.out.print("Card" + " = {");
		for (int i = 0; i < esecuzione.Card_lista.size(); i++)
			if (i != esecuzione.Card_lista.size() - 1)
				System.out.print(esecuzione.Card_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Card_lista.get(i));
		System.out.println("}");
		System.out.print("Till" + " = {");
		for (int i = 0; i < esecuzione.Till_lista.size(); i++)
			if (i != esecuzione.Till_lista.size() - 1)
				System.out.print(esecuzione.Till_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Till_lista.get(i));
		System.out.println("}");
		System.out.print("Date" + " = {");
		for (int i = 0; i < esecuzione.Date_lista.size(); i++)
			if (i != esecuzione.Date_lista.size() - 1)
				System.out.print(esecuzione.Date_lista.get(i) + ", ");
			else
				System.out.print(esecuzione.Date_lista.get(i));
		System.out.println("}");

	}

	static void askMonitored(CashPoint esecuzione) {

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale CashPoint.asm");
		System.out.println("Inizio esecuzione del file CashPoint.java\n\n");

		CashPoint esecuzione = new CashPoint();

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


