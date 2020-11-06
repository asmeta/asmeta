// fattoriale_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class fattoriale_Exe {

	static void printControlled(fattoriale esecuzione) {

		System.out.println("indice = " + esecuzione.indice.get());

		System.out.println(
				"fattorialeparam = " + esecuzione.fattorialeparam.get());

	}

	static void askMonitored(fattoriale esecuzione) {

		System.out.print("Inserire un valore Intero per valore (Integer):  ");
		Scanner valoreinput = new Scanner(System.in);

		for (;;) {
			int x;
			String valoreControllo = valoreinput.nextLine();
			if (valoreControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(valoreControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.valore.set(x);
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale fattoriale.asm");
		System.out.println("Inizio esecuzione del file fattoriale.java\n\n");

		fattoriale esecuzione = new fattoriale();

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


