
// moncontr_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class moncontr_Exe {
	static void printControlled(moncontr esecuzione) {
		System.out.println("controlledfunction = " + esecuzione.controlledfunction.get());
		System.out.println("controlledfunction2 = " + esecuzione.controlledfunction2.get());
	}

	static void askMonitored(moncontr esecuzione) {
		System.out.print("Inserire un valore Intero per monitoredfunction (Integer):  ");
		Scanner monitoredfunctioninput = new Scanner(System.in);
		for (;;) {
			int x;
			String monitoredfunctionControllo = monitoredfunctioninput.nextLine();
			if (monitoredfunctionControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(monitoredfunctionControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			esecuzione.monitoredfunction.set(x);
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale moncontr.asm");
		System.out.println("Inizio esecuzione del file moncontr.java\n\n");
		moncontr esecuzione = new moncontr();
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
