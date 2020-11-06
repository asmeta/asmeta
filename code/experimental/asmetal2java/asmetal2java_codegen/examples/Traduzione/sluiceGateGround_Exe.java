// sluiceGateGround_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class sluiceGateGround_Exe {

	static void printControlled(sluiceGateGround esecuzione) {

		System.out.println("phase = " + esecuzione.phase.oldValue.name());

	}

	static void askMonitored(sluiceGateGround esecuzione) {

		for (int i = 0; i < esecuzione.Minutes_elems.size(); i++) {
			esecuzione.Minutes_elem.value = esecuzione.Minutes_elems.get(i);
			System.out.print(
					"Inserire un valore booleano per passed, chiave "
							+ esecuzione.Minutes_elem.value
							+ " (true/false):  ");
			Scanner passedinput = new Scanner(System.in);

			for (;;) {
				Boolean y;
				String passedControllo = passedinput.nextLine();
				if (passedControllo.isEmpty())
					break;
				try {
					y = Boolean.parseBoolean(passedControllo);
				} catch (Exception e) {
					System.out.println(
							"hai inserito un valore sbagliato, riprova");
					continue;
				}
				// setti la variabile
				esecuzione.passed.set(esecuzione.Minutes_elem, y);
				break;
			}

		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale sluiceGateGround.asm");
		System.out.println("Inizio esecuzione del file sluiceGateGround.java\n\n");

		sluiceGateGround esecuzione = new sluiceGateGround();

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


