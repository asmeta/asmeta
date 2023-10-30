
// Counter_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
import java.util.Scanner;

class Counter_Exe {
	static void printControlled(Counter esecuzione) {
		System.out.println("counter = " + esecuzione.counter.get());
	}

	static void askMonitored(Counter esecuzione) {
		System.out.print("Inserire un valore booleano per signal (true/false):  ");
		Scanner signalinput = new Scanner(System.in);
		for (;;) {
			Boolean y;
			String signalControllo = signalinput.nextLine();
			if (signalControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(signalControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.signal.set(y);
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println("INFO - file java creto e tradotto dal file originale Counter.asm");
		System.out.println("Inizio esecuzione del file Counter.java\n\n");
		Counter esecuzione = new Counter();
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
