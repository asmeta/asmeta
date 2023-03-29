
// tombola_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class tombola_Exe {
	static void printControlled(tombola esecuzione) {
	}

	static void askMonitored(tombola esecuzione) {
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale tombola.asm");
		System.out.println("Inizio esecuzione del file tombola.java\n\n");
		tombola esecuzione = new tombola();
		String continuare = "no";
		int stato = 0;
		stato++;
		System.out.println("INITIAL STATE: ");
		do {
			System.out.println("<State " + stato + " (controlled)>");
			//Aggiornamento valori dell'ASM e inserimento dati monitorati
			printControlled(esecuzione);
			askMonitored(esecuzione);
			esecuzione.UpdateASM();
			System.out.println("</State " + stato + " (controlled)>");
			System.out.println("\n<Stato attuale>");
			printControlled(esecuzione);
			System.out.print("Vuoi continuare? (yes/no)  ");
			Scanner input = new Scanner(System.in);
			continuare = input.next();
			stato++;
		} while (continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES"));
		System.out.println("FINAL STATE:");
		//Valori finale delle variabili
		printControlled(esecuzione);
		System.out.println("esecuzione terminata");
	}
}
